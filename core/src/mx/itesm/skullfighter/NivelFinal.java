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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import javax.xml.soap.Text;

/**
 * Created by abrahamsoto on 30/03/16.
 */
public class NivelFinal extends PantallaAbstracta implements Screen {

    private final Principal principal;

    private AssetManager AssManager;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;

    Fondo fondo;
    PersonajeFinal jugador;

    public int nivelLargo;


    PersonajeFinal enemigo;

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

    private Texture[] texturaPunchDer;
    private Texture[] texturaPunchIzq;

    private Texture[] texturaEneMovDer;
    private Texture[] texturaEneMovIzq;
    private Texture[] texturaEnePunchDer;
    private Texture[] texturaEnePunchIzq;

    public Componente vidaJ;
    private Texture[] texturaVidaJ;
    public Componente vidaE;
    private Texture[] texturaVidaE;
    private int indexVidaJ = 6;
    // 1= ejecucion, 0=pausa, 3 perdio, 4 gano, 5 cargando
    private int estado = 1;

    //Pausa
    private Componente fondoPausa,cmpHuesosGanar;
    private Texture texturaFonPausa,texturaHuesosGanar;
    private Componente MenuPausa;
    private Texture texturaMenuPausa;
    private Boton BtnQuitPausa,BtnBackEnd;
    private Texture texturaQuitPausa,texturaBackEnd;
    private Boton BtnResumePausa;
    private Texture texturaResumePausa;

    private int indexVidaE = 6;

    private Boton BtnRestartGame,BtnBackLevel;
    private Texture TexturaBtnRestartGame,TexturaBackLevel;

    private Componente win;
    private Texture texturaWin;
    private Componente lose;
    private Texture texturaLose;
    int  movPointerDer,movPointerIzq, brincoPointer;
    private String banderaMoviendo;
    private boolean banderaIzquierdaApre;
    private boolean banderaDerechaApre;
    private int nivel;
    private int huesos;
    Preferences pref = Gdx.app.getPreferences("Preferencias");
    private int huesosGanar;
    private int ese;
    private int pantallaOri;
    private int dificultad;
    private Texture[] texturaEneWeaponDer;
    private Texture[] texturaEneWeaponIzq;
    private Componente cmpGloboParton;
    private Texture[] texGloboParton;
    private int variableTexto = 0;
    private int contador = 0;


    public NivelFinal(Principal principal, AssetManager ass, int nivel, int huesos, int huesosGanar, int ese, int pantallaOri, int dificultad) {
        this.principal = principal;
        this.nivel = nivel;
        this.AssManager = ass;
        this.huesos = pref.getInteger("huesos");
        this.huesosGanar = huesosGanar;
        this.ese = ese;
        this.pantallaOri = pantallaOri;
        this.dificultad = dificultad;
    }



    @Override
    public void show() {
        if(pref.getInteger("nivel")<4){
            pref.putInteger("nivel", 4);
            pref.flush();
        }
        setYUpgradeCamara();



        cargarTexturas();

        fondo = new Fondo(texturaFondo);

        jugador = new PersonajeFinal(texturaMovDer, texturaMovIzq, texturaOzDer, texturaOzIzq, texturaPunchDer, texturaPunchIzq, "jugador", this);
        jugador.setPosicion(-15, -30);

        enemigo = new PersonajeFinal(texturaEneMovDer,texturaEneMovIzq,texturaEneWeaponDer,texturaEneWeaponIzq,texturaEnePunchDer,texturaEnePunchIzq,"enemigo",this);
        enemigo.setPosicion(1050, 30);

        vidaJ = new Componente(texturaVidaJ[6]);
        vidaJ.setPosicion(20,550);

        vidaE = new Componente(texturaVidaE[6]);
        vidaE.setPosicion(700, 550);

        cmpGloboParton = new Componente(texGloboParton[0]);
        cmpGloboParton.setPosicion(900,400);

        fondoPausa = new Componente(texturaFonPausa);

        MenuPausa = new Componente(texturaMenuPausa);
        MenuPausa.setPosicion(417,120);

        win = new Componente(texturaWin);
        win.setPosicion(100,180);

        cmpHuesosGanar = new Componente(texturaHuesosGanar);
        cmpHuesosGanar.setPosicion(810,300);


        lose = new Componente(texturaLose);
        lose.setPosicion(250,450);

        BtnRestartGame = new Boton(TexturaBtnRestartGame);
        BtnRestartGame.setPosicion(480,290);

        BtnBackEnd = new Boton(texturaBackEnd);
        BtnBackEnd.setPosicion(650,290);



        crearYPosBotones();
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }

