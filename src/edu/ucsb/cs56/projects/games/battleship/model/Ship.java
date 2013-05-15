package edu.ucsb.cs56.projects.games.battleship;

import java.util.*;

/**
   class that makes the ships
   each ship has a String shipType that describes the type of ship it is, and a Map<Integer, Boolean> shipBody that describes the status of each section of the ship
*/

public class Ship {
    
    // Possible ship types
    public static final String CARRIER     = "CARRIER";
    public static final String BATTLESHIP  = "BATTLESHIP";
    public static final String SUBMARINE   = "SUBMARINE";
    public static final String DESTROYER   = "DESTROYER";
    public static final String PATROL_BOAT = "PATROL_BOAT";
    
    private Map<Integer, Boolean> shipBody;   // map to keep track of ship's parts
    private String shipType;                  // a character to denote the ship type
    
    /**
       Constructs a ship based on a ship type.
       @param shipType one of 5 possible ships: CARRIER, DESTROYER, SUBMARINE, BATTLESHIP, PATROL_BOAT
       @param location an integer from 00-99 to represent the ships location
       @param orientation a character (U,D,L,R) to represent the ships orientation
    */
    public Ship(String shipType, int location, String orientation){
	shipBody      = new HashMap<Integer, Boolean>();
	this.shipType = "" + shipType.charAt(0);

	//determine how big to build each ship
	if(shipType.equals(Ship.CARRIER)) {
	    if(orientation.equals("U")){
		for(int i = 0; i < 5; i++){
		    shipBody.put(location - 10 * i, true);
		}
	    }
	    
	    else if(orientation.equals("D")){
		for(int i = 0; i < 5; i++){
		    shipBody.put(location + 10 * i, true);
		}
	    }
	    
	    else if(orientation.equals("L")){
		for(int i = 0; i < 5; i++){
		    shipBody.put(location - i, true);
		}
	    }

	    else if(orientation.equals("R")){
		for(int i = 0; i < 5; i++){
		    shipBody.put(location + i, true);
		}
	    }
		    

	}
	else if(shipType.equals(Ship.BATTLESHIP)) {
	    if(orientation.equals("U")){
		for(int i = 0; i < 4; i++){
		    shipBody.put(location - 10 * i, true);
		}
	    }
	    
	    else if(orientation.equals("D")){
		for(int i = 0; i < 4; i++){
		    shipBody.put(location + 10 * i, true);
		}
	    }
	    
	    else if(orientation.equals("L")){
		for(int i = 0; i < 4; i++){
		    shipBody.put(location - i, true);
		}
	    }

	    else if(orientation.equals("R")){
		for(int i = 0; i < 4; i++){
		    shipBody.put(location + i, true);
		}
	    }
	}
	else if(shipType.equals(Ship.SUBMARINE)) {
	    if(orientation.equals("U")){
		for(int i = 0; i < 3; i++){
		    shipBody.put(location - 10 * i, true);
		}
	    }
	    
	    else if(orientation.equals("D")){
		for(int i = 0; i < 3; i++){
		    shipBody.put(location + 10 * i, true);
		}
	    }
	    
	    else if(orientation.equals("L")){
		for(int i = 0; i < 3; i++){
		    shipBody.put(location - i, true);
		}
	    }

	    else if(orientation.equals("R")){
		for(int i = 0; i < 3; i++){
		    shipBody.put(location + i, true);
		}
	    }
	}
	else if(shipType.equals(Ship.DESTROYER)) {
	    if(orientation.equals("U")){
		for(int i = 0; i < 3; i++){
		    shipBody.put(location - 10 * i, true);
		}
	    }
	    
	    else if(orientation.equals("D")){
		for(int i = 0; i < 3; i++){
		    shipBody.put(location + 10 * i, true);
		}
	    }
	    
	    else if(orientation.equals("L")){
		for(int i = 0; i < 3; i++){
		    shipBody.put(location - i, true);
		}
	    }

	    else if(orientation.equals("R")){
		for(int i = 0; i < 3; i++){
		    shipBody.put(location + i, true);
		}
	    }
	}
	else if(shipType.equals(Ship.PATROL_BOAT)) {
	    	    if(orientation.equals("U")){
		for(int i = 0; i < 2; i++){
		    shipBody.put(location - 10 * i, true);
		}
	    }
	    
	    else if(orientation.equals("D")){
		for(int i = 0; i < 2; i++){
		    shipBody.put(location + 10 * i, true);
		}
	    }
	    
	    else if(orientation.equals("L")){
		for(int i = 0; i < 2; i++){
		    shipBody.put(location - i, true);
		}
	    }

	    else if(orientation.equals("R")){
		for(int i = 0; i < 2; i++){
		    shipBody.put(location + i, true);
		}
	    }
	}
	else {
	    System.err.println("Invalid Ship Type");
	}
    }

    public int getLength(){
	return shipBody.size();
    }
    
    public String getShipType() {
	return shipType;
    }

    /**
       Updates the ship to reflect a hit at the specified location
       @param location The int representing the area of the ship hit
    */
    public void hit(int location) {
	shipBody.put(location, false);
    }

    /**
       Determines if the ship has been sunk
    */
    public boolean isSunk(){
	if(shipBody.containsValue(true)){
	    return false;
	}
	else return true;
    }

    public String getFullShipName(){
	String type = "";
      
	if(shipType.equals("C")){
	    type = "CARRIER";
	}
	if(shipType.equals("B")){
	    type = "BATTLESHIP";
	}
	if(shipType.equals("D")){
	    type = "DESTROYER";
	}
	if(shipType.equals("S")){
	    type = "SUBMARINE";
	}
	if(shipType.equals("P")){
	    type = "PATROL_BOAT";
	}
	return type;		
    }
}
