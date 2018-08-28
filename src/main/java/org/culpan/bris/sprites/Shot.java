package org.culpan.bris.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.culpan.bris.Utils;

public class Shot extends Sprite implements NonPlayerObject {
    double x;

    double y;

    double speed;

    boolean destroyed = false;

    public static Shot createShot(double x, double y, int direction) {
        Shot result = new Shot(Utils.loadTexture("shot.png", 5, 5));
        result.setCenter(result.getWidth() / 2, result.getHeight() / 2);

        result.y = y;
        result.x = x;
        result.speed = 10;
        result.setRotation(direction);

        result.setPosition((int)result.x, (int)result.y);

        return result;
    }

    private Shot(Texture texture) {
        super(texture);
    }

    public void move() {
        double radians = Math.toRadians(getRotation());
        double xDelta = Math.sin(radians);
        double yDelta = Math.cos(radians);
        x += speed * -xDelta;
        y += speed * yDelta;

        if (x < -25 || x > Gdx.graphics.getWidth() + 25) {
            destroyed = true;
        }

        if (y < -25 || y > Gdx.graphics.getHeight() + 25) {
            destroyed = true;
        }

        setPosition((int)x, (int)y);
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void destroy() {
        destroyed = true;
    }

}
