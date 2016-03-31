package mx.itesm.skullfighter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import java.io.IOException;

import mx.itesm.skullfighter.Principal;

public class DesktopLauncher {
	public static void main (String[] arg) throws IOException {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();



		new LwjglApplication(new Principal(), config);


	}



}
