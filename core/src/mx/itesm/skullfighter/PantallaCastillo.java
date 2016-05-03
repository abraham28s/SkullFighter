package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/** * Created by abrahamsoto on 17/02/16. */

public class PantallaCastillo extends PantallaAbstracta implements Screen {

    public static final float ANCHO_MAPA = 4000;   // Como se creó en Tiled

    // Referencia al objeto de tipo Game (tiene setScreen para cambiar de pantalla)
    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;

    private AssetManager AssManager;

    //fondo
    private Fondo fondo;
    private Fondo fondo2;

    private Boton btnDer;
    private Boton btnIzq;

    private Boton btnBrin;
    private Componente jugador;

    private Boton skip;

    private Componente Civil1;
    private Componente Civil2;
    private Componente Civil3;
    private Componente Espantapajaros;

    private int con = 0;

    private OrthographicCamera camaraFija;

    public static final int TAM_CELDA = 5;

    private Boton btnBack;

    private Componente tip;

    private Boton TextoCivil1;

    private Texture texturaBtnBrin;
    private Texture texturaTip;
    private Texture[] texturaMovDer;
    private Texture[] texturaMovIzq;
    private Texture texturaSkip;
    private Texture[] texturasCivil2;
    private Texture texturaBack;
    private Texture texturaTextoCivil1;
    private Texture texturaCivil1;
    private Texture texturaFondo;
    private Texture texturaFondo2;
    private Texture texturaBtnDer;
    private Texture texturaBtnIzq;
    private Texture texturaCivil3;
    private Texture texturaEspantapajaro;

    private int Mov = 0;
    private int ConCiv = 0;
    private int ConAle=0;

    public PantallaCastillo(Principal principal, AssetManager assetManager) {
        this.principal = principal;
        this.AssManager = assetManager;
    }
    @Override
    public void show() {

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
        setYUpgradeCamara();

        cargarTexturas();

        fondo = new Fondo(texturaFondo);
        fondo2 = new Fondo(texturaFondo2);

        jugador = new Componente(texturaMovDer[0]);
        jugador.setPosicion(-15, -30);
        Civil2 = new Componente(texturasCivil2[0]);
        Civil2.setPosicion(1900, 30);

        Civil1 = new Componente(texturaEspantapajaro);
        Civil1.setPosicion(3750,30);

        Civil3 = new Componente(texturaCivil3);
        Civil3.setPosicion(590,30);

        Espantapajaros = new Componente(texturaCivil1);
        Espantapajaros.setPosicion(2620,30);

        TextoCivil1 = new Boton(texturaTextoCivil1);
        TextoCivil1.setPosicion(3500,350);

        tip = new Componente(texturaTip);
        tip.setPosicion(1650,350);

        skip = new Boton(texturaSkip);
        skip.setPosicion(1110,12);

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
        btnBack.setPosicion(1110, 580);
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

        Civil3.render(batch);
        Espantapajaros.render(batch);
        jugador.render(batch);


        fondo2.render(batch);
        TextoCivil1.render(batch);

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
            Preferences pref = Gdx.app.getPreferences("Preferencias");
            if (verificarBoton(x, y, TextoCivil1) && verificarBordes()) {
                pref.putInteger("nivel",2);
                pref.flush();

            }
        }

    }

    private void animacionCiviles(Componente civil) {
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
                if(jugador.getEstado() == Personaje.EstadoBrinco.NORMAL) {
                    jugador.movimientoBrin();
                }
            }
        }*/
        if(Gdx.input.justTouched() &&Gdx.input.isTouched()){
            if(verificarBoton(x,y,btnDer) /*&& verificarBoton(x, y, btnBrin)*/ && verificarBordes()){
                movimientoDer();
                actualizarCamara();

            }
            if(verificarBoton(x,y,btnIzq)/*&& verificarBoton(x, y, btnBrin) */&& verificarBordes() ){
                movimientoIzq();actualizarCamara();

            }
            if(verificarBoton(x,y, btnBack)){

                //cambiar a pantalla de jugar

                Sonidos.reproducirBoton();
                principal.setScreen(new PantallaMenu(principal));

                //Preferencias música
                Preferences pref = Gdx.app.getPreferences("Preferencias");
                pref.getBoolean("musica", true);
                pref.flush();
                if (pref.getBoolean("musica")) {
                    Sonidos.reproducirMusicaFondo();
                }

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

        texturaFondo = AssManager.get("Castillo2.png",Texture.class);
        texturaEspantapajaro = AssManager.get("Espantapajaros3.png", Texture.class);
        texturaCivil3 = AssManager.get("CivilMalo3.png", Texture.class);

        texturaMovDer= new Texture[3];

        texturaMovIzq = new Texture[3];

        texturasCivil2 = new Texture[3];

        for (int i = 1; i <= 3; i++) {
            texturaMovDer[i-1] = AssManager.get("SkullCam"+i+"der.png",Texture.class);
            texturaMovIzq[i-1] = AssManager.get("SkullCam"+i+"izq.png",Texture.class);
            texturasCivil2[i-1] = AssManager.get("Civil2/Civil2-" + i + ".png", Texture.class);
        }

        texturaFondo2 = AssManager.get("Castillo1.png",Texture.class);
        texturaTip = AssManager.get("Civil2/Tip.png", Texture.class);

        texturaCivil1 = AssManager.get("CivilFrente1.png", Texture.class);
        texturaTextoCivil1 = AssManager.get("FightText.png", Texture.class);


        texturaBtnDer = AssManager.get("Boton_Derecha.png", Texture.class);
        texturaBtnIzq = AssManager.get("Boton_Izquierda.png", Texture.class);
        texturaBtnBrin = AssManager.get("BotonJump.png", Texture.class);
        texturaBack = AssManager.get("BackGame.png", Texture.class);
        texturaSkip = AssManager.get("skip.png", Texture.class);

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