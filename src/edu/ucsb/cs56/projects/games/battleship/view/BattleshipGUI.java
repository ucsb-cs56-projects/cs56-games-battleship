package edu.ucsb.cs56.projects.games.battleship;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.URL;
import java.io.*;
import javax.sound.sampled.*;

/**
 * A gui class for playing battleship.

 * @author Keenan Reimer
 * @author Wenjian Li (working since W14)
 * @author Chang Rey Tang (W16)
 * @author Joseph Song (W16)
 * @author Barbara Korycki (F16)
 * @author Edward Guardado (F16)
 * @version 2.3 (CS56 Fall 2016)

 */

public class BattleshipGUI extends JFrame{

	//GUI recorded information
    private String difficulty = null;
   	private int gameType = 0;
    private int replayType = 0;

	private int boatSpawn;
	private boolean replay = true;
	private boolean prompt = true;

    //Public so that other classes can play sound files

    public URL shotURL;
    public URL missURL;
    public URL winURL;
    public URL loseURL;
	
    private JPanel audioPanel = new JPanel();

	//GUI Texts
   	private JLabel title = new JLabel("Battleship",JLabel.CENTER);
	private JLabel messages = new JLabel("Messages go here:", JLabel.CENTER);
	
	//Gametype frame popup
	private TypeSetUpFrame typePopUp = new TypeSetUpFrame();
	
	//Difficulty frame popup
    private DiffPopUpFrame diffPopUp = new DiffPopUpFrame();

	//Select ship sizes frame popup
	private SizeSetUpFrame shipSizePopUp = new SizeSetUpFrame();

    //Color selection frame popup 
    private ColorPopUpFrame colorPopUp = new ColorPopUpFrame();
	
	//Join IP frame popup
	private IpPopUpFrame ipPopUp = new IpPopUpFrame();
	
    //Play again popup
    private JFrame playAgainPopUp = new JFrame();
    private JFrame networkPlayAgainPopUp = new JFrame();
    private JButton playAgainButton = new JButton("Play Again");
    private JButton networkPlayAgainButton = new JButton("Play Again"); //shows up when playing mult
    private JButton newShipsButton = new JButton("Play Again w/ New Ship Sizes");
    private JButton playAgainWithNewIPButton = new JButton( "Join a new game");
    private JButton playAgainAsHostButton = new JButton ("Start a new game as a host");
    private JButton mainMenuButton = new JButton("Main Menu");
    private JButton networkMainMenuButton = new JButton("Main Menu");

    //Game board component
    private GameGrid board = new GameGrid();
    GridLayout threeButtonGrid = new GridLayout(1,3);

