package com.colliu.colliu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MasterController {
  @FXML
  private Label welcomeText;

  @FXML
  protected void onHelloButtonClick() {
    welcomeText.setText("Welcome to JavaFX Application!");
  }
}