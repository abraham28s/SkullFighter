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
 * Created by abrahamsoto on 08/03/16.
 */
public class Sett extends PantallaAbstracta implements Screen{

    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;

    //fondo
    private Fondo fondo;
    private Texture texturaFondo;

    //botones
    private Boton btnAD;
    private Texture texturaAD;
    private Boton musicAD;
    public static Texture TextureMusic;
    private Boton returnAD;
    private Texture TextureReturn;
    public static Texture TextureMusic2;
    private Texture texturaFondo2;
    private Fondo fondo1;

    private Boton titulo;
    private Texture texturaTitulo;

    private Boton bonus;
    private Texture texturaBonus;

    //Musica

    public Sett(Principal principal) {
        this.principal = principal;
    }

    @Override
    public boolean verificarBoton(float x, float y, Boton btn) {

        Sprite sprite = btn.getSprite();
        return x>=sprite.getX() && x<=sprite.getX()+sprite.getWidth()
                && y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    @Override
    public void leerEntrada() {

        if(Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBoton(x, y, btnAD)) {
                principal.setScreen(new AcercaDe(principal));
            }
        }

        if(Gdx.input.justTouched()){
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;

            if (verificarBoton(x, y, musicAD)) {
                if (Sonidos.musicaFondo.isPlaying()){
                    Sonidos.pausarMusicaFondo();
                    musicAD.setTextura(TextureMusic2);
                    musicAD.setPosicion(630, 300);
                }
                else { Sonidos.reproducirMusicaFondo();
                    musicAD.setTextura(TextureMusic);
                    musicAD.setPosicion(630,300);
                }
                }

            if(verificarBoton(x,y,bonus)){
                Gdx.app.log("leerEntrada", "Tap sobre el hueso BONUS");
            }
        }

        if(Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBoton(x, y, returnAD)) {
                principal.setScreen(new PantallaMenu(principal));
            }
        }
    }

    @Override
    public void cargarTexturas() {;

        texturaFondo = new Texture(Gdx.files.internal("PantallaVacia.png"));
        texturaFondo2 = new Texture(Gdx.files.internal("PantallaVacia.png"));
        texturaTitulo = new Texture(Gdx.files.internal("BotonSettings.png"));
        texturaAD = new Texture(Gdx.files.internal("CreatorsBoton.png"));
        TextureMusic = new Texture(Gdx.files.internal("MusicaOn.png"));
        TextureMusic2 = new Texture(Gdx.files.internal("MusicaOff.png"));

        TextureReturn = new Texture(Gdx.files.internal("BackGame.png"));

        texturaBonus =  new Texture(Gdx.files.internal("GoldBone.png"));

    }

    @Override
    public void crearYPosBotones() {

    }

    @Override
    public void setYUpgradeCamara() {

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
        fondo1 = new Fondo(texturaFondo2);
        fondo1.getSprite().setX(fondo.getSprite().getWidth());
        titulo = new Boton((texturaTitulo));
        titulo.setPosicion(800,577);
        btnAD = new Boton(texturaAD);
        btnAD.setPosicion(100, 450);
        batch = new SpriteBatch();
        musicAD = new Boton(TextureMusic);
//        musicAD.setPosicion(630, 300);
        musicAD.setPosicion(1000, 150);
        returnAD = new Boton(TextureReturn);
        returnAD.setPosicion(30, 30);
        bonus=new Boton(texturaBonus);
        bonus.setPosicion(1000,100);
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
        fondo1.render(batch);
        btnAD.render(batch);
        musicAD.render(batch);
        //bonus.render(batch);
        titulo.render(batch);
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