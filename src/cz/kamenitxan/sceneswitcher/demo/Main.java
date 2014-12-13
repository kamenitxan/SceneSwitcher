package cz.kamenitxan.sceneswitcher.demo;

import cz.kamenitxan.sceneswitcher.SceneSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main application class.
 */
public class Main extends Application {
    private final SceneSwitcher sceneSwitcher = SceneSwitcher.getInstance();

    @Override
    public void start(Stage stage) throws Exception{
        sceneSwitcher.addScene("a", "a.fxml");
        sceneSwitcher.addScene("b", "b.fxml");

        stage.setTitle("Vista Viewer");
        stage.setScene(sceneSwitcher.createMainScene(this.getClass()));


        sceneSwitcher.loadScene("a");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}