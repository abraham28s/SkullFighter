package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
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

    private AssetManager AssManager;

    //fondo
    private Fondo fondo;
    private Fondo fondo2;

    private Boton btnDer;
    private Boton btnIzq;

    private Boton btnBrin, btnEspanta;
    private Componente jugador;


    private Boton skip;

    private Componente Civil1;
    private Componente Civil2;
    private Componente Civil3;
    private Componente Espantapajaros;

    private int con = 0;

    private OrthographicCamera camaraFija;

    public static final int TAM_CELDA = 5;

    private Boton btnBack,botonTexto1, botonTexto2, botonTexto3;

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
    private Texture texturaTexto1, texturaTexto2, texturaTexto3,texturaBtnEspanta;

    private Texture texturaHuesos;
    private Componente cmpHuesos;
    private Texto textoHuesos;
    Preferences pref = Gdx.app.getPreferences("Preferencias");
    private int Mov = 0;
    private int ConCiv = 0;
    private int ConAle=0;
    private int huesos;
    private Texture texturaBtnEspanta1;



    public PantallaJuego(Principal principal, AssetManager assetManager,int huesos) {
        this.principal = principal;
        this.AssManager = assetManager;
        this.huesos = pref.getInteger("huesos");
    }
    @Override
    public void show() {
        if(pref.getInteger("nivel")<2){
            pref.putInteger("nivel",2);
            pref.flush();
        }

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
        setYUpgradeCamara();

        cargarTexturas();

        textoHuesos = new Texto();

        fondo = new Fondo(texturaFondo);
        fondo2 = new Fondo(texturaFondo2);

        jugador = new Componente(texturaMovDer[0]);
        jugador.setPosicion(-15, -30);
        Civil2 = new Componente(texturaCivil1);
        Civil2.setPosicion(1900, 30);

        Civil1 = new Componente(texturaEspantapajaro);
        Civil1.setPosicion(3750,30);

        Civil3 = new Componente(texturaCivil1);
        Civil3.setPosicion(800,30);

        Espantapajaros = new Componente(texturaCivil1);
        Espantapajaros.setPosicion(2720,30);

        cmpHuesos = new Componente(texturaHuesos);
        cmpHuesos.setPosicion(100,580);

        TextoCivil1 = new Boton(texturaBtnEspanta);
        TextoCivil1.setPosicion(3500, 350);

        tip = new Componente(texturaTip);
        tip.setPosicion(1650,350);

        skip = new Boton(texturaSkip);
        skip.setPosicion(1110, 12);

        crearYPosBotones();
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }

    public void crearYPosBotones() {
        btnDer = new Boton(texturaBtnDer);
        btnDer.setPosicion(160, 12);
        //btnDer.setPosicion(TAM_CELDA,5*TAM_CELDA);
        btnIzq = new Boton(texturaBtnIzq);
        btnIzq.setPosicion(10, 12);
        btnBrin = new Boton(texturaBtnBrin);
        btnBrin.setPosicion(1100, 40);

        btnBack = new Boton(texturaBack);
        btnBack.setPosicion(1110, 580);
        botonTexto1 = new Boton(texturaTexto1);
        botonTexto1.setPosicion(900, 350);
        botonTexto2 = new Boton(texturaTexto2);
        botonTexto2.setPosicion(1950,350);
        botonTexto3 = new Boton(texturaTexto3);
        botonTexto3.setPosicion(2800, 350);
        batch = new SpriteBatch();
    }

    @Override
    public void setYUpgradeCamara() {
        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new FitViewport(Principal.ANCHO_MUNDO,Principal.ALTO_MUNDO,camara);

        camaraFija = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camaraFija.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camaraFija.update();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (pref.getInteger("a") == 1){
        if(huesos>=15) {
            Vector2 coor = new Vector2(TextoCivil1.getSprite().getX(), TextoCivil1.getSprite().getY());
            TextoCivil1.setTextura(texturaBtnEspanta1);
            TextoCivil1.setPosicion(coor.x, coor.y);
        }
        }

        if (pref.getInteger("a") == 2){
            Vector2 coor = new Vector2(TextoCivil1.getSprite().getX(), TextoCivil1.getSprite().getY());
            TextoCivil1.setTextura(texturaBtnEspanta1);
            TextoCivil1.setPosicion(coor.x, coor.y);
        }



        leerEntrada(); // Revisar eventos
        // Mover la cámara para que siga al personaje

        leerEntrada2();


        batch.setProjectionMatrix(camara.combined);

        // DIBUJA

        batch.begin();
        fondo.render(batch);
        Civil2.render(batch);
        botonTexto2.render(batch);
        botonTexto1.render(batch);
        botonTexto3.render(batch);
        Civil1.render(batch);
        TextoCivil1.render(batch);
        Civil3.render(batch);
        Espantapajaros.render(batch);
        jugador.render(batch);


        fondo2.render(batch);


        batch.end();

        batch.setProjectionMatrix(camaraFija.combined);
        batch.begin();
        btnIzq.render(batch);
        btnDer.render(batch);
        cmpHuesos.render(batch);
        textoHuesos.mostrarMensaje(batch,""+huesos,120,580);
        //skip.render(batch);
        //btnBrin.render(batch);
        btnBack.render(batch);
        batch.end();
    }

    private void leerEntrada2() {
        Vector3 coordenadas = new Vector3();
        coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camara.unproject(coordenadas,vista.getScreenX(),vista.getScreenY(),vista.getScreenWidth(),vista.getScreenHeight());  //traduce las coordenadas
        float x = coordenadas.x;
        float y = coordenadas.y;

        if(Gdx.input.justTouched()) {

            if (pref.getInteger("a") == 2){
                if (verificarBoton(x, y, TextoCivil1) && verificarBordes()) {
                    this.principal.setScreen(new PantallaCargando(this.principal, 2, huesos, 1));
                    this.dispose();
                }
            }
            if (pref.getInteger("a") == 1) {
                if (verificarBoton(x, y, TextoCivil1) && verificarBordes() && huesos >= 15) {



                }
                if (huesos >= 15) {
                    if (verificarBoton(x, y, TextoCivil1)) {

                        huesos -= 15;
                        pref.putInteger("huesos", huesos);
                        principal.setScreen(new PantallaCargando(this.principal, 2, huesos, 1));
                        pref.putInteger("a",2);
                        pref.flush();

                        this.dispose();
                    }
                }
            }
            if(verificarBoton(x,y,botonTexto1)){
                principal.setScreen(new PantallaCargando(this.principal, 1, huesos, 0, 2, 1, 0, 3));
                this.dispose();
            }
            if(verificarBoton(x,y,botonTexto2)){
                principal.setScreen(new PantallaCargando(this.principal,1,huesos,0,3,2,0,4));
                this.dispose();
            }
            if(verificarBoton(x, y, botonTexto3)){
                principal.setScreen(new PantallaCargando(this.principal, 1, huesos, 0, 5, 3, 0, 6));
                this.dispose();
            }
        }


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
        camaraFija.unproject(coordenadas,vista.getScreenX(),vista.getScreenY(),vista.getScreenWidth(),vista.getScreenHeight());  //traduce las coordenadas
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

        if(Gdx.input.justTouched() &&Gdx.input.isTouched()){
            if(verificarBoton(x,y,btnDer) /*&& verificarBoton(x, y, btnBrin)*/ && verificarBordes()){
                movimientoDer();
                actualizarCamara();

            }

            if(verificarBoton(x,y,btnIzq)/*&& verificarBoton(x, y, btnBrin) */&& verificarBordes() ){
                movimientoIzq();actualizarCamara();

            }
            if(verificarBoton(x,y, btnBack)){
                Preferences pref = Gdx.app.getPreferences("Preferencias");
                //cambiar a pantalla de jugar
                if (pref.getBoolean("boton") == true) {
                    Sonidos.reproducirBoton();
                }
                principal.setScreen(new SeleccionarNiveles(principal, huesos));
                this.dispose();

                //Preferencias música

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
        jugador.setPosicion(x - 15, y);
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
        jugador.setPosicion(x+15,y);
    }

    @Override
    public void cargarTexturas() {
        //texturaFondo = new Texture(Gdx.files.internal("Escenario1Cortado.png"));

        texturaFondo = AssManager.get("Fondo-Capa2.png",Texture.class);
        texturaEspantapajaro = AssManager.get("Espantapajaros3.png", Texture.class);

        texturaBtnEspanta = AssManager.get("DialogosEspantaPajaros/TextScarecrow1.png",Texture.class);
        texturaBtnEspanta1 = AssManager.get("DialogosEspantaPajaros/TextScarecrow2.png",Texture.class);

        texturaMovDer= new Texture[3];

        texturaMovIzq = new Texture[3];



        for (int i = 1; i <= 3; i++) {
            texturaMovDer[i-1] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/SkullCam"+i+"der.png",Texture.class);
            texturaMovIzq[i-1] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/SkullCam"+i+"izq.png",Texture.class);

        }
        texturaHuesos = AssManager.get("GoldBone.png",Texture.class);
        texturaTexto1 = AssManager.get("Dialogos/TextCivil3.png", Texture.class);
        texturaTexto2 = AssManager.get("Dialogos/TextCivil2.png", Texture.class);
        texturaTexto3 = AssManager.get("Dialogos/TextCivil1.png", Texture.class);

        texturaFondo2 = AssManager.get("Fondo-Capa1.png",Texture.class);
        texturaTip = AssManager.get("Tip.png", Texture.class);

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
        AssManager.dispose();
        texturaFondo.dispose();
        texturaEspantapajaro.dispose();

        texturaBtnEspanta.dispose();
        texturaBtnEspanta1.dispose();

        texturaHuesos.dispose();
        texturaTexto1.dispose();
        texturaTexto2.dispose();
        texturaTexto3.dispose();

        texturaFondo2.dispose();
        texturaTip.dispose();

        texturaCivil1.dispose();
        texturaTextoCivil1.dispose();

        texturaBtnDer.dispose();
        texturaBtnIzq.dispose();
        texturaBtnBrin.dispose();
        texturaBack.dispose();
        texturaSkip.dispose();

    }
}