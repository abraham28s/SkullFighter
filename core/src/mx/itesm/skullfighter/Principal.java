package mx.itesm.skullfighter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Created by abrahamsoto on 17/02/16.
 */
public class Principal extends Game {
    public static final float ANCHO_MUNDO = 1280;
    public static final float ALTO_MUNDO = 720;

    @Override
    public void create () {

        setScreen(new PantallaMenu(this));
    }

}
