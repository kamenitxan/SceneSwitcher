package cz.kamenitxan.sceneswitcher;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Main controller class for the entire layout.
 */
public class MainController {

	/** Holder of a switchable vista. */
	@FXML
	private StackPane sceneHolder;
	@FXML
	private VBox vb;

	/**
	 * Replaces the vista displayed in the vista holder with a new vista.
	 *
	 * @param node the vista node to be swapped in.
	 */
	public void setScene(Node node) {
		//vb.getChildren().setAll(node);
		sceneHolder.getChildren().setAll(node);
		sceneHolder.setAlignment(node, Pos.TOP_LEFT);
	}

}
