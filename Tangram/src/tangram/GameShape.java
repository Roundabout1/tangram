package tangram;

import java.awt.*;
import java.awt.geom.*;

public class GameShape {
    private Area area;
    private Color color;
    private boolean move = false;
    private boolean insideAreaStatus = false;
    GameShape(){
       
        this.color = new Color(0, 0, 0, 0);
        this.area = new Area();
       
    }
    GameShape(Color color){
       
        this.color = color;
        this.area = new Area();
       
    }
    GameShape(Area area, Color color){
       
        this.color = color;
        this.area = area;
        
    }
    GameShape(Polygon polygon, Color color){
        
        this.color = color;
        this.area = new Area(polygon);
        
    }
    
    
   
    //вне контура объект даёт яркий цвет, если же он полностью находится внутри контура, то эта фигура становится менее яркой
    public Color getCurrentColor() {
        if(!this.insideAreaStatus)
            return color;
        else
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), 150);
    }

    public void setInsideAreaStatus(boolean insideAreaStatus) {
        this.insideAreaStatus = insideAreaStatus;
    }

    public boolean isInsideAreaStatus() {
        return insideAreaStatus;
    }
    
    
    public double getCenterX(){
        return area.getBounds2D().getCenterX();
    }
    public double getCenterY(){
        return area.getBounds2D().getCenterY();
    }
    
    public boolean isMove() {
        return move;
    }

    public Area getArea() {
        return area;
    }
    

    public void setColor(Color color) {
        this.color = color;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public void setArea(Area area) {
        this.area = area;
    }
    
    
    public void changeMoveStatus(){
        move = !move;
        if(move)
            color = color.darker();
        else
            color = color.brighter();
    }
    
    //makeRectangle, makeParallelogram, makeTriangle - создают соответствующую фигуру, где xLeft, yLeft - это координаты крайней верхней левой точки этих фигур
    
    public void makeRectangle(int xLeft, int yLeft, int length){
        
        int xCoordinates[] = {xLeft, xLeft+length, xLeft+length, xLeft};
        int yCoordinates[] = {yLeft,  yLeft, yLeft+length, yLeft+length};
        setArea(new Area(new Polygon(xCoordinates, yCoordinates, 4)));
        //setPolygon(new Polygon(xCoordinates, yCoordinates, 4));
    }
    
    public void makeParallelogram(int xLeft, int yLeft, int length1, int length2){
       int shift = -length1/3;
       int xCoordinates[] = {xLeft, xLeft+shift, xLeft+shift+length1, xLeft+length1};
       int yCoordinates[] = {yLeft, yLeft+length2, yLeft+length2, yLeft};
       setArea(new Area(new Polygon(xCoordinates, yCoordinates, 4)));
    }
    
    public void makeTriangle(int xLeft, int yLeft, int length){
        int xCoordinates[] = {xLeft, xLeft+length, xLeft};
        int yCoordinates[] = {yLeft, yLeft, yLeft+length};
        setArea(new Area(new Polygon(xCoordinates, yCoordinates, 3)));
    }
    
    public boolean isInisde(int x, int y){
        return area.contains(x, y);
    }
    
    public void move(double mouseX, double mouseY){
        AffineTransform aftr = new AffineTransform();
        aftr.translate(mouseX-getCenterX(), mouseY-getCenterY());
        this.area.transform(aftr);
    }
    
    public void rotate(double mouseX, double mouseY, double deg){
        deg = Math.toRadians(deg);
        AffineTransform aftr = new AffineTransform();
        aftr.rotate(deg);
        area.transform(aftr);
        move(mouseX, mouseY);
    }
    
    public boolean isInsideArea(Area area){
        Area areaA = new Area(this.getArea());
        areaA.subtract(area);
        return areaA.isEmpty();
    }
    
    public boolean intersectsWith(GameShape other){
        Area areaA = new Area(this.getArea());
        areaA.intersect(new Area(other.getArea()));
        return !areaA.isEmpty();
    }
    
    public void addShape(GameShape other){
        this.getArea().add(other.getArea());
        
    }
}