    void leerEntrada() {



            Gdx.input.setInputProcessor(new InputAdapter() {


                public boolean touchUp(int x, int y, int pointer, int button) {
                    Vector3 coordenadas = new Vector3();
                    coordenadas.set(x, y, 0);
                    camara.unproject(coordenadas,vista.getScreenX(),vista.getScreenY(),vista.getScreenWidth(),vista.getScreenHeight());  //traduce las coordenadas
                    float x1 = coordenadas.x;
                    float y1 = coordenadas.y;
                    if(estado == 1 ){

                    if (verificarBoton(x1, y1, btnIzq) && movPointerIzq == pointer) {
                        jugador.setEstadoMov(PersonajeFinal.EstadoMov.QUIETO);
                        banderaIzquierdaApre = false;

                    } else if (verificarBoton(x1, y1, btnDer) && movPointerDer == pointer) {
                        jugador.setEstadoMov(PersonajeFinal.EstadoMov.QUIETO);
                        banderaDerechaApre = false;
                    }
                    if (verificarBoton(x, y, btnPunch)) {
                        //Sonidos.golpearSound();
                        jugador.setEstadoAca(PersonajeFinal.EstadoAtacando.NORMAL);

                    }
                    if (verificarBoton(x, y, btnWeapon)) {
                        //Sonidos.cuchilloSound();
                        jugador.setEstadoAca(PersonajeFinal.EstadoAtacando.NORMAL);
                    }}else if(estado == 3 || estado == 4 ){
                        if(verificarBoton(x1,y1,BtnQuitPausa)){
                            if (pref.getBoolean("boton") == true ) {
                                Sonidos.reproducirBoton();
                            }
                            estado = 100;
                            dispose();
                            principal.setScreen(new PantallaMenu(principal,huesos));
                        }
                        if(verificarBoton(x1,y1,BtnBackEnd)){
                            if (pref.getBoolean("boton") == true ) {
                                Sonidos.reproducirBoton();
                            }
                            estado = 100;
                            dispose();
                            principal.setScreen(new PantallaCargando(principal,2,huesos,2));
                        }
                    }else if( estado == 0){
                        if(verificarBoton(x1,y1,BtnBackEnd)){
                            if (pref.getBoolean("boton") == true ) {
                                Sonidos.reproducirBoton();
                            }
                            estado = 100;
                            dispose();
                            principal.setScreen(new PantallaCargando(principal,2,huesos,2));
                        }
                        if(verificarBoton(x1,y1,BtnQuitPausa)){
                            if (pref.getBoolean("boton") == true ) {
                                Sonidos.reproducirBoton();
                            }
                            estado = 100;
                            principal.setScreen(new PantallaMenu(principal,huesos));

                            //Preferencias música
                            Preferences pref = Gdx.app.getPreferences("Preferencias");
                            pref.flush();
                            pref.getBoolean("musica", true);
                            pref.flush();
                            if (pref.getBoolean("musica")) {
                                Sonidos.reproducirMusicaFondo();
                            }
                        }
                    }

                    return true;
                }

                @Override
                public boolean touchDragged(int x, int y, int pointer) {
                    // if(leftPointer == pointer){
                    Vector3 coordenadas = new Vector3();
                    coordenadas.set(x, y, 0);
                    camara.unproject(coordenadas,vista.getScreenX(),vista.getScreenY(),vista.getScreenWidth(),vista.getScreenHeight());  //traduce las coordenadas
                    float x1 = coordenadas.x;
                    float y1 = coordenadas.y;
                    if(estado==1) {
                        if (!verificarBoton(x1, y1, btnIzq) && !verificarBoton(x1, y1, btnDer)) {
                            jugador.setEstadoMov(PersonajeFinal.EstadoMov.QUIETO);
                        }
                        if (verificarBoton(x1, y1, btnIzq) && !verificarBoton(x1, y1, btnDer)) {
                            jugador.movimiento("izq");

                            movPointerIzq = pointer;
                            banderaMoviendo = "izq";
                        } else if (!verificarBoton(x1, y1, btnIzq) && verificarBoton(x1, y1, btnDer)) {
                            jugador.movimiento("der");
                            movPointerDer = pointer;
                            banderaMoviendo = "der";
                        }
                    }

                    return true;
                }

                public boolean touchDown(int x, int y, int pointer, int button) {

                    Vector3 coordenadas = new Vector3();
                    coordenadas.set(x,y, 0);
                    camara.unproject(coordenadas,vista.getScreenX(),vista.getScreenY(),vista.getScreenWidth(),vista.getScreenHeight());  //traduce las coordenadas
                    float x1 = coordenadas.x;
                    float y1 = coordenadas.y;

                    if(estado == 1){


                        if (verificarBoton(x1, y1, btnIzq) && pointer == 0&&variableTexto ==4 ) {
                            jugador.movimiento("izq");

                            movPointerIzq = pointer;
                            banderaMoviendo = "izq";
                            banderaIzquierdaApre = true;

                        } else if (verificarBoton(x1, y1, btnDer) && pointer == 0&&variableTexto ==4
                                )  {
                            jugador.movimiento("der");
                            movPointerDer = pointer;
                            banderaMoviendo = "der";
                            banderaDerechaApre = true;

                        }
                        if (verificarBoton(x1, y1, btnBrin) && pointer != 0 && movPointerDer == 0 && banderaMoviendo.equals("der")&& variableTexto ==4 ) {
                            //Sonidos.saltarSound();
                            if (jugador.getEstado() == PersonajeFinal.EstadoBrinco.NORMAL) {
                                jugador.movimientoBrin();
                                jugador.movimiento("der");

                            }
                            brincoPointer = pointer;
                        }
                        if (verificarBoton(x1, y1, btnBrin) && pointer != 0 && movPointerIzq == 0 && banderaMoviendo.equals("izq")&&variableTexto ==4 ) {
                            //Sonidos.saltarSound();
                            if (jugador.getEstado() == PersonajeFinal.EstadoBrinco.NORMAL) {
                                jugador.movimientoBrin();
                                jugador.movimiento("izq");

                            }
                            brincoPointer = pointer;
                        } else if (verificarBoton(x1, y1, btnBrin) && pointer == 0 &&variableTexto ==4 ) {
                            if (jugador.getEstado() == PersonajeFinal.EstadoBrinco.NORMAL) {
                                jugador.movimientoBrin();

                            }
                            brincoPointer = pointer;
                        }
                        if (verificarBoton(x1, y1, btnPunch) &&variableTexto ==4 ) {
                            //Sonidos.golpearSound();
                            jugador.ataquePuno();
                        }
                        if (verificarBoton(x1, y1, btnWeapon) &&variableTexto ==4 ) {
                            //Sonidos.cuchilloSound();
                            jugador.ataqueArma();
                        }

                        if(verificarBoton(x1,y1, btnPausa)&&variableTexto ==4 ){
                            //cambiar a pantalla de jugar
                            //Sonidos.reproducirBoton();
                            pausarJuego();
                        }
                    }else if(estado == 0){
                        if(verificarBoton(x1, y1, BtnResumePausa)){
                            if (pref.getBoolean("boton") == true ) {
                                Sonidos.reproducirBoton();
                            }
                            resumirJuego();
                        }

                    }else if(estado == 3 || estado == 4 ){
                        if(verificarBoton(x1, y1, BtnRestartGame)){
                            if (pref.getBoolean("boton") == true ) {
                                Sonidos.reproducirBoton();
                            }
                            estado = 100;
                            principal.setScreen(new NivelFinal(principal,AssManager,nivel,huesos,huesosGanar,ese,pantallaOri,dificultad));
                        }

                    }
                    return true;
                }
            });

    }

