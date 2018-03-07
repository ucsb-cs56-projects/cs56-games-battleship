package edu.ucsb.cs56.projects.games.battleship;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.URL;
import java.io.*;
import javax.sound.sampled.*;

/**
 * Class for choosing difficulty when playing against computer
 *
 * @version 2.4
 */

public class DiffPopUpFrame extends JFrame implements ActionListener {
    private JButton easyButton = new JButton();
    private JButton mediumButton = new JButton();
    private JButton hardButton = new JButton();
    private String difficulty;
    GridLayout threeButtonGrid = new GridLayout(1, 3);


    public DiffPopUpFrame() {

        easyButton.setIcon(new ImageIcon(getClass().getResource("images/easy.png")));
        easyButton.setBackground(Color.WHITE);
        mediumButton.setIcon(new ImageIcon(getClass().getResource("images/med.png")));
        mediumButton.setBackground(Color.WHITE);
        hardButton.setIcon(new ImageIcon(getClass().getResource("images/hard.png")));
        hardButton.setBackground(Color.WHITE);

        this.setLayout(threeButtonGrid);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 140);
        this.easyButton.addActionListener(this);
        this.mediumButton.addActionListener(this);
        this.hardButton.addActionListener(this);

        this.add(easyButton);
        this.add(mediumButton);
        this.add(hardButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.easyButton) {
            difficulty = "EASY";

        } else if (e.getSource() == this.mediumButton) {
            difficulty = "MEDIUM";
        } else if (e.getSource() == this.hardButton) {
            difficulty = "HARD";
        }
    }

    public String getDifficulty() {
        return this.difficulty;
    }
}
