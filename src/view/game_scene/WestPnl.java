package view.game_scene;

import engine.Game;
import model.world.Champion;
import view.GameView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class WestPnl extends JPanel implements ActionListener {
    private JPanel container;
    private JButton turnOrderBtn;
    private JPanel champList;

    private Dimension btnDim;

    public WestPnl(){
        setLayout(new BorderLayout());

        setOpaque(false);
        setPreferredSize(new Dimension((int) (0.08 * GameView.W),0));
        setBorder(BorderFactory.createEmptyBorder(10,20,10,0));
//        BorderFactory.createMatteBorder(0,20,0,0,new Color(0x14131c00,true));
//        setBackground(new Color(0x14131c));

        JPanel borderLytPnl = new JPanel(new BorderLayout());
        borderLytPnl.setBorder(new LineBorder(Color.lightGray,3));
        borderLytPnl.setBackground(new Color(0x14,0x13,0x1c,150));

//        borderLytPnl.setBackground(new Color(0x453e7a));


        btnDim = new Dimension((int) (0.52 * getPreferredSize().width),(int) (0.52 * getPreferredSize().width));

        //Button to display turn order
//        turnOrderBtn = new JButton("<html> <h3 style='text-align: center'>TURN<br/>ORDER");
        turnOrderBtn = new JButton(GameView.convertToMultiline("<center>Turn\nOrder"));
        turnOrderBtn.setFont(GameView.gameFont2.deriveFont(20f));
        turnOrderBtn.setForeground(Color.lightGray);
        turnOrderBtn.setContentAreaFilled(false);
        turnOrderBtn.setPreferredSize(btnDim);
        turnOrderBtn.setMaximumSize(btnDim);
        turnOrderBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        turnOrderBtn.setHorizontalTextPosition(SwingConstants.LEFT);
        turnOrderBtn.setVerticalTextPosition(SwingConstants.CENTER);
        turnOrderBtn.setBorder(BorderFactory.createLineBorder(Color.lightGray,2,true));
        turnOrderBtn.addActionListener(this);
        turnOrderBtn.setFocusable(false);

        container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));

        //Panel containing the champions
        champList = new JPanel();
        champList.setLayout(new BoxLayout(champList,BoxLayout.Y_AXIS));
        champList.setOpaque(false);
        champList.setVisible(false);

        //Container to carry button and list of champs
        container.add(champList);
        container.add(turnOrderBtn);
        container.add(Box.createRigidArea(new Dimension(0,30)));

        borderLytPnl.add(container,BorderLayout.SOUTH);

//        add(Box.createRigidArea(new Dimension(20,0)),BorderLayout.WEST);
        add(borderLytPnl,BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        champList.setVisible(!champList.isVisible());
    }

    public void setTurnOrder(ArrayList<Champion> list, Game game){
        champList.removeAll();
        for(Champion c : list){
            JButton btn = createChampBtn(c,GameScene.getBorderColor(game,c.getLocation().x,c.getLocation().y));
            champList.add(btn,0);
            champList.add(Box.createRigidArea(new Dimension(0,1)),1);
        }
        revalidate();
        repaint();
    }

    private JButton createChampBtn(Champion c, Color color) {
        JButton btn = new JButton();
        String name = c.getName();
        btn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        btn.setPreferredSize(btnDim);
        btn.setMaximumSize(btnDim);
        ImageIcon icon = new ImageIcon("./pics/icons/"+name+".jpg");
        double ratio = 1.0*icon.getIconWidth()/icon.getIconHeight();
        btn.setIcon(new ImageIcon(icon.getImage().getScaledInstance(btnDim.width, btnDim.height, Image.SCALE_SMOOTH)));
//        btn.setIcon(new ImageIcon("./pics/icons/"+name+".jpg"));
        btn.setBorder(new LineBorder(color,3));

        return btn;
    }
}
