package GameView;

import view.Display;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame{
	
	private CardLayout crd = new CardLayout();
	private Display display = new Display();
//	private Board board = new Board();
	
	public View() {
		
		setSize(new Dimension(1200,600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		ImageIcon img = new ImageIcon("Marvel-logo.png");
		setTitle("Marvel");
		setIconImage(img.getImage());
		setLayout(null);
		setLocation(100, 50);
		setVisible(true);
		
		
		setLayout(crd);
		
		getContentPane().add(display, "Queue");
		
		crd.next(getContentPane());
		
	}

	public CardLayout getCrd() {
		return crd;
	}

	public void setCrd(CardLayout crd) {
		this.crd = crd;
	}

	public Display getCs() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public static void main(String[]args) {
		
		new View();
	}
	
	
}
