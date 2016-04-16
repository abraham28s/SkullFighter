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

import java.util.Random;

/** * Created by abrahamsoto on 17/02/16. */

public class PantallaJuego extends PantallaAbstracta implements Screen {

    public static final float ANCHO_MAPA = 4000;   // Como se creó en Tiled

    // Referencia al objeto de tipo Game (tiene setScreen para cambiar de pantalla)
    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;

    //fondo
    private Fondo fondo;
    private Fondo fondo2;
    private Texture texturaFondo;
    private Texture texturaFondo2;
    private Texture texturaBtnDer;
    private Texture texturaBtnIzq;
    private Boton btnDer;
    private Boton btnIzq;
    private Texture texturaBtnBrin;
    private Boton btnBrin;
    private Personaje jugador;
    private Texture[] texturaMovDer;
    private Texture[] texturaMovIzq;

    private Personaje Civil2;
    private Texture[] texturasCivil2;
    private int con = 0;

    private OrthographicCamera camaraFija;

    public static final int TAM_CELDA = 5;

    private Boton btnBack;
    private Texture texturaBack;

    private Componente tip;
    private Texture texturaTip;

    private Componente Civil1;
    private Texture texturaCivil1;

    private Boton TextoCivil1;
    private Texture texturaTextoCivil1;


    private int Mov = 0;
    private int ConCiv = 0;
    private int ConAle=0;

    public PantallaJuego(Principal principal) {
        this.principal = principal;
    }
    @Override
    public void show() {
        setYUpgradeCamara();

        cargarTexturas();

        fondo = new Fondo(texturaFondo);
        fondo2 = new Fondo(texturaFondo2);

        jugador = new Personaje(texturaMovDer[0]);
        jugador.setPosicion(-15, -30);
        Civil2 = new Personaje(texturasCivil2[0]);
        Civil2.setPosicion(1900, 30);

        Civil1 = new Componente(texturaCivil1);
        Civil1.setPosicion(3750,30);

        TextoCivil1 = new Boton(texturaTextoCivil1);
        TextoCivil1.setPosicion(3500,350);

        tip = new Componente(texturaTip);
        tip.setPosicion(1650,350);

        crearYPosBotones();
    }

    public void crearYPosBotones() {
        btnDer = new Boton(texturaBtnDer);
        btnDer.setPosicion(160, 12);
        //btnDer.setPosicion(TAM_CELDA,5*TAM_CELDA);
        btnIzq = new Boton(texturaBtnIzq);
        btnIzq.setPosicion(10, 12);
        btnBrin = new Boton(texturaBtnBrin);
        btnBrin.setPosicion(1100, 40);
        batch = new SpriteBatch();
        btnBack = new Boton(texturaBack);
        btnBack.setPosicion(1100, 600);
        batch = new SpriteBatch();
    }

    @Override
    public void setYUpgradeCamara() {
        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new StretchViewport(Principal.ANCHO_MUNDO,Principal.ALTO_MUNDO,camara);

        camaraFija = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camaraFija.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camaraFija.update();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        leerEntrada(); // Revisar eventos
        // Mover la cámara para que siga al personaje
        animacionCiviles(Civil2);
        leerEntrada2();


        batch.setProjectionMatrix(camara.combined);

        // DIBUJA
        batch.begin();
        fondo.render(batch);
        Civil2.render(batch);
        tip.render(batch);
        Civil1.render(batch);
        TextoCivil1.render(batch);
        jugador.render(batch);
        jugador.actualizar();

        fondo2.render(batch);


        batch.end();

        batch.setProjectionMatrix(camaraFija.combined);
        batch.begin();
        btnIzq.render(batch);
        btnDer.render(batch);
        //btnBrin.render(batch);
        btnBack.render(batch);
        batch.end();
    }

    private void leerEntrada2() {
        Vector3 coordenadas = new Vector3();
        coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camara.unproject(coordenadas);  //traduce las coordenadas
        float x = coordenadas.x;
        float y = coordenadas.y;

        if(Gdx.input.justTouched()) {
            if (verificarBoton(x, y, TextoCivil1) && verificarBordes()) {
                this.principal.setScreen(new NivelUno(this.principal));
            }
        }
    }

    private void animacionCiviles(Personaje civil) {
        int tiempos[] = {13,14,15,16};
        Random numero = new Random();

        if(ConAle%tiempos[numero.nextInt(4)]==0){


        float x = civil.getSprite().getX();
        float y = civil.getSprite().getY();

        if(ConCiv % (5) == 0){
            if(civil.equals(Civil2)) {
                civil.setSprite(texturasCivil2[ConCiv % 3]);
            }else{
                civil.setSprite(texturasCivil2[ConCiv % 3]);
            }
        }
        if(ConCiv>500){
            ConCiv = 0;
        }
        ConCiv++;
        civil.setPosicion(x,y);
        }
        ConAle++;

    }

