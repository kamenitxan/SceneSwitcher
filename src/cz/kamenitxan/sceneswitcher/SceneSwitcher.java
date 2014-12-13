package cz.kamenitxan.sceneswitcher;

import cz.kamenitxan.sceneswitcher.demo.Main;
import javafx.fxml.FXMLLoader;
import cz.kamenitxan.sceneswitcher.lang.EncodedControl;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Utility class for controlling navigation between vistas.
 *
 * All methods on the navigator are static to facilitate
 * simple access from anywhere in the application.
 */
public class SceneSwitcher {
	private Class<?> mainClass;
	private static SceneSwitcher singleton = new SceneSwitcher();
	private SceneSwitcher() {}
	public static SceneSwitcher getInstance() {
		return singleton;
	}
	/**
	 * Convenience constants for fxml layouts managed by the navigator.
	 */
	private final String MAIN = "main.fxml";
	private Map<String, String> scenes = new HashMap<>();
	private String lastFxml = "";
	private String lastLocale = "";

	/**
	 * The main application layout controller.
	 */
	private static MainController mainController;

	/**
	 * Creates the main application scene.
	 * @param mainClass class used as relative root for finding scenes. Default path is "mainClass/gui/scenes/.
	 *                  If you need to change this, overide loadScene().
	 * @return the created scene.
	 */
	public Scene createMainScene(Class<?> mainClass) throws IOException {
		this.mainClass = mainClass;
		return new Scene(loadMainPane());
	}

	public void addScene(String sceneName, String fileName) {
		scenes.put(sceneName, fileName);
	}

	/**
	 * Loads the main fxml layout.
	 * Sets up the vista switching VistaNavigator.
	 * Loads the first vista into the fxml layout.
	 *
	 * @return the loaded pane.
	 * @throws IOException if the pane could not be loaded.
	 */
	private Pane loadMainPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();

		Pane mainPane = loader.load(
				SceneSwitcher.class.getResourceAsStream(MAIN)
		);

		mainController = loader.getController();

		return mainPane;
	}


	/**
	 * Loads the scene specified by the fxml file into the
	 * vistaHolder pane of the main application layout.
	 * <p>
	 * Previously loaded scene for the same fxml file are not cached.
	 * The fxml is loaded anew and a new vista node hierarchy generated
	 * every time this method is invoked.
	 * <p>
	 * A more sophisticated load function could potentially add some
	 * enhancements or optimizations, for example:
	 * cache FXMLLoaders
	 * cache loaded scene nodes, so they can be recalled or reused
	 * allow a user to specify scene node reuse or new creation
	 * allow back and forward history like a browser
	 *
	 * @param scene name to be loaded.
	 */
	public void loadScene(String scene) {
		loadScene(scene, lastLocale);
	}

	/**
	 * Loads scene with specified language.
	 * @param scene name to be loaded.
	 * @param language to be loaded from properties file.
	 */
	public void loadScene(String scene, String language) {
		lastFxml = scene;
		try {
				URL url = mainClass.getResource("gui/scenes/" + getScene(scene));
				mainController.setScene(FXMLLoader.load(url, getLocalization(language)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String getScene(String sceneName) {
		String scene = scenes.get(sceneName);
		if (scene == null) {
			throw new NoSuchElementException("Scene " + sceneName + " not found");
		} else {
			return scene;
		}
	}
	private ResourceBundle getLocalization(String language) {
		if (language.equals("")) {
			return null;
		} else {
			Locale locale = new Locale(language);

			return ResourceBundle.getBundle(Main.class.getPackage().getName() + ".gui.lang.strings",
											locale,
											new EncodedControl("UTF8")
			);
		}
	}

	// TODO: default language from system
	/**
	 * Sets used locale in SceneSwither and calls loadScene()
	 * @param language language code
	 */
	public void setLocale(String language) {
		lastLocale = language;
		loadScene(lastFxml, language);
	}
}