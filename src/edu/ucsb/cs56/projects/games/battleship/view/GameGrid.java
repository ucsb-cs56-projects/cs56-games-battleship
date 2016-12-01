package edu.ucsb.cs56.projects.games.battleship;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.URL;
import java.io.*;
import javax.sound.sampled.*;

public class GameGrid extends JComponent{
    public int width;
    public int height;
    public int cellWidth; 
    public int startX;
    private ArrayList<Integer> shots = new ArrayList<Integer>();

            //GUI's knowledge bank. Used for GameGrid cell coloring
    private ArrayList<Integer> playerBoats = new ArrayList<Integer>();
    private ArrayList<Integer> enemyBoats = new ArrayList<Integer>();
    private ArrayList<ArrayList<Integer>> playerBoatGroups = new ArrayList<ArrayList<Integer>>();
    public ArrayList<Integer> getEnemyBoats() {return this.enemyBoats;}

    private boolean placeBoats = false;
    private int[] playerShipSizes = Player.shipSizes;
    private boolean boatPlaced = false;
    private int boatToPlace;
    private boolean playersTurn = false;
    private int lastMove;
    private Color shipColor = Color.DARK_GRAY;
    private int xLoc;
    private int yLoc;
    private boolean horzOrVert = true; //true for horizontal false for verticle
    private boolean audio = true;

    public URL placeURL;
    public URL cantPlaceURL;
    public Clip clip;


    //Audio muted/unmuted checkbox
    JCheckBox audioMute = new JCheckBox("Mute");

    public GameGrid(){
        this.setSize(100,210);
        this.addMouseListener(this.new cellClick());
        this.addMouseMotionListener(this.new mouseMove());
        this.addKeyListener(this.new changeOrientation());
    }

            public void paintComponent(Graphics g)
            {

                width = this.getWidth() + 50; //50 added to accomodate for mute box
                height = this.getHeight();      
                cellWidth = height/21;
                startX = (width - (cellWidth*10))/2;
                Graphics2D g2d = (Graphics2D) g;
                Color ocean = new Color(0,119,190);

                //Make the background white
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0,0,this.getWidth(),this.getHeight());

                //Paint individual cells
                for(int i=0; i < 10; i++){
                    for(int j = 0; j<21; j++){
                        int loc = j*10 + i;
                        if(j==10)
                            g2d.setColor(Color.BLACK);
                        else if(shots.contains(loc) && (playerBoats.contains(loc) || enemyBoats.contains(loc))){
                            g2d.setColor(Color.RED);
                        }
                        else if(playerBoats.contains(loc))
                            g2d.setColor(shipColor);
                        else if(shots.contains(loc)){
                            g2d.setColor(ocean.darker());
                        }
                        else g2d.setColor(ocean);

                        g2d.fillRect(startX + (i*cellWidth),j*cellWidth,cellWidth,cellWidth);

                    }
                }

                //Paint GameGrid lines
                g2d.setColor(Color.GRAY);
                g2d.drawLine(startX,0,startX,cellWidth*21); //Far left border
                g2d.drawLine(startX + 10*cellWidth,0,startX + 10*cellWidth,cellWidth*21); //Far right border
                for(int i=1; i<10; i++)
                    g2d.drawLine(startX + i*cellWidth,0,startX + cellWidth*i,cellWidth*10); 
                for(int i=1; i<10; i++)
                    g2d.drawLine(startX + i*cellWidth,cellWidth*11,startX + cellWidth*i,cellWidth*21);  
                for(int j=0; j<22; j++)
                    g2d.drawLine(startX, j*cellWidth, startX + 10*cellWidth, j*cellWidth);

