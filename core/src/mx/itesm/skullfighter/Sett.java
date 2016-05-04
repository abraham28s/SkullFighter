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
 * Created by abrahamsoto on 08/03/16.
 */
public class Sett extends PantallaAbstracta implements Screen{

    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    //fondo
    private Fondo fondo, fondo1;

    //botones
    private Boton titulo, btnAD;
    private Boton musicAD, musicAD2;
    private Boton returnAD;
    private Boton restartAD;
    private Boton comic;

    //texturas
    private Texture texturaTitulo, texturaAD;
    private Texture texturaFondo, texturaFondo2;
    public static Texture TextureMusic, TextureMusic2;
    private Texture TextureReturn;
    private Texture TextureRestart;
    private Texture texturaComic;

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
                Preferences pref = Gdx.app.getPreferences("Preferencias");
                if (pref.getBoolean("boton") == true ) {
                    Sonidos.reproducirBoton();
                }
                principal.setScreen(new AcercaDe(principal));
                this.dispose();
            }
        }

        if(Gdx.input.justTouched()){
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;

            if (verificarBoton(x, y, musicAD)) {
                Preferences pref = Gdx.app.getPreferences("Preferencias");
                if (Sonidos.musicaFondo.isPlaying()){
                    if (pref.getBoolean("boton") == true ) {
                        Sonidos.reproducirBoton();
                    }
                    Sonidos.pausarMusicaFondo();
                    musicAD.setTextura(TextureMusic2);
                    musicAD.setPosicion(900, 267);
                    pref.putBoolean("musica", false);
                    pref.putBoolean("boton", false);
                    pref.flush();
                }
                else { Sonidos.reproducirMusicaFondo();
                    if (pref.getBoolean("boton") == true ) {
                        Sonidos.reproducirBoton();
                    }
                    musicAD.setTextura(TextureMusic);
                    musicAD.setPosicion(900, 270);
                    pref.putBoolean("musica", true);
                    pref.putBoolean("boton", true);
                    pref.flush();
                }
                }
        }

        if(Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBoton(x, y, returnAD)) {
                Preferences prefe = Gdx.app.getPreferences("Preferencias");
                if (prefe.getBoolean("boton") == true) {
                    Sonidos.reproducirBoton();
                }
                principal.setScreen(new PantallaMenu(principal));
                this.dispose();
            }
        }

        if(Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBoton(x, y, comic)) {
                Preferences prefe = Gdx.app.getPreferences("Preferencias");
                if (prefe.getBoolean("boton") == true) {
                    Sonidos.reproducirBoton();
                }
                principal.setScreen(new Comic1(principal));
                this.dispose();
            }
        }

        if(Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBoton(x, y, restartAD)) {
                Preferences pref = Gdx.app.getPreferences("Preferencias");
                pref.putBoolean("guardar", false);
                pref.flush();

                if (pref.getBoolean("boton") == true) {
                    Sonidos.reproducirBoton();
                }
                principal.setScreen(new P2(this.principal));
                this.dispose();
            }
        }//fjdsklfjasdlkjflksdajflkadsjkflsjdlkf


    }

    @Override
    public void cargarTexturas() {;

        texturaFondo = new Texture(Gdx.files.internal("PantallaVacia.png"));
        texturaFondo2 = new Texture(Gdx.files.internal("PantallaVacia.png"));
        texturaTitulo = new Texture(Gdx.files.internal("BotonSettings.png"));
        texturaAD = new Texture(Gdx.files.internal("CreatorsButton.png"));
        TextureMusic = new Texture(Gdx.files.internal("MusicaOn.png"));
        TextureMusic2 = new Texture(Gdx.files.internal("MusicaOff.png"));
        TextureReturn = new Texture(Gdx.files.internal("BackGame.png"));
        TextureRestart = new Texture(Gdx.files.internal("Restart.png"));
        texturaComic = new Texture(Gdx.files.internal("Comic_Boton.png"));
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
        titulo.setPosicion(800, 577);

        btnAD = new Boton(texturaAD);
        btnAD.setPosicion(160, 265);

        batch = new SpriteBatch();
        musicAD = new Boton(TextureMusic);
        musicAD.setPosicion(900, 270);
        musicAD2 = new Boton(TextureMusic2);
        musicAD2.setPosicion(900,267);

        returnAD = new Boton(TextureReturn);
        returnAD.setPosicion(30, 30);

        restartAD = new Boton (TextureRestart);
        restartAD.setPosicion(1050,30);

        comic = new Boton (texturaComic);
        comic.setPosicion(900,30);

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
        if (Sonidos.musicaFondo.isPlaying()){
            musicAD.render(batch);

        }
        else
            if (!Sonidos.musicaFondo.isPlaying()){
                musicAD2.render(batch);
            }
        titulo.render(batch);
        returnAD.render(batch);
        restartAD.render(batch);
        comic.render(batch);

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
        texturaFondo.dispose();
        texturaFondo2.dispose();
        texturaTitulo.dispose();
        texturaAD.dispose();
        TextureReturn.dispose();
        TextureRestart.dispose();
        texturaComic.dispose();
        //TextureMusic.dispose();
        //TextureMusic2.dispose();
    }
}