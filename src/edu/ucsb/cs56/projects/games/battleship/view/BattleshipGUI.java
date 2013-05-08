import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BattleshipGUI extends JFrame{

	private String difficulty;
	private int gameType;
	public BoardStatus gameStatus = new BoardStatus("test");

	private JLabel title = new JLabel("Battleship",JLabel.CENTER);
	
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
		this.getContentPane().add(BorderLayout.NORTH,title);
		
		//Add play board
		this.board.addMouseListener(this.new cellClick());
		this.getContentPane().add(BorderLayout.CENTER,board);
		
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
		gui.setVisible(true);
	
	}
	
	public int getBoardX(){
		return this.board.getX();
	}
	
	public int getBoardY(){
		return this.board.getY();
	}
	
	public void setOptions(){
		this.setVisible(false);
		this.typePopUp.setVisible(true);
	}
	
	public void setDifficulty(){
		this.setVisible(false);
		this.diffPopUp.setVisible(true);
	}
	
	public void setType(){
		this.setVisible(false);
		this.typePopUp.setVisible(true);
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
	
	public class Grid extends JPanel{
		@Override
		public void paintComponent(Graphics g)
		{
			Graphics2D g2d = (Graphics2D) g;
			int width = this.getWidth();
			int height = this.getHeight();
			int cellWidth = height/21;
			int startX = (width - (cellWidth*10))/2;
			Color ocean = new Color(0,119,190);
			
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0,0,this.getWidth(),this.getHeight());
			
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
			
			g2d.setColor(Color.GRAY);
			g2d.drawLine(startX,0,startX,cellWidth*21);
			g2d.drawLine(startX + 10*cellWidth,0,startX + 10*cellWidth,cellWidth*21);
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
				difficulty = "Easy";
				BattleshipGUI.this.hideDiffPopUp();
				BattleshipGUI.this.setVisible(true);
			}
			else if( e.getSource() == BattleshipGUI.this.mediumButton){
				System.out.println("Medium Button Clicked");
				difficulty = "Medium";
				BattleshipGUI.this.hideDiffPopUp();
				BattleshipGUI.this.setVisible(true);
			}
			else if ( e.getSource() == BattleshipGUI.this.hardButton){
				System.out.println("Hard Button Clicked");
				difficulty = "Hard";
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
		public void mouseClicked(MouseEvent e){
			int width = board.getWidth();
			int height = board.getHeight();
			int cellWidth = height/21;
			int startX = (width - (cellWidth*10))/2;
			//e.translatePoint(board.getX(),-board.getY());
			int clickPositionX = e.getX() - startX;
			int clickPositionY = e.getY();
			int cellColumn = (int) Math.floor(clickPositionX/cellWidth);
			int cellRow = (int) Math.floor(clickPositionY/cellWidth);
			gameStatus.addShot( cellColumn, cellRow);
			
			repaint();
		}
		
		public void mousePressed(MouseEvent e){
			int width = board.getWidth();
			int height = board.getHeight();
			int cellWidth = height/21;
			int startX = (width - (cellWidth*10))/2;
			//e.translatePoint(board.getX(),-board.getY());
			int clickPositionX = e.getX() - startX;
			int clickPositionY = e.getY();
			int cellColumn = (int) Math.floor(clickPositionX/cellWidth);
			int cellRow = (int) Math.floor(clickPositionY/cellWidth);
			gameStatus.addShot( cellColumn, cellRow);
			
			repaint();
		}
		public void mouseReleased(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
	}

}