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
 * hình chữ nhật
 *
 * @author Phúc
 */
public class Rectangle extends Shape implements DrawType {

    private Color fillColor; //màu bên trong hcn

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    public void draw(Graphics2D g2d) {
        BasicStroke bs = new BasicStroke(width, cap, join, miterlimit, dash, dash_phase); //định dạng viền hcn 
        g2d.setStroke(bs);
        if (doFill == true){
        g2d.setColor(fillColor);
        g2d.fillRect(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y), Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
        }
        g2d.setColor(strokeColor); // vẽ viền
        g2d.drawRect(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y), Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
    }

}
