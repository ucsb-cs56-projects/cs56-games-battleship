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
    String [] colorList = {"Default", "Black", "Blue", "Green", "Orange", "Pink", "White", "Yellow"};
    private JComboBox colorDrop = new JComboBox(colorList);
    private Color color;
    private boolean colorContinue = false;

    public ColorPopUpFrame(){
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
