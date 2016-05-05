package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Fer on 26/04/2016.
 */
public class Comic1 extends PantallaAbstracta  implements  Screen{

    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    //fondo
    private Fondo fondo;

    //botones
    private Boton skip;
    private Boton next;
    private Boton back;

    //texturas
    private Texture texturaSkip;
    private Texture texturaFondo;
    private Texture texturaNext;
    private Texture texturaBack;

    private Texture[] comic;
    private int contador = 1;

    private Texture texturaComic2;
    private Texture texturaComic3;
    private int huesos;

    public Comic1(Principal principal,int huesos) {
        this.principal = principal;
        this.huesos = huesos;

    }

    public Comic1(Principal principal) {
        this.principal = principal;


    }

    @Override
    public boolean verificarBoton(float x, float y, Boton btn) {
        Sprite sprite = btn.getSprite();
        return x>=sprite.getX() && x<=sprite.getX()+sprite.getWidth()
                && y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }
    @Override
    public void cargarTexturas() {
        texturaFondo = new Texture(Gdx.files.internal("Comic1.png"));
        texturaSkip = new Texture(Gdx.files.internal("skip.png"));
        texturaBack = new Texture(Gdx.files.internal("Boton_Izquierda.png"));
        texturaNext = new Texture(Gdx.files.internal("Boton_Derecha.png"));

        comic = new Texture[8];
        comic[0] = new Texture(Gdx.files.internal("Escenario1Cortado.png"));
        comic[1] = new Texture(Gdx.files.internal("Comic1.png"));
        comic[2] = new Texture(Gdx.files.internal("Comic2.png"));
        comic[3] = new Texture(Gdx.files.internal("Comic3.png"));
        comic[4] = new Texture(Gdx.files.internal("Comic4.png"));
        comic[5] = new Texture(Gdx.files.internal("Comic5.png"));
        comic[6] = new Texture(Gdx.files.internal("Comic6.png"));
        comic[7] = new Texture(Gdx.files.internal("Escenario1Cortado.png"));
            }

    @Override
    void crearYPosBotones() {
    }

    @Override
    void setYUpgradeCamara() {
    }

    @Override
    public void show() {
        camara = new OrthographicCamera(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new FitViewport(Principal.ANCHO_MUNDO,Principal.ALTO_MUNDO,camara);

        cargarTexturas();
        fondo = new Fondo(texturaFondo);

        back = new Boton(texturaBack);
        back.setPosicion(100,12);

        next = new Boton(texturaNext);
        next.setPosicion(1110,12);

        skip = new Boton(texturaSkip);
        skip.setPosicion(1110,580);

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) { Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        leerEntrada();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        fondo.render(batch);
        next.render(batch);
        back.render(batch);
        skip.render(batch);
        batch.end();
    }

    @Override
    void leerEntrada() {
        if(Gdx.input.justTouched()) {//saber si el usuario toca
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);//TRaduce coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBoton(x, y, next)) {

                //cambiar a pantalla
                Preferences pref = Gdx.app.getPreferences("Preferencias");
                fondo.setTextura(comic[contador + 1]);
                if (pref.getBoolean("boton") == true ) {
                    Sonidos.hojaSound();
                }
                contador++;
                System.out.println("Contador+: " + contador);
                if (pref.getInteger("comic") == 2) {
                    if (contador >= 7) {
                        principal.setScreen(new Sett(principal));
                    }
                }
                if (pref.getInteger("comic") == 1) {
                    if (contador == 2){
                        pref.putInteger("nivel", 1);
                        pref.flush();
                    }
                    if (contador >= 7) {
                        principal.setScreen(new NivelTutorial(principal));
                        this.dispose();
                    }
                }
            }
            if (verificarBoton(x, y, back)) {

                //cambiar a pantalla
                Preferences pref = Gdx.app.getPreferences("Preferencias");
                fondo.setTextura(comic[contador - 1]);
                if (contador > 0){
                if (pref.getBoolean("boton") == true ) {
                    Sonidos.hojaSound();
                }
                }
                contador--;
                //System.out.println("Contador-: " + contador);
                if(contador <= 0){
                    if(pref.getInteger("comic") == 1) {
                        principal.setScreen(new PantallaMenu(principal, huesos));
                    }
                    if (pref.getInteger("comic") == 2){
                        principal.setScreen(new Sett(principal));
                    }
                    this.dispose();
                    if (pref.getBoolean("musica")) {
                        Sonidos.reproducirMusicaFondo();
                    }
                }
            }

            if(verificarBoton(x,y,skip)){
                Preferences pref = Gdx.app.getPreferences("Preferencias");

                if (pref.getBoolean("boton",true) == true) {
                    Sonidos.reproducirBoton();
                }
                if (pref.getInteger("comic") == 2){
                    principal.setScreen(new Sett(principal));
                }
                if (pref.getInteger("comic") == 1) {
                    pref.putInteger("nivel", 1);
                    pref.flush();
                    this.principal.setScreen(new NivelTutorial(principal));
                    this.dispose();
                }
            }

        }
    }

    @Override
    public void resize(int width, int height) {vista.update(width,height);
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
        texturaFondo.dispose();
        texturaSkip.dispose();
        texturaBack.dispose();
        texturaNext.dispose();

    }
}