package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** Shared drawing helpers. All screen coordinates use a 960 by 600 world. */
public class GameView implements Disposable {
    public static final float WIDTH = 960, HEIGHT = 600;
    static final Color INK = Color.valueOf("0b192b"), CREAM = Color.valueOf("fff0cb");
    static final Color CYAN = Color.valueOf("69dbea"), FADED = Color.valueOf("a0b9c7");
    private final GameAssets assets;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final GlyphLayout layout = new GlyphLayout();
    private final FitViewport viewport = new FitViewport(WIDTH, HEIGHT);
    private final Vector2 pointer = new Vector2();

    GameView(GameAssets assets) {
        this.assets = assets;
        font.setUseIntegerPositions(false);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
    void begin() {
        ScreenUtils.clear(INK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        image(assets.background, 0, 0, WIDTH, HEIGHT);
    }
    void end() { batch.end(); }
    void image(Texture texture, float x, float y, float width, float height) {
        batch.setColor(Color.WHITE); batch.draw(texture, x, y, width, height);
    }
    void panel(float x, float y, float width, float height, float alpha) {
        batch.setColor(INK.r, INK.g, INK.b, alpha);
        batch.draw(assets.pixel, x, y, width, height);
        batch.setColor(Color.WHITE);
    }
    void text(String value, float x, float y, float scale, Color color) {
        font.getData().setScale(scale); font.setColor(color);
        font.draw(batch, value, x, y);
    }
    void centered(String value, float y, float scale, Color color) {
        font.getData().setScale(scale); layout.setText(font, value);
        text(value, (WIDTH - layout.width) / 2, y, scale, color);
    }
    void button(String value, float y, boolean selected) {
        batch.setColor(selected ? CYAN : FADED);
        batch.draw(assets.pixel, 316, y, 328, 48);
        panel(318, y + 2, 324, 44, selected ? 0.82f : 0.98f);
        centered(value, y + 32, 1.12f, selected ? CREAM : FADED);
    }
    Vector2 pointer() {
        return viewport.unproject(pointer.set(Gdx.input.getX(), Gdx.input.getY()));
    }
    boolean overButton(float y) {
        Vector2 p = pointer();
        return p.x >= 316 && p.x <= 644 && p.y >= y && p.y <= y + 48;
    }
    boolean clickedButton(float y) { return Gdx.input.justTouched() && overButton(y); }
    void resize(int width, int height) {
        if (width > 0 && height > 0) viewport.update(width, height, true);
    }
    @Override public void dispose() { batch.dispose(); font.dispose(); }
}
