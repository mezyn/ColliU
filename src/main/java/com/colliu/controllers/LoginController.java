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
  If both are correct, the com.colliu.colliu.user succeeds to log in and will be sent to the com.colliu.colliu.event page(homepage).
  Otherwise, the warning label shows which action the com.colliu.colliu.user shall take to successfully login. */

  @FXML
  private void onLogInClick() {
    String uEmail = guEmail.getText();
    String uPassword = password.getText();

    if (uEmail.isBlank()) {                              //checks if email field is empty
      warningLabel.setText(Info.EMPTY_EMAIL);
    } else if (uPassword.isBlank()) {                      //check if password field is empty
      warningLabel.setText(Info.EMPTY_PASSWORD);
    } else {
      boolean validLogin = userMethods.validatePassword(uEmail, uPassword); //check if the specific email and password is valid to log in by finding the com.colliu.colliu.user
      if (validLogin) {
        User user = userMethods.getUserByEmail(uEmail);
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

  // Upon clicking 'New Staff' button, a com.colliu.colliu.user is sent to the registration page for staff.
  @FXML
  private void registerStaff() {
    master.showRegisterStaff();
  }

  // Upon clicking 'New Student' button, a com.colliu.colliu.user is sent to the registration page for student.
  @FXML
  private void registerStudent() {
    master.showRegisterStudent();
  }

  /*
  When a com.colliu.colliu.user hover on/off the login button, the button color changes.
  */
  @FXML
  private void hoverOn(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(0.8);
  }

  @FXML
  private void hoverOff(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(1);
  }

  @FXML
  private ComboBox<String> cbLogin; /*******DELETE ME BEFORE SUBMIT ***************/

  public void setMaster(PageController master) {
    this.master = master;
    userMethods = master.getUserReference();
    loginDetails = new String[userMethods.getAllUsers().size()][2];  /*******DELETE ME BEFORE SUBMIT ***************/
    /*******DELETE ME BEFORE SUBMIT ***************/
    ObservableList<String> items = FXCollections.observableArrayList("Admin", "Staff", "Staff2", "Staff3", "Student", "Student2", "Student3", "Student4");
    cbLogin.setItems(items);  /*******DELETE ME BEFORE SUBMIT ***************/
    cbLogin.getSelectionModel().selectFirst();  /*******DELETE ME BEFORE SUBMIT ***************/
  }

  /*******DELETE ME BEFORE SUBMIT ***************/
  @FXML
  private void enterDetails() { /*******DELETE ME BEFORE SUBMIT ***************/
    String email; /*******DELETE ME BEFORE SUBMIT ***************/
    String pass; /*******DELETE ME BEFORE SUBMIT ***************/
    switch(cbLogin.getValue()) { /*******DELETE ME BEFORE SUBMIT ***************/
      case "Admin": /*******DELETE ME BEFORE SUBMIT ***************/
        email = loginDetails[0][0]; /*******DELETE ME BEFORE SUBMIT ***************/
        pass = loginDetails[0][1]; /*******DELETE ME BEFORE SUBMIT ***************/
        break; /*******DELETE ME BEFORE SUBMIT ***************/
      case "Staff": /*******DELETE ME BEFORE SUBMIT ***************/
        email = loginDetails[1][0]; /*******DELETE ME BEFORE SUBMIT ***************/
        pass = loginDetails[1][1]; /*******DELETE ME BEFORE SUBMIT ***************/
        break; /*******DELETE ME BEFORE SUBMIT ***************/
      case "Staff2": /*******DELETE ME BEFORE SUBMIT ***************/
        email = loginDetails[2][0]; /*******DELETE ME BEFORE SUBMIT ***************/
        pass = loginDetails[2][1]; /*******DELETE ME BEFORE SUBMIT ***************/
        break; /*******DELETE ME BEFORE SUBMIT ***************/
      case "Staff3": /*******DELETE ME BEFORE SUBMIT ***************/
        email = loginDetails[3][0]; /*******DELETE ME BEFORE SUBMIT ***************/
        pass = loginDetails[3][1]; /*******DELETE ME BEFORE SUBMIT ***************/
        break; /*******DELETE ME BEFORE SUBMIT ***************/
      case "Student": /*******DELETE ME BEFORE SUBMIT ***************/
        email = loginDetails[4][0]; /*******DELETE ME BEFORE SUBMIT ***************/
        pass = loginDetails[4][1]; /*******DELETE ME BEFORE SUBMIT ***************/ /*******DELETE ME BEFORE SUBMIT ***************/
        break; /*******DELETE ME BEFORE SUBMIT ***************/
      case "Student2": /*******DELETE ME BEFORE SUBMIT ***************/
        email = loginDetails[5][0]; /*******DELETE ME BEFORE SUBMIT ***************/
        pass = loginDetails[5][1]; /*******DELETE ME BEFORE SUBMIT ***************/
        break; /*******DELETE ME BEFORE SUBMIT ***************/
      case "Student3":
        email = loginDetails[6][0];
        pass = loginDetails[6][1]; /*******DELETE ME BEFORE SUBMIT ***************/
        break;
      default:
        email = loginDetails[7][0]; /*******DELETE ME BEFORE SUBMIT ***************/
        pass = loginDetails[7][1]; /*******DELETE ME BEFORE SUBMIT ***************/
    } /*******DELETE ME BEFORE SUBMIT ***************/
    guEmail.setText(email); /*******DELETE ME BEFORE SUBMIT ***************/
    password.setText(pass); /*******DELETE ME BEFORE SUBMIT ***************/
    for(int i = 0; i < userMethods.getAllUsers().size(); i++) {
      System.out.println(userMethods.getAllUsers().get(i).getEmail() + " " + userMethods.getAllUsers().get(i).getPassword());
    }
  }

}
