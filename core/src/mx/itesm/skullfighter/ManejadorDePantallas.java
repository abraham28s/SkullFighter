package mx.itesm.skullfighter;

import com.badlogic.gdx.Game;

/**
 * Created by abrahamsoto on 16/02/16.
 */
public class ManejadorDePantallas {


    private static ManejadorDePantallas instance;


    private Game game;

    // Singleton: private constructor
    private ManejadorDePantallas() {
        super();
    }

    // Singleton: retrieve instance
    public static ManejadorDePantallas getInstance() {
        if (instance == null) {
            instance = new ManejadorDePantallas();
        }
        return instance;
    }

    // Initialization with the game class
    public void initialize(Game game) {
        this.game = game;
    }
/*
    // Show in the game the screen which enum type is received
    public void showScreen(ScreenEnum screenEnum, Object... params) {

        // Get current screen to dispose it
        Screen currentScreen = game.getScreen();

        // Show new screen
        AbstractScreen newScreen = screenEnum.getScreen(params);
        newScreen.buildStage();
        game.setScreen(newScreen);

        // Dispose previous screen
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }*/
}