                //Paint boat to be placed if boats are being placed
                int topLeftX = xLoc - cellWidth/2;
                int topLeftY = yLoc - cellWidth/2;
                if(placeBoats && horzOrVert){ //Draw boat to be placed horizontally
                    g2d.setColor(shipColor);
                    g2d.fillRect(topLeftX, topLeftY, boatToPlace*cellWidth, cellWidth);

                    g2d.setColor(Color.GRAY);
                    g2d.drawLine( topLeftX, topLeftY, topLeftX + cellWidth*boatToPlace, topLeftY);
                    g2d.drawLine( topLeftX, topLeftY + cellWidth, topLeftX + cellWidth*boatToPlace, topLeftY + cellWidth);
                    for(int i =0; i<boatToPlace + 1; i++){
                        g2d.drawLine( topLeftX + i*cellWidth, topLeftY, topLeftX + i*cellWidth, topLeftY + cellWidth);
                    }
                }
                else if(placeBoats){ //Draw boat to be places vertically 
                    g2d.setColor(shipColor);
                    g2d.fillRect( topLeftX, topLeftY, cellWidth, boatToPlace*cellWidth);

                    g2d.setColor(Color.GRAY);
                    g2d.drawLine( topLeftX, topLeftY, topLeftX, topLeftY + boatToPlace*cellWidth);
                    g2d.drawLine( topLeftX + cellWidth, topLeftY, topLeftX + cellWidth, topLeftY + boatToPlace*cellWidth);
                    for(int i = 0; i< boatToPlace + 1; i++){
                        g2d.drawLine( topLeftX, topLeftY + i*cellWidth, topLeftX + cellWidth, topLeftY + i*cellWidth);
                    }
                }
            }
            public void reset(){
                this.shots= new ArrayList<Integer>();
                this.playerBoats = new ArrayList<Integer>();
                this.playersTurn = false;
                this.lastMove = 0;

            }
    /**
     * Add a shot to the gui's shots list
     * @param shot The shot to be added
     */
        public void addShot(int shot){
            this.shots.add(shot);
            this.repaint();
        }

    /**
     * Mouse listener for clicking on game cells
     **/
    public class cellClick implements MouseListener{
        public void mouseClicked(MouseEvent e){}
        public void mousePressed(MouseEvent e){
            //Calculate which column & row the mouse was clicked in
            int cellColumn = (int) Math.floor((e.getX() - startX)/cellWidth);
            int cellRow = (int) Math.floor(e.getY()/cellWidth);
            //Add to shot list if it was in enemy territory
            if(cellRow < 10 && cellRow >=0 && cellColumn >=0 && cellColumn < 10 && playersTurn && (!shots.contains(cellRow*10 + cellColumn))){
                lastMove = cellRow*10 + cellColumn;
                shots.add(lastMove);
                playersTurn = false;
            }
            //Record click for boat placement if it was in player's territory
            else if( cellRow < 21 && cellRow > 10 && cellColumn >=0 && cellColumn < 10){
                if(placeBoats){
                    int spawn = cellRow*10 + cellColumn;
                    if(isValidSpawn(spawn)){ //Place the boat on click if the spawn location is valid
                        boatPlaced = true;
                        placeBoat(spawn);
    
                        if(audio)
                            playAudioFile(placeURL);
    
                        repaint();
                    }
                }
            }
            repaint();
        }

        public void mouseReleased(MouseEvent e){}
        public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){repaint();}
    }
        /**
         * Returns whether a shot from the enemy against the player is a "HIT" or a "MISS".
         * @param shot The enemy's shot.
         * @return "HIT" or "MISS"
         **/

        public String hitPlayer(int shot){
            if( playerBoats.contains(shot)) return "HIT";
            else return "MISS";
        }
            /**
     * Check if a spawn is valid for placing boat
     * @param spawn representing the specific spawn
     */

    public boolean isValidSpawn(int spawn){
        if(this.horzOrVert){
            for(int i=0; i < boatToPlace; i++){
                if((spawn + i)%10 > 9 || playerBoats.contains(spawn+i) || (spawn + i)/10 != spawn/10){
                    playAudioFile(cantPlaceURL);
                    return false;
                }
            }
        }
        else{
            for(int i=0; i<boatToPlace; i++){
                if((spawn+10*i)/10 > 20 || playerBoats.contains(spawn+10*i)) {
                    playAudioFile(cantPlaceURL);
                    return false;
                }
            }
        }
        return true;
    }
    public void placeBoat(int spawn){
        ArrayList<Integer> spawns = new ArrayList<Integer>();
        if(horzOrVert){
            for(int i=0; i<this.boatToPlace; i++){
                this.playerBoats.add(spawn + i);
                spawns.add(spawn + i);
            }
        }
        else{
            for(int i=0; i<this.boatToPlace; i++){
                this.playerBoats.add(spawn + 10*i);
                spawns.add(spawn + 10*i);
            }
        }
        this.playerBoatGroups.add(spawns);
    }
    /**
     * Lets gui know its players turn
     */
    
    public void makeMove(){
        this.playersTurn = true;
    }
    
    
    /**
     * Adds locations for player's boats to the playerBoats list. Shifts their integer locations.
     * @param boatList A list of boat locations.
     */
    
    public void addPlayerBoats(ArrayList<Integer> boatList){
        for( Integer loc: boatList)
            this.playerBoats.add(shiftToPlayerGrid(loc));
    }
    
    /**
     * Adds locations for enemy's boats to the enemyBoats list.
     * @param boatList A list of boat locations.
     */
    
    public void addEnemyBoats(ArrayList<Integer> boatList){
        this.enemyBoats = boatList;
    }
    
    /**
     * Adds a single boat location to enemyBoats
     **/
    
    public void addEnemyBoat(int boatLoc){
        this.enemyBoats.add(boatLoc);
    }
    
    /**
     * Method for retrieving player boats. Used when GUI is used to place boats.
     **/
    
    public ArrayList<Integer> getPlayerBoats(){
        return this.playerBoats;
    }

    /**
     *controller class uses this method to set a player's boat list array
     **/
    public ArrayList<ArrayList<Integer>> getGroupBoats(){
       return this.playerBoatGroups;
    }
    
    /**
     * Returns the player's most recent move.
     **/
    
    public int getLastMove(){
        return this.lastMove;
    }
           /**
         * Returns whether a shot from the player against the enemy is a "HIT" or a "MISS".
         * @param shot The player's shot.
         * @return "HIT" or "MISS"
         **/
        public String hitEnemy(int shot){
            if( enemyBoats.contains(shot)) return "HIT";
            else return "MISS";
        }

        public void placeBoats() {
            this.placeBoats = true;
            int[] boats = playerShipSizes; 
            for(int boat: boats){
                this.boatPlaced = false;
                this.boatToPlace = boat;
                while(!this.boatPlaced){
                    BattleshipController.sleep();
                }
            }
            this.placeBoats = false;
            repaint();
        }
        public void setShipSizes(int[] sizes){
            this.playerShipSizes = sizes;
        }
                /**
         * Setter for playersTurn variable. Used by controller to let user make a move.
         * @param tf The boolean value to set to playersTurn.
         **/

        public void setPlayersTurn(boolean tf){
            this.playersTurn = tf;
        }
                /**
         * Getter for playersTurn instance variable
         * @return value stored in playersTurn
         */

        public boolean getPlayersTurn(){
            return this.playersTurn;
        }

    /**  
    * Methods that plays audio clip referenced by audioURL
    **/
    public void playAudioFile(URL audioURL){
        try{
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioURL);
            this.clip = AudioSystem.getClip();
            this.clip.open(audioStream);
            clip.start();
            clip.setMicrosecondPosition(0);
        } catch(Exception e) {
            System.err.println(e);
        } 
    }

    public class mouseMove implements MouseMotionListener{
        public void mouseMoved(MouseEvent e){
            if(placeBoats){ //Records x & y locations if player is placing boats
                xLoc = e.getX();
                yLoc = e.getY();
                repaint();
            }
        }
        public void mouseDragged(MouseEvent e){}
    }

    public class changeOrientation implements KeyListener{
        public void keyPressed(KeyEvent e){
            horzOrVert = !horzOrVert; //Switch from drawing boats horizontally to drawing them vertically
            repaint();
        }   
        public void keyReleased(KeyEvent e){}
        public void keyTyped(KeyEvent e){}
    }
    public void setShipColor(Color color){
        this.shipColor = color;
    }
    /**
    * Listener for the mute check box
    * audio is muted when checked and unmuted when unchecked
    **/

    public class audioCheck implements ItemListener{
        public void itemStateChanged(ItemEvent e){
            JCheckBox cb = (JCheckBox) e.getSource();
            if(cb.isSelected())
                audio = false;
            else
                audio = true;
        }
    }
            /**
         * Shifts some location to player's GameGrid by moving it down 11 rows.
         * @param loc The location to be shifted.
         * @return shifted integer location value
         **/

        public int shiftToPlayerGrid(int loc){
            int boatRow = loc/10 + 11;
            int boatColumn = loc%10;
            return boatRow*10 + boatColumn;
        }
}