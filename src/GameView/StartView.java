package GameView;

import javax.swing.*;
import java.awt.*;

public class StartView extends JFrame {
	
	private JLabel label1;
	private JLabel label2;
	private JTextField field1;
	private JTextField field2;
	private JButton startButton;
	 

	public JButton getStart_button() {
		return startButton;
	}

	public JButton getStartButton() {
		return startButton;
	}

	public void setStartButton(JButton startButton) {
		this.startButton = startButton;
	}

	public void setStart_button(JButton start_button) {
		this.startButton = start_button;
	}

	public JLabel getLabel1() {
		return label1;
	}

	public void setLabel1(JLabel label1) {
		this.label1 = label1;
	}

	public JLabel getLabel2() {
		return label2;
	}

	public void setLabel2(JLabel label2) {
		this.label2 = label2;
	}

	public JTextField getField1() {
		return field1;
	}

	public void setField1(JTextField field1) {
		this.field1 = field1;
	}

	public JTextField getField2() {
		return field2;
	}

	public void setField2(JTextField field2) {
		this.field2 = field2;
	}

	public StartView() {
		setSize(new Dimension(1000, 600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		ImageIcon img = new ImageIcon("Marvel-logo.png");
		setTitle("Marvel");
		setIconImage(img.getImage());
		setLayout(null);
		setLocation(120, 50);
		
		ImageIcon background=new ImageIcon("M2.jpg");
	    Image imag=background.getImage();
	    
		 this.setContentPane(new JPanel() {
	         @Override
	         public void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(imag, 0, 0, null);
	            repaint();
	            revalidate();
	         }
	      });
		 
		setLayout(null);
		label1 = new JLabel();
		label1.setText("First Player");
		label1.setFont(new Font("FiraCode", Font.BOLD, 20));
		label1.setForeground(Color.WHITE);
		label1.setBounds(400, 400, 200, 30);
		getContentPane().add(label1);

		field1 = new JTextField();
		field1.setBounds(550, 400, 100, 30);
		field1.setPreferredSize(new Dimension(100,30));
		getContentPane().add(field1);
		
		label2 = new JLabel();
		label2.setText("Second Player");
		label2.setFont(new Font("Fira Code", Font.BOLD, 20));
		label2.setForeground(Color.WHITE);
		label2.setBounds(400, 450, 200, 30);
		getContentPane().add(label2);
		
		
		
		field2 = new JTextField();
		field2.setBounds(550, 450, 100, 30);
		field2.setPreferredSize(new Dimension(100,30));
		getContentPane().add(field2);
		
		
		startButton = new JButton();
		startButton.setText("START");
		startButton.setBounds(500, 500, 75, 50);
		startButton.setOpaque(false);
		getContentPane().add(startButton);

		setVisible(true);
		repaint();
		revalidate();
	}

	public static void main(String[] args) {
		StartView frame = new StartView();
	    
	    frame.revalidate();
	    frame.repaint();

	}

}