    private void actualizarCamara() {
        float posX = jugador.getSprite().getX();

        // Si está en la parte 'media'
        if (posX>=Principal.ANCHO_MUNDO/2 && posX<=ANCHO_MAPA-Principal.ANCHO_MUNDO/2) {
            // El personaje define el centro de la cámara
            camara.position.set((int) posX, camara.position.y, 0);

            //movimientoDer();

        } else if (posX>ANCHO_MAPA-Principal.ANCHO_MUNDO/2) {    // Si está en la última mitad
            // La cámara se queda media pantalla antes del fin del mundo  :)
            camara.position.set(ANCHO_MAPA - Principal.ANCHO_MUNDO / 2, camara.position.y, 0);

        }
        camara.update();
    }

    @Override
    public void leerEntrada() {

        Vector3 coordenadas = new Vector3();
        coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camaraFija.unproject(coordenadas);  //traduce las coordenadas
        float x = coordenadas.x;
        float y = coordenadas.y;

        if(Gdx.input.isTouched()) {
            if(verificarBoton(x,y,btnDer) && verificarBordes()){
                movimientoDer();
                actualizarCamara();
            }
            if(verificarBoton(x,y,btnIzq) && verificarBordes()){
                movimientoIzq();
                actualizarCamara();
            }
        }
       /* if(Gdx.input.justTouched()) {
            if(verificarBoton(x, y, btnBrin)){
                if(jugador.getEstado() == Personaje.Estado.NORMAL) {
                    jugador.movimientoBrin();
                }
            }
        }*/
        if(Gdx.input.justTouched() &&Gdx.input.isTouched()){
            if(verificarBoton(x,y,btnDer) /*&& verificarBoton(x, y, btnBrin)*/ && verificarBordes()){
                movimientoDer();
                actualizarCamara();
                if(jugador.getEstado() == Personaje.Estado.NORMAL) {
                    //jugador.movimientoBrin();
                }
            }
            if(verificarBoton(x,y,btnIzq)/*&& verificarBoton(x, y, btnBrin) */&& verificarBordes() ){
                movimientoIzq();actualizarCamara();
                if(jugador.getEstado() == Personaje.Estado.NORMAL) {
                    //jugador.movimientoBrin();
                }
            }
            if(verificarBoton(x,y, btnBack)){

                //cambiar a pantalla de jugar
                Sonidos.reproducirBoton();
                principal.setScreen(new PantallaMenu(principal));
            }

        }


    }

    public boolean verificarBordes() {
        //System.out.println(jugador.getSprite().getX());
        if(jugador.getSprite().getX()<-135 ){
            jugador.getSprite().setX(-130);
            return false;
        }else if(jugador.getSprite().getX()>3750 ){
            jugador.getSprite().setX(3745);
            return false;
        }

        return true;

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
    public void cargarTexturas() {
        //texturaFondo = new Texture(Gdx.files.internal("Escenario1Cortado.png"));
        texturaFondo = new Texture(Gdx.files.internal("Fondo-Capa2.png"));

        texturaMovDer= new Texture[3];
        texturaMovDer[0] = new Texture(Gdx.files.internal("SkullCam1der.png"));
        texturaMovDer[1] = new Texture(Gdx.files.internal("SkullCam2der.png"));
        texturaMovDer[2] = new Texture(Gdx.files.internal("SkullCam3der.png"));

        texturaMovIzq = new Texture[3];
        texturaMovIzq[0] = new Texture(Gdx.files.internal("SkullCam1izq.png"));
        texturaMovIzq[1] = new Texture(Gdx.files.internal("SkullCam2izq.png"));
        texturaMovIzq[2] = new Texture(Gdx.files.internal("SkullCam3izq.png"));

        texturasCivil2 = new Texture[3];
        texturasCivil2[0] = new Texture(Gdx.files.internal("Civil2/Civil2-1.png"));
        texturasCivil2[1] = new Texture(Gdx.files.internal("Civil2/Civil2-2.png"));
        texturasCivil2[2] = new Texture(Gdx.files.internal("Civil2/Civil2-3.png"));

        texturaFondo2 = new Texture(Gdx.files.internal("Fondo-Capa1.png"));

        texturaTip = new Texture(Gdx.files.internal("Civil2/Tip.png"));

        texturaCivil1 = new Texture(Gdx.files.internal("CivilFrente1.png"));
        texturaTextoCivil1 = new Texture(Gdx.files.internal("FightText.png"));

        texturaBtnDer = new Texture(Gdx.files.internal("Boton_Derecha.png"));
        texturaBtnIzq = new Texture(Gdx.files.internal("Boton_Izquierda.png"));
        texturaBtnBrin = new Texture(Gdx.files.internal("BotonJump.png"));
        texturaBack = new Texture(Gdx.files.internal("BackGame.png"));

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