    /**
     * Default constructor for the class. Sets everything up.
     **/
    BattleshipGUI(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add Title
        this.getContentPane().add(BorderLayout.NORTH,title);

        //Add game board
        this.getContentPane().add(BorderLayout.CENTER,board);
        this.getContentPane().setBackground(Color.WHITE);

        //Add  controls
        audioPanel.setLayout(new BorderLayout());
        audioPanel.setBackground(Color.WHITE);
        this.getContentPane().add(BorderLayout.EAST,audioPanel);
        board.audioMute.setBackground(Color.WHITE);
        audioPanel.add(board.audioMute, BorderLayout.SOUTH);
        board.audioMute.addItemListener(board.new audioCheck());

        //Add messages
        this.getContentPane().add(BorderLayout.SOUTH, messages);

        this.pack(); // For some reason this code is necessary to give the board focus
        this.setSize(350,500);
        this.board.requestFocusInWindow();

        //Setup playAgain popup
        this.playAgainPopUp.setLayout(threeButtonGrid);
        this.playAgainPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.playAgainPopUp.setSize(800,100);

	   //Setup networkPlayAgain popup
	   GridLayout fourButtonGrid = new GridLayout(1,4);
        this.networkPlayAgainPopUp.setLayout(fourButtonGrid);
        this.networkPlayAgainPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.networkPlayAgainPopUp.setSize(800,100);
        
        //Add playAgain button listeners
        this.playAgainButton.addActionListener(this.new playAgainClick());
        this.newShipsButton.addActionListener(this.new playAgainClick());
        this.mainMenuButton.addActionListener(this.new playAgainClick());
	    this.playAgainWithNewIPButton.addActionListener(this.new networkPlayAgainClick());
	   this.playAgainAsHostButton.addActionListener(this.new networkPlayAgainClick());
	   this.networkMainMenuButton.addActionListener(this.new networkPlayAgainClick());
        this.networkPlayAgainButton.addActionListener(this.new networkPlayAgainClick());

        //Add playAgain buttons to window
        this.playAgainPopUp.add(playAgainButton);
        this.playAgainPopUp.add(newShipsButton);
        this.playAgainPopUp.add(mainMenuButton);

	    //Add networkPlayAgain buttons to window
	    this.networkPlayAgainPopUp.add(networkPlayAgainButton);
        this.networkPlayAgainPopUp.add(playAgainWithNewIPButton);
        this.networkPlayAgainPopUp.add(playAgainAsHostButton);
	    this.networkPlayAgainPopUp.add(networkMainMenuButton);
            
        //Initialize sound file locations
        board.placeURL = this.getClass().getResource("sfx/ship_place.aiff");
        shotURL = this.getClass().getResource("sfx/ship_hit.aiff");
        missURL = this.getClass().getResource("sfx/miss_splash.aiff");
        winURL = this.getClass().getResource("sfx/victory.aiff");
        loseURL = this.getClass().getResource("sfx/failure.aiff");
        board.cantPlaceURL = this.getClass().getResource("sfx/ship_cant_place.aiff");
    }

        /**
         * Method to initiate option prompts to the user.
         * This is the start of what makes the GUI go.
         **/

        public void setOptions(){
            this.replay = false;
            this.setVisible(false);
            this.typePopUp.setVisible(true);
            while (!typePopUp.entered){
                try{
                    Thread.sleep(100);}
                catch(InterruptedException e){
                    System.out.println(e);
                }
            }
            this.gameType = typePopUp.getGameType();

            this.typePopUp.setVisible(false);
            if(gameType == 1){                 //Host Game
                this.setVisible(true);
                this.setIpEntered(true);
            }
            else if (gameType == 2){            //Join Game
                setUpJoinGame();
            }
            else if (gameType == 3){            //Play computer
                setUpComputerGame();
            }
        }

        
        /**
         * Method to wait on the listeners for the color of the boats.
         **/
        public void waitForColor(){
            while(colorPopUp.getColor() == null || !colorPopUp.getContinue()){
                try{
                    Thread.sleep(100);}
                catch(InterruptedException e){
                    System.out.println(e);
                }                    
                
            }
        }


        /**
         * Method to wait for the boat sizes to be given.
         **/
        public void waitForSizes(){
            while(!shipSizePopUp.getSizeSet()){
                try{
                    Thread.sleep(100);}
                catch(InterruptedException e){
                    System.out.println(e);
                }                    
                
            }
        }


        /**
         * Method to wait for the difficulty to be chosen.
         **/
        public void waitForDifficulty(){
            while(diffPopUp.getDifficulty() == null){
                try{
                    Thread.sleep(100);}
                catch(InterruptedException e){
                    System.out.println(e);
                }                    
                
            }            
        }


        /**
         * Method to set up a computer game and the corresponding graphics.
         **/
        public void setUpComputerGame(){
            this.colorPopUp.setVisible(true);
            waitForColor();
            this.board.setShipColor(this.colorPopUp.getColor());
            this.shipSizePopUp.setVisible(true);           
            this.colorPopUp.setVisible(false);  

            waitForSizes();
            board.setShipSizes(shipSizePopUp.getShipSizes());
            this.shipSizePopUp.setVisible(false);
            this.diffPopUp.setVisible(true);

            waitForDifficulty();
            difficulty = diffPopUp.getDifficulty();
            this.diffPopUp.setVisible(false);
            this.setVisible(true);
        }

        
        /**
         * Method to initiate the graphics for computer boats.
         **/
        public void setUpComputerNewShips(){
            this.setVisible(false);
            this.shipSizePopUp.setVisible(true);
            waitForSizes();
            board.setShipSizes(shipSizePopUp.getShipSizes());
            this.shipSizePopUp.setVisible(false);
            this.diffPopUp.setVisible(true);

            waitForDifficulty();
            difficulty = diffPopUp.getDifficulty();
            this.diffPopUp.setVisible(false);
            this.setVisible(true);
        }

