package view;

import engine.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

public class PlayerNames extends JPanel implements ActionListener{
    private JButton confirmBtn = new JButton("Done");
    private JLabel label1 = new JLabel();
    private JLabel label2 = new JLabel();
    private JTextField txtField1 = new JTextField("Player 1");
    private JTextField txtField2 = new JTextField("Player 2");

    private PlayerNamesListener listener;

    public PlayerNames(){
        setLayout(null);

        txtField1.setBounds(800,300,400,100);
        txtField1.setOpaque(false);
        txtField1.setForeground(Color.white);
        txtField1.setFont(GameView.gameFont.deriveFont(50f));
        txtField1.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                txtField1.setText("");
            }

            public void focusLost(FocusEvent e) {
                // nothing
            }
        });

        txtField2.setBounds(800,420,400,100);
        txtField2.setOpaque(false);
        txtField2.setForeground(Color.white);
        txtField2.setFont(GameView.gameFont.deriveFont(50f));
        txtField2.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                txtField2.setText("");
            }

            public void focusLost(FocusEvent e) {
                // nothing
            }
        });

        label1.setText("First Player Name  ");
        label1.setFont(GameView.gameFontBold.deriveFont(50f));
        label1.setForeground(Color.lightGray);
        label1.setBounds(430,300,370,100);

        label2.setText("Second Player Name  ");
        label2.setFont(GameView.gameFontBold.deriveFont(50f));
        label2.setBounds(430,420,370,100);
        label2.setForeground(Color.lightGray);

        confirmBtn.setSize(200,100);
        confirmBtn.setBounds(GameView.W/2-confirmBtn.getWidth()/2, (int) (GameView.H/1.6), confirmBtn.getWidth(), confirmBtn.getHeight());
        confirmBtn.setActionCommand("NEXT");
        confirmBtn.addActionListener(this);
        confirmBtn.setFocusable(false);
        confirmBtn.setFocusable(false);
        confirmBtn.setIcon(new ImageIcon(GameView.btnImage.getImage().getScaledInstance(confirmBtn.getWidth(),confirmBtn.getHeight(),Image.SCALE_SMOOTH)));
        confirmBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        confirmBtn.setVerticalTextPosition(SwingConstants.CENTER);
        confirmBtn.setFont(GameView.gameFontBold.deriveFont(30f));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setBorderPainted(false);
        confirmBtn.setContentAreaFilled(false);

        add(txtField1);
        add(txtField2);
        add(label1);
        add(label2);
        add(confirmBtn);

    }

    public void addBtnListener(ActionListener al){
        confirmBtn.addActionListener(al);
    }

    public JButton getConfirmBtn() {
        return confirmBtn;
    }

    public String getP1Name() {
        return txtField1.getText();
    }

    public String getP2Name() {
        return txtField2.getText();
    }

    public void setPNsListener(PlayerNamesListener l){
        listener = l;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(listener != null){
            try {
                listener.onPlayerNamesConfirmed(txtField1.getText(),txtField2.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    ImageIcon bgImage = new ImageIcon((new ImageIcon("./pics/Background5.jpg")).getImage().getScaledInstance(GameView.W,GameView.H,Image.SCALE_SMOOTH));
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(bgImage.getImage(), 0, 0, null);
    }
}
