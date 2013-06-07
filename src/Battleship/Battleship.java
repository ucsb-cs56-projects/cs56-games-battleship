package Battleship;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
//import java.lang.Integer;
//import java.util.Vector;
import java.net.*;

public class Battleship extends JFrame
{		
	private static JButton ok = new JButton("OK"),//closes stats menu
						   done =new JButton("Done");//closes options menu
	private static JFrame statistics= new JFrame("Statistics"),//holds stats
						  options=new JFrame("Options");//holds opts
	private static JLabel data,//used for stats menu
						  title;//used for options menu
	private static JPanel stats=new JPanel(),//used for stats menu
						  opts,//used for options menu
						  inputpanel;//for manually inputting ships
	private static Container b,c,d;//board and input panel 
	private JPanel input;//input bar	
	private static JMenuItem m,pvp,pvc,cvc,pvpc;//menu items	
	private static String[] cletters = {" ","A","B","C","D","E","F","G","H","I","J"},
	//array of letters used for combo boxes
			    	 cnumbers = {" ","1","2","3","4","5","6","7","8","9","10"},
	//array of numbers used for combo boxes
					 ships = {"Carrier","Battleship","Submarine","Destroyer",
					 "Patrol Boat"},//strings used for ship combo box
					 direction = {"Horizontal","Vertical"},//directions
					 level={"Normal", "Ridiculously Hard"}, 
					 layout={"Manual","Automatic"},
					 colors={"Cyan", "Green", "Yellow", "Magenta", "Pink", "Red",
					 "White"},
					 first={"Player 1", "Player 2", "Random"};//used for options
	private JComboBox cshi = new JComboBox(ships),//ships
					  cdir = new JComboBox(direction);//directions
	private static JComboBox aiLevel=new JComboBox(level),
						     shipLayout=new JComboBox(layout),
							 shipColor=new JComboBox(colors),
							 playsFirst=new JComboBox(first);//used
					  			//for options menu
	private JTextField mbar = new JTextField();//message bar	
	private static int enemy=1,
				i,j,//counters							
				length=5,
				you=0,
				prevcolor=0,//index of previous color
				prevFirst=0,
				prevLayout=0,
				prevLevel=0,//tracks changes in corresponding comboboxes
				ready=0,
				sindex=0,//stores index of array
				dindex=0;//direction	
	private static Player players[]=new Player[2];
	private static JButton deploy=new JButton("GEGIN");
	private static int w=0,a=0,s=0,t=0,e=0;//counters to track the use of all ships
	private static String[][] shiphit=new String[10][10];
	private static String user,user2;
	private static Color[] color={Color.cyan,Color.green,Color.yellow,Color.magenta,
									Color.pink,	Color.red,	Color.white};		 	
	private static Object selectedValue=" ",
						  gametype;
	private static BattleshipClient me;
	private static boolean gameover=false;
        private static JCheckBoxMenuItem sound;
	
	public Battleship()
	{	
		setTitle("WELCOME TO BATTLESHIP!!!!");		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setJMenuBar(createMenuBar());
		setResizable(false);			
		
		//gets user to input name
//		user=JOptionPane.showInputDialog("Please Enter your name.");		
//		int dummy=0;
////		while (((user==null)||(user.equals("")))&&(dummy<3))
//                while ((user==null)||(user.equals("")))
//		{				
//			user=JOptionPane.showInputDialog("I won't play with you until I have your name.");
//			if ((user!=null)&&(!user.equals("")))
//				dummy=4;
//			else
//				dummy++;
//		}
//		if (dummy==3)
//		{
//			JOptionPane.showMessageDialog(null,"Since you're having trouble inp"
//			+"utting your name, I'll just call you stupid.","",JOptionPane.INFORMATION_MESSAGE);
//			user="Stupid";
//		}
                user="Human Being";
		players[you]=new Player (user);
		players[enemy]=new Player ("Computer");						
		b=getContentPane();		
		b.add(setBoard(you),BorderLayout.CENTER);			
		c=getContentPane();
		d = getContentPane();
		inputpanel=shipinput();
		d.add(inputpanel,BorderLayout.NORTH);			
		pack();		
		setVisible(true);
		
	}	
	public static boolean soundOn()
        {	return sound.isSelected();	}
        
	public static boolean getGameOver()
	{
	 	return gameover;	  
	}

	public static void setGameOver(boolean b)
	{
	 	gameover=b;	  
	}
	
