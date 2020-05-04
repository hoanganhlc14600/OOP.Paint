/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package property;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

/**
 *
 * @author Tuan Hien
 */
public class Undo {
    private Raster[] undo;
    private BufferedImage tmp;
    private final int size = 10;
    private int topUndo, countUndo;
    private WritableRaster writableRaster;
    private ColorModel colorModel;
    public Undo() {
        undo = new Raster[size];
        topUndo = -1;
        countUndo = 0;
    }
    public void push(Raster img, ColorModel colorModel) {
        topUndo = (topUndo + 1) % size;
        if (undo[topUndo] == null) countUndo++;
        undo[topUndo] = img;
        colorModel = colorModel;
    }
    public BufferedImage pop() {
        if (countUndo != 0) {
            WritableRaster writableRaster = undo[topUndo].createCompatibleWritableRaster();
            writableRaster.setDataElements(0, 0, undo[topUndo]);
            tmp = new BufferedImage(colorModel, writableRaster, true, null);
            undo[topUndo] = null;
            topUndo = (topUndo - 1) % size;
            countUndo--;
            return tmp;
        }
        return null;
    }
    public boolean isEmpty() {
        if (countUndo == 0) return true;
        return false;
    }
}
