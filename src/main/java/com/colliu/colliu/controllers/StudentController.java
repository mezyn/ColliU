package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

  final private String OUTLINE_BAD = "-fx-border-radius:1; -fx-border-width: 1; -fx-border-color: #f6a8a6; -fx-border-style: solid;";
  final private String OUTLINE_GOOD = "-fx-border-radius:1; -fx-border-width: 1; -fx-border-color:  rgb(192,236,204); -fx-border-style: solid;";

  @FXML
  private Button btnRegister;

  @FXML
  private Label lblWarning;

  @FXML
  private PasswordField tfConfirmPassword;

  @FXML
  private TextField tfFirstName;

  @FXML
  private TextField tfLastName;

  @FXML
  private PasswordField tfPassword;

  @FXML
  private TextField tfProgram;

  @FXML
  private ChoiceBox<String> graduationYearChoice;

  @FXML
  private ChoiceBox<String> programChoice;

  private String[] graduationYear = {"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};

  private String[] program = {"Software engineering and management", "Systemvetenskap", "Kognitionsvetenskap", "Datavetenskap"};

  public int getGraduationYear(ActionEvent event){
    int graduationYear = Integer.parseInt(graduationYearChoice.getValue());
    return graduationYear;
  }

  public String getProgram(ActionEvent event){
    return programChoice.getValue();
  }

  @FXML
  private TextField tfStudentEmail;
  private MasterController master;

  // Tells user what input is wrong if it is, otherwise it creates a student
  @FXML
  void registerStudent(ActionEvent event) throws Exception {
    if (tfStudentEmail.getText().isBlank()) {
      lblWarning.setText("Email cannot be blank");
      tfStudentEmail.setStyle(OUTLINE_BAD);
      throw new Exception("Email name cannot be blank");
    } else if (!tfStudentEmail.getText().endsWith("student.gu.se")) {
      lblWarning.setText("Email must end with: 'student.gu.se'");
      tfStudentEmail.setStyle(OUTLINE_BAD);
      throw new Exception("Email must end with: 'student.gu.se'");
    } else if (tfStudentEmail.getText().contains(" ")) {
      lblWarning.setText("Email cannot contain any blank spaces");
      tfStudentEmail.setStyle(OUTLINE_BAD);
      throw new Exception("Email cannot contain any blank spaces");
    } else {
      lblWarning.setText("");
      tfStudentEmail.setStyle(OUTLINE_GOOD);
    }

    // Write comment here
    if (tfFirstName.getText().isBlank()) {
      lblWarning.setText("First name cannot be blank");
      tfFirstName.setStyle(OUTLINE_BAD);
      throw new Exception("First name cannot be blank");
    } else if (tfFirstName.getText().matches(("(.*[0-9].*)"))) {
      lblWarning.setText("First name cannot contain any numbers");
      tfFirstName.setStyle(OUTLINE_BAD);
      throw new Exception("First name cannot contain any numbers");
    } else {
      lblWarning.setText("");
      tfFirstName.setStyle(OUTLINE_GOOD);
    }
    // Write comment here
    if (tfLastName.getText().isBlank()) {
      lblWarning.setText("Last name cannot be blank.");
      tfLastName.setStyle(OUTLINE_BAD);
      throw new Exception("Last name cannot be blank");
    } else if (tfLastName.getText().matches(("(.*[0-9].*)"))) {
      lblWarning.setText("Last name cannot " +  System.lineSeparator() + " contain any numbers");
      tfLastName.setStyle(OUTLINE_BAD);
      throw new Exception("Last name cannot contain any numbers");
    } else {
      lblWarning.setText("");
      tfLastName.setStyle(OUTLINE_GOOD);
    }
    // Write comment here
    if (tfPassword.getText().isBlank()) {
      lblWarning.setText("Password cannot be blank");
      tfPassword.setStyle(OUTLINE_BAD);
      throw new Exception("Password cannot be blank");
    } else if (tfPassword.getText().length() < 11 || tfPassword.getText().length() > 20) {
      lblWarning.setText("Password must be between 12 and 20 characters");
      tfPassword.setStyle(OUTLINE_BAD);
      throw new Exception("Password must be between 12 and 20 characters");
    } else if (!tfPassword.getText().matches("(.*[A-Z].*)")) {
      lblWarning.setText("Password must contain at least one uppercase letter");
      tfPassword.setStyle(OUTLINE_BAD);
      throw new Exception("Password must contain at least one uppercase letter");
    } else if (!tfPassword.getText().matches("(.*[a-z].*)")) {
      lblWarning.setText("Password must contain at least one lowercase character");
      tfPassword.setStyle(OUTLINE_BAD);
      throw new Exception("Password must contain at least one lowercase character");
    } else if (!tfPassword.getText().matches("(.*[0-9].*)")) {
      lblWarning.setText("Password must contain at least one number");
      tfPassword.setStyle(OUTLINE_BAD);
      throw new Exception("Password must contain at least one number");
    } else if (tfPassword.getText().contains(" ")) {
      lblWarning.setText("Passwords cannot contain blank spaces");
      tfPassword.setStyle(OUTLINE_BAD);
      throw new Exception("Passwords cannot contain blank spaces");
    }
    if (!tfPassword.getText().equals(tfConfirmPassword.getText())) {
      lblWarning.setText("Passwords does not match");
      tfPassword.setStyle(OUTLINE_BAD);
      throw new Exception("Passwords does not match");
    } else {
      lblWarning.setText("");
      tfPassword.setStyle(OUTLINE_GOOD);
    }
    // Write comment here
    try {
      String email = tfStudentEmail.getText();
      String password = tfPassword.getText();
      String name = tfFirstName.getText();
      String surname = tfLastName.getText();
      int graduationYear = getGraduationYear(event);
      String program = getProgram(event);
      master.createStudent(email, password, name, surname, graduationYear, program);
      master.saveUsers();
      master.showLogin();
    } catch (Exception exception)  {
    }
  }

  @FXML
  void cancelRegistration(ActionEvent event) {
    master.showLogin();
  }

  public void setMaster(MasterController master) {
    this.master = master;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {              //Gives options to the choiceboxes
    graduationYearChoice.getItems().addAll(graduationYear);
    graduationYearChoice.setOnAction(this::getGraduationYear);
    programChoice.getItems().addAll(program);
    programChoice.setOnAction(this::getProgram);
  }
}
