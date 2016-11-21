package edu.ucsb.cs56.projects.games.battleship;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class JoinGameController extends NetworkController{
	private String connectTo = "";	
	Player player2;
	Socket player1Socket = null;
	PrintWriter toPlayer1 = null;
	BufferedReader fromPlayer1 = null;
	ArrayList<Integer> player1BoatsList;
	ArrayList<ArrayList<Integer>> player1BoatGroups;
	/**
	 * method for joining a game
	*/
	public void go(BattleshipGUI gui){
		gui.setTitle("Battleship : Player 2");
	
		player2 = new Player();
		
		System.out.println("before enter");
		//Wait until an IP address has been entered
		while( !gui.getIPEntered()){
			BattleshipController.sleep();
		}
		connectTo = gui.getIP();

		player1Socket = null;
		try{
			player1Socket = new Socket(connectTo,22222);
		}
		catch(UnknownHostException e){
			gui.setMessage("Can't find host: " + connectTo);
			System.out.println("Can't find host:" + connectTo);
		}
		catch(IOException e){
			gui.setMessage("IOException when connecting to host.");
			System.out.println("IOException when connecting to host.");
		}
		
		gui.setMessage("Connected to " + player1Socket.getLocalAddress());
		
		try{
			toPlayer1 = new PrintWriter(player1Socket.getOutputStream(),true);
			fromPlayer1 = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
		}
		catch(IOException e){
			gui.setMessage("Error getting input/output streams from Player 1");
			System.out.println("Error getting input/output streams from Player 1");
		}
		
		gui.setMessage("Place your boats. Use any key to change orientation");
		gui.placeBoats();
		player2.setBoatsArrayList(gui.getPlayerBoats());
		gui.setMessage("Waiting for player 1 to place their boats.");
		
		//Send boat locations to player 1
		try{
			ArrayList<Integer> player2Boats = player2.getBoatsArrayList();
			for( Integer boatLoc: player2Boats){
				toPlayer1.println(boatLoc);
			}
			toPlayer1.println("DONE");
		}
		catch(Exception e){
			gui.setMessage("Error sending player2's boats");
			System.out.println("Error sending player2's boats");
		}
		 //Get boat locations from player 1
		try{
			String boatLoc = fromPlayer1.readLine();
			while( ! boatLoc.equals("DONE")){
				gui.addEnemyBoat(Integer.parseInt(boatLoc));
				boatLoc = fromPlayer1.readLine();
			}
		}
		catch(Exception e){
			gui.setMessage("Error getting boats from player 1");
			System.out.println("Error getting boats from player1 ");
		}	

		player1BoatsList = gui.getEnemyBoats();
		player1BoatGroups = new ArrayList<ArrayList<Integer>>();

		player2.setDefaultShipSizes();
	 	int[] player2ShipSizes = player2.getShipSizes();
		int iterator =0; 

		for (int i : player2ShipSizes){
			ArrayList<Integer> player1BoatConvert = new ArrayList<Integer>();
			for (int j=0; j<i ; j++){
				player1BoatConvert.add(player1BoatsList.get(iterator));
				iterator++;
			}
			player1BoatGroups.add(player1BoatConvert);
		}

		player2.setBoatGroups(player1BoatGroups);
		joinGamePlay(gui, player2);

		try{
			player1Socket.close();
			toPlayer1.close();
			fromPlayer1.close();
		}
		catch (IOException e){
		}
	}

	public void joinGamePlay(BattleshipGUI gui, Player player2){
		while(true){
			try{
				//Wait for player 1's move
				gui.setMessage("Waiting for player 1's move. You have hit "+ player2.getHitCount() + " pixels and sunk " + player2.getBoatCount() + " boats"  );
				int p1Move = Integer.parseInt(fromPlayer1.readLine());
				gui.addShot(gui.shiftToPlayerGrid(p1Move));
				player2.addShot(p1Move);
				
				//Check to see if you've lost
				if(player2.hasLost()){
					toPlayer1.println("LOSE");
					gui.setMessage("OH NO, YOU LOSE!");
                    gui.playAudioFile(gui.loseURL);
					break;
				}
				else
					toPlayer1.println("CONTINUE");
				
				gui.makeMove();
				gui.setMessage("Your turn! Now you've hit " + player2.getHitCount() + " pixels and sunk " + player2.getBoatCount() + " boats"  );
				//Halt the program until you've completed your move
				while(gui.getPlayersTurn()){
					BattleshipController.sleep();
				}
				int p2Move = gui.getLastMove();
				toPlayer1.println(p2Move);
				if(gui.getEnemyBoats().contains(p2Move)){
					player2.increaseHitCount();
					for(int i = 0; i < player1BoatGroups.size(); i++){
		     			ArrayList<Integer> array = player1BoatGroups.get(i);
		     			for (int j = 0; j < array.size(); j++){
			 				if (array.get(j) == p2Move){
			     			array.remove(j);
			 				}
		     			}
		 			}
		 			for(int i = 0; i < player1BoatGroups.size(); i++){
		     			if (player1BoatGroups.get(i).isEmpty()){
			 				player1BoatGroups.remove(i);
			 				player2.incrementBoatCount();
		     			}
				 	}
		 			player2.setBoatGroups(player1BoatGroups);
				}
				
				//Check to see if you've won
				String p1VictoryStatus = fromPlayer1.readLine();
				if(p1VictoryStatus.equals("LOSE")){
					gui.setMessage("CONGRATULATIONS, YOU WIN!");
                    gui.playAudioFile(gui.loseURL);
					break;
				}
				
			}
			catch(IOException e){
				System.out.println("Something went wrong while reading from or writing to player 1");
				gui.setMessage("Something went wrong while reading from or writing to player 1");
				System.exit(-1);
			}
		}
	}

	public void playAgainSameIP(BattleshipGUI gui){
		gui.end();
		gui = new BattleshipGUI();
		gui.resetForJoinAgain();
		this.wait(gui);

		//JoinGameController joinGame = new JoinGameController();
		this.joinGameAgain(gui);
		this.endOfGame(gui);
	}
	public void joinGameAgain(BattleshipGUI gui){
		System.out.println("join Game again");
		gui.setTitle("Battleship : Player 2");
	
		player2 = new Player();
		
		player1Socket = null;
		boolean ex = true;
		do{
			try{
				player1Socket = new Socket(connectTo,22222);
				ex = false;
			}
			catch(UnknownHostException e){
				ex = true;
			}
			catch(IOException e){
				ex = true;
				gui.setMessage("Waiting for host to play with you again...");
			}
		}
		while(ex);
		
		gui.setMessage("Connected to " + player1Socket.getLocalAddress());
		
	    toPlayer1 = null;
		fromPlayer1 = null;
		
		try{
			toPlayer1 = new PrintWriter(player1Socket.getOutputStream(),true);
			fromPlayer1 = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
		}
		catch(IOException e){
			gui.setMessage("Error getting input/output streams from Player 1");
			System.out.println("Error getting input/output streams from Player 1");
		}
		
		gui.setMessage("Place your boats. Use any key to change orientation");
		gui.placeBoats();
		player2.setBoatsArrayList(gui.getPlayerBoats());
		gui.setMessage("Waiting for player 1 to place their boats.");
		
		//Send boat locations to player 1
		try{
			ArrayList<Integer> player2Boats = player2.getBoatsArrayList();
			for( Integer boatLoc: player2Boats){
				toPlayer1.println(boatLoc);
			}
			toPlayer1.println("DONE");
		}
		catch(Exception e){
			gui.setMessage("Error sending player2's boats");
			System.out.println("Error sending player2's boats");
		}
		 //Get boat locations from player 1
		try{
			String boatLoc = fromPlayer1.readLine();
			while( ! boatLoc.equals("DONE")){
				gui.addEnemyBoat(Integer.parseInt(boatLoc));
				boatLoc = fromPlayer1.readLine();
			}
		}
		catch(Exception e){
			gui.setMessage("Error getting boats from player 1");
			System.out.println("Error getting boats from player1 ");
		}	

		player1BoatsList = gui.getEnemyBoats();
		player1BoatGroups = new ArrayList<ArrayList<Integer>>();


	 	int[] player2ShipSizes = player2.getShipSizes();
		int iterator =0; 

		for (int i : player2ShipSizes){
			ArrayList<Integer> player1BoatConvert = new ArrayList<Integer>();
			for (int j=0; j<i ; j++){
				player1BoatConvert.add(player1BoatsList.get(iterator));
				iterator++;
			}
			player1BoatGroups.add(player1BoatConvert);
		}

		player2.setBoatGroups(player1BoatGroups);
		joinGamePlay(gui, player2);
		try{
			player1Socket.close();
			toPlayer1.close();
			fromPlayer1.close();
		}
		catch (IOException e){
		}

	}

}