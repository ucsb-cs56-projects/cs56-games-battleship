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
 * @version 2.2 (CS56 Winter 2016)

 */

public class BattleshipGUI extends JFrame{

	//GUI recorded information
    private String difficulty = null;
   	private int gameType = 0;
    private int replayType = 0;

	private int boatSpawn;
	private boolean ipEntered = false;
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
	private JFrame typePopUp = new JFrame();
	private JButton hostButton = new JButton("Host a Game");
	private JButton joinButton = new JButton("Join a Game");
	private JButton computerButton = new JButton("Play Against a Computer");
	
	//Difficulty frame popup
    private JFrame diffPopUp = new JFrame();
    private JButton easyButton = new JButton("Easy");
    private JButton mediumButton = new JButton("Medium");
    private JButton hardButton = new JButton("Hard");

	//Select ship sizes frame popup
	private JFrame shipSizePopUp = new JFrame();
	private JButton reminder = new JButton("Input sizes of ships (between 2 and 9), then click to proceed");
	private JPanel shipSizePanel = new JPanel();
	private JTextField ship1 = new JTextField(5);
	private JTextField ship2 = new JTextField(5);
	private JTextField ship3 = new JTextField(5);
	private JTextField ship4 = new JTextField(5);
	private JTextField ship5 = new JTextField(5);
	private JTextField [] inputBoxes = {ship1, ship2, ship3, ship4, ship5};

    //Color selection frame popup 
    private JFrame colorPopUp = new JFrame();
    private JButton colorSelectButton = new JButton("Select a color, then click to proceed");
    String [] colorList = {"Default", "Black", "Blue", "Green", "Orange", "Pink", "White", "Yellow"};
    private JComboBox colorDrop = new JComboBox(colorList);
	
	//Join IP frame popup
	private JFrame ipPopUp = new JFrame();
	private JLabel ipRequest = new JLabel("Please input the IP address you wish to join.", JLabel.CENTER);
	private JTextField ipField = new JTextField();
	private JLabel ipMessage = new JLabel("Hit enter to submit.", JLabel.CENTER);
	
    //Play again popup
    private JFrame playAgainPopUp = new JFrame();
    private JFrame networkPlayAgainPopUp = new JFrame();
    private JButton playAgainButton = new JButton("Play Again");
    private JButton networkPlayAgainButton = new JButton("Play Again");
    private JButton newShipsButton = new JButton("Play Again w/ New Ship Sizes");
    private JButton playAgainWithNewIPButton = new JButton( "Join a new game");
    private JButton playAgainAsHostButton = new JButton ("Start a new game as a host");
    private JButton mainMenuButton = new JButton("Main Menu");
    private JButton networkMainMenuButton = new JButton("Main Menu");

    //Game board component
    private GameGrid board = new GameGrid();



