package view.game_scene;


import controller.GameGUI;
import engine.Game;
import model.abilities.Ability;
import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;
import model.world.Direction;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import view.GameView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class GameScene extends JPanel implements AnimationListener{
    Animator animator;

    JPanel boardPnl;
    Component glassPane;
    JPanel drawPnl;

    JPanel centerPnl;

    NorthPnl northPnl;

    JPanel southPnl = new JPanel(new GridLayout(1,2,0,5));
    JPanel actionsPnl;
    JLabel southLbl;
    String previousPnl = "MAIN";
    String currentPnl = "MAIN";

    EastPnl eastPnl;

    WestPnl westPnl;

    JPanel topPnl = new JPanel(null);

    Square[][] boardPnls = new Square[5][5];
//    ImageIcon[][] boardIcons = new ImageIcon[5][5];
//    JProgressBar[][] boardHpBars = new JProgressBar[5][5];

    GameSceneListener listener;

    String actionSelected = null;
    Direction directionSelected = null;
    int abilitySelected = -1;
    JButton[] arrowBtns = new BasicArrowButton[4];
    ArrayList<JButton> abilityBtns;
    boolean isChoosingTarget = false;
    Point selectedSquare = null;

    boolean done;
    ActionListener actionActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            CardLayout cl = ((CardLayout)actionsPnl.getLayout());
            switch (btn.getActionCommand()) {
                case "END TURN" -> listener.onGameActionClicked(btn.getActionCommand());
                case "MOVE", "ATTACK" -> {
                    actionSelected = btn.getActionCommand();
                    cl.show(actionsPnl, "DIRECTION");
                    currentPnl = "DIRECTION";
                    previousPnl = "MAIN";
                }
                case "CAST ABILITY" -> {
                    actionSelected = btn.getActionCommand();
                    listener.onCastAbilitySelected();
                    cl.show(actionsPnl, "ABILITIES");
                    currentPnl = "ABILITIES";
                    previousPnl = "MAIN";
                }
                case "USE LEADER ABILITY" -> {
                    actionSelected = btn.getActionCommand();
                    cl.show(actionsPnl, "CONFIRM PNL");
                    currentPnl = "CONFIRM PNL";
                    previousPnl = "MAIN";
                }
                case "BACK" -> {
                    cl.show(actionsPnl, previousPnl);
                    currentPnl = previousPnl;
                    previousPnl = "MAIN";
                    resetDirection();
                }
                case "CONFIRM" -> {
                    done = true;
                    if (directionSelected != null) {
                        System.out.println(actionSelected + " in direction " + directionSelected);
                        switch (actionSelected) {
                            case "MOVE" -> listener.onGameActionDirectional("MOVE", directionSelected);
                            case "ATTACK" -> listener.onGameActionDirectional("ATTACK", directionSelected);
                            case "CAST ABILITY" -> listener.onDirectionalAbilityConfirm(abilitySelected, directionSelected);
                        }
                    } else {
                        System.out.println("Confirm something non directional");
                        switch (actionSelected) {
                            case "CAST ABILITY" -> {
                                if (selectedSquare == null) {
                                    listener.onAbilitySelected(abilitySelected);
                                }
                                else {
                                    listener.onSingleTargetAbilityConfirm(abilitySelected, selectedSquare);
                                }
                            }
                            case "USE LEADER ABILITY" -> {
                                listener.onGameActionClicked(actionSelected);
                            }
                        }

                    }
                    if(done){
                        //Reset direction,action and direction buttons
                        resetDirection();
                        actionSelected = null;
                        abilitySelected = -1;
                        cl.show(actionsPnl, "MAIN");
                        currentPnl = "MAIN";
                        previousPnl = "MAIN";
                    }
                }
            }
            setSouthLblTxt(null);

        }
    };

    private void resetDirection(){
        //Reset direction, and direction buttons
        directionSelected = null;
        selectedSquare = null;
        for (int i = 0; i < 4; i++) {
            arrowBtns[i].setEnabled(true);
        }
        arrowsLbl.setIcon(new ImageIcon((new ImageIcon("./pics/arrow.png")).getImage().getScaledInstance(arrowsLbl.getIcon().getIconWidth(),arrowsLbl.getIcon().getIconHeight(),Image.SCALE_SMOOTH)));
        Game game = listener.getGame();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                JPanel pnl = boardPnls[i][j];
                Color borderColor = getBorderColor(game,i,j);
                pnl.setBorder(new LineBorder(borderColor, 2));

            }
        }
    }

    MouseAdapter actionMouseListener = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            GameGUI.Sound("./audio/Button Click.wav",false);
        }

        public void mouseEntered(MouseEvent e) {
//            GameGUI.Sound("./audio/button hover.wav",false);
        }
    };


    MouseAdapter squareActionListener = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            Square pnl = (Square) e.getSource();
            int x = pnl.getI();
            int y = pnl.getJ();
            System.out.println(x + " , " + y);
            if (isChoosingTarget) {
                ArrayList<Point> list = new ArrayList<>();
                list.add(new Point(x,y));
                selectedSquare = new Point(x,y);
                glowSquares(listener.getGame(),list);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Square p = (Square) e.getSource();
            if(!((JLabel)(p.getComponent(0))).getText().equals(""))
                p.getBar().setVisible(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Square p = (Square) e.getSource();
            //start timer to make hpBar invisible
            Timer timer = new Timer(2500, x -> {
                p.getBar().setVisible(false);
                revalidate();
                repaint();
            });
            timer.setRepeats(false);
            timer.start();
        }
    };


    ActionListener arrowsListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            BasicArrowButton btn = (BasicArrowButton) e.getSource();
            int direction = btn.getDirection() / 2; //to convert 1,3,5,7 to 0,1,2,3
