package com.colliu.colliu.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ErrorController {

  @FXML
  private Button btnClose;

  @FXML
  private TextArea txaErrorMessage;

  @FXML
  void closeThisWIndow(ActionEvent event) {
    ((Stage) btnClose.getScene().getWindow()).close();
  }

  public void setError(String error) {
    txaErrorMessage.setText(error);
  }

}
