package edu.ucsb.cs56.projects.games.battleship;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
   Dis da main class, son
*/

public class BattleShipMainGUI {
    
    public static void main(String[] args) {
	
		BattleshipGUI theGame = new BattleshipGUI();
		theGame.setOptions();
		while(theGame.getDifficulty() == null || theGame.getGameType() == 0){
			try{
				Thread.sleep(10);
			}
			catch (InterruptedException e){
			}
		
		}
	
	if(theGame.getGameType() == 3) {
	
		//Setup the players
	    Player human = new Player();
	    Player computer = new Computer(theGame.getDifficulty());
		
		//Send information about ship locations to the GUI
		ArrayList<Integer> boats = human.getBoatsArrayList();
		for(Integer loc:boats){
			int boatRow = loc/10 + 11;
			int boatColumn = loc%10;
			theGame.addPlayerBoat(boatRow*10 + boatColumn);
		}
		boats = computer.getboats();
		for(Integer loc:boats){
			theGame.addEnemyBoat(loc);
		}
		
		//Start the game
	    while(true) {
		
		//Start player's move
		theGame.makeMove();
		theGame.setMessage("Your turn!");
		while(theGame.getPlayersTurn()){
			try{
				Thread.sleep(10);
			}
			catch (InterruptedException e){
			}
		}
		
		//Get player's move from GUI and send it to model
		int humanMove = theGame.getLastMove();
		String humanStatus = computer.incomingMissile(humanMove);
		human.updateGuessGrid(humanMove, humanStatus);
		
		//Check win status
		if(human.hasWon()) {
		    theGame.setMessage("CONGRATULATIONS, YOU WIN!");
		    break;
		}
		
		//Start computer's move
		int computerMove = computer.requestMove();
		//String readableLocation = "" + (char)(computerMove/10+65) + computerMove%10;
		//System.out.println("COMPUTER HAS FIRED MISSILES AT " + readableLocation);
		//Update model and GUI with computer's move
		String computerStatus = human.incomingMissile(computerMove);
		computer.updateGuessGrid(computerMove, computerStatus);
		int computersMoveRow = (computerMove/10) + 11;
		int computersMoveColumn = computerMove%10;
		theGame.addShot(computersMoveRow*10 + computersMoveColumn);
		
		//Check win status
		if(computer.hasWon()) {
		    theGame.setMessage("OH NO, YOU LOSE!");
		    break;
		}
		//back up to player's move
	    }
	}
	//Display end of game message
	String currentMessage = theGame.getMessage();
	System.out.println(theGame.getGameType());
	theGame.setMessage(currentMessage + " THANK YOU FOR PLAYING");
    }

}
