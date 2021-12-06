package com.colliu.colliu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {
  @FXML
  private Label welcomeText;

  @FXML
  protected void onHelloButtonClick() {
    welcomeText.setText("Welcome to JavaFX Application!");
  }

  /*
  protected String onGUEmailClick() {
      String guEmail = UserInput.readInput();
      return guEmail;
    }
  protected void onPasswordClick() {
    String password = UserInput.readInput();

  }
  protected void onLogInClick() {

    if (UserMethods.checkExistingEmail() && UserMethods.validatePassword()) {


    }
  }*/

  @FXML
  void onLogInClick(ActionEvent event) {
//anything we code here for login should work
  }


  @FXML
  void onForgotPassword(ActionEvent event) {

  }

  @FXML
  void onGUEmail(ActionEvent event) {

  }

  @FXML
  void onPassword(ActionEvent event) {

  }

  @FXML
  void onRegisterAccount(ActionEvent event) {

  }
  }

