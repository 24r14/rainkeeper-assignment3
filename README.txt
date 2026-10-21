RAINKEEPER - ASSIGNMENT 3

Rainkeeper is a Java game made with libGDX. Move the gold bucket across a
nighttime garden, collect rain, and avoid red hazards. This project extends
the previous Rainkeeper drop game with a main menu and separate screen classes.

HOW TO PLAY
1. Choose PLAY from the main menu. Use the mouse, or UP/DOWN and ENTER.
2. Move with LEFT/RIGHT, A/D, or hold the left mouse button and drag.
3. Blue rain gives 10 points. Gold rain gives 30 points.
4. Missing blue or gold rain costs one life. Catching a red hazard also costs
   one life. Letting a red hazard fall past the bucket does not cost a life.
5. There are three lives. At zero lives, the game opens the results screen.
6. Every 50 points raises the level. Rain falls faster and spawns more often.
   Speed is capped at 420 world units/second; the shortest spawn interval is
   0.55 seconds. A high score lasts until the application is closed.

CONTROLS
Main menu: UP/DOWN + ENTER or click. Choose PLAY, HOW TO PLAY, SOUND, or QUIT.
Playing: P pauses/resumes; R starts a fresh round; M toggles sound effects;
ESC returns to the main menu. Leaving a round ends that round.
Pause screen: click RESUME or MAIN MENU, or use P / ESC.
Results: ENTER or R plays again; ESC returns to the menu. Buttons also work.
Help: ENTER starts a game; ESC returns to the menu.

WHAT CHANGED FOR ASSIGNMENT 3
The old version kept title, gameplay, and results in one ApplicationListener.
This version uses a Game subclass and four separate ScreenAdapter subclasses.
The menu has actual choices instead of starting the game on any click.
The help page explains the rules. Results show the score, level, drops caught,
and session best, with choices to replay or return to the menu.
The previous custom artwork, gold drops, hazards, lives, difficulty, and sound
effects are retained. This is a personalized extension, not a copy of the
guide's single-drop menu and gameplay example.

CLASS RESPONSIBILITIES
All game classes are in core/src/main/java/com/badlogic/drop/.
- RainkeeperGame: creates shared resources, changes screens, remembers sound
  settings and the best score, and disposes resources when the app closes.
- MenuScreen: main menu buttons and keyboard navigation.
- HelpScreen: instructions and controls.
- PlayScreen: bucket movement, spawning, collision handling, HUD, and pause.
- ResultScreen: final score, replay, and return to menu.
- GameRules: score, lives, levels, and difficulty limits; no graphics code.
- FallingItem: one falling object, its kind, movement, and rim collision.
- GameAssets: loads the textures and sounds once and disposes them once.
- GameView: shared drawing, font, viewport, and button hit detection.
The desktop entry point is lwjgl3/src/main/java/com/badlogic/drop/lwjgl3/
Lwjgl3Launcher.java.

HOW THE CODE WORKS
RainkeeperGame starts by showing MenuScreen. Choosing PLAY creates PlayScreen.
Every frame, PlayScreen reads input, updates objects using delta time, and
draws the game. Items are stored in an Array and removed by looping backward.
A drop is caught when its bottom crosses the bucket opening and its x-range
overlaps the opening. This avoids catching drops against the bucket's sides.
GameRules changes the score or lives. At zero lives, RainkeeperGame switches
to ResultScreen. A new PlayScreen gives each replay a fresh round.
The screens share GameAssets and GameView, so switching screens does not load
another set of textures. The old screen is disposed after each switch.

RUN FROM SOURCE
Use JDK 17 or newer (tested here with JDK 25), with JAVA_HOME set correctly.
Windows: open a terminal in this folder and run:
  gradlew.bat lwjgl3:run
macOS or Linux:
  sh gradlew lwjgl3:run
The first build needs Internet access to download Gradle and dependencies.
On macOS the Gradle run task adds -XstartOnFirstThread automatically.

INTELLIJ IDEA
Open this whole folder as a Gradle project. Choose an installed JDK 17 or newer
as the Gradle JVM. Wait for sync, then run the lwjgl3:run Gradle task.
Edit the .java source files under core/src/main/java, not build/classes.

BUILD A RUNNABLE JAR
  gradlew.bat lwjgl3:fatJar
The result is lwjgl3/build/libs/Rainkeeper-Assignment3.jar.
Run it with: java -jar lwjgl3/build/libs/Rainkeeper-Assignment3.jar
On macOS: java -XstartOnFirstThread -jar lwjgl3/build/libs/Rainkeeper-Assignment3.jar
The JAR includes artwork, sounds, and dependencies; Java is still required.

TEST
  gradlew.bat core:verifyRules
This runs ordinary Java assertions through a custom Gradle task, not JUnit.
It checks point values, the level boundary, losing three lives, game-over
score freeze, round reset, difficulty caps, rim crossing, and missed objects.
  gradlew.bat core:check lwjgl3:fatJar
also runs these checks before producing the desktop build.

REFERENCE AND CREDITS
Reference: https://libgdx.com/wiki/start/simple-game-extended
The Game/Screen structure follows the concepts in this guide. The menu layout,
screen organization, theme, rules, and UI are customized for Rainkeeper.
Assets in assets/custom were created for the earlier Rainkeeper version with
AI assistance and are reused here. The code and documentation were also
prepared with AI assistance. See ASSETS.txt for details.
