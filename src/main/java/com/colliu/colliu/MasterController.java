package com.colliu.colliu;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user.UserMethods;

/**
 * * * * * * * * * * * *
 * DOCUMENTATION HERE. *
 * * * * * * * * * * * *
 **/

public class MasterController {

  UserMethods userMethods = new UserMethods(this);
  final String eventPage = "EventPage.fxml";
  final String registerPage = "student-registration";

  @FXML
  private Label welcomeText;

  @FXML
  private Button forgotpassword;

  @FXML
  private TextField guEmail;

  @FXML
  private Button login;

  @FXML
  private TextField password;

  @FXML
  private Button registeraccount;


  @FXML // Write comment here
  protected void onHelloButtonClick(ActionEvent event) throws IOException {
    closeWindow(event);
    showWindow("homepage.fxml");
  }

  /*
  protected String onGUEmailClick() {
      String guEmail = UserInput.readInput();
      return guEmail;
    }
  protected void onPasswordClick() {
    String password = UserInput.readInput();

  }
  protected void onLogInClick() {

    if (UserMethods.checkExistingEmail() && UserMethods.validatePassword()) {


    }
  }*/

  @FXML
  void onLogInClick(ActionEvent event) throws IOException {
    //anything we code here for login should work
    showEventPage(event);
  }


  @FXML
  void onForgotPassword(ActionEvent event) {

  }

  @FXML
  void onGUEmail(ActionEvent event) {

  }

  @FXML
  void onPassword(ActionEvent event) {

  }

  @FXML
  void onRegisterAccount(ActionEvent event) {

  }

