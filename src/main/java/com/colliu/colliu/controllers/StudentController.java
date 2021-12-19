package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class StudentController {

  @FXML
  private Button btnRegister;

  @FXML
  private Label lblLastNameError;

  @FXML
  private Label lblNameError;

  @FXML
  private Label lblPasswordError;

  @FXML
  private Label lblProgramError;

  @FXML
  private Label lblStudentEmail;

  @FXML
  private Label lblYearError;

  @FXML
  private PasswordField tfConfirmPassword;

  @FXML
  private TextField tfFirstName;

  @FXML
  private TextField tfGraduationYear;

  @FXML
  private TextField tfLastName;

  @FXML
  private PasswordField tfPassword;

  @FXML
  private TextField tfProgram;

  @FXML
  private TextField tfStudentEmail;
  private MasterController master;

  // Tells user what input is wrong if it is, otherwise it creates a student
  @FXML
  void registerStudent(ActionEvent event) throws Exception {
    if (tfStudentEmail.getText().isBlank()) {
      lblStudentEmail.setText("Email cannot be blank");
    } else if (!tfStudentEmail.getText().endsWith("student.gu.se")) {
      lblStudentEmail.setText("Email must end with: 'student.gu.se'");
    } else if (tfStudentEmail.getText().contains(" ")) {
      lblStudentEmail.setText("Email cannot contain any blank spaces");
    } else {
      lblStudentEmail.setText("");
    }
    /*
    if (userMethods.validatePassword(tfConfirmPassword.getText()) == false){
        lblPasswordError.setText("Passwords does not match");
        throw new Exception();
    }

     */
    // Write comment here
    if (tfFirstName.getText().isBlank()) {
      lblNameError.setText("First name cannot be blank");
      throw new Exception("First name cannot be blank");
    } else if (tfFirstName.getText().matches(("(.*[0-9].*)"))) {
      lblNameError.setText("First name cannot contain any numbers");
      System.out.println("error");
      throw new Exception("First name cannot contain any numbers");
    } else {
      lblNameError.setText("");
    }
    // Write comment here
    if (tfLastName.getText().isBlank()) {
      lblLastNameError.setText("Last name cannot be blank.");
      throw new Exception("Last name cannot be blank");
    } else if (tfLastName.getText().matches(("(.*[0-9].*)"))) {
      lblLastNameError.setText("Last name cannot " +  System.lineSeparator() + " contain any numbers");
      throw new Exception("Last name cannot contain any numbers");
    } else {
      lblLastNameError.setText("");
    }
    // Write comment here
    if (tfPassword.getText().isBlank()) {
      lblPasswordError.setText("Password cannot be blank");
      throw new Exception("Password cannot be blank");
    } else if (tfPassword.getText().length() < 11 || tfPassword.getText().length() > 20) {
      lblPasswordError.setText("Password must be between 12 and 20 characters");
      throw new Exception("Password must be between 12 and 20 characters");
    } else if (!tfPassword.getText().matches("(.*[A-Z].*)")) {
      lblPasswordError.setText("Password must contain at least one uppercase letter");
      throw new Exception("Password must contain at least one uppercase letter");
    } else if (!tfPassword.getText().matches("(.*[a-z].*)")) {
      lblPasswordError.setText("Password must contain at least one lowercase character");
      throw new Exception("Password must contain at least one lowercase character");
    } else if (!tfPassword.getText().matches("(.*[0-9].*)")) {
      lblPasswordError.setText("Password must contain at least one number");
      throw new Exception("Password must contain at least one number");
    } else if (tfPassword.getText().contains(" ")) {
      lblPasswordError.setText("Passwords cannot contain blank spaces");
      throw new Exception("Passwords cannot contain blank spaces");
    }
    if (!tfPassword.getText().equals(tfConfirmPassword.getText())) {
      lblPasswordError.setText("Passwords does not match");
      throw new Exception("Passwords does not match");
    } else {
      lblPasswordError.setText("");
    }
    // Write comment here
    try {
      String email = tfStudentEmail.getText();
      String password = tfPassword.getText();
      String name = tfFirstName.getText();
      String surname = tfLastName.getText();
      int graduationYear = Integer.parseInt(tfGraduationYear.getText());
      String program = tfProgram.getText();
      master.userMethods.createStudent(email, password, name, surname, graduationYear, program);
      master.showLogin();
    } catch (Exception exception)  {
       // Do something here.
    }
  }
  
  public void setMaster(MasterController master) {
    this.master = master;
  }
}
