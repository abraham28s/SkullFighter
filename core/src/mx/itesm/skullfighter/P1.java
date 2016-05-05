package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by abrahamsoto on 11/03/16.
 */
public class P1 implements Screen {


    private final Principal principal;
    private Fondo fondo;
    private Texture texturaFondo;
    private SpriteBatch batch;
    private OrthographicCamera camara;
    private FitViewport vista;

    private int cont = 0;

    public P1(Principal principal) {
        this.principal = principal;
    }

    @Override
    public void show() {
        texturaFondo = new Texture(Gdx.files.internal("PantallaConLogo.png"));
        fondo = new Fondo(texturaFondo);
        batch = new SpriteBatch();
        setYUpgradeCamara();
    }

    public void setYUpgradeCamara() {
        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new FitViewport(Principal.ANCHO_MUNDO,Principal.ALTO_MUNDO,camara);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cont++;
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        fondo.render(batch);

        batch.end();

        if(cont>150){
            principal.setScreen(new P2(principal));
            this.dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width,height);

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
        texturaFondo.dispose();
        batch.dispose();

    }
}
