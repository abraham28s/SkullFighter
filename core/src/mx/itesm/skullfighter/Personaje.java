package mx.itesm.skullfighter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.lwjgl.Sys;

/**
 * Created by abrahamsoto on 18/02/16.
 */
public class Personaje {
    private Sprite sprite;

    private float alturaActual;
    private EstadoBrinco estado;
    private EstadoMov estadoMov;
    private EstadoAtacando estadoAca;
    private float alturaMax,alturaInicial;
    private float timerAnimacion;
    private com.badlogic.gdx.graphics.g2d.Animation animacion;
    private String vista = "der";
    private boolean atacandoWe = false;
    private boolean atacandoPu = false;
    private Texture[] texturaMovDer;
    private Texture[] texturaMovIzq;
    private int variableMovimiento = 0;
    private NivelUno pantalla;


    public Personaje(Texture[] texturaMovDer,Texture[] texturaMovIzq,String tipo,NivelUno pantalla){
        this.texturaMovDer = texturaMovDer;
        this.texturaMovIzq = texturaMovIzq;
        this.pantalla = pantalla;
        if(tipo.equals("jugador"))sprite = new Sprite(texturaMovDer[0]);
        else if (tipo.equals("enemigo"))sprite = new Sprite(texturaMovIzq[0]);

        sprite.setAlpha(1f);
        this.alturaInicial = sprite.getY();
        this.alturaMax = this.alturaInicial+270;
        estado = EstadoBrinco.NORMAL;
        estadoMov = EstadoMov.QUIETO;
        estadoAca = EstadoAtacando.NORMAL;

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
                alturaActual -= 12.5;
                this.setPosicion(this.sprite.getX(),this.sprite.getY()-12);
                if (alturaActual <= alturaInicial) {
                    alturaActual = alturaInicial;
                    estado = EstadoBrinco.NORMAL;
                }
                break;
            case NORMAL:
                break;

            case SUBIENDO:
                alturaActual += 12.5;
                this.setPosicion(this.sprite.getX(),this.sprite.getY()+12);
                if (alturaActual >= alturaMax) {
                    alturaActual = alturaMax;
                    estado = EstadoBrinco.BAJANDO;

                }
                break;




        }

        switch (estadoMov){
            case DERECHA:
                if(this.equals(pantalla.jugador)){
                    if(this.pantalla.verificarBordes(this,pantalla.enemigo)){
                        movimiento("der");
                        vista = "der";
                    }
                }else{
                    if(this.pantalla.verificarBordes(this,pantalla.jugador)){
                        movimiento("der");
                        vista = "der";
                    }

                }
                //System.out.println("movder");
                break;
            case IZQUIERDA:
                if(this.equals(pantalla.jugador)){
                    if(this.pantalla.verificarBordes(this,pantalla.enemigo)){
                        movimiento("izq");
                        vista = "izq";
                    }
                }
                else{
                    if(this.pantalla.verificarBordes(this,pantalla.jugador)) {
                        movimiento("izq");
                        vista = "izq";
                    }
                }
                //System.out.println("movizq");
                break;
            case QUIETO:
                break;
        }
        switch (estadoAca){
            case NORMAL:
                break;
            case WEAPON:
                ataqueArma(vista);
                break;
            case PUNO:
                break;
            case TERMINOPUNO:
                break;
            case TERMINOWEAPON:
                break;
        }

    }

    private void ataqueArma(String vista) {
        float x = this.getSprite().getX();
        float y = this.getSprite().getY();
        if(vista.equals("der")){
            this.estadoAca = EstadoAtacando.WEAPON;

        }else if(vista.equals("izq")){

        }
    }

    public void movimiento(String direccion) {
        float x = this.getSprite().getX();
        float y = this.getSprite().getY();

            if (direccion.equals("der")) {
                this.estadoMov = EstadoMov.DERECHA;
                //System.out.println("derechaaaa");
                if (variableMovimiento % 4 == 0)this.setSprite(texturaMovDer[variableMovimiento % 3]);
                this.getSprite().setPosition(x+5,y);

            } else if (direccion.equals("izq")) {
                this.estadoMov = EstadoMov.IZQUIERDA;
                if (variableMovimiento % 4 == 0)this.setSprite(texturaMovIzq[variableMovimiento % 3]);
                this.getSprite().setPosition(x-5,y);
            }


        variableMovimiento++;

        if(variableMovimiento>50)variableMovimiento=0;


    }

    public void movimientoBrin() {
        if(this.estado != EstadoBrinco.SUBIENDO){
            this.estado = EstadoBrinco.SUBIENDO;


        }
    }


    public EstadoBrinco getEstado() {
        return estado;
    }
    public void setEstadoMov(EstadoMov estado){this.estadoMov = estado;}

    public void setVista(String vista) {
        this.vista = vista;
    }

    public String getVista() {
        return vista;
    }

    public boolean getAtacandoWe() {
        return atacandoWe;
    }

    public void setAtacandoWe(boolean atacandoWe) {
        this.atacandoWe = atacandoWe;
    }

    public void setAtacandoPu(boolean atacandoPu) {
        this.atacandoPu = atacandoPu;
    }

    public boolean getAtacandoPu() {
        return atacandoPu;
    }

    public EstadoMov getEstadoMov() {
        return estadoMov;
    }


    public enum EstadoBrinco {
        SUBIENDO,
        BAJANDO,
        NORMAL,


    }

    public enum EstadoMov{
        DERECHA,
        IZQUIERDA,
        QUIETO
    }

    public enum EstadoAtacando {
        WEAPON,TERMINOWEAPON,TERMINOPUNO,
        PUNO,
        NORMAL
    }
}
