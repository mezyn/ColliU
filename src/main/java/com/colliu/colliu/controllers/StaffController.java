package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class StaffController {

  private MasterController master;

  @FXML
  private Button btnCreateStaff;

  @FXML
  private Hyperlink hplStaffBack;

  @FXML
  private Label lblStaffEmailError;

  @FXML
  private Label lblStaffLastNameError;

  @FXML
  private Label lblStaffNameError;

  @FXML
  private Label lblStaffPasswordError;

  @FXML
  private PasswordField tfStaffConfirmPassword;

  @FXML
  private TextField tfStaffDepartment;

  @FXML
  private TextField tfStaffEmail;

  @FXML
  private TextField tfStaffFirstName;

  @FXML
  private TextField tfStaffLastName;

  @FXML
  private PasswordField tfStaffPassword;


  @FXML
  void returnHomePage(ActionEvent event) {
    master.showLastWindow();
  }

  // Tells user what input is wrong if it is, otherwise it creates a staff user
  @FXML
  void registerStaff(ActionEvent event) throws Exception {
    // Checks if the Email that the user entered is adequate
    if (tfStaffEmail.getText().isBlank()) {
      lblStaffEmailError.setText("Email cannot be blank");
      throw new Exception("Email cannot be blank");
    } else if (!tfStaffEmail.getText().endsWith("gu.se")) {
      lblStaffEmailError.setText("Email must end with 'gu.se'");
      throw new Exception("Email must end with 'gu.se'");
    } else if (tfStaffEmail.getText().contains("student")) {
      lblStaffEmailError.setText("Students can't register as staff");
      throw new Exception("Students can't register as staff");
    } else if (tfStaffEmail.getText().contains(" ")) {
      lblStaffNameError.setText("Email cannot contain any blank spaces");
      throw new Exception("Email cannot contain any blank spaces");
    }
    // Write comment here
    if (tfStaffFirstName.getText().isBlank()) {
      lblStaffNameError.setText("First name cannot be blank");
      throw new Exception("First name cannot be blank");
    } else if (tfStaffFirstName.getText().matches("(.*[0-9].*)")) {
      lblStaffNameError.setText("First name cannot contain any numbers");
      throw new Exception("First name cannot contain any numbers");
    } else {
      lblStaffNameError.setText("");
    }
    if (tfStaffLastName.getText().isBlank()) {
      lblStaffLastNameError.setText("Last name cannot be blank");
      throw new Exception("Last name cannot be blank");
    } else if (tfStaffLastName.getText().matches("(.*[0-9].*)")) {
      lblStaffLastNameError.setText("Last name cannot contain any numbers");
      throw new Exception("Last name cannot contain any numbers");
    } else {
      lblStaffLastNameError.setText("");
    }
    // Write comment here
    if (tfStaffPassword.getText().length() < 11 || tfStaffPassword.getText().length() > 20) {
      lblStaffPasswordError.setText("Password must be between 12 and 20 characters");
      throw new Exception("Password must be between 12 and 20 characters");
    } else if (!tfStaffPassword.getText().matches("(.*[A-Z].*)")) {
      lblStaffPasswordError.setText("Password must contain at least one uppercase letter");
      throw new Exception("Password must contain at least one uppercase letter");
    } else if (!tfStaffPassword.getText().matches("(.*[a-z].*)")) {
      lblStaffPasswordError.setText("Password must contain at least one lowercase letter");
      throw new Exception("Password must contain at least one lowercase letter");
    } else if (!tfStaffPassword.getText().matches("(.*[0-9].*)")) {
      lblStaffPasswordError.setText("Password must contain at least one number");
      throw new Exception("Password must contain at least one number");
    } else if (tfStaffPassword.getText().contains(" ")) {
      lblStaffPasswordError.setText("Password cannot contain any blank spaces");
      throw new Exception("Password cannot contain any blank spaces");
    }
    if (!tfStaffPassword.getText().equals(tfStaffConfirmPassword.getText())) {
      lblStaffPasswordError.setText("Passwords does not match");
      throw new Exception("Passwords does not match");
    } else {
      lblStaffPasswordError.setText("");
    }

    //is department and title allowed to be empty? otherwise what are they allowed to be
    /*
    if (userMethods.validatePassword(tfConfirmPassword.getText()) == false){
      lblStaffPasswordError.setText("Passwords does not match");
      throw new Exception();
    }

     */
// Write comment here
    try {
      String email = tfStaffEmail.getText();
      String password = tfStaffPassword.getText();
      String name = tfStaffFirstName.getText();
      String surname = tfStaffLastName.getText();
      String department = tfStaffDepartment.getText();
      String staffTitle = "Professor"; //tfStaffTitle.getText();
      master.userMethods.createStaff(email, password, name, surname, department, staffTitle);
      master.showEventPage();
    } catch (Exception exception) {
      // Do something here.
    }
  }

  @FXML
  void cancelRegistration(ActionEvent event) {

  }

  public void setMaster(MasterController master) {
    this.master = master;
  }

}
