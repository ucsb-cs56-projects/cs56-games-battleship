package edu.ucsb.cs56.S13.WeiGuo.battleship.controller;

import java.util.*;

/**
  This class keeps tracks of the grid for the player's guesses. If the spot registers as a hit, an "X" will be shown, a miss is denoted by an "O", unchecked spots are denoted by "~"
*/
public class GuessGrid {

    // keep track of locations and guesses with this handy map
    private Map<Integer, String> guesses;
    
    /**
       Default constructor for guess grid.
       Every spot is unchecked
     */
    public GuessGrid() {
	guesses = new HashMap<Integer, String>();

	// initialize grid
	for(int i = 0; i < 100; i++) {
	    guesses.put(i, "~");
	}
    }

    /**
       the toString method prings out the grid in an easy to read format for the player
    */
    public String toString() {
	String output = "";
	String topNumbers = "  0 1 2 3 4 5 6 7 8 9\n";
	String lineDiv = " _____________________\n";
	output += topNumbers;
	output += lineDiv;
	for(int i = 0; i < 10; i++) {
	    char sideLetter = (char)(65 + i);
	    output += "" + sideLetter;
	    for(int j = 0; j < 10; j++) {
		int location = 10 * i + j;
		output += "|" + guesses.get(location);
	    }
	    output += "|\n"+lineDiv;
	}
	return output;
    }

    /**
       updates the guessGrid to reflect that the player hit a ship
       @param location the location the player is firing on
    */
    public void registerHit(int location){
	guesses.put(location, "X");
    }
    

    /**
       updates the guessGrid to reflect that the player missed
       @param location the location the player is firing on
    */
    public void registerMiss(int location){
	guesses.put(location, "O");
    }

    /**
       checks to see if the location has already been guessed. It is used by the Player class
       @param location the location to check to see if it's already been tried
    */
    public boolean alreadyGuessed(int location){
	if(guesses.get(location).equals("X") || guesses.get(location).equals("O")){
	    return true;
	}
	else return false;
    }
    
    /**
       updates the grid
       @param location the location to be updated
       @param status the status to be inserted at the location
    */
    public void update(int location, String status) {
	if(status.equals("HIT") || status.split(" ")[0].equals("SUNK"))
	    guesses.put(location, "X");
	else if(status.equals("MISS"))
	    guesses.put(location, "O");
	else
	    System.err.println("Invalid ship status");
    }
    
}
