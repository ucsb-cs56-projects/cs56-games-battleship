import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BattleshipGUI extends JFrame{

	private String difficulty;
	private int gameType;

	private JFrame typePopUp = new JFrame();
	private JFrame diffPopUp = new JFrame();
	private JLabel title = new JLabel("Battleship",JLabel.CENTER);
	private Grid board = new Grid();

	
	BattleshipGUI(){
		//Setup main game frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
		
		//Add Title
		this.getContentPane().add(BorderLayout.NORTH,title);
		
		//Add play board
		this.board.setSize(100,100);

		this.getContentPane().add(BorderLayout.CENTER,board);
		
		//setup difficulty options frame
		this.diffPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.diffPopUp.setSize(600,100);
		
		JPanel options = new JPanel();
		
		//Add difficulty buttons
		JButton easyButton = new JButton("Easy");
		easyButton.addActionListener(this.new EasyClick());
		JButton mediumButton = new JButton("Medium");
		mediumButton.addActionListener(this.new MediumClick());
		JButton hardButton = new JButton("Hard");
		hardButton.addActionListener(this.new HardClick());
		
		options.add(easyButton);
		options.add(mediumButton);
		options.add(hardButton);
		
		this.diffPopUp.add(options);
		
		//Setup gametype popup
		this.typePopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.typePopUp.setSize(600,100);
		
		JPanel gameTypes = new JPanel();
		
		//Add type buttons
		JButton hostButton = new JButton("Host a Game");
		hostButton.addActionListener(this.new HostClick());
		JButton joinButton = new JButton("Join a Game");
		joinButton.addActionListener(this.new JoinClick());
		JButton computerButton = new JButton("Play Against a Computer");
		computerButton.addActionListener(this.new ComputerClick());
		
		gameTypes.add(hostButton);
		gameTypes.add(joinButton);
		gameTypes.add(computerButton);
		
		this.typePopUp.add(gameTypes);
	
	}
	
	public static void main(String[] args){
	
		BattleshipGUI gui = new BattleshipGUI();
		gui.setVisible(true);
	
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
			
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0,0,this.getWidth(),this.getHeight());
			
			for(int i=0; i < 10; i++){
				for(int j = 0; j<21; j++){
					g2d.setColor(new Color(0,119,190));
					if(j==10)
						g2d.setColor(Color.BLACK);
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
	
	public class EasyClick implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Easy Button Clicked");
			difficulty = "Easy";
			BattleshipGUI.this.hideDiffPopUp();
			BattleshipGUI.this.setVisible(true);
		}
	
	}
	
	public class MediumClick implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Medium Button Clicked");
			difficulty = "Medium";
			BattleshipGUI.this.hideDiffPopUp();
			BattleshipGUI.this.setVisible(true);
		}
	
	}
	
	public class HardClick implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Hard Button Clicked");
			difficulty = "Hard";
			BattleshipGUI.this.hideDiffPopUp();
			BattleshipGUI.this.setVisible(true);
		
		}
	
	}
	
	public class HostClick implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Host Button Clicked");
			gameType = 1;
			BattleshipGUI.this.hideTypePopUp();
			BattleshipGUI.this.showDiffPopUp();
		
		}
	
	}
	
	public class JoinClick implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Join Button Clicked");
			gameType = 2;
			BattleshipGUI.this.hideTypePopUp();
			BattleshipGUI.this.showDiffPopUp();
		
		}
	
	}
	
	public class ComputerClick implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Computer Button Clicked");
			gameType = 3;
			BattleshipGUI.this.hideTypePopUp();
			BattleshipGUI.this.showDiffPopUp();
		
		}
	
	}	

}