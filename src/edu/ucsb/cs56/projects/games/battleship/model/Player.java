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
		for( int shipSize: shipSizes){
			int spawn = (int) (99.99*Math.random());
			int orientation = (int) (3.99*Math.random());
			while( !isValidSpawn( spawn, orientation, shipSize))
				spawn = (int) (99.99*Math.random());
			addShip( spawn, orientation, shipSize);
		}
		
	}
	
	public boolean isValidLocal(int i){
		if( i >= 0 && i <= 99 ) return true;
		else return false;
	}
	
	public boolean isValidSpawn(int spawn, int orientation, int shipSize){
		if( orientation == 0){ //up
			for( int i=0; i<shipSize; i++){
				if( !isValidLocal(spawn - 10*i) || boats.contains(spawn - 10*i)) return false;
				else return true;
			}
		}
		if( orientation == 1){ //right
			for( int i=0; i<shipSize; i++){
				if( !isValidLocal(spawn + i) || boats.contains(spawn + i)) return false;
				else return true;
			}
		}
		if( orientation == 2){ //down
			for( int i=0; i<shipSize; i++){
				if( !isValidLocal(spawn - 10*i) || boats.contains(spawn - 10*i)) return false;
				else return true;
			}
		}
		if( orientation == 3){ //left
			for( int i=0; i<shipSize; i++){
				if( !isValidLocal(spawn - i) || boats.contains(spawn - i)) return false;
				else return true;
			}
		}
		return false;
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
				boats.add(spawn - 10*i);
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