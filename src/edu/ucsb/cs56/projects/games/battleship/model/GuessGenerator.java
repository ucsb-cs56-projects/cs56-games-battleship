package edu.ucsb.cs56.projects.games.battleship;

import java.util.*;

/**
   Generates guesses for the Computer
*/
public class GuessGenerator {
    
    private String difficulty;

    private Set<Integer>                  guesses;
    private ArrayList<Integer>            diagonals;
    private ArrayList<ArrayList<Integer>> smartGuesses;
    private ArrayList<Integer>            currentDirection;

    public GuessGenerator(String difficulty) {
	this.difficulty = difficulty;
	guesses = new HashSet<Integer>();

	//adds all 'diagonals' to optimize shipfinding
	diagonals = new ArrayList<Integer>();
	for(int i = 0; i < 10; i++) {
	    for(int j = 0; j < 10; j++) {
		if(i % 2 == 0 && j % 2 == 1)
		    diagonals.add(i*10 + j);
		if(i % 2 == 1 && j % 2 == 0)
		    diagonals.add(i*10 + j);
	    }
	}

	smartGuesses = new ArrayList<ArrayList<Integer>>();
    }
    
    public int nextMove() {
	if(difficulty.equals("EASY"))
	    return genEasyMove();
	else if(difficulty.equals("MEDIUM"))
	    return genMedMove();
	else if(difficulty.equals("HARD"))
	    return genHardMove();
	return -1;
    }

    /**
       @return A random location 0-99 that hasn't been guessed
    */
    public int genEasyMove() {
	int rando = (int)(Math.random() * 100);
	while(guesses.contains(rando))
	    rando = (int)(Math.random() * 100);
	guesses.add(rando);
	return rando;
    }
    
    /**
       @return A medium difficulty move
    */
    public int genMedMove() {
	if(smartGuesses.isEmpty()) { //rando move
	    return genEasyMove();
	}
	else { // do a 'smart' move
	    while(currentDirection == null || currentDirection.size() == 0) {
		if(smartGuesses.isEmpty())
		    return genEasyMove();
		currentDirection = smartGuesses.remove(0);
	    }
	    int guess = currentDirection.remove(0);
	    guesses.add(guess);
	    return guess;
	}
    }

    /**
       @return A hard difficulty move
    */
    public int genHardMove() {
	if(smartGuesses.isEmpty()){ //guess a diagonal
	    int index = (int)(Math.random() * diagonals.size());
	    int guess = diagonals.remove(index);
	    guesses.add(guess);
	    return guess;
	}
	    else{ // do a smart move
		while(currentDirection == null || currentDirection.size() == 0) {
		    if(smartGuesses.isEmpty())
			return genHardMove();
		    currentDirection = smartGuesses.remove(0);
		}
		int guess = currentDirection.remove(0);
		guesses.add(guess);
		return guess;
	    }
    }
    

    /**
       updates lists and things based on results of previous move
    */
    public void processResult(int location, String status) {
	if(smartGuesses.isEmpty() && status.equals("HIT")) {
	    // make NWES && add to smart guesses
	    ArrayList<Integer> guessesNorth = new ArrayList<Integer>();
	    ArrayList<Integer> guessesSouth = new ArrayList<Integer>();
	    ArrayList<Integer> guessesEast  = new ArrayList<Integer>();
	    ArrayList<Integer> guessesWest  = new ArrayList<Integer>();

	    //create guessesNorth
	    for(int i = 1; i <= 4; i++){
		if((location - 10 * i < 0) || guesses.contains(location - 10 * i)){  //if out of bounds or already guessed;
		    break;
		}
		else guessesNorth.add(location - 10 * i);
	    }

	    //create guessesSouth
	    for(int i = 1; i <= 4; i++){
		if((location + 10 * i > 99) || guesses.contains(location + 10 * i)){  //if out of bounds or already guessed
		    break;
		}
		else guessesSouth.add(location + 10 * i);
	    }

	    //create guessesEast
	    for(int i = 1; i <= 4; i++){
		if(((location + i) % 10 == 0) || guesses.contains(location + i)){  //if out of bounds or already guessed
		    break;
		}
		else guessesEast.add(location + i);
	    }

	    //create guessesWest
	    for(int i = 1; i <= 4; i++){
		if(((location - i) % 10 == 9) || guesses.contains(location - i)){  //if out of bounds or already guessed
		    break;
		}
		else guessesWest.add(location - i);
	    }
	    smartGuesses.add(guessesNorth);
	    smartGuesses.add(guessesSouth);
	    smartGuesses.add(guessesEast);
	    smartGuesses.add(guessesWest);
	}
	else if(!smartGuesses.isEmpty() && status.equals("MISS")) {
	    currentDirection = null;
	}
	else if(!smartGuesses.isEmpty() && status.split(" ")[0].equals("SUNK")) {
	    smartGuesses.clear();
	}
    }
}
