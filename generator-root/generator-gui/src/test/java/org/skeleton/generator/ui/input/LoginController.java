package org.skeleton.generator.ui.input;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {
	@FXML
	private Text loginResult;
	
	@FXML
	private TextField loginField;
	
	@FXML
	private TextField passwordField;

	@FXML
	protected void handleLogin(ActionEvent event) {

		if (loginField.getText().equals("test") && passwordField.getText().equals("test")) {
			loginResult.setText("Ouh yeah !");
		} else {
			loginResult.setText("Nein Nein Nein !");
		}
	}
}
