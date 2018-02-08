# Jeff Longo and Richard Rodriguez

Answer these questions and delete them as we go

(a) This project allows the user to play the game battleship against a computer, or online via java networking.

(b) As a user, I can start the game and choose to play locally or online so that I can play by myself or with someone else.
    As a user, I can choose a color and difficulty so that I can determine which player is me.
    As a user, I can lay out my ships so that I have a choice where my ships are placed
    As a user, I can choose a pixel where to fire a shot so that I can attempt to win the game by my own choices.

(c) The software runs. When the game is opened, there is a choice of opponent type (online or against AI), a choice of color, and a choice of difficulty (if against AI). The user then places his/her ships and then attempts to sink the opponents.

(d) As a user, I can alter the background picture of the game through a settings menu so that the game is more visually appealing.
As a user, I can leave the game through an exit button so that I do not have to manually close the window to quit the game.
As a user, I can start the game with a default set of ship sizes so that I can start playing without entering values for ship sizes.
As a user, I can see my results after a match through a point system so that I can compare my score with other players.

(e) The current README.md includes various comments from the previous students that have worked on this legacy code. The features that students have added to the game are stated in the README.md along with suggestions for both improvement and fixes. There are also screenshots and a FAQ that are useful in understanding the overall function of this game. It would be very useful to organize all the previous information in the README.md. This could be done by creating sections that contain information on features, issues, possible improvements and suggestions.    


(f) This game runs on ant. The various targets in the build.xml file contain a short description of what the target should do. This build.xml file is in working condition and successfully built and ran the battleship game.

(g) On the git hub repository there are 13 issues stated. These issues add up to about 2000 possible points. Most of theses issues are explained straightforwardly and are simple to understand.

(h) (20 pts) A list of additional issues that you may have added, if any. For each, a link to the issue is good enough.

(i) The code is farily well written and clear. The game's main method lies in the BattleshipController class. Depending on the game type, a controller for that game mode is created. A GUI is also created using one of the GUI's defined in the view folder. The game type controller class handles the game's logic, while the player/opponent moves are handled by the classes (depending on type) in the model folder. Essentially, the hierarchy is BattleshipController->GameController->GUI/Player.

(j) The build.xml has a target called "test" which mentions the use of JUnit in its description. Running `ant test` states that many tests have failed and that some of the files have a compiling error. The actual tests do not give a failure message but instead give a error message. These test files are located in the src/edu/ucsb/cs56/projects/games/battleship/view directory. In total there are only three test files that seem to be in regards to the GUI of the game. The file called GameGridTest.java only has one test inside it and should probably have more. There should also be more tests for actual gameplay and other functions of the code. A test file for every class in the game would probably be an efficient way of testing all the functions of the code. The various test for the GUI also seem a bit incomplete and should have additional tests implemented. 
