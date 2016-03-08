package mx.itesm.skullfighter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import java.io.IOException;

import mx.itesm.skullfighter.Principal;

public class DesktopLauncher {
	public static void main (String[] arg) throws IOException {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//TexturePacker.process("Imagenes", "Imagenes", "game");



		new LwjglApplication(new Principal(), config);


	}
	/*public static void main (String[] args) throws IOException {
        Settings settings = new Settings();
        settings.maxWidth = 512;
        settings.maxHeight = 512;
        TexturePacker.process(settings, "../images", "../game-android/assets", "game");

        new LwjglApplication(new Game(), "Game", 320, 480, false);
    }*/


}
