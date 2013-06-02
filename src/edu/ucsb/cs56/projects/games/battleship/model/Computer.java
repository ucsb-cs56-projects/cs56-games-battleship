package edu.ucsb.cs56.projects.games.battleship;

import java.util.*;

/**
   The comptuer class, holds a gameboard and generates guesses
*/

public class Computer extends Player {
    
    private GuessGenerator gg;

    /**
       Constructs a computer player with the specified difficulty
       @param difficulty either "EASY", "MEDIUM" or "HARD"
    */
    public Computer(String difficulty) {
	super();
	super.randomGenerateBoats();

	gg = new GuessGenerator(difficulty);
    }

    /**
       Requests a move from the computer
       @return an int from 0-99, coming from the GuessGenerator
    */
    public int makeMove() {
	return gg.nextMove();
    }
    
    /**
       overriding updateGuessGrid so that we can have a 'smart' computer
    */
    public void updateGuessGrid(int location, String status) {
	gg.processResult(location, status);
    }
}