    /**
     * Default constructor for the class. Sets everything up.
     **/
    BattleshipGUI(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add Title
        this.getContentPane().add(BorderLayout.NORTH,title);

        //Add game board
        this.board.setSize(100,210);
        this.board.addMouseListener(board.new cellClick());
        this.board.addMouseMotionListener(board.new mouseMove());
        this.board.addKeyListener(board.new changeOrientation());
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

        //setup difficulty options popup
        GridLayout threeButtonGrid = new GridLayout(1,3);
        this.diffPopUp.setLayout(threeButtonGrid);
        this.diffPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.diffPopUp.setSize(600,100);

        //Add difficulty buttons listeners
        this.easyButton.addActionListener(this.new difficultyClick());
        this.mediumButton.addActionListener(this.new difficultyClick());
        this.hardButton.addActionListener(this.new difficultyClick());

        this.diffPopUp.add(easyButton);
        this.diffPopUp.add(mediumButton);
        this.diffPopUp.add(hardButton);

        //Setup gametype popup
        this.typePopUp.setLayout(threeButtonGrid);
        this.typePopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.typePopUp.setSize(800,100);

        //Add type buttons listeners
        this.hostButton.addActionListener(this.new typeClick());
        this.joinButton.addActionListener(this.new typeClick());
        this.computerButton.addActionListener(this.new typeClick());

        //Add type buttons to window
        this.typePopUp.add(hostButton);
        this.typePopUp.add(joinButton);
        this.typePopUp.add(computerButton);

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
        
        //Setup shipsize popup
        this.shipSizePopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.shipSizePopUp.setSize(600,100);

        //Add shipsize buttons listeners
        this.reminder.addActionListener(this.new sizeClick());
        for(int i=0; i<5; i++){
            this.inputBoxes[i].addActionListener(this.new sizeClick());
        }
        this.shipSizePopUp.getContentPane().add(BorderLayout.SOUTH, reminder);
        for(int i=0; i<5; i++){
            this.shipSizePanel.add(this.inputBoxes[i]);
        }
        this.shipSizePopUp.getContentPane().add(BorderLayout.CENTER, shipSizePanel);

        //Setup colorPopUp
        this.colorPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.colorPopUp.setSize(600,100);
        
        //Add shipColor buttons and dropdown listeners
        this.colorSelectButton.addActionListener(this.new colorClick());
        this.colorDrop.addActionListener(this.new colorDropSelect());

        //Add buttons and dropdown to window
        this.colorPopUp.getContentPane().add(BorderLayout.CENTER, colorDrop);
        this.colorPopUp.getContentPane().add(BorderLayout.SOUTH, colorSelectButton);

        //Setup IP popup
        GridLayout threeWidgetVerticleGrid = new GridLayout(3,1);
        this.ipPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ipPopUp.setLayout(threeWidgetVerticleGrid);
        this.ipPopUp.setSize(600,100);
        this.ipField.setHorizontalAlignment(JTextField.CENTER);
        this.ipField.addActionListener(this.new ipEnter());

        //Add IP widgets
        this.ipPopUp.getContentPane().add(BorderLayout.NORTH,ipRequest);
        this.ipPopUp.getContentPane().add(BorderLayout.CENTER,ipField);
        this.ipPopUp.getContentPane().add(BorderLayout.SOUTH,ipMessage);
        
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
         **/

        public void setOptions(){
            this.replay = false;
            this.setVisible(false);
            this.typePopUp.setVisible(true);
        }

        /**
         * Method to reset the GUI
         **/

        public void reset(){
            this.difficulty = null;
            this.gameType = 0;
            this.ipEntered = false;
            this.replay = true;
            this.prompt = true;

            board.reset();
            this.setVisible(false);
        }


        public void resetPlace() {
            this.difficulty = null;
            this.prompt = true;
            this.gameType = 3;
            this.ipEntered = false;
            this.replay = true;
             
            board.reset();
        
            this.setVisible(false);
            this.diffPopUp.setVisible(true);
        
        }

        public void resetForIP() {
            this.difficulty = "INTERNET";
            this.prompt = true;
            this.gameType = 2;
            this.ipEntered = false;
            this.replay = true;
             
            board.reset();
        
            this.setVisible(false);
            this.ipPopUp.setVisible(true);
        
        }

        public void resetForJoinAgain() {
            this.setVisible(false);
            this.difficulty = "INTERNET";
            this.prompt = true;
            this.gameType = 2;
            this.ipEntered = false;
            this.replay = true;
             
            board.reset();
        
            this.setVisible(true);
        
        }
        
        public void resetForHost() {
            this.setVisible(false);
            this.difficulty = "INTERNET";
            this.prompt = true;
            this.gameType = 1;
            this.ipEntered = false;
            this.replay = true;
             
            board.reset();
        
            this.setVisible(true);
        
        }

        public void resetShips() {
            this.difficulty = null;
            this.prompt = true;
            this.gameType = 3; 
            this.ipEntered = false;
            this.replay = true;


            board.reset();

            this.setVisible(false);
            this.shipSizePopUp.setVisible(true);
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
		return this.ipField.getText();
	}
	
	/**
	 * Find out whether or not an IP address has been entered
	 * @return state of IP address entry
	 **/
	
	public boolean getIPEntered(){
		return this.ipEntered;
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
	 * Listener for difficulty option buttons
	 **/
	
    public class difficultyClick implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if( e.getSource() == BattleshipGUI.this.easyButton){
                difficulty = "EASY";
                BattleshipGUI.this.diffPopUp.setVisible(false);
                BattleshipGUI.this.setVisible(true);
            }
            else if( e.getSource() == BattleshipGUI.this.mediumButton){
                difficulty = "MEDIUM";
                BattleshipGUI.this.diffPopUp.setVisible(false);
                BattleshipGUI.this.setVisible(true);
            }
            else if ( e.getSource() == BattleshipGUI.this.hardButton){
                difficulty = "HARD";
                BattleshipGUI.this.diffPopUp.setVisible(false);
                BattleshipGUI.this.setVisible(true);
            }
        }
    }
	
