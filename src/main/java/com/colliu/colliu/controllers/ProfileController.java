package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import org.w3c.dom.Text;
import user.Administrator;
import user.Staff;
import user.Student;

public class ProfileController {
  MasterController master;

  public void setMaster(MasterController master) {
    this.master = master;
  }

  /** Updates profile tab.
   * Updates the profile tab when we need to.
   */

  public void updateProfileTab() {
    int userType = master.getCurrentUser().getType();
    switch (userType)  {
      case 1:
        Student loggedInStudent = (Student) master.getCurrentUser();
        lblYourUserClass.setText("Student");
        paneAdminControls.setVisible(false);
        lblYourProgram.setText(loggedInStudent.getProgram());
        lblYourExamYear.setText("Class of " + loggedInStudent.getGraduationYear());
        lblYourName.setText(loggedInStudent.getLastName() + " " + loggedInStudent.getFirstName());
        lblYourEmail.setText(loggedInStudent.getEmail());
        break;
      case 2:
        Administrator loggedInAdmin = (Administrator) master.getCurrentUser();
        lblYourUserClass.setText("Student Administrator");
        lblYourProgram.setText(loggedInAdmin.getProgram());
        lblYourExamYear.setText("Class of " + loggedInAdmin.getGraduationYear());
        //paneAdminControls.setVisible(true);
        lblYourName.setText(loggedInAdmin.getLastName() + " " + loggedInAdmin.getFirstName());
        lblYourEmail.setText(loggedInAdmin.getEmail());
        break;
      case 3:
        Staff loggedInStaff = (Staff) master.user;
        lblYourUserClass.setText("Staff");
        //paneAdminControls.setVisible(false);
        lblYourProgram.setText("Change this later as department was removed hehe");
        lblYourExamYear.setVisible(false);
        //tabChangeProgram.setDisable(true);
        //lblYourName.setText(loggedInStaff.getLastName() + " " + loggedInStaff.getFirstName());
        lblYourEmail.setText(loggedInStaff.getEmail());
        break;
      default:
        lblYourUserClass.setText("Shit be fucked up");
        lblYourProgram.setText("Shit be fucked up");
        lblYourExamYear.setText("Shit be fucked up");
        paneAdminControls.setVisible(true);
    }
  }

  @FXML
  private Tab tabChangeProgram;

  @FXML
    private Button btnReturn;
  @FXML
    private Label Announcements;

  @FXML
 void onReturnButtonClick(ActionEvent event) throws Exception {
    master.showEventPage();
  }
  //Profile tab
  //Profile tab
  //Profile tab

  @FXML
    private Label lblYourName;
  @FXML
    private Label lblYourEmail;
  @FXML
    private Label lblYourProgram;
  @FXML
    private Label lblYourExamYear;
  @FXML
    private Label lblYourUserClass;

  //Profile tab admin controlls
  //Profile tab admin controlls
  //Profile tab admin controlls

  @FXML
    private Pane paneAdminControls;
  @FXML
    private Button btnBanUser;
  @FXML
    private Button btnUnbanUser;
  @FXML
    private Button btnPromoteUserToAdmin;
  @FXML
    private Label lblAdministratorControls;
  @FXML
  private Label lblAdminConfirmationLabel;
  @FXML
    private TextField tfAdministratorConstrols;

  @FXML
  void onButtonPressPromoteUserToAdmin() throws Exception {
    master.makeAdmin(tfAdministratorConstrols.getText());
    lblAdminConfirmationLabel.setVisible(true);
    lblAdminConfirmationLabel.setText("User was successfully promoted.");
  }

  @FXML
  void onButtonPressUnbanUser() throws Exception {
    master.unbanUser(tfAdministratorConstrols.getText());
    lblAdminConfirmationLabel.setVisible(true);
    lblAdminConfirmationLabel.setText("User was successfully unbanned.");
  }

  @FXML
  void onButtonPressBanUser() throws Exception {
    master.banUser(tfAdministratorConstrols.getText());
    lblAdminConfirmationLabel.setVisible(true);
    lblAdminConfirmationLabel.setText("User was successfully banned.");
  }

  //Change name tab
  //Change name tab
  //Change name tab

  @FXML
    private Button btnChangeLastName;
  @FXML
    private Label lblFirstNameChangedSuccessfully;
  @FXML
    private Label lblFirstNameMatch;
  @FXML
    private Label lblLastNameMatch;
  @FXML
    private Label lblLastNameChangedSuccessfully;
  @FXML
    private Text txtChangeName;
  @FXML
    private TextField tfEnterNewFirstName;
  @FXML
    private TextField tfNewLastName;
  @FXML
    private TextField tfRepeatNewLastName;
  @FXML
    private TextField tfRepeatFirstName;

