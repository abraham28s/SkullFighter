package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
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

import org.lwjgl.Sys;

import java.util.Random;

/**
 * Created by abrahamsoto on 30/03/16.
 */
public class NivelUno extends PantallaAbstracta implements Screen {

    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;

    Fondo fondo;
    Personaje jugador;
    Texture TexturaJugador;

    Personaje enemigo;
    Texture TexturaEnemigo;

    private Boton btnDer;
    private Texture texturaBtnDer;
    private Boton btnIzq;
    private Texture texturaBtnIzq;
    private Boton btnBrin;
    private Texture texturaBtnBrin;

    private Boton btnWeapon;
    private Texture texturaBtnWeapon;

    private Boton btnPunch;
    private Texture texturaBtnPunch;

    private Boton btnPausa;
    private Texture texturaPausa;
    private Texture texturaFondo;
    private Texture[] texturaMovDer;
    private Texture[] texturaMovIzq;
    private Texture[] texturaOzDer;
    private Texture[] texturaOzIzq;
    private int conJ = 0;
    int MovJ = 0;
    private int conE = 0;
    int MovE = 0;

    private int conWeJ= 0;
    private int conWeE= 0;
    private Texture[] texturaPunchDer;
    private Texture[] texturaPunchIzq;
    private int conPuJ = 0;
    private int conPuE = 0;
    private Texture[] texturaEneMovDer;
    private Texture[] texturaEneMovIzq;
    private Texture[] texturaEnePunchDer;
    private Texture[] texturaEnePunchIzq;

    private Componente vidaJ;
    private Texture[] texturaVidaJ;
    private Componente vidaE;
    private Texture[] texturaVidaE;
    private int indexVidaJ = 6;
    // 1= ejecucion, 0=pausa, 3 perdio, 4 gano, 5 cargando
    private int estado = 1;

    //Pausa
    private Componente fondoPausa;
    private Texture texturaFonPausa;
    private Componente MenuPausa;
    private Texture texturaMenuPausa;
    private Boton BtnQuitPausa;
    private Texture texturaQuitPausa;
    private Boton BtnResumePausa;
    private Texture texturaResumePausa;
    private boolean eneAtacoPu = false;
    private boolean juAtacoPu = false;
    private int indexVidaE = 6;
    private boolean juAtacoWe = false;
    private Boton BtnRestartGame;
    private Texture TexturaBtnRestartGame;
    private Componente win;
    private Texture texturaWin;
    private Componente lose;
    private Texture texturaLose;
    int  movPointerDer,movPointerIzq, brincoPointer;
    private String banderaMoviendo;


    public NivelUno(Principal principal) {
        this.principal = principal;
    }


    @Override
    public void show() {
        setYUpgradeCamara();



        cargarTexturas();

        fondo = new Fondo(texturaFondo);

        jugador = new Personaje(texturaMovDer,texturaMovIzq,texturaOzDer,texturaOzIzq,texturaPunchDer,texturaPunchIzq,"jugador",this);
        jugador.setPosicion(-15, -30);

        enemigo = new Personaje(texturaEneMovDer,texturaEneMovIzq,texturaOzDer,texturaOzIzq,texturaEnePunchDer,texturaEnePunchIzq,"enemigo",this);
        enemigo.setPosicion(1050, 30);

        vidaJ = new Componente(texturaVidaJ[6]);
        vidaJ.setPosicion(20,550);

        vidaE = new Componente(texturaVidaE[6]);
        vidaE.setPosicion(700, 550);

        fondoPausa = new Componente(texturaFonPausa);

        MenuPausa = new Componente(texturaMenuPausa);
        MenuPausa.setPosicion(417,120);

        win = new Componente(texturaWin);
        win.setPosicion(210,450);

        lose = new Componente(texturaLose);
        lose.setPosicion(250,450);

        BtnRestartGame = new Boton(TexturaBtnRestartGame);
        BtnRestartGame.setPosicion(565,310);

        crearYPosBotones();
    }

