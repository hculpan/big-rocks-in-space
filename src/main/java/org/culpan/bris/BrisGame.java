package org.culpan.bris;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.culpan.bris.screens.TitleScreen;

public class BrisGame extends Game {
    public static Skin skin;

	@Override
	public void create () {
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        this.setScreen(new TitleScreen(this));
    }

	@Override
	public void render () {
	    super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