  @FXML
    void onButtonChangeFirstName(ActionEvent event) throws IOException {
    if (tfEnterNewFirstName.getText().equals(tfRepeatFirstName.getText())) {
      int userType = master.getCurrentUser().getType();

      if (userType == 1) {
        Student loggedInStudent = (Student) master.user;
        loggedInStudent.setFirstName(tfEnterNewFirstName.getText());
      } else if (userType == 3) {
        Staff loggedInStaff = (Staff) master.user;
        loggedInStaff.setFirstName(tfEnterNewFirstName.getText());
      } else if (userType == 2) {
        Administrator loggedInAdmin = (Administrator) master.user;
        loggedInAdmin.setFirstName(tfEnterNewFirstName.getText());
      }
      lblFirstNameChangedSuccessfully.setVisible(true);
      lblFirstNameMatch.setVisible(false);
      updateProfileTab();
      master.saveUsers();
    } else {
      lblFirstNameMatch.setVisible(true);
      lblFirstNameChangedSuccessfully.setVisible(false);
    }


  }

  @FXML
 void onButtonChangeLastName(ActionEvent event) throws IOException {
    if (tfNewLastName.getText().equals(tfRepeatNewLastName.getText())) {
      int userType = master.user.getType();

      if (userType == 1) {
        Student loggedInStudent = (Student) master.user;
        loggedInStudent.setLastName(tfNewLastName.getText());
      } else if (userType == 3) {
        Staff loggedInStaff = (Staff) master.user;
        loggedInStaff.setLastName(tfNewLastName.getText());
      } else if (userType == 2) {
        Administrator loggedInAdmin = (Administrator) master.user;
        loggedInAdmin.setLastName(tfNewLastName.getText());
      }
      lblLastNameChangedSuccessfully.setVisible(true);
      lblLastNameMatch.setVisible(false);
      updateProfileTab();
      master.saveUsers();
    } else {
      lblLastNameMatch.setVisible(true);
      lblLastNameChangedSuccessfully.setVisible(false);
    }
  }

  //Change password tab
  //Change password tab
  //Change password tab

  @FXML
    private Button btnChangePasword;
  @FXML
    private Label lblChangePasswordError;
  @FXML
    private Label lblPasswordChangedSuccessfully;
  @FXML
    private Label lblPasswordComplexityError;
  @FXML
    private PasswordField tfCurrentPassword;
  @FXML
    private PasswordField tfNewPassword;
  @FXML
    private PasswordField tfRepeatNewPassword;
  @FXML
    private Text txtChangePassword;

  @FXML
 void onButtonChangePassword(ActionEvent event) throws Exception {
    if (!master.checkPassword(tfNewPassword.getText())) {
      lblPasswordComplexityError.setVisible(true);
    } else {
      if (master.validateLogin(tfCurrentPassword.getText(), master.getCurrentUser().getEmail()) && tfNewPassword.getText().equals(tfRepeatNewPassword.getText()) ) {
        int userType = master.getCurrentUser().getType();

        if (userType == 1) {
          Student loggedInStudent = (Student) master.user;
          loggedInStudent.setPassword(tfNewPassword.getText());
        } else if (userType == 3) {
          Staff loggedInStaff = (Staff) master.user;
          loggedInStaff.setPassword(tfNewPassword.getText());
        } else if (userType == 2) {
          Administrator loggedInAdmin = (Administrator) master.user;
          loggedInAdmin.setPassword(tfNewPassword.getText());
        }

        lblChangePasswordError.setVisible(false);
        lblPasswordChangedSuccessfully.setVisible(true);
        lblPasswordComplexityError.setVisible(false);
        master.saveUsers();
      } else {
        lblChangePasswordError.setVisible(true);
      }
    }
  }

  //Change program tab
  //Change program tab
  //Change program tab

  @FXML
    private Button btnDataVetenskap;
  @FXML
    private Button btnSoftwareEngineering;
  @FXML
    private Button btnSystemvetenskap;
  @FXML
    private Label lblSuccessfullyChangedProgam;
  @FXML
    private TextField tfNewGraduationYear;
  @FXML
    private Button btnChangeGraduationYear;
  @FXML
    private Label lblGraduationYearError;

  @FXML
    void onButtonPressSEM() throws Exception {
    int userType = master.getCurrentUser().getType();
    if (userType == 1) {
      Student loggedInStudent = (Student) master.user;
      loggedInStudent.setProgram("Software engineering and management");
    } else if (userType == 2) {
      Administrator loggedInAdmin = (Administrator) master.user;
      loggedInAdmin.setProgram("Software engineering and management");
    }
    updateProfileTab();
    master.saveUsers();
  }

