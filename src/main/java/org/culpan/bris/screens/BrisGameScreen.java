package org.culpan.bris.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import org.culpan.bris.FrameRate;
import org.culpan.bris.sprites.*;

import java.util.*;

public class BrisGameScreen implements Screen {
    public static final Random random = new Random();

    Game game;

	SpriteBatch batch;

	Starship ship;

	Texture background;

	List<NonPlayerObject> nonPlayerObjects;

	FrameRate frameRate;

	boolean displayFrameRate = false;

	long lastTimeDisplayFrameRatePressed;

	long lastTimeSpacePressed;

	World world;

    Box2DDebugRenderer debugRenderer;

    long timeToAllowPause;

    public BrisGameScreen(Game game) {
        this.game = game;

/*	    Box2D.init();
	    world = new World(new Vector2(0, 0), true);
	    debugRenderer  = new Box2DDebugRenderer();*/

		batch = new SpriteBatch();

		ship = Starship.createStarship();
		nonPlayerObjects = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
            nonPlayerObjects.add(createRandomAsteroid());
        }

        Pixmap pixmap = new Pixmap(1600, 900, Pixmap.Format.RGBA8888);
        for (int x = 0; x < 1600; x++) {
            for (int y = 0; y < 900; y++) {
                if (random.nextInt(1000) == 1) {
                    pixmap.drawPixel(x, y, Color.rgba8888(1, 1, 1, 1));
                } else {
                    pixmap.drawPixel(x, y, Color.rgba8888(0, 0, 0, 0));
                }
            }
        }

        background = new Texture(pixmap);
        pixmap.dispose();

        frameRate = new FrameRate();
    }

    private Asteroid createRandomAsteroid() {
        return Asteroid.createAsteroid(
                random.nextInt(1600),
                random.nextInt(900),
                random.nextInt(7) + 1,
                random.nextInt(360),
                (random.nextInt(100) < 50),
                random.nextInt(3));
    }

    private List<NonPlayerObject> detectCollisions(Rectangle shipRect, NonPlayerObject nonPlayerObject) {
        List<NonPlayerObject> result = new ArrayList<>();
        nonPlayerObject.move();
        Rectangle r = nonPlayerObject.getBoundingRectangle();
        if (nonPlayerObject instanceof Shot) {
            nonPlayerObjects.forEach(b -> {
                if (b instanceof Asteroid && b.getBoundingRectangle().overlaps(r)) {
                    b.destroy();
                    ship.addScore(50);
                    if (random.nextInt(100) < 35) {
                        EnergyBonus energyBonus = EnergyBonus.createEnergyBonus(((Asteroid) b).getX(), ((Asteroid) b).getY(), 3000, 1000);
                        result.add(energyBonus);
                    }
                }
            });
        } else if (nonPlayerObject instanceof Asteroid) {
            if (r.overlaps(shipRect)) {
                nonPlayerObject.destroy();
                ship.addScore(-50);
                ship.addEnergy(-300);
            }
        } else if (nonPlayerObject instanceof EnergyBonus) {
            if (nonPlayerObject.getBoundingRectangle().overlaps(shipRect)) {
                nonPlayerObject.destroy();
                ship.addEnergy(((EnergyBonus)nonPlayerObject).getBonus());
            }
        }

        return result;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            if (timeToAllowPause == 0) {
                game.setScreen(new PauseScreen(game, this));
            } else {
                long current = new Date().getTime();
                if (timeToAllowPause < current) {
                    game.setScreen(new PauseScreen(game, this));
                }
            }
        }

        frameRate.update();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            ship.rotateLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            ship.rotateRight();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            ship.fireThruster();
        } else {
            ship.noThrust();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            long current = new Date().getTime();
            if (lastTimeSpacePressed + 150 < current) {
                Shot shot = Shot.createShot(
                        ship.getX() + (ship.getWidth() / 2),
                        ship.getY() + (ship.getHeight() / 2),
                        (int)ship.getRotation());
                nonPlayerObjects.add(shot);
                lastTimeSpacePressed = current;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            long current = new Date().getTime();
            if (lastTimeDisplayFrameRatePressed + 500 < current) {
                displayFrameRate = !displayFrameRate;
                lastTimeDisplayFrameRatePressed = current;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            game.setScreen(new QuitScreen(game, this));
        }

        List<NonPlayerObject> adding = new ArrayList<>();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        Rectangle shipRect = ship.getBoundingRectangle();
        nonPlayerObjects.forEach(a -> {
            adding.addAll(detectCollisions(shipRect, a));
            a.draw(batch);
        });
        ship.draw(batch);
        batch.end();

        int numAsteroids = 0;
        Iterator<NonPlayerObject> it = nonPlayerObjects.iterator();
        while (it.hasNext()) {
            NonPlayerObject a = it.next();
            if (a.isDestroyed()) {
                it.remove();
            } else if (a instanceof Asteroid){
                numAsteroids++;
            }
        }

        nonPlayerObjects.addAll(adding);

        if (numAsteroids < 16 && random.nextInt(1000) < (16 - numAsteroids)) {
            nonPlayerObjects.add(createRandomAsteroid());
        }

        if (displayFrameRate) {
            frameRate.render();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }

    @Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		frameRate.dispose();
	}

    public long getTimeToAllowPause() {
        return timeToAllowPause;
    }

    public void setTimeToAllowPause(long timeToAllowPause) {
        this.timeToAllowPause = timeToAllowPause;
    }
}
