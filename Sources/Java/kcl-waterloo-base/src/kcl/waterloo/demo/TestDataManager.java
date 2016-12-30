/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.demo;

import java.beans.XMLDecoder;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author malcolm
 */
public class TestDataManager {

    private static final TestDataManager instance = new TestDataManager();

    final public double[] x1, y1, x2, y2, x3, y3, x4, y4;
    //public ContourExtra C1, C2;

    private TestDataManager() {
        final InputStream buffer = TestDataManager.class.getResourceAsStream("resources/TestData.xml");
        XMLDecoder decoder;
        decoder = new XMLDecoder(buffer);
        Thread.currentThread().setContextClassLoader(TestDataManager.class.getClassLoader());
        x1 = (double[]) decoder.readObject();
        y1 = (double[]) decoder.readObject();
        x2 = (double[]) decoder.readObject();
        y2 = (double[]) decoder.readObject();
        x3 = (double[]) decoder.readObject();
        y3 = (double[]) decoder.readObject();
        x4 = (double[]) decoder.readObject();
        y4 = (double[]) decoder.readObject();
        //C1 = (ContourExtra) decoder.readObject();
        //C2 = (ContourExtra) decoder.readObject();
        decoder.close();
        try {
            buffer.close();
        } catch (IOException ex) {
        }
    }

    public static TestDataManager getInstance() {
        return instance;
    }
}
