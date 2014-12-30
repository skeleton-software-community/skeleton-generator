package org.sklsft.generator.ui.poc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PocController {

	@FXML
	private Text result;

	@FXML
	protected void handleClick(ActionEvent event) {
		
		result.setText("OK");
		
	}
}