//            for (int i = 0; i < 4; i++) {
//                arrowBtns[i].setEnabled(true);
//            }
//            arrowBtns[direction].setEnabled(false);
            directionSelected = direction==0? Direction.UP :direction==1?Direction.RIGHT:
                                direction==2?Direction.DOWN:direction==3?Direction.LEFT:null;

            arrowsLbl.setIcon(new ImageIcon((new ImageIcon("./pics/arrow"+directionSelected+".png")).getImage().getScaledInstance(arrowsLbl.getIcon().getIconWidth(),arrowsLbl.getIcon().getIconHeight(),Image.SCALE_SMOOTH)));

            listener.onDirectionSelected(actionSelected,abilitySelected,directionSelected);
        }
    };

    //Constructor
    public GameScene(){
        setLayout(new BorderLayout());
//        setBackground(new Color(0x14131c));
//        setOpaque(true);

        setUpNorthPnl();
        setUpSouthPnl();
        setUpEastPnl();
        setUpWestPnl();
        setUpCenterPnl();

        add(northPnl,BorderLayout.NORTH);
        add(southPnl,BorderLayout.SOUTH);
        add(eastPnl,BorderLayout.EAST);
        add(westPnl,BorderLayout.WEST);
        add(centerPnl,BorderLayout.CENTER);
//        System.out.println("Pane width: "+ (GameView.W-westPnl.getWidth()-eastPnl.getWidth()));

    }

    public WestPnl getWestPnl() {
        return westPnl;
    }

    public NorthPnl getNorthPnl() {
        return northPnl;
    }

    public void setListener(GameSceneListener listener) {
        this.listener = listener;
    }

    private void setUpEastPnl() {
        ImageIcon bgBlack = new ImageIcon((new ImageIcon("./pics/Background1.jpg").getImage().getScaledInstance((int) (0.2 * GameView.W),GameView.H*2/3,Image.SCALE_SMOOTH)));
        eastPnl = new EastPnl(){
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                g.drawImage(bgBlack.getImage(),0,0,null);
//            }
        };

//        eastPnl.setPreferredSize(new Dimension((int) (0.2 * GameView.W),0));
//        eastPnl.setBorder(new LineBorder(Color.DARK_GRAY,2));
    }

    private void setUpSouthPnl() {
        southPnl.setOpaque(false);
        southPnl.setPreferredSize(new Dimension(0, (int) (0.15* GameView.H)));
        int gap = (int) (0.05 * southPnl.getPreferredSize().height);
        southPnl.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
//        southPnl.setBorder(new LineBorder(Color.DARK_GRAY,2));

        southLbl = new JLabel("",JLabel.CENTER);
        southLbl.setFont(GameView.gameFont2.deriveFont(20f));
        southLbl.setForeground(Color.lightGray);
        southLbl.setBorder(BorderFactory.createLineBorder(Color.pink,3,true));

        actionsPnl = createActionsPanel();

        southPnl.add(southLbl);
        southPnl.add(actionsPnl);
    }

    private JPanel createActionsPanel(){
        CardLayout cl = new CardLayout();
        JPanel pnl = new JPanel(cl);
        pnl.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
        pnl.setOpaque(false);

        JPanel mainPnl = createMainActionsPnl();
        JPanel directionPnl = createDirectionPnl(mainPnl);
        JPanel abilitiesPnl = createAbilitiesPnl();
        JPanel confirmPnl = createConfirmPnl();

        pnl.add(mainPnl,"MAIN");
        pnl.add(directionPnl,"DIRECTION");
        pnl.add(abilitiesPnl,"ABILITIES");
        pnl.add(confirmPnl,"CONFIRM PNL");

        return pnl;
    }

    private JPanel createMainActionsPnl(){
        JPanel mainPnl = new JPanel(new GridBagLayout());
        mainPnl.setOpaque(false);
        JButton moveBtn = createActionBtn("Move");
        JButton attackBtn = createActionBtn("Attack");
        JButton castAbilityBtn = createActionBtn("Cast Ability");
        JButton endTurnBtn = createActionBtn("End Turn");
        JButton useLeaderAbilityBtn = createActionBtn("Use Leader Ability");
//        useLeaderAbilityBtn.setPreferredSize(new Dimension(60,useLeaderAbilityBtn.getHeight()));
        useLeaderAbilityBtn.setIcon(new ImageIcon(btnImage.getImage().getScaledInstance(90,90,Image.SCALE_SMOOTH)));
        useLeaderAbilityBtn.setPressedIcon(new ImageIcon(btnImagePressed.getImage().getScaledInstance(90,90,Image.SCALE_SMOOTH)));
        useLeaderAbilityBtn.setText(GameView.convertToMultiline("<center>Leader\nAbility"));

        //Placing action btns
        {
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;c.gridy = 0;c.gridheight=2;c.ipady=40;c.weightx=0.0;c.fill=GridBagConstraints.HORIZONTAL;
            c.ipadx=40;c.insets=new Insets(0,10,0,0);mainPnl.add(useLeaderAbilityBtn,c);
            c.weightx=0.5;c.insets=new Insets(5,5,5,5);
            c.gridx=1;c.gridheight=1; c.ipady=0;   mainPnl.add(moveBtn,c);
            c.gridx=2;                             mainPnl.add(attackBtn,c);
            c.gridx=1; c.gridy=1;                  mainPnl.add(castAbilityBtn,c);
            c.gridx=2;                             mainPnl.add(endTurnBtn,c);
        }
        return mainPnl;
    }

    JLabel arrowsLbl;
    ImageIcon arrowsIcon = new ImageIcon((new ImageIcon("./pics/arrow.png")).getImage().getScaledInstance(102, 102,Image.SCALE_SMOOTH));
    private JPanel createDirectionPnl(JPanel p){
        JPanel pnl = new JPanel(null);
        pnl.setOpaque(false);
        pnl.add(new JButton());
        int yGap = (southPnl.getPreferredSize().height - 34*3 - 7)/2;
        int W = GameView.W/2;
//        System.out.println(southPnl.getVisibleRect());
        JButton up = new BasicArrowButton(BasicArrowButton.NORTH,new Color(0,0,0,0),new Color(0,0,0,0),new Color(0,0,0,0),new Color(0,0,0,0));
        up.setBounds(W/2-17,yGap,34,34);
        up.addActionListener(arrowsListener);

        JButton down = new BasicArrowButton(BasicArrowButton.SOUTH,new Color(0,0,0,0),new Color(0,0,0,0),new Color(0,0,0,0),new Color(0,0,0,0));
        down.setBounds(W/2-17,yGap+68,34,34);
        down.addActionListener(arrowsListener);

        JButton right = new BasicArrowButton(BasicArrowButton.EAST,new Color(0,0,0,0),new Color(0,0,0,0),new Color(0,0,0,0),new Color(0,0,0,0));
        right.setBounds(W/2+17,yGap+34,34,34);
        right.addActionListener(arrowsListener);

        JButton left = new BasicArrowButton(BasicArrowButton.WEST,new Color(0,0,0,0),new Color(0,0,0,0),new Color(0,0,0,0),new Color(0,0,0,0));
        left.setBounds(W/2-51,yGap+34,34,34);
        left.addActionListener(arrowsListener);

        arrowsLbl = new JLabel(arrowsIcon);
        arrowsLbl.setBounds(W/2-51,yGap,105,105);

        arrowBtns[0] = up;arrowBtns[1]=right;arrowBtns[2]=down;arrowBtns[3]=left;

        JButton confirmBtn = createActionBtn("Confirm");
        confirmBtn.setBounds(W-200,southPnl.getPreferredSize().height/2-20,100,40);
        confirmBtn.setIcon(new ImageIcon(btnImage.getImage().getScaledInstance(100,40,Image.SCALE_SMOOTH)));
        confirmBtn.setPressedIcon(new ImageIcon(btnImagePressed.getImage().getScaledInstance(100,40,Image.SCALE_SMOOTH)));

        JButton backBtn = createActionBtn("Back");
        backBtn.setBounds(100,southPnl.getPreferredSize().height/2-20,100,40);
        backBtn.setIcon(new ImageIcon(btnImage.getImage().getScaledInstance(100,40,Image.SCALE_SMOOTH)));
        backBtn.setPressedIcon(new ImageIcon(btnImagePressed.getImage().getScaledInstance(100,40,Image.SCALE_SMOOTH)));

        pnl.add(arrowsLbl);
        pnl.add(up);
        pnl.add(down);
        pnl.add(left);
        pnl.add(right);
        pnl.add(confirmBtn);
        pnl.add(backBtn);

        return pnl;
    }

    private JPanel createAbilitiesPnl(){
        int W = GameView.W/2;
//        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl,BoxLayout.X_AXIS));
        pnl.setOpaque(false);

        JButton confirmBtn = createActionBtn("Confirm");
        confirmBtn.setBounds(W-200,southPnl.getPreferredSize().height/2-20,100,40);
        confirmBtn.setIcon(new ImageIcon(btnImage.getImage().getScaledInstance(100,40,Image.SCALE_SMOOTH)));
        confirmBtn.setPressedIcon(new ImageIcon(btnImagePressed.getImage().getScaledInstance(100,40,Image.SCALE_SMOOTH)));

        JButton backBtn = createActionBtn("Back");
        backBtn.setBounds(100,southPnl.getPreferredSize().height/2-20,100,40);
        backBtn.setIcon(new ImageIcon(btnImage.getImage().getScaledInstance(100,40,Image.SCALE_SMOOTH)));
        backBtn.setPressedIcon(new ImageIcon(btnImagePressed.getImage().getScaledInstance(100,40,Image.SCALE_SMOOTH)));

//        JPanel aPnl = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        JPanel aPnl = new JPanel(new GridBagLayout());
        aPnl.setOpaque(false);

        pnl.add(Box.createRigidArea(new Dimension(20,0)));
        pnl.add(backBtn);
        pnl.add(aPnl);
        pnl.add(confirmBtn);
        pnl.add(Box.createRigidArea(new Dimension(20,0)));

        return pnl;
    }

    public JPanel createConfirmPnl(){
        int W = GameView.W/2;
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl,BoxLayout.X_AXIS));
        pnl.setOpaque(false);

        JButton confirmBtn = createActionBtn("Confirm");
        confirmBtn.setBounds(W-300,southPnl.getPreferredSize().height/2-20,100,40);
        confirmBtn.setIcon(new ImageIcon(btnImage.getImage().getScaledInstance(100,40,Image.SCALE_SMOOTH)));
        confirmBtn.setPressedIcon(new ImageIcon(btnImagePressed.getImage().getScaledInstance(100,40,Image.SCALE_SMOOTH)));

        JButton backBtn = createActionBtn("Back");
        backBtn.setBounds(200,southPnl.getPreferredSize().height/2-20,100,40);
        backBtn.setIcon(new ImageIcon(btnImage.getImage().getScaledInstance(100,40,Image.SCALE_SMOOTH)));
        backBtn.setPressedIcon(new ImageIcon(btnImagePressed.getImage().getScaledInstance(100,40,Image.SCALE_SMOOTH)));

