/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 *
 * @author Phúc
 */
public class Text extends Shape {

    static final int EDGE = 4; //cạnh hình vuông nhỏ = 4

    private Boolean isOpaque;           //Kiểm tra có làm mờ không?
    private Boolean isCreated = false; //kiểm tra đã được tạo chưa?

    private Color fillColor;    //màu bên trong khung
    private Color textColor;    //màu chữ
    private Color frameColor;   //màu khung

    private final BasicStroke stroke1 = new BasicStroke(1f); //vẽ hình vuông
    private final BasicStroke stroke2 = new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 2f, new float[]{2f, 0f, 2f}, 2f);//vẽ viền nét đứt

    private JTextPane area;//phần viết
    private Font font; //font chữ

    private Point start; //điểm bắt đầu
    private Point finish; //điểm kết thúc

    private String string = "";

    public Text() {
        this.setStroke(stroke2);//khung nét đứt
        this.setTextColor(Color.BLACK); //màu chữ mặc định là màu đen
        this.setFrameColor(Color.BLUE); //khung để viết màu xanh 
    }

    public Boolean getIsOpaque() {
        return isOpaque;
    }

    public void setIsOpaque(Boolean isOpaque) {
        this.isOpaque = isOpaque;
    }

    public Boolean getIsCreated() {
        return isCreated;
    }

    public void setIsCreated(Boolean isCreated) {
        this.isCreated = isCreated;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getFrameColor() {
        return frameColor;
    }

    public void setFrameColor(Color frameColor) {
        this.frameColor = frameColor;
    }

    public JTextPane getArea() {
        return area;
    }

    public void setArea(JPanel panel) { //them vao khung Panel
        area = new JTextPane();
        int[] a = {Math.min(start.x, finish.x), Math.min(start.y, finish.y), Math.max(start.x, finish.x), Math.max(start.y, finish.y)};
        area.setBounds(a[0] + 1, a[1] + 1, a[2] - a[0] - 1, a[3] - a[1] - 1); //kích thước vùng viết
        panel.add(area);
    }

    public void removeArea(JPanel panel) // xoa khoi Panel
    {
        panel.remove(area);
    }

    public void setFontArea() {
        area.setFont(font);
    }

    public Font getFontArea() {
        return area.getFont();
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getFinish() {
        return finish;
    }

    public void setFinish(Point finish) {
        this.finish = finish;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Boolean checkOverLap() //kiểm tra xem tung độ,hoành độ của điểm đầu,cuối có trùng nhau k?
    {
        if (start.x == finish.x || start.y == finish.y) {
            return true;
        } else {
            return false;
        }
    }

    public void draw(Graphics2D g2d, Graphics g2) {
        int[] a = {Math.min(start.x, finish.x), Math.min(start.y, finish.y), Math.max(start.x, finish.x), Math.max(start.y, finish.y)};
        if (isCreated == false) {
            if (start != null && finish != null) {
                g2d.setColor(frameColor);
                //vẽ hình vuông nhỏ
                g2d.setStroke(stroke1);
                g2d.drawRect(a[0] - EDGE / 2, a[1] - EDGE / 2, EDGE, EDGE);
                g2d.drawRect(a[0] - EDGE / 2, a[3] - EDGE / 2, EDGE, EDGE);
                g2d.drawRect(a[2] - EDGE / 2, a[1] - EDGE / 2, EDGE, EDGE);
                g2d.drawRect(a[2] - EDGE / 2, a[3] - EDGE / 2, EDGE, EDGE);
                g2d.drawRect((a[0] + a[2]) / 2 - EDGE / 2, a[1] - EDGE / 2, EDGE, EDGE);
                g2d.drawRect((a[0] + a[2]) / 2 - EDGE / 2, a[3] - EDGE / 2, EDGE, EDGE);
                g2d.drawRect(a[0] - EDGE / 2, (a[1] + a[3]) / 2 - EDGE / 2, EDGE, EDGE);
                g2d.drawRect(a[2] - EDGE / 2, (a[1] + a[3]) / 2 - EDGE / 2, EDGE, EDGE);
                //viền nét đứt của khung
                g2d.setStroke(stroke2);
                g2d.drawLine(a[0] + EDGE / 2, a[1], (a[0] + a[2]) / 2 - EDGE / 2, a[1]);
                g2d.drawLine((a[0] + a[2]) / 2 + EDGE / 2, a[1], a[2] - EDGE / 2, a[1]);
                g2d.drawLine(a[0] + EDGE / 2, a[3], (a[0] + a[2]) / 2 - EDGE / 2, a[3]);
                g2d.drawLine((a[0] + a[2]) / 2 + EDGE / 2, a[3], a[2] - EDGE / 2, a[3]);
                g2d.drawLine(a[0], a[1] + EDGE / 2, a[0], (a[1] + a[3]) / 2 - EDGE / 2);
                g2d.drawLine(a[0], (a[1] + a[3]) / 2 + EDGE / 2, a[0], a[3] - EDGE / 2);
                g2d.drawLine(a[2], a[1] + EDGE / 2, a[2], (a[1] + a[3]) / 2 - EDGE / 2);
                g2d.drawLine(a[2], (a[1] + a[3]) / 2 + EDGE / 2, a[2], a[3] - EDGE / 2);
            }
        }
        if(string.equals("") == false)
        {
        if (isOpaque) {
            g2.setFont(font);
            g2.setColor(fillColor);
            g2.fillRect(a[0], a[1], (a[2] - a[0]) , a[3] - a[1]);
            g2.setColor(textColor);
            for (String s : string.split("\n"))
                g2.drawString(s, start.x, start.y += g2d.getFontMetrics().getHeight()); // FontMetricc lấy số liệu của Font chữ
        }
        else
        {
            g2.setFont(font);
            g2.setColor(textColor);
            for (String s : string.split("\n"))
                g2.drawString(s, a[0], a[1] += g2d.getFontMetrics().getHeight());
        }
        }
    }
}
