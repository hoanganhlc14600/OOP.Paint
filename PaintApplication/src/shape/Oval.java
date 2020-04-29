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
 * @author dai
 */
public class Oval extends Shape implements DrawType{
    private Color fillColor; // màu lấp (phần trong) oval

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
    
    
    @Override
    public void draw(Graphics2D g2d) {
        BasicStroke stroke = new BasicStroke(width, cap, join, miterlimit, dash, dash_phase);
        g2d.setStroke(stroke); // lấy kiểu nét vẽ
        if (doFill == true){
        g2d.setColor(fillColor); // lấy màu phần trong oval
        g2d.fillOval(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y),// vẽ oval
                Math.abs(startPoint.x - endPoint.x), Math.abs(startPoint.y - endPoint.y));
        }
        g2d.setColor(strokeColor);                                                          // lấy màu cho phần viền
        g2d.drawOval(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y),// vẽ viền
                Math.abs(startPoint.x - endPoint.x), Math.abs(startPoint.y - endPoint.y));
    }
    
    
}
