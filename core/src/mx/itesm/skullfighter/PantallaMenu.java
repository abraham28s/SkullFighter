package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
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


        texturaBtnStory = new Texture(Gdx.files.internal("BotonStory.png"));
        texturaBtnCustom = new Texture(Gdx.files.internal("BotonCustomize.png"));
        texturaBtnSettings = new Texture(Gdx.files.internal("BotonSettings.png"));

        texturabtnMMenu = new Texture(Gdx.files.internal("MainMenu.png"));
    }

    @Override
    public void crearYPosBotones() {
        btnStory = new Boton(texturaBtnStory);
        btnStory.setPosicion(Principal.ANCHO_MUNDO / 15, Principal.ALTO_MUNDO - (Principal.ALTO_MUNDO / 4)-109);

        btnCustom = new Boton(texturaBtnCustom);

        btnCustom.setPosicion(Principal.ANCHO_MUNDO / 15, Principal.ALTO_MUNDO - (Principal.ALTO_MUNDO / 4) - 270);

        btnSettings = new Boton(texturaBtnSettings);
        btnSettings.setPosicion(Principal.ANCHO_MUNDO / 15, Principal.ALTO_MUNDO - (Principal.ALTO_MUNDO / 4) - 431);

        btnMMenu =new Boton(texturabtnMMenu);
        btnMMenu.setPosicion(700,575);
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
        if(Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBoton(x, y, btnStory)) {
                principal.setScreen(new PantallaJuego(principal));
                Sonidos.efectoBoton.play();
                Sonidos.pausarMusicaFondo();
            }  else if (verificarBoton(x, y, btnCustom)) {
                Gdx.app.log("leerEntrada", "Tap sobre el boton custom");
                Sonidos.efectoBoton.play();
                principal.setScreen(new Costumize(principal));
            } else if (verificarBoton(x, y, btnSettings)) {
                principal.setScreen(new Sett(principal));
                Sonidos.efectoBoton.play();
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