    private void pausarJuego() {
        this.estado = 0;
    }

    public void actualizarVida(int cuan,char player){

        if(player == 'j'){
            float x = vidaE.getSprite().getX();
            float y = vidaE.getSprite().getY();
            indexVidaE-=cuan;
            if(indexVidaE<0)indexVidaE=0;
            vidaE.setSprite(texturaVidaE[indexVidaE]);
            vidaE.getSprite().setPosition(x,y);
        }else{
            float x = vidaJ.getSprite().getX();
            float y = vidaJ.getSprite().getY();
            indexVidaJ-=cuan;
            if(indexVidaJ<0)indexVidaJ=0;
            vidaJ.setSprite(texturaVidaJ[indexVidaJ]);
            vidaJ.getSprite().setPosition(x,y);
        }
    }

    @Override
    void cargarTexturas() {

        texturaWin = AssManager.get("Final.png",Texture.class);
        texturaLose = AssManager.get("YouLose.png",Texture.class);
        TexturaBtnRestartGame = AssManager.get("Restart.png",Texture.class);

        texturaMenuPausa = AssManager.get("Pausemenu.png",Texture.class);
        texturaQuitPausa = AssManager.get("Quit.png",Texture.class);
        texturaResumePausa = AssManager.get("Resume.png",Texture.class);
        texturaFonPausa =AssManager.get("negro.png",Texture.class);

        texturaBtnDer = AssManager.get("Boton_Derecha.png",Texture.class);
        texturaBtnIzq = AssManager.get("Boton_Izquierda.png",Texture.class);
        texturaBtnBrin = AssManager.get("BotonJump.png",Texture.class);
        texturaPausa = AssManager.get("PauseBotton.png",Texture.class);
        texturaBtnPunch = AssManager.get("BotonPunch.png",Texture.class);
        texturaBtnWeapon = AssManager.get("BotonWeapon.png",Texture.class);

        texturaFondo = AssManager.get("Final/Escenario_Final.png",Texture.class);

        texturaMovDer= new Texture[3];
        texturaMovDer[0] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/SkullCam1der.png",Texture.class);
        texturaMovDer[1] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/SkullCam2der.png",Texture.class);
        texturaMovDer[2] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/SkullCam3der.png",Texture.class);

        texGloboParton= new Texture[4];
        texGloboParton[0] = AssManager.get("Final/DeathParton0.png",Texture.class);
        texGloboParton[1] = AssManager.get("Final/DeathParton1.png",Texture.class);
        texGloboParton[2] = AssManager.get("Final/DeathParton2.png",Texture.class);
        texGloboParton[3] = AssManager.get("Final/DeathParton3.png",Texture.class);

        texturaMovIzq = new Texture[3];
        texturaMovIzq[0] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/SkullCam1izq.png",Texture.class);
        texturaMovIzq[1] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/SkullCam2izq.png",Texture.class);
        texturaMovIzq[2] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/SkullCam3izq.png",Texture.class);

        texturaOzDer = new Texture[5];
        texturaOzDer[0] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/Oz1Der.png",Texture.class);
        texturaOzDer[1] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/Oz2Der.png",Texture.class);
        texturaOzDer[2] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/Oz3Der.png",Texture.class);
        texturaOzDer[3] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/Oz4Der.png",Texture.class);
        texturaOzDer[4] = texturaMovDer[0];

        texturaOzIzq = new Texture[5];
        texturaOzIzq[0] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/Oz1Izq.png",Texture.class);
        texturaOzIzq[1] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/Oz2Izq.png",Texture.class);
        texturaOzIzq[2] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/Oz3Izq.png",Texture.class);
        texturaOzIzq[3] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/Oz4Izq.png",Texture.class);
        texturaOzIzq[4] = texturaMovIzq[0];

        texturaPunchDer = new Texture[4];
        texturaPunchDer[0] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchDer1.png",Texture.class);
        texturaPunchDer[1] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchDer2.png",Texture.class);
        texturaPunchDer[2] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchDer3.png",Texture.class);
        texturaPunchDer[3] = texturaMovDer[0];


        texturaPunchIzq = new Texture[4];
        texturaPunchIzq[0] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchIzq1.png",Texture.class);
        texturaPunchIzq[1] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchIzq2.png",Texture.class);
        texturaPunchIzq[2] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchIzq3.png",Texture.class);
        texturaPunchIzq[3] = texturaMovIzq[0];

        texturaEneMovDer = new Texture[3];
        texturaEneMovDer[0] =AssManager.get("Final/Camina/Enemigo1Der.png",Texture.class);
        texturaEneMovDer[1]=AssManager.get("Final/Camina/Enemigo2Der.png",Texture.class);
        texturaEneMovDer[2]=AssManager.get("Final/Camina/Enemigo3Der.png",Texture.class);

        texturaEneMovIzq = new Texture[3];
        texturaEneMovIzq[0] =AssManager.get("Final/Camina/Enemigo1Izq.png",Texture.class);
        texturaEneMovIzq[1]=AssManager.get("Final/Camina/Enemigo2Izq.png",Texture.class);
        texturaEneMovIzq[2]=AssManager.get("Final/Camina/Enemigo3Izq.png",Texture.class);

        texturaEnePunchDer = new Texture[4];
        texturaEnePunchDer[0] = AssManager.get("Final/Golpe/EnemigoPun1Der.png",Texture.class);
        texturaEnePunchDer[1] = AssManager.get("Final/Golpe/EnemigoPun2Der.png",Texture.class);
        texturaEnePunchDer[2] = AssManager.get("Final/Golpe/EnemigoPun3Der.png",Texture.class);
        texturaEnePunchDer[3] =texturaEneMovDer[0];

        texturaEnePunchIzq= new Texture[4];
        texturaEnePunchIzq[0] = AssManager.get("Final/Golpe/EnemigoPun1Izq.png",Texture.class);
        texturaEnePunchIzq[1] = AssManager.get("Final/Golpe/EnemigoPun2Izq.png",Texture.class);
        texturaEnePunchIzq[2] = AssManager.get("Final/Golpe/EnemigoPun3Izq.png",Texture.class);
        texturaEnePunchIzq[3]=texturaEneMovIzq[0];

        texturaEneWeaponDer = new Texture[5];
        texturaEneWeaponDer[0] = AssManager.get("Final/Arma/EnemigoArm1Der.png",Texture.class);
        texturaEneWeaponDer[1] = AssManager.get("Final/Arma/EnemigoArm2Der.png",Texture.class);
        texturaEneWeaponDer[2] = AssManager.get("Final/Arma/EnemigoArm3Der.png",Texture.class);
        texturaEneWeaponDer[3] = AssManager.get("Final/Arma/EnemigoArm4Der.png",Texture.class);
        texturaEneWeaponDer[4] = texturaEneMovDer[0];

        texturaEneWeaponIzq = new Texture[5];
        texturaEneWeaponIzq[0] = AssManager.get("Final/Arma/EnemigoArm1Izq.png",Texture.class);
        texturaEneWeaponIzq[1] = AssManager.get("Final/Arma/EnemigoArm2Izq.png",Texture.class);
        texturaEneWeaponIzq[2] = AssManager.get("Final/Arma/EnemigoArm3Izq.png",Texture.class);
        texturaEneWeaponIzq[3] = AssManager.get("Final/Arma/EnemigoArm4Izq.png",Texture.class);
        texturaEneWeaponIzq[4] = texturaEneMovIzq[0];



        texturaBackEnd = AssManager.get("BackGame.png",Texture.class);


        texturaVidaJ = new Texture[7];
        texturaVidaE = new Texture[7];
        for (int i = 0; i<7;i++){
            texturaVidaJ[i] = AssManager.get("Personaje/VidaSkull"+ i+".png",Texture.class);
            texturaVidaE[i] = AssManager.get(nivel +"/VidaSkullE"+ i+".png",Texture.class);
        }

        texturaHuesosGanar = AssManager.get("+" + huesosGanar + ".png",Texture.class);



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
        BtnQuitPausa.setPosicion(585,200);

        batch = new SpriteBatch();
    }

