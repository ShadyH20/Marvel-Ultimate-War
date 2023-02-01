package GameGUI;

import GameView.StartView;
import GameView.View;
import engine.Game;
import engine.Player;
import model.world.Champion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;


public class Control {
	
	private boolean first_turn;
	private boolean second_turn;
	private int c1; //  number of champions selected by first player
	private int c2; //  number of champions selected by second player
	private View view;
	private StartView start;
	private Game game;
	private Player first;
	private Player second;
	private boolean chooseLeader = false;
	public Control() {
		
		// Initially
		first_turn = true;
		second_turn = false;
		// Start View
		start = new StartView();
		Start();
	
		
		
	}
	
	public static void main(String[]args) {
		new Control();
	}
	
	// Listeners
	public void Start() {
		
		start.getStart_button().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String s1 = start.getField1().getText();
				String s2 = start.getField2().getText();
				if(s1.length()==0||s2.length()==0) {
					return;
				}
				else {
					first = new Player(s1);
					second = new Player(s2);
					game = new Game(first,second);
					start.dispose();
					view = new View();
					view.getCrd().next(view.getContentPane());
					game = new Game(first,second);
					// Load Abilities to the Game
					try {
					Game.loadAbilities("Abilities.csv");
					// Load Champions to the Game
					Game.loadChampions("Champions.csv");
					// Place Icons
					placeIcons();
					addDisplayMouseListener();
					
					}
					catch(IOException ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
		});
	}
	
