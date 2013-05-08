import java.util.*;

/**
A test class for coloring specific squares in BattleshipGUI
*/

public class BoardStatus{
	private ArrayList<BoardStatus.Tuple> shots = new ArrayList<BoardStatus.Tuple>();
	private ArrayList<BoardStatus.Tuple> enemyBoats = new ArrayList<BoardStatus.Tuple>();
	private ArrayList<BoardStatus.Tuple> playerBoats = new ArrayList<BoardStatus.Tuple>();
	
	BoardStatus(String test){
		Tuple tup0 = new Tuple(0,0);
		Tuple tup1 = new Tuple(1,1);
		Tuple tup2 = new Tuple(5,4);
		Tuple tup3 = new Tuple(9,20);
		Tuple tup4 = new Tuple(9,16);
		
		this.shots.add(tup0);
		this.shots.add(tup1);
		this.shots.add(tup4);
		
		this.enemyBoats.add(tup1);
		this.enemyBoats.add(tup2);
		this.playerBoats.add(tup3);
	}
	
	public boolean isHit(int x, int y){
		Tuple tup = new Tuple(x,y);
		if(this.shots.contains(tup) && (this.playerBoats.contains(tup) || this.enemyBoats.contains(tup))) return true;
		else return false;
	}
	
	public boolean isPlayerBoat(int x, int y){
		Tuple tup = new Tuple(x,y);
		if( y < 11 ) return false;
		else if(this.playerBoats.contains(tup)) return true;
		else return false;
	}
	
	public boolean isShot(int x, int y){
		Tuple tup = new Tuple(x,y);
		if(this.shots.contains(tup)) return true;
		else return false;
	}
	
	public void addShot(int x, int y){
		Tuple tup = new Tuple(x,y);
		this.shots.add(tup);
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
			if( ! (o instanceof Tuple)) return false;
			Tuple tup = (Tuple) o;
			
			if(this.x == tup.x && this.y == tup.y) return true;
			else return false;	
		}
	}
}