    void leerEntrada() {



            Gdx.input.setInputProcessor(new InputAdapter() {
                public boolean touchUp(int x, int y, int pointer, int button) {
                    Vector3 coordenadas = new Vector3();
                    coordenadas.set(x, y, 0);
                    camara.unproject(coordenadas);  //traduce las coordenadas
                    float x1 = coordenadas.x;
                    float y1 = coordenadas.y;


                    if (verificarBoton(x1, y1, btnIzq) && movPointerIzq == pointer) {
                        jugador.setEstadoMov(Personaje.EstadoMov.QUIETO);

                    } else if (verificarBoton(x1, y1, btnDer) && movPointerDer == pointer) {
                        jugador.setEstadoMov(Personaje.EstadoMov.QUIETO);
                    }
                    if (verificarBoton(x, y, btnPunch)) {
                        //Sonidos.golpearSound();
                        jugador.setAtacandoPu(true);
                        juAtacoPu = true;
                    }
                    if (verificarBoton(x, y, btnWeapon)) {
                        //Sonidos.cuchilloSound();
                        jugador.setEstadoAca(Personaje.EstadoAtacando.NORMAL);
                    }

                    return true;
                }

                @Override
                public boolean touchDragged(int x, int y, int pointer) {
                    // if(leftPointer == pointer){


                    if (verificarBoton(x, y, btnIzq) ) {
                        jugador.movimiento("izq");
                    } else if (verificarBoton(x, y, btnDer)
                            ) {
                        jugador.movimiento("der");
                    }

                    // }
                    return true;
                }

                public boolean touchDown(int x, int y, int pointer, int button) {

                    Vector3 coordenadas = new Vector3();
                    coordenadas.set(x,y, 0);
                    camara.unproject(coordenadas);  //traduce las coordenadas
                    float x1 = coordenadas.x;
                    float y1 = coordenadas.y;

                    if(estado == 1){


                        if (verificarBoton(x1, y1, btnIzq) && pointer == 0) {
                            jugador.movimiento("izq");

                            movPointerIzq = pointer;
                            banderaMoviendo = "izq";

                        } else if (verificarBoton(x1, y1, btnDer) && pointer == 0
                                )  {
                            jugador.movimiento("der");
                            movPointerDer = pointer;
                            banderaMoviendo = "der";

                        }
                        if (verificarBoton(x1, y1, btnBrin) && pointer != 0 && movPointerDer == 0 && banderaMoviendo.equals("der") ) {
                            //Sonidos.saltarSound();
                            if (jugador.getEstado() == Personaje.EstadoBrinco.NORMAL) {
                                jugador.movimientoBrin();
                                jugador.movimiento("der");

                            }
                            brincoPointer = pointer;
                        }
                        if (verificarBoton(x1, y1, btnBrin) && pointer != 0 && movPointerIzq == 0 && banderaMoviendo.equals("izq") ) {
                            //Sonidos.saltarSound();
                            if (jugador.getEstado() == Personaje.EstadoBrinco.NORMAL) {
                                jugador.movimientoBrin();
                                jugador.movimiento("izq");

                            }
                            brincoPointer = pointer;
                        } else if (verificarBoton(x1, y1, btnBrin) && pointer == 0) {
                            if (jugador.getEstado() == Personaje.EstadoBrinco.NORMAL) {
                                jugador.movimientoBrin();

                            }
                            brincoPointer = pointer;
                        }
                        if (verificarBoton(x1, y1, btnPunch)) {
                            //Sonidos.golpearSound();
                            jugador.setAtacandoPu(true);
                            juAtacoPu = true;
                        }
                        if (verificarBoton(x1, y1, btnWeapon)) {
                            //Sonidos.cuchilloSound();
                            jugador.ataqueArma();
                        }

                        if(verificarBoton(x1,y1, btnPausa)){
                            //cambiar a pantalla de jugar
                            //Sonidos.reproducirBoton();
                            pausarJuego();
                        }
                    }else if(estado == 0){
                        if(verificarBoton(x1, y1, BtnResumePausa)){
                            Sonidos.reproducirBoton();
                            resumirJuego();
                        }
                        if(verificarBoton(x1,y1,BtnQuitPausa)){
                            Sonidos.reproducirBoton();
                            estado = 100;
                            principal.setScreen(new PantallaMenu(principal));

                            //Preferencias música
                            Preferences pref = Gdx.app.getPreferences("Preferencias");
                            pref.getBoolean("musica", true);
                            pref.flush();
                            if (pref.getBoolean("musica")) {
                                Sonidos.reproducirMusicaFondo();
                            }
                        }
                    }else if(estado == 3 || estado == 4 ){
                        if(verificarBoton(x1, y1, BtnRestartGame)){
                            Sonidos.reproducirBoton();
                            estado = 100;
                            principal.setScreen(new PantallaCargando(principal,0));
                        }
                        if(verificarBoton(x1,y1,BtnQuitPausa)){
                            Sonidos.reproducirBoton();
                            estado = 100;
                            principal.setScreen(new PantallaMenu(principal));
                        }
                    }
                    return true;
                }
            });

    }

