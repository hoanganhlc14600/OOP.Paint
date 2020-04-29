/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

/**
 *
 * @author hoanganh
 */
public class Triangle extends Shape implements DrawType{
    private Color fillColor;

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
    
    public void draw(Graphics2D g2d) {
        //Tạo tọa độ x 3 đỉnh tam giác
        int[] x = { startPoint.x, endPoint.x , startPoint.x*2 - endPoint.x};
        //Tạo tọa độ y 3 đỉnh tam giác
        int[] y = { startPoint.y, endPoint.y , endPoint.y};
        //Tạo nét vẽ
        BasicStroke stroke = new BasicStroke( width, cap, join, miterlimit, dash, dash_phase );
        g2d.setStroke(stroke);
        //Vẽ màu nền
        if (doFill == true){
        g2d.setColor(fillColor);
        g2d.fillPolygon(x, y, 3);
        }
        //Vẽ màu viền
        g2d.setColor(strokeColor);
        g2d.drawPolygon(x, y, 3);
    }
}
