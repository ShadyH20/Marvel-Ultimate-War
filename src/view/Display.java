package view;

import view.game_scene.EastPnl;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Display extends JPanel {
	
	
	private ChampSelectListener listener;
	
	private ArrayList<ImageIcon> backgrounds = new ArrayList<ImageIcon>();

	private ImageShuffle imageshuffle1 = new ImageShuffle();
	private JTextArea properties = new JTextArea("");
	
	private JPanel left = new JPanel();
	private JPanel center = new JPanel();
	
	private JLabel first = new JLabel();
	private JButton c1 = new JButton();
	private JButton c2 = new JButton();
	private JButton c3 = new JButton();
	
	private JLabel second = new JLabel();
	private JButton c4 = new JButton();
	private JButton c5 = new JButton();
	private JButton c6 = new JButton();
	
	private ArrayList<JButton> lists = new ArrayList<>();
	
	private ArrayList<JButton> list = new ArrayList<>();
	
	private JButton next = new JButton("Battle");
	
	// Left Panel 
	private JPanel top = new JPanel();
	private JPanel bot = new JPanel();
	
	private JPanel l = new JPanel();
	private JPanel r = new JPanel();
	
	// Center Panel
	private JPanel f = new JPanel();
	private JPanel s = new JPanel();
	private JPanel c = new JPanel();
	
	private JLabel photo = new JLabel();
	private JLabel data = new JLabel();
	private JLabel card = new JLabel();
	private JPanel dataPnl;
	private ArrayList<EastPnl> dataPnls = new ArrayList<>();
	
	private JButton Leader = new JButton("Choose Leader");
	ImageIcon btnImage = new ImageIcon("./pics/button.png");


	public Display() {
		
		
		 imageshuffle1.addIcon(new ImageIcon("./pics/cs/iron.jpg"));
		 imageshuffle1.addIcon(new ImageIcon("./pics/cs/Hulk2.jpg"));
		 imageshuffle1.addIcon(new ImageIcon("./pics/cs/iron.jpg"));

		 backgrounds.add(new ImageIcon((new ImageIcon("./pics/cs/A3.jpg")).getImage().getScaledInstance(GameView.W/2,GameView.H,Image.SCALE_SMOOTH)));
		 backgrounds.add(new ImageIcon((new ImageIcon("./pics/cs/A1.jpg")).getImage().getScaledInstance(GameView.W/2,GameView.H,Image.SCALE_SMOOTH)));
		 
		 Leader.setVisible(false);
		 
///////////////////////////////////////////////////////////////////////////				 
		 // Display 
//		setPreferredSize(new Dimension(1000,600));
		setLayout(new GridLayout(0,2)); // 
//		setLocation(100, 50);
///////////////////////////////////////////////////////////////////////////		
		// Left Panel	
		ImageIcon icon1 = new ImageIcon((new ImageIcon("./pics/cs/Worldd.jpg")).getImage().getScaledInstance(GameView.W/2,GameView.H,Image.SCALE_SMOOTH));
		JLabel label = new JLabel();
		label.setIcon(icon1);
        r();
///////////////////////////////////////////////////////////////////////////				
		// Top Panel
		top.setBounds(0, (int) (0.08 * GameView.H), (int) (0.5 * GameView.W), (int) (0.32 * GameView.H));
//		top.setBorder(new LineBorder(Color.BLACK));
		top.setLayout(new FlowLayout());
		top.setOpaque(false);
///////////////////////////////////////////////////////////////////////////			
		// Bottom Panel
		ImageIcon icon = new ImageIcon("./pics/cs/Back.jpg");
		double ratio = 1.0*icon.getIconWidth()/icon.getIconHeight();
		photo.setIcon(new ImageIcon((new ImageIcon("./pics/cs/Back.jpg")).getImage().getScaledInstance((int) (ratio*GameView.H/2), (int) (GameView.H/2),Image.SCALE_SMOOTH)));
		data.setIcon(new ImageIcon((new ImageIcon("./pics/cs/Back.jpg")).getImage().getScaledInstance((int) (ratio*GameView.H/2), (int) (GameView.H/2),Image.SCALE_SMOOTH)));

		bot.setLayout(new BorderLayout());
		bot.setBounds(0, (int) (0.33*GameView.H), (int) (0.5 * GameView.W), (int) (0.6 * GameView.H));
		data.setOpaque(false);
		data.setBounds(0,(bot.getHeight()-data.getIcon().getIconHeight())/2,data.getIcon().getIconWidth(),data.getIcon().getIconHeight());
		dataPnl = new JPanel(null);
		dataPnl.setPreferredSize(new Dimension(data.getIcon().getIconWidth(),data.getIcon().getIconHeight()));
		dataPnl.add(data);
		dataPnl.setOpaque(false);
		bot.add(photo,BorderLayout.WEST);
		bot.add(dataPnl,BorderLayout.EAST);
		bot.add(card,BorderLayout.CENTER);
//		bot.setBorder(new LineBorder(Color.BLACK));
		bot.setOpaque(false);
///////////////////////////////////////////////////////////////////////////	
		// Add Buttons
		int count = 15;
		while(count>0){
			JButton button =  new JButton();
			button.setPreferredSize(new Dimension((int) (0.08 * GameView.H),(int) (0.08 * GameView.H)));
			top.add(button);
			list.add(button);
			count--;
		}
///////////////////////////////////////////////////////////////////////////			
		// Center Panel
		 center = new JPanel() {
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                g.drawImage(icon1.getImage(), 0, 0, null);
	            }
	        };
	    add(center);
		center.setPreferredSize(new Dimension((int) (0.5*GameView.W),GameView.H));
		center.setLayout(new GridLayout(3,0));
		center.setBackground(Color.BLACK);
///////////////////////////////////////////////////////////////////////////		
		// f panel
		center.add(f);
		f.setLayout(null);
		first.setText("First Player");
		first.setFont(GameView.gameFont2.deriveFont((float) (35.0*GameView.W/1500)));
		first.setBounds((int) (0.07 * center.getPreferredSize().width), -10*GameView.H/960, (int) (0.3 * GameView.W), (int) (0.18 * GameView.H));
		first.setPreferredSize(new Dimension((int) (0.3 * GameView.W),(int) (0.18 * GameView.H)));
		first.setForeground(Color.WHITE);
		c1.setBounds((int) (center.getPreferredSize().width/4 - 0.1*GameView.H/2), GameView.H/7, (int) (0.1 * GameView.H),(int) (0.1 * GameView.H));
		c2.setBounds((int) (center.getPreferredSize().width/2 - 0.1*GameView.H/2), GameView.H/7, (int) (0.1 * GameView.H),(int) (0.1 * GameView.H));
		c3.setBounds((int) (center.getPreferredSize().width*3/4 - 0.1*GameView.H/2), GameView.H/7, (int) (0.1 * GameView.H),(int) (0.1 * GameView.H));

		next.setBounds((int) (0.7*center.getPreferredSize().width), 30, 200, 60);
		next.setIcon(new ImageIcon(btnImage.getImage().getScaledInstance(next.getWidth(),next.getHeight(),Image.SCALE_SMOOTH)));
		next.setHorizontalTextPosition(SwingConstants.CENTER);
		next.setVerticalTextPosition(SwingConstants.CENTER);
		next.setFont(GameView.gameFontBold.deriveFont(30f));
		next.setForeground(Color.WHITE);
		next.setBorderPainted(false);
		next.setContentAreaFilled(false);
		next.setBorder(new LineBorder(Color.CYAN));
		next.setVisible(false);
		next.setFocusable(false);

		Leader.setBounds(300, 30, 200, 60);
		Leader.setFocusable(false);
		Leader.setIcon(new ImageIcon(btnImage.getImage().getScaledInstance(Leader.getWidth(),Leader.getHeight(),Image.SCALE_SMOOTH)));
		Leader.setHorizontalTextPosition(SwingConstants.CENTER);
		Leader.setVerticalTextPosition(SwingConstants.CENTER);
		Leader.setFont(GameView.gameFontBold.deriveFont(30f));
		Leader.setForeground(Color.WHITE);
		Leader.setBorderPainted(false);
		Leader.setContentAreaFilled(false);


		f.add(Leader);
		f.add(next);
		f.add(first);
		f.add(c1);
		f.add(c2);
		f.add(c3);
//////////////////////////////////////////////////////////////////////////
		// c panel
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                c.add(imageshuffle1);
            }
        });
     	center.add(c);
