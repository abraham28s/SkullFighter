package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import javax.xml.soap.Text;

/**
 * Created by PAVILION on 11/04/2016.
 */
public class PantallaCargando extends PantallaAbstracta implements Screen {

    private int pantallaOri;
    private int nivelLargo;
    private Principal principal;

    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    //Imagen Cargando
    private Texture[] texturaCargado;
    private Componente spriteCargando;

    private Texture texturaTexto;
    private Componente load;
    //Fondo de la textura
    private Texture texturaFondoCargando;
    private Fondo spriteFondoCargando;

    AssetManager Manager;
    private int huesos;


    private int pantallaCargar;
    private float variableTexuta;
    private int nivel;
    Preferences pref = Gdx.app.getPreferences("Preferencias");

    private int huesosGanar;
    private int ese;
    private int enenum;
    private int dificultad;

    public PantallaCargando(Principal principal,int pantalla,int huesos,int pantallaOri){
        this.principal = principal;
        this.pantallaCargar = pantalla;
        this.huesos = huesos;
        this.pantallaOri = pantallaOri;



    }
    public PantallaCargando(Principal principal,int pantalla,int huesos,int nivel,int huesosGanar,int ese,int pantallaOri,int dificultad){
        this.principal = principal;
        this.pantallaCargar = pantalla;
        this.huesos = huesos;
        this.nivel = nivel;
        this.huesosGanar = huesosGanar;
        this.ese = ese;
        this.pantallaOri = pantallaOri;
        this.dificultad=dificultad;

    }


    @Override
    public void show() {
        pref.flush();
        setYUpgradeCamara();
        batch = new SpriteBatch();
        Manager = new AssetManager();
        texturaCargado= new Texture[5];



        for (int i = 0; i < 5; i++) {
            texturaCargado[i] = new Texture(Gdx.files.internal("Loading/Loading" + 25*i + ".png"));
        }
        spriteCargando = new Componente(texturaCargado[0]);
        spriteCargando.setPosicion(500,250);

        texturaFondoCargando = new Texture(Gdx.files.internal("PantallaVacia.png"));
        spriteFondoCargando = new Fondo(texturaFondoCargando);

        texturaTexto = new Texture(Gdx.files.internal("Loading/Loading.png"));
        load = new Componente(texturaTexto);

        load.setPosicion(390,100);

        cargarAssets();
    }

