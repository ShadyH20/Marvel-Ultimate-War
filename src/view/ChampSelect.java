package view;

import model.world.Champion;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChampSelect extends JPanel implements ActionListener{
    JPanel champPnl = new JPanel();
    JPanel infoPnl = new JPanel();
    ArrayList<JButton> champBtns = new ArrayList<>();
    ArrayList<Champion> champions;
    JButton doneBtn = new JButton("Done");

    ChampSelectListener listener;

    public ChampSelect(){
        setLayout(new BorderLayout());

        doneBtn.setActionCommand("NEXT");
        doneBtn.addActionListener(this);

        infoPnl.setPreferredSize(new Dimension(400,getHeight()));
        infoPnl.setBorder(new LineBorder(Color.black,3));

        champPnl.setLayout(new FlowLayout());


        add(champPnl,BorderLayout.CENTER);
        add(infoPnl,BorderLayout.EAST);


    }

    public void setListener(ChampSelectListener listener) {
        this.listener = listener;
    }

    public void addBtnListener(ActionListener al){
        doneBtn.addActionListener(al);
    }

    public void AddAllChampions(ArrayList<Champion> champs){
        System.out.println("Add champs");
        champions = champs;
        for(Champion champ : champs){
            JButton btn = CreateChampBtn(champ);
            champPnl.add(btn);
        }

        champPnl.add(doneBtn);
    }

    private JButton CreateChampBtn(Champion champ){
        JButton btn = new JButton(champ.getName());
        btn.setPreferredSize(new Dimension(130,130));
        btn.setBorder(new LineBorder(Color.pink,2));
        btn.setFocusable(false);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Finished choosing champs
        if(listener != null)
            listener.onChampSelectDone();
    }
}












