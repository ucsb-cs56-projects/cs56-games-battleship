package edu.ucsb.cs56.projects.games.battleship;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
	 * The game controller class, controller for handling for when configuring the game settings 
	 * @version 2.4 (CS56 Fall 2017)
	*/

public abstract class GameController{
	public abstract void go(BattleshipGUI gui);
	public abstract void playAgain(BattleshipGUI gui);
	public abstract void endOfGame(BattleshipGUI gui);


	/**
	 * Method to make program wait until replay have been set
	 * @param gui BattleshipGUI 
	 */

    public void waitReplay(BattleshipGUI gui) {
        while(gui.getReplayType() == 0) {
            BattleshipController.sleep();
        }
    }
	/**
	 * Method to make program wait until settings have been set
	 * @param gui BattleshipGUI 
	 */

    public void wait(BattleshipGUI gui){
        while(gui.getIPEntered() == false || gui.getGameType() == 0 ){
			this.sleep();
		}
	}

	/**
	 * Method to make program wait until game type have been set
	 * @param gui BattleshipGUI 
	 */

    public void waitForGameType(BattleshipGUI gui){
        while( gui.getGameType() == 0 ){
            this.sleep();
        }
    }

	/**
	 * Method to make program wait until ship sizes have been set
	 * @param gui BattleshipGUI 
	 */

    public void waitForSizes(BattleshipGUI gui){
        while(gui.shipSizePopUpVisibile() || gui.colorPopUpVisible()){
             try{
                Thread.sleep(10);
            }
            catch (InterruptedException e){}
        }
    }
    /**
	 * Method to make program sleep
	 */

	public static void sleep(){
        try{
            Thread.sleep(10);
        }
        catch (InterruptedException e){
        }
    }
}
