

package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



/**
 * Created by abrahamsoto on 12/02/16.
 */


public class PantallaMenu implements Screen,PantallaAbstracta {

    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;

    //fondo
    private Fondo fondo;
    private Texture texturaFondo;

    //botones
    private Boton btnStory;
    private Texture texturaBtnStory;

    private Boton btnVs;
    private Texture texturaBtnVs;

    private Boton btnCustom;
    private Texture texturaBtnCustom;

    private Boton btnSettings;
    private Texture texturaBtnSettings;

    private int contador=1;
    private Texture texturaTec;
    private Texture texturaTit;


    public PantallaMenu(Principal principal) {
        this.principal = principal;
    }

    @Override
    public void show() {
        //Crear camara y vista
        setYUpgradeCamara();

        cargarTexturas();
        fondo = new Fondo(texturaTec);

        crearYPosBotones();

        batch = new SpriteBatch();
    }

    public void cargarTexturas() {

        texturaFondo = new Texture(Gdx.files.internal("MainMenuSolo.jpg"));
        texturaTec = new Texture(Gdx.files.internal("PantallaConLogo.jpg"));
        texturaTit = new Texture(Gdx.files.internal("PantallaNomJuego.jpg"));
        texturaBtnStory = new Texture(Gdx.files.internal("BotonStory.png"));
        texturaBtnVs = new Texture(Gdx.files.internal("BotonVersus.png"));
        texturaBtnCustom = new Texture(Gdx.files.internal("BotonCustomize.png"));
        texturaBtnSettings = new Texture(Gdx.files.internal("BotonSettings.png"));
    }

    @Override
    public void crearYPosBotones() {
        btnStory = new Boton(texturaBtnStory);
        btnStory.setPosicion(Principal.ANCHO_MUNDO / 15, Principal.ALTO_MUNDO - (Principal.ALTO_MUNDO / 4));

        btnVs = new Boton(texturaBtnVs);
        btnVs.setPosicion(Principal.ANCHO_MUNDO / 15, Principal.ALTO_MUNDO - (Principal.ALTO_MUNDO / 4) - 143);

        btnCustom = new Boton(texturaBtnCustom);
        btnCustom.setPosicion(Principal.ANCHO_MUNDO / 15, Principal.ALTO_MUNDO - (Principal.ALTO_MUNDO / 4) - 284);

        btnSettings = new Boton(texturaBtnSettings);
        btnSettings.setPosicion(Principal.ANCHO_MUNDO / 15, Principal.ALTO_MUNDO - (Principal.ALTO_MUNDO / 4) - 431);
    }

    @Override
    public void setYUpgradeCamara() {
        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new StretchViewport(Principal.ANCHO_MUNDO,Principal.ALTO_MUNDO,camara);
    }

    @Override
    public void render(float delta) {
        contador++;
        fondo.setTextura(texturaTec);
        if(contador<100){
            batch.begin();
            fondo.render(batch);
            batch.end();
        }else if (contador>100&&contador<300) {
            fondo.setTextura(texturaTit);
            batch.begin();
            fondo.render(batch);
            batch.end();
        }else {
            fondo.setTextura(texturaFondo);
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
    }

    public void leerEntrada() {
        if(Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBoton(x, y, btnStory)) {
                principal.setScreen(new PantallaJuego(principal));
            } else if (verificarBoton(x, y, btnVs)) {
                Gdx.app.log("leerEntrada", "Tap sobre el botonvs");
            } else if (verificarBoton(x, y, btnCustom)) {
                Gdx.app.log("leerEntrada", "Tap sobre el boton custom");
            } else if (verificarBoton(x, y, btnSettings)) {
                principal.setScreen(new Sett(principal));
                Gdx.app.log("leerEntrada", "Tap sobre el boton sett");
            }
        }
    }

    @Override
    public boolean verificarBoton(float x, float y, Boton btn) {
        Sprite sprite = btn.getSprite();
        return x>=sprite.getX() && x<=sprite.getX()+sprite.getWidth()
                && y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
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


