package edu.ucsb.cs56.projects.games.battleship;
import java.util.ArrayList;



/**
   The gameboard class, holds shipgrid, guessgrid and associated logic
*/
public class GameBoard {
    
    private ShipGrid ships;
    private GuessGrid guesses;

    /**
       default constructor
       creates default ShipGrid and GuessGrid
    */
    public GameBoard() {
	ships = new ShipGrid();
	guesses = new GuessGrid();
    }

	public ArrayList getShipLocations(){
		return this.ships.getShipLocations();
	}
	
    /**
       checks that the location hasn't already been guessed
       @param location the location to be checked
    */
    public boolean alreadyGuessed(int location) {
	return guesses.alreadyGuessed(location);
    }
    
    /**
       Sends a missile location on the ShipGrid
       @param location the coordinate where the missile is targetted
    */
    public String incomingMissile(int location) {
	return ships.incomingMissile(location);
    }

    /**
       Requests the guessGrid to update the location given the status
       @param location the location to be updated
       @param status "HIT", "MISS", or "SUNK shiptype"
    */
    public void updateGuessGrid(int location, String status) {
	guesses.update(location, status);
    }

    public String toString() {
	String ggString = "YOUR GUESSES\n" + guesses.toString();
	String myShipsString = "YOUR SHIPS\n" + ships.toString();
	return ggString + "\n" + myShipsString;
    }

}
