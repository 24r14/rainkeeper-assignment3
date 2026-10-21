package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/** Loads the existing Rainkeeper artwork once, rather than once per screen. */
public class GameAssets implements Disposable {
    final Texture background = texture("night-garden.png");
    final Texture bucket = texture("bucket.png");
    final Texture rain = texture("rain.png");
    final Texture gold = texture("gold.png");
    final Texture hazard = texture("hazard.png");
    final Texture heart = texture("heart.png");
    final Texture pixel = texture("pixel.png");
    final Sound catchSound = sound("catch.wav");
    final Sound goldSound = sound("gold.wav");
    final Sound hurtSound = sound("hurt.wav");

    private Texture texture(String name) {
        Texture result = new Texture("custom/" + name);
        result.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return result;
    }
    private Sound sound(String name) {
        return Gdx.audio.newSound(Gdx.files.internal("custom/" + name));
    }
    Texture item(FallingItem.Kind kind) {
        if (kind == FallingItem.Kind.GOLD) return gold;
        if (kind == FallingItem.Kind.HAZARD) return hazard;
        return rain;
    }
    @Override public void dispose() {
        for (Texture texture : new Texture[]{background, bucket, rain, gold, hazard, heart, pixel}) texture.dispose();
        catchSound.dispose(); goldSound.dispose(); hurtSound.dispose();
    }
}
