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
	private JTable board = new JTable(21,10);

	
	BattleshipGUI(){
		//Setup main game frame
		JPanel gamePanel = new JPanel(new GridBagLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
		
		//Add Title
		GridBagConstraints titleConstraints = new GridBagConstraints();
		titleConstraints.gridy = 1;
		gamePanel.add(title,titleConstraints);
		
		//Add play board
		board.setColumnSelectionAllowed(false);
		board.setRowSelectionAllowed(false);
		board.setDefaultRenderer(String.class, new CustomRenderer());
		//board.repaint();
		GridBagConstraints boardConstraints = new GridBagConstraints();
		boardConstraints.gridy = 2;
		boardConstraints.gridx = 0;

		gamePanel.add(board, boardConstraints);
		
		this.add(gamePanel);
		
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
		gui.setOptions();
		gui.repaint();
	
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
	
	class CustomRenderer extends javax.swing.table.DefaultTableCellRenderer
	{
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			Component d =  super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if(true)
			{
				d.setBackground(Color.RED);
				d.setForeground(Color.RED);
			}
			else
			{
				d.setBackground(Color.WHITE);
				d.setForeground(Color.WHITE);
			}
			setOpaque(true);
			return d;
		}
	}
			

}