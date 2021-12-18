package com.colliu.colliu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import miscellaneous.Data;
import user.*;

/**
 * * * * * * * * * * * *
 * DOCUMENTATION HERE. *
 * * * * * * * * * * * *
 **/

public class MasterController {

  UserMethods userMethods = new UserMethods(this);
  final Data json = new Data();
  final String eventPage = "EventPage.fxml";
  final String registerPage = "account-type-detection.fxml";
  private Parent fxmlLoader;
  private double xOffset = 0;
  private double yOffset = 0;

  private boolean isClicked = false;
  private ArrayList<Event> events;

  //attributes and methods related to "LoginPage.fxml"
  @FXML
  private Pane eventList;

  @FXML
  private Button btnAttending;

  @FXML
  private ListView<?> filterList;

  @FXML
  private Pane filterPane;

  @FXML
  private TextArea txtDescription;

  @FXML
  private TextField txtSearch;

  @FXML
  private Label welcomeText;

  @FXML
  private Button forgotpassword;

  @FXML
  private TextField guEmail;

  @FXML
  private PasswordField password;

  @FXML
  private Button login;

  @FXML
  private Label warningLabel;

  @FXML
  private Button registeraccount;

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
          showEventPage(event);
        } else {
          warningLabel.setText("Wrong password.");
        }
      } else {
        warningLabel.setText("Not existing email address.");
      }
    }
  }
  @FXML
  private TextField tfGraduationYear;

  @FXML
  void onForgotPasswordClick(ActionEvent event) throws IOException {
    closeWindow(event);
    showWindow("forgot-password.fxml");
  }

  @FXML
  private TextField tfLastName;

  @FXML
  private PasswordField tfPassword;

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
  private PasswordField tfConfirmPassword;

  @FXML
  private TextField fp_email;
  @FXML
  private TextField tfProgram;

  @FXML
  void onForgotPassword(ActionEvent event) {

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
  private Label fp_warningLabel;

  @FXML
  private Label lblStaffLastNameError;

  @FXML
  void onGUEmail(ActionEvent event) {

  }

  @FXML
  private Pane titleBar;

  @FXML
  void onContinueClick(ActionEvent event) throws IOException {
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
  private Button closWindow;

  @FXML // Write comment here
  protected void onHelloButtonClick(ActionEvent event) throws IOException {
    closeWindow(event);
    showWindow("homepage.fxml");
  }

  @FXML
  void onPassword(ActionEvent event) {

  }

  @FXML
  void onSignUpClick(ActionEvent event) throws IOException {
    closeWindow(event);
    showWindow("account-type-detection.fxml");
  }

  //attributes and methods related to "new-password-sent.fxml"

  @FXML
  void backToLoginPageClick(ActionEvent event) throws IOException {
    closeWindow(event);
    showWindow("LoginPage.fxml");
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
    fileName = "fxml/" + (fileName.endsWith(".fxml") ? fileName : fileName + ".fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(Master.class.getResource(fileName));
    Scene scene = new Scene(fxmlLoader.load());
    //fxmlLoader = FXMLLoader.load(getClass().getResource(fileName));
    Stage stage = new Stage();
    stage.initStyle(StageStyle.UNDECORATED);
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
  void openStudentRegistration(ActionEvent event) throws Exception {
    closeWindow(event);
    showWindow("student-registration.fxml");
  }

  @FXML
  void openStaffRegistration(ActionEvent event) throws Exception{ //button that opens staff registration screen
    closeWindow(event);
    showWindow("staff-registration.fxml");
  }

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
    if (!tfPassword.getText().equals(tfConfirmPassword.getText())){
      lblPasswordError.setText("Passwords does not match");
      throw new Exception("Passwords does not match");
    }
    else {
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
      showWindow("LoginPage.fxml");
    } catch (Exception exception)  {
      // Do something here.
    }
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
      String staffTitle = tfStaffTitle.getText();
      userMethods.createStaff(email, password, name, surname, department, staffTitle);
      showEventPage(event);
    } catch (Exception exception) {
      // Do something here.
    }
  }

  @FXML
  void showLogin(ActionEvent event) throws Exception {
    closeWindow(event);
    showWindow("homepage.fxml");
  }

  @FXML
  void filterEvent(MouseEvent event) {

  }

  @FXML
  void logoutUser(ActionEvent event) {

  }


  @FXML
  void openSettings(MouseEvent event) {

  }

  @FXML
  void viewAnnouncements(MouseEvent event) {

  }

  @FXML
  private Label lblDescription;


  @FXML
  void attendEvent(ActionEvent event) {
    txtSearch.setText("Test");
  }

  @FXML
  void showAttendingEvents(ActionEvent event) throws Exception {

    ArrayList<User> test = new ArrayList<>();
    ArrayList<Event> test2 = new ArrayList<>();
    //test.add(new Student("test@email.gu.se", "asdasdas1238!A", "Hey", "Ho", 1980, "SEM"));
    test.add(new Student("Student@.gu.se", "assdadaSAD2138!", "Stud", "ent", 1980, "SEM"));
    test.add(new Staff("Staff@.gu.se", "13asdkASD98213!", "Teach", "er", "IT", "Prof."));
    test.add(new Administrator("Admin@.gu.se", "NotPassWord123!", "Admin", "Istrator", 1920, "SEM"));
    test2.add(new Event(0, "Cool Event For Free", new Date(), "School Property", 2));
    test2.add(new Event(2, "Cool Event For Free", new Date(), "School Property", 2));
    test2.add(new Event(3, "Cool Event For Free", new Date(), "School Property", 2));
    json.saveUsers(test);
    json.saveEvents(test2);
    for (int i = 0; i < json.loadUser().size(); i++) {
      System.out.println(json.loadUser().get(i).getFirstName());
    }
  }

// Write comment here
  @FXML
  void showEventPage(ActionEvent event) throws IOException {
    closeWindow(event);
    showWindow(eventPage);
  }


  void loadEvents() throws FileNotFoundException, UnsupportedEncodingException {
    events = json.loadEvent();
    for (int i = 0; i < events.size(); i++) {
      new StackPane();
    }
  }

  @FXML
  void closeProgram(ActionEvent event) {

  }

  @FXML
  void setOnMouseDragged(MouseEvent event) {
  }

  @FXML
  void setOnMousePressed(MouseEvent event) {
  }
}