package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by abrahamsoto on 12/02/16.
 */
public class PantallaMenu implements Screen {

    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;

    //fondo
    private Fondo fondo;
    private Texture texturaFondo;

    //boton play
    private BotonMenu btnPlay;
    private Texture texturaBtnPlay;
    public PantallaMenu(Principal principal) {
        this.principal = principal;
    }

    @Override
    public void show() {
        //Crear camara y vista
        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new StretchViewport(Principal.ANCHO_MUNDO,Principal.ALTO_MUNDO,camara);


        cargarTexturas();
        fondo = new Fondo(texturaFondo);
        btnPlay = new BotonMenu(texturaBtnPlay);
        btnPlay.setPosicion(Principal.ANCHO_MUNDO/15,Principal.ALTO_MUNDO-(Principal.ALTO_MUNDO/4));

        batch = new SpriteBatch();
    }

    private void cargarTexturas() {
        texturaFondo = new Texture(Gdx.files.internal("MainMenuSolo.jpg"));
        texturaBtnPlay = new Texture(Gdx.files.internal("BotonStory.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


      //  leerEntrada(); // Revisar eventos


        batch.setProjectionMatrix(camara.combined);



        // DIBUJA
        batch.begin();
        fondo.render(batch);
        btnPlay.render(batch);

        batch.end();
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
