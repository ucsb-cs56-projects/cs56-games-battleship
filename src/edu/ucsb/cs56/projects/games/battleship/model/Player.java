package edu.ucsb.cs56.projects.games.battleship;

import java.util.*;
import java.io.*;

/**
   The player class, holds a gameboard and takes in player input
*/

public class Player {
    
    private GameBoard gb;
    private boolean isWinner;
    
    /**
       default constructor
    */
    public Player(){
	gb = new GameBoard();
	isWinner = false;
    }
    
	public ArrayList getShipLocations(){
		return this.gb.getShipLocations();
	}
	
    /**
       Requests a move from the player
       @return an int from 0-99 corresponding to the player's move or -1 if error
    */
    public int requestMove() {
	// prompt the user for a move
	System.out.println("Where to fire missiles?");
	
	//  open up standard input
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	String playersMove = null;

	//  read the move from the command-line; need to use try/catch with the
	//  readLine() method
	try {
	    playersMove = br.readLine();
	} catch (IOException ioe) {
	    System.err.println("IO error trying to read your move!" + ioe.getMessage());
	    return -1;
	}
	// check to see if the move is a valid location (A0-J9)
	if(playersMove.length() != 2) {
	    System.err.println("Invalid Move");
	    return -1;
	}
	else {
	    playersMove = playersMove.toUpperCase();
	    try {
		int char1 = (int)playersMove.charAt(0);
		int char2 = Integer.parseInt(""+playersMove.charAt(1));
	    
		// A = 65, J = 74
		if(char1 < 65 || char1 > 74 || char2 < 0 || char2 > 9) {
		    System.err.println("Invalid Move");
		    return -1;
		}
		// converts playersMove to an int from 0-99
		int location = (char1-65)*10 + char2;

		// checks to see if location has already been guessed
		if(gb.alreadyGuessed(location)) {
		    System.err.println("Already guessed " + playersMove);
		    return -1;
		}
		return location;
	    }
	    catch (NumberFormatException nfe) {
		System.err.println("Invalid Move");
		return -1;
	    }
	}
    }

    /**
       sends a missle down to the gameboard
       @param location the location being attacked by the opponent
       @return status of the missle "HIT", "MISS" or "SUNK shiptype"
    */
    public String incomingMissile(int location) {
        return gb.incomingMissile(location);
    }

    /**
       updates the guessgrid to reflect the status at location
       @param location the location to be updated
       @param status "HIT", "MISS", "SUNK shiptype", or "WIN"
    */
    public void updateGuessGrid(int location, String status) {
	if(status.equals("WIN"))
	    isWinner = true;
	else
	    gb.updateGuessGrid(location, status);
    }	
    
    /**
       @return if this player is a winner
    */
    public boolean hasWon() {
	return isWinner;
    }

    public void printGameBoard() {
	System.out.println(gb.toString());
    }
}
