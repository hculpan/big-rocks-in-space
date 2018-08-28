package org.culpan.bris.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.culpan.bris.Utils;

import java.util.Date;

public class EnergyBonus extends Sprite implements NonPlayerObject {
    boolean destroyed = false;

    long timeCreated;

    long duration;

    int bonus;

    public static EnergyBonus createEnergyBonus(double x, double y, long duration, int bonus) {
        EnergyBonus result = new EnergyBonus(Utils.loadTexture("fuel-bonus.png", 25, 25));
        result.setCenter(result.getWidth() / 2, result.getHeight() / 2);

        result.timeCreated = new Date().getTime();
        result.duration = duration;
        result.bonus = bonus;
        result.setRotation(0);

        result.setPosition((int)x, (int)y);

        return result;
    }

    private EnergyBonus(Texture texture) {
        super(texture);
    }

    public void move() {
        long current = new Date().getTime();
        if (current > timeCreated + duration) {
            destroy();
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void destroy() {
        destroyed = true;
    }

    public int getBonus() {
        return bonus;
    }
}
