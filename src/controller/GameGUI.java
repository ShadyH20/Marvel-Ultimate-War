package controller;

import engine.Game;
import engine.GameListener;
import engine.Player;
import engine.PriorityQueue;
import exceptions.*;
import model.abilities.*;
import model.world.*;
import view.ChampSelectListener;
import view.GameView;
import view.PlayerNamesListener;
import view.game_scene.EastPnl;
import view.game_scene.GameSceneListener;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class GameGUI implements ActionListener,PlayerNamesListener, ChampSelectListener, GameSceneListener, DamageableListener, GameListener {

    private Game game;
    private Player p1;
    private Player p2;

    private GameView view;
    private JPanel cards;
    private CardLayout cardL;

    //From Mazen's Control
    private boolean first_turn = true;
    private boolean second_turn = false;

    private boolean chooseLeader = false;


    public GameGUI(){
        view = new GameView();

        cards = view.getCards();
        cardL = view.getCardL();

        //Call addBtnListeners
        view.getSs().addBtnListeners(this);
        view.getPn().addBtnListener(this);
//        view.getCs().addBtnListener(this);


        //set listeners for view
        view.getPn().setPNsListener(this);
        view.getCs().setListener(this);
        view.getGs().setListener(this);

        view.getGs().setUpGlassPane(SwingUtilities.getRootPane(view));

        Sound("/audio/scene.wav",true);
    }

    public static void main(String[] args) {
        new GameGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        JButton btn = (JButton) e.getSource();
        String action = btn.getActionCommand();
        switch (action) {
            case "NEXT" -> cardL.next(cards);
            case "EXIT" -> System.exit(0);
        }
        view.revalidate();
        view.repaint();
    }

    public JFrame getFrame(){return view;}
    public Object[][] getBoard(){return game.getBoard();}
    public Game getGame(){return game;}
    public Champion getCurrChamp(){return game.getCurrentChampion();}

    public void handleException(Exception e){
        JOptionPane.showMessageDialog(null, e.getMessage(), "Game Action Exception", JOptionPane.ERROR_MESSAGE);
        Sound("./audio/Error.wav",false);
    }

    public void onPlayerNamesConfirmed(String p1Name, String p2Name) throws IOException {
        p1 = new Player(p1Name.equals("")?"Player 1":p1Name);
        p2 = new Player(p2Name.equals("")?"Player 2":p2Name);
        game = new Game(p1,p2);
        game.setAnim(view.getGs());
        game.setListener(this);
        Object[][] board = game.getBoard();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(board[i][j] instanceof Cover){
                    ((Cover)board[i][j]).setListener(this);
                }
            }
        }
        Game.loadAbilities("Abilities.csv");
        Game.loadChampions("Champions.csv");
        placeIcons();
        addDisplayMouseListener();
        view.getCs().setNames(p1.getName(), p2.getName());
    }

    @Override
    public void onChampSelectDone() {
        ArrayList<Champion> t1 = game.getFirstPlayer().getTeam();
        ArrayList<Champion> t2 = game.getSecondPlayer().getTeam();

        //%%%%%%%%% PLACEHOLDER UNTIL CHAMP SELECTION IS IMPLEMENTED %%%%%%%%%%%%
//        ArrayList<Champion> available = Game.getAvailableChampions();
//        t1.add(available.get(0));
//        t1.add(available.get(13));
//        t1.add(available.get(11));
//        game.getFirstPlayer().setLeader(available.get(0));
//        t2.add(available.get(12));
//        t2.add(available.get(4));
//        t2.add(available.get(6));
//        game.getSecondPlayer().setLeader(available.get(4));
//
//
//
        for (Champion c : t1)
            c.setListener(this);
        for (Champion c : t2)
            c.setListener(this);
        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

        game.placeChampions();
        game.getCurrentChampion();  //instead of prepareChampionTurns

        view.getGs().setUpDrawPnl(view.getDrawPnl());
        view.getGs().InitializeGameScene(game);
        updateTurnOrder();

        cardL.next(cards);
    }

