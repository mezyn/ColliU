package com.colliu.colliu.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ForgotPassword {

  @FXML
  private Button continueButton;

  @FXML
  private TextField fp_email;

  @FXML
  private Label fp_warningLabel;

  @FXML
  private Button signupButton;

  @FXML
  void onContinueClick(ActionEvent event) throws IOException {
    if (fp_email.getText().isBlank()) {
      fp_warningLabel.setText("Email address cannot be empty.");
    }else if (!fp_email.getText().endsWith("gu.se")) {
      fp_warningLabel.setText("Email address must be associated with GU.");
    }else {
      // Code in changing text to new Password set instead of showing a new window
      // WIlliam.
    }
  }

  @FXML
  void onSignUpClick(ActionEvent event) {

  }

}
