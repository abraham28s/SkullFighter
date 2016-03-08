package mx.itesm.skullfighter;

/**
 * Created by abrahamsoto on 18/02/16.
 */
public interface PantallaAbstracta {


    boolean verificarBoton(float x, float y,Boton btn);
    void leerEntrada();
    void cargarTexturas();
    void crearYPosBotones();
    void setYUpgradeCamara();
}