//    @Override
//    public void onPlaceIcons() {
//        placeIcons();
//    }

    public void updateTurnOrder(){
        PriorityQueue pq = game.getTurnOrder();
        ArrayList<Champion> list = new ArrayList<>();
        //Put all champs in list
        int size = pq.size();
        for (int i = 0; i < size; i++) {
            Champion c = (Champion) pq.remove();
            list.add(c);
        }
        //Return champs to pq
        for(Champion c : list)
            pq.insert(c);

        //set turn order in west panel
        view.getGs().getWestPnl().setTurnOrder(list,game);
    }
    @Override
    public void onGameActionClicked(String actionName) {
//        System.out.println("You want to " + actionName);
        switch (actionName){
            case "END TURN" :
                game.endTurn();
                updateTurnOrder();
                break;
            case "USE LEADER ABILITY":
                try {
                    game.useLeaderAbility();
                } catch (LeaderNotCurrentException | LeaderAbilityAlreadyUsedException | CloneNotSupportedException e) {
                    handleException(e);
                }
                break;
        }
        view.getGs().refreshScene(game);
    }

    public void onGameActionDirectional(String actionName,Direction d){
        switch (actionName){
            case "MOVE" :
                try {
                    game.move(d);
                } catch (UnallowedMovementException | NotEnoughResourcesException e) {
                    handleException(e);
                }
                break;
            case "ATTACK" :
                try {
                    game.attack(d);
                    Sound("./audio/punch.wav",false);
                } catch (ChampionDisarmedException | NotEnoughResourcesException e) {
                    handleException(e);
                }
                break;
        }
        view.getGs().refreshScene(game);


    }

    public void onDirectionSelected(String actionName, int abilityIndex, Direction d){
        Champion champ = game.getCurrentChampion();
        Ability a = abilityIndex==-1?null:champ.getAbilities().get(abilityIndex);
        int range = 0;
        switch (actionName){
            case "MOVE" -> range = 1;
            case "ATTACK" -> range = champ.getAttackRange();
            case "CAST ABILITY" -> range = a.getCastRange();
        }

        ArrayList<Point> list = new ArrayList<>();
        Point p = champ.getLocation();
        switch (d) {
            case RIGHT:
                for (int j = p.y + 1; j <= p.y + range; j++)
                    if (j < Game.getBoardwidth())
                        list.add(new Point(p.x, j));
                break;
            case LEFT:
                for (int j = p.y - 1; j >= p.y - range; j--)
                    if (j >= 0)
                        list.add(new Point(p.x, j));
                break;
            case UP:
                for (int i = p.x + 1; i <= p.x + range; i++)
                    if (i < Game.getBoardheight())
                        list.add(new Point(i, p.y));

                break;
            case DOWN:
                for (int i = p.x - 1; i >= p.x - range; i--)
                    if (i >= 0)
                        list.add(new Point(i, p.y));
                break;
        }
        view.getGs().glowSquares(game,list);
    }

    public void onCastAbilitySelected() {
        view.getGs().setAbilities(game.getCurrentChampion().getAbilities());
    }

    public void onAbilityClicked(int index) {
        Champion champ = game.getCurrentChampion();
        Ability a = champ.getAbilities().get(index);
        String col;
        if(a instanceof DamagingAbility)
            col = "red";
        else if(a instanceof HealingAbility)
            col = "green";
        else
            col = "orange";
        String str = "<html><center>"
                + "Name: <font color=yellow>" + a.getName()
                + "</font> &nbsp;&nbsp;&nbsp;&nbsp; Type: <font color="+col+">" + a.getClass().getSimpleName()
                + "</font> &nbsp;&nbsp;&nbsp;&nbsp; AOE: <font color=yellow>" + a.getCastArea()
                + "</font><br/>                     Range: <font color=yellow>" + a.getCastRange()
                + "</font>&nbsp;&nbsp;&nbsp;&nbsp;  Mana Cost: <font color=yellow>" + a.getManaCost() + "&nbsp;&nbsp;&nbsp;&nbsp;"
                + "</font>AP Cost: <font color=yellow>" + a.getRequiredActionPoints()
                + "</font>&nbsp;&nbsp;&nbsp;&nbsp;  Cooldown: " + a.getCurrentCooldown() + "/" + a.getBaseCooldown() +"<br/>"+
                ((a instanceof HealingAbility)?"Heal Amount: <font color=green>"+((HealingAbility)a).getHealAmount():
                (a instanceof DamagingAbility)?"Damage Amount: <font color=red>"+((DamagingAbility)a).getDamageAmount():
                (a instanceof CrowdControlAbility)?"<font color=orange>"+((CrowdControlAbility) a).getEffect():"");
        view.getGs().setSouthLblTxt(str);
        
        Point p = game.getCurrentChampion().getLocation();
        ArrayList<Point> list = new ArrayList<>();
        if(a.getCastArea()==AreaOfEffect.SELFTARGET){
            list.add(p);
        }
        else if(a.getCastArea() == AreaOfEffect.SURROUND){
            for(int i = p.x - 1; i <= p.x + 1; i++)
                for(int j = p.y - 1; j <= p.y + 1; j++){
                    if( (i >= 0 && i < 5) && (j >= 0 && j < 5) ) //Check if point is inside the board
                        if(!(i == p.x && j == p.y))	//Check if point is not the middle(yellow)
                            list.add(new Point(i,j));
                }
        }
        view.getGs().glowSquares(game,list);
    }

    public void onAbilitySelected(int index) {
        Champion champ = game.getCurrentChampion();
        Ability a = champ.getAbilities().get(index);
        AreaOfEffect aoe = a.getCastArea();
        if(aoe == AreaOfEffect.SELFTARGET || aoe == AreaOfEffect.TEAMTARGET || aoe == AreaOfEffect.SURROUND){
            try {
                game.castAbility(a);
                playAbilitySound(index);
            } catch (AbilityUseException | NotEnoughResourcesException | CloneNotSupportedException e) {
                handleException(e);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ignored) {
            }
            view.getGs().refreshScene(game);
        }
        else{
            if(aoe == AreaOfEffect.SINGLETARGET){
                view.getGs().handleSingleTargetAbility();
            }
            else if(aoe == AreaOfEffect.DIRECTIONAL){
                view.getGs().handleDirectionalAbility();
            }
        }
    }

    public void onDirectionalAbilityConfirm(int abilitySelected,Direction d) {
        Champion champ = game.getCurrentChampion();
        Ability a = champ.getAbilities().get(abilitySelected);

        System.out.println("Casting " + a.getName() + "in direction " + d);
        try {
            game.castAbility(a,d);
            playAbilitySound(abilitySelected);
        } catch (AbilityUseException | NotEnoughResourcesException | CloneNotSupportedException e) {
            handleException(e);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ignored) {
        }
        view.getGs().refreshScene(game);

    }

    public void onSingleTargetAbilityConfirm(int index, Point p){
        Champion champ = game.getCurrentChampion();
        Ability a = champ.getAbilities().get(index);

        System.out.println("Casting " + a.getName() + " on " + p.x + ", " + p.y);
        try {
            game.castAbility(a,p.x,p.y);
            playAbilitySound(index);
        } catch (AbilityUseException | NotEnoughResourcesException | CloneNotSupportedException | InvalidTargetException e) {
            handleException(e);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ignored) {
        }
        view.getGs().refreshScene(game);
    }

    public String getLeaderAbilityDescription(Champion c) {
        Champion leader = game.getFirstPlayer().getTeam().contains(c)?game.getFirstPlayer().getLeader():game.getSecondPlayer().getLeader();
        return (leader instanceof Hero)?"Removes all negative effects from the player’s entire team and adds an Embrace effect to them which lasts for 2 turns.":
                (leader instanceof Villain)?"Immediately eliminates (knocks out) all enemy champions with less than 30% health points.":
                "All champions on the board except for the leaders of each team will be stunned for 2 turns.";
    }

    @Override
    public void onHpSet(Point location, int prev, int currentHP, int maxHP) {
        view.getGs().refreshHpBar(location,prev,currentHP,maxHP);
    }

    public void placeIcons() {
        ImageIcon icon = new ImageIcon("./pics/cs/Back.jpg");
        double ratio = 1.0*icon.getIconWidth()/icon.getIconHeight();
//        icon = new ImageIcon(icon.getImage().getScaledInstance((int) (ratio*GameView.H/2), (int) (GameView.H/2),Image.SCALE_SMOOTH));

        ArrayList<Champion> a = Game.getAvailableChampions();
        int index = 0;
        for(Champion c : a ) {
            JButton button = view.getCs().getList().get(index) ;
            button.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
            button.setName(c.getName());
            String name = c.getName();

            button.setIcon(new ImageIcon((new ImageIcon("./pics/icons/"+name+".jpg")).getImage().getScaledInstance(button.getPreferredSize().width,button.getPreferredSize().height,Image.SCALE_SMOOTH)));

            EastPnl pnl = new EastPnl();
            JLabel data = view.getCs().getData();
            pnl.updateEastPnl(c);
            pnl.setName(c.getName());
            pnl.setBg((ImageIcon) view.getCs().getData().getIcon());
            pnl.getMain().setBackground(null);
            pnl.getMain().setOpaque(false);
            pnl.setBorder(new EmptyBorder(2,2,2,2));
            pnl.getLabel().setFont(GameView.gameFontOutline.deriveFont(20f));
            pnl.getAbilityBtns().setBounds(0,380,(int) (ratio*GameView.H/2),80);
            pnl.setBounds(data.getX(),data.getY(),(int) (ratio*GameView.H/2), GameView.H/2);
            pnl.setVisible(false);
            view.getCs().getDataPnls().add(pnl);
            view.getCs().getDataPnl().add(pnl);

            index++;
        }
    }

    public void addDisplayMouseListener() {

        for(JButton button : view.getCs().getList()) {

            button.addMouseListener(new MouseListener() {

                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseExited(MouseEvent e) {
//                    view.getCs().getPhoto().setIcon(new ImageIcon((new ImageIcon("./pics/cs/back.jpg")).getImage().getScaledInstance(view.getCs().getPhoto().getIcon().getIconWidth(),view.getCs().getPhoto().getIcon().getIconHeight(),Image.SCALE_SMOOTH)));
//                    view.getCs().getData().setIcon(new ImageIcon((new ImageIcon("./pics/cs/back.jpg")).getImage().getScaledInstance(view.getCs().getPhoto().getIcon().getIconWidth(),view.getCs().getPhoto().getIcon().getIconHeight(),Image.SCALE_SMOOTH)));
//                    view.getCs().getData().setVisible(true);
//                    ArrayList<EastPnl> dataPnls = view.getCs().getDataPnls();
//                    view.getCs().getData().setVisible(true);
//                    for(EastPnl pnl : dataPnls){
//                        pnl.setVisible(false);
//                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    Sound("./audio/button hover.wav",false);
                    JButton button = (JButton)e.getSource();
                    String name = button.getName();
                    JLabel label1 = view.getCs().getPhoto();
                    JLabel label2 = view.getCs().getData();


                    label1.setIcon(new ImageIcon((new ImageIcon("./pics/cs/"+name+"1.jpg")).getImage().getScaledInstance(label1.getIcon().getIconWidth(),label1.getIcon().getIconHeight(),Image.SCALE_SMOOTH)));

                    label2.setVisible(false);
                    ArrayList<EastPnl> dataPnls = view.getCs().getDataPnls();
                    for(EastPnl pnl : dataPnls){
                        pnl.setVisible(pnl.getName().equals(name));
                    }

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    Sound("./audio/Button Click.wav",false);
                    // Next
                    // Clicking mouse meaning Champion should be added to Arraylist of Champions of the player
                    JButton button = (JButton)e.getSource();
                    JButton c1 = view.getCs().getC1();
                    JButton c2 = view.getCs().getC2();
                    JButton c3 = view.getCs().getC3();
                    JButton c4 = view.getCs().getC4();
                    JButton c5 = view.getCs().getC5();
                    JButton c6 = view.getCs().getC6();

                    System.out.println("Name is " + button.getName());
//                    nextListener();
                    for(JButton b : view.getCs().getList()) {
                        if(b == null) {
                            return;
                        }
                    }
                    Champion champ = null;
                    for(Champion c :  Game.getAvailableChampions()) {
                        if(button.getName().equals(c.getName())) {
                            champ = c;
                            break;
                        }
                    };
                    if(p1.getTeam().contains(champ)&&!chooseLeader) {
                        chooseLeader = false;
                        view.getCs().getLeader().setVisible(false);
                        view.getCs().getNext().setVisible(false);
                        first_turn = true;
                        second_turn = false;
                        System.out.println("balabixo");

                        if(button==view.getCs().getC1()) {
                            JButton b = new JButton();
                            JButton cBtn = view.getCs().getC1();
                            b.setBounds(cBtn.getX(), cBtn.getY(), cBtn.getWidth(), cBtn.getHeight());
                            view.getCs().setC1(b);
                        }
                        else if(button==view.getCs().getC2()) {
                            JButton b = new JButton();
                            JButton cBtn = view.getCs().getC2();
                            b.setBounds(cBtn.getX(), cBtn.getY(), cBtn.getWidth(), cBtn.getHeight());
                            view.getCs().setC2(b);
                        }
                        else if(button==view.getCs().getC3()) {
                            JButton b = new JButton();
                            JButton cBtn = view.getCs().getC3();
                            b.setBounds(cBtn.getX(), cBtn.getY(), cBtn.getWidth(), cBtn.getHeight());
                            view.getCs().setC3(b);
                        }
                        view.getCs().getTop().add(button);
                        view.getCs().getLists().add(button);
                        p1.getTeam().remove(champ);
                        view.revalidate();
                        view.repaint();
                    }

                    else if(p2.getTeam().contains(champ)&&!chooseLeader) {
                        chooseLeader = false;
                        view.getCs().getLeader().setVisible(false);
                        view.getCs().getNext().setVisible(false);
                        first_turn = false;
                        second_turn = true;
                        if(button==view.getCs().getC4()) {
                            JButton b = new JButton();
                            JButton cBtn = view.getCs().getC4();
                            b.setBounds(cBtn.getX(), cBtn.getY(), cBtn.getWidth(), cBtn.getHeight());
                            view.getCs().setC4(b);
                        }
                        else if(button==view.getCs().getC5()) {
                            JButton b = new JButton();
                            JButton cBtn = view.getCs().getC5();
                            b.setBounds(cBtn.getX(), cBtn.getY(), cBtn.getWidth(), cBtn.getHeight());
                            view.getCs().setC5(b);
                        }
                        else if(button==view.getCs().getC6()) {
                            JButton b = new JButton();
                            JButton cBtn = view.getCs().getC6();
                            b.setBounds(cBtn.getX(), cBtn.getY(), cBtn.getWidth(), cBtn.getHeight());
                            view.getCs().setC6(b);
                        }
                        view.getCs().getTop().add(button);
                        view.getCs().getLists().add(button);
                        p2.getTeam().remove(champ);
                        view.revalidate();
                        view.repaint();
                    }

                    else if(first_turn && p1.getTeam().size()<3) {
                        if(p1.getTeam().size()==3&& p2.getTeam().size()==3) {
//                            nextListener();
                            view.getCs().getNext().setVisible(true);
                        }
                        // Add Champion to the first player team

                        if(p2.getTeam().size()<3) {
                            first_turn = false;
                            second_turn = true;
                        }

                        if(c1.getIcon()==null) {
                            JButton cBtn = view.getCs().getC1();
                            button.setBounds(cBtn.getX(), cBtn.getY(), cBtn.getWidth(), cBtn.getHeight());
                            view.getCs().setC1(button);
                        }
                        else if(c2.getIcon()==null) {
                            JButton cBtn = view.getCs().getC2();
                            button.setBounds(cBtn.getX(), cBtn.getY(), cBtn.getWidth(), cBtn.getHeight());
                            view.getCs().setC2(button);
                        }
                        else if(c3.getIcon()==null) {
                            JButton cBtn = view.getCs().getC3();
                            button.setBounds(cBtn.getX(), cBtn.getY(), cBtn.getWidth(), cBtn.getHeight());
                            view.getCs().setC3(button);
                        }
                        view.getCs().getTop().remove(button);
                        view.getCs().getList().remove(button);
                        p1.getTeam().add(champ);
                        view.revalidate();
                        view.repaint();

                    }
                    else if(second_turn && p2.getTeam().size()<3){
                        if(p1.getTeam().size()==3&& p2.getTeam().size()==3) {
//                            nextListener();
                            view.getCs().getNext().setVisible(true);
                        }
                        // Add Champion to the second player team
                        if(p1.getTeam().size()<3) {
                            first_turn = true;
                            second_turn = false;
                        }
                        if(c4.getIcon()==null) {
                            JButton cBtn = view.getCs().getC4();
                            button.setBounds(cBtn.getX(), cBtn.getY(), cBtn.getWidth(), cBtn.getHeight());
                            view.getCs().setC4(button);
                        }
                        else if(c5.getIcon()==null) {
                            JButton cBtn = view.getCs().getC5();
                            button.setBounds(cBtn.getX(), cBtn.getY(), cBtn.getWidth(), cBtn.getHeight());
                            view.getCs().setC5(button);
                        }
                        else if(c6.getIcon()==null) {
                            JButton cBtn = view.getCs().getC6();
                            button.setBounds(cBtn.getX(), cBtn.getY(), cBtn.getWidth(), cBtn.getHeight());
                            view.getCs().setC6(button);
                        }
                        view.getCs().getTop().remove(button);
                        view.getCs().getList().remove(button);
                        p2.getTeam().add(champ);
                        view.revalidate();
                        view.repaint();
                    }
                    if(p1.getTeam().size()==3&& p2.getTeam().size()==3) {
                        chooseLeaderListener();
                        view.getCs().getLeader().setVisible(true);
                    }
                    else if(p1.getTeam().size()<3&& p2.getTeam().size()==3) {
                        first_turn = true;
                        second_turn = false;
                    }
                    else if(p1.getTeam().size()==3&& p2.getTeam().size()<3) {
                        first_turn = false;
                        second_turn = true;
                    }

                    if(chooseLeader) {
                        System.out.println(button.getName());
                        for(Champion c: p1.getTeam()) {
                            if(c.getName().equals(button.getName())) {
                                p1.setLeader(c);

                            }
                        }
                        for(Champion c: p2.getTeam()) {
                            if(c.getName().equals(button.getName())) {
                                p2.setLeader(c);
                            }
                        }
                        if(p1.getLeader()!=null&& p2.getLeader()!=null) {
                            view.getCs().getNext().setVisible(true);
                        }

                        //Put border
                        if(p1.getLeader() != null){
                            if (c1.getName().equals(p1.getLeader().getName()))
                                c1.setBorder(BorderFactory.createLineBorder(Color.red,4));
                            else
                                c1.setBorder(BorderFactory.createLineBorder(Color.yellow,2));
                            if (c2.getName().equals(p1.getLeader().getName()))
                                c2.setBorder(BorderFactory.createLineBorder(Color.red,4));
                            else
                                c2.setBorder(BorderFactory.createLineBorder(Color.yellow,2));
                            if (c3.getName().equals(p1.getLeader().getName()))
                                c3.setBorder(BorderFactory.createLineBorder(Color.red,4));
                            else
                                c3.setBorder(BorderFactory.createLineBorder(Color.yellow,2));
                        }
                        if(p2.getLeader() != null){
                            if (c4.getName().equals(p2.getLeader().getName()))
                                c4.setBorder(BorderFactory.createLineBorder(Color.blue,4));
                            else
                                c4.setBorder(BorderFactory.createLineBorder(Color.yellow,2));
                            if (c5.getName().equals(p2.getLeader().getName()))
                                c5.setBorder(BorderFactory.createLineBorder(Color.blue,4));
                            else
                                c5.setBorder(BorderFactory.createLineBorder(Color.yellow,2));
                            if (c6.getName().equals(p2.getLeader().getName()))
                                c6.setBorder(BorderFactory.createLineBorder(Color.blue,4));
                            else
                                c6.setBorder(BorderFactory.createLineBorder(Color.yellow,2));
                        }
                        view.getCs().getCenter().revalidate();
                        view.getCs().getCenter().repaint();
                    }
                    String l1 = p1.getLeader()==null?"null": p1.getLeader().getName();
                    String l2 =  p2.getLeader()==null?"null": p2.getLeader().getName();
                    System.out.println("First Team : " + c1.getName() + " " + c2.getName() + " " + c3.getName() + l1 );
                    System.out.println("second Team : " + c4.getName() + " " + c5.getName() + " " + c6.getName() + l2 );
                    System.out.println(p1.getTeam().size());
                    view.revalidate();
                    view.repaint();
                }
            });

        }
    }

    public void nextListener() {
        // Next Button
        view.getCs().getNext().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Next Button
                System.out.println("Next listener has been set");
                cardL.next(cards);
            }
        });
    }

    Clip clip;
    public void chooseLeaderListener() {

        view.getCs().getLeader().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                chooseLeader = true;
//                Sound(,false);
                try {
                    clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(getClass().getClassLoader().getResourceAsStream("./audio/chooseLeader (1).wav")));
                    clip.start();
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ignored) {
                }
            }
        });
    }

    public static void Sound(String sound,boolean loop){
        try {
            System.out.println(sound);

//            URL url = GameGUI.class.getResource(sound);
//            System.out.println(url);
//            InputStream is= GameGUI.class.getResourceAsStream(sound);
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(is);

            InputStream audioSrc = GameGUI.class.getResourceAsStream(sound);
            //add buffer for mark/reset support
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);

            // Get clip resource
            Clip clip = AudioSystem.getClip();

            // Open clip from audio input stream
            clip.open(audioInputStream);
            //TESTTTTT



//            InputStream audioSrc = GameGUI.class.getResourceAsStream(sound);
//            //add buffer for mark/reset support
//            InputStream bufferedIn = new BufferedInputStream(audioSrc);
//            AudioInputStream audio = AudioSystem.getAudioInputStream(bufferedIn);
//
////            AudioInputStream audio = AudioSystem.getAudioInputStream(new FileInputStream(sound));
//            Clip clip = AudioSystem.getClip();
//            clip.open(audio);
            clip.start();
            if(loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            System.out.println("Error for: " + sound);
            ex.printStackTrace();
        }
    }

    public void playAbilitySound(int index) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        Champion champ = game.getCurrentChampion();
//        Ability a = champ.getAbilities().get(index);
//        System.out.println(champ.getName());

//        Sound("./audio/"+champ.getName()+".wav",false);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                getClass().getClassLoader().getResourceAsStream("./audio/"+champ.getName()+".wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
//        FloatControl gainControl =
//                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//        gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
        clip.start();

    }

    @Override
    public void onGameOver(Player winner) {
        view.getWs().setUpWinScreen(winner);
        view.getDrawPnl().setVisible(false);
        cardL.show(cards,"WinScreen");
        clip.stop();
        Sound("./audio/Victory.wav",true);
    }
}
