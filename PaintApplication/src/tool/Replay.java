/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
/**
 *
 * @author Dang Hung
 */
public class Replay extends JPanel implements Runnable {
    private BufferedImage buff_img; //buf de cap nhat cac image len panel
    private int delay = 20; //set speed for video
    private boolean isPlaying; //kiem tra resume or stop
    private Thread thread = null;
    private Graphics2D g2d, g2;
    private Stack stack; //stack undo : chieu lai undo 
    private final int width = 800;
    private final int height = 500;
    private int currentImage = 0; //anh hien tai duoc ve len panel tu stack 
    private Image null_img;
    
    public Replay(Stack stack) {
        this.stack = stack;
        isPlaying = false;
        initComponents();
        this.setSize(width, height);
        buff_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) buff_img.createGraphics();
        g2.drawImage(null_img, 0, 0, null);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(0, 0, buff_img.getWidth(), buff_img.getHeight());
        g2.dispose();
    }

    public int getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(int currentImage) {
        this.currentImage = currentImage;
    }
    
    public int getTop() {
        return stack.getTop();
    }
    
    public Stack getStack(){
        return stack;
    }
    
    public void setDelay(int delay) {
        this.delay = (100 - delay)/ 2;
    }
    
    //method is called when use replay button
    public void startReplay() {
        if (thread == null) {
            thread = new Thread(this);
            isPlaying = true;
            thread.start();//start with this.run
        }
        isPlaying = true;
    }
    public void pauseReplay(){
        isPlaying = false;
    }
    
    public boolean isPlaying() {
        return isPlaying;
    }

    public void setImage(BufferedImage img) {
        buff_img = img;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        g2 = (Graphics2D) g;
        g2.drawImage(buff_img, null, 0, 0);
    }
    @Override
    public void run() {
        while (currentImage <= stack.getTop()){//khi chua chay het stack
            if (isPlaying == false) {//isplaying == false: stop
                break;
            }
            setImage(stack.getImageIndex(currentImage)); //buf_img = currentImage
            try {
                Thread.sleep(delay*20);//delay : toc do chay video
            } catch (InterruptedException ex) {
                Logger.getLogger(Replay.class.getName()).log(Level.SEVERE, null, ex);
            }
            repaint();//ve lai
            currentImage ++;
        }
        System.gc();
        isPlaying = false;
        if (currentImage > stack.getTop()) currentImage = 0;
        thread = null;
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
            .addGap(0, 708, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 525, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
