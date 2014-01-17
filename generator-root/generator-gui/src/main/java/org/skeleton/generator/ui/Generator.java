package org.skeleton.generator.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Generator extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Skeleton Generator");
		BorderPane borderPane = (BorderPane)FXMLLoader.load(getClass().getResource("/fxml/borderPane.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
        Application.launch(args);
    }

}
