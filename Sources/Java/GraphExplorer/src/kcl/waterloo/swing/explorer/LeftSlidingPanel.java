package kcl.waterloo.swing.explorer;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import kcl.waterloo.docking.DockContainer;

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
/**
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class LeftSlidingPanel extends DockContainer{
    
    private JScrollPane explorerPane;
    private JSplitPane explorerSplitPane;
    
    public LeftSlidingPanel(){
        setPreferredSize(new Dimension(200, 500));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        // EXPLORER
        explorerSplitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        explorerSplitPane.setName("Explorer");
        add(explorerSplitPane);
        // Graph Explorer
        explorerPane=new JScrollPane();
        explorerPane.setPreferredSize(new Dimension(32767,300));
        explorerSplitPane.setTopComponent(explorerPane);
        //Variables pane
        JScrollPane variablesPane=new JScrollPane();
        variablesPane.setBorder(new TitledBorder("Variables"));
        explorerSplitPane.setBottomComponent(variablesPane);
        JTable variableTable=new JTable(5,2);
        variableTable.getColumnModel().getColumn(0).setHeaderValue("Name");
        variableTable.getColumnModel().getColumn(1).setHeaderValue("Value");
        variablesPane.setViewportView(variableTable);
        
        JPanel extraPanel=new JPanel();
        extraPanel.setName("Extra");
        extraPanel.setPreferredSize(new Dimension(32767,75));
        add(extraPanel);
        

    }

    /**
     * @return the explorerSplitPane
     */
    public JSplitPane getExplorerSplitPane() {
        return explorerSplitPane;
    }

    /**
     * @param explorerSplitPane the explorerSplitPane to set
     */
    public void setExplorerSplitPane(JSplitPane explorerSplitPane) {
        this.explorerSplitPane = explorerSplitPane;
    }

    /**
     * @return the explorerPane
     */
    public JScrollPane getExplorerPane() {
        return explorerPane;
    }

    /**
     * @param explorerPane the explorerPane to set
     */
    public void setExplorerPane(JScrollPane explorerPane) {
        this.explorerPane = explorerPane;
    }

}
