package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class StaffController {

  final private String OUTLINE_BAD = "-fx-border-radius:1; -fx-border-width: 1; -fx-border-color: #f6a8a6; -fx-border-style: solid;";
  final private String OUTLINE_GOOD = "-fx-border-radius:1; -fx-border-width: 1; -fx-border-color:  rgb(192,236,204); -fx-border-style: solid;";

  private MasterController master;


  @FXML
  private Button btnCreateStaff;

  @FXML
  private Label lblWarning;

  @FXML
  private PasswordField tfStaffConfirmPassword;

  @FXML
  private TextField tfStaffEmail;

  @FXML
  private TextField tfStaffFirstName;

  @FXML
  private TextField tfStaffLastName;

  @FXML
  private PasswordField tfStaffPassword;

  // Tells user what input is wrong if it is, otherwise it creates a staff user
  @FXML
  void registerStaff(ActionEvent event) throws Exception {
    // Checks if the Email that the user entered is adequate
    if (tfStaffEmail.getText().isBlank()) {
      lblWarning.setText("Email cannot be blank");
      tfStaffEmail.setStyle(OUTLINE_BAD);
      throw new Exception("Email cannot be blank");
    } else if (!tfStaffEmail.getText().endsWith("gu.se")) {
      lblWarning.setText("Email must end with 'gu.se'");
      tfStaffEmail.setStyle(OUTLINE_BAD);
      throw new Exception("Email must end with 'gu.se'");
    } else if (tfStaffEmail.getText().contains("student")) {
      lblWarning.setText("Students can't register as staff");
      tfStaffEmail.setStyle(OUTLINE_BAD);
      throw new Exception("Students can't register as staff");
    } else if (tfStaffEmail.getText().contains(" ")) {
      lblWarning.setText("Email cannot contain any blank spaces");
      tfStaffEmail.setStyle(OUTLINE_BAD);
      throw new Exception("Email cannot contain any blank spaces");
    }
    else {
      lblWarning.setText("");
      tfStaffEmail.setStyle(OUTLINE_GOOD);
    }
    if (master.findUser(tfStaffEmail.getText()) != null){
      lblWarning.setText("Email is already registered");
      throw new Exception("Email is already registered");
    } else {
      lblWarning.setText("");
    }
    // Checks if the first name that the user entered is blank or contains any numbers. Informs the user if any of these are fulfilled.
    if (tfStaffFirstName.getText().isBlank()) {
      lblWarning.setText("First name cannot be blank");
      tfStaffFirstName.setStyle(OUTLINE_BAD);
      throw new Exception("First name cannot be blank");
    } else if (tfStaffFirstName.getText().matches("(.*[0-9].*)")) {
      lblWarning.setText("First name cannot contain any numbers");
      tfStaffFirstName.setStyle(OUTLINE_BAD);
      throw new Exception("First name cannot contain any numbers");
    } else {
      lblWarning.setText("");
      tfStaffFirstName.setStyle(OUTLINE_GOOD);
    }
    // Checks if the last name that the user entered is blank or contains any numbers. Informs the user if any of these are fulfilled.
    if (tfStaffLastName.getText().isBlank()) {
      lblWarning.setText("Last name cannot be blank");
      tfStaffLastName.setStyle(OUTLINE_BAD);
      throw new Exception("Last name cannot be blank");
    } else if (tfStaffLastName.getText().matches("(.*[0-9].*)")) {
      lblWarning.setText("Last name cannot contain any numbers");
      tfStaffLastName.setStyle(OUTLINE_BAD);
      throw new Exception("Last name cannot contain any numbers");
    } else {
      lblWarning.setText("");
      tfStaffLastName.setStyle(OUTLINE_GOOD);
    }
    // Checks that password is between 12-20 characters, that it has at least one uppercase and one lowercase character, that it contains at least one number, that it doesn't contain any blank spaces and that the password and confirm password text field matches.
    if (tfStaffPassword.getText().length() < 11 || tfStaffPassword.getText().length() > 20) {
      lblWarning.setText("Password must be between 12 and 20 characters");
      tfStaffPassword.setStyle(OUTLINE_BAD);
      throw new Exception("Password must be between 12 and 20 characters");
    } else if (!tfStaffPassword.getText().matches("(.*[A-Z].*)")) {
      lblWarning.setText("Password must contain at least one uppercase letter");
      tfStaffPassword.setStyle(OUTLINE_BAD);
      throw new Exception("Password must contain at least one uppercase letter");
    } else if (!tfStaffPassword.getText().matches("(.*[a-z].*)")) {
      lblWarning.setText("Password must contain at least one lowercase letter");
      tfStaffPassword.setStyle(OUTLINE_BAD);
      throw new Exception("Password must contain at least one lowercase letter");
    } else if (!tfStaffPassword.getText().matches("(.*[0-9].*)")) {
      lblWarning.setText("Password must contain at least one number");
      tfStaffPassword.setStyle(OUTLINE_BAD);
      throw new Exception("Password must contain at least one number");
    } else if (tfStaffPassword.getText().contains(" ")) {
      lblWarning.setText("Password cannot contain any blank spaces");
      tfStaffPassword.setStyle(OUTLINE_BAD);
      throw new Exception("Password cannot contain any blank spaces");
    } else if (!tfStaffPassword.getText().equals(tfStaffConfirmPassword.getText())) {
      lblWarning.setText("Passwords does not match");
      tfStaffPassword.setStyle(OUTLINE_BAD);
      throw new Exception("Passwords does not match");
    } else {
      lblWarning.setText("");
      tfStaffPassword.setStyle(OUTLINE_GOOD);
    }

    // Puts the user input into variables and then creates a staff user. This also saves the created user into a JSon file and then redirects you to the login page.
    try {
      String email = tfStaffEmail.getText();
      String password = tfStaffPassword.getText();
      String name = tfStaffFirstName.getText();
      String surname = tfStaffLastName.getText();
      master.createStaff(email, password, name, surname);
      master.saveUsers();
      master.showLogin();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  //Button that cancels registration and brings you back to the login page
  @FXML
  void cancelRegistration(ActionEvent event) {
    master.showLogin();
  }

  //Changes the look of the cursor when hovering over specific objects
  @FXML
  void hoverOn(MouseEvent mouse) {
    ((Button) mouse.getSource()).setOpacity(0.8);
  }

  @FXML
  void hoverOff(MouseEvent mouse) {
    ((Button) mouse.getSource()).setOpacity(1);
  }

  public void setMaster(MasterController master) {
    this.master = master;
  }

}
