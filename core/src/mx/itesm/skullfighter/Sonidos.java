package mx.itesm.skullfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

/** * Created by Jorge Alvarado */
public class Sonidos {

    public static Music musicaFondo;

    public static void cargaAudio(){
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


}