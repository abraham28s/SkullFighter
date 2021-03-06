package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.lwjgl.Sys;

import java.util.Random;

/**
 * Created by abrahamsoto on 30/03/16.
 */
public class NivelTutorial extends PantallaAbstracta implements Screen {

    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;

    Fondo fondo;
    PersonajeTutorial jugador;
    PersonajeTutorial enemigo;

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
    private Componente fondoPausa;
    private Texture texturaFonPausa;
    private Componente MenuPausa;
    private Texture texturaMenuPausa;
    private Boton BtnQuitPausa;
    private Texture texturaQuitPausa;
    private Boton BtnResumePausa;
    private Texture texturaResumePausa;

    private int indexVidaE = 6;

    private Boton BtnRestartGame;
    private Texture TexturaBtnRestartGame;
    private Componente win;
    private Texture texturaWin;
    private Componente lose;
    private Texture texturaLose;
    int  movPointerDer,movPointerIzq, brincoPointer;
    private String banderaMoviendo;
    private boolean banderaIzquierdaApre;
    private boolean banderaDerechaApre;

    private Boton textoParton;
    private Texture texTextoParton[];

    private Texture texturaParton;
    private Componente cmpParton;
    private int variableTexto = 5;
    Preferences pref = Gdx.app.getPreferences("Preferencias");
    private int contador=0;
    private int huesos;


    public NivelTutorial(Principal principal,int huesos) {
        this.principal = principal;
        this.huesos = huesos;
    }

