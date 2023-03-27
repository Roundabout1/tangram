package tangram;

import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
public class SelectLevel extends JFrame{
    private int HEIGHT = 320, WIDTH = 500;
    JButton back;
    ArrayList<JButton> buttonLevels;
    SelectLevel(){
        setTitle("Tangram");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        
        Listener listener = new Listener();
        setLayout(new FlowLayout());
        back = new JButton("Выход из игры");
        buttonLevels = new ArrayList();
        for(int i = 0; i < Tangram.levels; i++){
            String name = "Уровень № " + Integer.toString(i+1);
            buttonLevels.add(new JButton(name));
            add(buttonLevels.get(i));
            (buttonLevels.get(i)).addActionListener(listener);
        }
        add(back);
        back.addActionListener(listener);
    }
    
    public class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
             if(e.getSource() == back){
                System.exit(0);
            }
            
            for(int i = 0; i < Tangram.levels; i++){
                if(e.getSource() == buttonLevels.get(i)){
                    setVisible(false);
                    String name = buttonLevels.get(i).getName();
                    String fileName = "Level"+Integer.toString(i)+".ser";
                    LevelWindow lw = new LevelWindow(name, fileName);
                    lw.setVisible(true);
                }
            }
        }
               
    }
    
}