//		c.setPreferredSize(new Dimension(400,300));
///////////////////////////////////////////////////////////////////////////				
		// s panel
		center.add(s);
		s.setLayout(null);
		second.setText("Second Player");
		second.setFont(GameView.gameFont2.deriveFont((float) (35.0*GameView.W/1500)));
		second.setBounds((int) (0.07 * center.getPreferredSize().width), -40, (int) (0.3 * GameView.W), (int) (0.18 * GameView.H));
//		second.setPreferredSize(new Dimension(100,100));
		second.setForeground(Color.WHITE);
		c4.setBounds((int) (center.getPreferredSize().width/4 - 0.1*GameView.H/2), GameView.H/12, (int) (0.1 * GameView.H),(int) (0.1 * GameView.H));
		c5.setBounds((int) (center.getPreferredSize().width/2 - 0.1*GameView.H/2), GameView.H/12, (int) (0.1 * GameView.H),(int) (0.1 * GameView.H));
		c6.setBounds((int) (center.getPreferredSize().width*3/4 - 0.1*GameView.H/2), GameView.H/12, (int) (0.1 * GameView.H),(int) (0.1 * GameView.H));
		s.add(second);
		s.add(c4);
		s.add(c5);
		s.add(c6);
///////////////////////////////////////////////////////////////////////////		
		f.setOpaque(false);
		c.setOpaque(false);
		s.setOpaque(false);
