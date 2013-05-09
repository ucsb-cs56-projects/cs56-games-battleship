package edu.ucsb.cs56.projects.games.battleship;
import java.util.*;

/**
A test class for coloring specific squares in BattleshipGUI
*/

public class BoardStatus{
	private ArrayList<BoardStatus.Tuple> shots = new ArrayList<Tuple>();
	private ArrayList<BoardStatus.Tuple> enemyBoats = new ArrayList<Tuple>();
	private ArrayList<BoardStatus.Tuple> playerBoats = new ArrayList<Tuple>();
	
	BoardStatus(){
		super();
	}
	
	public boolean isHit(int x, int y){
		Tuple tup = new Tuple(x,y);
		if(this.shots.contains(tup) && (this.playerBoats.contains(tup) || this.enemyBoats.contains(tup))) return true;
		else return false;
	}
	
	public boolean isPlayerBoat(int x, int y){
		Tuple tup = new Tuple(x,y);
		if( y < 11 ) return false;
		return this.playerBoats.contains(tup);
	}
	
	public boolean isShot(int x, int y){
		Tuple tup = new Tuple(x,y);
		return this.shots.contains(tup);
	}
	
	public void addShot(int x, int y){
		Tuple tup = new Tuple(x,y);
		if(!this.shots.contains(tup))
			this.shots.add(tup);
	}
	
	public boolean humanWin(){
		for(Tuple tup:enemyBoats){
			if(!shots.contains(tup))
				return false;
		}
		return true;
	}
	
	public boolean computerWin(){
		for(Tuple tup: playerBoats){
			if(!shots.contains(tup))
				return false;
		}
		return true;
	}
	
	
	public class Tuple{
		public int x;
		public int y;
	
		Tuple(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public boolean equals(Object o){
			if( o == null) return false;
			if( ! (o instanceof Tuple)){ 
			return false;
			}
			Tuple tup = (Tuple) o;
			
			if(this.x == tup.x && this.y == tup.y){
			return true;
			}
			else return false;	
		}
	}
}