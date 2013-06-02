package edu.ucsb.cs56.projects.games.battleship;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
   Main class for Battleship Game
*/

public class BattleShipMainGUI {
    
    public static void main(String[] args) {
	
		BattleshipGUI gui = new BattleshipGUI();
		gui.setOptions();
		while(gui.getDifficulty() == null || gui.getGameType() == 0){
			try{
				Thread.sleep(10);
			}
			catch (InterruptedException e){
			}
		
		}
	
	if(gui.getGameType() == 3) {
	
		//Setup the players
	    Player human = new Player();
		human.randomGenerateBoats();
	    Computer computer = new Computer(gui.getDifficulty());
		
		//Send information about ship locations to the GUI
		ArrayList<Integer> boats = human.getBoatsArrayList();
		gui.addPlayerBoats(boats);
		boats = computer.getBoatsArrayList();
		gui.addEnemyBoats(boats);
		
		//Start the game
	    while(true) {
		
		//Start player's move
		gui.makeMove();
		gui.setMessage("Your turn!");
		while(gui.getPlayersTurn()){
			try{
				Thread.sleep(10);
			}
			catch (InterruptedException e){
			}
		}
		
		//Get player's move from GUI and send it to model
		int humanMove = gui.getLastMove();
		computer.addShot(humanMove);
		
		//Check win status
		if(computer.hasLost()) {
		    gui.setMessage("CONGRATULATIONS, YOU WIN!");
		    break;
		}
		
		//Start computer's move
		int computerMove = computer.makeMove();
		gui.addShot(gui.shiftToPlayerGrid(computerMove));
		human.addShot(computerMove);
		computer.updateGuessGrid(computerMove, gui.hitPlayer(gui.shiftToPlayerGrid(computerMove)));
		
		//Check win status
		if(human.hasLost()) {
			System.out.println(human.hasLost());
		    gui.setMessage("OH NO, YOU LOSE!");
		    break;
		}
		//back up to player's move
	    }
	}
	//Display end of game message
	String currentMessage = gui.getMessage();
	System.out.println(gui.getGameType());
	gui.setMessage(currentMessage + " THANK YOU FOR PLAYING");
    }

}
