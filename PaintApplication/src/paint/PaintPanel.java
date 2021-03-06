/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

/**
 *
 * @author All
 */
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import tool.Bucket;
import tool.Eraser;
import shape.Line;
import shape.Oval;
import tool.Pencil;
import shape.Polygon;
import shape.Rectangle;
import shape.RightTriangle;
import shape.RoundRect;
import shape.Triangle;
import shape.Curve;
import javax.swing.JRadioButton;
import color.ColorChooser;
import java.awt.geom.Ellipse2D;
import tool.Stack;
import tool.Stroke;
import tool.TextPanel;
import tool.Text;
import shape.Selection;

public class PaintPanel extends javax.swing.JPanel implements MouseListener, MouseMotionListener {

    /**
     * Creates new form DrawPanel
     */
    Graphics2D g2d, g2; // doi tuong do hoa
    private BufferedImage buff_img, copy_img, sub_img; // anh de ve
    public boolean isSaved = false;
    private Point startPoint, endPoint;
    private JLabel jCoordinate;
    private Line line;
    private Oval oval;
    private Eraser eraser;
    private Rectangle rect;
    private RoundRect roundRect;
    private Triangle triangle;
    private RightTriangle rightTriangle;
    private Polygon polygon;
    private Pencil pencil;
    private Bucket bucket;
    private Curve curve;
    private String mode;
    private Stroke stroke;
    private ColorChooser colorChooser;
    private Stack undo, redo, replay;
    private Text text;
    private Selection select;
    private int x = 0;
    private double r = 15.0;
    private TextPanel textPanel = new TextPanel();
    private int tempx, tempy;
    private boolean startSelect = false;
    private int width = 800;
    private int height = 500;
    private JRadioButton isFill;

    
    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }
    public void setColorChooser(ColorChooser colorChooser) {
        this.colorChooser = colorChooser;
    }
    public void setIsFill(JRadioButton isFill) {
        this.isFill = isFill;
    }
    public void setTextPanel(TextPanel textPanel) {
        this.textPanel = textPanel;
    }

    public PaintPanel() {
        initComponents();

        mode = new String("PENCIL");
        pencil = new Pencil();
        line = new Line();
        oval = new Oval();
        rect = new Rectangle();
        roundRect = new RoundRect();
        triangle = new Triangle();
        rightTriangle = new RightTriangle();
        polygon = new Polygon();
        eraser = new Eraser();
        bucket = new Bucket();
        buff_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        undo = new Stack();
        redo = new Stack();
        replay = new Stack();
        this.setSize(width, height);
        this.setLocation(5, 5);

        g2d = (Graphics2D) buff_img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(255, 255, 255));
        g2d.fillRect(0, 0, width, height);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    private void doDrawing(Graphics g) {
        g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(buff_img, null, 0, 0);
        if (startPoint != null && endPoint !=null) {
            switch (mode) {
            case "LINE":
                line.draw(g2);
                break;
            case "OVAL":
                oval.draw(g2);
                break;
            case "RECTANGLE":
                rect.draw(g2);
                break;
            case "ROUNDRECTANGLE":
                roundRect.draw(g2);
                break;
            case "TRIANGLE":
                triangle.draw(g2);
                break;
            case "RIGHTTRIANGLE":
                rightTriangle.draw(g2);
                break;
            case "POLYGON":
                line.draw(g2);
                break;
            case "CURVE":
                if (curve != null) {
                    curve.draw(g2);
                }
                break;
            case "TEXT":
                if (text != null) {
                    text.draw(g2, g2d);
                }
                break;
            case "SELECT":
                if (select != null) {
                    select.draw(g2);
                }
                break;
            case "MAGNIFIER":
                if (startPoint.x < 20) startPoint.x = 20;
                if (startPoint.x > buff_img.getWidth() - 20) startPoint.x = buff_img.getWidth() - 20;
                if (startPoint.y < 20) startPoint.y = 20;
                if (startPoint.y > buff_img.getHeight() - 20) startPoint.y = buff_img.getHeight() - 20;
                
                sub_img = buff_img.getSubimage(startPoint.x-20, startPoint.y-20, 40, 40);
                
                g2.scale(3, 3);
                g2.setClip(new Ellipse2D.Float(startPoint.x/3 -20, startPoint.y/3-20, 40, 40));
                g2.drawImage(sub_img, null, startPoint.x/3 -20, startPoint.y/3-20);
                g2.setColor(Color.pink);
                g2.drawOval(startPoint.x/3 -19, startPoint.y/3-19, 38, 38);
                
                break;
            }
        }
    }
    public Stack getReplay(){
        return replay;
    }
    public void clear() {
        undo.clear();
        redo.clear();
        isSaved = true;
    }
    
    public void flipping(int typeFlip) {
        AffineTransform at = new AffineTransform();
        if (startSelect == true) {
            if (select.isIsCreating() == true) {
                if (select.isIsSelected() == false) {  //Neu anh chua duoc co dinh tren Buffer
                    int w = select.getWidth();
                    int h = select.getHeight();
                    if (typeFlip == 1) {   //lật dọc ảnh
                        at = AffineTransform.getScaleInstance(1, -1);
                        at.translate(0, -h);
                    } else if (typeFlip == 2) { //Lật ngang ảnh
                        at = AffineTransform.getScaleInstance(-1, 1);
                        at.translate(-w, 0);
                    }
                    AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    int[] data = select.getData();
                    BufferedImage Image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                    Image.getRaster().setPixels(0, 0, w, h, data);
                    Image = op.filter(Image, null);
                    select.setIMG(Image); //sửa ảnh trong phần đã được chọn
                }
            }
        } else {
            if (typeFlip == 1) {
                at = AffineTransform.getScaleInstance(1, -1);
                at.translate(0, -buff_img.getHeight());
            } else if (typeFlip == 2) {
                at = AffineTransform.getScaleInstance(-1, 1);
                at.translate(-buff_img.getWidth(), 0);
            }
            AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            buff_img = op.filter(buff_img, null);
            g2d = (Graphics2D) buff_img.getGraphics();
        }
        repaint();
    }

    public void rotate(int alpha) {
        AffineTransform at = new AffineTransform();
        if (startSelect == true) {
            if (select.isIsCreating() == true) {
                if (select.isIsSelected() == false) {
                    //Neu anh chua duoc co dinh tren Buffer

                    int w = select.getWidth();
                    int h = select.getHeight();
                    if (alpha == 90) {
                        at.translate(h, 0);
                    } else if (alpha == -90) {
                        at.translate(0, w);
                    }
                    at.rotate(Math.toRadians(alpha), 0, 0);
                    AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    int[] data = select.getData();
                    BufferedImage Image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                    Image.getRaster().setPixels(0, 0, w, h, data);
                    Image = op.filter(Image, null);
                    select.setIMG(Image);
                }
            }
        } else {
            at = new AffineTransform();
            if (alpha == 90) {
                at.translate(buff_img.getHeight(), 0);
            } else if (alpha == -90) {
                at.translate(0, buff_img.getWidth());
            }
            at.rotate(Math.toRadians(alpha));
            AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            buff_img = op.filter(buff_img, null);
            g2d = (Graphics2D) buff_img.getGraphics();
        }
        repaint();
    }
    
    public void cut(){
        if(select != null){
            int[] data = select.getData();
            int w = select.getWidth();
            int h = select.getHeight();
            copy_img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            copy_img.getRaster().setPixels(0, 0, w, h, data);
            Graphics2D g = (Graphics2D) buff_img.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(select.getStartOrigin().x, select.getStartOrigin().y, w, h);
            select = null;
            repaint();
            g.dispose();
        }
    }
    
    public void copy(){
        if(select != null){
            int[] data = select.getData();
            int w = select.getWidth();
            int h = select.getHeight();
            copy_img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            copy_img.getRaster().setPixels(0, 0, w, h, data);
            select = null;
            repaint();
        }
        
    }
    
    public void paste(){
        if(copy_img != null ){
            Point p = new Point(0,0);
            select = new Selection();
            select.setIsPaste(true);
            select.setIMG(copy_img);
            startSelect = true; //Da duoc khoi tao
            select.setStartOrigin(p);
            select.setStartPoint(p);
            select.setEndPoint(p);
            select.setEndOrigin(new Point(p.x+copy_img.getWidth(), p.y+copy_img.getHeight()));
            select.setIsCreating(true);
            repaint();
        }
    }

    public void setImage(BufferedImage img) {
        width = img.getWidth();
        height = img.getHeight();
        buff_img = img;
        g2d = (Graphics2D) buff_img.createGraphics();
        this.setSize(width, height);
        this.revalidate();
        this.repaint();
       
    }

    public BufferedImage getImage() {
        return buff_img;
    }

    public void setCoordinate(JLabel jCoordinate) {
        this.jCoordinate = jCoordinate;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    private boolean testSel(Point test) {
        if (select != null && select.getStartPoint() != null && select.getEndPoint() != null) {
            if (test.x < Math.min(select.getStartPoint().x, select.getEndPoint().x)
                    || test.x > Math.max(select.getStartPoint().x, select.getEndPoint().x)) {
                return false;
            } else if (test.y < Math.min(select.getStartPoint().y, select.getEndPoint().y)
                    || test.y > Math.max(select.getStartPoint().y, select.getEndPoint().y)) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }
    
    public void Undo() {
        if (!undo.isEmpty()) {
            redo.push(buff_img);
            setImage(undo.pop());
            replay.push(buff_img);
        }
    }
    
    public void Redo() {
        if (!redo.isEmpty()) {
            undo.push(buff_img);
            setImage(redo.pop());
            replay.push(buff_img);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void ChangeTool() {
        if (polygon.getStartPoint() != null) {
            undo.push(buff_img);
            replay.push(buff_img);
            line.setPoint(endPoint, polygon.getStartPoint());
            line.draw(g2d);
            repaint();
            polygon.setPoint(null, null);
            startPoint = null;
            endPoint = null;
        } else if (curve != null) {
            curve.draw(g2d);
            repaint();
            curve = null;
            startPoint = null;
            endPoint = null;
        } else if (text != null) {
            undo.push(buff_img);
            replay.push(buff_img);
            text.setString();
            text.removeArea(this);
            text.draw(g2, g2d);
            repaint();
            text = null;
        } else if (startSelect == true) {
            if (select.isIsCreating()) {
                undo.push(buff_img);
                replay.push(buff_img);
                select.setIsSelected(true);
                select.draw(g2d);
                select = null;
                startSelect = false;
                startPoint = null;
                endPoint = null;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {//goi khi giu chuot
        startPoint = e.getPoint();
        redo.clear();
        switch (mode) {
            case "PENCIL":
                undo.push(buff_img);
                replay.push(buff_img);
                pencil.setPoint(startPoint, startPoint);
                pencil.setStroke(stroke.getStroke());
                pencil.setStrokeColor(colorChooser.getStrokeColor());
                pencil.draw(g2d);
                break;
            case "ERASER":
                undo.push(buff_img);
                replay.push(buff_img);
                eraser.setPoint(startPoint, startPoint);
                eraser.setStrokeColor(colorChooser.getFillColor());
                eraser.setSize(10);
                eraser.setStroke(stroke.getStroke());
                eraser.draw(g2d);
                break;
            case "LINE":
                undo.push(buff_img);
                replay.push(buff_img);
                line.setPoint(startPoint, startPoint);
                line.setStrokeColor(colorChooser.getStrokeColor());
                line.setStroke(stroke.getStroke());
                break;
            case "OVAL":
                undo.push(buff_img);
                replay.push(buff_img);
                oval.setPoint(startPoint, startPoint);
                oval.setDoFill(isFill.isSelected());
                oval.setFillColor(colorChooser.getFillColor());
                oval.setStrokeColor(colorChooser.getStrokeColor());
                oval.setStroke(stroke.getStroke());
                break;
            case "RECTANGLE":
                undo.push(buff_img);
                replay.push(buff_img);
                rect.setPoint(startPoint, startPoint);
                rect.setDoFill(isFill.isSelected());
                rect.setStrokeColor(colorChooser.getStrokeColor());
                rect.setFillColor(colorChooser.getFillColor());
                rect.setStroke(stroke.getStroke());
                break;
            case "ROUNDRECTANGLE":
                undo.push(buff_img);
                replay.push(buff_img);
                roundRect.setPoint(startPoint, startPoint);
                roundRect.setDoFill(isFill.isSelected());
                roundRect.setStrokeColor(colorChooser.getStrokeColor());
                roundRect.setFillColor(colorChooser.getFillColor());
                roundRect.setStroke(stroke.getStroke());
                break;
            case "TRIANGLE":
                undo.push(buff_img);
                replay.push(buff_img);
                triangle.setPoint(startPoint, startPoint);
                triangle.setDoFill(isFill.isSelected());
                triangle.setStrokeColor(colorChooser.getStrokeColor());
                triangle.setFillColor(colorChooser.getFillColor());
                triangle.setStroke(stroke.getStroke());
                break;
            case "RIGHTTRIANGLE":
                undo.push(buff_img);
                replay.push(buff_img);
                rightTriangle.setPoint(startPoint, startPoint);
                rightTriangle.setDoFill(isFill.isSelected());
                rightTriangle.setStrokeColor(colorChooser.getStrokeColor());
                rightTriangle.setFillColor(colorChooser.getFillColor());
                rightTriangle.setStroke(stroke.getStroke());
                break;
            case "POLYGON":
                undo.push(buff_img);
                replay.push(buff_img);
                if (polygon.getStartPoint() == null) {//chua su dung polygon
                    polygon.setStartPoint(startPoint);
                }
                line.setStrokeColor(colorChooser.getStrokeColor()); 
                line.setStroke(stroke.getStroke());
                if (endPoint != null) {
                    //da ve 1 hoac nhieu duong thang roi => ta se ve duong thang tu diem cuoi duong thang truoc toi diem vua nhan
                    Point temp = new Point(startPoint);//endPoint la diem cuoi duong thang trc => chuyen sang startPoint
                    startPoint = endPoint;
                    endPoint = temp;//diem vua nhan thanh diem cuoi cua duong thang vua ve
                    line.setPoint(startPoint, endPoint);
                } else {
                    line.setPoint(startPoint, startPoint);//neu chua ve duong thang nao ta ve 1 diem
                }
                break;
            case "BUCKET":
                undo.push(buff_img);
                replay.push(buff_img);
                bucket.addArrPoint(startPoint);
                bucket.setColor(colorChooser.getStrokeColor());
                bucket.setPoint(startPoint, startPoint);
                bucket.draw(buff_img);
                break;
            case "CURVE":
                if (curve == null) {
                    undo.push(buff_img);
                    replay.push(buff_img);
                    curve = new Curve();
                    curve.setStrokeColor(colorChooser.getStrokeColor());
                    curve.setStartPoint(startPoint);
                    curve.setStroke(stroke.getStroke());
                }
                if (curve.getState() == 1) //vua khoi tao bang cach nhan vao mot diem tren panel
                {
                    curve.setEndPoint(startPoint);
                } else if (curve.getState() == 2) {
                    //co 1 control point
                    //set control point vi tri 2 => control point 1 trung controlpoint 2
                    curve.getCurveLine().get(1).setLocation(curve.getEndPoint());
                } else if (curve.getState() == 3) {
                    //co 2 control point
                    //set control point thu 2, control point 1 giu nguyen
                    curve.getCurveLine().get(1).setLocation(curve.getEndPoint());
                }
                break;
            case "TEXT":
                if (text == null) {
                    text = new Text();
                    text.setStart(startPoint);
                    text.setFinish(startPoint);
                    text.setIsCreated(false);
                    return;
                } else {
                    undo.push(buff_img);
                    replay.push(buff_img);
                    text.setIsCreated(true);
                    if (text.getStart() != null && text.getFinish() != null) {
                        if (text.checkOverLap() == false) {
                            text.setString();
                            if (text.getString().equals("") == false) {
                                repaint();
                            }
                        }
                        text.removeArea(this);
                    }
                    return;
                }
            case "SELECT":
                if (select != null) {
                    if (select.isIsCreating()) {
                        if (!testSel(startPoint)) { // Neu click chuot khong trong khoang Select thi coi nhu hoan thanh
                            undo.push(buff_img);
                            replay.push(buff_img);
                            select.setIsSelected(true);
                            select.draw(g2d);
                            repaint();
                            startPoint = null;
                            endPoint = null;
                            select = null;
                            startSelect = false;
                        } else {
                            tempx = select.getStartPoint().x - startPoint.x;
                            tempy = select.getStartPoint().y - startPoint.y;
                            select.addArrPoint(new Point(startPoint.x - tempx, startPoint.y - tempy));
                            return;
                        }
                    }
                } else if (select == null) {
                    select = new Selection();
                    startSelect = true; //Da duoc khoi tao
                    startPoint = e.getPoint();
                    select.setStartOrigin(startPoint);
                    select.setStartPoint(startPoint);
                    select.setEndPoint(startPoint);
                }
                break;
            case "COLORPICKER":
                colorChooser.colorButton(new Color(buff_img.getRGB(startPoint.x, startPoint.y)));
                break;
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {//goi khi vua tha chuot

        switch (mode) {
            case "LINE":
                line.draw(g2d);
                break;
            case "OVAL":
                oval.draw(g2d);
                break;
            case "RECTANGLE":
                rect.draw(g2d);
                break;
            case "ROUNDRECTANGLE":
                roundRect.draw(g2d);
                break;
            case "TRIANGLE":
                triangle.draw(g2d);
                break;
            case "RIGHTTRIANGLE":
                rightTriangle.draw(g2d);
                break;
            case "POLYGON":
                endPoint = e.getPoint();
                line.draw(g2d);
                return;
            case "CURVE":
                if (curve != null) {
                    endPoint = e.getPoint();
                    if (curve.getState() == 1) {
                        curve.updateState();
                        return;//tranh startpoint va endpoint ve null
                    } else if (curve.getState() == 2) {
                        curve.updateState();
                        return;
                    } else if (curve.getState() == 3) {
                        curve.draw(g2d);//ve lai lan cuoi
                        curve = null;
                    }
                }
                break;
            case "TEXT":
                if (text != null) {
                    text.setFont(textPanel.getFont());
                    text.setTextColor(colorChooser.getStrokeColor());
                    text.setFillColor(colorChooser.getFillColor());
                    text.setIsOpaque(textPanel.getIsOpaque());
                    text.setArea(this);
                    text.setFontArea();
                    text.getArea().setForeground(colorChooser.getStrokeColor());
                    text.getArea().setOpaque(textPanel.getIsOpaque());
                    repaint();
                    if (text.getIsCreated()) {
                        text.removeArea(this);
                        text = null;
                    }
                }
                return;
            case "SELECT":
                if (select != null) {
                    if (!select.isIsCreating()) {
                        if (select.isIsDragging()) {
                            select.setEndOrigin(endPoint);
                            select.setIsCreating(true); //Danh dau anh da duoc khoi tao
                            Point selP1 = new Point(Math.min(select.getStartOrigin().x, select.getEndOrigin().x) ,
                            Math.min(select.getStartOrigin().y, select.getEndOrigin().y));
                            
                            Point selP2 = new Point(Math.max(select.getStartOrigin().x, select.getEndOrigin().x) ,
                            Math.max(select.getStartOrigin().y, select.getEndOrigin().y));
                            
                            if (selP1.x < 0) selP1.x = 0;
                            if (selP1.y < 0) selP1.y = 0;
                            if (selP2.x > width) selP2.x = width;
                            if (selP2.y > height) selP2.y = height;
                            select.setIMG(buff_img.getSubimage(selP1.x, selP1.y , Math.abs(selP2.x - selP1.x), Math.abs(selP2.y - selP1.y)));
                            select.setIsDragging(false); //Drag xong
                        }
                    }
                }
                return;
        }
        startPoint = null;
        endPoint = null;
        isSaved = false;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {

    }

    @Override
    public void mouseExited(MouseEvent e
    ) {
        jCoordinate.setText("");
    }

    @Override
    public void mouseDragged(MouseEvent e
    ) {
        jCoordinate.setText(e.getX() + ", " + e.getY() + " px");
        endPoint = e.getPoint();
        switch (mode) {
            case "SELECT":
                if (select != null) {
                    if (select.isIsCreating()) {
                        select.setStartPoint(new Point(endPoint.x + tempx, endPoint.y + tempy));
                        select.addArrPoint(new Point(endPoint.x + tempx, endPoint.y + tempy));
                    } else {
                        Point startSEL = new Point(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y));//trai tren
                        Point endSEL = new Point(Math.max(startPoint.x, endPoint.x), Math.max(startPoint.y, endPoint.y));//phai duoi
                        if (startSEL.x < 0) {
                            startSEL.x = 0;
                        }
                        if (startSEL.y < 0) {
                            startSEL.y = 0;
                        }
                        if (endSEL.x > width) {
                            endSEL.x = width;
                        }
                        if (endSEL.y > height) {
                            endSEL.y = height;
                        }
                        //Neu anh chua tao thi tao diem bat dau va ket thuc roi danh dau la dang keo -> released
                        select.setPoint(startSEL, endSEL);
                        select.setIsDragging(true);
                    }
                }

                break;
            case "PENCIL":
                pencil.setPoint(startPoint, endPoint);
                startPoint = endPoint;
                pencil.draw(g2d);
                break;
            case "COLORPICKER":
                break;
            case "ERASER":
                eraser.setPoint(startPoint, endPoint);
                startPoint = endPoint;
                eraser.draw(g2d);
                break;
            case "BUCKET":
                break;
            case "MAGNIFIER":
                break;
            case "POLYGON":
                line.setPoint(startPoint, endPoint);
                repaint();
                break;
            case "LINE":
                line.setPoint(startPoint, endPoint);
                break;
            case "OVAL":
                oval.setPoint(startPoint, endPoint);
                break;
            case "RECTANGLE":
                rect.setPoint(startPoint, endPoint);
                break;
            case "ROUNDRECTANGLE":
                roundRect.setPoint(startPoint, endPoint);
                break;
            case "TRIANGLE":
                triangle.setPoint(startPoint, endPoint);
                break;
            case "RIGHTTRIANGLE":
                rightTriangle.setPoint(startPoint, endPoint);
                break;
            case "CURVE":
                if (curve != null) {
                    if (curve.getState() == 1) {
                        curve.setEndPoint(endPoint);
                    } else if (curve.getState() == 2 || curve.getState() == 3) //luon set control point cho curvelist.get(2)
                    {
                        curve.getCurveLine().get(1).setLocation(endPoint);
                    }
                }
                break;
            case "TEXT":
                if (text != null) {
                    if (text.getFinish() != e.getPoint() && text.getIsCreated() == false) {
                        text.setFinish(endPoint);
                        repaint();
                    } else {
                        text.setString();
                        text.removeArea(this);
                        text = null;
                        repaint();
                        mousePressed(e);
                    }
                }
                return;
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e
    ) {
        if (mode == "MAGNIFIER") {
            startPoint = e.getPoint();
            endPoint = startPoint;
            repaint();
        }
        jCoordinate.setText(e.getX() + ", " + e.getY() + " px");

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