  @FXML // Write comment here
  private void closeWindow(ActionEvent action) {
    ((Node)(action.getSource())).getScene().getWindow().hide();
  }
// Write comment here
  private void showWindow(String fileName) throws IOException {
    fileName = (fileName.endsWith(".fxml") ? fileName : fileName + ".fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(Master.class.getResource(fileName));
    Scene scene = new Scene(fxmlLoader.load());
    Stage stage = new Stage();
    String title = "ColliU - " + fileName.substring(0, fileName.length() - 4);
    stage.setTitle(title);
    stage.setScene(scene);
    stage.show();
    //stage.initModality(Modality.APPLICATION_MODAL);
    //stage.initStyle(StageStyle.UNDECORATED);
    //stage.setTitle("ABC");
    //stage.setScene(new Scene(root1));
    //stage.show();
  }

  @FXML
  private Button btnCreateAccount;


  @FXML
  //Button that opens student registration screen
  void openStudentRegistration(ActionEvent event) throws Exception {
    closeWindow(event);
    showWindow("student-registration.fxml");
  }

  @FXML
  void openStaffRegistration(ActionEvent event) throws Exception{ //button that opens staff registration screen
    closeWindow(event);
    showWindow("staff-registration.fxml");
  }

  @FXML
  private Button btnRegister;

  @FXML
  private TextField tfStudentEmail;

  @FXML
  private Label lblStudentEmail;

  @FXML
  private Label lblNameError;

  @FXML
  private Label lblLastNameError;

  @FXML
  private Label lblPasswordError;

  @FXML
  private Label lblProgramError;

  @FXML
  private Label lblYearError;

  @FXML
  private TextField tfFirstName;

  @FXML
  private TextField tfGraduationYear;

  @FXML
  private TextField tfLastName;

  @FXML
  private PasswordField tfPassword;

  @FXML
  private PasswordField tfConfirmPassword;

  @FXML
  private TextField tfProgram;

  @FXML
  // Tells user what input is wrong if it is, otherwise it creates a student
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
    } else if (tfFirstName.getText().matches(("(.*[0-9].*)"))) {
      lblNameError.setText("First name cannot contain any numbers.");
      System.out.println("error");
    } else {
      lblNameError.setText("");
    }
    // Write comment here
    if (tfLastName.getText().isBlank()) {
      lblLastNameError.setText("Last name cannot be blank.");
    } else if (tfLastName.getText().matches(("(.*[0-9].*)"))) {
      lblLastNameError.setText("Last name cannot contain any numbers.");
    } else {
      lblLastNameError.setText("");
    }
    // Write comment here
    if (tfPassword.getText().isBlank()) {
      lblPasswordError.setText("Password cannot be blank.");
    } else if (tfPassword.getText().length() < 11 || tfPassword.getText().length() > 20) {
      lblPasswordError.setText("Password must be between 12 and 20 characters.");
    } else if (!tfPassword.getText().matches("(.*[A-Z].*)")) {
      lblPasswordError.setText("Password must contain at least one uppercase letter.");
    } else if (!tfPassword.getText().matches("(.*[a-z].*)")) {
      lblPasswordError.setText("Password must contain at least one lowercase character.");
    } else if (!tfPassword.getText().matches("(.*[0-9].*)")) {
      lblPasswordError.setText("Password must contain at least one number.");
    } else if (tfPassword.getText().contains(" ")) {
      lblPasswordError.setText("Passwords cannot contain blank spaces.");
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
      userMethods.createStudent(email, password, name, surname, graduationYear, program);
    } catch (Exception exception)  {
      // Do something here.
    }
  }

  @FXML
  private Button btnCreateStaff;

  @FXML
  private TextField tfStaffEmail;

  @FXML
  private PasswordField tfStaffConfirmPassword;

  @FXML
  private TextField tfStaffDepartment;

  @FXML
  private TextField tfStaffFirstName;

  @FXML
  private TextField tfStaffLastName;

  @FXML
  private PasswordField tfStaffPassword;

  @FXML
  private TextField tfStaffTitle;

  @FXML
  private Label lblStaffDepartmentError;

  @FXML
  private Label lblStaffEmailError;

  @FXML
  private Label lblStaffNameError;

  @FXML
  private Label lblStaffPasswordError;

  @FXML
  private Label lblTitleError;

  @FXML
  private Label lblStaffLastNameError;

  @FXML
  // Tells user what input is wrong if it is, otherwise it creates a staff user
  void registerStaff(ActionEvent event) {
    if (tfStaffEmail.getText().isBlank()) {
      lblStaffEmailError.setText("Email cannot be blank");
    } else if (!tfStaffEmail.getText().endsWith("gu.se")) {
      lblStaffEmailError.setText("Email must end with 'gu.se'");
    } else if (tfStaffEmail.getText().contains(" ")) {
      lblStaffNameError.setText("Email cannot contain any blank spaces");
    }
// Write comment here
    if (tfStaffFirstName.getText().isBlank()) {
      lblStaffNameError.setText("First name cannot be blank");
    } else if (tfStaffFirstName.getText().matches("(.*[0-9].*)")) {
      lblStaffNameError.setText("First name cannot contain any numbers");
    } else {
      lblStaffNameError.setText("");
    }
    if (tfStaffLastName.getText().isBlank()) {
      lblStaffLastNameError.setText("Last name cannot be blank");
    } else if (tfStaffLastName.getText().matches("(.*[0-9].*)")) {
      lblStaffLastNameError.setText("Last name cannot contain any numbers");
    } else {
      lblStaffLastNameError.setText("");
    }
// Write comment here
    if (tfStaffPassword.getText().length() < 11 || tfStaffPassword.getText().length() > 20) {
      lblStaffPasswordError.setText("Password must be between 12 and 20 characters.");
    } else if (!tfStaffPassword.getText().matches("(.*[A-Z].*)")) {
      lblStaffPasswordError.setText("Password must contain at least one uppercase letter");
    } else if (!tfStaffPassword.getText().matches("(.*[a-z].*)")) {
      lblStaffPasswordError.setText("Password must contain at least one lowercase letter");
    } else if (!tfStaffPassword.getText().matches("(.*[0-9].*)")) {
      lblStaffPasswordError.setText("Password must contain at least one number.");
    } else if (tfStaffPassword.getText().contains(" ")) {
      lblStaffPasswordError.setText("Password cannot contain any blank spaces");
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
      String staffTitle = tfStaffTitle.getText();
      userMethods.createStaff(email, password, name, surname, department, staffTitle);
    } catch (Exception exception) {
      // Do something here.
    }
  }

  @FXML
  private Button btnAttending;

  @FXML
  private ListView<?> filterList;

  @FXML
  private Pane filterPane;

  @FXML
  private Label lblDescription;

  @FXML
  private TextField txtSearch;


  @FXML
  void showAttendingEvents(ActionEvent event) {

  }
// Write comment here
  void showEventPage(ActionEvent event) throws IOException {
    closeWindow(event);
    showWindow(eventPage);
  }
}


