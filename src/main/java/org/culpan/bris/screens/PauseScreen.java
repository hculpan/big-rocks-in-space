package org.culpan.bris.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.culpan.bris.BrisGame;

import java.util.Date;

public class PauseScreen implements Screen {
    Stage stage;

    Game game;

    Screen callingScreen;

    long nextTimeToPause;

    long nextTimeToQuit;

    public PauseScreen(Game game, Screen callingScreen) {
        this.game = game;
        this.callingScreen = callingScreen;
        this.nextTimeToPause = new Date().getTime() + 500;
        this.nextTimeToQuit = nextTimeToPause;

        stage = new Stage(new ScreenViewport());

        Label title = new Label("Big Rocks in Space!", BrisGame.skin,"big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton playButton = new TextButton("Resume",BrisGame.skin);
        playButton.setWidth(Gdx.graphics.getWidth()/2);
        playButton.setPosition(Gdx.graphics.getWidth()/2-playButton.getWidth()/2,Gdx.graphics.getHeight()/2-playButton.getHeight()/2);
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if (callingScreen instanceof BrisGameScreen) {
                    ((BrisGameScreen) callingScreen).setTimeToAllowPause(new Date().getTime() + 500);
                }
                game.setScreen(callingScreen);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            if (new Date().getTime() > nextTimeToPause) {
                if (callingScreen instanceof BrisGameScreen) {
                    ((BrisGameScreen) callingScreen).setTimeToAllowPause(new Date().getTime() + 500);
                }
                game.setScreen(callingScreen);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            if (new Date().getTime() > nextTimeToQuit) {
                Gdx.app.exit();
            }
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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
    public void dispose() {

    }
}
