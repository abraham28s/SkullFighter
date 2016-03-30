package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** * Created by abrahamsoto on 16/02/16. */
public class Boton {
    private Sprite sprite;

    public Boton(Texture textura){
        sprite = new Sprite(textura);
        sprite.setAlpha(1f);
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

    public Boolean estaTocado() {
        float x = Gdx.input.getX();
        float y = Gdx.input.getY();
        return x>=sprite.getX() && x<=sprite.getX()+sprite.getWidth()
                && y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    public void setTextura(Texture textura) {
        this.sprite = new Sprite(textura);
        sprite.setAlpha(1f);
    }
}