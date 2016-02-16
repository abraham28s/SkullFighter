package mx.itesm.skullfighter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by abrahamsoto on 16/02/16.
 */
public class BotonMenu {
    private Sprite sprite;

    public BotonMenu(Texture textura){
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
}
