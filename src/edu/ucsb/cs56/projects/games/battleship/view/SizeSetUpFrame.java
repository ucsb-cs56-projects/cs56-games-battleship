package edu.ucsb.cs56.projects.games.battleship;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.URL;
import java.io.*;
import javax.sound.sampled.*;

public class SizeSetUpFrame extends JFrame implements ActionListener{
	private JButton reminder = new JButton("Input sizes of ships (between 2 and 9), then click to proceed");
	private JPanel shipSizePanel = new JPanel();
	private JTextField ship1 = new JTextField(5);
	private JTextField ship2 = new JTextField(5);
	private JTextField ship3 = new JTextField(5);
	private JTextField ship4 = new JTextField(5);
	private JTextField ship5 = new JTextField(5);
	private JTextField [] inputBoxes = {ship1, ship2, ship3, ship4, ship5};
	private boolean sizeSet = false;
	private int[] shipSizes;

	public SizeSetUpFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,100);

        //Add shipsize buttons listeners
        this.reminder.addActionListener(this);
        for(int i=0; i<5; i++){
            this.inputBoxes[i].addActionListener(this);
        }
        this.getContentPane().add(BorderLayout.SOUTH, reminder);
        for(int i=0; i<5; i++){
            this.shipSizePanel.add(this.inputBoxes[i]);
        }
        this.getContentPane().add(BorderLayout.CENTER, shipSizePanel);
    
	}
	/**
	 * Listener for the ship sizes button
     * once the user clicks the proceed button, texts in the 5 text fields will be check by isValid() method, 
     * and if all of them are valid, program will read in the 5 numbers into the shipSizes array and 
     * send the message of clicking to GUI
	 **/
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == SizeSetUpFrame.this.reminder){
			int [] inputSizes = Player.shipSizes; 
            shipSizes = inputSizes;
			boolean isValid = true;
			
			for(int i=0; i<5; i++){
				String input = SizeSetUpFrame.this.inputBoxes[i].getText();
				if(SizeSetUpFrame.isValid(input)==true) {
					inputSizes[i] = Integer.parseInt(SizeSetUpFrame.this.inputBoxes[i].getText());}
				else {isValid=false; break;}
			}
			if(isValid==true){
				sizeSet = true;
			}
			else reminder.setText("Please input sizes between 2 and 9");
		}
	}

	public boolean getSizeSet(){
		return this.sizeSet;
	}
	public int[] getShipSizes(){ return this.shipSizes;}
	/**
	 * Method exlusively for ship sizes button listener to check validity of user input
	 **/
	public static boolean isValid(String input){
		if(input.equals("") || input.length()!=1) return false;
		char character = input.charAt(0);
		if(character>'9' || character<'2') return false;
		return true;
	}
	
}