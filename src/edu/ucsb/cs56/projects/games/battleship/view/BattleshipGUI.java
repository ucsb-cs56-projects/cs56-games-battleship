import java.awt.*;

import javax.swing.*;

public class BattleshipGUI extends JFrame{

	private JLabel title = new JLabel("Battleship",JLabel.CENTER);
	private String[] options = new String[] {"Easy","Medium","Hard"};
	private JOptionPane difficultySet = new JOptionPane("Select Difficulty:", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options);
	private JTable board = new JTable(21,10);

	
	BattleshipGUI(){
	
		JPanel gamePanel = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 200);

		
		gamePanel.setLayout(new GridBagLayout());
		
		gamePanel.add(difficultySet);
		
		GridBagConstraints titleConstraints = new GridBagConstraints();
		titleConstraints.gridy = 0;
		gamePanel.add(title,titleConstraints);
		
		GridBagConstraints boardConstraints = new GridBagConstraints();
		boardConstraints.gridy = 1;
		boardConstraints.gridx = 0;
		board.setDefaultRenderer(String.class, new CustomRenderer());
		board.repaint();
		gamePanel.add(board, boardConstraints);
		
		this.add(gamePanel);
	
	}
	
	public static void main(String[] args){
	
		BattleshipGUI gui = new BattleshipGUI();
		
		gui.setVisible(true);
	
	}
	
	class CustomRenderer extends javax.swing.table.DefaultTableCellRenderer
	{
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			JLabel d = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if((row == 1) && (column == 1))
				d.setBackground(Color.BLACK);
			else
				d.setBackground(Color.WHITE);
			return d;
		}
	}
			

}