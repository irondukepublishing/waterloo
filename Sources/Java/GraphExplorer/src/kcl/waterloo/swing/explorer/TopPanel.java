/* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package kcl.waterloo.swing.explorer;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import kcl.waterloo.node.ContainerListenerNode;


/**
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class TopPanel extends JPanel {

    private LeftSlidingPanel leftSlidingPanel =  new LeftSlidingPanel();
    private JPanel centerPanel=new JPanel(new BorderLayout());
    private JTabbedPane tabPane=new JTabbedPane();
    private RightSlidingPanel rightSlidingPanel = new RightSlidingPanel();
    


    TopPanel() {
        super();

        tabPane.setPreferredSize(new Dimension(32767, 32767));
        tabPane.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        rightSlidingPanel.setPreferredSize(new Dimension(32767, 32767));
        
        // LEFT PANEL;

        
        
        //Right panel

        setLayout(new GridLayout());
        JSplitPane splitPaneLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        splitPaneLeft.setLeftComponent(leftSlidingPanel);

        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneRight.setLeftComponent(centerPanel);
        JPanel p=new JPanel();
        p.setBackground(Color.YELLOW);
        splitPaneRight.setRightComponent(rightSlidingPanel);
        
        centerPanel.add(tabPane, BorderLayout.CENTER);
        
        
        splitPaneLeft.setRightComponent(splitPaneRight);
        
        ContainerListenerNode node=new ContainerListenerNode(null, "Root", null, tabPane);
        leftSlidingPanel.getExplorerPane().setViewportView(node.getTree());

        leftSlidingPanel.getExplorerSplitPane().setContinuousLayout(true);
        splitPaneLeft.setContinuousLayout(true);
        splitPaneRight.setContinuousLayout(true);

        leftSlidingPanel.getExplorerSplitPane().setResizeWeight(0.8);
        splitPaneLeft.setResizeWeight(0.2);
        splitPaneRight.setResizeWeight(0.8);

        add(splitPaneLeft);
        rightSlidingPanel.revalidate();



    }

    /**
     * @return the leftSlidingPanel
     */
    public JPanel getLeftPanel() {
        return leftSlidingPanel;
    }


    /**
     * @return the tabPane
     */
    public JTabbedPane getTabPane() {
        return tabPane;
    }

    /**
     * @param tabPane the tabPane to set
     */
    public void setTabPane(JTabbedPane tabPane) {
        this.tabPane = tabPane;
    }




    /**
     * @return the centerPanel
     */
    public JPanel getCenterPanel() {
        return centerPanel;
    }
}
