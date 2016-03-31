package mx.itesm.skullfighter;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by abrahamsoto on 18/02/16.
 */
public abstract class PantallaAbstracta {


    boolean verificarBoton(float x, float y,Boton btn) {
        Sprite sprite = btn.getSprite();
        return x>=sprite.getX() && x<=sprite.getX()+sprite.getWidth()
                && y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }
    abstract void leerEntrada();
    abstract void cargarTexturas();
    abstract void crearYPosBotones();
    abstract void setYUpgradeCamara();

}
