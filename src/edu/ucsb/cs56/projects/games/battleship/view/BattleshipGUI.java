package edu.ucsb.cs56.projects.games.battleship;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BattleshipGUI extends JFrame{

    private String difficulty;
    private int gameType;
	private boolean playersTurn = false;
	
    public BoardStatus gameStatus = new BoardStatus();

    private JLabel title = new JLabel("Battleship",JLabel.CENTER);
    private JLabel messages = new JLabel("Messages go here:", JLabel.CENTER);
	
    private JFrame typePopUp = new JFrame();
    private JButton hostButton = new JButton("Host a Game");
    private JButton joinButton = new JButton("Join a Game");
    private JButton computerButton = new JButton("Play Against a Computer");
	
    private JFrame diffPopUp = new JFrame();
    private JButton easyButton = new JButton("Easy");
    private JButton mediumButton = new JButton("Medium");
    private JButton hardButton = new JButton("Hard");
	
    private Grid board = new Grid();

	
    BattleshipGUI(){
	
	//Setup main game frame
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
		
	//Add Title
	//Font titleFont = new Font(Font.SERIF, Font.BOLD,this.getWidth()/3);
	//this.title.setFont(titleFont);
	this.getContentPane().add(BorderLayout.NORTH,title);
		
	//Add play board
	this.board.addMouseListener(this.new cellClick());
	this.getContentPane().add(BorderLayout.CENTER,board);
		
	//Add messages
	this.getContentPane().add(BorderLayout.SOUTH, messages);
		
	//setup difficulty options popup
	GridLayout threeButtonGrid = new GridLayout(1,3);
	this.diffPopUp.setLayout(threeButtonGrid);
	this.diffPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.diffPopUp.setSize(600,100);
		
	//Add difficulty buttons listeners
	this.easyButton.addActionListener(this.new difficultyClick());
	this.mediumButton.addActionListener(this.new difficultyClick());
	this.hardButton.addActionListener(this.new difficultyClick());
		
	this.diffPopUp.add(easyButton);
	this.diffPopUp.add(mediumButton);
	this.diffPopUp.add(hardButton);
		
	//Setup gametype popup
	this.typePopUp.setLayout(threeButtonGrid);
	this.typePopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.typePopUp.setSize(600,100);
		
	//Add type buttons listeners
	this.hostButton.addActionListener(this.new typeClick());
	this.joinButton.addActionListener(this.new typeClick());
	this.computerButton.addActionListener(this.new typeClick());
		
	this.typePopUp.add(hostButton);
	this.typePopUp.add(joinButton);
	this.typePopUp.add(computerButton);
		
    }
	
    public static void main(String[] args){
	
	BattleshipGUI gui = new BattleshipGUI();
	//gui.setOptions();
	gui.setMessage("The game is waiting!");
	gui.setVisible(true);
	
    }
	
    public void setOptions(){
	this.setVisible(false);
	this.typePopUp.setVisible(true);
    }

    public void setMessage(String message){
	this.messages.setText(message);
    }
	
	public void setPlayersTurn(boolean tf){
		this.playersTurn = tf;
	}
	
	public int getGameType(){
		return this.gameType;
	}
	
	public String getDifficulty(){
		return this.difficulty;
	}
	
	public boolean getPlayersTurn(boolean tf){
		return this.playersTurn = tf;
	}
	
	public String getMessage(){
		return this.messages.getText();
	}
	
    public void showDiffPopUp(){
	this.diffPopUp.setVisible(true);
    }
	
    public void showTypePopUp(){
	this.typePopUp.setVisible(true);
    }
	
    public void hideDiffPopUp(){
	this.diffPopUp.setVisible(false);
    }
	
    public void hideTypePopUp(){
	this.typePopUp.setVisible(false);
    }
	
	public int makeMove(){
		this.playersTurn = true;
		while(playersTurn == true)
			return -1;
		return 0;
	}
	
	public void computerMove(int row, int column){
		gameStatus.addShot( row, column);
	}
	
	public boolean humanWins(){
		return this.gameStatus.humanWin();
	}
	
	public boolean computerWins(){
		return this.gameStatus.computerWin();
	}
	
    public class Grid extends JPanel{
	public int width;
	public int height;
	public int cellWidth;
	public int startX;

	@Override
	public void paintComponent(Graphics g)
	{

	    width = this.getWidth();
	    height = this.getHeight();		
	    cellWidth = height/21;
	    startX = (width - (cellWidth*10))/2;
	    Graphics2D g2d = (Graphics2D) g;
	    Color ocean = new Color(0,119,190);
			
	    //Make the background white
	    g2d.setColor(Color.WHITE);
	    g2d.fillRect(0,0,this.getWidth(),this.getHeight());
			
	    //Paint individual cells
	    for(int i=0; i < 10; i++){
		for(int j = 0; j<21; j++){
		    if(j==10)
			g2d.setColor(Color.BLACK);
		    else if(gameStatus.isHit(i,j))
			g2d.setColor(Color.RED);
		    else if(gameStatus.isPlayerBoat(i,j))
			g2d.setColor(Color.DARK_GRAY);
		    else if(gameStatus.isShot(i,j))
			g2d.setColor(ocean.darker());
		    else g2d.setColor(ocean);
					
		    g2d.fillRect(startX + (i*cellWidth),j*cellWidth,cellWidth,cellWidth);
			
		}
	    }
			
	    //Paint grid lines
	    g2d.setColor(Color.GRAY);
	    g2d.drawLine(startX,0,startX,cellWidth*21); //Far left border
	    g2d.drawLine(startX + 10*cellWidth,0,startX + 10*cellWidth,cellWidth*21); //Far right border
	    for(int i=1; i<10; i++)
		g2d.drawLine(startX + i*cellWidth,0,startX + cellWidth*i,cellWidth*10);	
	    for(int i=1; i<10; i++)
		g2d.drawLine(startX + i*cellWidth,cellWidth*11,startX + cellWidth*i,cellWidth*21);	
	    for(int j=0; j<22; j++)
		g2d.drawLine(startX, j*cellWidth, startX + 10*cellWidth, j*cellWidth);
	}
		
	
    }
	
    public class difficultyClick implements ActionListener{
	public void actionPerformed(ActionEvent e){
	    if( e.getSource() == BattleshipGUI.this.easyButton){
		System.out.println("Easy Button Clicked");
		difficulty = "EASY";
		BattleshipGUI.this.hideDiffPopUp();
		BattleshipGUI.this.setVisible(true);
	    }
	    else if( e.getSource() == BattleshipGUI.this.mediumButton){
		System.out.println("Medium Button Clicked");
		difficulty = "MEDIUM";
		BattleshipGUI.this.hideDiffPopUp();
		BattleshipGUI.this.setVisible(true);
	    }
	    else if ( e.getSource() == BattleshipGUI.this.hardButton){
		System.out.println("Hard Button Clicked");
		difficulty = "HARD";
		BattleshipGUI.this.hideDiffPopUp();
		BattleshipGUI.this.setVisible(true);
	    }
	}
    }
	
    public class typeClick implements ActionListener{
	public void actionPerformed(ActionEvent e){
	    if( e.getSource() == BattleshipGUI.this.hostButton){
			
		System.out.println("Host Button Clicked");
		gameType = 1;
		BattleshipGUI.this.hideTypePopUp();
		BattleshipGUI.this.showDiffPopUp();
	    }
	    else if( e.getSource() == BattleshipGUI.this.joinButton){
		System.out.println("Join Button Clicked");
		gameType = 2;
		BattleshipGUI.this.hideTypePopUp();
		BattleshipGUI.this.showDiffPopUp();
	    }
	    else if ( e.getSource() == BattleshipGUI.this.computerButton){
		System.out.println("Computer Button Clicked");
		gameType = 3;
		BattleshipGUI.this.hideTypePopUp();
		BattleshipGUI.this.showDiffPopUp();
	    }
	}
	
    }
	
    public class cellClick implements MouseListener{
	public void mouseClicked(MouseEvent e){}
		
	public void mousePressed(MouseEvent e){
	    //Calculate which column & row the mouse was clicked in
	    int cellColumn = (int) Math.floor((e.getX() - board.startX)/board.cellWidth);
	    int cellRow = (int) Math.floor(e.getY()/board.cellWidth);
	    //Add to shot list if it was in enemy territory
	    if(cellRow < 10 && cellRow >=0 && cellColumn >=0 && cellColumn < 10 && (!gameStatus.isShot( cellColumn, cellRow)) && playersTurn){
			gameStatus.addShot( cellColumn, cellRow);
			playersTurn = false;
		}
	    repaint();
	}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
    }

}
