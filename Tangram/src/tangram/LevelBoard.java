package tangram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Random;
import java.util.*;
public class LevelBoard extends JPanel{
    private final Mouse MOUSE;
    private final LevelWindow WINDOW;
    private Contour contour;
    private ArrayList<GameShape> shapes;
    private boolean victory = false;
    //индекс последней фигуры в shapes
    private int last;
    //эти переменные определяют границы вдоль которых игрок может перемещать фигуры
    final int XMIN, YMIN, XMAX, YMAX, SHIFT;
    //единица измерения, эта константа нужна для того, чтобы синхронизировать пропорции фигур
    final int UNIT;
    LevelBoard(LevelWindow window){
        this.WINDOW = window;
        //загрузка контура
        this.contour = Tangram.loading(this.WINDOW.FILENAME);
        //переносим контур в центр игрового поля
        AffineTransform t = new AffineTransform();
        t.translate(this.WINDOW.WIDTH/2 - this.contour.getBounds2D().getCenterX(), this.WINDOW.HEIGHT/2 - this.contour.getBounds2D().getCenterY());
        this.contour.transform(t);
        
        
        
        UNIT = 150;
        SHIFT = 100;
        XMIN = YMIN = SHIFT;
        XMAX = window.getWidth() - SHIFT;
        YMAX = window.getHeight() - SHIFT;
        
        this.setBackground(Color.WHITE);
        
        MOUSE = new Mouse();
        this.addMouseMotionListener(MOUSE);
        this.addMouseWheelListener(MOUSE);
        this.addMouseListener(MOUSE);
        
        addGameShapes(700, 125);
        
    }

    public ArrayList<GameShape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<GameShape> shapes) {
        this.shapes = shapes;
    }

    public void setContour(Contour contour) {
        this.contour = contour;
    }

    public Area getContour() {
        return contour;
    }
    
    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); //сглаживание
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if(!victory){
            for (GameShape shape : shapes) {
                g2d.setColor(shape.getCurrentColor());
                 
                g2d.fill(shape.getArea());
                
            }
            if(!this.contour.isEmpty()){
                g2d.setColor(new Color(0, 0, 0, 127));
                g2d.fill(contour);
              
            }
        }else{
            String str = "Победа!";
            Font f = new Font("Arial", Font.BOLD, 20);
            g.setColor(Color.YELLOW);
            g.setFont(f);
            g.drawString(str, this.WINDOW.getWidth()/2 - 30, SHIFT);  
            g2d.setColor(Color.BLUE);
            g2d.fill(contour);
        }
    }
    
    //x и y - это координаты точки с которой начнутся добавляться новые фигуры на игровое поле
    private void addGameShapes(int x, int y){
        shapes = new ArrayList<GameShape>();
        int len;
        //Фигуры танграма это: 5 прямоугольных треугольников (2 маленьких, 1 средний и 2 больших), квадрат и параллелограм
        
        //2 больших треугольника
        len = (int)Math.round(UNIT/Math.sqrt(2.0));
        shapes.add(new GameShape(Color.MAGENTA));
        shapes.get(0).makeTriangle(x, y, len);
        y = yDown(y, 0);
        shapes.add(new GameShape(Color.BLUE));
        shapes.get(1).makeTriangle(x, y, len);
        y = yDown(y, 1);
        
        //2 маленьких треугольника
        len =(int)Math.round(UNIT*(Math.sqrt(2.0)/4.0));
        shapes.add(new GameShape(Color.PINK));
        shapes.get(2).makeTriangle(x, y, len);
        y = yDown(y, 2);
        shapes.add(new GameShape(Color.CYAN));
        shapes.get(3).makeTriangle(x, y, len);
        y = yDown(y, 3);
        
        //средний треугольник
        len =(int)Math.round(UNIT/2.0);
        shapes.add(new GameShape(Color.GREEN));
        shapes.get(4).makeTriangle(x, y, len);
        y = yDown(y, 4);
        
        //квадрат
        len = (int)(Math.round(UNIT*Math.sqrt(2)/4.0));
        shapes.add(new GameShape(Color.RED));
        shapes.get(5).makeRectangle(x, y, len);
        y = yDown(y, 5);
        
        //параллелограм
        len = (int)Math.round(UNIT/2.0);
        int len2 = (int)(Math.round(UNIT*Math.sqrt(2.0)/4.0));
        shapes.add(new GameShape(Color.ORANGE));
        shapes.get(6).makeParallelogram(x, y, len, len2);
        
        last = shapes.size()-1;
    }
    //y - текущая высота, i - индекс фигуры в shapes
    private int yDown(int y, int i){
        return y + (int)shapes.get(i).getArea().getBounds2D().getHeight() + 30;
    }
    private Color getRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
    }
    public boolean isInside(int x, int y){
        return (x >= XMIN && x <= XMAX && y >= YMIN && y <= YMAX);
    }
    
    //проверяет пересекается ли фигура с другими, i - индекс фигуры в shapes
    public boolean hasIntersects(int index){
        for(int i = 0; i < last; i++){
            if(i != index && shapes.get(i).intersectsWith(shapes.get(index))){
                return true;
            }
        }
        return false;
    }
    
    public boolean checkVictory(){
      
        for(GameShape shape : shapes){
            if(!shape.isInsideAreaStatus())
                return false;
        }
        return true;
    }
    
    class Mouse extends MouseAdapter implements MouseListener{
        //равняется true, если существует фигура, которую пользователь в данный момент перемещает
        boolean hasMove = false;
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e); 
            if(e.getButton() == MouseEvent.BUTTON1){
                if(!hasMove){
                    for(int i = 0; i < shapes.size(); i++){
                        if(shapes.get(i).isInisde(e.getX(), e.getY())){
                            shapes.get(i).changeMoveStatus();
                            /*я меняю последний элемент массива с i-ым элементом. 
                            Это нужно для того, чтобы при отрисовке фигур на поле, данная прорисовыволась в последнюю очередь, в таком случае движущая фигура не будет перекрываться другими фигурами*/
                            GameShape temp = shapes.get(i);
                            shapes.set(i, shapes.get(last));
                            shapes.set(last, temp);
                            hasMove = true;
                         
                            break;
                        }
                    }
                }else{
                    if(!hasIntersects(last)){
                        shapes.get(last).changeMoveStatus();
                        shapes.get(last).setInsideAreaStatus(shapes.get(last).isInsideArea(contour));
                        hasMove = false;           
                        victory = checkVictory();
                    }
                   
                }
                WINDOW.repaint();
            }
            
            /*if(e.getButton() == MouseEvent.BUTTON3){
                checkAll();
               
            }*/
        
        }
        
        @Override
        public void mouseMoved(MouseEvent e){
            
            int mouseX = e.getX(), mouseY = e.getY();
            if(hasMove && isInside(mouseX, mouseY)){
                
                shapes.get(last).move(e.getX(), e.getY());
            }
           
            
            WINDOW.repaint();
        }
       
       @Override
       public void mouseWheelMoved(MouseWheelEvent e){
           int wheel = e.getWheelRotation();

           if(hasMove && wheel != 0){
               if(wheel > 0) 
                    wheel = 1;
               else
                   wheel = -1;
              
               shapes.get(last).rotate(e.getX(), e.getY(), wheel*5);
               WINDOW.repaint();
           }
       }
    }
}
