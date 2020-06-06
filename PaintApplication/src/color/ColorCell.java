/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package color;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
/**
 *
 * @author Đại
 */
public class ColorCell extends ImageIcon{
    private int width;
    private int height;
    private int align;
    private Color color; 

    public ColorCell(Color color, int width, int height, int align) {
        this.color = color;
        this.width = width;
        this.height = height;
        this.align = align;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        g.fillRect(align, align, width - 2*align, height - 2*align);
    }
    
}
