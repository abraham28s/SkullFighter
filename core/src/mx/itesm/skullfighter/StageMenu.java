package mx.itesm.skullfighter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by abrahamsoto on 24/02/16.
 */
public class StageMenu extends Stage {


    private OrthographicCamera camera;

    public StageMenu() {

        setupCamera();
    }

    private void setupCamera() {
        camera = new OrthographicCamera(1280,720);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }
}