	//method to determine who plays first
	public void whoGoesFirst()
	{
		int x=0;
		if (playsFirst.getSelectedIndex()!=2)
		{
			if (playsFirst.getSelectedIndex()!=you)
				flipYou();	
			players[playsFirst.getSelectedIndex()].getTimer().start();
			x=playsFirst.getSelectedIndex();
		}
		else
		{		
			int rand=(int)(Math.random()*2);					
			JOptionPane.showMessageDialog(null,players[rand].getUser()+" will "
			+"go first.","",JOptionPane.PLAIN_MESSAGE);
			if (rand!=you)
				flipYou();	
			players[rand].getTimer().start();
			x=rand;
		}
		if
		((!players[x].getUser().equals("Computer"))||(!players[x].getUser().equals("CPU1"))||(!players[x].getUser().equals("CPU2")))
			players[x].setMove(true);
	}
	
	//returns ship color, as selected by the user
	public static Color getColor()
	{			
		return (color[shipColor.getSelectedIndex()]);	
	}
	
	//asks if two players are playing on the same computer or over the web
	public static boolean isLocal()
	{
		if ((gametype==pvp)&&(selectedValue.equals("Local")))
				return true;
		else
                    return false;
	}
	
	
	public static void flipYou()
	{
		if (you==1)
		{	
			you=0;
			enemy=1;
		}
		else
		{
			you=1;
			enemy=0;
		}	
	}
	
	//determines whether or not is shipLayout is set to automatic
	public static boolean isAutoSet()
	{
		if (shipLayout.getSelectedIndex()==0)
			return false;
		else
			return true;
	}
	
	
	//variable that determines whether or not a carrier has been placed
	public static int getW()
	{
		return w;	
	}
	
	//variable that determines whether or not a battleship has been placed
	public static int getA()
	{
		return a;	
	}
	
	//variable that determines whether or not a submarine has been placed
	public static int getS()
	{
		return s;	
	}
	
	//variable that determines whether or not a destroyer has been placed
	public static int getT()
	{
		return t;	
	}
	
	//variable that determines whether or not a patrol boat has been placed
	public static int getE()
	{
		return e;	
	}		
	
	public static int getReady()
	{
		return ready;	
	}
	
	public static JFrame getStatistics()
	{
		return statistics;	
	}
	
	public static void setData(JLabel x)
	{
		data=x;	
	}
	
	public static JLabel getData()
	{
		return data;	
	}
	
	public static JPanel getStats()
	{
		return stats;	
	}	
	
	public static void setDeploy(boolean k)
	{
		deploy.setEnabled(k);	
	}	
	
	public static Player getPlayers(int x)
	{
		return players[x];	
	}
	
	public static String getDirection(int i)
	{
		return direction[i];	
	}
	
	public static String getCletters(int i)
	{
		return cletters[i];	
	}	
	
	public static String getShips(int i)
	{
		return ships[i];	
	}
	
	public static String getCnumbers(int i)
	{
		return cnumbers[i];	
	}	
	
	public static int getSIndex()
	{
		return sindex;	
	}
	
	public static int getDIndex()
	{
		return dindex;	
	}	
	
	public static int getYou()
	{
		return you;	
	}
	
	public static int getEnemy()
	{
		return enemy;	
	}	
	
	public static void setYou(int x)
	{
		you=x;	
	}
	
	public static void setEnemy(int x)
	{
		enemy=x;	
	}
	
	//creates Game menu and submenus
	public JMenuBar createMenuBar()
	{
		JMenu menu;//menu
      
		// create the menu bar
		JMenuBar menuBar = new JMenuBar();

		// build the Game menu
		menu = new JMenu("Menu");
		menuBar.add(menu);
		m = new JMenu("New Game");		
		//submenu of New Game
		GameListener stuff = new GameListener();
		pvp = new JMenuItem("HOST A GAME",'H');		
		pvp.addActionListener(stuff);
		m.add(pvp);
                
                pvpc = new JMenuItem("JOIN A GAME",'J');
		pvpc.addActionListener(stuff);
                m.add(pvpc);
                
		pvc = new JMenuItem("PLAY AGAINST A COMPUTER",'P');
		pvc.addActionListener(stuff);
                m.add(pvc);
		menu.add(m);
		
		//m.add(pvc);
//		cvc = new JMenuItem("Computer vs. Computer");
//		cvc.addActionListener(stuff);
//		m.add(cvc);
		
//		m = new JMenuItem("Rules");
//		m.addActionListener(new RulesListener());
//		menu.add(m);
//		m = new JMenuItem("Statistics");
//		m.addActionListener(new StatsListener());		
//		menu.add(m);
//		m = new JMenuItem("Options");
//		m.addActionListener(new OptionsListener());		
//		menu.add(m);
		m = new JMenuItem("Exit");
		m.addActionListener(new ExitListener());
		menu.add(m);	
                
                
                sound = new JCheckBoxMenuItem("Sound");
		sound.addActionListener(new SoundListener());
		sound.setSelected(true);
		menu.add(sound);
                
		return menuBar;
	}
	
