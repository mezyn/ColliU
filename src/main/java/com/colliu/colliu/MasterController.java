package com.colliu.colliu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.User;
import user.UserMethods;


public class MasterController {

  UserMethods method = new UserMethods();

  public Master master;

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

  public String email(){
    return tfEmail.getText();
  }

  @FXML
  private TextField tfEmail;

  @FXML
  private Button btnCreateAccount;

  @FXML
  void clickCreateAccount(ActionEvent event) throws Exception{
    if (getTfEmail().contains("student")) {
      master.showWindow("registration-page.fxml");
    }
    else if(getTfEmail().contains("gu.se")){
      //new scene
      master.showWindow("staff-registration.fxml");
    }
  }

  @FXML
  private Button btnRegister;

  @FXML
  private TextField tfConfirmPassword;


  @FXML
  private TextField tfFirstName;

  @FXML
  private TextField tfLastName;

  @FXML
  private TextField tfPassword;

  @FXML
  private TextField tfGraduationYear;

  @FXML
  private TextField tfProgram;

  public String getTfEmail() {
    return tfEmail.getText();
  }

  @FXML
  void clickRegister(ActionEvent event) throws Exception{
    method.createStudent(tfEmail.getText(), tfPassword.getText(), tfFirstName.getText(), tfLastName.getText(), Integer.parseInt(tfGraduationYear.getText()), tfProgram.getText());
  }

  @FXML
  private Button btnCreateStaff;

  @FXML
  private PasswordField tfStaffConfirmPassword;

  @FXML
  private TextField tfStaffDepartment;

  @FXML
  private TextField tfStaffFirstName;

  @FXML
  private TextField tfStaffLastName;

  @FXML
  private PasswordField tfStaffPassword;

  @FXML
  private TextField tfStaffTitle;
}


