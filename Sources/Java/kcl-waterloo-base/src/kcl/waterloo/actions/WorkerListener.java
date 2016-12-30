/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.actions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.SwingWorker;

/**
 *
 * @author ML
 */
public class WorkerListener implements PropertyChangeListener {

    Object messageInterface = null;
    String doneMessage = "";
    Method m;

    public WorkerListener(Object messageInterface, String doneMessage) {
        this.messageInterface = messageInterface;
        this.doneMessage = doneMessage;
        try {
            m = messageInterface.getClass().getMethod("showMessage", new Class[]{String.class});
        } catch (NoSuchMethodException ex) {
            try {
                m = messageInterface.getClass().getMethod("setText", new Class[]{String.class});
            } catch (NoSuchMethodException ex1) {
            } catch (SecurityException ex1) {
            }
        } catch (SecurityException ex) {
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if (((SwingWorker) pce.getSource()).isDone()) {
            if (m != null) {
                try {
                    m.invoke(messageInterface, doneMessage);
                } catch (IllegalAccessException ex) {
                } catch (IllegalArgumentException ex) {
                } catch (InvocationTargetException ex) {
                }
            }
        } else {
            if (m != null) {
                SwingWorker worker = (SwingWorker) pce.getSource();
                int progress = worker.getProgress();
                try {
                    m.invoke(messageInterface, progress + "% Completed");
                } catch (IllegalAccessException ex) {
                } catch (IllegalArgumentException ex) {
                } catch (InvocationTargetException ex) {
                }
            }
        }

    }
}
