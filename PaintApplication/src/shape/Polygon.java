/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.Graphics2D;
import java.util.ArrayList;


 /**       
 * hình đa giác
 * @author Phuc
 */
public class Polygon extends Shape implements DrawType {
    private ArrayList<Line> listLine = new ArrayList<>(); //danh sách đường thẳng

    public ArrayList<Line> getListLine() { 
        return listLine;
    }
    
    public void addLine(Line line) //thêm đường thẳng vào danh sách
    {
        listLine.add(line);
    }

    @Override
    public void draw(Graphics2D g2d) {
        
        for(int i=0;i<listLine.size();i++)
        {
            listLine.get(i).draw(g2d);
        }
    }
    
    
}
