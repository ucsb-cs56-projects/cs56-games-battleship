# cs56-games-battleship

This is an extended version of the game Battleship. 

project history
===============
```
 W14 | andrewberls 5pm | OliverHKU  | Battleship game
```

* Original developer description - the Milton Bradley board game that has won the hearts of children for over four decades. It will be single-player (vs. computer) and will have various difficulty settings (easy, normal, hard).  You can also host/join games and play against your friends online WITH THE POWER OF JAVA NETWORKING!

* Current version: 2.1.1
* Previous versions:
	* version 1: See "Original developer description" (see figure 0)
	* version 2.0: New feature added. Player can customize ship sizes in the "against computer" mode through a new window.(see figure 1) Illegal ship sizes will be reported to the player. (see figure 2)
	* version 2.1: New feature added. There will be a hit count under the game board indicating how many pixels on the opponent's ship has the player already hit. (see figure 3)
	* <b>version 2.1.1</b>: Bug fix on checking ship sizes validity. In version 2.0, if user input is not a number, program will terminate. Now fixed. (see figure 4)

* Game description - this Battleship game has 3 modes for user to choose from: hosting a game, joining a game, and playing against a computer. (see figure 5)
	
	* Hosting a game: User's IP address will be displayed, and user shall wait for someone else to join his game.
	* Joining a game: User shall be told the host's IP address, and input the IP address to join a game.
	* Playing with a computer:
		* User can custimize ship sizes from 2 to 9. [since version 2.0]
		* There are 3 difficulty levels in the game, EASY, MEDIUM and HARD.


* For future developers:
	* There are 5 classes included in the package. BattleshipController is the controller of the game including the main method to be run. BattleshipGUI is for setting up the GUI. The other three classes are player classes, representing individual players. I recommend that you start with looking at BattleshipGUI.java.
	* Suggested improvement to be made:
		* Improve the computer's algorithm. It is obvious that playing Battleship game involves two status, hunting (when you have zero knowledge of where a ship is) and targeting (when you've found at least one pixel on a ship). The current algorithm of the computer is fair enough on targeting, but it only does random selections in hunting. However, we know that the possibility of a pixel containing a part of a ship varies from pixel to pixel, and it envolves complicated calculation of probabilities. There's space to improve the computer's algorithm so that the "HARD" mode can become really hard. Here's a reference about the battleship algorithm: 
(http://www.datagenetics.com/blog/december32011/index.html)

		* Make the game adaptable to the Internet. Currently the game can only be played within the same network, is it possible that it be played by 2 players 5000 miles from each other?
		* More suggestions on improvement: see issues under this project.


The code can also be found at these links

- [Archive page](https://foo.cs.ucsb.edu/cs56/issues/0000501/)



![](http://i.imgur.com/bLJD2Wi.jpg)

figure 0



![](http://i.imgur.com/E8tnJgH.png)

figure 1



![](http://i.imgur.com/jAmS9n9.png)

figure 2



![](http://i.imgur.com/t8NVd2M.png)

figure 3



![](http://i.imgur.com/ACUXISc.png)

figure 4



![](http://i.imgur.com/9BjJ868.png)

figure 5


<b>FAQ</b>

1. How to run the program?
	* In Unix command line or MacOS terminal, simply type ant run.
	* In Windows or Android machines with Java installed, run the .jar file directly.
