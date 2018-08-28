package org.culpan.bris.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.culpan.bris.Utils;

public class Asteroid extends Sprite implements NonPlayerObject{
    static Texture [] asteroidTextures;

    double x;

    double y;

    double speed;

    int direction;

    int directionChange = -1;

    public boolean destroyed = false;

    public static Asteroid createAsteroid(double x, double y, double speed, int direction, boolean rotateClockwise, int size) {
        if (asteroidTextures == null) {
            asteroidTextures = new Texture[3];

            for (int i = 0; i < 3; i++) {
                asteroidTextures[0] = Utils.loadTexture("asteroid-small.png", 30, 30);
                asteroidTextures[1] = Utils.loadTexture("asteroid-medium.png", 60, 60);
                asteroidTextures[2] = Utils.loadTexture("asteroid-large.png", 120, 120);
            }
        }
        Asteroid result = new Asteroid(asteroidTextures[size]);
        result.setCenter(result.getWidth() / 2, result.getHeight() / 2);

        result.y = y;
        result.x = x;
        result.speed = speed;
        result.setRotation(0);
        result.direction = direction;
        if (!rotateClockwise) {
            result.directionChange = 1;
        }

        result.setPosition((int)result.x, (int)result.y);

        return result;
    }

    private Asteroid(Texture texture) {
        super(texture);
    }

    public void move() {
        setRotation(getRotation() + directionChange);

        double radians = Math.toRadians(direction);
        double xDelta = Math.sin(radians);
        double yDelta = Math.cos(radians);
        x += speed * -xDelta;
        y += speed * yDelta;

        if (x < -25) {
            x = Gdx.graphics.getWidth() + 25;
        } else if (x > Gdx.graphics.getWidth() + 25) {
            x = -25;
        }

        if (y < -25) {
            y = Gdx.graphics.getHeight() + 25;
        } else if (y > Gdx.graphics.getHeight() + 25) {
            y = -25;
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
