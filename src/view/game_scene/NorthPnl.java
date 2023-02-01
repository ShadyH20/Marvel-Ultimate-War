package view.game_scene;

import engine.Game;
import model.effects.Effect;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Hero;
import view.GameView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;
import java.io.*;

public class NorthPnl extends JPanel {

    Dimension btnDim;

    JPanel p1Pnl;
    JPanel p2Pnl;
    JLabel p1Name;
    JLabel p2Name;
    JButton p1LA;
    JButton p2LA;
    JPanel p1BtnPnl;
    JPanel p2BtnPnl;
    ImageIcon dataIcon;

    ArrayList<JButton> p1Champs = new ArrayList<>();
    ArrayList<JButton> p2Champs = new ArrayList<>();
    //Constructors

    public NorthPnl(){
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
//        setOpaque(false);
        setPreferredSize(new Dimension(0, (int) (0.14* GameView.H)));
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        setBackground(new Color(0x14,0x13,0x1c,150));

        bg  = new ImageIcon((new ImageIcon("./pics/North Pnl.png")).getImage().getScaledInstance(GameView.W,getPreferredSize().height,Image.SCALE_SMOOTH));

        btnDim = new Dimension((int)(0.45 * getPreferredSize().height),(int)(0.45 * getPreferredSize().height));
        dataIcon = new ImageIcon((new ImageIcon("./pics/data.png").getImage().getScaledInstance(300,getPreferredSize().height,Image.SCALE_SMOOTH)));

        //Player 1
        p1Pnl = new JPanel();
        p1Pnl.setOpaque(false);
        p1Pnl.setLayout(new BoxLayout(p1Pnl,BoxLayout.X_AXIS));
        p1Pnl.setAlignmentX(RIGHT_ALIGNMENT);
        p1Pnl.setPreferredSize(new Dimension((int) (GameView.W/2.2),getPreferredSize().height));

//        p1Pnl.setLayout(new FlowLayout(FlowLayout.TRAILING));
//        p1Pnl.setBorder(BorderFactory.createLineBorder(Color.red));

        p1Name = new JLabel("Shady");
//        p1Name.setFont(new Font("hooge 05_53",Font.BOLD,40));
        p1Name.setFont(GameView.gameFont.deriveFont(65f));

        p1Name.setForeground(Color.white);
        p1Name.setAlignmentY(CENTER_ALIGNMENT);
        p1Name.setVerticalTextPosition(SwingConstants.CENTER);
        p1Name.setHorizontalAlignment(SwingConstants.CENTER);
        p1Name.setVerticalAlignment(SwingConstants.CENTER);

        ImageIcon leaderIcon = new ImageIcon("./pics/leader.png");
        p1LA = new JButton(new ImageIcon(leaderIcon.getImage().getScaledInstance((int)(0.8*btnDim.width),(int)(0.8*btnDim.height),Image.SCALE_SMOOTH)));
        p1LA.setPreferredSize(new Dimension((int)(0.8*btnDim.width),(int)(0.8*btnDim.height)));
        p1LA.setFont(GameView.gameFont2.deriveFont(20f));
//        p1LA.setForeground(Color.yellow);
        p1LA.setBorderPainted(false);
        p1LA.setContentAreaFilled(false);
//        p1LA.setBackground(new Color(0x14,0x13,0x1c,100));
//        p1LA.setOpaque(false);

        p1BtnPnl = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        p1BtnPnl.setMaximumSize(new Dimension(2000, (int) (btnDim.height*1.2)));
        p1BtnPnl.setAlignmentY(CENTER_ALIGNMENT);
        p1BtnPnl.setOpaque(false);

        JPanel p1Main = new JPanel();
        p1Main.setLayout(new BoxLayout(p1Main,BoxLayout.X_AXIS));
        p1Main.setPreferredSize(new Dimension(getPreferredSize().width,getPreferredSize().height));
        p1Main.setOpaque(false);
        p1Main.setBorder(new LineBorder(Color.red));
        p1Main.add(new JLabel(dataIcon));
        p1Main.add(Box.createHorizontalGlue());
        p1Main.add(p1Pnl);

        p1Pnl.add(p1BtnPnl);
        p1Pnl.add(Box.createRigidArea(new Dimension(30,0)));
        p1Pnl.add(p1LA);
        p1Pnl.add(Box.createRigidArea(new Dimension(30,0)));
        p1Pnl.add(p1Name);
        p1Pnl.add(Box.createRigidArea(new Dimension(25,0)));


        //Player 2
        {
            p2Pnl = new JPanel();

            p2Pnl.setLayout(new BoxLayout(p2Pnl, BoxLayout.X_AXIS));
            p2Pnl.setOpaque(false);
            p2Pnl.setAlignmentX(LEFT_ALIGNMENT);
//            p2Pnl.setBorder(BorderFactory.createLineBorder(Color.blue));
            p2Pnl.setPreferredSize(new Dimension((int) (GameView.W/2.2),getPreferredSize().height));

            p2Name = new JLabel("Mazen");
            p2Name.setVisible(true);

//            p2Name.setFont(new Font("hooge 05_53", Font.BOLD, 40));
            p2Name.setFont(GameView.gameFont.deriveFont(65f));
//            p2Name.setBorder(new LineBorder(Color.orange));
            p2Name.setAlignmentX(LEFT_ALIGNMENT);
            p2Name.setForeground(Color.white);
            p2Name.setHorizontalAlignment(SwingConstants.CENTER);
            p2Name.setVerticalAlignment(SwingConstants.CENTER);

            p2LA = new JButton(new ImageIcon(leaderIcon.getImage().getScaledInstance((int)(0.8*btnDim.width),(int)(0.8*btnDim.height),Image.SCALE_SMOOTH)));
            p2LA.setFont(GameView.gameFont2.deriveFont(20f));
//            p2LA.setForeground(Color.yellow);
            p2LA.setPreferredSize(new Dimension((int)(0.8*btnDim.width),(int)(0.8*btnDim.height)));
            p2LA.setBorderPainted(false);
            p2LA.setContentAreaFilled(false);
//            p2LA.setBackground(new Color(0x14,0x13,0x1c,100));
            p2LA.setFocusable(false);

            p2BtnPnl = new JPanel(new FlowLayout(FlowLayout.LEADING));
            p2BtnPnl.setAlignmentX(LEFT_ALIGNMENT);
            p2BtnPnl.setOpaque(false);
            p2BtnPnl.setMaximumSize(new Dimension(2000, (int) (btnDim.height*1.2)));

            p2Pnl.add(Box.createRigidArea(new Dimension(25,0)));
            p2Pnl.add(p2Name);
            p2Pnl.add(Box.createRigidArea(new Dimension(30,0)));
            p2Pnl.add(p2LA);
            p2Pnl.add(Box.createRigidArea(new Dimension(30,0)));
            p2Pnl.add(p2BtnPnl);
        }

        JLabel vs = new JLabel("Vs");
        vs.setForeground(Color.white);
        vs.setFont(GameView.gameFont2.deriveFont(80f));

        add(p1Pnl);
//        add(p1Main);
        add(Box.createHorizontalGlue());
//        add(Box.createRigidArea(new Dimension(50,0)));
        add(vs);
        add(Box.createHorizontalGlue());
        add(p2Pnl);

    }

