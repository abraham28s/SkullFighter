package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by abrahamsoto on 11/03/16.
 */
public class P1 implements Screen {

    private Fondo fondo;
    private Texture texturaFondo;

    @Override
    public void show() {
        texturaFondo = new Texture(Gdx.files.internal("PantallaConLogo.jpg"));
        fondo = new Fondo(texturaFondo);
    }

    @Override
    public void render(float delta) {

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
