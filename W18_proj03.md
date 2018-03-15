Issues

Refactor playAgain method in the controller class to not create a new GUI
- Currently when hitting a replay button a new GUI is created so all previous data from the last game is "forgotten". This is needed to clean up the way the ship layout and color setting is stored when replaying a game. (points: 200)

Add images for boat
- A boat is represented as colored squares. Replace this with an image of a boat. The boat size would have to scale with the size of the boat. Also if color setting is to be preserved a way to color the image would need to be employed. (points: 200).

Integrate settings into one window
- Put all the settings into a single window. This would allow settings to be modified after they are set before starting the game. (points: 200)

Improve AI
- The AI is currently very basic. It chooses a random number and if it is less than a number defined by the difficulty level your ship is hit. Make more considerations into the AI move based on how the player is performing. (points: 150)
