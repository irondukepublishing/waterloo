 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2011-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * Project Waterloo is free software:  you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Project Waterloo is distributed in the hope that it will  be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.serviceproviders;

import kcl.waterloo.actions.ActionManager;
import kcl.waterloo.graphics.GJGraphInterface;
import kcl.waterloo.gui.BasePanel;
import kcl.waterloo.gui.LogoPanel;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GUIFactory {

    public static void graphEditor(final GJGraphInterface gr) {

        if (gr.fetchEditor() == null) {
            gr.setEditor(new GJGraphEditor(gr));
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EditorFrame frame = new EditorFrame(gr, "GraphEditor");
                //frame.setAlwaysOnTop(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.addWindowListener(frame);
                frame.setResizable(true);
                frame.setLayout(new VerticalLayout());

                JComponent panel = new LogoPanel();
                panel.setPreferredSize(new Dimension(580, 70));
                frame.add(panel);

                panel = new ToolBar((Component) gr);
                panel.setPreferredSize(new Dimension(580, 25));
                frame.add(panel);
                installActions((ToolBar) panel);

                panel = (JComponent) gr.fetchEditor();
                panel.setPreferredSize(new Dimension(580, 540));
                frame.add(panel);

                panel = new BasePanel();
                panel.setPreferredSize(new Dimension(580, 15));
                frame.add(panel);

                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.pack();
            }
        });

    }

    /**
     *
     * @param bar
     */
    private static void installActions(ToolBar bar) {
        bar.getOpenButton().addActionListener(LocalActionListener.getInstance());
        bar.getSaveButton().addActionListener(LocalActionListener.getInstance());
        bar.getSaveAsButton().addActionListener(LocalActionListener.getInstance());
        bar.getDeployButton().addActionListener(LocalActionListener.getInstance());
        bar.getCopyButton().addActionListener(LocalActionListener.getInstance());
        bar.getCopyAsImageButton().addActionListener(LocalActionListener.getInstance());
        bar.getPrintButton().addActionListener(LocalActionListener.getInstance());
        bar.getDefaultsButton().addActionListener(LocalActionListener.getInstance());
        bar.getStartRecording().addActionListener(LocalActionListener.getInstance());
        bar.getPauseRecording().addActionListener(LocalActionListener.getInstance());
        bar.getStopRecording().addActionListener(LocalActionListener.getInstance());
        bar.getSaveRecording().addActionListener(LocalActionListener.getInstance());
    }

    /**
     *
     */
    static class LocalActionListener implements ActionListener {

        static LocalActionListener instance = new LocalActionListener();

        private LocalActionListener() {
        }

        static LocalActionListener getInstance() {
            return instance;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            EditorFrame editor = (EditorFrame) ((JComponent) ae.getSource()).getTopLevelAncestor();
            ActionManager.processAction(ae, (Component) editor.getGraph().getGraphContainer());
        }
    }
}
