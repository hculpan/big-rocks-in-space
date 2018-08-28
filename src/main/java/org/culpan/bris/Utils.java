package org.culpan.bris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Utils {
    public static Texture loadTexture(String name, int width, int height) {
        Pixmap pixmap200 = new Pixmap(Gdx.files.internal(name));
        Pixmap pixmap100 = new Pixmap(width, height, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        Texture result = new Texture(pixmap100);
        pixmap200.dispose();
        pixmap100.dispose();

        return result;
    }


}
