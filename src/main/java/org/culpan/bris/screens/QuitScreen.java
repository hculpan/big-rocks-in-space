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

public class QuitScreen implements Screen {
    Stage stage;

    Game game;

    long nextTimeToPause;

    Screen callingScreen;

    public QuitScreen(Game game, Screen callingScreen) {
        this.game = game;
        this.nextTimeToPause = new Date().getTime() + 500;
        this.callingScreen = callingScreen;

        stage = new Stage(new ScreenViewport());

        Label title = new Label("Big Rocks in Space!", BrisGame.skin,"big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton quitButton = new TextButton("Quit",BrisGame.skin);
        quitButton.setWidth(Gdx.graphics.getWidth()/4);
        quitButton.setPosition(Gdx.graphics.getWidth()/4-quitButton.getWidth()/2,Gdx.graphics.getHeight()/2-quitButton.getHeight()/2);
        quitButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(quitButton);

        TextButton playButton = new TextButton("Resume",BrisGame.skin);
        playButton.setWidth(Gdx.graphics.getWidth()/4);
        playButton.setPosition((float)(Gdx.graphics.getWidth() * 0.75) -playButton.getWidth()/2,Gdx.graphics.getHeight()/2-playButton.getHeight()/2);
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(callingScreen);                if (callingScreen instanceof BrisGameScreen) {
                    ((BrisGameScreen) callingScreen).setTimeToAllowPause(new Date().getTime() + 500);
                }

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
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            if (new Date().getTime() > nextTimeToPause) {
                if (callingScreen instanceof BrisGameScreen) {
                    ((BrisGameScreen) callingScreen).setTimeToAllowPause(new Date().getTime() + 500);
                }
                game.setScreen(callingScreen);
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