        /**
         * Method to initiate the graphics a second game with the AI.
         **/
        public void setUpCompterPlayAgain(){
            waitForDifficulty();
            difficulty = diffPopUp.getDifficulty();
            this.diffPopUp.setVisible(false);
            this.setVisible(true);
        }

        /**
         * Method to initiate the graphics for a Joining Game.
         **/
        public void setUpJoinGame(){
            this.ipPopUp.setVisible(true);
            while(this.ipPopUp.getIpEntered() == false){
                try{
                    Thread.sleep(100);}
                catch(InterruptedException e){
                    System.out.println(e);
                }
            }
            this.ipPopUp.setVisible(false);
            this.setVisible(true);
        }

        /**
         * Method to reset the GUI
         **/

        public void reset(){
            this.difficulty = null;
            this.gameType = 0;
            this.replay = true;
            this.prompt = true;

            board.reset();
            this.setVisible(false);
        }


        public void resetPlace() {
            this.difficulty = null;
            this.prompt = true;
            this.gameType = 3;
            this.replay = true;
             
            board.reset();
        
            this.setVisible(false);
            this.diffPopUp.setVisible(true);
            setUpCompterPlayAgain();
        
        }

        public void resetForIP() {
            this.prompt = true;
            this.gameType = 2;
            this.ipPopUp.setIpEntered(false);
            this.replay = true;
             
            board.reset();
        
            this.setVisible(false);
            setUpJoinGame();
        
        }

        public void resetForJoinAgain() {
            this.setVisible(false);
            this.prompt = true;
            this.gameType = 2;
            this.ipPopUp.setIpEntered(true);
            this.replay = true;
             
            board.reset();
        
            this.setVisible(true);
        
        }
        
        public void resetForHost() {
            this.setVisible(false);
            this.prompt = true;
            this.gameType = 1;
            this.ipPopUp.setIpEntered(true);
            this.replay = true;
             
            board.reset();
            setDefaultShipSizes();
            this.setVisible(true);
        
        }

        public void resetShips() {
            this.difficulty = null;
            this.prompt = true;
            this.gameType = 3; 
            this.ipPopUp.setIpEntered(false);
            this.replay = true;


            board.reset();

            setUpComputerNewShips();
        }

        public void computerPlayAgain() {
            this.playAgainPopUp.setVisible(true);
        }

        public void networkPlayAgain() {
	       this.networkPlayAgainPopUp.setVisible(true);
	   }

        /**
         * Changes the title at the top of the gui.
         * @param title The new title to set.
         **/

        public void setTitle(String title){
            this.title.setText(title);
        }

        /**
         * Changes the message at the bottom of the gui.
         * @param message The new message to set.
         **/
            
        public void setMessage(String message){
            this.messages.setText(message);
        }

        /**
         * Getter for replay instance variable. Used to check if the player wants to keep playing.
         * @return true for keep playing false for stop playing
         **/

        public int getReplayType(){
            return this.replayType;
        }

        /**
         * Getter for gameType instance variable
         * @return value stored in gameType
         */

        public int getGameType(){
            return this.gameType;
        }
	
        /**
         * Getter for gameDifficulty instance variable
         * @return value stored in gameDifficulty
         */

        public String getDifficulty(){
            return this.difficulty;
        }

	/**
	 * Returns the message being displayed at the bottom of the GUI.
	 * @return message String
	 */
	
	public String getMessage(){
		return this.messages.getText();
	}
	
	/**
	 * Returns the IP address that should be stored in ipField
	 * @return the IP address stored in ipField
	 **/
	 
