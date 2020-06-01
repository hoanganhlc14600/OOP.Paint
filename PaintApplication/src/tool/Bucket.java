
package tool;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import shape.DrawType;
import shape.Shape;

/**
 *
 * @author Dang Hung
 */
public class Bucket extends Shape implements DrawType{
    private Color color;// mau duoc chon de to 
    private int color_fill ;//mau duoc chon de to dinh dang int
    private int color_before;//mau truoc khi to dinh dang int
    //getter and setter
    public void setStart(Point start){
        this.startPoint = start; 
    }
        
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    //overloading draw
    public void draw(BufferedImage image){
        color_before = image.getRGB(startPoint.x, startPoint.y);//tao mau ban dau cua diem anh
        color_fill = this.color.getRGB();//tao mau muon to
        spreadFill(image);
        
    }
    //thuat toan to mau loang - bfs
    public void spreadFill(BufferedImage image){
        ArrayList<Point> listPoints = new ArrayList<>();
        if(color_before == color_fill){
            //check color tai diem xet neu da to dung mau thi dung
            return;
        }
        else {
            image.setRGB(startPoint.x, startPoint.y, color_fill);//to cho diem anh toa do click
            listPoints.add(startPoint);//them click vao dau cua hang doi de thuc hien loang tu click
            while(!listPoints.isEmpty()){
                //lay ra phan tu dau danh sach de xet va xet toi khi hang doi rong
                // thuat toan giong bfs tren do thi
                Point pointEx = listPoints.get(0);
                listPoints.remove(0);
                //check 4  diem anh xung quanh diem dang xet
                //               (x,y-1)     
                //(x-1,y)                   (x+1,y)
                //               (x,y+1)     
                //image.setRGB(Point) == color_before: check xem diem anh co phai canh da giac hay k
                //cac phep so sanh de tranh bug tran vien
                if (pointEx.x - 1 >= 0 && image.getRGB(pointEx.x - 1, pointEx.y) == color_before) {
                    image.setRGB(pointEx.x - 1, pointEx.y, color_fill);
                    listPoints.add(new Point(pointEx.x - 1, pointEx.y));
                }
                if (pointEx.y - 1 >= 0 && image.getRGB(pointEx.x, pointEx.y - 1) == color_before) {
                    image.setRGB(pointEx.x, pointEx.y - 1, color_fill);
                    listPoints.add(new Point(pointEx.x, pointEx.y - 1));
                }
                if (pointEx.y+1 <= image.getHeight()-1 && image.getRGB(pointEx.x, pointEx.y + 1) == color_before) {
                    image.setRGB(pointEx.x, pointEx.y + 1, color_fill);
                    listPoints.add(new Point(pointEx.x, pointEx.y + 1));
                }
                if (pointEx.x+1 <= image.getWidth()-1 && image.getRGB(pointEx.x+1, pointEx.y ) == color_before) {
                    image.setRGB(pointEx.x+1, pointEx.y , color_fill);
                    listPoints.add(new Point(pointEx.x+1, pointEx.y ));
                }
            }
        }
    }
    @Override
    public void draw(Graphics2D g2d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
