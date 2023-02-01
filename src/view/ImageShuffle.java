package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class ImageShuffle extends JPanel {

    private ArrayList<Icon> list = new ArrayList<Icon>();
    private JLabel label = new JLabel();
    
    private Timer timer = new Timer(2000, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            update();
        }
    });

    public ImageShuffle() {
        
    	this.setLayout(new GridLayout(1, 0));
        
       
    }

    private void update() {
        Collections.shuffle(list);
        label.setIcon(list.get(0));
    }
    private void display() {
        JFrame f = new JFrame("ImageShuffle");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ImageShuffle().display();
            }
        });
    }
    public void addIcon(ImageIcon i) {
    	 list.add(i);
    	 label.setIcon(list.get(0));
         add(label);
         timer.start();
    }
}
