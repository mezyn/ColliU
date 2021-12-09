package com.colliu.colliu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MasterController {

  Stage stage;

  @FXML
  private Label welcomeText;

  @FXML
  private Button forgotpassword;

  @FXML
  private TextField guEmail;

  @FXML
  private Button login;

  @FXML
  private TextField password;

  @FXML
  private Button registeraccount;


  @FXML
  protected void onHelloButtonClick(ActionEvent Event) throws IOException {
    closeWindow(Event);
    showWindow("homepage.fxml");
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

  @FXML
  private void closeWindow(ActionEvent action) {
    ((Node)(action.getSource())).getScene().getWindow().hide();
  }

  private void showWindow(String fileName) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Master.class.getResource((fileName.endsWith(".fxml") ? fileName : fileName + ".fxml")));
    Scene scene = new Scene(fxmlLoader.load());
    Stage stage = new Stage();
    stage.setTitle("ColliU - " + fileName);
    stage.setScene(scene);
    stage.show();
    //stage.initModality(Modality.APPLICATION_MODAL);
    //stage.initStyle(StageStyle.UNDECORATED);
    //stage.setTitle("ABC");
    //stage.setScene(new Scene(root1));
    //stage.show();
  }
}

