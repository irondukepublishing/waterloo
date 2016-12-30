/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.graphics.transforms;

/**
 *
 * @author ML
 */
public interface GJTransform {

    public Object get(int index);

    public Object getAxisTickPositions(Object o, double v0, double v1, double v2);

}
