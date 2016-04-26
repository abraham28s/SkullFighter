package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/** * Created by Jorge Alvarado */
public class Sonidos {

    public static Music musicaFondo;
    public static Sound efectoBoton;
    public static Sound efectoGolpear;
    public static Sound efectoSaltar;
    public static Sound efectoCuchillo;
    public static Sound hojas;

    public static void cargaAudio(){
        efectoBoton = Gdx.audio.newSound(Gdx.files.internal("botonSound.mp3"));
        musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("WarDrum.mp3"));
        musicaFondo.setLooping(true);
    }

    public static void reproducirMusicaFondo(){
        musicaFondo.play();
        Sett.TextureMusic = new Texture(Gdx.files.internal("MusicaOn.png"));

    }


    public static void pausarMusicaFondo(){
        musicaFondo.pause();
        Sett.TextureMusic = new Texture(Gdx.files.internal("MusicaOff.png"));

    }

    public static void quitarMusicaFondo(){
        musicaFondo.stop();
    }

    public static void reproducirBoton(){
        efectoBoton.play();
    }

    public static void cargarEfectos(){
        efectoGolpear = Gdx.audio.newSound(Gdx.files.internal("golpeo.mp3"));
        efectoSaltar = Gdx.audio.newSound(Gdx.files.internal("Saltar.mp3"));
        efectoCuchillo = Gdx.audio.newSound(Gdx.files.internal("Arma.mp3"));
        hojas = Gdx.audio.newSound(Gdx.files.internal("hoja.mp3"));
    }

    public static void golpearSound(){
        efectoGolpear.play();
    }

    public static void saltarSound(){
        efectoSaltar.play();
    }
    public static void cuchilloSound(){
        efectoCuchillo.play();
    }
    public static void hojaSound(){        hojas.play();    }


}