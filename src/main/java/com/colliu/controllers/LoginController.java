package com.colliu.controllers;

import com.colliu.PageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import com.colliu.miscellaneous.Info;
import com.colliu.user.User;
import com.colliu.user.UserMethods;

/**
 * All functions for the Login UI are hanlded in this class.
 */

public class LoginController {
  private PageController master;
  private String[][] loginDetails;
  private UserMethods userMethods;

  @FXML
  private TextField guEmail;

  @FXML
  private PasswordField password;

  //In case there is an issue during login process, the application informs a com.colliu.colliu.user about the matter with this label.
  @FXML
  private Label warningLabel;



  /* Upon login button click, the system checks if the input email is already registered in the system as well as
  if the input password matches the email address.
  If both are correct, the user succeeds to log in and will be sent to the homepage.
  Otherwise, the warning label shows which action the user shall take to successfully login. */

  @FXML
  private void onLogInClick() {
    String userEmail = guEmail.getText();
    String userPassword = password.getText();

    if (userEmail.isBlank()) {                              //checks if email field is empty
      warningLabel.setText(Info.EMPTY_EMAIL);
    } else if (userPassword.isBlank()) {                      //check if password field is empty
      warningLabel.setText(Info.EMPTY_PASSWORD);
    } else {
      //check if the specific email and password is valid to log in by finding the user
      boolean validLogin = userMethods.validatePassword(userEmail, userPassword);
      if (validLogin) {
        User user = userMethods.getUserByEmail(userEmail);
        userMethods.setLoggedInUser(user);
        boolean accountStatus = user.getAccountStatus();
        if (accountStatus) {
          warningLabel.setText(Info.ACCOUNT_STATUS_BANNED);
        } else {
          master.showHomepage();
        }
      } else {
        warningLabel.setText(Info.INCORRECT_LOGIN);
      }
    }

  }

  // Upon clicking 'New Staff' button, a user is sent to the registration page for staff.
  @FXML
  private void registerStaff() {
    master.showRegisterStaff();
  }

  // Upon clicking 'New Student' button, a user is sent to the registration page for student.
  @FXML
  private void registerStudent() {
    master.showRegisterStudent();
  }

  /**
   * Hovering different Button(s) will create a nice opacity effect.
   - @param event The source which fired the method.
   */
  @FXML
  private void btnOnMouseEntered(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(0.8);
  }

  @FXML
  private void btnOnMouseExited(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(1);
  }

  public void setMaster(PageController master) {
    this.master = master;
    userMethods = master.getUserReference();
  }
}
