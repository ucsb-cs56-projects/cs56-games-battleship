package edu.ucsb.cs56.projects.games.battleship;

import java.util.*;
import java.io.*;
import java.lang.Math.*;

/**
 * The player class, holds a gameboard and takes in player input
 * @author Wenjian Li (working since W14)
 * @version 2.0 (CS56 Winter 2014)
*/

public class Player {
    
    private ArrayList<Integer> boats;
    private ArrayList<Integer> shots;
    //I'm trying to make the {2,3,3,4,5} array into a static array variable;
    public static int [] shipSizes = {2,3,3,4,5};
    
    /**
     * default constructor
    */
    public Player(){
		boats = new ArrayList<Integer>();
		shots = new ArrayList<Integer>();
    }
	
	/**
	 * Randomly generate a set of boats for this player
	 **/
	public void randomGenerateBoats(){
		for(int shipSize: shipSizes){
			int spawn = (int) (100*Math.random());
			int orientation = (int) (4*Math.random());
			while(!isValidSpawnAndOrientation( spawn, orientation, shipSize)){
				spawn = (int) (100*Math.random());
				orientation = (int) (4*Math.random());
			}
			addShip( spawn, orientation, shipSize);
		}
		
	}
	
	/**
	 * Add an incoming shot to the player's knowledge
	 * @param shot an incomming shot
	 **/
	public void addShot(int shot){
		this.shots.add(shot);
	}
	
	/**
	 * Compare all shots taken to boat locations to see if this player has lost
	 **/
	 
	public boolean hasLost(){
		for( Integer boatLoc: this.boats){
			if( ! shots.contains(boatLoc)) return false;
		}
		return true;
	}
	
	/**
	 * Check to see if a location exists on the game board
	 * @param loc The location in question
	 * @return true if it is false if it is not
	 **/
	 
	public boolean isValidLocal(int loc){
		if( loc >= 0 && loc < 100 ) return true;
		else return false;
	}
	
	/**
	 * Check to make sure a boat will fit on the board and not collide with any other boats.
	 * @param spawn The location that boat's head is at
	 * @param orientation The direction the boat extends in
	 * @param shipSize The size of this boat
	 * @return true if it it's valid false if it is not
	 **/
	
	public boolean isValidSpawnAndOrientation(int spawn, int orientation, int shipSize){
		if( orientation == 0){ //up
			for( int i=0; i<shipSize; i++){
				int loc = spawn - 10*i;
				if( !isValidLocal(loc) || boats.contains(loc)) return false;
			}
		}
		else if( orientation == 1){ //right
			for( int i=0; i<shipSize; i++){
				int loc = spawn + i;
				if( !isValidLocal(loc) || boats.contains(loc) || loc >= (10 * (spawn/10) + 1)) return false;
			}
		}
		else if( orientation == 2){ //down
			for( int i=0; i<shipSize; i++){
				int loc = spawn + 10*i;
				if( !isValidLocal(loc) || boats.contains(loc)) return false;
			}
		}
		else if( orientation == 3){ //left
			for( int i=0; i<shipSize; i++){
				int loc = spawn - i;
				if( !isValidLocal(loc) ||  boats.contains(loc) || loc <= (10 * (spawn/10) - 1)) return false;
			}
		}
		return true;
	}
	
	/**
	 * Add a ship to boats list
	 * @param spawn The location that boat's head is at
	 * @param orientation The direction the boat extends in
	 * @param shipSize The size of this boat
	 **/
	 
	public void addShip(int spawn, int orientation, int shipSize){
		if( orientation == 0){ //up
			for( int i=0; i<shipSize; i++){
				boats.add(spawn - 10*i);
			}
		}
		if( orientation == 1){ //right
			for( int i=0; i<shipSize; i++){
				boats.add(spawn + i);
			}
		}
		if( orientation == 2){ //down
			for( int i=0; i<shipSize; i++){
				boats.add(spawn + 10*i);
			}
		}
		if( orientation == 3){ //left
			for( int i=0; i<shipSize; i++){
				boats.add(spawn - i);
			}
		}
	}
	
	/**
	 * Get a list of boats from the GUI
	 * @param playerBoats a list of boat locations
	 **/
	 
	 public void setBoatsArrayList(ArrayList<Integer> playerBoats){
		for(Integer boat: playerBoats){
			int boatColumn = boat/10;
			boatColumn = boatColumn - 11;
			int boatRow = boat%10;
			boat = boatColumn*10 + boatRow;
			boats.add(boat);
		}
	 }
	
	/**
	 * Share the knowledge of this player's boat locations with the GUI
	 * @return A list of boat locations
	 **/
	
	public ArrayList<Integer> getBoatsArrayList(){
		return this.boats;
	}
}
