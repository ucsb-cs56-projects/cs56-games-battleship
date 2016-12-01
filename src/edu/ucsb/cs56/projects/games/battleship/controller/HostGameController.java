package edu.ucsb.cs56.projects.games.battleship;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class HostGameController extends NetworkController{

	/**
	 * Method for hosting a game
	*/
	public void go(BattleshipGUI gui){
		gui.setTitle("Battleship : Player 1");
	
		Player player1 = new Player();
		
		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(22222);
		}
		catch(IOException e){
			System.out.println("Could not listen on port: 22222");
		}
		try{
			gui.setMessage("Listening for player to connect on " + InetAddress.getLocalHost().getHostAddress());
		}
		catch(UnknownHostException e){
			gui.setMessage("Unknown Host");
			System.out.println("Uknown host exception: " + e);
		}

		Socket player2Socket = null;
		try{
			player2Socket = serverSocket.accept();
		}
		catch(IOException e){
			gui.setMessage("Accept failed on port 22222");
			System.out.println("Accept failed on port 22222");
		}
		
		PrintWriter toPlayer2 = null;
		BufferedReader fromPlayer2 = null;
		
		try{
			toPlayer2 = new PrintWriter(player2Socket.getOutputStream(), true);
			fromPlayer2 = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
		}
		catch(IOException e){
			gui.setMessage("Error setting up input/output streams from Player 2");
			System.out.println("Error setting up input/output streams from Player 2");
		}

		
		gui.setMessage("Place your boats. Use any key to change orientation");
		gui.placeBoats();
		player1.setBoatsArrayList(gui.getPlayerBoats());
		gui.setMessage("Waiting for player 2 to place their boats.");
		
		
		//Send boat locations to player 2
		try{
			ArrayList<Integer> player1Boats = player1.getBoatsArrayList();
			for( Integer boatLoc: player1Boats){
				toPlayer2.println(boatLoc);
			}
			toPlayer2.println("DONE");
		}
		catch(Exception e){
			gui.setMessage("Error sending player1's boats");
			System.out.println("Error sending player1's boats");
		}
		
		//Get player 2's boat locations
		try{
			String boatLoc = fromPlayer2.readLine();
			while( ! boatLoc.equals("DONE")){
				gui.addEnemyBoat(Integer.parseInt(boatLoc));
				boatLoc = fromPlayer2.readLine();
			}
		}
		catch(Exception e){
			gui.setMessage("Error getting boats from player2");
			System.out.println("Error getting boats from player2");
		}	

		ArrayList<Integer> player2BoatsList = gui.getEnemyBoats();
		ArrayList<ArrayList<Integer>> player2BoatGroups = new ArrayList<ArrayList<Integer>>();


	 	int[] player1ShipSizes = player1.getShipSizes();
		int iterator =0; 

		for (int i : player1ShipSizes){
			ArrayList<Integer> player2BoatConvert = new ArrayList<Integer>();
			for (int j=0; j<i ; j++){
				player2BoatConvert.add(player2BoatsList.get(iterator));
				iterator++;
			}
			player2BoatGroups.add(player2BoatConvert);
		}

		player1.setBoatGroups(player2BoatGroups);
		
		//Begin game
		while(true){
			try{
				gui.makeMove();
				gui.setMessage("Your turn! Now you've hit " + player1.getHitCount() + " pixels and sunk " + player1.getBoatCount() + " boats"  );
				
				//Wait until player 1 has completed their turn
				while(gui.getPlayersTurn()){
					BattleshipController.sleep();
				}
				int p1Move = gui.getLastMove();
				toPlayer2.println(p1Move); //Send move to player 2
				if(gui.getEnemyBoats().contains(p1Move)){
					player1.increaseHitCount();
		 			for(int i = 0; i < player2BoatGroups.size(); i++){
		     			ArrayList<Integer> array = player2BoatGroups.get(i);
		     			for (int j = 0; j < array.size(); j++){
			 				if (array.get(j) == p1Move){
			     			array.remove(j);
			 				}
		     			}
		 			}
		 			for(int i = 0; i < player2BoatGroups.size(); i++){
		     			if (player2BoatGroups.get(i).isEmpty()){
			 				player2BoatGroups.remove(i);
			 				player1.incrementBoatCount();
		     			}
				 	}
		 			player1.setBoatGroups(player2BoatGroups);
				}
				
				//Check if you've won
				String p2VictoryStatus = fromPlayer2.readLine();
				if(p2VictoryStatus.equals("LOSE")){
					gui.setMessage("CONGRATULATIONS, YOU WIN!");
					break;
				}
				
				//Get player 2's move
				gui.setMessage("Waiting for player 2's move. You have hit " + player1.getHitCount() + " pixels and sunk " + player1.getBoatCount() + " boats"  );
				int p2Move = Integer.parseInt(fromPlayer2.readLine());
				gui.addShot(gui.shiftToPlayerGrid(p2Move));
				player1.addShot(p2Move);
				
				//Check if you've lost
				if(player1.hasLost()){
					toPlayer2.println("LOSE");
					gui.setMessage("OH NO, YOU LOSE!");
					break;
				}
				else
					toPlayer2.println("CONTINUE");
			}
			catch(IOException e){
				System.out.println("Something went wrong while reading from or writing to player 2");
				gui.setMessage("Something went wrong while reading from or writing to player 2");
				System.exit(-1);
			}
		}
		try{
		serverSocket.close();
		player2Socket.close();
		toPlayer2.close();
		fromPlayer2.close();
		}
		catch (IOException e){
		}
	}

	public void playAgainSameIP(BattleshipGUI gui){
		gui.end();
        gui = new BattleshipGUI();
        gui.resetForHost();
        this.wait(gui);
        HostGameController hostGame = new HostGameController();
        hostGame.go(gui);

		this.endOfGame(gui);
	}


}