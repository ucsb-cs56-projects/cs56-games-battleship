import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BattleshipGUI extends JFrame{

	String difficulty;

	private JLabel title = new JLabel("Battleship",JLabel.CENTER);
	private JTable board = new JTable(21,10);
	private JButton easyButton = new JButton("Easy");

	
	BattleshipGUI(){
	
		JPanel gamePanel = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 200);
		
		gamePanel.setLayout(new GridBagLayout());
		
		GridBagConstraints titleConstraints = new GridBagConstraints();
		titleConstraints.gridy = 0;
		gamePanel.add(title,titleConstraints);
		
		GridBagConstraints boardConstraints = new GridBagConstraints();
		boardConstraints.gridy = 1;
		boardConstraints.gridx = 0;
		board.setDefaultRenderer(String.class, new CustomRenderer());
		//board.repaint();
		gamePanel.add(board, boardConstraints);
		
		this.easyButton.addActionListener(new EasyClick());
		gamePanel.add(easyButton);
		
		this.add(gamePanel);
	
	}
	
	public static void main(String[] args){
	
		BattleshipGUI gui = new BattleshipGUI();
		
		gui.setDifficulty();
		
		gui.setVisible(true);
	
	}
	
	public void setDifficulty(){
		JFrame popUp = new JFrame();
		popUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		popUp.setSize(600,100);
		
		JPanel options = new JPanel();
		
		JButton easyButton = new JButton("Easy");
		easyButton.addActionListener(this.new EasyClick());
		JButton mediumButton = new JButton("Medium");
		mediumButton.addActionListener(this.new MediumClick());
		JButton hardButton = new JButton("Hard");
		hardButton.addActionListener(this.new HardClick());
		
		options.add(easyButton);
		options.add(mediumButton);
		options.add(hardButton);
		
		popUp.add(options);
		popUp.setVisible(true);
	}
	
	public class EasyClick implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Easy Button Clicked");
			difficulty = "Easy";
		
		}
	
	}
	
	public class MediumClick implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Medium Button Clicked");
			difficulty = "Medium";
		
		}
	
	}
	
		public class HardClick implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Hard Button Clicked");
			difficulty = "Hard";
		
		}
	
	}
	
	class CustomRenderer extends javax.swing.table.DefaultTableCellRenderer
	{
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			JLabel d = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
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