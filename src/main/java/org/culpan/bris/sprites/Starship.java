package org.culpan.bris.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import org.culpan.bris.Utils;

import java.util.Date;

public class Starship extends Sprite {
    private static final double MAX_SPEED = 6.0;

    float x;

    float y;

    double speed;

    TextureRegion standingShip;

    TextureRegion movingShip [];

    long nextFrameTime;

    int rocketFrameIndex = 0;

    boolean accelerating = false;

    int score = 0;

    int energy = 1000;

    BitmapFontCache bitmapFontCache = new BitmapFontCache(new BitmapFont());

    public static Starship createStarship() {
        Starship result = new Starship();
        result.standingShip = new TextureRegion(Utils.loadTexture("starship-no-rocket.png", 50, 50));
        result.movingShip = new TextureRegion[4];
        result.movingShip[0] = new TextureRegion(Utils.loadTexture("starship-rocket-1.png", 50, 50));
        result.movingShip[1] = new TextureRegion(Utils.loadTexture("starship-rocket-2.png", 50, 50));
        result.movingShip[2] = new TextureRegion(Utils.loadTexture("starship-rocket-3.png", 50, 50));
        result.movingShip[3] = new TextureRegion(Utils.loadTexture("starship-rocket-4.png", 50, 50));
        result.setCenter(25, 25);

        result.y = Gdx.graphics.getHeight() / 2;
        result.x = Gdx.graphics.getWidth() / 2;
        result.setSize(50, 50);

        result.setPosition((int)result.x, (int)result.y);

        result.bitmapFontCache.addText("Score:", 20, Gdx.graphics.getHeight() - 20);
        result.bitmapFontCache.addText("Energy:", 20, Gdx.graphics.getHeight() - 50);
        result.bitmapFontCache.setColor(Color.RED);

        return result;
    }

    private Starship() {
        super();
    }

    public void rotateLeft() {
        rotate(3f);

        if (getRotation() > 359) {
            setRotation(getRotation() % 360);
        }
    }

    public void rotateRight() {
        rotate(-3f);

        if (getRotation() < 0) {
            setRotation(360 + getRotation());
        }
    }

    public void noThrust() {
        accelerating = false;
        speed = speed * 0.9;
        if (speed < .15) {
            speed = 0.0;
        }

        calculateNewPosition();
    }

    private void calculateNewPosition() {
        if (speed < 0.05) return;

        double radians = Math.toRadians(getRotation());
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

    public void fireThruster() {
        if (energy > 0) {
            accelerating = true;
            energy--;
            if (speed < 1.0) {
                speed = 1.0;
            } else if (speed > MAX_SPEED) {
                speed = MAX_SPEED;
            } else {
                speed = speed * 1.10;
            }
        } else {
            noThrust();
        }

        calculateNewPosition();
    }

    @Override
    public void draw(Batch batch) {
        setPosition(x, y);
        if (accelerating) {
            long current = new Date().getTime();
            batch.draw(movingShip[rocketFrameIndex], (int)x, (int)y, 25, 25, 50, 50, 1, 1, getRotation());
            if (current > nextFrameTime) {
                rocketFrameIndex = (rocketFrameIndex + 1) % 4;
                nextFrameTime = current + 50;
            }
        } else {
            nextFrameTime = 0;
            rocketFrameIndex = 0;
            batch.draw(standingShip, (int)x, (int)y, 25, 25, 50, 50, 1, 1, getRotation());
        }

        bitmapFontCache.draw(batch);
        bitmapFontCache.getFont().draw(batch, Integer.toString(score), 75, Gdx.graphics.getHeight() - 20);
        bitmapFontCache.getFont().draw(batch, Integer.toString(energy), 75, Gdx.graphics.getHeight() - 50);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy < 0) {
            this.energy = 0;
        }
    }
}
