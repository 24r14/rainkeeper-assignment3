package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;

public class ResultScreen extends ScreenAdapter {
    private final RainkeeperGame game;
    private final int score, caught, level;
    public ResultScreen(RainkeeperGame game, int score, int caught, int level) {
        this.game = game; this.score = score; this.caught = caught; this.level = level;
    }
    @Override public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.R) || game.view.clickedButton(176)) { game.startGame(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || game.view.clickedButton(110)) { game.openMenu(); return; }
        GameView v = game.view;
        v.begin(); v.panel(180, 76, 600, 454, 0.96f);
        v.centered("THE STORM WINS", 492, 1f, GameView.CYAN);
        v.centered("GAME OVER", 448, 3f, GameView.CREAM);
        v.centered("FINAL SCORE  " + score, 381, 1.9f, GameView.CYAN);
        v.centered("Level " + level + "    /    " + caught + " drops caught", 333, 1.15f, GameView.FADED);
        v.centered("Best this session: " + game.getBestScore(), 296, 1.1f, GameView.CREAM);
        v.centered("All three lives are gone. Try a new strategy.", 252, 1f, GameView.FADED);
        v.button("PLAY AGAIN  /  ENTER", 176, true);
        v.button("MAIN MENU  /  ESC", 110, v.overButton(110));
        v.end();
    }
    @Override public void resize(int width, int height) { game.view.resize(width, height); }
}
