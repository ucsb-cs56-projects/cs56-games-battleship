# cs56-games-battleship

This is an extended version of the game Battleship. 

project history
===============
```
 W14 | andrewberls 5pm | OliverHKU  | Battleship game
```

* Original developer description - the Milton Bradley board game that has won the hearts of children for over four decades. It will be single-player (vs. computer) and will have various difficulty settings (easy, normal, hard).  You can also host/join games and play against your friends online WITH THE POWER OF JAVA NETWORKING!

* Current version: 2.4
* Previous versions:
	* version 1: See "Original developer description" (see figure 0)
	* version 2.0: New feature added. Player can customize ship sizes in the "against computer" mode through a new window.(see figure 1) Illegal ship sizes will be reported to the player. (see figure 2)
	* version 2.1: New feature added. There will be a hit count under the game board indicating how many pixels on the opponent's ship has the player already hit. (see figure 3)
	* <b>version 2.1.1</b>: Bug fix on checking ship sizes validity. In version 2.0, if user input is not a number, program will terminate. Now fixed. (see figure 4)
    * <b>version 2.2</b>: Game now has replay functionality, ship color selection, harder computer difficulty, and sound effects.

    * <b>version 2.3</b>: Game now has replay functionality for multiplayer games and also has a boat count, so the user can keep track of how many boats have been sunk. Also, the port number has been removed in order to limit confusion when setting up multiplayer games.
    
    * <b>version 2.4</b>: Game now has proper background music, new background images, buttons for the settings should now look much better. 

* Game description - this Battleship game has 3 modes for user to choose from: hosting a game, joining a game, and playing against a computer. (see figure 5)
	
	* Hosting a game: User's IP address will be displayed, and user shall wait for someone else to join his game.
	* Joining a game: User shall be told the host's IP address, and input the IP address to join a game.
	* Playing with a computer:
		* User can customize ship sizes from 2 to 9. [since version 2.0]
		* There are 3 difficulty levels in the game, EASY, MEDIUM and HARD.


* For future developers:

	* There are 5 classes included in the package. BattleshipController is the controller of the game including the main method to be run. BattleshipGUI is for setting up the GUI. The other three classes are player classes, representing individual players. I recommend that you start with looking at BattleshipGUI.java.

* <b>W16 final remarks</b>
    * This game is of the model-view-controller design pattern
    * <b>Note about sound effects:</b> You must be on a CSIL machine or your own personal machine to get sound effects to work, you can't play them through ssh'ing. 
    * The sound effects are currently set to the lower bitrate sound effects so that they play better on CSIL, but there are higher quality sound effects that sound much better. To change to the higher bitrate sfx, edit BattleshipGUI.java and find the URL initializations and change all of the file extensions from .aiff to .wav.
    * When running sound effects on a CSIL machine, after the sounds haved played ~30 times the system can't handle them anymore and the sound effects will stop working.
    * Test cases are very underdeveloped for this project, there's a lot of room for work there.

	* Suggested improvement to be made:
        * Make the game adaptable to the Internet. Currently the game can only be played within the same network, is it possible that it be played by 2 players 5000 miles from each other?
        * Somehow get the sound effects to not crash when playing them on a CSIL machine
        * Add options to go back to previous windows when setting up a game
        * Rework the GUI to use actual graphics instead of colored squares


	* There are 16 classes included in the package. BattleshipController is the controller of the game including the main method to be run. The other 5 classes in the controller directory are helper classes. BattleshipGUI is for setting up the GUI. The 6 other classes in the directory are helper classes that hold elements of the gui. The final three classes in the model directory are player classes, representing individual players. I recommend that you start with looking at BattleshipGUI.java.
	* Suggested improvement to be made:
		* Improve the computer's algorithm. It is obvious that playing Battleship game involves two status, hunting (when you have zero knowledge of where a ship is) and targeting (when you've found at least one pixel on a ship). The current algorithm of the computer is fair enough on targeting, but it only does random selections in hunting. However, we know that the possibility of a pixel containing a part of a ship varies from pixel to pixel, and it envolves complicated calculation of probabilities. There's space to improve the computer's algorithm so that the "HARD" mode can become really hard. Here's a reference about the battleship algorithm: 
(http://www.datagenetics.com/blog/december32011/index.html)

```
 F16 | EdwardGuardado | bkorycki97  | F16 final remarks:
```
	
* For whomever takes over the project next, we have refactored the code very much so that the helper classes are pretty much all seperated from botht the GUI and the Controller, but there is still a lot the can be done. The current code runs and creates a game that allows people to play eachother or the computer. We have tested our code with the minimal test suite and also by trial and error, which isn't the greatest way to test code, so we advise that you create stronger test coverage that simulates all the game types. The strongest issues we see still are the ability to go back once a game mode ha been chosen (Issue #15) and the revamp of the boat shapes and sizes (Issue #27). We believe the refactor was very in-depth, but most likely more can be done in the GUI, which might help you attack a GUI revamp. Just as the people before us advised, you should check out how the controller and gui talk to eachother and how the GUI helper classes talk to eachother heading into your work. With that, best of luck and Allons-Y. :)


		* Make the game adaptable to the Internet. Currently the game can only be played within the same network, is it possible that it be played by 2 players 5000 miles from each other?
		* More suggestions on improvement: see issues under this project.


The code can also be found at these links

- [Archive page](https://foo.cs.ucsb.edu/cs56/issues/0000501/)
- [JavaDoc] (https://ucsb-cs56-f16.github.io/cs56-games-battleship_javadoc/javadoc/index.html)
- [Git JavaDoc Repo] (https://github.com/UCSB-CS56-F16/cs56-games-battleship_javadoc)

```
F17 | EthanYSu | ktan97 | F17 final remarks:
```
* For whomever takes over the project next, we added some features to the game such as adding background music, adding test coverage to the game, and improved the GUI by adding more images to the game. The program in it's current state allows the user to create a game of battleship to play against a computer, of multiple difficulties,or play agaisnt someone using their IP address. The code could still be refactored, mainly the two classes BattleshipGUI.java and GameGrid.java. Everything in this program is tightly coupled so adding features to the game could be fairly difficult without the code being refactored first. My suggestion would be to refactor the two classes BattleshipGUI.java and GameGrid.java so changing the GUI and adding features to the game would be easier. 

	* Refactor the classes BattleshipGUI.java and GameGrid.java, if you want to change the GUI or add new features to the game like a back button 
	* Instead of continuing with using Java Swing, use JavaFX
	* Add volume slider
	* Revamp GUI for the boats so that it could accept different shapes and sizes of boats.
	* See issues for other problems with the game
	
Figure 0

![](https://i.imgur.com/Ec6e5vK.png)


Figure 1

![](https://i.imgur.com/CbHuT8s.png)


Figure 2

![](https://i.imgur.com/PxXeQOP.png)


Figure 3

![](https://i.imgur.com/GHJOrJW.png)


Figure 4

![](https://i.imgur.com/8ZYeSSO.png)


Figure 5

![](https://i.imgur.com/1SU9e0B.png)		

<b>FAQ</b>

1. How to run the program?
	* In Unix command line or MacOS terminal, simply type ant run.
	* In Windows or Android machines with Java installed, run the .jar file directly.
2. How come sound effects aren't playing?
    * Sound effects won't play if you're trying to run the program over ssh
    * You must run the program on a physical CSIL machine or run the .jar file locally on your own machine
