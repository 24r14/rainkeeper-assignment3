package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;

public class HelpScreen extends ScreenAdapter {
    private final RainkeeperGame game;
    public HelpScreen(RainkeeperGame game) { this.game = game; }
    @Override public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || game.view.clickedButton(72)) { game.openMenu(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || game.view.clickedButton(134)) { game.startGame(); return; }
        GameView v = game.view;
        v.begin(); v.panel(120, 48, 720, 510, 0.96f);
        v.centered("HOW TO PLAY", 522, 2.5f, GameView.CREAM);
        v.image(game.assets.rain, 200, 408, 38, 46);
        v.image(game.assets.gold, 437, 408, 38, 46);
        v.image(game.assets.hazard, 661, 408, 38, 46);
        v.text("+10 points", 250, 436, 1.05f, GameView.CYAN);
        v.text("+30 points", 486, 436, 1.05f, GameView.CREAM);
        v.text("-1 life", 710, 436, 1.05f, GameView.CREAM);
        v.centered("Move with LEFT / RIGHT, A / D, or drag the bucket.", 371, 1.05f, GameView.FADED);
        v.centered("Catch blue and gold rain. Let red hazards fall past you.", 336, 1.05f, GameView.FADED);
        v.centered("Missing rain or catching a hazard costs one of your 3 lives.", 301, 1.05f, GameView.FADED);
        v.centered("Every 50 points: higher level, faster rain, more drops.", 266, 1.05f, GameView.FADED);
        v.centered("P: pause    R: restart    M: sound    ESC: main menu", 219, 1.02f, GameView.CYAN);
        v.button("PLAY  /  ENTER", 134, true);
        v.button("BACK TO MENU  /  ESC", 72, v.overButton(72));
        v.end();
    }
    @Override public void resize(int width, int height) { game.view.resize(width, height); }
}