///////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(listener!=null)
					listener.onChampSelectDone();
			}
		});
		revalidate();
		repaint();
	}

	public void setNames(String p1, String p2){
		first.setText(p1);
		second.setText(p2);
	}

	public void setListener(ChampSelectListener listener) {
		this.listener = listener;
	}

	public JButton getLeader() {
		return Leader;
	}



	public void setLeader(JButton leader) {
		Leader = leader;
	}



	public JLabel getCard() {
		return card;
	}

	public JPanel getDataPnl() {
		return dataPnl;
	}

	public void setCard(JLabel card) {
		this.card = card;
	}

	public ArrayList<EastPnl> getDataPnls() {
		return dataPnls;
	}

	public JTextArea getProperties() {
		return properties;
	}

	public void setProperties(JTextArea properties) {
		this.properties = properties;
	}

	public JPanel getLeft() {
		return left;
	}

	public void setLeft(JPanel left) {
		this.left = left;
	}

	public JPanel getCenter() {
		return center;
	}

	public void setCenter(JPanel center) {
		this.center = center;
	}

	public JLabel getFirst() {
		return first;
	}

	public void setFirst(JLabel first) {
		this.first = first;
	}

	public JButton getC1() {
		return c1;
	}

	public void setC1(JButton c1) {
		f.remove(this.c1);
		this.c1 = c1;
		this.c1.setBorder(BorderFactory.createLineBorder(Color.YELLOW,2));
		f.add(c1);
	}

	public JButton getC2() {
		return c2;
	}

	public void setC2(JButton c2) {
		f.remove(this.c2);
		this.c2 = c2;
		this.c2.setBorder(BorderFactory.createLineBorder(Color.YELLOW,2));
		f.add(c2);
	}

	public JButton getC3() {
		return c3;
	}

	public void setC3(JButton c3) {
		f.remove(this.c3);
		this.c3 = c3;
		this.c3.setBorder(BorderFactory.createLineBorder(Color.YELLOW,2));
		f.add(c3);
	}

	public JLabel getSecond() {
		return second;
	}

	public void setSecond(JLabel second) {
		this.second = second;
	}

	public JButton getC4() {
		return c4;
	}

	public void setC4(JButton c4) {
		s.remove(this.c4);
		this.c4 = c4;
		this.c4.setBorder(BorderFactory.createLineBorder(Color.YELLOW,2));
		s.add(c4);
	}

	public JButton getC5() {
		return c5;
		
	}

	public void setC5(JButton c5) {
		s.remove(this.c5);
		this.c5 = c5;
		this.c5.setBorder(BorderFactory.createLineBorder(Color.YELLOW,2));
		s.add(c5);
	}

	public JButton getC6() {
		return c6;
	}

	public void setC6(JButton c6) {
		s.remove(this.c6);
		this.c6 = c6;
		this.c6.setBorder(BorderFactory.createLineBorder(Color.YELLOW,2));
		s.add(c6);
	}

	public ArrayList<JButton> getLists() {
		return lists;
	}

	public void setLists(ArrayList<JButton> lists) {
		this.lists = lists;
	}

	public ArrayList<JButton> getList() {
		return list;
	}

	public void setList(ArrayList<JButton> list) {
		this.list = list;
	}

	public JButton getNext() {
		return next;
	}

	public void setNext(JButton next) {
		this.next = next;
	}

	public JPanel getTop() {
		return top;
	}

	public void setTop(JPanel top) {
		this.top = top;
	}

	public JPanel getBot() {
		return bot;
	}

	public void setBot(JPanel bot) {
		this.bot = bot;
	}

	public JPanel getF() {
		return f;
	}

	public void setF(JPanel f) {
		this.f = f;
	}

	public JPanel getS() {
		return s;
	}

	public void setS(JPanel s) {
		this.s = s;
	}

	public JPanel getC() {
		return c;
	}

	public void setC(JPanel c) {
		this.c = c;
	}
	public void r() {
		
		int index = (int)(Math.random()*1)+1;
		ImageIcon icon = backgrounds.get(index);

		left = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);     
	                g.drawImage(icon.getImage(), 0, 0, null);
	            }
	        };
	        
        add(left);    
    
		left.setLayout(null);
		left.setPreferredSize(new Dimension((int) (0.43 * GameView.W),GameView.H));
		left.setBorder(new LineBorder(Color.BLACK));	
		left.add(top,BorderLayout.NORTH);
		left.add(bot,BorderLayout.CENTER);
	}
	public static void  main(String[]args){
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(1200,600));
		frame.setSize(new Dimension(1536,900));
		frame.add(new Display());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public ArrayList<ImageIcon> getBackgrounds() {
		return backgrounds;
	}



	public void setBackgrounds(ArrayList<ImageIcon> backgrounds) {
		this.backgrounds = backgrounds;
	}



	public ImageShuffle getImageshuffle1() {
		return imageshuffle1;
	}



	public void setImageshuffle1(ImageShuffle imageshuffle1) {
		this.imageshuffle1 = imageshuffle1;
	}



	public JPanel getL() {
		return l;
	}



	public void setL(JPanel l) {
		this.l = l;
	}



	public JPanel getR() {
		return r;
	}



	public void setR(JPanel r) {
		this.r = r;
	}
	public JLabel getPhoto() {
		return photo;
	}



	public void setPhoto(JLabel photo) {
		this.photo = photo;
	}



	public JLabel getData() {
		return data;
	}



	public void setData(JLabel data) {
		this.data = data;
	}


}
