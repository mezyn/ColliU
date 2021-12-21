package com.colliu.colliu;

import java.io.IOException;

import event.EventController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user.Student;
import user.User;
import user.UserMethods;

/**
 * * * * * * * * * * * *
 * DOCUMENTATION HERE. *
 * * * * * * * * * * * *
 **/

public class MasterController {

  UserMethods userMethods = new UserMethods();
  final String eventPage = "EventPage.fxml";
  final String registerPage = "account-type-detection.fxml";
  Master master = new Master();
  EventController masterController = new EventController(this.master);





  //attributes and methods related to "LoginPage.fxml"

  @FXML
  private Label welcomeText;

  @FXML
  private TextField guEmail;

  @FXML
  private PasswordField password;

  @FXML
  private Label warningLabel;



  @FXML // Write comment here
  protected void onHelloButtonClick(ActionEvent event) throws IOException {
    closeWindow(event);
    showWindow("LoginPage.fxml");
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
      if (userMethods.checkExistingEmail(guEmail.getText())) {
        int foundUserIndex = userMethods.findUser(guEmail.getText());
        User user = userMethods.activeUsers.get(foundUserIndex);
        if (userMethods.validatePassword(password.getText())) {
          closeWindow(event);
          showWindow("EventPage.fxml");
        } else {
          warningLabel.setText("Wrong password.");
        }
      } else {
        warningLabel.setText("Not existing email address.");
      }
    }
  }

  @FXML
  void onForgotPasswordClick(ActionEvent event) throws IOException {
    closeWindow(event);
    showWindow("forgot-password.fxml");
  }

  /* Upon clicking 'register account' button, the system checks if the user account is a type of student or staff,
  then send the user to the corresponding registration page.
   */
  @FXML
  void onRegisterAccountClick(ActionEvent event) throws IOException {
    closeWindow(event);
    if (guEmail.getText().endsWith("@student.gu.se")) {
      showWindow("student-registration.fxml");
    } else {
      showWindow("staff-registration.fxml");
    }
  }

  //attributes and methods related to "forgot-password.fxml"

  @FXML
  private TextField fp_email;

  @FXML
  private Label fp_warningLabel;


  @FXML
  void onContinueClick(ActionEvent event) throws IOException{
    if (fp_email.getText().isBlank()) {
      fp_warningLabel.setText("Email address cannot be empty.");
    } else if (!fp_email.getText().endsWith("gu.se")) {
      fp_warningLabel.setText("Email address must be associated with GU.");
    } else {
      closeWindow(event);
      showWindow("new-password-sent.fxml");
    }
  }

  @FXML
  void onSignUpClick(ActionEvent event) throws IOException{
    closeWindow(event);
    showWindow("account-type-detection.fxml");
  }

  //attributes and methods related to "new-password-sent.fxml"

  @FXML
  void backToLoginPageClick(ActionEvent event) throws IOException{
    closeWindow(event);
    showWindow("LoginPage.fxml");
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
    this.latestStage = stage;
    return fxmlLoader;
  }

  public void showRegisterStudent() throws Exception {
    String registerStudentPage = "student-registration.fxml";
    closeWindow();

    FXMLLoader studentLoader = showWindow(registerStudentPage);
    StudentController studentController = studentLoader.getController();
    studentController.setMaster(this);
  }

  public void showRegisterStaff() throws Exception { //button that opens staff registration screen
    String registerStaffPage = "staff-registration.fxml";
    closeWindow();

    FXMLLoader staffLoader = showWindow(registerStaffPage);
    StaffController staffController = staffLoader.getController();
    staffController.setMaster(this);
  }

  public void showLogin() throws IOException {
    String loginPage = "LoginPage.fxml";
    closeWindow();

    FXMLLoader temp = showWindow(loginPage);
    LoginController loginController = temp.getController();
    loginController.setMaster(this);
  }

  public void showEventPage() throws Exception {
    String eventPage = "EventPage.fxml";
    closeWindow();
    FXMLLoader temp = showWindow(eventPage);
    EventController eventController = temp.getController();
    eventController.setMaster(this);
    eventController.loadEvents();
  }

  public void showForgottenPassword() throws Exception {
    String forgottenPasswordPage = "forgot-password.fxml";
    showWindow(forgottenPasswordPage);
  }

  private void closeWindow() {
    if(latestStage != null)
      latestStage.close();
  }

  public void showLastWindow() {
    Stage temp = latestStage;
    latestStage = previousStage;
    previousStage = temp;
    previousStage.close();
    latestStage.show();
  }

  public void setStage(Stage stage) {
    this.latestStage = stage;
  }
}