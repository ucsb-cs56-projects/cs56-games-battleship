package edu.ucsb.cs56.projects.games.battleship;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Main class for Battleship Game
 * @author Wenjian Li (working since W14)
 * @author Chang Rey Tang (W16)
 * @author Joseph Song (W16)
 * @author Barbara Korycki (F16)
 * @version 2.2 (CS56 Winter 2016)
*/

public class BattleshipController {
    //private String previousIP = "";		//keep track of IP address that game joiner previously used
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
	public void wait(BattleshipGUI gui){
        while(gui.getDifficulty() == null || gui.getGameType() == 0 ){
			BattleshipController.sleep();
		}
	}



    public void go() {
		BattleshipGUI gui = new BattleshipGUI();
		gui.reset();
		gui.setOptions();
		this.wait(gui);

		GameController game;

        //Hosting a game
        if(gui.getGameType() == 1){
        	game = new HostGameController();
        	game.go(gui);
        	game.endOfGame(gui);
            //this.hostGame(gui);
        }
	
        //Joining a game
        if(gui.getGameType() == 2){
        	game = new JoinGameController();
            game.go(gui);
            game.endOfGame(gui);
        }
	
        if(gui.getGameType() == 3) {
            game = new ComputerGameController();
            game.go(gui);
            game.endOfGame(gui);
        }
    }

    public static void main(String[] args){
		BattleshipController myController = new BattleshipController();
		myController.go();
	}

}
