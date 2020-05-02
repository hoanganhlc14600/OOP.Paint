/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package property;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
/**
 *
 * @author Đại
 */
public class ColorCell extends ImageIcon{
    public static int WIDTH = 43;
    public static int HEIGHT = 43;
    private Color color = Color.WHITE; 

    public ColorCell(Color color) {
        this.color = color;
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
        g.fillRect(5, 5, WIDTH, HEIGHT);
    }
     
    
    
}
