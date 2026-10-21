package com.badlogic.drop;

import com.badlogic.gdx.math.Rectangle;

/** Run with gradlew.bat core:verifyRules. Checks behavior without a graphics window. */
public class GameRulesTest {
    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
    public static void main(String[] args) {
        GameRules rules = new GameRules();
        check(rules.getLives() == 3 && rules.getLevel() == 1, "New round state");
        rules.catchRain(false); rules.catchRain(true);
        check(rules.getScore() == 40 && rules.getCaught() == 2 && rules.getLevel() == 1, "Blue and gold rewards");
        float speed = rules.fallSpeed(), interval = rules.spawnInterval();
        rules.catchRain(false);
        check(rules.getLevel() == 2 && rules.fallSpeed() > speed && rules.spawnInterval() < interval, "Difficulty at exactly 50 points");
        rules.loseLife(); rules.loseLife();
        check(!rules.isGameOver() && rules.getLives() == 1, "Two misses still allow play");
        rules.loseLife(); rules.loseLife(); rules.catchRain(true);
        check(rules.isGameOver() && rules.getLives() == 0 && rules.getScore() == 50, "Game over freezes score and clamps lives");
        rules.reset();
        check(rules.getScore() == 0 && rules.getCaught() == 0 && rules.getLives() == 3 && rules.getLevel() == 1, "Restart resets the round");
        for (int i = 0; i < 1000; i++) rules.catchRain(true);
        check(rules.fallSpeed() == 420 && rules.spawnInterval() == 0.55f, "Difficulty has safe limits");

        Rectangle bucket = new Rectangle(400, 54, 104, 86);
        FallingItem aligned = new FallingItem(430, FallingItem.Kind.BLUE);
        aligned.update(1, 400); // Crosses the rim in one frame: should not tunnel through.
        check(aligned.crossesOpening(bucket), "Catch a drop crossing the opening");
        aligned.update(0.1f, 100);
        check(!aligned.crossesOpening(bucket), "Do not catch again below the rim");
        FallingItem outside = new FallingItem(80, FallingItem.Kind.HAZARD);
        outside.update(1, 400);
        check(!outside.crossesOpening(bucket), "A hazard outside the opening is not caught");
        outside.update(1, 200);
        check(outside.isBelowGarden(), "Remove items past the garden");
        System.out.println("PASS: scoring, level boundaries, lives, game-over freeze, restart, difficulty caps, rim collision and misses.");
    }
}
