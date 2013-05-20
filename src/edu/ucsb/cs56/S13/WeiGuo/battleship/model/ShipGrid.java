package edu.ucsb.cs56.S13.WeiGuo.battleship.controller;

import java.util.*;

/**
   this class keeps track of the player's ship placement. By default, the ships are placed randomly. Later versions will have user controlled ship placement.
*/
public class ShipGrid {

    // keep track of locations and their contents with this handy map
    private Map<Integer, String> grid;
    // keep track of the ships and their locations with this handy map
    private Map<Integer, Ship> shipMap;
    
    private static final String[] orientations = {"U","D","L","R"};

    
    /**
       Default constructor for ship grid . It makes a grid with randomly placed ships
     */
    public ShipGrid() {
	grid    = new HashMap<Integer, String>();
	shipMap = new HashMap<Integer, Ship>();

	// initialize grid
	for(int i = 0; i < 100; i++) {
	    grid.put(i, "~");
	}
	//place carrier, battleship, destroyer, submarine, patrol boat
	place(Ship.CARRIER);
	place(Ship.BATTLESHIP);
	place(Ship.DESTROYER);
	place(Ship.SUBMARINE);
	place(Ship.PATROL_BOAT);
	
    }
    
    /**
       returns a string representation of the grid
    */
    public String toString() {
	String output     = "";
	String topNumbers = "  0 1 2 3 4 5 6 7 8 9\n";
	String lineDiv    = " _____________________\n";
	output += topNumbers;
	output += lineDiv;

	for(int i = 0; i < 10; i++) {
	    char sideLetter = (char) (65 + i);
	    output += "" + sideLetter;

	    for(int j = 0; j < 10; j++) {
		int location = 10 * i + j;
		output += "|" + grid.get(location);
	    }
	    output += "|\n" + lineDiv;
	}
	return output;
    }
    
    /**
       places a ship into the grid, checks for valid ship placement (no overlapping, full ship within bounds)
       @param shipType the string value of the type of ship that is to be placed
    */

    private void place(String shipType){
	int spawnPoint              = 0;
	int [] possibleOrientations = new int[4];
	int orientationIndex        = 0;
	boolean validShipPlacement  = false;
       	int shipSize                = 0;

	if(shipType.equals(Ship.CARRIER))
	    shipSize = 5;

	else if(shipType.equals(Ship.BATTLESHIP))
	    shipSize = 4;

	else if(shipType.equals(Ship.SUBMARINE) || shipType.equals(Ship.DESTROYER))
	    shipSize = 3;

	else if(shipType.equals(Ship.PATROL_BOAT))
	    shipSize = 2;

	else {
	    System.err.println("Invalid ship type.");
	    System.exit(1);
	}

	while(!validShipPlacement) {
	    // (re)set possibleOrientations
	    for(int i = 0; i < 4; i++){
		possibleOrientations[i] = 1;
	    }
	    //reset spawn point
	    spawnPoint = (int) (Math.random() * 100);

	    // check out of bounds conditions
	    if(spawnPoint < (shipSize - 1) * 10){
		//ship cannot be oriented U
		possibleOrientations[0] = 0;
	    }	
	    if(spawnPoint > 99 - (shipSize - 1) * 10){
		//ship cannot be oriented D
		possibleOrientations[1] = 0;
	    }
	    if(spawnPoint % 10 < shipSize){
		//ship cannot be oriented L
		possibleOrientations[2] = 0;
	    }
	    if(spawnPoint % 10 > shipSize){
		//ship cannot be oriented R
		possibleOrientations[3] = 0;
	    }
	    
	    // check overlapping ship conditions
	    for(int i = 0; i < 4; i++) {
		if(possibleOrientations[i] != 0) {
		    for(int j = 0; j < shipSize; j++) {
			if(i == 0) { //check up for ships
			    if(!grid.get(spawnPoint - 10 * j).equals("~")){
				possibleOrientations[i] = 0;
			    }
			}
			else if(i == 1) { //check down for ships
			    if(!grid.get(spawnPoint + 10 * j).equals("~")){
				possibleOrientations[i] = 0;
			    }
			}
		    
			else if(i == 2) { //check left for ships
			    if(!grid.get(spawnPoint - j).equals("~")){
				possibleOrientations[i] = 0;
			    }
			}
			else if(i == 3) { //check right for ships
			    if(!grid.get(spawnPoint + j).equals("~")){
				possibleOrientations[i] = 0;
			    }
			}
		    }
		}
		
	    }
	    
	    // check to see if we have a valid spawnpoint & orientation
	    for(int i = 0; i < 4; i++) {
		if(possibleOrientations[i] == 1)
		    validShipPlacement = true;
	    }
	    
	    
	}

	while(possibleOrientations[orientationIndex] == 0){
	    orientationIndex = (int) (Math.random() * 4);
	}
	
	Ship toBePlaced  = new Ship(shipType, spawnPoint, orientations[orientationIndex]);

	// put the ship into the grid and shipMap
	for(int i = 0; i < shipSize; i++) {
	    if(orientationIndex == 0) {      //ship oriented up
		grid.put    (spawnPoint - 10 * i   , toBePlaced.getShipType());
		shipMap.put (spawnPoint - 10 * i   , toBePlaced);
	    }
	    else if(orientationIndex == 1) { //ship oriented down
		grid.put    (spawnPoint + 10 * i   , toBePlaced.getShipType());
		shipMap.put (spawnPoint + 10 * i   , toBePlaced);
	    }
	    else if(orientationIndex == 2) { //ship oriented left
		grid.put    (spawnPoint - i        , toBePlaced.getShipType());
		shipMap.put (spawnPoint - i        , toBePlaced);
	    }
	    else if(orientationIndex == 3) { //ship oriented right
		grid.put    (spawnPoint + i        , toBePlaced.getShipType());
		shipMap.put (spawnPoint + i        , toBePlaced);
	    }
	}
    }

    /**
       determines if the location has a ship in it
       @param location the location that is checked
    */

    public boolean isShipLocation(int location ){
	if(grid.get(location).equals( "~" ))
	    return false;
	else 
	    return true;
    }

    /**
      returns the ship at the location specified
      @param location the location of the ship you want to identify)
    */

    private Ship getShipAtLocation(int location){
	return shipMap.get(location);
    }

    /** 
       returns information about what the missle has accomplished, (hit, miss, sunk a ship)
       @param location the location of the missle
    */

    public String incomingMissile(int location){
	if(!isShipLocation(location)){ 
	    //if there is no ship at that location
	    return "MISS";
	}
	else{
	    Ship hitShip = getShipAtLocation(location);
	    //update the ship at that location so it knows it's been hit
	    hitShip.hit(location); 

	    //update the ship grid
	    grid.put(location, "X");
	    
	    //remove the keyvalue pair <location, ship>
	    shipMap.remove(location);
	    
	    //check to see if the ship has been sunk
	    if(hitShip.isSunk()){
		//check for winning case
		if(shipMap.size() == 0)
		    return "WIN";
		else 
		    return "SUNK " + hitShip.getFullShipName();
	    }
	    else{
		return "HIT";
	    }
	}
    }
}

