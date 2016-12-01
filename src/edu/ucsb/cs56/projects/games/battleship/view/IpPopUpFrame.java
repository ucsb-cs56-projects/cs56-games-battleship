package edu.ucsb.cs56.projects.games.battleship;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.URL;
import java.io.*;
import javax.sound.sampled.*;

public class IpPopUpFrame extends JFrame implements ActionListener{
	private JLabel ipRequest = new JLabel("Please input the IP address you wish to join.", JLabel.CENTER);
	private JTextField ipField = new JTextField();
	private JLabel ipMessage = new JLabel("Hit enter to submit.", JLabel.CENTER);
	private boolean ipEntered = false;

	public IpPopUpFrame(){
		        //Setup IP popup
        GridLayout threeWidgetVerticleGrid = new GridLayout(3,1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(threeWidgetVerticleGrid);
        this.setSize(600,100);
        this.ipField.setHorizontalAlignment(JTextField.CENTER);
        this.ipField.addActionListener(this);

        //Add IP widgets
        this.getContentPane().add(BorderLayout.NORTH,ipRequest);
        this.getContentPane().add(BorderLayout.CENTER,ipField);
        this.getContentPane().add(BorderLayout.SOUTH,ipMessage);
	}

	public void actionPerformed(ActionEvent e){
		ipEntered = true;
	}
	public void setIpEntered(boolean entered){
		ipEntered = entered;
	}
	public boolean getIpEntered(){
		return ipEntered;
	}
	public String getText(){ return ipField.getText(); }
}