  @FXML
    void onButtonPressSystemvetenskap() throws Exception {
    int userType = master.getCurrentUser().getType();
    if (userType == 1) {
      Student loggedInStudent = (Student) master.user;
      loggedInStudent.setProgram("Systemvetenskap");
    } else if (userType == 2) {
      Administrator loggedInAdmin = (Administrator) master.user;
      loggedInAdmin.setProgram("Systemvetenskap");
    }
    updateProfileTab();
    master.saveUsers();
  }

  @FXML
    void onButtonPressDatavetenskap() throws Exception {
    int userType = master.getCurrentUser().getType();
    if (userType == 1) {
      Student loggedInStudent = (Student) master.user;
      loggedInStudent.setProgram("Datavetenskap");
    } else if (userType == 2) {
      Administrator loggedInAdmin = (Administrator) master.user;
      loggedInAdmin.setProgram("Datavetenskap");
    }
    updateProfileTab();
    master.saveUsers();
  }

  @FXML
    void onButtonPressKognitionsvetenskap() throws Exception {
    int userType = master.getCurrentUser().getType();
    if (userType == 1) {
      Student loggedInStudent = (Student) master.user;
      loggedInStudent.setProgram("Kognitionsvetenskap");
    } else if (userType == 2) {
      Administrator loggedInAdmin = (Administrator) master.user;
      loggedInAdmin.setProgram("Kognitionsvetenskap");
    }
    updateProfileTab();
    master.saveUsers();
  }

  @FXML
    void onButtomPressChangeGraduationYear() throws Exception {
    int userType = master.getCurrentUser().getType();

    if (!tfNewGraduationYear.getText().matches("(.*[0-9].*)")) {
      lblGraduationYearError.setVisible(true);
    } else {
      if (userType == 1) {
        Student loggedInStudent = (Student) master.user;
        loggedInStudent.setGraduationYear(Integer.parseInt(tfNewGraduationYear.getText()));
      } else if (userType == 2) {
        Administrator loggedInAdmin = (Administrator) master.user;
        loggedInAdmin.setGraduationYear(Integer.parseInt(tfNewGraduationYear.getText()));
      }
      lblGraduationYearError.setVisible(false);
      updateProfileTab();
      master.saveUsers();
    }
  }

  //Testing stuff
  //Testing stuff
  //Testing stuff

  @FXML
    private Button btbTestingUserInfo;
  @FXML
    private Label lblTest;

  @FXML
void onButtonClickTestUserInfo(ActionEvent event) throws Exception { //What should load when you go to this javaFX scene
    // User currentUser2 = master.userMethods.getLoggedInUser();
    lblTest.setText(String.valueOf(master.user.getClass()));

/*
    master.userMethods.createAdministrator("admin32@student.gu.se", "ErikHarring1337", "Admin", "One", 2024, "SEM");
    master.userMethods.createAdministrator("admin12@student.gu.se", "ErikHarring1337", "Admin", "TWO", 2026, "Ingenting");
    master.userMethods.createStudent("student13@student.gu.se", "ErikHarring1337", "Student", "One", 2021, "SEM");
    master.userMethods.createStudent("student12@student.gu.se", "ErikHarring1337", "Student", "TWO", 2026, "Something");
    master.userMethods.createStaff("staff23@staff.gu.se","ErikHarring1337", "Stotsky", "One", "IT", "Master of the universe");
    master.userMethods.createStaff("staff123@staff.gu.se","ErikHarring1337", "Stotsky", "Two", "IT", "Master of the universe");
    master.json.saveUsers(master.userMethods.activeUsers);

 */
  }


  @FXML
  private TextField tfName;

  @FXML
  private TextField tfNameConfirm;

  @FXML
  private TextField tfSurname;

  @FXML
  private TextField tfSurnameConfirm;

  @FXML
  private PasswordField pfPassword;

  @FXML
  private PasswordField pfPasswordConfirm;

  @FXML
  private Button btnCloseSettings;

  @FXML
  private Button btnSaveSettings;

  @FXML
  private ComboBox<String> cbProgram;

  @FXML
  private ComboBox<Integer> cbGraduationYear;

  @FXML
  private Label lblWarningName;

  @FXML
  private Label lblWarningSurname;

  @FXML
  private Label lblWarningPassword;

  @FXML
  private Label lblWarningStudies;

  private boolean nameCheck = true;
  private boolean surnameCheck = true;
  private boolean passwordCheck = true;
  private boolean programCheck = true;
  private boolean graduationCheck = true;

