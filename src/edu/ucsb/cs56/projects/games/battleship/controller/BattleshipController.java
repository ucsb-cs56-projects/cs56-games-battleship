package edu.ucsb.cs56.projects.games.battleship;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
   Main class for Battleship Game
*/

public class BattleshipController {
    
    public static void main(String[] args) {
	
		BattleshipGUI gui = new BattleshipGUI();
		gui.setOptions();
		
		//Makes program wait until options have been set.
		while(gui.getDifficulty() == null || gui.getGameType() == 0){
			try{
				Thread.sleep(10);
			}
			catch (InterruptedException e){
			}
		
		}
	
	//Hosting a game
	if(gui.getGameType() == 1){
		gui.setTitle("Battleship : Player 1");
	
		Player player1 = new Player();
		player1.randomGenerateBoats();
		gui.addPlayerBoats(player1.getBoatsArrayList());
		
		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(22222);
		}
		catch(IOException e){
			System.out.println("Could not listen on port: 22222");
		}
		try{
			gui.setMessage("Listening for player to connect on " + InetAddress.getLocalHost().getHostAddress() + " : " + serverSocket.getLocalPort());
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
		
		gui.setMessage("Connection recieved from " + player2Socket.getLocalAddress());
		
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
		
		//Get player 1's boat locations
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
		
		//Begin game
		while(true){
			try{
				gui.makeMove();
				gui.setMessage("Your turn!");
				
				//Wait until player 1 has completed their turn
				while(gui.getPlayersTurn()){
					try{
						Thread.sleep(10);
					}
					catch (InterruptedException e){
					}
				}
				int p1Move = gui.getLastMove();
				toPlayer2.println(p1Move); //Send move to player 2
				
				//Check if you've won
				String p2VictoryStatus = fromPlayer2.readLine();
				if(p2VictoryStatus.equals("LOSE")){
					gui.setMessage("CONGRATULATIONS, YOU WIN!");
					break;
				}
				
				//Get player 2's move
				gui.setMessage("Waiting for player 2's move.");
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
	}
	
	//Joining a game
	if(gui.getGameType() == 2){
		gui.setTitle("Battleship : Player 2");
	
		Player player2 = new Player();
		player2.randomGenerateBoats();
		gui.addPlayerBoats(player2.getBoatsArrayList());
		
		//Wait until an IP address has been entered
		while( !gui.getIPEntered()){
			try{
				Thread.sleep(10);
			}
			catch (InterruptedException e){
			}
		}
		String connectTo = gui.getIP();

		Socket player1Socket = null;
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
		
		PrintWriter toPlayer1 = null;
		BufferedReader fromPlayer1 = null;
		
		try{
			toPlayer1 = new PrintWriter(player1Socket.getOutputStream(),true);
			fromPlayer1 = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
		}
		catch(IOException e){
			gui.setMessage("Error getting input/output streams from Player 1");
			System.out.println("Error getting input/output streams from Player 1");
		}
		
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
		
		while(true){
			try{
				//Wait for player 1's move
				gui.setMessage("Waiting for player 1's move.");
				int p1Move = Integer.parseInt(fromPlayer1.readLine());
				gui.addShot(gui.shiftToPlayerGrid(p1Move));
				player2.addShot(p1Move);
				
				//Check to see if you've lost
				if(player2.hasLost()){
					toPlayer1.println("LOSE");
					gui.setMessage("OH NO, YOU LOSE!");
					break;
				}
				else
					toPlayer1.println("CONTINUE");
				
				gui.makeMove();
				gui.setMessage("Your turn!");
				//Halt the program until you've completed your move
				while(gui.getPlayersTurn()){
					try{
						Thread.sleep(10);
					}
					catch (InterruptedException e){
					}
				}
				int p2Move = gui.getLastMove();
				toPlayer1.println(p2Move);
				
				//Check to see if you've won
				String p1VictoryStatus = fromPlayer1.readLine();
				if(p1VictoryStatus.equals("LOSE")){
					gui.setMessage("CONGRATULATIONS, YOU WIN!");
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
	
	if(gui.getGameType() == 3) {
	
		//Setup the players
	    Player human = new Player();
		human.randomGenerateBoats();
	    Computer computer = new Computer(gui.getDifficulty());
		
		//Send information about ship locations to the GUI
		gui.addPlayerBoats(human.getBoatsArrayList());
		gui.addEnemyBoats(computer.getBoatsArrayList());
		
		//Start the game
	    while(true) {
		
		//Start player's move
		gui.makeMove();
		gui.setMessage("Your turn!");
		while(gui.getPlayersTurn()){
			try{
				Thread.sleep(10);
			}
			catch (InterruptedException e){
			}
		}
		
		//Get player's move from GUI and send it to model
		int humanMove = gui.getLastMove();
		computer.addShot(humanMove);
		
		//Check win status
		if(computer.hasLost()) {
		    gui.setMessage("CONGRATULATIONS, YOU WIN!");
		    break;
		}
		
		//Start computer's move
		int computerMove = computer.makeMove();
		gui.addShot(gui.shiftToPlayerGrid(computerMove));
		human.addShot(computerMove);
		computer.updateGuessGrid(computerMove, gui.hitPlayer(gui.shiftToPlayerGrid(computerMove)));
		
		//Check win status
		if(human.hasLost()) {
		    gui.setMessage("OH NO, YOU LOSE!");
		    break;
		}
		//back up to player's move
	    }
	}
	//Display end of game message
	String currentMessage = gui.getMessage();
	gui.setMessage(currentMessage + " THANK YOU FOR PLAYING");
    }

}
