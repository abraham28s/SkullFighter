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
import java.util.Random;

/**
 * Created by Fer on 08/03/2016.
 */
public class Costumize implements Screen {

    private final Principal Principal;
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    //Fondo
    private Fondo fondo, fondo1;
    private Texture texturaFondo, texturaFondo2;

    //Botoones
    private Boton btnBack;

    private Boton btnTitulo;
    private Boton labelWeapon, labelClothes;

    private Boton customs, weapons, armaS, army;

    private Boton clothes, ropas;

    //Texturas
    private Texture texturaBack;

    private Texture texturabtnTitulo;
    private Texture texturaLabelW, texturaLabelC;

    private Texture texturaCustom;

    private Texture texturaWeapon;
    private Texture texturaArma;
    private Texture texturaArmy;

    private Texture texturaCloth, texturaRopas;
    private Texture Mazo, Oz;
    private Texture Negro, Verde;

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

        //Fondo
        fondo = new Fondo(texturaFondo);
        fondo1 = new Fondo(texturaFondo2);
        fondo1.getSprite().setX(fondo.getSprite().getWidth());

        //Titulos
        btnTitulo =new Boton(texturabtnTitulo);
        btnTitulo.setPosicion(760, 600);

        labelClothes = new Boton(texturaLabelC);
        labelClothes.setPosicion(645,170);

        labelWeapon = new Boton(texturaLabelW);
        labelWeapon.setPosicion(660,450);

        //Personaje
        customs = new Boton(texturaCustom);
        customs.setPosicion(185, 100);

        //Prenda negra
        clothes = new Boton(texturaCloth);
        clothes.setPosicion(875, 120);

        //Prenda verde
        ropas= new Boton (texturaRopas);
        ropas.setPosicion(1065,120);

        //Mazo
        weapons = new Boton(texturaWeapon);
        weapons.setPosicion(1065, 390);

        //Oz
        armaS = new Boton(texturaArma);
        armaS.setPosicion(900,390);

        //Arma general
        army = new Boton(texturaArmy);
        army.setPosicion(0,0);

        btnBack = new Boton(texturaBack);
        btnBack.setPosicion(40, 40);

        batch = new SpriteBatch();
    }

    private void cargarTexturas() {

        texturaFondo=new Texture(Gdx.files.internal("PantallaVacia.png"));
        texturaFondo2 = new Texture(Gdx.files.internal("PantallaVacia.png"));
        texturabtnTitulo = new Texture(Gdx.files.internal("BotonCustomize.png"));
        texturaBack = new Texture((Gdx.files.internal("BackGame.png")));
        texturaLabelC = new Texture((Gdx.files.internal("clothes.png")));
        texturaLabelW = new Texture((Gdx.files.internal("weapon.png")));

        texturaCustom = new Texture((Gdx.files.internal("SkullSolo1.png")));

        //Armas
        texturaWeapon = new Texture((Gdx.files.internal("mazo.png")));
        texturaArma = new Texture(Gdx.files.internal("Oz.png"));
        texturaArmy = new Texture(Gdx.files.internal("white.png"));
        Mazo = new Texture((Gdx.files.internal("MazoEquipado.png")));
        Oz = new Texture((Gdx.files.internal("OzEquipada.png")));

        //Ropa
        texturaCloth = new Texture((Gdx.files.internal("Ropa.png")));
        texturaRopas = new Texture((Gdx.files.internal("Ropa2.png")));
        Negro = new Texture((Gdx.files.internal("SkullSolo1.png")));
        Verde =  new Texture((Gdx.files.internal("SkullSolo2.png")));
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
        btnBack.render(batch);
        labelWeapon.render(batch);
        labelClothes.render(batch);

        weapons.render(batch);
        customs.render(batch);
        clothes.render(batch);
        ropas.render(batch);
        armaS.render(batch);
        army.render(batch);

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
                Gdx.app.log("leerEntrada","TAp sobre BACK");
                //cambiar a pantalla
                Sonidos.reproducirBoton();
                Principal.setScreen(new PantallaMenu(Principal));
            }
            if(verificarBoton(x,y, weapons)){
                Gdx.app.log("leerEntrada", "TAp sobre MAZO");
                Sonidos.reproducirBoton();
                //cambiar textura
                army.setTextura(Mazo);
                army.setPosicion(412, 236);

            }
            if(verificarBoton(x,y,armaS)){
                Gdx.app.log("leerEntrada", "Tap sobre OZ");
                Sonidos.reproducirBoton();
                //cambiar textura
                army.setTextura(Oz);
                army.setPosicion(432,136);
            }
            if(verificarBoton(x,y,labelWeapon)){
                Sonidos.reproducirBoton();
                //Limpiar
                army.setTextura(texturaArmy);
            }
            if(verificarBoton(x,y, clothes)){
                Sonidos.reproducirBoton();
                Gdx.app.log("leerEntrada", "TAp sobre NEGRO");
                //cambiar textura
                customs.setTextura(Negro);
                customs.setPosicion(185, 100);
            }
            if(verificarBoton(x,y,ropas)){
                Sonidos.reproducirBoton();
                Gdx.app.log("leerEntrada", "Tap sobre VERDE");
                customs.setTextura(Verde);
                customs.setPosicion(185,100);
            }
        }
    }

    private boolean verificarBoton(float x, float y, Boton btn) {
        Sprite sprite = btn.getSprite();

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
