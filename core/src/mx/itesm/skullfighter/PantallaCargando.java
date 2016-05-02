package mx.itesm.skullfighter;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.xml.soap.Text;

/**
 * Created by PAVILION on 11/04/2016.
 */
public class PantallaCargando extends PantallaAbstracta implements Screen {

    private Principal principal;

    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    //Imagen Cargando
    private Texture texturaCargado;
    private Componente spriteCargando;
    //Fondo de la textura
    private Texture texturaFondoCargando;
    private Fondo spriteFondoCargando;

    private int pantallaCargar;

    public PantallaCargando(Principal principal,int pantalla){
        this.principal = principal;
        this.pantallaCargar = pantalla;
    }


    @Override
    public void show() {
        setYUpgradeCamara();
        batch = new SpriteBatch();


    }

    @Override
    public void render(float delta) {

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

    }

    @Override
    void cargarTexturas() {

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
