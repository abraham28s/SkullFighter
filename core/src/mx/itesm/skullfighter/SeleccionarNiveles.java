package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Jorge Alvarado on 22/04/2016.
 */
public class SeleccionarNiveles extends PantallaAbstracta implements Screen {

    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    //fondo

    private Fondo fondo1;
    private Texture texturaFondo2;
    private Fondo fondo;
    private Texture texturaFondo;
    private Boton returnAD;
    private Texture TextureReturn;
    private Boton introAD;
    private Texture TextureIntro;
    private Boton nivel1;
    private Texture TextureNivel1;

    public SeleccionarNiveles (Principal principal) {
        this.principal = principal;
    }
    @Override
    public void show() {

        //Crear camara y vista
        setYUpgradeCamara();

        cargarTexturas();
        fondo = new Fondo(texturaFondo);
        fondo1 = new Fondo(texturaFondo2);
        fondo1.getSprite().setX(fondo.getSprite().getWidth());
        returnAD = new Boton(TextureReturn);
        returnAD.setPosicion(30, 30);
        introAD = new Boton (TextureIntro);
        Preferences pref = Gdx.app.getPreferences("Preferencias");
        if (pref.getInteger("nivel") == 1 || pref.getInteger("nivel") == 2){
            introAD.setPosicion(95,300);
        }
        if (pref.getInteger("nivel") == 2){
            nivel1 = new Boton (TextureNivel1);
            nivel1.setPosicion(300,300);
        }

        crearYPosBotones();

        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {

        Preferences pref = Gdx.app.getPreferences("Preferencias");
        batch.setProjectionMatrix(camara.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        leerEntrada(); // Revisar eventos

        // DIBUJA
        batch.begin();
        fondo.render(batch);
        fondo1.render(batch);
        returnAD.render(batch);
        if (pref.getInteger("nivel") == 1 || pref.getInteger("nivel") == 2) {
            introAD.render(batch);
        }
        if (pref.getInteger("nivel") == 2){
            nivel1.render(batch);
        }
        batch.end();
        actualizarFondo();

    }

    private void actualizarFondo() {
        fondo.getSprite().setX(fondo.getSprite().getX() - 1);
        fondo1.getSprite().setX(fondo1.getSprite().getX() - 1);

        if(fondo.getSprite().getX()+fondo.getSprite().getWidth() == 0){
            fondo.getSprite().setX(fondo1.getSprite().getWidth());
        }if(fondo1.getSprite().getX()+fondo1.getSprite().getWidth() == 0){
            fondo1.getSprite().setX(fondo.getSprite().getWidth());
        }
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

    @Override
    void leerEntrada() {

        Preferences pref = Gdx.app.getPreferences("Preferencias");
        if(Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBoton(x, y, returnAD)) {
                Sonidos.reproducirBoton();
                principal.setScreen(new PantallaMenu(principal));
            }
        }

        if(Gdx.input.justTouched()){
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBoton(x, y, introAD)) {
                Sonidos.reproducirBoton();
                Sonidos.pausarMusicaFondo();
                principal.setScreen(new PantallaCargando(principal,0));
            }
        }


        if (pref.getInteger("nivel") == 2) {
            if (Gdx.input.justTouched()) {
                Vector3 coordenadas = new Vector3();
                coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camara.unproject(coordenadas);  //traduce las coordenadas
                float x = coordenadas.x;
                float y = coordenadas.y;
                if (verificarBoton(x, y, nivel1)) {
                    Sonidos.reproducirBoton();
                    Sonidos.pausarMusicaFondo();
                    principal.setScreen(new NivelUno(principal));
                }
            }
        }

    }

    @Override
    void cargarTexturas() {

        texturaFondo = new Texture(Gdx.files.internal("PantallaVacia.png"));
        texturaFondo2 = new Texture(Gdx.files.internal("PantallaVacia.png"));
        TextureReturn = new Texture(Gdx.files.internal("BackGame.png"));
        TextureIntro = new Texture (Gdx.files.internal("Intro.png"));
        TextureNivel1 = new Texture (Gdx.files.internal("nivel1.png"));


    }

    @Override
    void crearYPosBotones() {

    }

    @Override
    void setYUpgradeCamara() {

        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new StretchViewport(Principal.ANCHO_MUNDO,Principal.ALTO_MUNDO,camara);

    }
}
