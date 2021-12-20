package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import user.User;

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
  void onForgotPasswordClick(ActionEvent event) throws Exception {
    master.showForgottenPassword();
  }

  /* Upon login button click, the system checks if the input email is already registered in the system as well as
if the input password matches the email address. If both are correct, the user succeeds to log in and will be sent
to the event page(homepage). Otherwise, the warning label shows which action the user shall take to successfully login. */

  @FXML
  void onLogInClick(ActionEvent event) throws Exception {
    master.showEventPage();
/*
    if (guEmail.getText().isBlank()) {
      warningLabel.setText("Email address cannot be empty.");
    } else if (password.getText().isBlank()) {
      warningLabel.setText("Password cannot be empty.");
    } else {
      if (master.userMethods.checkExistingEmail(guEmail.getText())) {
        int foundUserIndex = master.userMethods.findUser(guEmail.getText());
        User user = master.userMethods.activeUsers.get(foundUserIndex);
        if (master.userMethods.validatePassword(password.getText())) {
          master.showEventPage();
        } else {
          warningLabel.setText("Wrong password.");
        }
      } else {
        warningLabel.setText("Not existing email address.");
      }
    }*/
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
