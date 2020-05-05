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
public class Undo {
    private int data[][];
    private final int size = 10;
    private int topUndo, countUndo;
    private int[] w, h;
    private BufferedImage img;
    
    public Undo() {
        data = new int[size][];
        w = new int[size];
        h = new int[size];
        topUndo = -1;
        countUndo = 0;
    }
    public void push(BufferedImage buff_img) {
        if (countUndo < 10) ++countUndo;
        topUndo = (topUndo + 1) % 10;
        w[topUndo] = buff_img.getWidth();
        h[topUndo] = buff_img.getHeight();
        WritableRaster raster = buff_img.getRaster();
        data[topUndo] = raster.getPixels(0, 0, w[topUndo], h[topUndo], data[topUndo]);
    }
    public BufferedImage pop() {
        if (countUndo > 0 && data[topUndo] != null) {
            img = new BufferedImage(w[topUndo], h[topUndo] , BufferedImage.TYPE_INT_RGB);
            img.getRaster().setPixels(0, 0, w[topUndo], h[topUndo], data[topUndo]);
            countUndo--;
            w[topUndo] = 0;
            h[topUndo] = 0;
            data[topUndo] = null;
            topUndo = (topUndo - 1) % 10;
            return img;
        }
        return null;
    }
    public boolean isEmpty() {
        if (countUndo == 0) return true;
        return false;
    }
}
