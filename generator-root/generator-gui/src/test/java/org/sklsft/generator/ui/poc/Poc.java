package org.sklsft.generator.ui.poc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Poc extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Test");
		AnchorPane anchorPane = (AnchorPane)FXMLLoader.load(getClass().getResource("/fxml/poc/poc.fxml"));
		Scene scene = new Scene(anchorPane);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
        Application.launch(args);
    }
}