/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.serviceproviders;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import kcl.waterloo.graphics.GJGraphInterface;

/**
 *
 * @author Malcolm Lidierth
 */
public class EditorFrame extends JFrame implements WindowListener {

    private final GJGraphInterface graph;

    EditorFrame(GJGraphInterface gr, String s) {
        super(s);
        graph = gr;
    }

    /**
     * @return the graph
     */
    public GJGraphInterface getGraph() {
        return graph;
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosing(WindowEvent we) {
    }

    @Override
    public void windowClosed(WindowEvent we) {
        graph.setEditor(null);
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
    }

}
