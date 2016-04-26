package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
public class PantallaMenu extends PantallaAbstracta implements Screen {

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

    private Boton btnCustom;
    private Texture texturaBtnCustom;

    private Boton btnSettings;
    private Texture texturaBtnSettings;

    private Boton btnMMenu;
    private Texture texturabtnMMenu;

    private Texture texturaFondo2;
    private Fondo fondo1;

    private Boton   titCustomize;
    private Texture textCustomize;
    private Boton   titStory;
    private Texture textStory;
    private Boton   titSettings;
    private Texture textSettings;

    public PantallaMenu(Principal principal) {
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

        crearYPosBotones();

        batch = new SpriteBatch();
    }

    public void cargarTexturas() {

        texturaFondo = new Texture(Gdx.files.internal("PantallaVacia.png"));
        texturaFondo2 = new Texture(Gdx.files.internal("PantallaVacia.png"));

        //Botones
        texturaBtnStory = new Texture(Gdx.files.internal("Boton_Story.png"));
        texturaBtnCustom = new Texture(Gdx.files.internal("Boton_Customize.png"));
        texturaBtnSettings = new Texture(Gdx.files.internal("Boton_Settings.png"));

        //Titulos
        texturabtnMMenu = new Texture(Gdx.files.internal("MainMenu.png"));
        textCustomize = new Texture(Gdx.files.internal("BotonCustomize.png"));
        textStory = new Texture(Gdx.files.internal("BotonStory.png"));
        textSettings = new Texture(Gdx.files.internal("BotonSettings.png"));
    }

    @Override
    public void crearYPosBotones() {

        btnStory = new Boton(texturaBtnStory);
        btnStory.setPosicion(65, 60);
        titStory = new Boton(textStory);
        titStory.setPosicion(95,400);

        btnCustom = new Boton(texturaBtnCustom);
        btnCustom.setPosicion(488,60);
        titCustomize = new Boton(textCustomize);
        titCustomize.setPosicion(430,400);

        btnSettings = new Boton(texturaBtnSettings);
        btnSettings.setPosicion(910,60);
        titSettings = new Boton(textSettings);
        titSettings.setPosicion(900,400);

        btnMMenu =new Boton(texturabtnMMenu);
        btnMMenu.setPosicion(733,600);
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

        batch.setProjectionMatrix(camara.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        leerEntrada(); // Revisar eventos

            // DIBUJA
        batch.begin();
        fondo.render(batch);
        fondo1.render(batch);
        btnMMenu.render(batch);
        btnStory.render(batch);
        btnCustom.render(batch);
        btnSettings.render(batch);
        titStory.render(batch);
        titCustomize.render(batch);
        titSettings.render(batch);


        /*textCustomize
        textStory
        textSettings*/



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


    public void leerEntrada() {
        Preferences pref = Gdx.app.getPreferences("Preferencias");
        if(Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBoton(x, y, btnStory)) {

                pref.putBoolean("guardar",true);
                pref.flush();

                if (pref.getInteger("nivel") == 1 || pref.getInteger("nivel") == 2){
                    principal.setScreen(new SeleccionarNiveles(principal));
                    Sonidos.reproducirBoton();
                    Sonidos.cargarEfectos();
                }

                if (pref.getInteger("nivel") == 0) {
                    principal.setScreen(new Comic1(principal));
                    pref.putInteger("nivel", 1);
                    pref.flush();
                    Sonidos.reproducirBoton();
                    Sonidos.pausarMusicaFondo();
                    Sonidos.cargarEfectos();
                }

            }  else if (verificarBoton(x, y, btnCustom)) {
                Gdx.app.log("leerEntrada", "Tap sobre el boton custom");
                Sonidos.reproducirBoton();
                principal.setScreen(new Costumize(principal));
            } else if (verificarBoton(x, y, btnSettings)) {
                principal.setScreen(new Sett(principal));
                Sonidos.reproducirBoton();
                Gdx.app.log("leerEntrada", "Tap sobre el boton sett");

            }
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
}