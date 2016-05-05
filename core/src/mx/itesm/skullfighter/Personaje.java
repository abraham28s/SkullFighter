package mx.itesm.skullfighter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.lwjgl.Sys;

import java.util.Objects;

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

    private String vista;

    private Texture[] texturaMovDer;
    private Texture[] texturaMovIzq;
    private Texture[] texturaArmaDer;
    private Texture[] texturaArmaIzq;
    private Texture[] texturaGolpeDer;
    private Texture[] texturaGolpeIzq;

    private int variableMovimiento = 0;
    private NivelUno pantalla;

    private int variableWeapon1 = 0;
    private int variableWeapon2 = 0;
    private int variablePuno1 = 0;
    private int variablePuno2 = 0;


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
        if(this.equals(pantalla.jugador)){
            vista = "der";
        }else{
            vista = "izq";
        }

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


        float xp = pantalla.jugador.getSprite().getX();
        float wp = pantalla.jugador.getSprite().getWidth();
        float xe = pantalla.enemigo.getSprite().getX();
        float we = pantalla.enemigo.getSprite().getWidth();
        switch (estadoAca){
            case NORMAL:
                break;
            case WEAPON:

                ataqueArma();
                break;
            case PUNO:

                ataquePuno();
                break;
            case TERMINOPUNO:

                if((xp-(wp/4)-5<= xe + (we/4)+5 && xp>xe) || (xp+(wp/4)+5 >= xe-(xe/4) - 5) && xe>xp) {
                    if (this.equals(pantalla.jugador)) {


                        pantalla.actualizarVida(1, 'j');

                    } else if (this.equals(pantalla.enemigo)) {

                        pantalla.actualizarVida(1, 'e');

                    }
                }

                this.estadoAca = EstadoAtacando.NORMAL;
                break;
            case TERMINOWEAPON:

                if((xp-(wp/2)<= xe + (we/2) && xp>xe) || (xp+(wp/2) >= xe-(xe/2)) && xe>xp) {
                    if(this.equals(pantalla.jugador)){
                        pantalla.actualizarVida(2,'j');
                    }else if(this.equals(pantalla.enemigo)){
                        pantalla.actualizarVida(2, 'e');

                    }
                }
                this.estadoAca = EstadoAtacando.NORMAL;
                break;
        }

    }

    public void ataquePuno() {
        float x = this.getSprite().getX();
        float y = this.getSprite().getY();

        if(vista.equals("der")){
            this.estadoAca = EstadoAtacando.PUNO;
            if(variablePuno1 %5==0){

                this.setSprite(texturaGolpeDer[variablePuno2]);
                variablePuno2++;
                if(variablePuno2 > 3){
                    variablePuno2 = 0;
                    this.estadoAca = EstadoAtacando.TERMINOPUNO;
                }
            }
        }else if(vista.equals("izq")){
            this.estadoAca = EstadoAtacando.PUNO;
            if(variablePuno1 %5==0){
                this.setSprite(texturaGolpeIzq[variablePuno2]);
                variablePuno2++;
                if(variablePuno2 > 3){
                    variablePuno2 = 0;
                    this.estadoAca = EstadoAtacando.TERMINOPUNO;
                }
            }
        }
        this.getSprite().setX(x);
        this.getSprite().setY(y);
        variablePuno1++;
        if(variablePuno1>100)variablePuno1 = 0;
    }

    public void ataqueArma() {

        float x = this.getSprite().getX();
        float y = this.getSprite().getY();
        if(vista.equals("der")){
            this.estadoAca = EstadoAtacando.WEAPON;
            if(variableWeapon1 %5==0){

                this.setSprite(texturaArmaDer[variableWeapon2]);
                variableWeapon2++;
                if(variableWeapon2 > 4){
                    variableWeapon2 = 0;
                    this.estadoAca = EstadoAtacando.TERMINOWEAPON;
                }
            }
        }else if(vista.equals("izq")){
            this.estadoAca = EstadoAtacando.WEAPON;
            if(variableWeapon1 %5==0){
                this.setSprite(texturaArmaIzq[variableWeapon2]);
                variableWeapon2++;
                if(variableWeapon2 > 4){
                    variableWeapon2 = 0;
                    this.estadoAca = EstadoAtacando.TERMINOWEAPON;
                }
            }
        }
        this.getSprite().setX(x);
        this.getSprite().setY(y);
        variableWeapon1++;
        if(variableWeapon1>100)variableWeapon1 = 0;
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





    public EstadoMov getEstadoMov() {
        return estadoMov;
    }

    public void setEstadoAca(EstadoAtacando estadoAca) {
        this.estadoAca = estadoAca;
    }

    public EstadoAtacando getEstadoAca() {
        return estadoAca;
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