    ImageIcon bg;
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawImage(bg.getImage(),0,0,null);
//    }

    public void setNorthChampBtns(Game game, ArrayList<Champion> t1, ArrayList<Champion> t2){
        for(Champion c : t1){
            JButton btn = new JButton();
            btn.setName(c.getName());
            btn.setPreferredSize(btnDim);
            btn.setIcon(new ImageIcon("./pics/icons/"+c.getName()+".jpg"));
            btn.setToolTipText(getOtherChampInfo(game,c));
            p1Champs.add(btn);
            p1BtnPnl.add(btn);
        }
        for(Champion c : t2){
            JButton btn = new JButton();
            btn.setName(c.getName());
            btn.setPreferredSize(btnDim);
            btn.setIcon(new ImageIcon("./pics/icons/"+c.getName()+".jpg"));
            btn.setToolTipText(getOtherChampInfo(game,c));
            p2Champs.add(btn);
            p2BtnPnl.add(btn);
        }
        //Names
        p1Name.setText(game.getFirstPlayer().getName());
        p2Name.setText(game.getSecondPlayer().getName());

        //Leader Ability
//        p1LA.setToolTipText(GameView.convertToMultiline("<font color=navy>Leader Ability</font> Available"));
//        p2LA.setToolTipText(GameView.convertToMultiline("<font color=navy>Leader Ability</font> Available"));

        revalidate();
        repaint();
    }

    public void refreshNorthPnl(Game game){
        //Update champion info
        for(JButton btn : p1Champs){
            Champion c = null;
            for(Champion x : game.getFirstPlayer().getTeam())
                if(x.getName().equals(btn.getName())) {
                    c = x;
                    break;
                }
            if(c != null)
                btn.setToolTipText(getOtherChampInfo(game,c));
            else
                btn.setEnabled(false);
        }
        for(JButton btn : p2Champs){
            Champion c = null;
            for(Champion x : game.getSecondPlayer().getTeam())
                if(x.getName().equals(btn.getName())) {
                    c = x;
                    break;
                }
            if(c != null)
                btn.setToolTipText(getOtherChampInfo(game,c));
            else
                btn.setEnabled(false);
        }

        //Refresh leader Ability
        if(game.isFirstLeaderAbilityUsed()){
//            p1LA.setToolTipText(GameView.convertToMultiline("<font color=navy>Leader Ability</font> Used"));
//            p1LA.setForeground(Color.DARK_GRAY);
            p1LA.setEnabled(false);
        }
        if(game.isSecondLeaderAbilityUsed()){
//            p2LA.setToolTipText(GameView.convertToMultiline("<font color=navy>Leader Ability</font> Used"));
//            p2LA.setForeground(Color.DARK_GRAY);
            p2LA.setEnabled(false);
        }
//        p1Pnl.revalidate();
//        p1Pnl.repaint();
//        p2Pnl.revalidate();
//        p2Pnl.repaint();
    }

    public String getOtherChampInfo(Game game, Champion c){
        String s = c.getName()
                + "\n" + c.getCurrentHP()+"/"+c.getMaxHP()+" HP"
                + "\nType: " + ((c instanceof Hero)?"Hero":(c instanceof AntiHero)?"AntiHero":"Villain")
                + "\nIs Leader: " + ((c==game.getFirstPlayer().getLeader() || c==game.getSecondPlayer().getLeader())?"Yes":"No");
        String effStr = "\nApplied Effects: ";
        for(Effect e : c.getAppliedEffects()){
            effStr += "\n" + e.getName() + "   ,   Duration: "+e.getDuration();
        }
        s += effStr;
        return GameView.convertToMultiline(s);
    }


}
