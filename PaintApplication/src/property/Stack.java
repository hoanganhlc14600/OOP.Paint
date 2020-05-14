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
public class Stack {
    private int data[][];
    private final int size = 1000;
    private int top;
    private int[] w, h;
    private BufferedImage img;
    
    public Stack() {
        data = new int[size][];
        w = new int[size];
        h = new int[size];
        top = -1;
    }
    public void push(BufferedImage buff_img) {
        top++;
        w[top] = buff_img.getWidth();
        h[top] = buff_img.getHeight();
        WritableRaster raster = buff_img.getRaster();
        data[top] = raster.getPixels(0, 0, w[top], h[top], data[top]);
    }
    public BufferedImage pop() {
        if (data[top] != null) {
            img = new BufferedImage(w[top], h[top] , BufferedImage.TYPE_INT_RGB);
            img.getRaster().setPixels(0, 0, w[top], h[top], data[top]);
            w[top] = 0;
            h[top] = 0;
            data[top] = null;
            top--;
            return img;
        }
        return null;
    }
    public boolean isEmpty() {
        if (top == -1) return true;
        return false;
    }
    public void clear() {
        if (top > -1) {
            w[top] = 0;
            h[top] = 0;
            data[top] = null;
            top--;
        }
    }
}
