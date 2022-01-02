package com.colliu.colliu.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Controller of ErrorPage.fxml
 * Displays an error message to the user, to inform that something went wrong.
 * Main purpose is to allow setting text and closing stage.
 */
public class ErrorController {

  @FXML
  private Button btnClose;

  @FXML
  private TextArea txaErrorMessage;

  @FXML
  private void closeThisWindow() {
    // When pressed close the stage for this window.
    ((Stage) btnClose.getScene().getWindow()).close();
  }

  public void setErrorText(String error) {
    // Will be called when loaded, sets the error message displayed to the user.
    txaErrorMessage.setText(error);
  }

}
