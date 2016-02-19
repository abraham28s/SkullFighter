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
 * Created by Fer on 18/02/2016.
 */
public class AcercaDe implements Screen {

    private final Principal Principal;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;
    private Fondo fondo;
    private Texture texturaFondo;

    //Botoones
    private BotonMenu btnPlay;
    private Texture texturaBtnPlay;

    public AcercaDe(Principal principal) {
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

        btnPlay = new BotonMenu(texturaBtnPlay);
        btnPlay.setPosicion(20, 20);
        //btnPlay.setPosicion(200, 200);

        batch = new SpriteBatch();
    }

    private void cargarTexturas() {

        texturaFondo=new Texture(Gdx.files.internal("AboutMenu.jpg")); //AcercaDe.png
        texturaBtnPlay = new Texture((Gdx.files.internal("touchBackground.png"))); //acerca de
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        leerEntrada();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        fondo.render(batch);
        btnPlay.render(batch);
        batch.end();
    }

    private void leerEntrada() {
        if(Gdx.input.justTouched()){//saber si el usuario toca
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camara.unproject(coordenadas);//TRaduce coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if(verificarBoton(x,y)==true){
                Gdx.app.log("leerEntrada","TAp sobre el boton");
                //cambiar a pantalla de jugar
                Principal.setScreen(new PantallaJuego(Principal));
            }
        }
    }

    private boolean verificarBoton(float x, float y) {
        Sprite sprite = btnPlay.getSprite();

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

    }
}