	/**
	 * Listener for the type options buttons
	 **/
	 
    public class typeClick implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if( e.getSource() == BattleshipGUI.this.hostButton){
                gameType = 1;
                difficulty = "INTERNET";
                BattleshipGUI.this.typePopUp.setVisible(false);
                BattleshipGUI.this.setVisible(true);
            }
            else if( e.getSource() == BattleshipGUI.this.joinButton){
                gameType = 2;
                difficulty = "INTERNET";
                BattleshipGUI.this.typePopUp.setVisible(false);
                BattleshipGUI.this.ipPopUp.setVisible(true);
            }
            else if ( e.getSource() == BattleshipGUI.this.computerButton){
                gameType = 3;
                BattleshipGUI.this.typePopUp.setVisible(false);
                BattleshipGUI.this.colorPopUp.setVisible(true);
			}
		}
	}

	/**
	 * Listener for the ship sizes button
     * once the user clicks the proceed button, texts in the 5 text fields will be check by isValid() method, 
     * and if all of them are valid, program will read in the 5 numbers into the shipSizes array and 
     * send the message of clicking to GUI
	 **/

	public class sizeClick implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == BattleshipGUI.this.reminder){
				int [] inputSizes = Player.shipSizes; 
                board.setShipSizes(inputSizes);
				boolean isValid = true;
				
				for(int i=0; i<5; i++){
					String input = BattleshipGUI.this.inputBoxes[i].getText();
					if(BattleshipGUI.isValid(input)==true) {inputSizes[i] = Integer.parseInt(BattleshipGUI.this.inputBoxes[i].getText());}
					else {isValid=false; break;}
				}
				if(isValid==true){
					BattleshipGUI.this.shipSizePopUp.setVisible(false);
					BattleshipGUI.this.diffPopUp.setVisible(true);
				}
				else reminder.setText("Please input sizes between 2 and 9");
			}
		}
	}


    /**
     * Listener for the color selection menu's continue button
     * when the user clicks the button it will move onto the ship size
     * selection menu
     **/

    public class colorClick implements ActionListener{ 
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == BattleshipGUI.this.colorSelectButton){
                BattleshipGUI.this.colorPopUp.setVisible(false);
                BattleshipGUI.this.shipSizePopUp.setVisible(true);
            }
        }
    }

    /**
    * Listener for the color selection menu's dropdown button
    * when the user selects a color it will set shipColor to that color
    **/

    public class colorDropSelect implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JComboBox combo = (JComboBox)e.getSource();
            switch((String)combo.getSelectedItem()){
                case "Default":
                    board.setShipColor(Color.DARK_GRAY);
                    break;
                case "Black":
                    board.setShipColor(Color.BLACK);
                    break;
                case "Blue":
                    board.setShipColor(Color.BLUE);
                    break;
                case "Green":
                    board.setShipColor(Color.GREEN);
                    break;
                case "Orange":
                    board.setShipColor(Color.ORANGE);
                    break;
                case "Pink":
                    board.setShipColor(Color.PINK);
                    break;
                case "White":
                    board.setShipColor(Color.WHITE);
                    break;
                case "Yellow":
                    board.setShipColor(Color.YELLOW);
                    break;
                default:
                    break;
            }
        }
    }
    

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

	/**
	 * Method exlusively for ship sizes button listener to check validity of user input
	 **/
	public static boolean isValid(String input){
		if(input.equals("") || input.length()!=1) return false;
		char character = input.charAt(0);
		if(character>'9' || character<'2') return false;
		return true;
	}
	
	/**
	 * Listener class for entering IP addresses
	 **/
	
	public class ipEnter implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ipEntered = true;
			BattleshipGUI.this.ipPopUp.setVisible(false);
			BattleshipGUI.this.setVisible(true);
		}
	}
	
}
