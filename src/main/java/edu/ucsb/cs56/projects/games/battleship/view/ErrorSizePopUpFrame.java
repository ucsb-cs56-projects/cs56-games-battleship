package edu.ucsb.cs56.projects.games.battleship;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.URL;
import java.io.*;
import javax.sound.sampled.*;



public class ErrorSizePopUpFrame extends JFrame implements ActionListener
{

  private JButton ErrorButton = new JButton("Please enter a vaild size");


  public ErrorSizePopUpFrame()
  {
    this.ErrorButton.setFont(new Font("Arial", Font.PLAIN, 30));
    this.setSize(600, 200);
    this.getContentPane().add(BorderLayout.CENTER,ErrorButton);
    this.ErrorButton.addActionListener(this);
  }

  public void actionPerformed(ActionEvent input)
  {
    this.dispose();
  }
}
