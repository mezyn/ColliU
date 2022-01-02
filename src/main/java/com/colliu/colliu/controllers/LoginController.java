package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import miscellaneous.Info;
import user.User;

public class LoginController {
  private MasterController master;
  private String[][] loginDetails;

  @FXML
  private TextField guEmail;

  @FXML
  private PasswordField password;

  //In case there is an issue during login process, the application informs a user about the matter with this label.
  @FXML
  private Label warningLabel;



  /* Upon login button click, the system checks if the input email is already registered in the system as well as
  if the input password matches the email address.
  If both are correct, the user succeeds to log in and will be sent to the event page(homepage).
  Otherwise, the warning label shows which action the user shall take to successfully login. */

  @FXML
  void onLogInClick() {
    String uEmail = guEmail.getText();
    String uPassword = password.getText();

    if (uEmail.isBlank()) {                              //checks if email field is empty
      warningLabel.setText(Info.EMPTY_EMAIL);
    } else if (uPassword.isBlank()) {                      //check if password field is empty
      warningLabel.setText(Info.EMPTY_PASSWORD);
    } else {
      boolean validLogin = master.validateLogin(uEmail, uPassword); //check if the specific email and password is valid to log in by finding the user
      if (validLogin) {
        User user = master.findUser(uEmail);
        master.setLoggedInUser(user);
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
  void registerStaff() {
    master.showRegisterStaff();
  }

  // Upon clicking 'New Student' button, a user is sent to the registration page for student.
  @FXML
  void registerStudent() {
    master.showRegisterStudent();
  }

  /*
  When a user hover on/off the login button, the button color changes.
  */
  @FXML
  void hoverOn(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(0.8);
  }

  @FXML
  void hoverOff(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(1);
  }


  /* To facilitate the demo of the software, we added the user profiles that exist in JSON file the login page
  in a form of combo box. The codes below are written to achieve the purpose.
   */
  @FXML
  private ComboBox<String> cbLogin;

  public void setMaster(MasterController master) {
    this.master = master;
    loginDetails = new String[master.getAllUsers().size()][2];
    System.out.println(master.getAllUsers().size());
    for (int i = 0; i < master.getAllUsers().size(); i++) {
      loginDetails[i][0] = master.getAllUsers().get(i).getEmail();
      loginDetails[i][1] = master.getAllUsers().get(i).getPassword();
    }
    ObservableList<String> items = FXCollections.observableArrayList("Admin", "Staff", "Staff2", "Staff3", "Student", "Student2", "Student3", "Student4");
    cbLogin.setItems(items);
    cbLogin.getSelectionModel().selectFirst();
  }


  @FXML
  void enterDetails() {
    String email;
    String pass;
    switch(cbLogin.getValue()) {
      case "Admin":
        email = loginDetails[0][0];
        pass = loginDetails[0][1];
        break;
      case "Staff":
        email = loginDetails[1][0];
        pass = loginDetails[1][1];
        break;
      case "Staff2":
        email = loginDetails[2][0];
        pass = loginDetails[2][1];
        break;
      case "Staff3":
        email = loginDetails[3][0];
        pass = loginDetails[3][1];
        break;
      case "Student":
        email = loginDetails[4][0];
        pass = loginDetails[4][1];
        break;
      case "Student2":
        email = loginDetails[5][0];
        pass = loginDetails[5][1];
        break;
      case "Student3":
        email = loginDetails[6][0];
        pass = loginDetails[6][1];
        break;
      default:
        email = loginDetails[7][0];
        pass = loginDetails[7][1];
    }
    guEmail.setText(email);
    password.setText(pass);
  }

}
