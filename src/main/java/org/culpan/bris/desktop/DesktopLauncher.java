package org.culpan.bris.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.culpan.bris.BrisGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Big Rocks in Space!";
		config.width = 1600;
		config.height = 900;
		new LwjglApplication(new BrisGame(), config);
	}
}
