Rainkeeper

Move the bucket to catch rain and avoid red hazards.
Blue drops give 10 points; gold drops give 30. Missing a drop or catching
a hazard costs one of three lives. At zero lives, the results screen opens.
Every 50 points increases the difficulty.

Controls
Use the arrow keys or A/D to move, or hold and drag the mouse.
P pauses, R restarts, M switches sound, and Esc returns to the menu.
The main menu has Play, How to Play, Sound and Quit.

Run
Open the project folder in IntelliJ IDEA with JDK 17 or later.
Run the Gradle task lwjgl3:run. The first build needs internet access.
Windows command: gradlew.bat lwjgl3:run
macOS/Linux command: sh gradlew lwjgl3:run

Code
RainkeeperGame switches between MenuScreen, HelpScreen, PlayScreen and
ResultScreen. PlayScreen updates falling items and checks catches.
GameRules manages score, lives and difficulty. GameAssets and GameView
handle shared resources and drawing.

Reference: https://libgdx.com/wiki/start/simple-game-extended
Asset and library details: ../ASSETS.txt