    @Override
    public void show() {
        if(pref.getInteger("nivel")<1){
            pref.putInteger("nivel",1);
            pref.flush();
        }

        pref.flush();
        setYUpgradeCamara();

        cargarTexturas();

        fondo = new Fondo(texturaFondo);

        jugador = new PersonajeTutorial(texturaMovDer,texturaMovIzq,texturaOzDer,texturaOzIzq,texturaPunchDer,texturaPunchIzq,"jugador",this);
        jugador.setPosicion(-15, -30);



        enemigo = new PersonajeTutorial(texturaMovDer,texturaMovIzq,texturaOzDer,texturaOzIzq,texturaPunchDer,texturaPunchIzq,"enemigo",this);

        fondoPausa = new Componente(texturaFonPausa);

        MenuPausa = new Componente(texturaMenuPausa);
        MenuPausa.setPosicion(417,120);

        win = new Componente(texturaWin);
        win.setPosicion(210,450);

        lose = new Componente(texturaLose);
        lose.setPosicion(250,450);

        BtnRestartGame = new Boton(TexturaBtnRestartGame);
        BtnRestartGame.setPosicion(565,310);

        cmpParton = new Componente(texturaParton);
        cmpParton.setPosicion(1000,20);



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
                        jugador.setEstadoMov(PersonajeTutorial.EstadoMov.QUIETO);
                        banderaIzquierdaApre = false;

                    } else if (verificarBoton(x1, y1, btnDer) && movPointerDer == pointer) {
                        jugador.setEstadoMov(PersonajeTutorial.EstadoMov.QUIETO);
                        banderaDerechaApre = false;
                    }
                    if (verificarBoton(x, y, btnPunch)) {
                        //Sonidos.golpearSound();
                        jugador.setEstadoAca(PersonajeTutorial.EstadoAtacando.NORMAL);

                    }
                    if (verificarBoton(x, y, btnWeapon)) {
                        //Sonidos.cuchilloSound();
                        jugador.setEstadoAca(PersonajeTutorial.EstadoAtacando.NORMAL);

                    }
                        if(verificarBoton(x1,y1, textoParton) && variableTexto == 5){
                            //cambiar a pantalla de jugar
                            if (pref.getBoolean("boton") == true ) {
                                Sonidos.reproducirBoton();
                            }

                            principal.setScreen(new PantallaCargando(principal, 0, huesos, 0));
                            estado = 1000;
                        }
                    }else if(estado == 3 || estado == 4 ){
                        if(verificarBoton(x1,y1,BtnQuitPausa)){
                            if (pref.getBoolean("boton") == true ) {
                                Sonidos.reproducirBoton();
                            }
                            estado = 100;
                            dispose();
                            principal.setScreen(new PantallaMenu(principal));
                        }
                    }else if( estado == 0){
                        if(verificarBoton(x1,y1,BtnQuitPausa)){

                            if (pref.getBoolean("boton") == true ) {
                                Sonidos.reproducirBoton();
                            }
                            estado = 100;
                            principal.setScreen(new PantallaMenu(principal));

                            //Preferencias música
                            Preferences pref = Gdx.app.getPreferences("Preferencias");
                            pref.getBoolean("musica", true);
                            pref.flush();
                            if (pref.getBoolean("musica",true)) {
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
                            jugador.setEstadoMov(PersonajeTutorial.EstadoMov.QUIETO);
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
                            if (jugador.getEstado() == PersonajeTutorial.EstadoBrinco.NORMAL) {
                                jugador.movimientoBrin();
                                jugador.movimiento("der");

                            }
                            brincoPointer = pointer;
                        }
                        if (verificarBoton(x1, y1, btnBrin) && pointer != 0 && movPointerIzq == 0 && banderaMoviendo.equals("izq") ) {
                            //Sonidos.saltarSound();
                            if (jugador.getEstado() == PersonajeTutorial.EstadoBrinco.NORMAL) {
                                jugador.movimientoBrin();
                                jugador.movimiento("izq");

                            }
                            brincoPointer = pointer;
                        } else if (verificarBoton(x1, y1, btnBrin) && pointer == 0) {
                            if (jugador.getEstado() == PersonajeTutorial.EstadoBrinco.NORMAL) {
                                jugador.movimientoBrin();

                            }
                            brincoPointer = pointer;
                        }
                        if (verificarBoton(x1, y1, btnPunch)) {
                            //Sonidos.golpearSound();
                            if(variableTexto==2){
                                variableTexto=3;
                            }
                            jugador.ataquePuno();

                        }
                        if (verificarBoton(x1, y1, btnWeapon)) {
                            //Sonidos.cuchilloSound();
                            if(variableTexto==3){
                                variableTexto=4;
                            }
                            jugador.ataqueArma();

                        }

                        if(verificarBoton(x1,y1, btnPausa)){
                            //cambiar a pantalla de jugar
                            if (pref.getBoolean("boton") == true ) {
                                Sonidos.reproducirBoton();
                            }
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
                            principal.setScreen(new NivelTutorial(principal,huesos));

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

        texturaFondo = new Texture(Gdx.files.internal("Tutorial/Entrenamiento.png"));
        texturaParton = new Texture(Gdx.files.internal("Tutorial/Parton_Dialogo.png"));
        texturaMovDer= new Texture[3];
        texturaMovDer[0] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/SkullCam1der.png"));
        texturaMovDer[1] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/SkullCam2der.png"));
        texturaMovDer[2] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/SkullCam3der.png"));

        texturaMovIzq = new Texture[3];
        texturaMovIzq[0] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/SkullCam1izq.png"));
        texturaMovIzq[1] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/SkullCam2izq.png"));
        texturaMovIzq[2] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/SkullCam3izq.png"));

        texturaOzDer = new Texture[5];
        texturaOzDer[0] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/"+pref.getInteger("arma",1)+"/Oz1Der.png"));
        texturaOzDer[1] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/"+pref.getInteger("arma",1)+"/Oz2Der.png"));
        texturaOzDer[2] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/"+pref.getInteger("arma",1)+"/Oz3Der.png"));
        texturaOzDer[3] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/"+pref.getInteger("arma",1)+"/Oz4Der.png"));
        texturaOzDer[4] =texturaMovDer[2];

        texturaOzIzq = new Texture[5];
        texturaOzIzq[0] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/"+pref.getInteger("arma",1)+"/Oz1Izq.png"));
        texturaOzIzq[1] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/"+pref.getInteger("arma",1)+"/Oz2Izq.png"));
        texturaOzIzq[2] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/"+pref.getInteger("arma",1)+"/Oz3Izq.png"));
        texturaOzIzq[3] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa",1)+"/"+pref.getInteger("arma",1)+"/Oz4Izq.png"));
        texturaOzIzq[4] = texturaMovIzq[0];

        texturaPunchDer = new Texture[4];
        texturaPunchDer[0] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchDer1.png"));
        texturaPunchDer[1] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchDer2.png"));
        texturaPunchDer[2] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchDer3.png"));
        texturaPunchDer[3] = texturaMovDer[2];

        texTextoParton = new Texture[6];
        for (int i = 0; i < 6; i++) {
            texTextoParton[i] = new Texture(Gdx.files.internal("Tutorial/Training"+i+".png"));
        }

        texturaPunchIzq = new Texture[4];
        texturaPunchIzq[0] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchIzq1.png"));
        texturaPunchIzq[1] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchIzq2.png"));
        texturaPunchIzq[2] = new Texture(Gdx.files.internal("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchIzq3.png"));
        texturaPunchIzq[3] = texturaMovIzq[0];


        texturaWin = new Texture(Gdx.files.internal("YouWin.png"));
        texturaLose = new Texture(Gdx.files.internal("YouLose.png"));
        TexturaBtnRestartGame = new Texture(Gdx.files.internal("Restart.png"));

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

        textoParton = new Boton(texTextoParton[variableTexto]);
        textoParton.setPosicion(850,400);

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
        vista = new FitViewport(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO, camara);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        actualizarTexto();

        // DIBUJA
        batch.begin();
        fondo.render(batch);
        cmpParton.render(batch);
        jugador.render(batch);

        textoParton.setTextura(texTextoParton[variableTexto]);
        textoParton.setPosicion(850, 400);
        textoParton.render(batch);

       // System.out.println(jugador.getEstadoMov());


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

        }else if(this.estado == 4){

            fondoPausa.render(batch);
            win.render(batch);
            BtnRestartGame.render(batch);
            BtnQuitPausa.render(batch);
        }
        batch.end();
    }

    private void actualizarTexto() {
        if(contador<=200){
            variableTexto = 0;
            contador++;

        }else if(contador>200 && contador<400){
            variableTexto = 1;
            contador++;
        }else if(contador==400){
            variableTexto = 2;
            contador = 1000;
        }
        if(variableTexto==4){
            if(contador>=1000 && contador <1200){
                contador++;
            }else{
                contador = 1300;
                variableTexto = 5;
            }
        }
    }

    private void revisarVida() {
        if(indexVidaJ==0){
            perdioJuego();
        }else if(indexVidaE == 0){
            ganoJuego();
        }
    }


    public boolean verificarBordes(PersonajeTutorial player){

        if(player.getSprite().getX()-(player.getSprite().getWidth()/2) >= -280 && player.getSprite().getX()+player.getSprite().getWidth()/2 +10 <= 1220){
            return true;
        }

        return false;
    }

    private void ganoJuego() {
                this.estado = 4;

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
        int nazar = numero.nextInt(40);

        if(enemigo.getEstadoAca() == PersonajeTutorial.EstadoAtacando.NORMAL){
        if(nazar<1 && nazar>0){
            enemigo.ataquePuno();
        }else if(nazar>=3 && nazar<=4){
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

        texturaFondo.dispose();
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
    }
}