	//creates panels that used to place ships
	public JPanel shipinput()
	{
		input= new JPanel();
		mbar.setText("Select a ship, its front position and direction.");
		mbar.setFont(new Font("Courier New", Font.BOLD, 14));
		mbar.setEditable(false);
		//input.add(mbar);
		cshi.setSelectedIndex(0);	
		cshi.addActionListener(new ShipsListener());
		TitledBorder title;//used for titles around combo boxes
		title = BorderFactory.createTitledBorder("Ships");
		cshi.setBorder(title);	
		input.add(cshi);		
		cdir.setSelectedIndex(0);	
		cdir.addActionListener(new DirectListener());	
		input.add(cdir);
		title = BorderFactory.createTitledBorder("Direction");
		cdir.setBorder(title);		
		deploy.setEnabled(false);
		deploy.addActionListener(new DeployListener());
		input.add(deploy);//deploy is the key of Begin
		return input;
	}	
	
	//creates board for manual ship placement
	public JPanel setBoard(int n)
	{
		players[n].setMyBoard(new JPanel(new GridLayout(11,11)));//panel to store board		
		JTextField k;		
		for (i=0;i<11;i++)
		{			
			for (j=0;j<11;j++)
			{
				if ((j!=0)&&(i!=0))
				{					
					players[n].getBboard(i-1,j-1).addActionListener(new BoardListener());
					players[n].getMyBoard().add(players[n].getBboard(i-1,j-1));
				}				
				if (i==0)
				{				
					if (j!=0)
					{	
						//used to display row of numbers
						k= new JTextField(Battleship.getCnumbers(j));
						k.setEditable(false);
						k.setHorizontalAlignment((int)JFrame.CENTER_ALIGNMENT); 
					}									
					else 
					{	
						//used to display column of numbers
						k= new JTextField();
						k.setEditable(false);						
					}
					players[n].getMyBoard().add(k);
				}
				else if (j==0)					
				{	
					k= new JTextField(Battleship.getCletters(i));	
					k.setEditable(false);
					k.setHorizontalAlignment((int)JFrame.CENTER_ALIGNMENT); 
					players[n].getMyBoard().add(k);
				}				
			}
		}
		return players[n].getMyBoard();		
	}
	
	//creates board and automatically places ship
	public JPanel autoBoard(int u,int t) 
	{
		players[u].setGBoard(new JPanel(new GridLayout(11,11)));//panel to store board		
		JTextField k;	
		if (!players[u].getUser().equals("Unknown"))
			for (i=0;i<5;i++)
			{				
				players[u].setBoats(i,players[u].getBoats(i).compinput(i,u));
			}		
		for (i=0;i<11;i++)
		{			
			for (j=0;j<11;j++)
			{
				if ((j!=0)&&(i!=0))
				{								
					if ((players[u].getUser().equals("Computer"))||(isLocal()))						
					{						
						players[u].getBboard(i-1,j-1).addActionListener(new AttackListener());									
					}						
					else if
						((players[t].getUser().equals("Computer"))||(players[t].getUser().equals("CPU1"))||(players[t].getUser().equals("CPU2"))||(players[t].getUser().equals("Unknown")))			
					{
						if (players[u].getHitOrMiss(i-1,j-1))
							players[u].setBboard(i-1,j-1,getColor());						
					}
					else
					{
						players[u].getBboard(i-1,j-1).addActionListener(new InternetListener());		
					}
					players[u].getGBoard().add(players[u].getBboard(i-1,j-1));
				}				
				if (i==0)
				{				
					if (j!=0)
					{	
						//used to display row of numbers
						k= new JTextField(Battleship.getCnumbers(j));
						k.setEditable(false);
						k.setHorizontalAlignment((int)JFrame.CENTER_ALIGNMENT); 
					}									
					else 
					{	
						//used to display column of numbers
						k= new JTextField();
						k.setEditable(false);						
					}
					players[u].getGBoard().add(k);
				}
				else if (j==0)					
				{	
					k= new JTextField(Battleship.getCletters(i));	
					k.setEditable(false);
					k.setHorizontalAlignment((int)JFrame.CENTER_ALIGNMENT); 
					players[u].getGBoard().add(k);
				}				
			}			
		}
		return players[u].getGBoard();		
	}
	
