/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 *
 * @author hoanganh
 */
public class RightTriangle extends Shape implements DrawType {
    
    private Color fillColor;

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
    
    public void draw(Graphics2D g2d) {
        //Tạo 3 đỉnh
        int[] x = {startPoint.x, startPoint.x, endPoint.x};
        int[] y = {startPoint.y, endPoint.y, endPoint.y};
        //Tạo nét vẽ
        BasicStroke stroke = new BasicStroke(width, cap, join, miterlimit, dash, dash_phase);
        g2d.setStroke(stroke);
        //Vẽ nền tam giác
        if (doFill == true){
        g2d.setColor(fillColor);
        g2d.fillPolygon(x, y, 3);
        }
        //Vẽ viền tam giác
        g2d.setColor(strokeColor);
        g2d.drawPolygon(x, y, 3);
    }
    
}
