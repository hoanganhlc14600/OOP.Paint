/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

/**
 *
 * @author dai
 */
public class Line extends Shape implements DrawType{

    @Override
    public void draw(Graphics2D g2d) {
        BasicStroke stroke = new BasicStroke(width, cap, join, miterlimit, dash, dash_phase);
        g2d.setStroke(stroke); // lấy kiểu nét vẽ
        g2d.setColor(strokeColor); // lấy màu
        g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y); // dùng điểm đầu cuối để vẽ
    }
    
}
