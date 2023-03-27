package tangram;

import java.awt.Shape;

import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;


public class Tangram {
    
    public static SelectLevel selectLevel;
    public static int levels;
    private static String[] args;
    public static void main(String[] args) {
      Tangram.args = args;
      levels = 20;
      selectLevel = new SelectLevel();
      selectLevel.setVisible(true);
    }
    
   
   
    public static Contour loading(String filename) {
        if(args.length > 0)
            filename = args[0];
        Shape contour = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try{
            fis = new FileInputStream(filename);
            in = new ObjectInputStream(fis);
            contour = (Shape)in.readObject();
            in.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return new Contour(contour);
    }
}
