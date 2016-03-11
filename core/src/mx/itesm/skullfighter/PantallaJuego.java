
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
 * Created by abrahamsoto on 17/02/16.
 */

public class PantallaJuego extends PantallaAbstracta implements Screen {

    public static final float ANCHO_MAPA = 1280;   // Como se creó en Tiled

    // Referencia al objeto de tipo Game (tiene setScreen para cambiar de pantalla)




    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;

    //fondo
    private Fondo fondo;
    private Texture texturaFondo;
    private Texture texturaBtnDer;
    private Texture texturaBtnIzq;
    private Boton btnDer;
    private Boton btnIzq;
    private Texture texturaBtnBrin;
    private Boton btnBrin;
    private Personaje jugador;
    private Texture[] texturaMovDer;
    private Texture[] texturaMovIzq;
    private int con = 0;

    public PantallaJuego(Principal principal) {

        this.principal = principal;
    }

    @Override
    public void show() {
        setYUpgradeCamara();


        cargarTexturas();

        fondo = new Fondo(texturaFondo);

        jugador = new Personaje(texturaMovDer[0]);
        jugador.setPosicion(75, 150);

        crearYPosBotones();


    }


    public void crearYPosBotones() {
        btnDer = new Boton(texturaBtnDer);
        btnDer.setPosicion(200, 40);
        btnIzq = new Boton(texturaBtnIzq);
        btnIzq.setPosicion(50, 40);
        btnBrin = new Boton(texturaBtnBrin);
        btnBrin.setPosicion(1100, 40);
        batch = new SpriteBatch();
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
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        leerEntrada(); // Revisar eventos


        batch.setProjectionMatrix(camara.combined);



        // DIBUJA
        batch.begin();
        fondo.render(batch);
        jugador.render(batch);
        jugador.actualizar();
        btnDer.render(batch);
        btnIzq.render(batch);
        btnBrin.render(batch);


        batch.end();
    }










    @Override
    public void leerEntrada() {
        btnIzq.estaTocado();
        if(Gdx.input.isTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if(verificarBoton(x,y,btnDer)){
                movimientoDer();
            }
            if(verificarBoton(x,y,btnIzq)){
                movimientoIzq();
            }
        }
        if(Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);  //traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if(verificarBoton(x, y, btnBrin)){
                if(jugador.getEstado() == Personaje.Estado.NORMAL) {
                    jugador.movimientoBrin();
                }
            }
        }
    }



    private void movimientoIzq() {

        float x = jugador.getSprite().getX();
        float y = jugador.getSprite().getY();
        if(Gdx.graphics.getDeltaTime()%17<1&&Gdx.graphics.getDeltaTime()%17>0) {
            jugador.setSprite(texturaMovIzq[con % 3]);
            con++;
        }
        jugador.setPosicion(x-5,y);

    }

    private void movimientoDer() {
        float x = jugador.getSprite().getX();
        float y = jugador.getSprite().getY();
        if(Gdx.graphics.getDeltaTime()%17<1&&Gdx.graphics.getDeltaTime()%17>0) {
            jugador.setSprite(texturaMovDer[con % 3]);
            con++;
        }
        jugador.setPosicion(x+5,y);
    }

    @Override
    public void cargarTexturas() {
        texturaFondo = new Texture(Gdx.files.internal("Escenario1Cortado.png"));

        texturaMovDer= new Texture[3];
        texturaMovDer[0] = new Texture(Gdx.files.internal("SkullCam1der.png"));
        texturaMovDer[1] = new Texture(Gdx.files.internal("SkullCam2der.png"));
        texturaMovDer[2] = new Texture(Gdx.files.internal("SkullCam3der.png"));

        texturaMovIzq = new Texture[3];
        texturaMovIzq[0] = new Texture(Gdx.files.internal("SkullCam1izq.png"));
        texturaMovIzq[1] = new Texture(Gdx.files.internal("SkullCam2izq.png"));
        texturaMovIzq[2] = new Texture(Gdx.files.internal("SkullCam3izq.png"));

        texturaBtnDer = new Texture(Gdx.files.internal("botonder.png"));
        texturaBtnIzq = new Texture(Gdx.files.internal("botonizq.png"));
        texturaBtnBrin = new Texture(Gdx.files.internal("Escenario1Cortado.png"));
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

