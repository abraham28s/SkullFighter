package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by abrahamsoto on 30/03/16.
 */
public class NivelUno extends PantallaAbstracta implements Screen {

    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;

    Fondo fondo;

    Componente vidaP;
    Texture TexturaVidaP;
    Componente vidaE;
    Texture TexturaVidaE;

    Personaje jugador;
    Texture TexturaJugador;
    PersonajeIA enemigo;
    Texture TexturaEnemigo;
    private Boton btnDer;
    private Texture texturaBtnDer;
    private Boton btnIzq;
    private Texture texturaBtnIzq;
    private Boton btnBrin;
    private Texture texturaBtnBrin;
    private Boton btnPausa;
    private Texture texturaPausa;
    private Texture texturaFondo;
    private Texture[] texturaMovDer;
    private Texture[] texturaMovIzq;
    private int con = 0;
    int Mov = 0;


    public NivelUno(Principal principal) {
        this.principal = principal;
    }

    @Override
    public void show() {
        setYUpgradeCamara();

        cargarTexturas();

        fondo = new Fondo(texturaFondo);

        jugador = new Personaje(texturaMovDer[0]);
        jugador.setPosicion(-15, -30);

        crearYPosBotones();
    }


    @Override
    void leerEntrada() {

        Vector3 coordenadas = new Vector3();
        coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camara.unproject(coordenadas);  //traduce las coordenadas
        float x = coordenadas.x;
        float y = coordenadas.y;

        if(Gdx.input.isTouched()) {
            if(verificarBoton(x,y,btnDer) && verificarBordes()){
                movimientoDer();

            }
            if(verificarBoton(x,y,btnIzq) && verificarBordes()){
                movimientoIzq();

            }
        }
        if(Gdx.input.justTouched()) {
            if(verificarBoton(x, y, btnBrin)){
                if(jugador.getEstado() == Personaje.Estado.NORMAL) {
                    jugador.movimientoBrin();
                }
            }
        }
        if(Gdx.input.justTouched() &&Gdx.input.isTouched()){
            if(verificarBoton(x,y,btnDer) && verificarBoton(x, y, btnBrin) && verificarBordes()){
                movimientoDer();

                if(jugador.getEstado() == Personaje.Estado.NORMAL) {
                    jugador.movimientoBrin();
                }
            }
            if(verificarBoton(x,y,btnIzq)&& verificarBoton(x, y, btnBrin) && verificarBordes() ){
                movimientoIzq();
                if(jugador.getEstado() == Personaje.Estado.NORMAL) {
                    jugador.movimientoBrin();
                }
            }
            if(verificarBoton(x,y, btnPausa)){

                //cambiar a pantalla de jugar
                principal.setScreen(new P2(principal));
            }
        }

    }

    private void movimientoIzq() {

        float x = jugador.getSprite().getX();
        float y = jugador.getSprite().getY();
        //System.out.println(Gdx.graphics.getDeltaTime());
        if(Mov%4==0) {
            jugador.setSprite(texturaMovIzq[con % 3]);
            con++;
        }
        if(con>500){
            con=0;
        }
        Mov++;
        if(Mov>500){
            Mov=0;
        }
        jugador.setPosicion(x - 5, y);
    }

    private void movimientoDer() {
        float x = jugador.getSprite().getX();
        float y = jugador.getSprite().getY();
        if(Mov%4==0) {
            jugador.setSprite(texturaMovDer[con % 3]);
            con++;
        }
        if(con>500){
            con=0;
        }
        Mov++;
        if(Mov>500){
            Mov = 0;
        }
        jugador.setPosicion(x+5,y);
    }

    @Override
    void cargarTexturas() {

        texturaFondo = new Texture(Gdx.files.internal("Entrenamiento.png"));
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
        texturaBtnBrin = new Texture(Gdx.files.internal("BotonJump.png"));
        texturaPausa = new Texture(Gdx.files.internal("BackMenu.png"));
    }

    @Override
    void crearYPosBotones() {
        btnDer = new Boton(texturaBtnDer);
        btnDer.setPosicion(200, 40);
        //btnDer.setPosicion(TAM_CELDA,5*TAM_CELDA);
        btnIzq = new Boton(texturaBtnIzq);
        btnIzq.setPosicion(50, 40);
        btnBrin = new Boton(texturaBtnBrin);
        btnBrin.setPosicion(1100, 40);
        batch = new SpriteBatch();
        btnPausa = new Boton(texturaPausa);
        btnPausa.setPosicion(1100, 600);
        batch = new SpriteBatch();
    }

    @Override
    void setYUpgradeCamara() {
        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new StretchViewport(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO, camara);

    }


    boolean verificarBordes() {
        //System.out.println(jugador.getSprite().getX());
        if(jugador.getSprite().getX()<-135 ){
            jugador.getSprite().setX(-130);
            return false;
        }else if(jugador.getSprite().getX()>1045 ){
            jugador.getSprite().setX(1035);
            return false;
        }

        return true;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        leerEntrada(); // Revisar eventos
        // Mover la cámara para que siga al personaje



        batch.setProjectionMatrix(camara.combined);

        // DIBUJA
        batch.begin();
        fondo.render(batch);
        jugador.render(batch);
        jugador.actualizar();


        btnIzq.render(batch);
        btnDer.render(batch);
        btnBrin.render(batch);
        btnPausa.render(batch);
        batch.end();
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
