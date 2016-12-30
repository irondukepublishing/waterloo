package kcl.waterloo.gui;

import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class Welcome extends JWindow {

    private JPanel contentPane;

    /**
     * Launch the application.
     *
     * @return the Welcome frame
     */
    public static Welcome createWelcome() {
        final Welcome frame;
        frame = new Welcome();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
                frame.validate();
            }
        });
        return frame;
    }

    /**
     * Create the frame.
     */
    public Welcome() {
        setBounds(200, 100, 450, 80);
        setContentPane(new LogoPanel());
        setLabel("Scientific Graphics");
    }

    public final void setLabel(String txt) {
        LogoPanel panel = (LogoPanel) getContentPane();
        panel.setLabelText(txt);
    }

    public final void setCentralLabel(String txt) {
        LogoPanel panel = (LogoPanel) getContentPane();
        panel.setCentralText(txt);
    }
}
