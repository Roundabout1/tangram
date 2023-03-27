package tangram;

import java.awt.event.*;
import javax.swing.*;
public class LevelWindow extends JFrame{
    
    public final int WIDTH = 900, HEIGHT = 900; //ширина и высота окна
    private JButton back; //кнопка возвращения в главное меню
    final String NAME; //название уровня 
    final String FILENAME; // название файла из которого загружается задача
    private ListenerButton listenerButton = new ListenerButton(); //слушатель для кнопки
    private LevelBoard board; //игровое поле
    LevelWindow(String name, String fileName){
        this.NAME = name;
        this.FILENAME = fileName;
        //создание и настройка основного фрейма
        setTitle(NAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        
        //добавляю кнопку возвращения в главное меню
        back = new JButton("Главное меню");
        back.setBounds(0, HEIGHT-60, WIDTH, 30);
        back.addActionListener(listenerButton);
        add(back);
        
        //добавление игрового поля
        board = new LevelBoard(this);
        add(board);
      
    }

    @Override
    public String toString() {
        return NAME;
    }
    
    @Override
    public String getName() {
        return NAME;
    }

    public LevelBoard getBoard() {
        return board;
    }
    
  
    
    public class ListenerButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == back){
                dispose();
                setVisible(false);
                Tangram.selectLevel.setVisible(true);
            }
        }
               
    }
}
