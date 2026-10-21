package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/** Owns shared resources and switches between the four independent screens. */
public class RainkeeperGame extends Game {
    GameAssets assets;
    GameView view;
    private boolean muted;
    private int bestScore;

    @Override public void create() {
        assets = new GameAssets();
        view = new GameView(assets);
        openMenu();
    }

    public void openMenu() { changeScreen(new MenuScreen(this)); }
    public void openHelp() { changeScreen(new HelpScreen(this)); }
    public void startGame() { changeScreen(new PlayScreen(this)); }
    void finishGame(GameRules rules) {
        rememberScore(rules.getScore());
        changeScreen(new ResultScreen(this, rules.getScore(), rules.getCaught(), rules.getLevel()));
    }

    private void changeScreen(Screen next) {
        Screen previous = getScreen();
        setScreen(next); // Calls hide(), show(), and resize() at the right times.
        if (previous != null) previous.dispose();
    }

    public boolean isMuted() { return muted; }
    public void toggleSound() { muted = !muted; }
    public int getBestScore() { return bestScore; }
    void rememberScore(int score) { bestScore = Math.max(bestScore, score); }

    @Override public void dispose() {
        if (getScreen() != null) getScreen().dispose();
        view.dispose();
        assets.dispose();
    }
}
