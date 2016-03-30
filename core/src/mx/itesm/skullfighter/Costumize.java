package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Fer on 08/03/2016.
 */
public class Costumize implements Screen {

    private final Principal Principal;
    private OrthographicCamera camara;
    private Viewport vista;

    private SpriteBatch batch;
    private Fondo fondo;
    private Texture texturaFondo;

    private Texture texturaCloth;
    private Texture texturaWeapon;
    private Texture texturaCustom;

    private Texture texturaFondo2;
    private Fondo fondo1;

    private Boton btnTitulo;
    private Texture texturabtnTitulo;

    //Botoones
    private Boton btnBack;
    private Texture texturaBack;

    private Boton clothes;
    private Boton weapons;
    private Boton customs;

    public Costumize(Principal principal) {
        this.Principal = principal;
    }

    @Override
    public void show() {

        camara = new OrthographicCamera(mx.itesm.skullfighter.Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO);
        camara.position.set(Principal.ANCHO_MUNDO / 2, Principal.ALTO_MUNDO / 2, 0);
        camara.update();
        vista = new StretchViewport(Principal.ANCHO_MUNDO, Principal.ALTO_MUNDO,camara);

        cargarTexturas(); //cargar texturas
        fondo = new Fondo(texturaFondo);

        //botones nombres
        fondo1 = new Fondo(texturaFondo2);
        fondo1.getSprite().setX(fondo.getSprite().getWidth());

        clothes = new Boton(texturaCloth);
        clothes.setPosicion(805, 140);
        weapons = new Boton(texturaWeapon);
        weapons.setPosicion(805, 400);
        customs = new Boton(texturaCustom);
        customs.setPosicion(130, 50);
        btnTitulo =new Boton(texturabtnTitulo);
        btnTitulo.setPosicion(700,575);



        btnBack = new Boton(texturaBack);
        btnBack.setPosicion(40, 40);


        batch = new SpriteBatch();
    }

    private void cargarTexturas() {


        texturaFondo=new Texture(Gdx.files.internal("PantallaVacia.png"));
        texturaFondo2 = new Texture(Gdx.files.internal("PantallaVacia.png"));
        texturaBack = new Texture((Gdx.files.internal("BackGame.png")));
        texturaCloth = new Texture((Gdx.files.internal("Cloth1Customize.png")));
        //texturaWeapon = new Texture((Gdx.files.internal("Arma1Customize.png")));
        texturaWeapon = new Texture((Gdx.files.internal("BotonWeapon.png")));
        texturaCustom = new Texture((Gdx.files.internal("Skull_Oz2.png")));
        texturabtnTitulo = new Texture(Gdx.files.internal("BotonCustomize.png"));


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        leerEntrada();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        fondo.render(batch);
        fondo1.render(batch);
        btnTitulo.render(batch);
        weapons.render(batch);
        customs.render(batch);
        clothes.render(batch);
        btnBack.render(batch);
        batch.end();
        actualizarFondo();
    }
    private void actualizarFondo() {
        fondo.getSprite().setX(fondo.getSprite().getX() - 1);
        fondo1.getSprite().setX(fondo1.getSprite().getX() - 1);

        if(fondo.getSprite().getX()+fondo.getSprite().getWidth() == 0){
            fondo.getSprite().setX(fondo1.getSprite().getWidth());
        }if(fondo1.getSprite().getX()+fondo1.getSprite().getWidth() == 0){
            fondo1.getSprite().setX(fondo.getSprite().getWidth());
        }
    }

    private void leerEntrada() {
        if(Gdx.input.justTouched()){//saber si el usuario toca
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camara.unproject(coordenadas);//TRaduce coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if(verificarBoton(x,y, btnBack)){
                Gdx.app.log("leerEntrada","TAp sobre back");
                //cambiar a pantalla de jugar
                Principal.setScreen(new PantallaMenu(Principal));
            }
            if(verificarBoton(x,y, weapons)){
                Gdx.app.log("leerEntrada","TAp sobre WEAPONS");
                //cambiar a pantalla de jugar
            }
            if(verificarBoton(x,y, clothes)){
                Gdx.app.log("leerEntrada","TAp sobre CLOTHES");
                //cambiar a pantalla de jugar
            }
        }
    }

    private boolean verificarBoton(float x, float y, Boton btn) {
        Sprite sprite = btnBack.getSprite();

        return x>sprite.getX() && x<=sprite.getX()+sprite.getWidth()
                && y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
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
