package kcl.waterloo.gui;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class DeployDialog extends JDialog {

    private Component htmlTitle = new JEditorPane();
    private Component description = new JEditorPane();
    private JPanel contents = new JPanel();

    public DeployDialog() {
        setTitle("Deploy to Web");
        setName("DeployToWeb");
        getContentPane().setLayout(new BorderLayout(0, 5));
        contents.setPreferredSize(new Dimension(10, 200));
        contents.setMinimumSize(new Dimension(10, 30));
        getContentPane().add(contents, BorderLayout.NORTH);
        htmlTitle.setPreferredSize(new Dimension(100, 50));
        getContentPane().add(htmlTitle, BorderLayout.CENTER);
        description.setName("");
        description.setPreferredSize(new Dimension(100, 150));
        description.setMinimumSize(new Dimension(0, 150));
        getContentPane().add(description, BorderLayout.SOUTH);

    }

    public Component getHtmlTitle() {
        return htmlTitle;
    }

    public Component getDescription() {
        return description;
    }

    public JPanel getContents() {
        return contents;
    }

}
