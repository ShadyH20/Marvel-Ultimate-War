package view.game_scene;

import model.abilities.Ability;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Effect;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Hero;
import view.GameView;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class EastPnl extends JPanel{

    private JPanel main;
    static final int W = (int) (0.2*Toolkit.getDefaultToolkit().getScreenSize().getWidth());
    static final int H = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight());

    JLabel image = new JLabel();
    JLabel name = new JLabel();
    JLabel currentHp = new JLabel();
    JLabel mana = new JLabel();
    JLabel speed = new JLabel();
    JLabel maxActions = new JLabel();
    JLabel attackDmg = new JLabel();
    JLabel attackRange = new JLabel();
    JLabel type = new JLabel();
    JLabel leader = new JLabel();

    JLabel label;
    JPanel abilityBtns;
    JPanel effects;
    ImageIcon background;

    JButton ability1 = new JButton();
    JButton ability2 = new JButton();
    JButton ability3 = new JButton();

    private Dimension btnDim;

    private MouseListener listener;

    public EastPnl(){


        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(400,0));
        setBorder(new LineBorder(Color.LIGHT_GRAY,2));



        main = new JPanel() {

        };

        main.setLayout(null);
        main.setBounds(0,0, getPreferredSize().width ,500);
//        main.setOpaque(false);
        main.setBackground(new Color(0x14,0x13,0x1c,150));


        add(main,BorderLayout.CENTER);

        {
            name.setText("Name : ");
            name.setFont(new Font("FiraCode", Font.BOLD, 20));
            name.setForeground(Color.WHITE);
            name.setBounds(50, 70, 170, 30);

            currentHp.setText("Current HP : ");
            currentHp.setFont(new Font("FiraCode", Font.BOLD, 20));
            currentHp.setForeground(Color.WHITE);
            currentHp.setBounds(50, 100, 170, 30);

            mana.setText("Mana : ");
            mana.setFont(new Font("FiraCode", Font.BOLD, 20));
            mana.setForeground(Color.WHITE);
            mana.setBounds(50, 130, 170, 30);

            speed.setText("Speed : ");
            speed.setFont(new Font("FiraCode", Font.BOLD, 20));
            speed.setForeground(Color.WHITE);
            speed.setBounds(50, 160, 170, 30);

            maxActions.setText("Max Action Points : ");
            maxActions.setFont(new Font("FiraCode", Font.BOLD, 20));
            maxActions.setForeground(Color.WHITE);
            maxActions.setBounds(50, 190, 200, 30);

            attackDmg.setText("Attack Damage : ");
            attackDmg.setFont(new Font("FiraCode", Font.BOLD, 20));
            attackDmg.setForeground(Color.WHITE);
            attackDmg.setBounds(50, 220, 170, 30);

            attackRange.setText("Attack Range : ");
            attackRange.setFont(new Font("FiraCode", Font.BOLD, 20));
            attackRange.setForeground(Color.WHITE);
            attackRange.setBounds(50, 250, 170, 30);

            type.setText("Type : ");
            type.setFont(new Font("FiraCode", Font.BOLD, 20));
            type.setForeground(Color.WHITE);
            type.setBounds(50, 280, 170, 30);

            leader.setText("Leader Ability used : ");
            leader.setFont(new Font("FiraCode", Font.BOLD, 20));
            leader.setForeground(Color.WHITE);
            leader.setBounds(50, 310, 200, 30);

            btnDim = new Dimension((int) (0.15 * getPreferredSize().width), (int) (0.15 * getPreferredSize().width));

            ability1.setPreferredSize(btnDim);
            ability1.setMaximumSize(btnDim);
            ability1.setBorder(new LineBorder(Color.YELLOW, 3));
            ability1.setBounds(50, 400, (int) btnDim.getWidth(), (int) btnDim.getHeight());
            ability1.setToolTipText("TEST");

            ability2.setPreferredSize(btnDim);
            ability2.setMaximumSize(btnDim);
            ability2.setBorder(new LineBorder(Color.YELLOW, 3));
            ability2.setBounds(150, 400, (int) btnDim.getWidth(), (int) btnDim.getHeight());

            ability3.setPreferredSize(btnDim);
            ability3.setMaximumSize(btnDim);
            ability3.setBorder(new LineBorder(Color.YELLOW, 3));
            ability3.setBounds(250, 400, (int) btnDim.getWidth(), (int) btnDim.getHeight());
        }

        label = new JLabel();
        label.setFont(GameView.gameFont2.deriveFont(30f));
        label.setForeground(Color.white);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setBounds(20,20,getPreferredSize().width,700);
