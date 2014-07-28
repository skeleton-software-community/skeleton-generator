package org.sklsft.generator.ui.input;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TestLogin extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Test");
		GridPane gridPane = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/input/test-login.fxml"));
		Scene scene = new Scene(gridPane);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
        Application.launch(args);
    }
}
