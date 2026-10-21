package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;

/** A real main menu with mouse and keyboard navigation. */
public class MenuScreen extends ScreenAdapter {
    private final RainkeeperGame game;
    private int selection;
    private final float[] rows = {300, 238, 176, 114};

    public MenuScreen(RainkeeperGame game) { this.game = game; }
    @Override public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) selection = (selection + 3) % 4;
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) selection = (selection + 1) % 4;
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) game.toggleSound();
        int action = -1;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) action = selection;
        for (int i = 0; i < rows.length; i++) if (game.view.clickedButton(rows[i])) action = i;
        if (action == 0) { game.startGame(); return; }
        if (action == 1) { game.openHelp(); return; }
        if (action == 2) game.toggleSound();
        if (action == 3) { Gdx.app.exit(); return; }

        GameView v = game.view;
        v.begin();
        v.panel(230, 82, 500, 446, 0.95f);
        v.centered("NIGHT GARDEN / COLLECT & SURVIVE", 493, 0.9f, GameView.CYAN);
        v.centered("RAINKEEPER", 448, 3.1f, GameView.CREAM);
        v.centered("A little rain. A big challenge.", 397, 1.1f, GameView.FADED);
        String[] options = {"PLAY", "HOW TO PLAY", "SOUND: " + (game.isMuted() ? "OFF" : "ON"), "QUIT"};
        for (int i = 0; i < rows.length; i++) v.button(options[i], rows[i], selection == i || v.overButton(rows[i]));
        v.image(game.assets.bucket, 104, 66, 104, 86);
        v.image(game.assets.gold, 151, 220, 38, 46);
        v.image(game.assets.rain, 776, 306, 38, 46);
        v.centered("Best this session: " + game.getBestScore() + "    |    UP / DOWN + ENTER, or click", 44, 0.95f, GameView.CREAM);
        v.end();
    }
    @Override public void resize(int width, int height) { game.view.resize(width, height); }
}