//        label.setBorder(new LineBorder(Color.green));

         abilityBtns = new JPanel();
         abilityBtns.setLayout(new FlowLayout(FlowLayout.CENTER,30,0));
         abilityBtns.setBounds(0,490,getPreferredSize().width,100);
//         abilityBtns.setBorder(new LineBorder(Color.red));
         abilityBtns.setOpaque(false);

         effects = new JPanel(new FlowLayout(FlowLayout.LEADING));
         effects.setOpaque(false);
         effects.setBounds(200,345,200,50);
//         effects.setBorder(new LineBorder(Color.orange));


//        main.add(name);
//        main.add(currentHp);
//        main.add(currentHp);
//        main.add(mana);
//        main.add(speed);
//        main.add(maxActions);
//        main.add(attackDmg);
//        main.add(attackRange);
//        main.add(type);
//        main.add(leader);
//        main.add(ability1);
//        main.add(ability2);
//        main.add(ability3);
        main.add(label);
        main.add(abilityBtns);
        main.add(effects);

//        JRootPane root = new JRootPane();
//        Component glassPane = root.getGlassPane();
//        JPanel glass = (JPanel) glassPane;
//        glass.setLayout(new BorderLayout());
//        JButton btn = new JButton("helloo");
//        glass.add(btn);
//        glass.setVisible(true);
//
//        main.add(root);
//
//        btn.setBounds(0,0,100,100);
//        root.getContentPane().add(btn);
    }

    public void updateEastPnl(Champion c){
        String type = (c instanceof Hero)?"Hero":(c instanceof AntiHero)?"AntiHero":"Villain";
        label.setText("<html>" +
                "Name: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+champNameColored(c.getName())+" <br/>" +
                "<font size='3'><br/></font>" +
                "Type : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+type+"<br/>" +
                "<font size='3'><br/></font>" +
                "Action Points : &nbsp; "+c.getCurrentActionPoints()+"<br/>" +
                "<font size='3'><br/></font>" +
                "Mana : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+c.getMana()+"<br/>" +
                "<font size='3'><br/></font>" +
                "Attack Damage : "+c.getAttackDamage()+"<br/>" +
                "<font size='3'><br/></font>" +
                "Attack Range : &nbsp; "+c.getAttackRange()+"<br/>" +
                "<font size='3'><br/></font>" +
                "Applied Effects : <br/><br/>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Abilities<br/>" +
                "</p></body>"
        );

        //Update Abilities
        abilityBtns.removeAll();
        for(Ability a : c.getAbilities()) {
            JButton btn = new JButton();
            String path = "./pics/icons/abilities/";
            if (a.getName().equals("Punch"))
                path += "Punch.png";
            else
                path += c.getName() + "/" + a.getName() + ".png";
            btn.setIcon(new ImageIcon(path));
            btn.setContentAreaFilled(false);
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn.setFont(GameView.gameFont2.deriveFont(20f));
            btn.setForeground(Color.WHITE);
            btn.setBorder(new LineBorder(Color.orange,2));
            String col;
            if(a instanceof DamagingAbility)
                col = "red";
            else if(a instanceof HealingAbility)
                col = "green";
            else
                col = "orange";
            String str = "<html><center>"
                    + "Name: <font color=yellow>" + a.getName()
                    + "</font> <br/> Type: <font color="+col+">" + a.getClass().getSimpleName()
                    + "</font> <br/> AOE: <font color=yellow>" + a.getCastArea()
                    + "</font><br/>                     Range: <font color=yellow>" + a.getCastRange()
                    + "</font><br/>  Mana Cost: <font color=yellow>" + a.getManaCost() + "&nbsp;&nbsp;&nbsp;&nbsp;"
                    + "</font>AP Cost: <font color=yellow>" + a.getRequiredActionPoints()
                    + "</font><br/>  Cooldown: " + a.getCurrentCooldown() + "/" + a.getBaseCooldown() +"<br/>"+
                    ((a instanceof HealingAbility)?"Heal Amount: <font color=green>"+((HealingAbility)a).getHealAmount():
                            (a instanceof DamagingAbility)?"Damage Amount: <font color=red>"+((DamagingAbility)a).getDamageAmount():
                                    (a instanceof CrowdControlAbility)?"<font color=orange>"+((CrowdControlAbility) a).getEffect():"");
            btn.setToolTipText(str);
            abilityBtns.add(btn);
        }

        //Update effects
        effects.removeAll();
        for(Effect e : c.getAppliedEffects()){
            JLabel lbl = new JLabel();
            ImageIcon icon = new ImageIcon((new ImageIcon("./pics/icons/effects/"+e.getName()+".png")).getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH));
            lbl.setIcon(icon);
            lbl.setBorder(new LineBorder(Color.white));
            lbl.setToolTipText(GameView.convertToMultiline(e.getName()+"\nDuration: "+e.getDuration()));
            effects.add(lbl);
        }

        background =  new ImageIcon("./pics/3d/"+c.getName()+".png");
        background = new ImageIcon(background.getImage().getScaledInstance(getPreferredSize().width, (int) (1.0*background.getIconHeight()/background.getIconWidth()*getPreferredSize().width),Image.SCALE_SMOOTH));

        main.revalidate();
        main.repaint();
    }

    public String champNameColored(String name){
        String str = "";
        switch (name){
            case "Captain America" -> str = "<font color=blue>Captain</font><font color=red> America</font>";
            case "Deadpool" -> str = "<font color=#9c1313>Deadpool</font>";
            case "Dr Strange" -> str = "<font color=#263873>Dr </font><font color=#5e0d0d>Strange</font>";
            case "Electro" -> str = "<font color=#286b33>Elec</font><font color=#ffb300>tro</font>";
            case "Ghost Rider" -> str = "<font color=#ffb300>Ghost</font><font color=#ff6a00> Rider</font>";
            case "Hela" -> str = "<font color=#058512>Hela</font>";
            case "Hulk" -> str = "<font color=#185713>Hulk</font>";
            case "Iceman" -> str = "<font color=#00e5ff>Iceman</font>";
            case "Ironman" -> str = "<font color=#f52a0f>Iron</font><font color=#ffb300>man</font>";
            case "Loki" -> str = "<font color=#387a43>Loki</font>";
            case "Quicksilver" -> str = "<font color=silver>Quicksilver</font>";
            case "Spiderman" -> str = "<font color=red>Spider</font><font color=navy>man</font>";
            case "Thor" -> str = "<font color=#636f96>Thor</font>";
            case "Venom" -> str = "<font color=gray>Venom</font>";
            case "Yellow Jacket" -> str = "<font color=yellow>Yellow</font><font color=#454545> Jacket</font>";

        }
        return str;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, null);
    }

    public JLabel getLabel() {
        return label;
    }

    public JPanel getMain() {
        return main;
    }

    public ImageIcon getBg() {
        return background;
    }

    public JPanel getAbilityBtns() {
        return abilityBtns;
    }

    public void setBg(ImageIcon background) {
        this.background = background;
    }
}

