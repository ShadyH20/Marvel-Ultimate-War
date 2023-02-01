package view;

import engine.Player;
import model.world.Champion;
import model.world.Hero;
import view.game_scene.GameScene;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;
import java.io.*;

public class WinScreen extends JPanel {

    JLabel title;
    JPanel champs;
    JButton exitBtn;

    public WinScreen(){
        setLayout(null);

        title = new JLabel("Shady has won!",SwingConstants.CENTER);
//        title.setHorizontalTextPosition(SwingConstants.CENTER);
//        title.setVerticalTextPosition(SwingConstants.CENTER);
        title.setFont(GameView.gameFontBold.deriveFont(80f));
        title.setForeground(Color.orange);
//        title.setBorder(new LineBorder(Color.red));
        title.setBounds(GameView.W/2- 800/2, 200,800,100);

        champs = new JPanel();
        champs.setOpaque(false);
        champs.setBounds(0, 400,GameView.W,500);
//        champs.setBorder(new LineBorder(Color.blue));

        add(title);
        add(champs);

    }

    public void setUpWinScreen(Player p){
        title.setText(p.getName() + " has won!");

        for(Champion c : p.getTeam()){
            JLabel lbl = new JLabel();
            ImageIcon icon = new ImageIcon("./pics/3d/"+c.getName()+".png");

            lbl.setIcon(icon);

            champs.add(lbl);
        }

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        GameView view = new GameView();
        WinScreen scene = new WinScreen();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(0,0,1536,960);
        frame.add(scene);
        frame.setVisible(true);

        //
        Player p = new Player("Shodzz");
        p.getTeam().add(new Hero("Captain America",8,8,8,8,8,8));
        p.getTeam().add(new Hero("Ironman",8,8,8,8,8,8));
        p.getTeam().add(new Hero("Thor",8,8,8,8,8,8));

        scene.setUpWinScreen(p);
    }

    ImageIcon bgImage = new ImageIcon((new ImageIcon("./pics/Background5.jpg")).getImage().getScaledInstance(GameView.W,GameView.H,Image.SCALE_SMOOTH));
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage.getImage(), 0, 0, null);
    }

}
