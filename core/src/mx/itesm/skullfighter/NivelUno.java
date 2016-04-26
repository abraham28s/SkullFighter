package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
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
    private int estado = 5;

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

        enemigo = new Personaje(texturaEneMovIzq[0]);
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

    @Override
    void leerEntrada() {

        Vector3 coordenadas = new Vector3();
        coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camara.unproject(coordenadas);  //traduce las coordenadas
        float x = coordenadas.x;
        float y = coordenadas.y;

        if(Gdx.input.isTouched()) {
            if(verificarBoton(x,y,btnDer) && verificarBordes()){
                movimientoDer(jugador,texturaMovDer);
            }
            if(verificarBoton(x,y,btnIzq) && verificarBordes()){
                movimientoIzq(jugador,texturaMovIzq);
            }
        }
        if(Gdx.input.justTouched()) {
            if(verificarBoton(x, y, btnBrin)){
                Sonidos.saltarSound();
                if(jugador.getEstado() == Personaje.Estado.NORMAL) {
                    jugador.movimientoBrin();
                }
            }
            if(verificarBoton(x,y,btnPunch)){
                Sonidos.golpearSound();
                jugador.setAtacandoPu(true);
                juAtacoPu = true;
            }
            if(verificarBoton(x,y,btnWeapon) ){
                Sonidos.cuchilloSound();
                jugador.setAtacandoWe(true);
            }
        }
        if(Gdx.input.justTouched() &&Gdx.input.isTouched()){
            if(verificarBoton(x,y,btnDer) && verificarBoton(x, y, btnBrin) && verificarBordes()){
                movimientoDer(jugador,texturaMovDer);

                if(jugador.getEstado() == Personaje.Estado.NORMAL) {
                    jugador.movimientoBrin();
                }
            }
            if(verificarBoton(x,y,btnIzq)&& verificarBoton(x, y, btnBrin) && verificarBordes() ){
                movimientoIzq(jugador,texturaMovIzq);
                if(jugador.getEstado() == Personaje.Estado.NORMAL) {
                    jugador.movimientoBrin();
                }
            }
            if(verificarBoton(x,y, btnPausa)){
                //cambiar a pantalla de jugar
                Sonidos.reproducirBoton();
                pausarJuego();
            }
        }
    }

    private void pausarJuego() {
        this.estado = 0;
    }

    private void movimientoIzq(Personaje per, Texture[] izq) {

        float x = per.getSprite().getX();
        float y = per.getSprite().getY();
        //System.out.println(Gdx.graphics.getDeltaTime());
        if(per.equals(jugador)) {
            if (conJ % 4 == 0) {
                per.setSprite(izq[MovJ % 3]);
                MovJ++;
            }
            if (MovJ > 500) {
                MovJ = 0;
            }
            conJ++;
            if (conJ > 500) {
                conJ = 0;
            }
            per.setVista("izq");
            per.setPosicion(x - 5, y);
        }else{
            if (conE % 4 == 0) {
                per.setSprite(izq[MovE % 3]);
                MovE++;
            }
            if (MovE > 500) {
                MovE = 0;
            }
            conE++;
            if (conE > 500) {
                conE = 0;
            }
            per.setVista("izq");
            per.setPosicion(x - 5, y);
        }
    }

    private void movimientoDer(Personaje per, Texture[] der) {
        float x = per.getSprite().getX();
        float y = per.getSprite().getY();
        if(per.equals(jugador)) {
            if (conJ % 4 == 0) {
                per.setSprite(der[MovJ % 3]);
                MovJ++;
            }
            if (MovJ > 500) {
                MovJ = 0;
            }
            conJ++;
            if (conJ > 500) {
                conJ = 0;
            }
            per.setVista("der");
            per.setPosicion(x + 5, y);
        }else{
            if (conE % 4 == 0) {
                per.setSprite(der[MovE % 3]);
                MovE++;
            }
            if (MovE > 500) {
                MovE = 0;
            }
            conE++;
            if (conE > 500) {
                conE = 0;
            }
            per.setVista("der");
            per.setPosicion(x + 5, y);
        }
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

        batch.setProjectionMatrix(camara.combined);

        // DIBUJA
        batch.begin();
        fondo.render(batch);
        jugador.render(batch);
        jugador.actualizar();
        enemigo.render(batch);
        vidaJ.render(batch);
        vidaE.render(batch);

        btnIzq.render(batch);
        btnDer.render(batch);
        btnBrin.render(batch);
        btnPausa.render(batch);
        btnWeapon.render(batch);
        btnPunch.render(batch);

        if(this.estado == 1) {
            leerEntrada(); // Revisar eventos
            // Mover la cámara para que siga al personaje
            movimientoEnemigo();

            revisarAtacando(jugador, texturaOzDer, texturaOzIzq, texturaPunchDer, texturaPunchIzq);

            ataqueEnemigo();
            revisarAtacando(enemigo, texturaOzDer, texturaOzIzq, texturaEnePunchDer, texturaEnePunchIzq);
            revisarColi();

        }else if(this.estado ==0){
            leerEntradaPausa();
            fondoPausa.render(batch);
            MenuPausa.render(batch);
            BtnQuitPausa.render(batch);
            BtnResumePausa.render(batch);

        }else if(this.estado == 3){
            leerEntradaFin();
            fondoPausa.render(batch);
            lose.render(batch);
            BtnRestartGame.render(batch);
            BtnQuitPausa.render(batch);

        }else if(this.estado == 4){
            leerEntradaFin();
            fondoPausa.render(batch);
            win.render(batch);
            BtnRestartGame.render(batch);
            BtnQuitPausa.render(batch);
        }
        batch.end();
    }

    private void leerEntradaFin() {
        Vector3 coordenadas = new Vector3();
        coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camara.unproject(coordenadas);  //traduce las coordenadas
        float x = coordenadas.x;
        float y = coordenadas.y;
        if(Gdx.input.justTouched()) {
            if(verificarBoton(x, y, BtnRestartGame)){
                Sonidos.reproducirBoton();
                this.principal.setScreen(new PantallaJuego(principal));
            }
            if(verificarBoton(x,y,BtnQuitPausa)){
                Sonidos.reproducirBoton();
                this.principal.setScreen(new PantallaMenu(principal));
            }
        }
    }

    private void leerEntradaPausa() {
        Vector3 coordenadas = new Vector3();
        coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camara.unproject(coordenadas);  //traduce las coordenadas
        float x = coordenadas.x;
        float y = coordenadas.y;
        if(Gdx.input.justTouched()) {
            if(verificarBoton(x, y, BtnResumePausa)){
                Sonidos.reproducirBoton();
                resumirJuego();
            }
            if(verificarBoton(x,y,BtnQuitPausa)){
                Sonidos.reproducirBoton();
                this.principal.setScreen(new PantallaMenu(principal));

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

    private void revisarColi() {
        //System.out.print(juAtacoWe);
        //System.out.println(indexVidaE);
        if(enemigo.getSprite().getX()+100>jugador.getSprite().getX()&&enemigo.getSprite().getX()-100<jugador.getSprite().getX()){
            //System.out.print("tru de x");
            //System.out.print(conPuE);
        if(eneAtacoPu== true &&conPuE%3==0){
            //System.out.print("tru de x");
            if(indexVidaJ>0){
                indexVidaJ--;
                float x = vidaJ.getSprite().getX();
                float y = vidaJ.getSprite().getY();
                vidaJ.setTextura(texturaVidaJ[indexVidaJ]);
                vidaJ.setPosicion(x,y);
            }else if(indexVidaJ ==0){
                perdioJuego();
            }
            eneAtacoPu = false;
        }

            if(juAtacoPu== true &&conPuJ%3==0){
                //System.out.print("tru de x");
                if(indexVidaE>0){
                    indexVidaE--;
                    float x = vidaE.getSprite().getX();
                    float y = vidaE.getSprite().getY();
                    vidaE.setTextura(texturaVidaE[indexVidaE]);
                    vidaE.setPosicion(x,y);
                }else if(indexVidaE ==0){
                    ganoJuego();
                }
                juAtacoPu = false;
            }else if(juAtacoWe== true){
                //System.out.println("tru de x");
                //System.out.println(indexVidaE + "hola");
                if(indexVidaE>0){
                    indexVidaE-=2;
                    System.out.print("llegue");
                    float x = vidaE.getSprite().getX();
                    float y = vidaE.getSprite().getY();
                    vidaE.setTextura(texturaVidaE[indexVidaE]);
                    vidaE.setPosicion(x,y);
                }else if(indexVidaE ==0){
                    ganoJuego();
                }
                juAtacoWe = false;
            }
        }
        else{
            eneAtacoPu = false;
            juAtacoPu = false;
            juAtacoWe = false;
        }
    }

    private void ganoJuego() {
        this.estado = 4;
    }

    private void perdioJuego() {
        this.estado = 3;
    }

    private void movimientoEnemigo() {
        Random numero = new Random();
        if(jugador.getSprite().getX()>enemigo.getSprite().getX()){

            if(numero.nextInt(15)<3) {
                movimientoDer(enemigo, texturaEneMovDer);
            }
        }else if(jugador.getSprite().getX()<enemigo.getSprite().getX()){
            if(numero.nextInt(15)<3) {
                movimientoIzq(enemigo, texturaEneMovIzq);
            }
        }
    }

    private void ataqueEnemigo() {
        if(Math.random()*80+1 <2) {
            enemigo.setAtacandoPu(true);
        }
    }

    private void revisarAtacando(Personaje per, Texture[] derW, Texture[] izqW, Texture[] derP, Texture[] izqP) {
        float x = per.getSprite().getX();
        float y = per.getSprite().getY();
        if(per.equals(jugador)) {
            if (per.getAtacandoWe() == true) {

                if (MovJ % 5 == 0) {
                    if (per.getVista().equals("der")) {
                        per.setSprite(derW[conWeJ % 5]);

                    } else if (per.getVista().equals("izq")) {
                        per.setSprite(izqW[conWeJ % 5]);
                    }
                    conWeJ++;
                }

                if (conWeJ > 500) {
                    conWeJ = 0;
                }
                MovJ++;
                if (MovJ > 500) {
                    MovJ = 0;
                }
                if (conWeJ % 5 == 0) {
                    per.setAtacandoWe(false);
                    juAtacoWe = true;
                    conWeJ++;
                }
                //System.out.print(conWe);
                per.setPosicion(x, y);
            }
            if (per.getAtacandoPu() == true) {
                //float ypr = y-40;
                if (MovJ % 5 == 0) {
                    if (per.getVista().equals("der")) {

                        per.setSprite(derP[conPuJ % 3]);

                    } else if (per.getVista().equals("izq")) {
                        per.setSprite(izqP[conPuJ % 3]);
                    }
                    conPuJ++;
                }
                if (conPuJ > 500) {
                    conPuJ = 0;
                }
                MovJ++;
                if (MovJ > 500) {
                    MovJ = 0;
                }
                if (conPuJ % 4 == 0) {
                    per.setAtacandoPu(false);
                    conPuJ++;
                }
                //System.out.print(conPu);
                per.setPosicion(x, y);
            }
        }else{
            if (per.getAtacandoPu() == true) {
                //float ypr = y-40;
                if (MovE % 5 == 0) {
                    if (per.getVista().equals("der")) {
                        per.setSprite(derP[conPuJ % 3]);

                    } else if (per.getVista().equals("izq")) {
                        per.setSprite(izqP[conPuJ % 3]);
                    }
                    conPuE++;
                }
                if (conPuE > 500) {
                    conPuE = 0;
                }
                MovE++;
                if (MovE > 500) {
                    MovE = 0;
                }
                if (conPuE % 4 == 0) {
                    per.setAtacandoPu(false);
                    eneAtacoPu = true;
                    conPuE++;
                }
                //System.out.print(conPu);
                per.setPosicion(x, y);
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