//        JPanel aPnl = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        JLabel label = new JLabel();

        pnl.add(Box.createRigidArea(new Dimension(100,0)));
        pnl.add(backBtn);
        pnl.add(Box.createHorizontalGlue());
        pnl.add(label);
        pnl.add(Box.createHorizontalGlue());
        pnl.add(confirmBtn);
        pnl.add(Box.createRigidArea(new Dimension(100,0)));

        return pnl;
    }

    ImageIcon btnImage = new ImageIcon("./pics/button.png");
    ImageIcon btnImagePressed = new ImageIcon("./pics/button clicked.png");
    private JButton createActionBtn(String actionName){
        JButton btn = new JButton(actionName);
        btn.setActionCommand(actionName.toUpperCase());
        btn.addActionListener(actionActionListener);
        btn.addMouseListener(actionMouseListener);
        btn.setFocusable(false);
        btn.setIcon(new ImageIcon(btnImage.getImage().getScaledInstance(200,50,Image.SCALE_SMOOTH)));
        btn.setPressedIcon(new ImageIcon(btnImagePressed.getImage().getScaledInstance(200,50,Image.SCALE_SMOOTH)));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFont(GameView.gameFontBold.deriveFont(20f));
        btn.setForeground(Color.lightGray);
        btn.setVerticalTextPosition(SwingConstants.CENTER);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        return btn;
    }

    public void setAbilities(ArrayList<Ability> abilities){
        JPanel aPnl = (JPanel) ((JPanel)(actionsPnl.getComponent(2))).getComponent(2);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets= new Insets(5,5,5,5);
        aPnl.removeAll();
        aPnl.revalidate();aPnl.repaint();
        abilityBtns = new ArrayList<>();
        for(Ability a : abilities){
            JButton btn = new JButton(a.getName());
            String path = "./pics/icons/abilities/";
            if(a.getName().equals("Punch"))
                path += "Punch.png";
            else
                path += listener.getCurrChamp().getName() + "/" + a.getName() + ".png";
            btn.setIcon(new ImageIcon(path));
            btn.setFont(GameView.gameFont2.deriveFont(20f));
            btn.setForeground(Color.WHITE);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.YELLOW, 3, true),
                                                            BorderFactory.createEmptyBorder(6,6,6,6)));
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    abilitySelected = abilityBtns.indexOf(btn);
                    listener.onAbilityClicked(abilitySelected);
                    for(JButton b : abilityBtns){
                        b.setBorderPainted(b == btn);
                    }

                }
            });
            btn.setFocusable(false);
            abilityBtns.add(btn);
            aPnl.add(btn,gbc);
        }
    }

    public void handleSingleTargetAbility(){
        done = false;
        CardLayout cl = ((CardLayout)actionsPnl.getLayout());
        cl.show(actionsPnl,"CONFIRM PNL");
        currentPnl = "CONFIRM PNL";
        previousPnl="ABILITIES";
        isChoosingTarget = true;
    }

    public void handleDirectionalAbility(){
        done = false;
        CardLayout cl = ((CardLayout)actionsPnl.getLayout());
        cl.show(actionsPnl,"DIRECTION");
        currentPnl = "DIRECTION";
        previousPnl="ABILITIES";
        setSouthLblTxt(null);
    }

    public void setSouthLblTxt(String text){
        if(text==null) {
            Champion c = listener.getCurrChamp();
            switch (currentPnl) {
                case "MAIN" -> {
                    southLbl.setText("<html><center>"
                            + "<font size='10'>"
                            + "Choose an action.</font><br/>Current Action Points: <font size='6',color=yellow><u>" + c.getCurrentActionPoints() + "</u></font></html>");
              }
                case "DIRECTION" -> {
                    switch (actionSelected) {
                        case "MOVE" -> southLbl.setText("<html><center>"
                                + "<font size='10'>"
                                + "Choose a direction to move.</font><br/>Required Action Points: <font size='6',color=yellow><u>1</u></html>");
                        case "ATTACK" -> southLbl.setText("<html><center>"
                                + "<font size='10'>"
                                + "Choose a direction to attack.</font><br/>Required Action Points: <font size='6',color=yellow><u>2</u></html>");
                        case "CAST ABILITY" -> southLbl.setText("<html><center>"
                                + "<font size='10'>"
                                + "Choose a direction to cast the ability.</font><br/>Required Action Points: <font size='6',color=yellow><u>" +
                                c.getAbilities().get(abilitySelected).getRequiredActionPoints()+"</u></html>");
                    }
                }
                case "ABILITIES" -> {
                    southLbl.setText("<html><center>"
                            + "<font size='10'>"
                            + "Choose an ability to cast.</font><br/>Current Action Points: <font size='6',color=yellow><u>" + c.getCurrentActionPoints() + "</u></font></html>");
                }
                case "CONFIRM PNL" -> {
                    if(actionSelected.equals("USE LEADER ABILITY")){
                        String desc = listener.getLeaderAbilityDescription(c);
                        southLbl.setText("<html><center>"
                                + "<font size='10'>"
                                + "Confirm using leader ability.</font><br/>"
                                + "<u>Description:</u> " + desc);
                    }
                    else if (actionSelected.equals("CAST ABILITY")){
                        southLbl.setText("<html><center>"
                                + "<font size='10'>"
                                + "Click on square to select target for ability.</html>");
                    }
                }
//
            }
        }
        else{
            southLbl.setText(text);
        }
    }


    private void setUpNorthPnl() {
        northPnl = new NorthPnl();

    }

    private void setUpWestPnl() {
        westPnl = new WestPnl();
    }

    private void setUpCenterPnl() {
        centerPnl = new JPanel(new FlowLayout()){
//            protected void paintComponent(Graphics g) {
//
//                super.paintComponent(g);
//                g.drawImage(bgImage.getImage(), 0, 0, null);
//            }
        };
        centerPnl.setOpaque(false);
        centerPnl.setLayout(new GridBagLayout());
        centerPnl.setBackground(new Color(0x1e1b33));
//        centerPnl.setBounds(0,0,GameView.W-westPnl.getPreferredSize().width-eastPnl.getPreferredSize().width,GameView.H-northPnl.getPreferredSize().height-southPnl.getPreferredSize().height);
//        System.out.println(GameView.W+"-"+westPnl.getPreferredSize().width+"-"+eastPnl.getPreferredSize().width+"="+ centerPnl.getWidth());
        setUpBoardPnl();
        centerPnl.add(boardPnl);
    }


    ImageIcon ground = new ImageIcon("./pics/land.jpg");


    private void setUpBoardPnl(){


        boardPnl = new JPanel(new GridLayout(5,5,0,0)){

            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Container c = getParent();
                if (c != null) {
                    d = c.getSize();
                } else {
                    return new Dimension(10, 10);
                }
                int w = (int) d.getWidth();
                int h = (int) d.getHeight();
                int s = (Math.min(w, h));
                return new Dimension(s, s);
            }

            final ImageIcon groundSide = new ImageIcon((new ImageIcon("./pics/groundH.png")).getImage().getScaledInstance(655,32,Image.SCALE_SMOOTH));
            final ImageIcon groundSide2 = new ImageIcon((new ImageIcon("./pics/groundV.png")).getImage().getScaledInstance(22, 620,Image.SCALE_SMOOTH));

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g.setColor(new Color(150,150,150));
                g.setColor(Color.BLACK);
                Dimension d = boardPnl.getPreferredSize();
                int xSh = 22;
                int ySh = 32;
                g.fillRect(0,64+25,boardPnl.getPreferredSize().width-xSh-10,boardPnl.getPreferredSize().height);
                g2d.setColor(Color.black);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(0,d.height,xSh,d.height-ySh);

//                for (int i = 0,x=0; i < 5; i++,x+=groundSide.getIconWidth()-xSh)
                g2d.drawImage(groundSide.getImage(),0,d.height-ySh,null);
//                for (int i = 0,y=ySh+15; i < 5; i++,y+=groundSide2.getIconHeight()-ySh)
                g2d.drawImage(groundSide2.getImage(),0,ySh+15,null);
            }
            };

        boardPnl.setOpaque(false);
        boardPnl.setBorder(BorderFactory.createEmptyBorder(45,20,30,0));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Square p = new Square(new BorderLayout()){
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(ground.getImage(),0,0,null);

                    }
                };
                p.setI(i); p.setJ(j);
                p.setOpaque(true);
                p.setBorder(new LineBorder(Color.BLACK,2));
                p.addMouseListener(squareActionListener);

                boardPnls[i][j] = p;
                boardPnl.add(p,i,j);
                if(i % 2 == j % 2)
                    p.setBackground(Color.LIGHT_GRAY);
