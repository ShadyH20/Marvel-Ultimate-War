package view;

import engine.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel{

    private JButton playBtn;
    private JButton exitBtn;


    public StartScreen(String txt){
        //((FlowLayout)getLayout()).setAlignment(FlowLayout.LEADING);
        //setAlignmentX(JPanel.RIGHT_ALIGNMENT);
        setLayout(null);
        JLabel label = new JLabel(txt);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds((int) (GameView.W/2- (0.73 * GameView.W)/2), (int) (0.2 * GameView.H), (int) (0.73 * GameView.W),(int) (120 * GameView.H/960.0));
        label.setFont(GameView.gameFont2.deriveFont((float) (120 * GameView.H/960.0)));
//        label.setBorder(new LineBorder(Color.black,2));
//        label.setBackground(Color.gray);
//        label.setOpaque(true);
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(0x14,0x13,0x1c,150));

//        ImageIcon icon = new ImageIcon("./pics/Captain America.png");
//        label.setIcon(icon);
        add(label);

        playBtn = createBtn();
        playBtn.setText("Play");
        playBtn.setActionCommand("NEXT");
        playBtn.setFocusable(false);
        playBtn.setSize(new Dimension((int)(0.15 * GameView.W),(int)(0.07 * GameView.W)));
        playBtn.setBounds(GameView.W/2-playBtn.getWidth()/2, (int) (0.5 * GameView.H),playBtn.getWidth(), playBtn.getHeight());
        playBtn.setIcon(new ImageIcon(btnImage.getImage().getScaledInstance(playBtn.getWidth(),playBtn.getHeight(),Image.SCALE_SMOOTH)));
        playBtn.setPressedIcon(new ImageIcon(btnImagePressed.getImage().getScaledInstance(playBtn.getWidth(),playBtn.getHeight(),Image.SCALE_SMOOTH)));
        playBtn.setFont(playBtn.getFont().deriveFont(40f));

        exitBtn = createBtn();
        exitBtn.setText("Exit");
        exitBtn.setActionCommand("EXIT");
        exitBtn.setFocusable(false);
        exitBtn.setSize(new Dimension((int)(0.15 * GameView.W),(int)(0.07 * GameView.W)));
        exitBtn.setBounds(GameView.W/2-playBtn.getWidth()/2, (int) (0.52 * GameView.H) + playBtn.getHeight(),playBtn.getWidth(), playBtn.getHeight());
        exitBtn.setIcon(new ImageIcon(btnImage.getImage().getScaledInstance(exitBtn.getWidth(),exitBtn.getHeight(),Image.SCALE_SMOOTH)));
        exitBtn.setPressedIcon(new ImageIcon(btnImagePressed.getImage().getScaledInstance(exitBtn.getWidth(),exitBtn.getHeight(),Image.SCALE_SMOOTH)));
        exitBtn.setFont(exitBtn.getFont().deriveFont(40f));

        add(playBtn);
        add(exitBtn);

        revalidate();
        repaint();



    }

    public JButton getPlayBtn() {
        return playBtn;
    }

    public JButton getExitBtn() {
        return exitBtn;
    }

    public void addBtnListeners(ActionListener al){
        playBtn.addActionListener(al);
        exitBtn.addActionListener(al);
    }

    ImageIcon bgImage = new ImageIcon((new ImageIcon("./pics/M2.jpg")).getImage().getScaledInstance(GameView.W,GameView.H,Image.SCALE_SMOOTH));
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage.getImage(), 0, 0, null);
    }

    ImageIcon btnImage = new ImageIcon("./pics/button.png");
    ImageIcon btnImagePressed = new ImageIcon("./pics/button clicked.png");
    private JButton createBtn(){
        JButton btn = new JButton();
//        btn.setActionCommand(actionName.toUpperCase());
        btn.setFocusable(false);
        btn.setIcon(new ImageIcon(btnImage.getImage().getScaledInstance(200,50,Image.SCALE_SMOOTH)));
        btn.setPressedIcon(new ImageIcon(btnImagePressed.getImage().getScaledInstance(200,50,Image.SCALE_SMOOTH)));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFont(GameView.gameFontBold.deriveFont(20f));
        btn.setForeground(Color.white);
        btn.setVerticalTextPosition(SwingConstants.CENTER);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        return btn;
    }
    
}
