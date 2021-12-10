package com.colliu.colliu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.UserMethods;

import java.io.IOException;

public class MasterController {

  UserMethods userMethods = new UserMethods();
  Stage stage;

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


  @FXML
  protected void onHelloButtonClick(ActionEvent Event) throws IOException {
    closeWindow(Event);
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
  void onLogInClick(ActionEvent event) {
//anything we code here for login should work
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

  @FXML
  private void closeWindow(ActionEvent action) {
    ((Node)(action.getSource())).getScene().getWindow().hide();
  }

  private void showWindow(String fileName) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Master.class.getResource((fileName.endsWith(".fxml") ? fileName : fileName + ".fxml")));
    Scene scene = new Scene(fxmlLoader.load());
    Stage stage = new Stage();
    stage.setTitle("ColliU - " + fileName);
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
  void openStudentRegistration(ActionEvent event) throws Exception{   //Button that opens student registration screen
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
  void registerStudent(ActionEvent event) throws Exception{   // Tells user what input is wrong if it is, otherwise it creates a student
    if (tfStudentEmail.getText().isBlank()){
      lblStudentEmail.setText("Email cannot be blank");
    }
    else if(!tfStudentEmail.getText().endsWith("student.gu.se")){
      lblStudentEmail.setText("Email must end with: 'student.gu.se'");
    }
    else if(tfStudentEmail.getText().contains(" ")){
      lblStudentEmail.setText("Email cannot contain any blank spaces");
    }
    else{
      lblStudentEmail.setText("");
    }
    /*
    if (userMethods.validatePassword(tfConfirmPassword.getText()) == false){
        lblPasswordError.setText("Passwords does not match");
        throw new Exception();
    }

     */
    if (tfFirstName.getText().isBlank()) {
      lblNameError.setText("First name cannot be blank");
    }
    else if (tfFirstName.getText().matches(("(.*[0-9].*)"))){
      lblNameError.setText("First name cannot contain any numbers.");
      System.out.println("error");
    }
    else{
      lblNameError.setText("");
    }
    if (tfLastName.getText().isBlank()) {
      lblLastNameError.setText("Last name cannot be blank.");
    }
    else if (tfLastName.getText().matches(("(.*[0-9].*)"))){
      lblLastNameError.setText("Last name cannot contain any numbers.");
    }
    else{
      lblLastNameError.setText("");
    }
    if (tfPassword.getText().isBlank()) {
      lblPasswordError.setText("Password cannot be blank.");
    }
    else if (tfPassword.getText().length() < 11 || tfPassword.getText().length() > 20) {
      lblPasswordError.setText("Password must be between 12 and 20 characters.");
    }
    else if (!tfPassword.getText().matches("(.*[A-Z].*)")) {
      lblPasswordError.setText("Password must contain at least one uppercase letter.");
    }
    else if (!tfPassword.getText().matches("(.*[a-z].*)")) {
      lblPasswordError.setText("Password must contain at least one lowercase character.");
    }
    else if (!tfPassword.getText().matches("(.*[0-9].*)")) {
      lblPasswordError.setText("Password must contain at least one number.");
    }
    else if (tfPassword.getText().contains(" ")) {
      lblPasswordError.setText("Passwords cannot contain blank spaces.");
    }
    else {
      lblPasswordError.setText("");
    }
    try {
      userMethods.createStudent(tfStudentEmail.getText(), tfPassword.getText(), tfFirstName.getText(), tfLastName.getText(), Integer.parseInt(tfGraduationYear.getText()), tfProgram.getText());
    }
    catch(Exception exception)  {
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
  void registerStaff(ActionEvent event) throws Exception{ // Tells user what input is wrong if it is, otherwise it creates a staff user
    if (tfStaffEmail.getText().isBlank()){
      lblStaffEmailError.setText("Email cannot be blank");
    }
    else if(!tfStaffEmail.getText().endsWith("gu.se")){
      lblStaffEmailError.setText("Email must end with 'gu.se'");
    }
    else if(tfStaffEmail.getText().contains(" ")){
      lblStaffNameError.setText("Email cannot contain any blank spaces");
    }

    if (tfStaffFirstName.getText().isBlank()){
      lblStaffNameError.setText("First name cannot be blank");
    }
    else if (tfStaffFirstName.getText().matches("(.*[0-9].*)")) {
      lblStaffNameError.setText("First name cannot contain any numbers");
    }
    else {
      lblStaffNameError.setText("");
    }
    if (tfStaffLastName.getText().isBlank()){
      lblStaffLastNameError.setText("Last name cannot be blank");
    }
    else if (tfStaffLastName.getText().matches("(.*[0-9].*)")){
      lblStaffLastNameError.setText("Last name cannot contain any numbers");
    }
    else{
      lblStaffLastNameError.setText("");
    }

    if (tfStaffPassword.getText().length() < 11 || tfStaffPassword.getText().length() > 20){
      lblStaffPasswordError.setText("Password must be between 12 and 20 characters.");
    }
    else if (!tfStaffPassword.getText().matches("(.*[A-Z].*)")){
      lblStaffPasswordError.setText("Password must contain at least one uppercase letter");
    }
    else if (!tfStaffPassword.getText().matches("(.*[a-z].*)")){
      lblStaffPasswordError.setText("Password must contain at least one lowercase letter");
    }
    else if (!tfStaffPassword.getText().matches("(.*[0-9].*)")){
      lblStaffPasswordError.setText("Password must contain at least one number.");
    }
    else if (tfStaffPassword.getText().contains(" ")){
      lblStaffPasswordError.setText("Password cannot contain any blank spaces");
    }
    else {
      lblStaffPasswordError.setText("");
    }

    //is department and title allowed to be empty? otherwise what are they allowed to be
    /*
    if (userMethods.validatePassword(tfConfirmPassword.getText()) == false){
      lblStaffPasswordError.setText("Passwords does not match");
      throw new Exception();
    }

     */

    try{
      userMethods.createStaff(tfStaffEmail.getText(), tfStaffPassword.getText(), tfStaffFirstName.getText(), tfStaffLastName.getText(), tfStaffDepartment.getText(), tfStaffTitle.getText());
    } catch(Exception exception){
    }

  }
}


