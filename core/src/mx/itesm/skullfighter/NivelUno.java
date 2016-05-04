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
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
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
    private AssetManager AssManager;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;

    Fondo fondo;
    Personaje jugador;


    Personaje enemigo;

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


    public NivelUno(Principal principal,AssetManager ass,int nivel,int huesos,int huesosGanar,int ese) {
        this.principal = principal;
        this.nivel = nivel;
        this.AssManager = ass;
        this.huesos = huesos;
        this.huesosGanar = huesosGanar;
        this.ese = ese;
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

        cmpHuesosGanar = new Componente(texturaHuesosGanar);
        cmpHuesosGanar.setPosicion(810,300);


        lose = new Componente(texturaLose);
        lose.setPosicion(250,450);

        BtnRestartGame = new Boton(TexturaBtnRestartGame);
        BtnRestartGame.setPosicion(480,290);

        BtnBackEnd = new Boton(texturaBackEnd);
        BtnBackEnd.setPosicion(650,290);



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
                    if(estado == 1 ){

                    if (verificarBoton(x1, y1, btnIzq) && movPointerIzq == pointer) {
                        jugador.setEstadoMov(Personaje.EstadoMov.QUIETO);
                        banderaIzquierdaApre = false;

                    } else if (verificarBoton(x1, y1, btnDer) && movPointerDer == pointer) {
                        jugador.setEstadoMov(Personaje.EstadoMov.QUIETO);
                        banderaDerechaApre = false;
                    }
                    if (verificarBoton(x, y, btnPunch)) {
                        //Sonidos.golpearSound();
                        jugador.setEstadoAca(Personaje.EstadoAtacando.NORMAL);

                    }
                    if (verificarBoton(x, y, btnWeapon)) {
                        //Sonidos.cuchilloSound();
                        jugador.setEstadoAca(Personaje.EstadoAtacando.NORMAL);
                    }}else if(estado == 3 || estado == 4 ){
                        if(verificarBoton(x1,y1,BtnQuitPausa)){
                            Sonidos.reproducirBoton();
                            estado = 100;
                            dispose();
                            principal.setScreen(new PantallaMenu(principal,huesos));
                        }
                        if(verificarBoton(x1,y1,BtnBackEnd)){
                            Sonidos.reproducirBoton();
                            estado = 100;
                            dispose();
                            principal.setScreen(new PantallaCargando(principal,0,huesos));
                        }
                    }else if( estado == 0){
                        if(verificarBoton(x1,y1,BtnQuitPausa)){
                            Sonidos.reproducirBoton();
                            estado = 100;
                            principal.setScreen(new PantallaMenu(principal,huesos));

                            //Preferencias música
                            Preferences pref = Gdx.app.getPreferences("Preferencias");
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
                    camara.unproject(coordenadas);  //traduce las coordenadas
                    float x1 = coordenadas.x;
                    float y1 = coordenadas.y;
                    if(estado==1) {
                        if (!verificarBoton(x1, y1, btnIzq) && !verificarBoton(x1, y1, btnDer)) {
                            jugador.setEstadoMov(Personaje.EstadoMov.QUIETO);
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
                    camara.unproject(coordenadas);  //traduce las coordenadas
                    float x1 = coordenadas.x;
                    float y1 = coordenadas.y;

                    if(estado == 1){


                        if (verificarBoton(x1, y1, btnIzq) && pointer == 0) {
                            jugador.movimiento("izq");

                            movPointerIzq = pointer;
                            banderaMoviendo = "izq";
                            banderaIzquierdaApre = true;

                        } else if (verificarBoton(x1, y1, btnDer) && pointer == 0
                                )  {
                            jugador.movimiento("der");
                            movPointerDer = pointer;
                            banderaMoviendo = "der";
                            banderaDerechaApre = true;

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
                            jugador.ataquePuno();
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

                    }else if(estado == 3 || estado == 4 ){
                        if(verificarBoton(x1, y1, BtnRestartGame)){
                            Sonidos.reproducirBoton();
                            estado = 100;
                            principal.setScreen(new NivelUno(principal,AssManager,nivel,huesos,huesosGanar,ese));
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
            vidaJ.setSprite(texturaVidaE[indexVidaJ]);
            vidaJ.getSprite().setPosition(x,y);
        }
    }

    @Override
    void cargarTexturas() {

        texturaWin = AssManager.get("YouWin.png",Texture.class);
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

        texturaFondo = AssManager.get(nivel +"/Entrenamiento"+ese+".png",Texture.class);

        texturaMovDer= new Texture[3];
        texturaMovDer[0] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/SkullCam1der.png",Texture.class);
        texturaMovDer[1] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/SkullCam2der.png",Texture.class);
        texturaMovDer[2] = AssManager.get("Personaje/"+pref.getInteger("ropa")+"/SkullCam3der.png",Texture.class);

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
        texturaEneMovDer[0] =AssManager.get(nivel +"/Enemigo1Der.png",Texture.class);
        texturaEneMovDer[1]=AssManager.get(nivel +"/Enemigo2Der.png",Texture.class);
        texturaEneMovDer[2]=AssManager.get(nivel +"/Enemigo3Der.png",Texture.class);

        texturaEneMovIzq = new Texture[3];
        texturaEneMovIzq[0] =AssManager.get(nivel +"/Enemigo1Izq.png",Texture.class);
        texturaEneMovIzq[1]=AssManager.get(nivel +"/Enemigo2Izq.png",Texture.class);
        texturaEneMovIzq[2]=AssManager.get(nivel +"/Enemigo3Izq.png",Texture.class);

        texturaEnePunchDer = new Texture[4];
        texturaEnePunchDer[0] = AssManager.get(nivel +"/EnemigoPun1Der.png",Texture.class);
        texturaEnePunchDer[1] = AssManager.get(nivel +"/EnemigoPun2Der.png",Texture.class);
        texturaEnePunchDer[2] = AssManager.get(nivel +"/Enemigo1Der.png",Texture.class);
        texturaEnePunchDer[3] =AssManager.get(nivel +"/Enemigo1Der.png",Texture.class);

        texturaEnePunchIzq= new Texture[4];
        texturaEnePunchIzq[0] = AssManager.get(nivel +"/EnemigoPun1Izq.png",Texture.class);
        texturaEnePunchIzq[1] = AssManager.get(nivel +"/EnemigoPun2Izq.png",Texture.class);
        texturaEnePunchIzq[2] = AssManager.get(nivel +"/Enemigo1Izq.png",Texture.class);
        texturaEnePunchIzq[3]=AssManager.get(nivel +"/Enemigo3Izq.png",Texture.class);
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
            jugador.actualizar();
            enemigo.actualizar();
            movimientoEnemigo();
            revisarVida();




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
            BtnBackEnd.render(batch);

        }else if(this.estado == 4){

            fondoPausa.render(batch);
            win.render(batch);
            cmpHuesosGanar.render(batch);
            BtnRestartGame.render(batch);
            BtnQuitPausa.render(batch);
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


    public boolean verificarBordes(Personaje player){

        if(player.getSprite().getX()-(player.getSprite().getWidth()/2) >= -280 && player.getSprite().getX()+player.getSprite().getWidth()/2 +10 <= 1220){
            return true;
        }

        return false;
    }

    private void ganoJuego() {
                this.estado = 4;
                this.huesos += huesosGanar;

    }

    private void perdioJuego() {
        this.estado = 3;
    }

    private void movimientoEnemigo() {
        Random numero = new Random();
        if(jugador.getSprite().getX()+70>enemigo.getSprite().getX()+100 ){

            if(numero.nextInt(500)<3) {
                if(enemigo.getEstadoAca() == Personaje.EstadoAtacando.NORMAL)
                enemigo.movimiento("der");
            }
        }else if(jugador.getSprite().getX()+100<enemigo.getSprite().getX()-100){
            if(numero.nextInt(500)<3) {
                if(enemigo.getEstadoAca() == Personaje.EstadoAtacando.NORMAL)
                enemigo.movimiento("izq");

            }
        }
        int nazar = numero.nextInt(50);

        if(enemigo.getEstadoAca() == Personaje.EstadoAtacando.NORMAL){
        if(nazar<2 && nazar>0){

            enemigo.ataquePuno();
        }else if(nazar>=3 && nazar<=4){
            //enemigo.ataqueArma();
        }}
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
