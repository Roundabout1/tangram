package tangram;
import javax.swing.*;
import java.awt.event.*;

public class Windows extends JFrame{
    private final int HEIGHT = 500;
    private final int WIDTH = 500;
   
    Windows(){
        setTitle("Tangram");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(HEIGHT, WIDTH);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    
}