    @Override
    void setYUpgradeCamara() {
        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new FitViewport(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO, camara);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        // DIBUJA
        batch.begin();
        fondo.render(batch);
        jugador.render(batch);

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
            float x = cmpGloboParton.getSprite().getX();
            float y = cmpGloboParton.getSprite().getY();
             // Revisar eventos
            // Mover la cámara para que siga al PersonajeFinal

            jugador.actualizar();
            enemigo.actualizar();

            if(variableTexto == 0) {
                cmpGloboParton.render(batch);
                contador++;
                if(contador>100){
                    variableTexto = 1;
                    contador = 0;
                }
            }else if(variableTexto == 1){
                cmpGloboParton.setTextura(texGloboParton[variableTexto]);
                cmpGloboParton.setPosicion(x,y);
                cmpGloboParton.render(batch);
                contador++;
                if(contador>100){
                    variableTexto = 2;
                    contador = 0;
                }

            }else if(variableTexto == 2){
                cmpGloboParton.setTextura(texGloboParton[variableTexto]);
                cmpGloboParton.setPosicion(x,y);
                cmpGloboParton.render(batch);
                contador++;
                if(contador>100){
                    variableTexto = 3;
                    contador = 0;
                }

            }else if(variableTexto == 3){
                cmpGloboParton.setTextura(texGloboParton[variableTexto]);
                cmpGloboParton.setPosicion(x,y);
                cmpGloboParton.render(batch);
                contador++;
                if(contador>100){
                    variableTexto = 4;
                    contador = 0;
                }

            }else if(variableTexto == 4){
                movimientoEnemigo();
                revisarVida();
            }




        }else if(this.estado ==0){

            fondoPausa.render(batch);
            MenuPausa.render(batch);
            BtnQuitPausa.render(batch);
            BtnBackEnd.setPosicion(565,280);
            BtnResumePausa.render(batch);
            BtnBackEnd.render(batch);

        }else if(this.estado == 3){

            fondoPausa.render(batch);
            lose.render(batch);
            BtnRestartGame.render(batch);
            BtnQuitPausa.render(batch);
            BtnBackEnd.setPosicion(650, 290);
            BtnBackEnd.render(batch);

        }else if(this.estado == 4){

            fondoPausa.render(batch);
            win.render(batch);
            cmpHuesosGanar.render(batch);
            BtnRestartGame.render(batch);
            BtnQuitPausa.render(batch);
            BtnBackEnd.setPosicion(650, 290);
            BtnBackEnd.render(batch);
        }
        batch.end();
    }

    private void revisarVida() {
        if(indexVidaJ==0){
            perdioJuego();
        }else if(indexVidaE == 0){
            ganoJuego();
        }
    }


    public boolean verificarBordes(PersonajeFinal player){

        if(player.getSprite().getX()-(player.getSprite().getWidth()/2) >= -280 && player.getSprite().getX()+player.getSprite().getWidth()/2 +10 <= 1220){
            return true;
        }

        return false;
    }

    private void ganoJuego() {
                this.estado = 4;
                pref.putInteger("huesos",huesos);
                huesos += huesosGanar;
                pref.putInteger("huesos",huesos);
                pref.flush();

    }

    private void perdioJuego() {
        this.estado = 3;
    }

    private void movimientoEnemigo() {

        Random numero = new Random();
        int dificultad1 = dificultad * 2;
        if(jugador.getSprite().getX()+70>enemigo.getSprite().getX()+100 ){

            if(numero.nextInt(100)<3*(dificultad1*13)) {
                if(enemigo.getEstadoAca() == PersonajeFinal.EstadoAtacando.NORMAL)
                enemigo.movimiento("der");

            }
        }else if(jugador.getSprite().getX()+100<enemigo.getSprite().getX()-100){
            if(numero.nextInt(100)<3*(dificultad1*13)) {
                if(enemigo.getEstadoAca() == PersonajeFinal.EstadoAtacando.NORMAL)
                enemigo.movimiento("izq");



            }
        }
        int nazar = numero.nextInt(400);

        if(enemigo.getEstadoAca() == PersonajeFinal.EstadoAtacando.NORMAL ){
            enemigo.setEstadoMov(PersonajeFinal.EstadoMov.QUIETO);
        if(nazar<(1*dificultad1)){

            enemigo.ataquePuno();
        }else if( nazar<dificultad1*8){
            enemigo.ataqueArma();
        }}
    }





    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
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

        AssManager.dispose();
        texturaWin.dispose();
        texturaLose.dispose();
        TexturaBtnRestartGame.dispose();
        texturaMenuPausa.dispose();
        texturaQuitPausa.dispose();
        texturaResumePausa.dispose();
        texturaFonPausa.dispose();
        texturaBtnDer.dispose();
        texturaBtnIzq.dispose();
        texturaBtnBrin.dispose();
        texturaPausa.dispose();
        texturaBtnPunch.dispose();
        texturaBtnWeapon.dispose();
        texturaFondo.dispose();
    }
}
