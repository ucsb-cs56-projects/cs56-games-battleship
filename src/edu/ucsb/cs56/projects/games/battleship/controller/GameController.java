package edu.ucsb.cs56.projects.games.battleship;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public abstract class GameController{
	public abstract void go(BattleshipGUI gui);
	public abstract void playAgain(BattleshipGUI gui);
	public abstract void endOfGame(BattleshipGUI gui);


	/**
	 * Method to make program wait until options have been set
	*/

    public void waitReplay(BattleshipGUI gui) {
        while(gui.getReplayType() == 0) {
            BattleshipController.sleep();
        }
    }

    public void wait(BattleshipGUI gui){
        while(gui.getIPEntered() == false || gui.getGameType() == 0 ){
			this.sleep();
		}
	}

    public void waitForGameType(BattleshipGUI gui){
        while( gui.getGameType() == 0 ){
            this.sleep();
        }
    }
    public void waitForSizes(BattleshipGUI gui){
        while(gui.shipSizePopUpVisibile() || gui.colorPopUpVisible()){
             try{
                Thread.sleep(10);
            }
            catch (InterruptedException e){}
        }
    }
    
	public static void sleep(){
        try{
            Thread.sleep(10);
        }
        catch (InterruptedException e){
        }
    }
}