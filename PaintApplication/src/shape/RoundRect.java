/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author phuc
 */
public class RoundRect extends Shape implements DrawType {
    private Color fillColor;

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    public void draw(Graphics2D g2d) {
        BasicStroke bs = new BasicStroke(width, cap, join, miterlimit, dash, dash_phase);
        g2d.setStroke(bs);
        if (doFill == true) {
        g2d.setColor(fillColor);
        g2d.fillRoundRect(Math.min(startPoint.x,endPoint.x),Math.min(startPoint.y,endPoint.y), Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y), 50, 50);
        }
        g2d.setColor(strokeColor);
        g2d.drawRoundRect(Math.min(startPoint.x,endPoint.x),Math.min(startPoint.y,endPoint.y), Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y), 50, 50);
    }
    
}
