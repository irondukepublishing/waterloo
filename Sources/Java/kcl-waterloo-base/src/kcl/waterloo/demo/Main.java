/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.demo;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.LinkedHashMap;

import kcl.waterloo.graphics.GJGraphContainer;
import kcl.waterloo.swing.GCGridContainer;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws IOException
     */
    @SuppressWarnings(value = "unchecked")
    public static void main(String[] args) throws IOException {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Demo.demo1();
                Demo.demo2();
                Demo.demo3();
                Demo.demo4();

                LinkedHashMap<String, Object> o;

                o = (LinkedHashMap<String, Object>) Demo.file1();
                ((GCGridContainer) o.get("Graphics")).createFrame();

                o = (LinkedHashMap<String, Object>) Demo.file2();
                ((GJGraphContainer) o.get("Graphics")).createFrame();

                o = (LinkedHashMap<String, Object>) Demo.file3();
                ((GJGraphContainer) o.get("Graphics")).createFrame();

                o = (LinkedHashMap<String, Object>) Demo.file4();
                ((GJGraphContainer) o.get("Graphics")).createFrame();

                o = (LinkedHashMap<String, Object>) Demo.file5();
                ((GJGraphContainer) o.get("Graphics")).createFrame();

                o = (LinkedHashMap<String, Object>) Demo.file6();
                ((GJGraphContainer) o.get("Graphics")).createFrame();

                o = (LinkedHashMap<String, Object>) Demo.file7();
                ((GCGridContainer) o.get("Graphics")).createFrame();

                o = (LinkedHashMap<String, Object>) Demo.file8();
                ((GJGraphContainer) o.get("Graphics")).createFrame();

            }
        });

    }
}
