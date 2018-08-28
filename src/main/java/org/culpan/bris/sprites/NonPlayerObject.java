package org.culpan.bris.sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public interface NonPlayerObject {
    void move();

    Rectangle getBoundingRectangle();

    boolean isDestroyed();

    void destroy();

    void draw(Batch batch);
}
