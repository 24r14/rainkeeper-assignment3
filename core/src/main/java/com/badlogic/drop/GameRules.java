package com.badlogic.drop;

/** Pure Java rules: these can be tested without opening a game window. */
public class GameRules {
    private int score, caught;
    private int lives = 3;
    public void catchRain(boolean gold) {
        if (isGameOver()) return;
        score += gold ? 30 : 10;
        caught++;
    }
    public void loseLife() { lives = Math.max(0, lives - 1); }
    public boolean isGameOver() { return lives == 0; }
    public int getScore() { return score; }
    public int getCaught() { return caught; }
    public int getLives() { return lives; }
    public int getLevel() { return 1 + score / 50; }
    public float fallSpeed() { return Math.min(420, 160 + (getLevel() - 1) * 28); }
    public float spawnInterval() { return Math.max(0.55f, 1.15f - (getLevel() - 1) * 0.08f); }
    public void reset() { score = 0; caught = 0; lives = 3; }
}
