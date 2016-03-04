package mx.itesm.skullfighter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by abrahamsoto on 24/02/16.
 */
public class PantallaMenu implements Screen {

    private final Principal principal;
    private Stage stage;

    private SpriteBatch batch;

    private Texture blockTexture;
    private Sprite blockSprite;

    private Fondo fondo;
    private Texture texturaFondo;

    //botones
    private ImageButton btnStory;
    private Texture texturaBtnStory;

    private BotonMenu btnVs;
    private Texture texturaBtnVs;

    private BotonMenu btnCustom;
    private Texture texturaBtnCustom;

    private BotonMenu btnSettings;
    private Texture texturaBtnSettings;

    public PantallaMenu(Principal principal) {

        this.principal = principal;
        stage = new StageMenu();
    }

    public void cargarTexturas() {
        texturaFondo = new Texture(Gdx.files.internal("MainMenuSolo.jpg"));
        texturaBtnStory = new Texture(Gdx.files.internal("BotonStory.png"));
        texturaBtnVs = new Texture(Gdx.files.internal("BotonVersus.png"));
        texturaBtnCustom = new Texture(Gdx.files.internal("BotonCustomize.png"));
        texturaBtnSettings = new Texture(Gdx.files.internal("BotonSettings.png"));
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        blockTexture = new Texture(Gdx.files.internal("block.png"));
        blockSprite = new Sprite(blockTexture);
        //Set position to centre of the screen
        blockSprite.setPosition(Gdx.graphics.getWidth() / 2 - blockSprite.getWidth() / 2, Gdx.graphics.getHeight() / 2 - blockSprite.getHeight() / 2);
        cargarTexturas();
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        //btnStory = new ImageButton();




        stage.addActor(btnStory);


    }



    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        batch.begin();


        batch.end();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