    private void cargarAssets() {
        switch (pantallaCargar){
            case 0://PantallaJuego
                        Manager.load("Fondo-Capa2.png",Texture.class);
                        Manager.load("GoldBone.png",Texture.class);
                        for (int i = 1; i <= 3; i++) {
                            Manager.load("Personaje/"+pref.getInteger("ropa")+"/SkullCam"+i+"der.png",Texture.class);
                            Manager.load("Personaje/"+pref.getInteger("ropa")+"/SkullCam"+i+"izq.png",Texture.class);
                            Manager.load("Civil2/Civil2-" + i + ".png", Texture.class);
                            Manager.load("Dialogos/TextCivil" + i + ".png", Texture.class);
                        }
                        Manager.load("DialogosEspantaPajaros/TextScarecrow1.png",Texture.class);
                        Manager.load("DialogosEspantaPajaros/TextScarecrow2.png",Texture.class);

                        Manager.load("Fondo-Capa1.png",Texture.class);

                        Manager.load("Civil2/Tip.png", Texture.class);
                        Manager.load("CivilFrente1.png", Texture.class);
                        Manager.load("Espantapajaros3.png", Texture.class);
                        Manager.load("CivilMalo3.png", Texture.class);
                        Manager.load("CivilFrente1.png", Texture.class);
                        Manager.load("FightText.png", Texture.class);
                        Manager.load("Boton_Derecha.png", Texture.class);
                        Manager.load("Boton_Izquierda.png", Texture.class);
                        Manager.load("BotonJump.png", Texture.class);
                        Manager.load("BackGame.png", Texture.class);
                        Manager.load("skip.png", Texture.class);



                        break;




            case 1://NivelUno
                Manager.load("YouWin.png",Texture.class);
                Manager.load("YouLose.png", Texture.class);
                Manager.load("Restart.png",Texture.class);

                Manager.load("Pausemenu.png",Texture.class);
                Manager.load("Quit.png",Texture.class);
                Manager.load("Resume.png",Texture.class);
                Manager.load("negro.png",Texture.class);

                Manager.load("Boton_Derecha.png",Texture.class);
                Manager.load("Boton_Izquierda.png",Texture.class);
                Manager.load("BotonJump.png",Texture.class);
                Manager.load("PauseBotton.png",Texture.class);
                Manager.load("BotonPunch.png",Texture.class);
                Manager.load("BotonWeapon.png",Texture.class);

                Manager.load(nivel + "/Entrenamiento"+ese+".png",Texture.class);

                Manager.load("BackGame.png", Texture.class);
                for (int i = 1; i <=3 ; i++) {
                    Manager.load("Personaje/"+pref.getInteger("ropa")+"/SkullCam"+i+"der.png", Texture.class);
                    Manager.load("Personaje/"+pref.getInteger("ropa")+"/SkullCam"+i+"izq.png", Texture.class);
                    Manager.load("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchDer"+i+".png", Texture.class);
                    Manager.load("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/SkullPunchIzq"+i+".png", Texture.class);
                    Manager.load(nivel + "/Enemigo"+i+"Der.png",Texture.class);
                    Manager.load(nivel + "/Enemigo"+i+"Izq.png",Texture.class);
                }


                for (int i = 1; i <=2 ; i++) {
                    Manager.load(nivel + "/EnemigoPun"+i+"Der.png",Texture.class);

                    Manager.load(nivel + "/EnemigoPun"+i+"Izq.png",Texture.class);
                }

                for (int i = 1; i <=4 ; i++) {
                    Manager.load("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/Oz"+i+"Der.png",Texture.class);

                    Manager.load("Personaje/"+pref.getInteger("ropa")+"/"+pref.getInteger("arma")+"/Oz"+i+"Izq.png",Texture.class);
                }

                for (int i = 0; i<7;i++) {
                    Manager.load("Personaje/VidaSkull" + i + ".png",Texture.class);
                    Manager.load(nivel + "/VidaSkullE" + i + ".png",Texture.class);
                }
                Manager.load("+"+huesosGanar+".png",Texture.class);
                break;



            case 2: //Castillo
                Manager.load("Castillo2.png",Texture.class);
                Manager.load("GoldBone.png",Texture.class);
                for (int i = 1; i <= 3; i++) {
                    Manager.load("Personaje/"+pref.getInteger("ropa")+"/SkullCam"+i+"der.png",Texture.class);
                    Manager.load("Personaje/"+pref.getInteger("ropa")+"/SkullCam"+i+"izq.png",Texture.class);
                    Manager.load("Civil2/Civil2-" + i + ".png", Texture.class);
                    Manager.load("Dialogos/TextCivil" + i + ".png", Texture.class);
                }
                Manager.load("DialogosEspantaPajaros/TextScarecrow4.png",Texture.class);
                Manager.load("DialogosEspantaPajaros/TextScarecrow5.png",Texture.class);

                Manager.load("Castillo1.png",Texture.class);

                Manager.load("Civil2/Tip.png", Texture.class);
                Manager.load("Guardia1.png", Texture.class);
                Manager.load("Espantapajaros3.png", Texture.class);
                Manager.load("Guardia2.png", Texture.class);
                Manager.load("Guardia3.png", Texture.class);
                Manager.load("FightText.png", Texture.class);
                Manager.load("Boton_Derecha.png", Texture.class);
                Manager.load("Boton_Izquierda.png", Texture.class);
                Manager.load("BotonJump.png", Texture.class);
                Manager.load("BackGame.png", Texture.class);
                Manager.load("skip.png", Texture.class);
                break;

        }
    }

    @Override
    public void render(float delta) {


        batch.setProjectionMatrix(camara.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        spriteFondoCargando.render(batch);
        spriteCargando.render(batch);
        load.render(batch);
        batch.end();
        actualizar();

    }

    public void actualizarHuesos(int huesos){
        this.huesos = huesos;
    }

    private void actualizar() {
        if(this.Manager.update()) {
            switch (pantallaCargar) {
                case 0://PantallaJuego
                    this.principal.setScreen(new PantallaJuego(this.principal,this.Manager,this.huesos));
                    //this.principal.setScreen(new PantallaCastillo(this.principal,this.Manager));
                    this.dispose();
                    break;
                case 1://NivelUno
                    this.principal.setScreen(new NivelUno(this.principal,this.Manager,nivel,huesos,huesosGanar,ese,pantallaOri,dificultad));
                    this.dispose();
                    break;

                case 2:
                    this.principal.setScreen(new PantallaCastillo(this.principal,this.Manager,huesos));
                    this.dispose();
                    break;

            }
        }else{
            float x = spriteCargando.getSprite().getX();
            float y = spriteCargando.getSprite().getY();
            variableTexuta = this.Manager.getProgress()*100;
            if (variableTexuta>=0 && variableTexuta<=25){
                spriteCargando.setTextura(texturaCargado[0]);
            }else if (variableTexuta>25 && variableTexuta<=50){
                spriteCargando.setTextura(texturaCargado[1]);
            }else if (variableTexuta>50 && variableTexuta<=75){
                spriteCargando.setTextura(texturaCargado[2]);
            }else if (variableTexuta>75 && variableTexuta<100){
                spriteCargando.setTextura(texturaCargado[3]);
            }else if (variableTexuta == 100){
                spriteCargando.setTextura(texturaCargado[4]);
            }
            spriteCargando.setPosicion(x,y);
        }
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

        texturaTexto.dispose();
        texturaFondoCargando.dispose();
    }

    @Override
    void leerEntrada() {

    }

    @Override
    void cargarTexturas() {

    }

    @Override
    void crearYPosBotones() {

    }

    @Override
    void setYUpgradeCamara() {
        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new StretchViewport(Principal.ANCHO_MUNDO,Principal.ALTO_MUNDO,camara);
    }
}