//                    p.setBackground(Color.WHITE);
                else
                    p.setBackground(Color.white);
            }
        }
    }

    public void setUpGlassPane(JRootPane root){
        root.setGlassPane(new JComponent() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
//                for (int i = 0; i < 5; i++) {
//                    for (int j = 0; j < 5; j++) {
//                        Square pnl = boardPnls[i][j];
//                        ImageIcon icon = pnl.getIcon();
//                        Point p = SwingUtilities.convertPoint(boardPnl, pnl.getLocation(), this);
//                        Point newP = new Point(p.x + (pnl.getWidth()-icon.getIconWidth())/2, (int) (p.y+0.8*pnl.getHeight()-icon.getIconHeight()));
//                        g.drawImage(icon.getImage(), newP.x, newP.y, null);
//                    }
//                }
//                revalidate();
//                repaint();

            }
        });
        glassPane = root.getGlassPane();
    }

    public void setUpDrawPnl(JPanel drawPnl){
        this.drawPnl = drawPnl;

    }

    public void refreshHpBars(){
        if(!isAnimating) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    Square pnl = boardPnls[i][j];
                    JProgressBar bar = pnl.getBar();
                    ImageIcon icon = pnl.getIcon();
                    JLabel label = pnl.getLabel();
                    Point p = SwingUtilities.convertPoint(boardPnl, pnl.getLocation(), drawPnl);
                    Point newP2 = new Point(p.x + (pnl.getWidth() - icon.getIconWidth()) / 2, (int) (p.y + 0.8 * pnl.getHeight() - icon.getIconHeight()));
                    Point newP = new Point(p.x + (pnl.getWidth() - bar.getWidth()) / 2, newP2.y);

                    bar.setBounds(newP.x, newP.y, bar.getWidth(), bar.getHeight());
                    label.setBounds(newP2.x, newP2.y, icon.getIconWidth(), icon.getIconHeight());
                    drawPnl.add(bar);
                    drawPnl.add(label);
                }
            }

            drawPnl.revalidate();
            drawPnl.repaint();
        }
    }

    public static Color getBorderColor(Game game, int i, int j){
        Color borderColor = Color.black;
        Object o = game.getBoard()[i][j];
        if (o instanceof Champion c) {
            if(game.getFirstPlayer().getTeam().contains(c)){
                borderColor = c==game.getFirstPlayer().getLeader()?new Color(0xf79123):Color.red;
            }
            else{
                borderColor = c==game.getSecondPlayer().getLeader()?new Color(0x00fff2):Color.blue;
            }
        }

        return borderColor;
    }

    public void glowSquares(Game game,ArrayList<Point> list) {
        LineBorder b = new LineBorder(Color.yellow, 4);
        for (Point p : list) {
            boardPnls[p.x][p.y].setBorder(b);
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                JPanel pnl = boardPnls[i][j];
                if (pnl.getBorder() != b) {
                    int thick = 2;
                    Color borderColor = getBorderColor(game,i,j);
                    pnl.setBorder(new LineBorder(borderColor, 2));
                }
            }
        }
    }

    public void InitializeGameScene(Game game){
        refreshBoard(game,true);
        glassPane.setVisible(true);
        setSouthLblTxt(null);
        northPnl.setNorthChampBtns(game,game.getFirstPlayer().getTeam(),game.getSecondPlayer().getTeam());
        eastPnl.updateEastPnl(game.getCurrentChampion());
        //initialize remaining 3 panels
    }

    public void refreshScene(Game game){
        refreshBoard(game,false);
        northPnl.refreshNorthPnl(game);
        eastPnl.updateEastPnl(game.getCurrentChampion());
    }
    
    public void refreshBoard(Game game, boolean firstTime){
        if(!isAnimating) {
            Object[][] board = game.getBoard();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    Object o = board[i][j];
                    Square pnl = boardPnls[i][j];

                    String txt = "";
                    String shadow = "";
                    int maxHp = 0;
                    Color borderColor = getBorderColor(game, i, j);
                    Color bgColor = new Color(0, true);

                    if (o instanceof Champion) {
                        Champion c = ((Champion) o);
                        txt = c.getName();
                        maxHp = c.getMaxHP();
                        shadow = "shadow";
                        if (c == game.getCurrentChampion()) {
                            if (game.getFirstPlayer().getTeam().contains(c)) {
//                            bgColor = new Color(250, 60, 90);
                                bgColor = new Color(200, 40, 130, 80);
                            } else {
                                bgColor = new Color(0, 150, 250, 80);
                            }
                        }

                    } else if (o instanceof Cover) {
                        txt = "COVER4";
//                    shadow = "shadow";
                        maxHp = ((Cover) o).getMaxHp();
                    }

                    pnl.setBackground(bgColor);
                    pnl.setBorder(new LineBorder(borderColor, 2));
//                pnl.setBorder(BorderFactory.createMatteBorder(2,2,0,0,Color.black));

                    JLabel lbl;
                    JProgressBar bar;
                    if (firstTime) {
                        bar = createProgressBar((int) (pnl.getSize().width * 0.8), pnl.getSize().width);
                        lbl = new JLabel();

                        lbl.setOpaque(true);
                        lbl.setForeground(new Color(0, true));
                        lbl.setBackground(bgColor);
                        lbl.setBounds(0, 5, pnl.getSize().width, pnl.getSize().height);
//                    ImageIcon icon = new ImageIcon("./pics/"+txt+".png");
                        ground = new ImageIcon(ground.getImage().getScaledInstance(lbl.getWidth(), lbl.getWidth(), Image.SCALE_SMOOTH));

                        pnl.setBar(bar);
                        pnl.add(lbl);

                    } else {
                        lbl = ((JLabel) pnl.getComponent(0));
                        bar = pnl.getBar();
                        lbl.setBackground(bgColor);

                    }
                    lbl.setText(txt);
                    lbl.setIcon(new ImageIcon((new ImageIcon("./pics/alliance/" + shadow + ".png")).getImage().getScaledInstance(lbl.getWidth(), (int) (0.7 * lbl.getWidth()), Image.SCALE_SMOOTH)));
                    ImageIcon icon = new ImageIcon("./pics/alliance/" + txt + ".png");
                    double ratio = 1.0 * icon.getIconWidth() / icon.getIconHeight();
                    ImageIcon newIcon = new ImageIcon(icon.getImage().getScaledInstance((int) (1.15 * ratio * lbl.getHeight()), (int) (1.15 * lbl.getHeight()), Image.SCALE_SMOOTH));

                    pnl.setIcon(newIcon);

                    if (o != null)
                        setHpBar(bar, ((Damageable) o).getCurrentHP(), maxHp);

                }
            }
            refreshHpBars();
        }
    }

    private JProgressBar createProgressBar(int d, int pnlW){
        JProgressBar bar = new JProgressBar();
        bar.setBounds((pnlW-d)/2,2,d,15);
//        bar.setPreferredSize(new Dimension((int) (d * 0.95),15));
        bar.setStringPainted(true);
        bar.setFont(new Font("Bahnschrift",Font.PLAIN,10));
        bar.setForeground(new Color(61,153,58));
        bar.setBackground(Color.red);
        bar.setString("80/100 HP");
        bar.setValue(80);

        bar.setVisible(false);

        return bar;
    }

    private void setHpBar(JProgressBar bar, int newCurr, int max){
//        bar.setVisible(true);
        bar.setMaximum(max);
        bar.setValue(newCurr);
        bar.setString(newCurr + "/" + max + " HP");
    }
    public void refreshHpBar(Point p,int prev, int newCurr, int max){
        JProgressBar bar = boardPnls[p.x][p.y].getBar();
//        int delay = (int) (3000.0 / (newCurr-prev));
        int delay = 7;
        slideHpBar(bar,prev,newCurr,max,delay);

    }

    ImageIcon bgImage = new ImageIcon((new ImageIcon("./pics/Background5.jpg")).getImage().getScaledInstance(GameView.W,GameView.H,Image.SCALE_SMOOTH));
    ImageIcon bgImage2 = new ImageIcon((new ImageIcon("./pics/cs/A1.jpg")).getImage().getScaledInstance(GameView.W,GameView.H,Image.SCALE_SMOOTH));
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(bgImage.getImage(), 0, 0, null);
    }

    public void slideHpBar(JProgressBar bar, int initial, int end, int max,int delay){
        if(initial==end) {
            Timer timer = new Timer(2500, x -> {
                bar.setVisible(false);
                revalidate();
                repaint();
            });
            timer.setRepeats(false);
            timer.start();
            return;
        }
        int curr = end>initial?initial+1:initial-1;
        bar.setVisible(true);
        bar.setValue(curr);
        bar.setString(curr + "/" + max + " HP");
        Timer t = new Timer(delay, e -> slideHpBar(bar,curr,end,max,delay));
        t.setRepeats(false);
        t.start();
    }

    private boolean isAnimating;
    private void animateImageMove(JLabel label, Point destination) {
        System.out.println("Animating image from " + label.getLocation()+" to "+destination);
        int dur = 600;
        if (animator != null && animator.isRunning()) {
            animator.stop();
        }
        animator = PropertySetter.createAnimator(dur, label, "location", label.getLocation(), destination);
        //  To refresh or repaint graphics
        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                repaint();
            }
        });
        animator.setResolution(5);
        Timer timer = new Timer(dur, x -> {
            isAnimating = false;
            refreshScene(listener.getGame());
        });
        timer.setRepeats(false);

        animator.start();
        timer.start();

