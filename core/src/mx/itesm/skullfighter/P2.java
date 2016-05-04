package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by abrahamsoto on 11/03/16.
 */
public class P2 implements Screen {


    private final Principal principal;
    private Fondo fondo;
    private Texture texturaFondo;
    private SpriteBatch batch;
    private OrthographicCamera camara;
    private StretchViewport vista;

    //Preferencas
    private Preferences pref;


    private int cont = 0;

    public P2(Principal principal) {
        this.principal = principal;
    }

    @Override
    public void show() {
        texturaFondo = new Texture(Gdx.files.internal("PantallaNomJuego.png"));
        fondo = new Fondo(texturaFondo);
        batch = new SpriteBatch();
        setYUpgradeCamara();
    }

    public void setYUpgradeCamara() {
        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new StretchViewport(Principal.ANCHO_MUNDO,Principal.ALTO_MUNDO,camara);

        Sonidos.cargaAudio();

        Preferences pref = Gdx.app.getPreferences("Preferencias");
        pref.getBoolean("musica",true);
        pref.getBoolean("boton", true);
        pref.flush();
        if (pref.getBoolean("musica")) {
            Sonidos.reproducirMusicaFondo();
        }

        //pref = Gdx.app.getPreferences("Música-Prefe");
        //float b = pref.getFloat("volumen",0.6f);
        //Sonidos.musicaFondo.setVolume(b);

    }

    @Override
    public void render(float delta) {
        cont++;
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        fondo.render(batch);

        batch.end();

        if(cont>150){
            principal.setScreen(new PantallaMenu(principal));
            Preferences pref = Gdx.app.getPreferences("Preferencias");
            pref.getBoolean("guardar",false);
            if (pref.getBoolean("guardar") == false) {
                pref.putInteger("nivel", 0);
            }
            this.dispose();
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

        //pref.putFloat("volumen", Sonidos.musicaFondo.getVolume());
        //pref.flush();
        texturaFondo.dispose();
        batch.dispose();
    }
}
