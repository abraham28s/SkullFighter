

package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
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

    //botones
    private BotonMenu btnStory;
    private Texture texturaBtnStory;

    private BotonMenu btnVs;
    private Texture texturaBtnVs;

    private BotonMenu btnCustom;
    private Texture texturaBtnCustom;

    private BotonMenu btnSettings;
    private Texture texturaBtnSettings;


    public PantallaMenu(Principal principal) {
        this.principal = principal;
    }

    @Override
    public void show() {
        //Crear camara y vista
        camara = new OrthographicCamera(EjemploPantalla.ANCHO_MUNDO, EjemploPantalla.ALTO_MUNDO);
        camara.position.set(EjemploPantalla.ANCHO_MUNDO / 2, EjemploPantalla.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new StretchViewport(EjemploPantalla.ANCHO_MUNDO,EjemploPantalla.ALTO_MUNDO,camara);


        cargarTexturas();
        fondo = new Fondo(texturaFondo);
        btnStory = new BotonMenu(texturaBtnStory);
        btnStory.setPosicion(EjemploPantalla.ANCHO_MUNDO/15,EjemploPantalla.ALTO_MUNDO-(EjemploPantalla.ALTO_MUNDO/4));

        btnVs = new BotonMenu(texturaBtnVs);
        btnVs.setPosicion(EjemploPantalla.ANCHO_MUNDO/15,EjemploPantalla.ALTO_MUNDO-(EjemploPantalla.ALTO_MUNDO/4)-143);

        btnCustom = new BotonMenu(texturaBtnCustom);
        btnCustom.setPosicion(EjemploPantalla.ANCHO_MUNDO/15,EjemploPantalla.ALTO_MUNDO-(EjemploPantalla.ALTO_MUNDO/4)-284);

        btnSettings = new BotonMenu(texturaBtnSettings);
        btnSettings.setPosicion(EjemploPantalla.ANCHO_MUNDO/15,EjemploPantalla.ALTO_MUNDO-(EjemploPantalla.ALTO_MUNDO/4)-431);

        batch = new SpriteBatch();
    }

    private void cargarTexturas() {
        texturaFondo = new Texture(Gdx.files.internal("MainMenuSolo.jpg"));
        texturaBtnStory = new Texture(Gdx.files.internal("BotonStory.png"));
        texturaBtnVs = new Texture(Gdx.files.internal("BotonVersus.png"));
        texturaBtnCustom = new Texture(Gdx.files.internal("BotonCustomize.png"));
        texturaBtnSettings = new Texture(Gdx.files.internal("BotonSettings.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        leerEntrada(); // Revisar eventos


        batch.setProjectionMatrix(camara.combined);



        // DIBUJA
        batch.begin();
        fondo.render(batch);
        btnStory.render(batch);
        btnVs.render(batch);
        btnCustom.render(batch);
        btnSettings.render(batch);

        batch.end();
    }

    private void leerEntrada() {
        if(Gdx.input.justTouched()){
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if( verificarBotonStory(x,y)){
                Gdx.app.log("leerEntrada", "Tap sobre el boton");
                principal.setScreen(new PantallaJuego(principal));
            }
        }
    }

    private boolean verificarBotonStory(float x, float y) {
        return false;
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


