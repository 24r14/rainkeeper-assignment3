package com.badlogic.drop;

import com.badlogic.gdx.math.Rectangle;

/** One independently moving rain drop or hazard. */
public class FallingItem {
    public enum Kind { BLUE, GOLD, HAZARD }
    final Rectangle bounds;
    final Kind kind;
    private float previousBottom;

    public FallingItem(float x, Kind kind) {
        this.kind = kind;
        bounds = new Rectangle(x, 492, 38, 46);
        previousBottom = bounds.y;
    }
    public void update(float delta, float speed) {
        previousBottom = bounds.y;
        bounds.y -= delta * speed;
    }
    public boolean crossesOpening(Rectangle bucket) {
        float rim = bucket.y + 61;
        return previousBottom >= rim && bounds.y <= rim
            && bounds.x + bounds.width > bucket.x + 12
            && bounds.x < bucket.x + bucket.width - 12;
    }
    public boolean isBelowGarden() { return bounds.y + bounds.height < 54; }
}
