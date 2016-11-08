package edu.ucsb.cs56.projects.games.battleship;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Main class for Battleship Game
 * @author Wenjian Li (working since W14)
 * @author Chang Rey Tang (W16)
 * @author Joseph Song (W16)
 * @version 2.2 (CS56 Winter 2016)
*/

public class BattleshipController {
    private String previousIP = "";		//keep track of IP address that game joiner previously used
    /**
     * Let the thread to sleep
    */

    public static void sleep(){
        try{
            Thread.sleep(10);
        }
        catch (InterruptedException e){
        }
    }


	/**
	 * Method to make program wait until options have been set
	*/
    public void wait(BattleshipGUI gui){
        while(gui.getDifficulty() == null || gui.getGameType() == 0 ){
			BattleshipController.sleep();
		}
	}

    public void waitReplay(BattleshipGUI gui) {
        while(gui.getReplayType() == 0) {
            BattleshipController.sleep();
        }
    }

	/**
	 * Method for hosting a game
	*/
	public void hostGame(BattleshipGUI gui){
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

	/**
	 * method for joining a game
	*/
	public void joinGame(BattleshipGUI gui){
		gui.setTitle("Battleship : Player 2");
	
		Player player2 = new Player();
		
		//Wait until an IP address has been entered
		while( !gui.getIPEntered()){
			BattleshipController.sleep();
		}
		String connectTo = gui.getIP();
		previousIP = connectTo;

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

		ArrayList<Integer> player1BoatsList = gui.getEnemyBoats();
		ArrayList<ArrayList<Integer>> player1BoatGroups = new ArrayList<ArrayList<Integer>>();


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
		
		//Begin game
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
		try{
			player1Socket.close();
			toPlayer1.close();
			fromPlayer1.close();
		}
		catch (IOException e){
		}
	}

	public void joinGameAgain(BattleshipGUI gui){
		gui.setTitle("Battleship : Player 2");
	
		Player player2 = new Player();
		
		String connectTo = previousIP;

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

		ArrayList<Integer> player1BoatsList = gui.getEnemyBoats();
		ArrayList<ArrayList<Integer>> player1BoatGroups = new ArrayList<ArrayList<Integer>>();


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
		
		//Begin game
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
		try{
			player1Socket.close();
			toPlayer1.close();
			fromPlayer1.close();
		}
		catch (IOException e){
		}
	}

    /** method for playing against computer
	 * 
     **/
    public void playComputer(BattleshipGUI gui){
		//Setup the players
	    Player human = new Player();
		
		gui.setMessage("Place your boats. Use any key to change orientation");
		gui.placeBoats();
		human.setBoatsArrayList(gui.getPlayerBoats());
        
	    Computer computer = new Computer(gui.getDifficulty(), human.getBoatsArrayList());
		
		//Send information about ship locations to the GUI
		gui.addPlayerBoats(human.getBoatsArrayList());
		gui.addEnemyBoats(computer.getBoatsArrayList());
		
		//Start the game
	    while(true) {

            //Start player's move
            gui.makeMove();
            gui.setMessage("Your turn! (Now you've hit " + human.getHitCount() + " pixels and sunk "  + human.getBoatCount() + " boats)");
            while(gui.getPlayersTurn()){
                BattleshipController.sleep();
            }

            //Get player's move from GUI and send it to model
            int humanMove = gui.getLastMove();
            computer.addShot(humanMove);
            if(gui.getEnemyBoats().contains(humanMove)) {
                human.increaseHitCount();
                gui.playAudioFile(gui.shotURL);
		//checkBoatCount(humanMove);

	         ArrayList<ArrayList<Integer>> groups = computer.getBoatGroups();
		 for(int i = 0; i < groups.size(); i++){
		     ArrayList<Integer> array = groups.get(i);
		     for (int j = 0; j < array.size(); j++){
			 if (array.get(j) == humanMove){
			     array.remove(j);
			 }
		     }
		 }
		 for(int i = 0; i < groups.size(); i++){
		     if (groups.get(i).isEmpty()){
			 groups.remove(i);
			 human.incrementBoatCount();
		     }
		 }
		 computer.setBoatGroups(groups);
            }
            else
                gui.playAudioFile(gui.missURL);

            //Check win status
            if(computer.hasLost()) {
                gui.setMessage("CONGRATULATIONS, YOU WIN!");
                gui.playAudioFile(gui.winURL);
                break;
            }

            //Start computer's move
            /*
            try{
                Thread.sleep(300);
            } catch(InterruptedException e){
                System.out.println(e);
            }
            */
            int computerMove = computer.makeMove();
            gui.addShot(gui.shiftToPlayerGrid(computerMove));
            human.addShot(computerMove);
            computer.updateGuessGrid(computerMove, gui.hitPlayer(gui.shiftToPlayerGrid(computerMove)));

            //Check win status
            if(human.hasLost()) {
                gui.setMessage("OH NO, YOU LOSE!");
                gui.playAudioFile(gui.loseURL);
                break;
            }
            //back up to player's move
        }
    }
    /*   public void checkBoatCount(int shot){
	   ArrayList<ArrayList<Integer>> groups = computer.getBoatGroups();
	for(int i = 0; i < groups.size(); i++){
	    ArrayList<Integer> array = groups.get(i);
	    for (int j = 0; j < array.size(); j++){
		if (array.get(j) == shot){
		    array.remove(j);
		}
	    }
	}
	for(int i = 0; i < groups.size(); i++){
	    if (groups.get(i).isEmpty()){
		groups.remove(i);
		human.increaseBoatCount();
	    }
	}
	computer.setBoatGroups(groups);
	}*/

     /** 
     * method for the game to run
     */

    public void go() {
		BattleshipGUI gui = new BattleshipGUI();
		gui.reset();
		gui.setOptions();
		this.wait(gui);

        //Hosting a game
        if(gui.getGameType() == 1){
            this.hostGame(gui);
        }
	
        //Joining a game
        if(gui.getGameType() == 2){
            this.joinGame(gui);
        }
	
        if(gui.getGameType() == 3) {
            this.playComputer(gui);
        }
        this.endOfGame(gui);
    }

	/**
	 * method for displaying end of game message
	*/
	public void endOfGame(BattleshipGUI gui){
		String currentMessage = gui.getMessage();
		gui.setMessage(currentMessage + " THANK YOU FOR PLAYING");
		if(gui.getGameType() == 3) {
		    gui.computerPlayAgain();
		    this.waitReplay(gui);
		    this.computerPlayAgain(gui);
		}
		else{
		  gui.networkPlayAgain();
		  this.waitReplay(gui);
		  this.networkPlayAgain(gui);
		}    
	}

    public void computerPlayAgain(BattleshipGUI gui){
        if(gui.getReplayType() == 1) {	
            gui.end();
            gui = new BattleshipGUI();
            gui.resetPlace();
            this.wait(gui);

            this.playComputer(gui);
            this.endOfGame(gui);
        }
        if(gui.getReplayType() == 2) {
            gui.end();
            gui = new BattleshipGUI();
            gui.resetShips();
            this.wait(gui);

            this.playComputer(gui);
            this.endOfGame(gui);
        }
        if(gui.getReplayType() == 3) {
            gui.end();
            gui = new BattleshipGUI();
            gui.reset();
            gui.setOptions();
            this.wait(gui);

            if(gui.getGameType() == 1) {
                this.hostGame(gui);
            }
            if(gui.getGameType() == 2) {
                this.joinGame(gui);
            }
            if(gui.getGameType() == 3) {
                this.playComputer(gui);
            }
            this.endOfGame(gui);
        }
    }

     public void networkPlayAgain(BattleshipGUI gui){

		if(gui.getReplayType() == 5) {		//join game
            gui.end();
            gui = new BattleshipGUI();
            gui.resetForIP();
            this.wait(gui);

            this.joinGame(gui);
            this.endOfGame(gui);
        }

		if(gui.getReplayType() == 6) {		//host game
            gui.end();
            gui = new BattleshipGUI();
            gui.resetForHost();
            this.wait(gui);

            this.hostGame(gui);

            this.endOfGame(gui);
        }
		if(gui.getReplayType() == 4) {		//Play again
			if(gui.getGameType == 1){		//hosting again
            	gui.end();
           		gui = new BattleshipGUI();
            	gui.resetForHost();
            	this.wait(gui);

            	this.hostGame(gui);

            	this.endOfGame(gui);
			}
			else{							//joining again
				gui.end();
				gui = new BattleshipGUI();
				gui.resetForJoinAgain();
				this.wait(gui);

				this.joinGameAgain(gui);

				this.endOfGame();

			}
        }
		if(gui.getReplayType() == 3) {		//main menu
            gui.end();
            gui = new BattleshipGUI();
            gui.reset();
            gui.setOptions();
            this.wait(gui);

            if(gui.getGameType() == 1) {
                this.hostGame(gui);
            }
            if(gui.getGameType() == 2) {
                this.joinGame(gui);
            }
            if(gui.getGameType() == 3) {
                this.playComputer(gui);
            }
            this.endOfGame(gui);
        }
	 }

    public static void main(String[] args){
		BattleshipController myController = new BattleshipController();
		myController.go();
	}

}
