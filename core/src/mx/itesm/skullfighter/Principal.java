package mx.itesm.skullfighter;

/**
 * Created by abrahamsoto on 12/02/16.
 */
public class Principal {
    public static final float ANCHO_MUNDO = 1280;
    public static final float ALTO_MUNDO = 720;
    private String hola;

    @Override
    public void create () {
        setScreen(new PantallaMenu(this));
    }
}
