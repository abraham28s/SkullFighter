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
    private Texture[] texturaArmaDer;
    private Texture[] texturaArmaIzq;
    private Texture[] texturaGolpeDer;
    private Texture[] texturaGolpeIzq;

    private int variableMovimiento = 0;
    private NivelUno pantalla;
    private int variableWeapon = 1;


    public Personaje(Texture[] texturaMovDer,Texture[] texturaMovIzq,Texture[] texturaArmaDer,Texture[] texturaArmaIzq,
                     Texture[] texturaGolpeDer,Texture[] texturaGolpeIzq,String tipo,NivelUno pantalla){
        this.texturaMovDer = texturaMovDer;
        this.texturaMovIzq = texturaMovIzq;
        this.texturaArmaDer = texturaArmaDer;
        this.texturaArmaIzq = texturaArmaIzq;
        this.texturaGolpeDer = texturaGolpeDer;
        this.texturaGolpeIzq = texturaGolpeIzq;


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
                if(this.pantalla.verificarBordes(this)){
                movimiento("der");
                vista = "der";}
                //System.out.println("movder");
                break;
            case IZQUIERDA:
                if(this.pantalla.verificarBordes(this)){
                movimiento("izq");
                vista = "izq";}
                //System.out.println("movizq");
                break;
            case QUIETO:
                break;
        }
        switch (estadoAca){
            case NORMAL:
                break;
            case WEAPON:
                ataqueArma();
                break;
            case PUNO:
                break;
            case TERMINOPUNO:
                break;
            case TERMINOWEAPON:
                break;
        }

    }

    public void ataqueArma() {

        float x = this.getSprite().getX();
        float y = this.getSprite().getY();
        if(vista.equals("der")){
            this.estadoAca = EstadoAtacando.WEAPON;
            if(variableWeapon %5==0){
                System.out.println(variableWeapon/5);
                this.setSprite(texturaArmaDer[variableWeapon%5]);
                if(variableWeapon == 30){
                    this.estadoAca = EstadoAtacando.TERMINOWEAPON;
                    variableWeapon = 1;
                }
            }
        }else if(vista.equals("izq")){
            this.estadoAca = EstadoAtacando.WEAPON;
            if(variableWeapon %5==0){
                this.setSprite(texturaArmaIzq[variableWeapon%5]);
                if(variableWeapon == 30){
                    this.estadoAca = EstadoAtacando.TERMINOWEAPON;
                    variableWeapon = 1;
                }
            }
        }
        this.getSprite().setX(x);
        this.getSprite().setY(y);
        variableWeapon++;
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

    public void setEstadoAca(EstadoAtacando estadoAca) {
        this.estadoAca = estadoAca;
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