//        animator.set
        isAnimating = true;
    }

    @Override
    public void onMove(Point from, Point to) {
        System.out.println("Move from" + from +" to " + to);
        Square pnl1 = boardPnls[from.x][from.y];
        Square pnl2 = boardPnls[to.x][to.y];
        Point p1 = SwingUtilities.convertPoint(boardPnl, pnl1.getLocation(), drawPnl);
        Point p2 = SwingUtilities.convertPoint(boardPnl, pnl2.getLocation(), drawPnl);

        ImageIcon icon = pnl1.getIcon();
        JLabel label1 = pnl1.getLabel();
        JLabel label2 = pnl2.getLabel();
        Point newP2 = new Point(p2.x + (pnl1.getWidth() - icon.getIconWidth()) / 2, (int) (p2.y + 0.8 * pnl1.getHeight() - icon.getIconHeight()));
        Point dest = new Point(label1.getLocation().x + 100, label1.getLocation().y);
        animateImageMove(label1,newP2);
    }

    public static void main(String[] args) {
        GameScene scene = new GameScene();
        JProgressBar bar = scene.createProgressBar(400,500);
        bar.setVisible(true);
        bar.setPreferredSize(new Dimension(300,100));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(0,0,700,200);
        frame.add(bar);
        frame.setVisible(true);

        int prev = 80;
        int curr = 40;
//        while(prev >= curr){
//            bar.setValue(prev);
//            bar.setString(prev + " / " + 100 + " HP");
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            prev--;
//        }
        //slideHpBar(bar,80,40,100);


    }


}
