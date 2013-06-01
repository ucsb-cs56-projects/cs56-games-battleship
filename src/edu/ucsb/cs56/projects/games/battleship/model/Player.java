package edu.ucsb.cs56.projects.games.battleship;

import java.util.*;
import java.io.*;
import java.lang.Math.*;

/**
   The player class, holds a gameboard and takes in player input
*/

public class Player {
    
    private ArrayList<Integer> boats;
    private ArrayList<Integer> shots;
    
    /**
       default constructor
    */
    public Player(){
		boats = new ArrayList<Integer>();
		shots = new ArrayList<Integer>();
    }
	
	public void randomGenerateBoats(){
		int[] shipSizes = {2,3,3,4,5};
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
	
	public boolean isValidLocal(int i){
		if( i >= 0 && i < 100 ) return true;
		else return false;
	}
	
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
	
	public ArrayList<Integer> getBoatsArrayList(){
		return this.boats;
	}
}