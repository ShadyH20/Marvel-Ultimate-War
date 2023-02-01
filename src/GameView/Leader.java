package GameView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Leader extends JPanel{
	
	private JLabel label = new JLabel("Choose Leader Champion");
	
	private JPanel T = new JPanel();
	private JPanel Top = new JPanel();
	private JPanel Bottom = new JPanel();
	
	private JButton c1 = new JButton();
	private JButton c2 = new JButton();
	private JButton c3 = new JButton();
	
	private JButton c4 = new JButton();
	private JButton c5 = new JButton();
	private JButton c6 = new JButton();
	
	private JButton next = new JButton("Next");

	
	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JPanel getT() {
		return T;
	}

	public void setT(JPanel t) {
		T = t;
	}

	public JButton getC1() {
		return c1;
	}

	public void setC1(JButton c1) {
		this.c1 = c1;
	}

	public JButton getC2() {
		return c2;
	}

	public void setC2(JButton c2) {
		this.c2 = c2;
	}

	public JButton getC3() {
		return c3;
	}

	public void setC3(JButton c3) {
		this.c3 = c3;
	}

	public JButton getC4() {
		return c4;
	}

	public void setC4(JButton c4) {
		this.c4 = c4;
	}

	public JButton getC5() {
		return c5;
	}

	public void setC5(JButton c5) {
		this.c5 = c5;
	}

	public JButton getC6() {
		return c6;
	}

	public void setC6(JButton c6) {
		this.c6 = c6;
	}

	public Leader() {
		
		setSize(new Dimension(800,700));
		setBackground(Color.BLACK);
		setLayout(null);
		
		// Next Button
		next.setPreferredSize(new Dimension(100,5));
		next.setBounds(500, 5, 100, 150);
		next.setEnabled(false);
		
		T.setBackground(Color.BLACK);
		Top.setBackground(Color.BLACK);
		Bottom.setBackground(Color.BLACK);
		
		T.setBorder(new LineBorder(Color.BLACK));
		Top.setBorder(new LineBorder(Color.BLACK));
		Bottom.setBorder(new LineBorder(Color.BLACK));
		
		// Layout
		T.setLayout(null);
		Top.setLayout(new GridLayout(0,3,10,0));
		Bottom.setLayout(new GridLayout(0,3,10,0));

		// Add Panels
		T.setBounds(0, 0, 1200, 50);
		Top.setBounds(0,50,1200, 240);
		Bottom.setBounds(0, 300, 1200, 240);
		
		add(T);
		add(Top);
		add(Bottom);
		
		// T Panel
		JLabel label = new JLabel();
		T.add(next);
		
		
		// Top Panel
		c1.setPreferredSize(new Dimension(265,250));
		c2.setPreferredSize(new Dimension(265,250));
		c3.setPreferredSize(new Dimension(265,250));
		Top.add(c1);
		Top.add(c2);
		Top.add(c3);
		
		
		// Bottom Panel
		c4.setPreferredSize(new Dimension(265,175));
		c5.setPreferredSize(new Dimension(265,175));
		c6.setPreferredSize(new Dimension(265,175));
		
		Bottom.add(c4);
		Bottom.add(c5);
		Bottom.add(c6);
		
		revalidate();
		repaint();
		
	}
	
	public JPanel getTop() {
		return Top;
	}

	public void setTop(JPanel top) {
		Top = top;
	}

	public JPanel getBottom() {
		return Bottom;
	}

	public void setBottom(JPanel bottom) {
		Bottom = bottom;
	}

	public JButton getNext() {
		return next;
	}

	public void setNext(JButton next) {
		this.next = next;
	}
	
	public static void main(String[]args) {
		
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(1200,600));
		Leader leader = new Leader();
		frame.getContentPane().add(leader);
		frame.setVisible(true);
		frame.repaint();
		frame.revalidate();
		
	}

}