  @FXML
  void onTextfieldType(KeyEvent event) {
    String normalField = "-fx-background-color:#FFF; -fx-border-color:#DDD; -fx-border-radius: 5; -fx-text-fill:#333;";
    String badField = "-fx-background-color:#FFF; -fx-border-color:rgb(246, 168, 166); -fx-border-radius: 5; -fx-text-fill:#333;";
    String goodField = "-fx-background-color:#FFF; -fx-border-color:rgb(192,236,204); -fx-border-radius: 5; -fx-text-fill:#333;";
    if (event.getSource() instanceof PasswordField passwordField) {
      System.out.println("Typing in pw field: " + passwordField);
      if (pfPassword.getText().length() + pfPasswordConfirm.getText().length() == 0) {
        lblWarningPassword.setText("");
        pfPassword.setStyle(normalField);
        pfPasswordConfirm.setStyle(normalField);
        passwordCheck = true;
      } else if (!master.checkPassword(pfPassword.getText())) {
        lblWarningPassword.setWrapText(true);
        lblWarningPassword.setText("Password requires minimum 11 characters, 1 uppercase, 1 lowercase, 1 number and 1 symbol.");
        passwordCheck = false;
        pfPasswordConfirm.setStyle(badField);
        pfPassword.setStyle(badField);
      } else if (!pfPassword.getText().equals(pfPasswordConfirm.getText())) {
        lblWarningPassword.setText("Passwords don't match.");
        passwordCheck = false;
        pfPasswordConfirm.setStyle(badField);
        pfPassword.setStyle(badField);
      } else {
        lblWarningPassword.setText("");
        passwordCheck = true;
        pfPasswordConfirm.setStyle(goodField);
        pfPassword.setStyle(goodField);
      }
    } else if (event.getSource() instanceof TextField typedField) {
      if ((typedField == tfName || typedField == tfNameConfirm) && (tfName.getText().equals(tfNameConfirm.getText()) || (tfName.getText().length() + tfNameConfirm.getText().length() == 0))) {
        nameCheck = true;
        lblWarningName.setText("");
        tfNameConfirm.setStyle((tfName.getText().length() + tfNameConfirm.getText().length() == 0 ? normalField : goodField));
        tfName.setStyle((tfName.getText().length() + tfNameConfirm.getText().length() == 0 ? normalField : goodField));
      } else if ((typedField == tfName || typedField == tfNameConfirm) && !tfNameConfirm.getText().equals(tfName.getText())) {
        nameCheck = false;
        lblWarningName.setText("Names don't match.");
        tfNameConfirm.setStyle(badField);
        tfName.setStyle(badField);
      } else if ((typedField == tfSurname || typedField == tfSurnameConfirm) && (tfSurname.getText().equals(tfSurnameConfirm.getText()) || (tfSurname.getText().length() + tfSurnameConfirm.getText().length() == 0))) {
        surnameCheck = true;
        lblWarningSurname.setText("");
        tfSurnameConfirm.setStyle((tfSurname.getText().length() + tfSurnameConfirm.getText().length() == 0 ? normalField : goodField));
        tfSurname.setStyle((tfSurname.getText().length() + tfSurnameConfirm.getText().length() == 0 ? normalField : goodField));
      } else if ((typedField == tfSurname || typedField == tfSurnameConfirm) && !tfNameConfirm.getText().equals(tfSurname.getText())) {
        surnameCheck = false;
        lblWarningSurname.setText("Surnames don't match.");
        tfSurname.setStyle(badField);
        tfSurnameConfirm.setStyle(badField);
      }
    }
    checkSavePossible();
  }

  @FXML
  void onComboBoxSelect(ActionEvent event) {

  }

  @FXML
  void closeSettings(ActionEvent event) {

  }


  private void checkSavePossible() {
    String cancelButton = "-fx-background-color:rgb(246, 168, 166);";
    String normalButton = "-fx-background-color:#DDD;";

    boolean noChanges = (tfName.getText().length() + tfNameConfirm.getText().length()
        + tfSurname.getText().length() + tfSurnameConfirm.getText().length()
        + pfPassword.getText().length() + pfPasswordConfirm.getText().length()) == 0;

    if (noChanges) {
      System.out.println("Changing savesettings and closesettings..");
      btnSaveSettings.setDisable(true);
      btnCloseSettings.setStyle(normalButton);
      btnCloseSettings.setText("Close");
    } else if (nameCheck && surnameCheck && passwordCheck && programCheck && graduationCheck) {
      btnSaveSettings.setDisable(false);
      btnCloseSettings.setText("Cancel");
      btnCloseSettings.setStyle(cancelButton);
    } else {
      btnSaveSettings.setDisable(true);
      btnCloseSettings.setText("Cancel");
      btnCloseSettings.setStyle(cancelButton);
    }
  }

  private void toggleClose() {

  }

  @FXML
  void saveSettings(ActionEvent event) {

  }

  @FXML
  void onButtonEntered(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(0.8);
  }

  @FXML
  void onButtonExited(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(1);
  }

 }