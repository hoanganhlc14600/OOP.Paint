/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author hoanganh
 */
public class Selection extends Shape implements DrawType {
    private Point startOrigin, endOrigin; //Lưu điểm bắt đầu và kết thúc ban đầu khi di chuyển hình
    private boolean isCreating = false; //kiểm tra xem đã tạo phần chọn hay chưa
    private boolean isSelected = false; //kiểm tra hình đã được chọn chưa
    private boolean isDragging = false; //kiểm tra hình được chọn có đang di chuyển không
    private int[] data; //lưu pixel ảnh
    private int w;
    private int h;

    public boolean isIsCreating() {
        return isCreating;
    }

    public void setIsCreating(boolean isCreating) {
        this.isCreating = isCreating;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isIsDragging() {
        return isDragging;
    }

    public void setIsDragging(boolean isDragging) {
        this.isDragging = isDragging;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return new Point(startPoint.x + w, startPoint.y + h);
    }

    public Point getStartOrigin() {
        return startOrigin;
    }

    public void setStartOrigin(Point startOrigin) {
        this.startOrigin = startOrigin;
    }

    public Point getEndOrigin() {
        return endOrigin;
    }

    public void setEndOrigin(Point endOrigin) {
        this.endOrigin = endOrigin;
    }

    public int[] getData() {
        return data;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }
    
    public void draw(Graphics2D g2d) {
        BasicStroke stroke = new BasicStroke(1f, BasicStroke.CAP_SQUARE, 
                BasicStroke.JOIN_BEVEL, 3f, new float[]{10f}, 0f);
        
        g2d.setColor(Color.RED);
        g2d.setStroke(stroke);
        BufferedImage img = null;
        //Tạo khoảng trống khi di chuyển
        if (startOrigin != null && endOrigin != null) {
            g2d.setColor(Color.WHITE);
            //tô lại khoảng trống màu trắng
            g2d.fillRect(Math.min(startOrigin.x, endOrigin.x), Math.min(startOrigin.y, endOrigin.y) ,
                    Math.abs(startOrigin.x - endOrigin.x), Math.abs(startOrigin.y - endOrigin.y));
            //Tạo ảnh mới;
            img = new BufferedImage(w, h , BufferedImage.TYPE_INT_RGB); 
            img.getRaster().setPixels(0, 0, w, h, data); // Tạo lại ảnh mang thông số pixels cũ
            g2d.drawImage(img, startPoint.x, startPoint.y, null); //Vẽ ảnh lên
        }
        
        if (!isCreating) {
            //Nếu chưa tạo vùng chọn thì tạo vùng chọn
            g2d.drawRect(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y), 
                    Math.abs(startPoint.x - endPoint.x), Math.abs(startPoint.y - endPoint.y));
        }
        else if (!isSelected) {
            //Nếu đã tạo vùng thì vẽ khung chọn vùng để kéo đi
            g2d.drawRect(startPoint.x, startPoint.y, w, h);
        }
    }
    //Tạo ảnh ảo
    public void setIMG (BufferedImage IMG){
        w = IMG.getWidth();
        h = IMG.getHeight();
        data = new int[w*h*3];
        //Tạo bản ảnh mới có kích thước bằng ảnh ban đầu
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) img.getGraphics(); //Dán ảnh trống có kích thước tương tự ảnh đầu lên g2d
        g2d.drawImage(IMG, 0, 0, null); //Vẽ ảnh ban đầu lên ảnh mới vừa tạo
        g2d.dispose();
        final WritableRaster raster = img.getRaster(); // Tạo raster để lấy lưu trữ pixels của ảnh
        data = raster.getPixels(0, 0, w, h, data); //Trả về một mảng data chứa tất cả các mẫu cho một hình chữ nhật pixel, một mẫu cho mỗi phần tử mảng
    }
    
}
