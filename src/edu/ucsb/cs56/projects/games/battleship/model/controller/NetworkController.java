package edu.ucsb.cs56.projects.games.battleship;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public abstract class NetworkController extends GameController{
	public abstract void go(BattleshipGUI gui);
	public void endOfGame(BattleshipGUI gui){
		String currentMessage = gui.getMessage();
		gui.setMessage(currentMessage + " THANK YOU FOR PLAYING");
		gui.networkPlayAgain();
		this.waitReplay(gui);
		this.playAgain(gui);
	}
	public void playAgain(BattleshipGUI gui){
		String currentMessage = gui.getMessage();
		gui.setMessage(currentMessage + " THANK YOU FOR PLAYING");
        
		if(gui.getReplayType() == 5) {		//join game
            gui.end();
            gui = new BattleshipGUI();
            gui.resetForIP();
            this.wait(gui);

            JoinGameController joinGame = new JoinGameController();
            joinGame.go(gui);
            joinGame.endOfGame(gui);
        }

		if(gui.getReplayType() == 6) {		//host game
            gui.end();
            gui = new BattleshipGUI();
            gui.resetForHost();
            this.wait(gui);

            HostGameController hostGame = new HostGameController();
            hostGame.go(gui);
            //this.hostGame(gui);

            this.endOfGame(gui);
        }
		if(gui.getReplayType() == 4) {		//Play again with same person
            	playAgainSameIP(gui);
        }
		if(gui.getReplayType() == 3) {		//main menu
            gui.end();
            gui = new BattleshipGUI();

            gui.reset();
            gui.setOptions();
            this.waitForGameType(gui);

            if(gui.getGameType() == 1) {
            	HostGameController hostGame = new HostGameController();
            	hostGame.go(gui);
                this.endOfGame(gui);
            }
            if(gui.getGameType() == 2) {
            	JoinGameController joinGame = new JoinGameController();
            	joinGame.go(gui);
                this.endOfGame(gui);

            }
            if(gui.getGameType() == 3) {
                this.waitForSizes(gui);
                ComputerGameController computerGame = new ComputerGameController();
                computerGame.go(gui);
                computerGame.endOfGame(gui);
            }
        }
	 }

	 public abstract void playAgainSameIP(BattleshipGUI gui);

}