	public void placeIcons() {

		ArrayList<Champion> a = Game.getAvailableChampions();
		int index = 0;
		for(Champion c : a ) {
					JButton button = view.getCs().getList().get(index) ;
					button.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
					button.setName(c.getName());
					String name = c.getName();
				
				switch(name) {
					case "Captain America":  button.setIcon(new ImageIcon("Captian_America.jpg"));  break;
					case "Deadpool" : button.setIcon(new ImageIcon("Deadpool.jpg"));break;
					case "Dr Strange" : button.setIcon(new ImageIcon("DrStrange.jpg"));break;
					case "Electro" : button.setIcon(new ImageIcon("electro.jpg")); break;
					case "Ghost Rider" : button.setIcon(new ImageIcon("Ghostrider.jpg")); break;
					case "Hela" : button.setIcon(new ImageIcon("Hela.jpg")); break;
					case "Hulk" : button.setIcon(new ImageIcon("Hulk.jpg")); break;
					case "Ironman" : button.setIcon(new ImageIcon("Ironman.jpg")); break;
					case "Iceman" : button.setIcon(new ImageIcon("Iceman.jpg")); break;
					case "Loki" :  button.setIcon(new ImageIcon("loki.jpg"));break;
					case "Quicksilver" :  button.setIcon(new ImageIcon("Quicksilver.jpg"));break;
					case "Spiderman" : button.setIcon(new ImageIcon("Spiderman.jpg")); break;
					case "Thor" : button.setIcon(new ImageIcon("Thor.jpg")); break;
					case "Venom" :  button.setIcon(new ImageIcon("Venom.jpg"));break;
					case "Yellow Jacket": button.setIcon(new ImageIcon("Yellowjacket.jpg"));break;
				}
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
						view.getCs().getPhoto().setIcon(new ImageIcon("Back.jpg"));
						view.getCs().getData().setIcon(new ImageIcon("Back.jpg"));
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						JButton button = (JButton)e.getSource();
						String name = button.getName();
						JLabel label1 = view.getCs().getPhoto();
						JLabel label2 = view.getCs().getData();
						
					switch(name) {
						case "Captain America":  label1.setIcon(new ImageIcon("Captain America1.jpg"));  break;
						case "Deadpool" :label1.setIcon(new ImageIcon("Deadpool1.jpg"));break;
						case "Dr Strange" : label1.setIcon(new ImageIcon("Dr Strange1.jpg"));break;
						case "Electro" : label1.setIcon(new ImageIcon("Electro1.jpg")); break;
						case "Ghost Rider" : label1.setIcon(new ImageIcon("Ghost Rider1.jpg")); break;
						case "Hela" : label1.setIcon(new ImageIcon("Hela1.jpg")); break;
						case "Hulk" : label1.setIcon(new ImageIcon("Hulk1.jpg")); break;
						case "Ironman" : label1.setIcon(new ImageIcon("Ironman1.jpg")); break;
						case "Iceman" : label1.setIcon(new ImageIcon("Iceman1.jpg")); break;
						case "Loki" :  label1.setIcon(new ImageIcon("Loki1.jpg"));break;
						case "Quicksilver" :  label1.setIcon(new ImageIcon("Quicksilver1.jpg"));break;
						case "Spiderman" : label1.setIcon(new ImageIcon("Spiderman1.jpg")); break;
						case "Thor" : label1.setIcon(new ImageIcon("Thor1.jpg")); break;
						case "Venom" :  label1.setIcon(new ImageIcon("Venom1.jpg"));break;
						case "Yellow Jacket": label1.setIcon(new ImageIcon("Yellow Jacket1.jpg"));break;
					}
					
					switch(name) {
						case "Captain America":  label2.setIcon(new ImageIcon("Deadpool2.jpg"));  break;
						case "Deadpool" :label2.setIcon(new ImageIcon("Deadpool2.jpg"));break;
						case "Dr Strange" : label2.setIcon(new ImageIcon("Deadpool2.jpg"));break;
						case "Electro" : label2.setIcon(new ImageIcon("Deadpool2.jpg")); break;
						case "Ghost Rider" : label2.setIcon(new ImageIcon("Deadpool2.jpg")); break;
						case "Hela" : label2.setIcon(new ImageIcon("Deadpool2.jpg")); break;
						case "Hulk" : label2.setIcon(new ImageIcon("Deadpool2.jpg")); break;
						case "Ironman" : label2.setIcon(new ImageIcon("Deadpool2.jpg")); break;
						case "Iceman" : label2.setIcon(new ImageIcon("Deadpool2.jpg")); break;
						case "Loki" :  label2.setIcon(new ImageIcon("Deadpool2.jpg"));break;
						case "Quicksilver" :  label2.setIcon(new ImageIcon("Deadpool2.jpg"));break;
						case "Spiderman" : label2.setIcon(new ImageIcon("Deadpool2.jpg")); break;
						case "Thor" : label2.setIcon(new ImageIcon("Deadpool2.jpg")); break;
						case "Venom" :  label2.setIcon(new ImageIcon("Deadpool2.jpg"));break;
						case "Yellow Jacket": label2.setIcon(new ImageIcon("Deadpool2.jpg"));break;
				}
					
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						
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
						if(first.getTeam().contains(champ)&&!chooseLeader) {
							chooseLeader = false;
							view.getCs().getLeader().setEnabled(false);
							view.getCs().getNext().setEnabled(false);
							first_turn = true;
							second_turn = false;
							System.out.println("balabixo");

							if(button==view.getCs().getC1()) {
								JButton b = new JButton();
								b.setBounds(100, 75, 70, 70);
									view.getCs().setC1(b);
							}
							else if(button==view.getCs().getC2()) {
								JButton b = new JButton();
								b.setBounds(300, 75, 70, 70);
								view.getCs().setC2(b);
							}
							else if(button==view.getCs().getC3()) {
								JButton b = new JButton();
								b.setBounds(500, 75, 70, 70);
								view.getCs().setC3(b);
							}
							view.getCs().getTop().add(button);
							view.getCs().getLists().add(button);
							first.getTeam().remove(champ);
							view.revalidate();
							view.repaint();
						}
						
						else if(second.getTeam().contains(champ)&&!chooseLeader) {
							chooseLeader = false;
							view.getCs().getLeader().setEnabled(false);
							view.getCs().getNext().setEnabled(false);
							first_turn = false;
							second_turn = true;
							if(button==view.getCs().getC4()) {
								JButton b = new JButton();
								b.setBounds(100, 75, 70, 70);
								view.getCs().setC4(b);
							}
							else if(button==view.getCs().getC5()) {
								JButton b = new JButton();
								b.setBounds(300, 75, 70, 70);
								view.getCs().setC5(b);
							}
							else if(button==view.getCs().getC6()) {
								JButton b = new JButton();
								b.setBounds(500, 75, 70, 70);
								view.getCs().setC6(b);
							}
							view.getCs().getTop().add(button);
							view.getCs().getLists().add(button);
							second.getTeam().remove(champ);
							view.revalidate();
							view.repaint();
						}	
						
						else if(first_turn && first.getTeam().size()<3) {
							if(first.getTeam().size()==3&&second.getTeam().size()==3) {
								nextListener();
								view.getCs().getNext().setEnabled(true);
							}
							// Add Champion to the first player team
							
							if(second.getTeam().size()<3)
								first_turn = false;
								second_turn = true;

							if(c1.getIcon()==null) {
								button.setBounds(100, 75, 70, 70);
								view.getCs().setC1(button);
							}
							else if(c2.getIcon()==null) {
								button.setBounds(300, 75, 70, 70);
								view.getCs().setC2(button);
							}
							else if(c3.getIcon()==null) {
								button.setBounds(500, 75, 70, 70);
								view.getCs().setC3(button);
							}	
							view.getCs().getTop().remove(button);
							view.getCs().getList().remove(button);
							first.getTeam().add(champ);
							view.revalidate();
							view.repaint();

						}
						else if(second_turn && second.getTeam().size()<3){
							if(first.getTeam().size()==3&&second.getTeam().size()==3) {
								nextListener();
								view.getCs().getNext().setEnabled(true);
							}
							// Add Champion to the second player team
							if(first.getTeam().size()<3)
								first_turn = true;
								second_turn = false;
								if(c4.getIcon()==null) {
									button.setBounds(100, 75, 70, 70);
									view.getCs().setC4(button);
								}
								else if(c5.getIcon()==null) {
									button.setBounds(300, 75, 70, 70);
									view.getCs().setC5(button);
								}
								else if(c6.getIcon()==null) {
									button.setBounds(500, 75, 70, 70);
									view.getCs().setC6(button);
								}	
								view.getCs().getTop().remove(button);
								view.getCs().getList().remove(button);
								second.getTeam().add(champ);
							view.revalidate();
							view.repaint();
						}
						if(first.getTeam().size()==3&&second.getTeam().size()==3) {
							chooseLeaderListener();
							view.getCs().getLeader().setEnabled(true);
						}
						else if(first.getTeam().size()<3&&second.getTeam().size()==3) {
							first_turn = true;
							second_turn = false;
						}
						else if(first.getTeam().size()==3&&second.getTeam().size()<3) {
							first_turn = false;
							second_turn = true;
						}
						
						if(chooseLeader) {
							System.out.println(button.getName());
							for(Champion c:first.getTeam()) {
								if(c.getName().equals(button.getName())) {
									first.setLeader(c);
								}
							}
							for(Champion c:second.getTeam()) {
								if(c.getName().equals(button.getName())) {
									second.setLeader(c);
								}
							}
							if(first.getLeader()!=null&&second.getLeader()!=null) {
								view.getCs().getNext().setEnabled(true);
							}
						}
						String l1 = first.getLeader()==null?"null":first.getLeader().getName();
						String l2 =  second.getLeader()==null?"null":second.getLeader().getName();
						System.out.println("First Team : " + c1.getName() + " " + c2.getName() + " " + c3.getName() + l1 );
						System.out.println("second Team : " + c4.getName() + " " + c5.getName() + " " + c6.getName() + l2 );
						System.out.println(first.getTeam().size());
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
				view.getCrd().next(view.getContentPane());
				
			}
		});
	}

	public void chooseLeaderListener() {
		
		view.getCs().getLeader().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseLeader = true;
			}
		});
	}
}