    private void pausarJuego() {
        this.estado = 0;
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

        texturaOzDer = new Texture[5];
        texturaOzDer[0] = new Texture(Gdx.files.internal("Oz1Der.png"));
        texturaOzDer[1] = new Texture(Gdx.files.internal("Oz2Der.png"));
        texturaOzDer[2] = new Texture(Gdx.files.internal("Oz3Der.png"));
        texturaOzDer[3] = new Texture(Gdx.files.internal("Oz4Der.png"));
        texturaOzDer[4] = new Texture(Gdx.files.internal("SkullCam1der.png"));

        texturaOzIzq = new Texture[5];
        texturaOzIzq[0] = new Texture(Gdx.files.internal("Oz1Izq.png"));
        texturaOzIzq[1] = new Texture(Gdx.files.internal("Oz2Izq.png"));
        texturaOzIzq[2] = new Texture(Gdx.files.internal("Oz3Izq.png"));
        texturaOzIzq[3] = new Texture(Gdx.files.internal("Oz4Izq.png"));
        texturaOzIzq[4] = new Texture(Gdx.files.internal("SkullCam1izq.png"));

        texturaPunchDer = new Texture[4];
        texturaPunchDer[0] = new Texture(Gdx.files.internal("SkullPunchDer1.png"));
        texturaPunchDer[1] = new Texture(Gdx.files.internal("SkullPunchDer2.png"));
        texturaPunchDer[2] = new Texture(Gdx.files.internal("SkullPunchDer3.png"));
        texturaPunchDer[3] = new Texture(Gdx.files.internal("SkullCam1der.png"));

        texturaPunchIzq = new Texture[4];
        texturaPunchIzq[0] = new Texture(Gdx.files.internal("SkullPunchIzq1.png"));
        texturaPunchIzq[1] = new Texture(Gdx.files.internal("SkullPunchIzq2.png"));
        texturaPunchIzq[2] = new Texture(Gdx.files.internal("SkullPunchIzq3.png"));
        texturaPunchIzq[3] = new Texture(Gdx.files.internal("SkullCam1izq.png"));

        texturaEneMovDer = new Texture[3];
        texturaEneMovDer[0] =new Texture(Gdx.files.internal("Enemigo1Der.png"));
        texturaEneMovDer[1]=new Texture(Gdx.files.internal("Enemigo2Der.png"));
        texturaEneMovDer[2]=new Texture(Gdx.files.internal("Enemigo3Der.png"));

        texturaEneMovIzq = new Texture[3];
        texturaEneMovIzq[0] =new Texture(Gdx.files.internal("Enemigo1Izq.png"));
        texturaEneMovIzq[1]=new Texture(Gdx.files.internal("Enemigo2Izq.png"));
        texturaEneMovIzq[2]=new Texture(Gdx.files.internal("Enemigo3Izq.png"));

        texturaEnePunchDer = new Texture[3];
        texturaEnePunchDer[0] = new Texture(Gdx.files.internal("EnemigoPun1Der.png"));
        texturaEnePunchDer[1] = new Texture(Gdx.files.internal("EnemigoPun2Der.png"));
        texturaEnePunchDer[2] = new Texture(Gdx.files.internal("Enemigo1Der.png"));

        texturaEnePunchIzq= new Texture[3];
        texturaEnePunchIzq[0] = new Texture(Gdx.files.internal("EnemigoPun1Izq.png"));
        texturaEnePunchIzq[1] = new Texture(Gdx.files.internal("EnemigoPun2Izq.png"));
        texturaEnePunchIzq[2] = new Texture(Gdx.files.internal("Enemigo1Izq.png"));

        texturaWin = new Texture(Gdx.files.internal("YouWin.png"));
        texturaLose = new Texture(Gdx.files.internal("YouLose.png"));
        TexturaBtnRestartGame = new Texture(Gdx.files.internal("Restart.png"));

        texturaVidaJ = new Texture[7];
        texturaVidaE = new Texture[7];
        for (int i = 0; i<7;i++){
            texturaVidaJ[i] = new Texture(Gdx.files.internal("VidaSkull"+ i+".png"));
            texturaVidaE[i] = new Texture(Gdx.files.internal("VidaSkullE"+ i+".png"));
        }

        texturaMenuPausa = new Texture(Gdx.files.internal("Pausemenu.png"));
        texturaQuitPausa = new Texture(Gdx.files.internal("Quit.png"));
        texturaResumePausa = new Texture(Gdx.files.internal("Resume.png"));
        texturaFonPausa =new Texture(Gdx.files.internal("negro.png"));

        texturaBtnDer = new Texture(Gdx.files.internal("Boton_Derecha.png"));
        texturaBtnIzq = new Texture(Gdx.files.internal("Boton_Izquierda.png"));
        texturaBtnBrin = new Texture(Gdx.files.internal("BotonJump.png"));
        texturaPausa = new Texture(Gdx.files.internal("PauseBotton.png"));
        texturaBtnPunch = new Texture(Gdx.files.internal("BotonPunch.png"));
        texturaBtnWeapon = new Texture(Gdx.files.internal("BotonWeapon.png"));
    }

