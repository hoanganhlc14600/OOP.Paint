/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package property;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 *
 * @author Tuan Hien
 */
public class Redo {
    private int data[][];
    private final int size = 100;
    private int topRedo;
    private int[] w, h;
    private BufferedImage img;
    
    public Redo() {
        data = new int[size][];
        w = new int[size];
        h = new int[size];
        topRedo = -1;
    }
    public void push(BufferedImage buff_img) {
        topRedo++;
        w[topRedo] = buff_img.getWidth();
        h[topRedo] = buff_img.getHeight();
        WritableRaster raster = buff_img.getRaster();
        data[topRedo] = raster.getPixels(0, 0, w[topRedo], h[topRedo], data[topRedo]);
    }
    public BufferedImage pop() {
        if (data[topRedo] != null) {
            img = new BufferedImage(w[topRedo], h[topRedo] , BufferedImage.TYPE_INT_RGB);
            img.getRaster().setPixels(0, 0, w[topRedo], h[topRedo], data[topRedo]);
            w[topRedo] = 0;
            h[topRedo] = 0;
            data[topRedo] = null;
            topRedo--;
            return img;
        }
        return null;
    }
    public boolean isEmpty() {
        if (topRedo == -1) return true;
        return false;
    }
    public void clear() {
        if (topRedo > -1) {
            w[topRedo] = 0;
            h[topRedo] = 0;
            data[topRedo] = null;
            topRedo--;
        }
    }
}
