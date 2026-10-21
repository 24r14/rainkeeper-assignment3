package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import java.util.Random;

/** Input, simulation, and drawing for one round. Menu screens never update this round. */
public class PlayScreen extends ScreenAdapter {
    private final RainkeeperGame game;
    private final GameRules rules = new GameRules();
    private final Rectangle bucket = new Rectangle(428, 54, 104, 86);
    private final Array<FallingItem> items = new Array<>();
    private final Random random = new Random();
    private boolean paused;
    private float spawnTimer, elapsed, feedbackTime;
    private String feedback = "";

    public PlayScreen(RainkeeperGame game) { this.game = game; }
    @Override public void render(float delta) {
        float dt = Math.min(delta, 0.04f);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || (paused && game.view.clickedButton(210))) { game.openMenu(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) { game.startGame(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) game.toggleSound();
        if (Gdx.input.isKeyJustPressed(Input.Keys.P) || (paused && game.view.clickedButton(274))) paused = !paused;
        if (!paused) {
            input(dt);
            updateGame(dt);
            if (rules.isGameOver()) { game.finishGame(rules); return; }
        }
        draw();
    }

    private void input(float dt) {
        float direction = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) direction--;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) direction++;
        bucket.x += direction * 520 * dt;
        if (Gdx.input.isTouched()) bucket.x = game.view.pointer().x - bucket.width / 2;
        bucket.x = MathUtils.clamp(bucket.x, 20, GameView.WIDTH - bucket.width - 20);
    }

    private void updateGame(float dt) {
        elapsed += dt;
        feedbackTime = Math.max(0, feedbackTime - dt);
        spawnTimer += dt;
        if (spawnTimer >= rules.spawnInterval()) {
            spawnTimer = 0;
            int roll = random.nextInt(100);
            FallingItem.Kind kind = roll < 18 ? FallingItem.Kind.HAZARD : roll < 35 ? FallingItem.Kind.GOLD : FallingItem.Kind.BLUE;
            if (elapsed < 1.5f) kind = FallingItem.Kind.BLUE;
            items.add(new FallingItem(35 + random.nextFloat() * 852, kind));
        }
        // Removing from the end prevents us from skipping an item after a catch.
        for (int i = items.size - 1; i >= 0; i--) {
            FallingItem item = items.get(i);
            item.update(dt, rules.fallSpeed());
            if (item.crossesOpening(bucket)) {
                if (item.kind == FallingItem.Kind.HAZARD) {
                    rules.loseLife(); showFeedback("HAZARD  -1 LIFE", game.assets.hurtSound);
                } else {
                    boolean gold = item.kind == FallingItem.Kind.GOLD;
                    rules.catchRain(gold); game.rememberScore(rules.getScore());
                    showFeedback(gold ? "+30  GOLD RAIN" : "+10  RAIN CAUGHT", gold ? game.assets.goldSound : game.assets.catchSound);
                }
                items.removeIndex(i);
            } else if (item.isBelowGarden()) {
                if (item.kind != FallingItem.Kind.HAZARD) {
                    rules.loseLife(); showFeedback("MISSED RAIN  -1 LIFE", game.assets.hurtSound);
                }
                items.removeIndex(i);
            }
            if (rules.isGameOver()) break;
        }
    }

    private void showFeedback(String message, Sound sound) {
        feedback = message; feedbackTime = 1.1f;
        if (!game.isMuted()) sound.play(0.35f);
    }
    private void draw() {
        GameView v = game.view;
        v.begin();
        for (FallingItem item : items) v.image(game.assets.item(item.kind), item.bounds.x, item.bounds.y, item.bounds.width, item.bounds.height);
        v.image(game.assets.bucket, bucket.x, bucket.y, bucket.width, bucket.height);
        v.panel(18, 516, 924, 66, 0.94f);
        v.text("RAINKEEPER", 36, 563, 1.65f, GameView.CREAM);
        v.text("NIGHT GARDEN / COLLECT & SURVIVE", 37, 537, 0.72f, GameView.FADED);
        v.text("SCORE", 365, 563, 0.75f, GameView.FADED);
        v.text(String.format("%04d", rules.getScore()), 365, 543, 1.45f, GameView.CYAN);
        v.text("LEVEL", 493, 563, 0.75f, GameView.FADED);
        v.text(String.format("%02d", rules.getLevel()), 493, 543, 1.45f, GameView.CREAM);
        v.text("LIVES", 625, 563, 0.75f, GameView.FADED);
        for (int i = 0; i < rules.getLives(); i++) v.image(game.assets.heart, 624 + i * 31, 527, 25, 25);
        v.text("BEST " + game.getBestScore(), 782, 554, 1f, GameView.CREAM);
        v.panel(18, 13, 924, 31, 0.94f);
        v.text("MOVE: ARROWS / A,D / DRAG", 32, 34, 0.82f, GameView.FADED);
        v.text("P: PAUSE   R: RESTART   M: SOUND   ESC: MENU", 470, 34, 0.82f, GameView.FADED);
        if (feedbackTime > 0) v.centered(feedback, 478, 1.05f, GameView.CYAN);
        if (paused) {
            v.panel(0, 0, 960, 600, 0.72f);
            v.centered("PAUSED", 390, 2.7f, GameView.CREAM);
            v.centered("Take a breath. The storm can wait.", 350, 1f, GameView.FADED);
            v.button("RESUME  /  P", 274, true);
            v.button("MAIN MENU  /  ESC", 210, v.overButton(210));
        }
        v.end();
    }
    @Override public void resize(int width, int height) { game.view.resize(width, height); }
    @Override public void pause() { paused = true; }
}
