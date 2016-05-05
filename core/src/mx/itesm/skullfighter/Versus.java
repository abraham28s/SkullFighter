package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
 * Created by Fer on 08/03/2016.
 */
public class Versus extends PantallaAbstracta implements Screen {

    private final Principal Principal;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;
    private Fondo fondo;
    private Texture texturaFondo;

    //Botoones
    private Boton btnBack;
    private Texture texturaBack;

    public Versus(Principal principal) {
        this.Principal = principal;
    }

    @Override
    public void show() {

        camara = new OrthographicCamera(mx.itesm.skullfighter.Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new StretchViewport(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO,camara);

        cargarTexturas(); //cargar texturas
        fondo = new Fondo(texturaFondo);
        //botones nombres

        btnBack = new Boton(texturaBack);
        btnBack.setPosicion(20, 20);
        //btnPlay.setPosicion(200, 200);

        batch = new SpriteBatch();
    }

    public void cargarTexturas() {

        texturaFondo=new Texture(Gdx.files.internal("PantallaVacia.png"));
        texturaBack = new Texture((Gdx.files.internal("BackGame.png")));
    }

    @Override
    void crearYPosBotones() {

    }

    @Override
    void setYUpgradeCamara() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        leerEntrada();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        fondo.render(batch);
        btnBack.render(batch);
        batch.end();
    }

    public void leerEntrada() {
        Preferences pref = Gdx.app.getPreferences("Preferencias");
        if(Gdx.input.justTouched()){//saber si el usuario toca
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camara.unproject(coordenadas);//TRaduce coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if(verificarBoton(x,y, btnBack)){
                if (pref.getBoolean("boton") == true ){
                    Sonidos.reproducirBoton();
                }
                Gdx.app.log("leerEntrada","TAp sobre back");
                //cambiar a pantalla de jugar
                Principal.setScreen(new PantallaMenu(Principal));
                this.dispose();
            }
        }
    }

    public boolean verificarBoton(float x, float y, Boton btn) {
        Sprite sprite = btnBack.getSprite();

        return x>sprite.getX() && x<=sprite.getX()+sprite.getWidth()
                && y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
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
        texturaBack.dispose();

    }
}