	//Listener for combo boxes used to layout ships 
	private class ShipsListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent v)
		{				
			sindex=cshi.getSelectedIndex();
			if (players[you].getBoats(sindex)!=null)
				cdir.setSelectedIndex(players[you].getBoats(sindex).getDirect());
			switch (sindex)
			{
				case 0:		length=5;
				break;
				case 1:		length=4;
				break;
				case 2:		length=3;	
				break;
				case 3:		length=3;
				break;
				case 4:		length=2;
				break;							
			}
			if (players[you].getBoats(sindex) != null)
			{
				Ship boat=new Ship(ships[sindex],players[you].getBoats(sindex).getDirect()
				,length,players[you].getBoats(sindex).getX(),players[you].getBoats(sindex).getY());		
				players[you].getBoats(sindex).clearship();
				players[you].setBoats(sindex,boat);
                                players[you].paintShip(boat);
				players[you].getBoats(sindex).placeship();			
			}							
		}
	}			
			
	//Listener for the Direction combo box		
	private class DirectListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent v)
		{						
			dindex = cdir.getSelectedIndex();					
			if (players[you].getBoats(sindex) != null)
			{
				Ship boat=new Ship(ships[sindex],dindex,players[you].getBoats(sindex).getLength(),
			   	players[you].getBoats(sindex).getX(),players[you].getBoats(sindex).getY());		   
				players[you].getBoats(sindex).clearship();
				players[you].setBoats(sindex,boat);
				players[you].getBoats(sindex).placeship();		   
			}						
		}
	}				
	
	//Listener for the buttons on the board		
	private class BoardListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent v)
		{				
			if (ready==0)
			{
				if (players[you].getBoats(sindex)!=null)
					players[you].getBoats(sindex).clearship();
				Object source = v.getSource();
				outer:						
				for (i=0;i<10;i++)
				{				
					for (j=0;j<10;j++)
					{
						if (source==players[you].getBboard(i,j))
						{						
							switch (sindex)
							{
								case 0:	{											
											if (w==0)
												w++;														
										}
								break;						
								case 1:	{											
											if (a==0)
												a++;														
										}
								break;
								case 2:	{								
											if (s==0)								
												s++;
										}
								break;
								case 3:	{									
											if (t==0)
												t++;													
										}
							break;
							case 4:	{								
										if (e==0)
											e++;															
									}
							break;							
						}	
						players[you].setBoats(sindex,new Ship(ships[sindex],dindex,length,i,j));//display ship's image here																									
						break outer;						
					}					
				}
			}			
			players[you].getBoats(sindex).placeship();
			}						
		}
    }
	
	//creates a panel that tells whose board is which
	private JPanel whoseBoard()
	{
		JPanel panel=new JPanel(new BorderLayout());
		panel.add(new JLabel(players[you].getUser()+"'s Board",SwingConstants.LEFT),BorderLayout.WEST);
		panel.add(new JLabel(players[enemy].getUser()+"'s Board",SwingConstants.RIGHT),BorderLayout.EAST);
		return panel;
	}
	
	//Listener for exit choice on Game menu	
	private class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int r= JOptionPane.showConfirmDialog(null,"Are you sure you would l"
			+"ike to exit Battleship?", "Exit?", JOptionPane.YES_NO_OPTION);
			if (r==0)
				System.exit(0);	
		}	
	}

        //Listener for sound setup on Game menu	
	private class SoundListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
		if (!sound.isSelected())	
                {
                    int r= JOptionPane.showConfirmDialog(null,"Are you sure you would l"
			+"ike to shut sound down?", "Shut sound down?", JOptionPane.YES_NO_OPTION);
			if (r==0)
				sound.setSelected(false); //System.exit(0);
                        
		}
                else
                {
                int r= JOptionPane.showConfirmDialog(null,"Are you sure you would l"
			+"ike to open sound down?", "open sound ?", JOptionPane.YES_NO_OPTION);
			if (r==0)
				sound.setSelected(true); //System.exit(0);
                       
                }
                }
	}
        
        
	//listener for New Game submenu		
	private class GameListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent e)
		{	
			int q= JOptionPane.showConfirmDialog(null,"Are you sure you would l"
			+"ike to start a new game?", "New Game?", JOptionPane.YES_NO_OPTION);
			if (q==0)
			{					
				//resets variables
				b.removeAll();
				c.removeAll();
				d.removeAll();				
				you=0;
				enemy=1;
				ready=0;
				
				if (players[you].getTimer()!=null)
					if (players[you].getTimer().isRunning())
						players[you].getTimer().stop();
				if (players[enemy].getTimer()!=null)
					if (players[enemy].getTimer().isRunning())
						players[enemy].getTimer().stop();									
				
				gametype = e.getSource();			
			
				if (gametype==pvp)
				{
                                     Player player1 = new Player("host");
	    
	    ServerSocket serverSocket = null;
	    try {
		serverSocket = new ServerSocket(22222);
	    }
	    catch (IOException e2) {
		System.out.println("Could not listen on port: 22222");
		System.exit(-1);
	    }
             try {
		System.out.println("Listening for player connect on " + InetAddress.getLocalHost().getHostAddress()+":"+serverSocket.getLocalPort());
	    }
	    catch (UnknownHostException e1) {
		System.out.println("Unknown host exception: " + e);
		System.exit(-1);
	    }
	    Socket player2Socket = null;
	    try {
		player2Socket = serverSocket.accept();
	    }
	    catch (IOException e1) {
		System.out.println("Accept failed on port 22222");
		System.exit(-1);
	    }
	    
	    System.out.println("Connection receieved from " + player2Socket.getLocalAddress());
	    
	    PrintWriter toPlayer2 = null;
	    BufferedReader fromPlayer2 = null;
	    
	    try {
		toPlayer2 = new PrintWriter(player2Socket.getOutputStream(), true);
		fromPlayer2 = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
	    }
	    catch (IOException e1) {
		System.out.println("Error getting input/output streams from Player 2");
		System.exit(-1);
	    }

//	    while(true) {
//		player1.printGameBoard();
//		try {
//
//		    int p1Move = player1.requestMove();
//		    while(p1Move == -1)
//			p1Move = player1.requestMove();
//
//		    String readableLocation = "" + (char)(p1Move/10+65) + p1Move%10;
//		    // send player1 move to player 2
//		    System.out.println("Firing missiles at: " + readableLocation);
//		    toPlayer2.println(p1Move);
//
//		    // get status from player 2
//		    String p1Status = fromPlayer2.readLine();
//		    
//		    System.out.println(p1Status);
//		    player1.updateGuessGrid(p1Move, p1Status);
//		    player1.printGameBoard();
//
//		    if(player1.hasWon()) {
//			toPlayer2.println("WIN");
//			System.out.println("CONGRATULATIONS, YOU WIN!");
//			break;
//		    }
//		    else {
//			toPlayer2.println("CONTINUE");
//		    }
//
//		    // get player 2 move
//		    System.out.println("Waiting for player 2's move...");
//		    int p2Move = Integer.parseInt(fromPlayer2.readLine());
//
//		    readableLocation = "" + (char)(p2Move/10+65) + p2Move%10;
//		    System.out.println("PLAYER2 HAS FIRED MISSILES AT " + readableLocation);
//
//		    String p2Status = player1.incomingMissile(p2Move);
//		    System.out.println(p2Status);
//
//		    toPlayer2.println(p2Status);
//		
//		    String p2victoryStatus = fromPlayer2.readLine();
//		
//		    if(p2victoryStatus.equals("WIN")) {
//			System.out.println("OH NO, YOU LOSE!");
//			break;
//		    }
//		}
//		catch (IOException e1) {
//		    System.out.println("SOMETHING WENT WRONG WITH READING THINGS N SUCH, SORRY");
//		    System.exit(-1);
//		}
//	    }                       
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
					if (!selectedValue.equals("no server"))
					{
						String[] possibleValues = { "Local"};
						selectedValue = JOptionPane.showInputDialog(null, 
						"Choose one", "Input", JOptionPane.INFORMATION_MESSAGE, null,
						possibleValues, possibleValues[0]);
					}
					if (!players[you].getUser().equals("CPU1"))
					{
						if (players[you].getUser().equals("Stupid"))
						{
							int w=JOptionPane.showConfirmDialog(null,"Would you"
							+" like to try inputting your name again?","",
							JOptionPane.YES_NO_OPTION);
							if (w==JOptionPane.YES_OPTION)
							{	
								user=JOptionPane.showInputDialog("Enter your name.");
								int dummy=0;
								while (((user==null)||(user.equals("")))&&(dummy<3))
								{				
									user=JOptionPane.showInputDialog("You have to input something.");
									if ((user!=null)&&(!user.equals("")))
										dummy=4;
									else
										dummy++;
								}
								if (dummy==3)
								{
									JOptionPane.showMessageDialog(null,"Still a"
									+"cting stupid.  Oh well, we'll run with it."
									,"",JOptionPane.INFORMATION_MESSAGE);
									user="Stupid";
								}
								else
									JOptionPane.showMessageDialog(null,"That wasn't"
									+" so hard now, was it?","YEAH!",
									JOptionPane.INFORMATION_MESSAGE);									
							}							
						}
						players[you]=new Player (players[you].getUser());
					}
					else									
						players[you]=new Player (user);								
					if (selectedValue.equals("Online"))
					{
						players[enemy]=new Player ("Unknown");
						if (!isAutoSet())
						{
							b.add(setBoard(you),BorderLayout.CENTER);							
							deploy.setEnabled(false);
							d.add(inputpanel,BorderLayout.NORTH);					
						}
						else
						{
							b.add(autoBoard(you,enemy),BorderLayout.WEST);																				
							c.add(autoBoard(enemy,you),BorderLayout.EAST);
							ready=1;																
						}					
					}
					else
					{
						//gets user to input name
						if((players[enemy].getUser().equals("Computer"))||(players[enemy].getUser().equals("CPU2"))||(players[enemy].getUser().equals("Unknown")))
						{							
							user2=JOptionPane.showInputDialog("Enter your name.");					
							while ((user2==null)||(user2.equals("")))
							{				
								user2=JOptionPane.showInputDialog("You have to input something.");							
							}						
						}
						else
							user2=players[enemy].getUser();
						players[enemy]=new Player (user2);	
						b.add(autoBoard(you,enemy),BorderLayout.WEST);																				
						c.add(autoBoard(enemy,you),BorderLayout.EAST);
						d.add(whoseBoard(),BorderLayout.NORTH);						
						whoGoesFirst();
						ready=1;
					}
					//ready=1;
				}
				else 
                                if (gametype==pvc)//Player vs Computer
				{						
					if (!players[you].getUser().equals("CPU1"))
					{
						if (players[you].getUser().equals("Stupid"))
						{
							int w=JOptionPane.showConfirmDialog(null,"Would you"
							+" like to try inputting your name again?","",
							JOptionPane.YES_NO_OPTION);
							if (w==JOptionPane.YES_OPTION)
							{	
								user=JOptionPane.showInputDialog("Enter your name.");
								int dummy=0;
								while (((user==null)||(user.equals("")))&&(dummy<3))
								{				
									user=JOptionPane.showInputDialog("You have to input something.");
									if ((user!=null)&&(!user.equals("")))
										dummy=4;
									else
										dummy++;
								}
								if (dummy==3)
								{
									JOptionPane.showMessageDialog(null,"Still a"
									+"cting stupid.  Oh well, we'll run with it."
									,"",JOptionPane.INFORMATION_MESSAGE);
									user="Stupid";
								}
								else
									JOptionPane.showMessageDialog(null,"That wasn't"
									+" so hard now, was it?","YEAH!",
									JOptionPane.INFORMATION_MESSAGE);									
							}							
						}
						players[you]=new Player (players[you].getUser());
					}
					else									
						players[you]=new Player (user);								
					players[enemy]=new Player ("Computer");			
					if (!isAutoSet())
					{
						b.add(setBoard(you),BorderLayout.CENTER);							
						deploy.setEnabled(false);
						d.add(inputpanel,BorderLayout.NORTH);					
					}
					else
					{
						b.add(autoBoard(you,enemy),BorderLayout.WEST);																				
						c.add(autoBoard(enemy,you),BorderLayout.EAST);
						whoGoesFirst();	
					}
				}
				else if (gametype==cvc)//Computer vs Computer
				{										
					mbar.setText("Battleship Demo");					
					mbar.setEditable(false);					
					d.add(mbar,BorderLayout.NORTH);
					players[you]=new Player ("CPU1");
					players[enemy]=new Player ("CPU2");					
					b.add(autoBoard(you,enemy),BorderLayout.WEST);																				
					c.add(autoBoard(enemy,you),BorderLayout.EAST);					
					whoGoesFirst();		
				}
                                else if (gametype==pvpc)//Computer vs Computer
				{										
					Player player2 = new Player("client");
	    
	    System.out.println("ATTEMPT TO CONNECT TO WHICH IP/HOSTNAME?");
	    String connectTo = null;
	    try {
		connectTo = "127.0.0.1";
	    } 
	    catch (Exception ioe) {
		System.err.println("IO error trying to read your selection!" + ioe.getMessage());
		System.exit(1);
	    }
	    
	    Socket player1Socket = null;
	    try {
		player1Socket = new Socket(connectTo, 22222);
	    }
	    catch (UnknownHostException e1) {
		System.out.println("Don't know about host: " + connectTo);
		System.exit(-1);
	    }
	    catch (IOException e1) {
		System.out.println("IOException when connecting to host.");
		System.exit(-1);
	    }

	    System.out.println("Connected to " + player1Socket.getLocalAddress());
	    
	    PrintWriter toPlayer1 = null;
	    BufferedReader fromPlayer1 = null;
	    
	    try {
		toPlayer1 = new PrintWriter(player1Socket.getOutputStream(), true);
		fromPlayer1 = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
	    }
	    catch (IOException e1) {
		System.out.println("Error getting input/output streams from Player 1");
		System.exit(-1);
	    }
                                    
                                    
                                   
				}
				pack();		
				repaint();
			}									
		}	
	}	
	
	//Listener for Rules menu
	private class RulesListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent e)
		{	
			
		}	
	}
	
	
	//Listener for ok button in statistics menu
	private class OkListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent e)
		{	
			statistics.dispose();
		}	
	}
	
	//Listener for Stats menu
	private class StatsListener implements ActionListener
	{	
		//
		public void setup()
		{			
			stats=new JPanel();
			ok.addActionListener(new OkListener());		
			statistics.setSize(300,300);
			statistics.setResizable(false);		
			statistics.getContentPane().add(ok,BorderLayout.SOUTH);
			//statistics.setLocation(700,200);				
		}	
		
		public void actionPerformed(ActionEvent e)
		{				
			if (data==null)
				setup();
			else
				stats.removeAll();
			stats.setLayout(new GridLayout(6,3));					
			data=new JLabel("");
			stats.add(data);
			data=new JLabel("Player 1",SwingConstants.CENTER);
			stats.add(data);
			data=new JLabel("Player 2",SwingConstants.CENTER);
			stats.add(data);				
			data=new JLabel("Names");
			stats.add(data);
			if (you == 0)
			{								
				data=new JLabel(players[you].getUser(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(players[enemy].getUser(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Shots Taken");
				stats.add(data);
				data=new JLabel(Integer.toString(players[you].getShots()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(Integer.toString(players[enemy].getShots()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Hits");
				stats.add(data);
				data=new JLabel(Integer.toString(players[you].getHits()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(Integer.toString(players[enemy].getHits()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Shot Accuracy");
				stats.add(data);
				data=new JLabel(players[you].getAcc(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(players[enemy].getAcc(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Ships Left");
				stats.add(data);
				data=new JLabel(Integer.toString(players[you].getShipsLeft()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(Integer.toString(players[enemy].getShipsLeft()),SwingConstants.CENTER);
				stats.add(data);
			}		
			else 
			{					
				data=new JLabel(players[enemy].getUser(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(players[you].getUser(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Shots Taken");
				stats.add(data);
				data=new JLabel(Integer.toString(players[enemy].getShots()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(Integer.toString(players[you].getShots()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Hits");
				stats.add(data);
				data=new JLabel(Integer.toString(players[enemy].getHits()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(Integer.toString(players[you].getHits()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Shot Accuracy");
				stats.add(data);
				data=new JLabel(players[enemy].getAcc(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(players[you].getAcc(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Ships Left");
				stats.add(data);
				data=new JLabel(Integer.toString(players[enemy].getShipsLeft()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(Integer.toString(players[you].getShipsLeft()),SwingConstants.CENTER);
				stats.add(data);
			}
			statistics.getContentPane().add(stats);			
			statistics.pack();
			statistics.setVisible(true);			
		}	
	}
	
	//Listener for Deploy Button 
	private class DeployListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent v)
		{	
			int r= JOptionPane.showConfirmDialog(null,"Are you sure about current deployment?", "Begin to Battle?", 
			JOptionPane.YES_NO_OPTION);
			if (r==0)
			{	
				w=0;
				a=0;
				s=0;
				t=0;
				e=0;									
				d.remove(input);	
                                
                                //players[you].
				b.add(players[you].getMyBoard(),BorderLayout.WEST);//important 在此处添加
                                
				ready=1;	
				c.add(autoBoard(enemy,you),BorderLayout.EAST);													
				d.add(new JPanel(),BorderLayout.CENTER);
				if (!selectedValue.equals("Online"))
					whoGoesFirst();						
				pack();
				repaint();				//就在此处刷新 important						
			}
		}	
	}

	//Listener for Options menu
	public class OptionsListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent e)
		{		
			if (opts==null)
				setup();
			else
				options.setVisible(true);					
		}		
		
		public void setup()
		{			
			opts=new JPanel(new GridLayout(4,2));
			title=new JLabel("Computer AI");
			opts.add(title);			
			aiLevel.setSelectedIndex(0);			
			opts.add(aiLevel);
			title=new JLabel("Ship Layout");
			opts.add(title);			
			shipLayout.setSelectedIndex(0);			
			opts.add(shipLayout);
			title=new JLabel("Ship Color");
			opts.add(title);				
			shipColor.addActionListener(new SColorListener());
			shipColor.setSelectedIndex(0);	
			opts.add(shipColor);
			title=new JLabel("Who Plays First?");
			opts.add(title);			
			playsFirst.setSelectedIndex(0);			
			opts.add(playsFirst);		
			options.getContentPane().add(opts,BorderLayout.CENTER);
			//options.setSize(600,800);
			options.setResizable(false);
			done.addActionListener(new DoneListener());		
			options.getContentPane().add(done,BorderLayout.SOUTH);
			options.setLocation(200,200);
			options.pack();
			options.setVisible(true);		
		}
		
		//Listener for the Colors combo box		
		private class SColorListener implements ActionListener
		{	
			public void actionPerformed(ActionEvent v)
			{	
				for (i=0;i<10;i++)
					for (j=0;j<10;j++)
					{
						if (players[you].getBboard(i,j).getBackground()==color[prevcolor])
							players[you].setBboard(i,j,color[shipColor.getSelectedIndex()]);				
						if (players[enemy].getBboard(i,j).getBackground()
							==color[prevcolor])
                                                            players[enemy].setBboard(i,j,color[shipColor.getSelectedIndex()]);		
					}
				prevcolor=shipColor.getSelectedIndex();	
			}
		}	
		
		//Listener for ok button in statistics menu
		private class DoneListener implements ActionListener
		{	
			public void actionPerformed(ActionEvent e)
			{	
				if ((shipLayout.getSelectedIndex()!=prevLayout)||
					(aiLevel.getSelectedIndex()!=prevLevel)||
					(playsFirst.getSelectedIndex()!=prevFirst))
				{
					JOptionPane.showMessageDialog(null,"Changes will take"+
					" place at the start of a new game.",""
					,JOptionPane.PLAIN_MESSAGE);
					if (shipLayout.getSelectedIndex()!=prevLayout)
						prevLayout=shipLayout.getSelectedIndex();
					if (playsFirst.getSelectedIndex()!=prevFirst)
						prevFirst=playsFirst.getSelectedIndex();
					if (aiLevel.getSelectedIndex()!=prevLevel)
						prevLevel=aiLevel.getSelectedIndex();
				}
				options.dispose();
			}	
		}	
	}	
	
	public static BattleshipClient getClient()
	{
		return me;		
	}
	
	public static void main(String[] args){		
	
		Battleship gui= new Battleship();
		
		while (gui.isActive())
		{
			while (selectedValue.equals(" "))
				{	}
//			System.out.println("xenophobia");
			System.out.println("Object = "+selectedValue);
			if (selectedValue.equals("Online"))
			{	
				selectedValue=" ";
				while (ready!=1)
				{ }			
				
				try
				{
					me=new BattleshipClient();
					if (!me.getServerName().equals("invalid"))
					{
						me.sendShips();
						while (!gameover)
						{
							if (!players[you].getMove())	
							{
								try
								{
									me.listen();							
								}
								catch (IOException e){ System.out.println("Aw naw."); }					
							}
							while (players[you].getMove())
								{ }
							me.results();
						}								
					}
					else
					{
						b.removeAll();
						c.removeAll();
						d.removeAll();
						players[you]=new Player (user);
						players[enemy]=new Player ("Computer");					
						b.add(gui.setBoard(you),BorderLayout.CENTER);					
						inputpanel=gui.shipinput();
						d.add(inputpanel,BorderLayout.NORTH);			
						gui.pack();		
						gui.repaint();
					}					
				}					
				catch (IOException e)
				{ System.out.println("You Suck"); }
			}			
		}		//System.out.println("okay");		
	}
}	