	public String getIP(){
		return this.ipPopUp.getText();
	}
	
	/**
	 * Find out whether or not an IP address has been entered
	 * @return state of IP address entry
	 **/
	
	public boolean getIPEntered(){
		return this.ipPopUp.getIpEntered();
	}
	
    public void setIpEntered(boolean set){
        this.ipPopUp.setIpEntered(set);
    }
    public void setDefaultShipSizes(){
        int[] array = {2,3,3,4,5};
        board.setShipSizes(array);
       // player.setDefaultShipSizes();
    }
    public boolean shipSizePopUpVisibile(){
        return this.shipSizePopUp.isVisible();
    }
    public boolean colorPopUpVisible(){
        return this.colorPopUp.isVisible();
    }

	/**
	 * Method for returning status of user prompt
	 * @return true for the uses acknowledged prompt, false for user hasn't acknowledged prompt
	 **/
	 
	 public boolean getPrompt(){
		return this.prompt;
	 }
	 
    
    public void end() {
        this.setVisible(false);
        this.getContentPane().removeAll();
    }
	
	public void placeBoats(){ board.placeBoats(); }
    public ArrayList<Integer> getPlayerBoats(){ return board.getPlayerBoats();}
    public void addPlayerBoats(ArrayList<Integer> boatList){ board.addPlayerBoats(boatList);}
    public void addEnemyBoats(ArrayList<Integer> boatList){board.addEnemyBoats(boatList);}
    public void makeMove(){board.makeMove();}
    public boolean getPlayersTurn(){ return board.getPlayersTurn();}
    public int getLastMove(){ return board.getLastMove();}
    public ArrayList<Integer> getEnemyBoats() {return board.getEnemyBoats();}
    public void playAudioFile(URL audioURL){    board.playAudioFile(audioURL);}
    public int shiftToPlayerGrid(int loc){return board.shiftToPlayerGrid(loc);}
    public void addShot(int shot){ board.addShot(shot);}
    public String hitPlayer(int shot){ return board.hitPlayer(shot);}
    public void addEnemyBoat(int boatLoc){ board.addEnemyBoat(boatLoc);}
     
    /**
     * Listener for the play again options
     **/

    public class playAgainClick implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == BattleshipGUI.this.playAgainButton) {
                replayType = 1;
                BattleshipGUI.this.setVisible(false);
                BattleshipGUI.this.playAgainPopUp.setVisible(false);
            }
            else if(e.getSource() == BattleshipGUI.this.newShipsButton) {
                replayType = 2;
                BattleshipGUI.this.setVisible(false);
                BattleshipGUI.this.playAgainPopUp.setVisible(false);
            }
            else if(e.getSource() == BattleshipGUI.this.mainMenuButton) {
                replayType = 3;
                BattleshipGUI.this.setVisible(false);
                BattleshipGUI.this.playAgainPopUp.setVisible(false);
            }
        } 
    }

    /**
     * Listener for the network play again options
     **/

    public class networkPlayAgainClick implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == BattleshipGUI.this.networkPlayAgainButton) {
                replayType = 4;
                BattleshipGUI.this.setVisible(false);
                BattleshipGUI.this.networkPlayAgainPopUp.setVisible(false);
            }
	    
            else if(e.getSource() == BattleshipGUI.this.playAgainWithNewIPButton) {
                replayType = 5;
                BattleshipGUI.this.setVisible(false);
                BattleshipGUI.this.networkPlayAgainPopUp.setVisible(false);
            }

	       else if(e.getSource() == BattleshipGUI.this.playAgainAsHostButton) {
                replayType = 6;
                BattleshipGUI.this.setVisible(false);
                BattleshipGUI.this.networkPlayAgainPopUp.setVisible(false);
            }
	    
            else if(e.getSource() == BattleshipGUI.this.networkMainMenuButton) {
                replayType = 3;
                BattleshipGUI.this.setVisible(false);
                BattleshipGUI.this.networkPlayAgainPopUp.setVisible(false);
            }
        } 
    }    
}
