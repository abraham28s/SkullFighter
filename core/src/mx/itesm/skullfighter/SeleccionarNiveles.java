package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
    Preferences pref = Gdx.app.getPreferences("Preferencias");
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

    private Boton nivel2;
    private Texture TextureNivel2;

    private Boton nivel3;
    private Texture TextureNivel3;
    private int huesos;


    public SeleccionarNiveles (Principal principal,int huesos) {
        this.principal = principal;
        this.huesos = huesos;
    }
    @Override
    public void show() {

        //Crear camara y vista
        setYUpgradeCamara();

        cargarTexturas();
        Preferences pref = Gdx.app.getPreferences("Preferencias");
        pref.flush();
        fondo = new Fondo(texturaFondo);
        fondo1 = new Fondo(texturaFondo2);
        fondo1.getSprite().setX(fondo.getSprite().getWidth());
        returnAD = new Boton(TextureReturn);
        returnAD.setPosicion(555, 20);

            introAD = new Boton(TextureIntro);
            introAD.setPosicion(30,150);

            nivel1 = new Boton(TextureNivel1);
            nivel1.setPosicion(360, 150);

            nivel2 = new Boton(TextureNivel2);
            nivel2.setPosicion(670, 150);

            nivel3 = new Boton(TextureNivel3);
            nivel3.setPosicion(985, 150);



        //if (pref.getInteger("nivel") == 1 || pref.getInteger("nivel") == 2){






        crearYPosBotones();

        batch = new SpriteBatch();
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);

    }

    @Override
    public void render(float delta) {

        Preferences pref = Gdx.app.getPreferences("Preferencias");
        batch.setProjectionMatrix(camara.combined);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        leerEntrada(); // Revisar eventos

        // DIBUJA
        batch.begin();
        fondo.render(batch);
        fondo1.render(batch);

        nivel1.getSprite().setColor(Color.GRAY);
        nivel2.getSprite().setColor(Color.GRAY);
        nivel3.getSprite().setColor(Color.GRAY);



        //if (pref.getInteger("nivel") == 1 || pref.getInteger("nivel") == 2) {
        if (pref.getInteger("nivel") == 1 || pref.getInteger("nivel") == 2 || pref.getInteger("nivel") == 3 || pref.getInteger("nivel") == 4) {
            introAD.render(batch);
        }
        if(pref.getInteger("nivel") == 2 || pref.getInteger("nivel") == 3 || pref.getInteger("nivel") == 4) {
            nivel1.getSprite().setColor(Color.WHITE);
        }

        if(pref.getInteger("nivel") == 3 || pref.getInteger("nivel") == 4) {
            nivel2.getSprite().setColor(Color.WHITE);
        }

        if(pref.getInteger("nivel") == 4) {
            nivel3.getSprite().setColor(Color.WHITE);
        }
        nivel3.render(batch);
        nivel2.render(batch);
        nivel1.render(batch);

        returnAD.render(batch);
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
        texturaFondo2.dispose();
        TextureReturn.dispose();
        TextureIntro.dispose();
        TextureNivel1.dispose();
        TextureNivel2.dispose();
        TextureNivel3.dispose();
    }

    @Override
    void leerEntrada() {

        Preferences pref = Gdx.app.getPreferences("Preferencias");
        if(Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas,vista.getScreenX(),vista.getScreenY(),vista.getScreenWidth(),vista.getScreenHeight());  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBoton(x, y, returnAD)) {
                if (pref.getBoolean("boton") == true ) {
                    Sonidos.reproducirBoton();
                }
                principal.setScreen(new PantallaMenu(principal));
                this.dispose();
            }
            if (pref.getInteger("nivel") == 1 || pref.getInteger("nivel") == 2 || pref.getInteger("nivel") == 3 || pref.getInteger("nivel") == 4) {
                if (verificarBoton(x, y, introAD)) {
                    if (pref.getBoolean("boton") == true ) {
                        Sonidos.reproducirBoton();
                    }
                    Sonidos.pausarMusicaFondo();

                    principal.setScreen(new /*PantallaCargando(principal, 0, huesos, 0)*/ NivelTutorial(principal,huesos));
                    this.dispose();
                }
            }
            if(pref.getInteger("nivel") == 2 || pref.getInteger("nivel") == 3 || pref.getInteger("nivel") == 4) {
                if (verificarBoton(x, y, nivel1)) {
                    if (pref.getBoolean("boton") == true ) {
                        Sonidos.reproducirBoton();
                    }

                    Sonidos.pausarMusicaFondo();

                    principal.setScreen(new PantallaCargando(principal, 0, huesos, 0));
                    this.dispose();
                }
            }

            if(pref.getInteger("nivel") == 3 || pref.getInteger("nivel") == 4) {
                if (verificarBoton(x, y, nivel2)) {
                    if (pref.getBoolean("boton") == true) {
                        Sonidos.reproducirBoton();
                    }

                    Sonidos.pausarMusicaFondo();

                    principal.setScreen(new PantallaCargando(this.principal, 2, pref.getInteger("huesos"), 1));
                    this.dispose();
                }
            }

            if(pref.getInteger("nivel") == 4) {
                if (verificarBoton(x, y, nivel3)) {
                    if (pref.getBoolean("boton") == true) {
                        Sonidos.reproducirBoton();
                    }

                    Sonidos.pausarMusicaFondo();

                    principal.setScreen(new PantallaCargando(principal, 4, huesos, 1, 15, 0, 1, 50));
                    this.dispose();
                }
            }


        }









        /*      if(Gdx.input.justTouched()){
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBoton(x, y, nivel2)) {
                Sonidos.reproducirBoton();
                Sonidos.pausarMusicaFondo();
                principal.setScreen(new PantallaCargando(principal,0));//Pantalla castigo
            }
        }*/
    }

    @Override
    void cargarTexturas() {

        texturaFondo = new Texture(Gdx.files.internal("PantallaVacia.png"));
        texturaFondo2 = new Texture(Gdx.files.internal("PantallaVacia.png"));
        TextureReturn = new Texture(Gdx.files.internal("BackGame.png"));
        TextureIntro = new Texture (Gdx.files.internal("Boton_Tutorial.png"));
        TextureNivel1 = new Texture (Gdx.files.internal("Boton_Nivel1.png"));
        TextureNivel2 = new Texture (Gdx.files.internal("Boton_Nivel2.png"));
        TextureNivel3 = new Texture (Gdx.files.internal("Boton_Nivel3.png"));


    }

    @Override
    void crearYPosBotones() {

    }

    @Override
    void setYUpgradeCamara() {

        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new FitViewport(Principal.ANCHO_MUNDO,Principal.ALTO_MUNDO,camara);

    }
}