package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import user.User;

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

}
