package tangram;
import java.awt.TextComponent;
import javax.swing.*;
import java.awt.event.*;

public class Help extends JFrame{
    private final int HEIGHT = 500, WIDTH = 500;
    JLabel label;
    JButton back;
    TextComponent text;
    Help(){
        setTitle("Tangram. Help");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(HEIGHT, WIDTH);
        setLocationRelativeTo(null);
        setResizable(false);
        
    }
    
}
