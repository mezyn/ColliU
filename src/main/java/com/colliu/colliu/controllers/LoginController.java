package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import user.User;
import user.UserMethods;

import java.io.IOException;

public class LoginController {

  MasterController master;


  @FXML
  private Button forgotpassword;

  @FXML
  private TextField guEmail;

  @FXML
  private Button login;

  @FXML
  private PasswordField password;

  @FXML
  private Button newTeacher;

  @FXML
  private Button newStaff;

  @FXML
  private Label warningLabel;

  @FXML
  private Pane accountBannedPane;

  @FXML
  private Button btnAccountBannedOK;

  @FXML
  void onForgotPasswordClick(ActionEvent event) throws Exception {
    master.showForgottenPassword();
  }

  @FXML
  void onButtonPressAccountBannedOK(ActionEvent event) throws Exception {
    accountBannedPane.setVisible(false);
  }

  /* Upon login button click, the system checks if the input email is already registered in the system as well as
if the input password matches the email address. If both are correct, the user succeeds to log in and will be sent
to the event page(homepage). Otherwise, the warning label shows which action the user shall take to successfully login. */

  @FXML
  void onLogInClick() throws Exception {
    if (guEmail.getText().isBlank()) {                              //checks if email is empty
      warningLabel.setText("Email address cannot be empty.");
    } else if (password.getText().isBlank()) {                      //check if password is empty
      warningLabel.setText("Password cannot be empty.");

    } else {
      if (master.userMethods.checkExistingEmail(guEmail.getText())) { //checks if email exists
        if (master.userMethods.validatePassword(password.getText(), guEmail.getText())) { //checks if email matches password
          if (master.userMethods.getAccountStatus(guEmail.getText())) {
            accountBannedPane.setVisible(true);
          } else {
            master.userMethods.setLoggedInUser(guEmail.getText());
            master.showEventPage();
          }
        } else {
          warningLabel.setText("Wrong password.");
        }
      } else {
        warningLabel.setText("Not existing email address.");
      }
    }
  }

  @FXML
  void registerStaff(ActionEvent event) throws Exception {
    master.showRegisterStudent();
  }

  @FXML
  void registerStudent(ActionEvent event) throws Exception {
    master.showRegisterStaff();
  }

  public void setMaster(MasterController master) {
      this.master = master;
  }

}
