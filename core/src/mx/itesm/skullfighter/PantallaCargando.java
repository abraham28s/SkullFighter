package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
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


    private int pantallaCargar;
    private float variableTexuta;

    public PantallaCargando(Principal principal,int pantalla){
        this.principal = principal;
        this.pantallaCargar = pantalla;
    }


    @Override
    public void show() {
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
                for (int i = 1; i <= 3; i++) {
                    Manager.load("SkullCam"+i+"der.png",Texture.class);
                    Manager.load("SkullCam"+i+"izq.png",Texture.class);
                    Manager.load("Civil2/Civil2-" + i + ".png", Texture.class);
                }

                Manager.load("Fondo-Capa1.png",Texture.class);

                Manager.load("Civil2/Tip.png", Texture.class);
                Manager.load("CivilFrente1.png", Texture.class);
                Manager.load("FightText.png", Texture.class);
                Manager.load("Boton_Derecha.png", Texture.class);
                Manager.load("Boton_Izquierda.png", Texture.class);
                Manager.load("BotonJump.png", Texture.class);
                Manager.load("BackGame.png", Texture.class);
                Manager.load("skip.png", Texture.class);


                break;
            case 1:
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
        switch (pantallaCargar) {
            case 0://PantallaJuego
                actualizar(Manager);
            case 1:

        }

    }

    private void actualizar(AssetManager man) {
        if(man.update()) {
            switch (pantallaCargar) {
                case 0://PantallaJuego
                    this.principal.setScreen(new PantallaJuego(this.principal,this.Manager));
                case 1:

            }
        }else{
            float x = spriteCargando.getSprite().getX();
            float y = spriteCargando.getSprite().getY();
            variableTexuta = man.getProgress()*100;
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
