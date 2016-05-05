package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by abrahamsoto on 11/03/16.
 */
public class P2 implements Screen {


    private final Principal principal;
    private Fondo fondo;
    private Texture texturaFondo;
    private SpriteBatch batch;
    private OrthographicCamera camara;
    private FitViewport vista;

    //Preferencas
    private Preferences pref = Gdx.app.getPreferences("Preferencias");


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
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
        pref.putInteger("ropa", pref.getInteger("ropa", 1));
        pref.putInteger("arma", pref.getInteger("arma", 1));
        pref.putInteger("a", pref.getInteger("a",1));
        pref.putInteger("huesos", pref.getInteger("huesos",0));
        pref.putInteger("nivel",pref.getInteger("nivel",0));
        pref.flush();
    }

    public void setYUpgradeCamara() {
        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new FitViewport(Principal.ANCHO_MUNDO,Principal.ALTO_MUNDO,camara);

        Sonidos.cargaAudio();


        pref.flush();
        pref.getBoolean("musica",true);
        pref.getBoolean("boton", true);
        pref.flush();

        if (pref.getBoolean("musica") ) {
            Sonidos.reproducirMusicaFondo();
        }

        //pref = Gdx.app.getPreferences("Música-Prefe");
        //float b = pref.getFloat("volumen",0.6f);
        //Sonidos.musicaFondo.setVolume(b);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
                pref.flush();
            }
            this.dispose();
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

        //pref.putFloat("volumen", Sonidos.musicaFondo.getVolume());
        //pref.flush();
        texturaFondo.dispose();
        batch.dispose();
    }
}
