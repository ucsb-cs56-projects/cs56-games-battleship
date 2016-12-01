package edu.ucsb.cs56.projects.games.battleship;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.URL;
import java.io.*;
import javax.sound.sampled.*;

public class DiffPopUpFrame extends JFrame implements ActionListener{
    private JButton easyButton = new JButton("Easy");
    private JButton mediumButton = new JButton("Medium");
    private JButton hardButton = new JButton("Hard");
    private String difficulty;
    GridLayout threeButtonGrid = new GridLayout(1,3);


    public DiffPopUpFrame(){
    	this.setLayout(threeButtonGrid);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,100);        
        this.easyButton.addActionListener(this);
        this.mediumButton.addActionListener(this);
        this.hardButton.addActionListener(this);

        this.add(easyButton);
        this.add(mediumButton);
        this.add(hardButton);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if( e.getSource() == this.easyButton){
            difficulty = "EASY";

        }
        else if( e.getSource() == this.mediumButton){
            difficulty = "MEDIUM";
        }
        else if ( e.getSource() == this.hardButton){
            difficulty = "HARD";
        }
   }
   public String getDifficulty(){ return this.difficulty;}
}