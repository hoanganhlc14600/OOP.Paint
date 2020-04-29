/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.BasicStroke; 
import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author hoanganh
 */

//Định dạng các kiểu nét vẽ 

public class Shape implements Serializable {
    protected Color strokeColor;
    protected ArrayList <Point> arrPoint = new ArrayList<Point>();
    protected Point startPoint;
    protected Point endPoint;
    protected boolean doFill = true; //Có đổ màu nền hay không ? True - Có / False - Không
    //Lấy màu    
    public Color getStrokeColor() {
        return strokeColor;
    }
    
    //Chọn màu
    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    //Lấy ds điểm
    public ArrayList<Point> getArrPoint() {
        return arrPoint;
    }
    
    //Thêm điểm vào ds điểm 
    public void addArrPoint(Point arr) {
        arrPoint.add(arr);
    }
    
    public Point getStartPoint() {
        return startPoint;
    }
    
    public Point getEndPoint(){
        return endPoint;
    }
    
    public void setStartPoint(Point start){
        this.startPoint = start;
    }
    
    public void setEndPoint(Point end){
        this.endPoint = end;
    }
    //Tạo điểm bắt đầu và kết thúc
    public void setPoint(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public boolean isDoFill() {
        return doFill;
    }

    public void setDoFill(boolean doFill) {
        this.doFill = doFill;
    }

    protected float width; //độ dày đoạn thẳng
    protected int cap; //viền 2 đầu đoạn thẳng
    protected int join; //Định dạng đường nối giữa 2 đt cắt nhau
    protected float miterlimit = 1.0f; // giới hạn cắt, min là 1.0f
    protected float[] dash; //định dạng phần ẩn, phần hiện của đoạn thẳng
                    // ví dụ dash = {2f , 0f , 2f}; nghĩa là vẽ 1 đoạn
                    // 2f pixel rồi cách ra 0f pixel rồi vẽ tiếp đoạn 2f pixel
    protected float dash_phase; // độ dài đoạn lồi ở điểm bắt đầu
    
    //định dạng thông tin của 1 stroke
    public void setStroke(BasicStroke stroke) {
        this.width = stroke.getLineWidth();
        this.cap = stroke.getEndCap();
        this.join = stroke.getLineJoin();
        this.miterlimit = stroke.getMiterLimit();
        this.dash = stroke.getDashArray();
        this.dash_phase = stroke.getDashPhase();
    }
}
