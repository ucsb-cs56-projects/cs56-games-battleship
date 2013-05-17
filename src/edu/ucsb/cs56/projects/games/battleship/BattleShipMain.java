package edu.ucsb.cs56.projects.games.battleship;

import java.io.*;
import java.net.*;

/**
   Dis da main class, son
*/

public class BattleShipMain {
    
    public static void main(String[] args) {
	
	System.out.println("WELCOME TO BATTLESHIP!");
	System.out.println("WHAT DO YOU WANT TO DO?");
	System.out.println("1) HOST A GAME \n" +
                           "2) JOIN A GAME \n" +
			   "3) PLAY AGAINST A COMPUTER");
	
	//  open up standard input
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	String gameChoice = null;

	//  read the choice from the command-line; need to use try/catch with the
	//  readLine() method
	while(gameChoice == null || !isValid(gameChoice, "intro")) {
	    try {
		gameChoice = br.readLine();
	    } 
	    catch (IOException ioe) {
		System.err.println("IO error trying to read your selection!" + ioe.getMessage());
		System.exit(1);
	    }
	}
	
	// hosting a game
	if(gameChoice.equals("1")) {

	    Player player1 = new Player();
	    
	    ServerSocket serverSocket = null;
	    try {
		serverSocket = new ServerSocket(22222);
	    }
	    catch (IOException e) {
		System.out.println("Could not listen on port: 22222");
		System.exit(-1);
	    }
	    try {
		System.out.println("Listening for player connect on " + InetAddress.getLocalHost().getHostAddress()+":"+serverSocket.getLocalPort());
	    }
	    catch (UnknownHostException e) {
		System.out.println("Unknown host exception: " + e);
		System.exit(-1);
	    }
	    Socket player2Socket = null;
	    try {
		player2Socket = serverSocket.accept();
	    }
	    catch (IOException e) {
		System.out.println("Accept failed on port 22222");
		System.exit(-1);
	    }
	    
	    System.out.println("Connection receieved from " + player2Socket.getLocalAddress());
	    
	    PrintWriter toPlayer2 = null;
	    BufferedReader fromPlayer2 = null;
	    
	    try {
		toPlayer2 = new PrintWriter(player2Socket.getOutputStream(), true);
		fromPlayer2 = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
	    }
	    catch (IOException e) {
		System.out.println("Error getting input/output streams from Player 2");
		System.exit(-1);
	    }

	    while(true) {
		player1.printGameBoard();
		try {

		    int p1Move = player1.requestMove();
		    while(p1Move == -1)
			p1Move = player1.requestMove();

		    String readableLocation = "" + (char)(p1Move/10+65) + p1Move%10;
		    // send player1 move to player 2
		    System.out.println("Firing missiles at: " + readableLocation);
		    toPlayer2.println(p1Move);

		    // get status from player 2
		    String p1Status = fromPlayer2.readLine();
		    
		    System.out.println(p1Status);
		    player1.updateGuessGrid(p1Move, p1Status);
		    player1.printGameBoard();

		    if(player1.hasWon()) {
			toPlayer2.println("WIN");
			System.out.println("CONGRATULATIONS, YOU WIN!");
			break;
		    }
		    else {
			toPlayer2.println("CONTINUE");
		    }

		    // get player 2 move
		    System.out.println("Waiting for player 2's move...");
		    int p2Move = Integer.parseInt(fromPlayer2.readLine());

		    readableLocation = "" + (char)(p2Move/10+65) + p2Move%10;
		    System.out.println("PLAYER2 HAS FIRED MISSILES AT " + readableLocation);

		    String p2Status = player1.incomingMissile(p2Move);
		    System.out.println(p2Status);

		    toPlayer2.println(p2Status);
		
		    String p2victoryStatus = fromPlayer2.readLine();
		
		    if(p2victoryStatus.equals("WIN")) {
			System.out.println("OH NO, YOU LOSE!");
			break;
		    }
		}
		catch (IOException e) {
		    System.out.println("SOMETHING WENT WRONG WITH READING THINGS N SUCH, SORRY");
		    System.exit(-1);
		}
	    }
	}
	
	// joining a game
	else if(gameChoice.equals("2")) {
	    
	    Player player2 = new Player();
	    
	    System.out.println("ATTEMPT TO CONNECT TO WHICH IP/HOSTNAME?");
	    String connectTo = null;
	    try {
		connectTo = br.readLine();
	    } 
	    catch (IOException ioe) {
		System.err.println("IO error trying to read your selection!" + ioe.getMessage());
		System.exit(1);
	    }
	    
	    Socket player1Socket = null;
	    try {
		player1Socket = new Socket(connectTo, 22222);
	    }
	    catch (UnknownHostException e) {
		System.out.println("Don't know about host: " + connectTo);
		System.exit(-1);
	    }
	    catch (IOException e) {
		System.out.println("IOException when connecting to host.");
		System.exit(-1);
	    }

	    System.out.println("Connected to " + player1Socket.getLocalAddress());
	    
	    PrintWriter toPlayer1 = null;
	    BufferedReader fromPlayer1 = null;
	    
	    try {
		toPlayer1 = new PrintWriter(player1Socket.getOutputStream(), true);
		fromPlayer1 = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
	    }
	    catch (IOException e) {
		System.out.println("Error getting input/output streams from Player 1");
		System.exit(-1);
	    }
	    
	    player2.printGameBoard();
	    
	    while(true) {
		try {
		    System.out.println("Waiting for player 1's move...");
		    // get player 1's move
		    int p1Move = Integer.parseInt(fromPlayer1.readLine());
		    String p1Status = player2.incomingMissile(p1Move);
		    
		    String readableLocation = "" + (char)(p1Move/10+65) + p1Move%10;
		    System.out.println("Player 1 fires at: " + readableLocation);
		    System.out.println(p1Status);

		    // send player 1 status back to player 1
		    toPlayer1.println(p1Status);

		    // read player 1 win status
		    String p1victoryStatus = fromPlayer1.readLine();
		
		    if(p1victoryStatus.equals("WIN")) {
			System.out.println("OH NO, YOU LOSE!");
			break;
		    }

		    player2.printGameBoard();

		    // send move to player 1
		    int p2Move = player2.requestMove();
		    while(p2Move == -1)
			p2Move = player2.requestMove();
		    
		    readableLocation = "" + (char)(p2Move/10+65) + p2Move%10;
		    System.out.println("Firing missiles at: " + readableLocation);
		    toPlayer1.println(p2Move);

		    // get status from player 1
		    String p2Status = fromPlayer1.readLine();
		    System.out.println(p2Status);

		    player2.updateGuessGrid(p2Move, p2Status);
		    player2.printGameBoard();

		    if(player2.hasWon()) {
			toPlayer1.println("WIN");
			System.out.println("CONGRATULATIONS, YOU WIN!");
			break;
		    }
		    else {
			toPlayer1.println("CONTINUE");
		    }
		}
		catch (IOException e) {
		    System.out.println("SOMETHING WENT WRONG WITH READING THINGS N SUCH, SORRY");
		    System.exit(-1);
		}
	    }
	    
	    

	}
	else if(gameChoice.equals("3")) {
	    System.out.println("SELECT DIFFICULTY: EASY/MEDIUM/HARD");
	    gameChoice = null;
	    while(gameChoice == null || !isValid(gameChoice, "cpu difficulty")) {
		try {
		    gameChoice = br.readLine();
		} 
		catch (IOException ioe) {
		    System.err.println("IO error trying to read your selection!" + ioe.getMessage());
		    System.exit(1);
		}
	    }

	    gameChoice = gameChoice.toUpperCase();

	    Player human = new Player();
	    Player computer = new Computer(gameChoice);
	    while(true) {
		System.out.println("HUMAN GAME");
		human.printGameBoard();
		int humanMove = human.requestMove();
		while(humanMove == -1)
		    humanMove = human.requestMove();
		String humanStatus = computer.incomingMissile(humanMove);
		System.out.println(humanStatus);
		human.updateGuessGrid(humanMove, humanStatus);
		if(human.hasWon()) {
		    System.out.println("CONGRATULATIONS, YOU WIN!");
		    break;
		}
		int computerMove = computer.requestMove();
		String readableLocation = "" + (char)(computerMove/10+65) + computerMove%10;
		System.out.println("COMPUTER HAS FIRED MISSILES AT " + readableLocation);
		String computerStatus = human.incomingMissile(computerMove);
		System.out.println(computerStatus);
		computer.updateGuessGrid(computerMove, computerStatus);
		if(computer.hasWon()) {
		    System.out.println("OH NO, YOU LOSE!");
		    break;
		}
	    }
	}
	System.out.println("THANK YOU FOR PLAYING");
    }
    
    private static boolean isValid(String gameSelection, String state) {
	if(state.equals("intro")) {
	    if(gameSelection.equals("1") || gameSelection.equals("2") || gameSelection.equals("3"))
		return true;
	    System.out.println("Invalid game selection, please enter '1', '2', or '3'");
	    return false;
	}
	else if(state.equals("cpu difficulty")) {
	    if(gameSelection.compareToIgnoreCase("Easy") == 0 || gameSelection.compareToIgnoreCase("Medium") == 0 || gameSelection.compareToIgnoreCase("Hard") == 0)
		return true;
	    System.out.println("Invalid computer difficulty, please enter 'EASY', 'MEDIUM', or 'HARD'");
	    return false;
	}
	return false;
    }

}
