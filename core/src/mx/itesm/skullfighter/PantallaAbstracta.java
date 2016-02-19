package mx.itesm.skullfighter;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by abrahamsoto on 18/02/16.
 */
public interface PantallaAbstracta {


    boolean verificarBoton(float x, float y,BotonMenu btn);
    void leerEntrada();
    void cargarTexturas();
}
