package mx.itesm.skullfighter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by abrahamsoto on 30/03/16.
 */
public class Componente {
    private Sprite sprite;

    public Componente(Texture textura){
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

    public void setTextura(Texture textura) {
        this.sprite = new Sprite(textura);
        sprite.setAlpha(1f);
    }


    public void setSprite(Texture sprite) {
        this.sprite = new Sprite(sprite);
    }
}
