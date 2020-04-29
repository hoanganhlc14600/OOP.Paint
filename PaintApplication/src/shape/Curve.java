/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

/**
 *
 * @author Dang Hung
 */
public class Curve extends Shape implements DrawType{
    private int state = 1;//trang thai cua doan thang
    private ArrayList<Point> curveLine = new ArrayList<>();
    private ArrayList<Point> State1, State2,State3;
    public Curve(){
        //tao 4 diem ban dau cho CurveLine de tien loi cho viec set cac diem
        //vi diem dau la start point va diem cuoi doan thang o cuoi CurveLine
        for(int i=0 ; i<2;i++){
            curveLine.add(new Point(0, 0));
        }
    }

    public int getState() {
        return state;
    }

    public void resetState(){
        this.state = 1;
    }
    
    public void updateState() {
        this.state ++;
    }

    public ArrayList<Point> getCurveLine() {
        return curveLine;
    }

    public void setCurveLine(ArrayList<Point> curveLine) {
        this.curveLine = curveLine;
    }

    @Override
    public void draw(Graphics2D g2d) {
        BasicStroke str = new BasicStroke(width, cap, join, miterlimit, dash, dash_phase);
        g2d.setStroke(str);
        g2d.setColor(strokeColor);
        if(this.state != 3){
            //get(0): start; get(1),get(2) la control point(dieu huong cong); get(3):end
            //doan code co y nghia sau
            //neu state = 1: chi moi ve doan thang chua cong; control point chua co
            //vi vay cho control point trung voi diem end de van la duong thang // get(2)<-get(3)(end) => get(1) <-get(2)
            //neu state =2 : da co control point nhung chi co 1 control point
            //tao 2 control point trung nhau get(1) <-get(2) vi mousedragger luon tao control point add vao get(2)
            //state = 3: 2 control point khac nhau khong can thay doi gi ca
            if(this.state == 1){
                curveLine.get(1).setLocation(this.getEndPoint());
            }
            curveLine.get(0).setLocation(curveLine.get(1));
        }
        GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        path.moveTo(this.getStartPoint().x, this.startPoint.y); //co dinh diem start   
        path.curveTo(curveLine.get(0).x, curveLine.get(0).y, curveLine.get(1).x, curveLine.get(1).y, this.getEndPoint().x, this.getEndPoint().y); // point 2 + point 3
        g2d.draw(path);
    }
}
