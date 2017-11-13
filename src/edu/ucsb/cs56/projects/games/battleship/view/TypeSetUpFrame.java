package edu.ucsb.cs56.projects.games.battleship;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.URL;
import java.io.*;
import javax.sound.sampled.*;

/**
	 * Class for setting up the game type 
	 * @version 2.4 
	*/

public class TypeSetUpFrame extends JFrame implements ActionListener{
	private JButton hostButton = new JButton();
	private JButton joinButton = new JButton();
	private JButton computerButton = new JButton();
	private int gameType = 0;
    public boolean entered;
    GridLayout threeButtonGrid = new GridLayout(1,3);


	public TypeSetUpFrame(){
		
		hostButton.setIcon(new ImageIcon(getClass().getResource("images/host.png")));
		joinButton.setIcon(new ImageIcon(getClass().getResource("images/join.png")));
		computerButton.setIcon(new ImageIcon(getClass().getResource("images/computer.png")));

        entered = false;
		this.setLayout(threeButtonGrid);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,220);

        //Add type buttons listeners
        this.hostButton.addActionListener(this);
        this.joinButton.addActionListener(this);
        this.computerButton.addActionListener(this);

        //Add type buttons to window
        this.add(hostButton);
        this.add(joinButton);
        this.add(computerButton);

	}

    @Override
		public void actionPerformed(ActionEvent e){
			if( e.getSource() == this.hostButton){
                this.gameType = 1;
                entered = true;
            }
            else if( e.getSource() == this.joinButton){
                this.gameType = 2;
                entered = true;
            }
            else if ( e.getSource() == this.computerButton){
                this.gameType = 3;
                entered = true;
			}
		}
	public int getGameType(){ return this.gameType;}
}
