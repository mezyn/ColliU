package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
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
    String uEmail = guEmail.getText();
    String uPassword = password.getText();

    if (uEmail.isBlank()) {                              //checks if email is empty
      warningLabel.setText("Email address cannot be empty.");
    } else if (uPassword.isBlank()) {                      //check if password is empty
      warningLabel.setText("Password cannot be empty.");
    } else {
      boolean validLogin = master.validateLogin(uEmail, uPassword);
      if (validLogin) {
        User user = master.findUser(uEmail);
        master.setLoggedInUser(user);
        boolean accountStatus = user.getAccountStatus();
        if (accountStatus) {
          accountBannedPane.setVisible(accountStatus);
        } else {
          master.showEventPage();
        }
      } else {
        warningLabel.setText("Email or Password is incorrect.");
      }
    }

  }

  @FXML
  void registerStaff(ActionEvent event) throws Exception {
    master.showRegisterStaff();
  }

  @FXML
  void registerStudent(ActionEvent event) throws Exception {
    master.showRegisterStudent();
  }

  @FXML
  void hoverOn(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(0.8);
  }

  @FXML
  void hoverOff(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(1);
  }

  @FXML
  void underlineOn(MouseEvent event) {
    String buttonStyle = ((Button) event.getSource()).getStyle();
    ((Button) event.getSource()).setStyle(buttonStyle + "-fx-underline: true;");
  }

  @FXML
  void underlineOff(MouseEvent event) {
    String buttonStyle = ((Button) event.getSource()).getStyle();
    ((Button) event.getSource()).setStyle(buttonStyle.substring(0, buttonStyle.length() - 20));
  }

  public void setMaster(MasterController master) {
      this.master = master;
  }

  @FXML
  void ChangeLogin(ActionEvent event) {
    String button = ((Button) event.getSource()).getText();
    if(button.equals("Student")) {
      guEmail.setText("student@student.gu.se");
    } else if(button.equals("Admin")) {
      guEmail.setText("admin@student.gu.se");
    } else {
      guEmail.setText("staff@teacher.gu.se");
    }
    password.setText("Hej123123!!");
  }

}
