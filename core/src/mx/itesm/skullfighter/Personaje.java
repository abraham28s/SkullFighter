package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by abrahamsoto on 18/02/16.
 */
public class Personaje {
    private Sprite sprite;

    private float alturaActual;
    private Estado estado;
    private float alturaMax,alturaInicial;
    private float timerAnimacion;
    private com.badlogic.gdx.graphics.g2d.Animation animacion;


    public Personaje(Texture textura){
        /*TextureRegion[][] texturaPersonaje =  new TextureRegion(textura).split(16,32);
        animacion = new Animation(0.25f,texturaPersonaje[0][3],texturaPersonaje[0][2],texturaPersonaje[0][1] );
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;
        sprite = new Sprite(texturaPersonaje[0][0]);*/


        sprite = new Sprite(textura);
        sprite.setAlpha(1f);
        this.alturaInicial = sprite.getY();
        this.alturaMax = this.alturaInicial+70;
        estado = Estado.NORMAL;

    }

    public void setSprite(Texture textura){
        sprite = new Sprite(textura);

    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void setPosicion(float x, float y) {
        sprite.setPosition(x, y);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void actualizar() {
        switch (estado) {

            case BAJANDO:
                alturaActual -= 5;
                this.setPosicion(this.sprite.getX(),this.sprite.getY()-5);
                if (alturaActual <= alturaInicial) {
                    alturaActual = alturaInicial;
                    estado = Estado.NORMAL;



                }
                break;
            case NORMAL:


                break;

            case SUBIENDO:
                alturaActual += 5;
                this.setPosicion(this.sprite.getX(),this.sprite.getY()+5);
                if (alturaActual >= alturaMax) {
                    alturaActual = alturaMax;
                    estado = Estado.BAJANDO;

                }
                break;

        }

    }

    public void movimientoBrin() {
        if(this.estado != Estado.SUBIENDO){
            this.estado = Estado.SUBIENDO;


        }
    }


    public Estado getEstado() {
        return estado;
    }

    public enum Estado{
        SUBIENDO,
        BAJANDO,
        NORMAL

    }
}
