package edu.ucsb.cs56.projects.games.battleship;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ComputerGameController extends GameController{

    /** method for playing against computer
	 * 
     **/
    public void go(BattleshipGUI gui){
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
	public void playAgain(BattleshipGUI gui){
		if(gui.getReplayType() == 1) {	
            gui.end();
            gui = new BattleshipGUI();
            gui.resetPlace();
            this.waitForGameType(gui);

            this.go(gui);
            this.endOfGame(gui);
        }
        if(gui.getReplayType() == 2) {
            gui.end();
            gui = new BattleshipGUI();
            gui.resetShips();
            this.waitForSizes(gui);

            this.go(gui);
            this.endOfGame(gui);
        }
        if(gui.getReplayType() == 3) {
            gui.end();
            gui = new BattleshipGUI();
            gui.reset();
            gui.setOptions();
            this.wait(gui);


            if(gui.getGameType() == 1) {
                
                gui.setDefaultShipSizes();
            	HostGameController hostGame = new HostGameController();
                hostGame.go(gui);
                hostGame.endOfGame(gui);
            }
            if(gui.getGameType() == 2) {
                gui.setDefaultShipSizes();
            	JoinGameController joinGame = new JoinGameController();
                joinGame.go(gui);
                joinGame.endOfGame(gui);
            }
            if(gui.getGameType() == 3) {
            	ComputerGameController computerGame = new ComputerGameController();
                computerGame.go(gui);
                this.endOfGame(gui);
            }
        }
	}
	public void endOfGame(BattleshipGUI gui){
		String currentMessage = gui.getMessage();
		gui.setMessage(currentMessage + " THANK YOU FOR PLAYING");
		gui.computerPlayAgain();
	    this.waitReplay(gui);
	    this.playAgain(gui);
		
	}

}