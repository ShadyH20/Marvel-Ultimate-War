package quiz3;

import engine.Game;
import engine.Player;
import model.abilities.Ability;
import model.world.Champion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;

public class MyFrame extends JFrame {

    Game game;
    ArrayList<Champion> champions;
    ArrayList<Ability> abilities;
    JButton btn1;
    JButton btn2;
    JButton btn3;
    JButton btn4;

    public MyFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(300,300,600,300);
        setTitle("Quiz");


        //Initialize
        final int[] counter = {0};
        JButton btn1 = new JButton("Component 1");
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (counter[0]){
                    case 0 -> {
                        Ability a = abilities.get(0);
                        btn2.setText(a.getName());
                        counter[0]++;
                    }
                    case 1 -> {
                        Ability a = abilities.get(1);
                        btn3.setText(a.getName());
                        counter[0]++;
                    }
                    case 2 ->{
                        Ability a = abilities.get(2);
                        btn4.setText(a.getName());
                        counter[0]++;
                    }
                    case 3 -> {
                        counter[0] = 0;
                        int champIndex = (int) (Math.random() * champions.size());
                        Champion champ = champions.get(champIndex);
                        abilities = champ.getAbilities();
                        btn1.setText(champ.getName());
                        btn2.setText("");
                        btn3.setText("");
                        btn4.setText("");
                    }
                }
            }
        });


        btn1.setPreferredSize(new Dimension(0,60));
        btn2 = new JButton();
        btn2.setPreferredSize(new Dimension(200,0));
        btn3 = new JButton();
        btn3.setPreferredSize(new Dimension(200,0));
        btn4 = new JButton();
        btn4.setPreferredSize(new Dimension(200,0));

        add(btn1, BorderLayout.NORTH);
        add(btn2, BorderLayout.WEST);
        add(btn3, BorderLayout.CENTER);
        add(btn4, BorderLayout.EAST);

        game = new Game(new Player("Player 1"),new Player("Player 2"));
        try {
            Game.loadAbilities("Abilities.csv");
            Game.loadChampions("Champions.csv");
        } catch (IOException ignored) {
        }

        champions = Game.getAvailableChampions();

        int champIndex = (int) (Math.random() * champions.size());
        Champion champ = champions.get(champIndex);
        abilities = champ.getAbilities();


        btn1.setText(champ.getName());

        setVisible(true);
    }

    public static void main(String[] args) {
        new MyFrame();
    }
}
