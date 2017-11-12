package edu.ucsb.cs56.projects.games.battleship;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.URL;
import java.io.*;
import javax.sound.sampled.*;

public class ColorPopUpFrame extends JFrame implements ActionListener{

	private JButton colorSelectButton = new JButton("Select a color, then click to proceed");
    static Color[] colorForList = {Color.DARK_GRAY, Color.BLACK, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK, Color.WHITE, Color.YELLOW};
    static String [] colorList = {"Default", "Black", "Blue", "Green", "Orange", "Pink", "White", "Yellow"};
    JComboBox colorDrop = new JComboBox(colorList);
    private Color color;
    private boolean colorContinue = false;

	

    public ColorPopUpFrame(){
		ComboBoxRenderer renderer = new ComboBoxRenderer(colorDrop);

    renderer.setColors(colorForList);
    renderer.setStrings(colorList);

    colorDrop.setRenderer(renderer);
        //Setup colorPopUp
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,100);
        
        //Add shipColor buttons and dropdown listeners
        this.colorSelectButton.addActionListener(this.new colorClick());
        this.colorDrop.addActionListener(this);

        //Add buttons and dropdown to window
        this.getContentPane().add(BorderLayout.CENTER, colorDrop);
        this.getContentPane().add(BorderLayout.SOUTH, colorSelectButton);
    }

    /**
    * Listener for the color selection menu's dropdown button
    * when the user selects a color it will set shipColor to that color
    **/

    @Override
    public void actionPerformed(ActionEvent e){
        JComboBox combo = (JComboBox)e.getSource();
        switch((String)combo.getSelectedItem()){
            case "Default":
                color = Color.DARK_GRAY;
                break;
            case "Black":
                color = Color.BLACK;
                 break;
            case "Blue":
                color = Color.BLUE;
                break;
            case "Green":
                color = Color.GREEN;
                break;
            case "Orange":
                color = Color.ORANGE;
                break;
            case "Pink":
                color =Color.PINK;
                break;
            case "White":
            	color = Color.WHITE;
                break;
            case "Yellow":
                color = Color.YELLOW;
                break;
            default:
                break;
        }
    }
        /**
     * Listener for the color selection menu's continue button
     * when the user clicks the button it will move onto the ship size
     * selection menu
     **/

    public class colorClick implements ActionListener{ 
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == ColorPopUpFrame.this.colorSelectButton){
            	colorContinue = true;
            }
        }
    }

    public boolean getContinue(){ return this.colorContinue; }
    public Color getColor(){ return this.color;}
}

class ComboBoxRenderer extends JPanel implements ListCellRenderer
{

    private static final long serialVersionUID = -1L;
    private Color[] colors;
    private String[] strings;
    JPanel textPanel;
    JLabel text;

    public ComboBoxRenderer(JComboBox combo) {
        textPanel = new JPanel();
        textPanel.add(this);
        text = new JLabel();
        text.setOpaque(true);
        text.setFont(combo.getFont());
        textPanel.add(text);
    }

    public void setColors(Color[] col){ colors = col;}

    public void setStrings(String[] str){ strings = str;}

    public Color[] getColors(){ return colors;}

    public String[] getStrings(){ return strings;}

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

        if (isSelected)
        {
            setBackground(list.getSelectionBackground());
        }
        else
        {
            setBackground(Color.WHITE);
        }

        if (colors.length != strings.length)
        {
            System.out.println("colors.length does not equal strings.length");
            return this;
        }
        else if (colors == null)
        {
            System.out.println("use setColors first.");
            return this;
        }
        else if (strings == null)
        {
            System.out.println("use setStrings first.");
            return this;
        }

        text.setBackground(getBackground());

        text.setText(value.toString());
        if (index>-1) {
            text.setForeground(colors[index]);
        }
        return text;
    }
}
