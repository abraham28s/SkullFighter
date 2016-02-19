package mx.itesm.skullfighter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by abrahamsoto on 18/02/16.
 */
public class Personaje {
    private Sprite sprite;

    public Personaje(Texture textura){
        sprite = new Sprite(textura);
        sprite.setAlpha(1f);

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
}