    @Override
    void crearYPosBotones() {
        btnDer = new Boton(texturaBtnDer);
        btnDer.setPosicion(160, 12);
        //btnDer.setPosicion(TAM_CELDA,5*TAM_CELDA);
        btnIzq = new Boton(texturaBtnIzq);
        btnIzq.setPosicion(10, 12);

        btnBrin = new Boton(texturaBtnBrin);
        btnBrin.setPosicion(1100, 12);
        batch = new SpriteBatch();
        btnPausa = new Boton(texturaPausa);
        btnPausa.setPosicion(570, 580);
        btnPunch = new Boton(texturaBtnPunch);
        btnPunch.setPosicion(950, 12);
        btnWeapon = new Boton(texturaBtnWeapon);
        btnWeapon.setPosicion(800, 12);

        BtnResumePausa = new Boton(texturaResumePausa);
        BtnResumePausa.setPosicion(555,430);

        BtnQuitPausa = new Boton(texturaQuitPausa);
        BtnQuitPausa.setPosicion(585,270);

        batch = new SpriteBatch();
    }

    @Override
    void setYUpgradeCamara() {
        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new StretchViewport(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO, camara);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        // DIBUJA
        batch.begin();
        fondo.render(batch);
        jugador.render(batch);
        jugador.actualizar();
       // System.out.println(jugador.getEstadoMov());
        enemigo.render(batch);
        vidaJ.render(batch);
        vidaE.render(batch);

        btnIzq.render(batch);
        btnDer.render(batch);
        btnBrin.render(batch);
        btnPausa.render(batch);
        btnWeapon.render(batch);
        btnPunch.render(batch);
        leerEntrada();
        if(this.estado == 1) {
             // Revisar eventos
            // Mover la cámara para que siga al personaje

            movimientoEnemigo();




        }else if(this.estado ==0){

            fondoPausa.render(batch);
            MenuPausa.render(batch);
            BtnQuitPausa.render(batch);
            BtnResumePausa.render(batch);

        }else if(this.estado == 3){

            fondoPausa.render(batch);
            lose.render(batch);
            BtnRestartGame.render(batch);
            BtnQuitPausa.render(batch);

        }else if(this.estado == 4){

            fondoPausa.render(batch);
            win.render(batch);
            BtnRestartGame.render(batch);
            BtnQuitPausa.render(batch);
        }
        batch.end();
    }


    public boolean verificarBordes(Personaje player){

        if(player.getSprite().getX()-(player.getSprite().getWidth()/2) >= -280 && player.getSprite().getX()+player.getSprite().getWidth()/2 +10 <= 1220){
            return true;
        }

        return false;
    }

    private void perdioJuego() {
        this.estado = 3;
    }

    private void movimientoEnemigo() {
        Random numero = new Random();
        if(jugador.getSprite().getX()+70>enemigo.getSprite().getX()+100 ){

            if(numero.nextInt(15)<3) {
                enemigo.movimiento("der");
            }
        }else if(jugador.getSprite().getX()+100<enemigo.getSprite().getX()-100){
            if(numero.nextInt(15)<3) {
                enemigo.movimiento("izq");
            }
        }
    }





    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        pausarJuego();
    }

    @Override
    public void resume() {
        resumirJuego();
    }

    private void resumirJuego() {
        this.estado = 1;
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {

    }
}
