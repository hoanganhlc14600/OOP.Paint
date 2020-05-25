/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import shape.DrawType;
import shape.Shape;

/**
 *
 * @author Dang Hung
 */
public class Eraser extends Shape implements DrawType{
    private float size; //size eraser

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public void draw(Graphics2D g2d) {
        setSize(this.width + 10f); // set size eraser
        BasicStroke str = new BasicStroke(size); //tao kich thuoc tay
        g2d.setStroke(str);
        g2d.setColor(strokeColor);
        if (startPoint != null && endPoint != null) {
            g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        }
    }
}
