package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.w3c.dom.Text;
import user.Administrator;
import user.Staff;
import user.Student;
import user.User;

/**
 *
 */
public class ProfileController {
  MasterController master;

  public void setMaster(MasterController master) {
    this.master = master;
  }

  /** Updates profile tab.
   * Updates the profile tab when we need to.
   */

  public void updateProfileTab() {
    int userType = master.userMethods.getCurrentUserType();
switch(userType) {
  case 1:
      Student loggedInStudent = (Student) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
      lblYourUserClass.setText("Student");
      paneAdminControls.setVisible(false);
      lblYourProgram.setText(loggedInStudent.getProgram());
      lblYourExamYear.setText("Class of " + loggedInStudent.getGraduationYear());
      lblYourName.setText(loggedInStudent.getLastName() + " " + loggedInStudent.getFirstName());
      lblYourEmail.setText(loggedInStudent.getEmail());
      break;
  case 2:
      Administrator loggedInAdmin = (Administrator) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
      lblYourUserClass.setText(String.valueOf(loggedInAdmin.getClass()));
      lblYourProgram.setText(loggedInAdmin.getProgram());
      lblYourExamYear.setText("Class of " + loggedInAdmin.getGraduationYear());
      paneAdminControls.setVisible(true);
      lblYourName.setText(loggedInAdmin.getLastName() + " " + loggedInAdmin.getFirstName());
      lblYourEmail.setText(loggedInAdmin.getEmail());
      break;
  case 3:
      Staff loggedInStaff = (Staff) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
      lblYourUserClass.setText("Staff");
      paneAdminControls.setVisible(false);
      lblYourProgram.setText(loggedInStaff.getDepartment());
      lblYourExamYear.setVisible(false);
      lblYourName.setText(loggedInStaff.getLastName() + " " + loggedInStaff.getFirstName());
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
    master.userMethods.promoteStudentToAdmin(tfAdministratorConstrols.getText());
    lblAdminConfirmationLabel.setVisible(true);
    lblAdminConfirmationLabel.setText("User was successfully promoted.");
  }

  @FXML
  void onButtonPressUnbanUser() throws Exception {
    master.userMethods.unbanUser(tfAdministratorConstrols.getText());
    lblAdminConfirmationLabel.setVisible(true);
    lblAdminConfirmationLabel.setText("User was successfully unbanned.");
  }

  @FXML
  void onButtonPressBanUser() throws Exception {
    master.userMethods.banUser(tfAdministratorConstrols.getText());
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
      int userType = master.userMethods.getCurrentUserType();

      if (userType == 1) {
        Student loggedInStudent = (Student) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
        loggedInStudent.setFirstName(tfEnterNewFirstName.getText());
      } else if (userType == 3) {
        Staff loggedInStaff = (Staff) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
        loggedInStaff.setFirstName(tfEnterNewFirstName.getText());
      } else if (userType == 2) {
        Administrator loggedInAdmin = (Administrator) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
        loggedInAdmin.setFirstName(tfEnterNewFirstName.getText());
      }
      lblFirstNameChangedSuccessfully.setVisible(true);
      lblFirstNameMatch.setVisible(false);
      updateProfileTab();
      master.json.saveUsers(master.userMethods.activeUsers);
    } else {
      lblFirstNameMatch.setVisible(true);
      lblFirstNameChangedSuccessfully.setVisible(false);
    }


  }

  @FXML
 void onButtonChangeLastName(ActionEvent event) throws IOException {
    if (tfNewLastName.getText().equals(tfRepeatNewLastName.getText())) {
      int userType = master.userMethods.getCurrentUserType();

      if (userType == 1) {
        Student loggedInStudent = (Student) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
        loggedInStudent.setLastName(tfNewLastName.getText());
      } else if (userType == 3) {
        Staff loggedInStaff = (Staff) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
        loggedInStaff.setLastName(tfNewLastName.getText());
      } else if (userType == 2) {
        Administrator loggedInAdmin = (Administrator) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
        loggedInAdmin.setLastName(tfNewLastName.getText());
      }
      lblLastNameChangedSuccessfully.setVisible(true);
      lblLastNameMatch.setVisible(false);
      updateProfileTab();
      master.json.saveUsers(master.userMethods.activeUsers);
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
    if (!master.userMethods.checkPasswordComplexity(tfNewPassword.getText())) {
      lblPasswordComplexityError.setVisible(true);
    } else {
      if (master.userMethods.validatePassword(tfCurrentPassword.getText()) && tfNewPassword.getText().equals(tfRepeatNewPassword.getText()) ) {
        int userType = master.userMethods.getCurrentUserType();

        if (userType == 1) {
          Student loggedInStudent = (Student) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
          loggedInStudent.setPassword(tfNewPassword.getText());
        } else if (userType == 3) {
          Staff loggedInStaff = (Staff) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
          loggedInStaff.setPassword(tfNewPassword.getText());
        } else if (userType == 2) {
          Administrator loggedInAdmin = (Administrator) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
          loggedInAdmin.setPassword(tfNewPassword.getText());
        }

        lblChangePasswordError.setVisible(false);
        lblPasswordChangedSuccessfully.setVisible(true);
        lblPasswordComplexityError.setVisible(false);
        master.json.saveUsers(master.userMethods.activeUsers);
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

    int userType = master.userMethods.getCurrentUserType();
    if (userType == 1) {
      Student loggedInStudent = (Student) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
      loggedInStudent.setProgram("Software engineering and management");
    } else if (userType == 2) {
      Administrator loggedInAdmin = (Administrator) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
      loggedInAdmin.setProgram("Software engineering and management");
    }
    updateProfileTab();
    master.json.saveUsers(master.userMethods.activeUsers);
  }

  @FXML
    void onButtonPressSystemvetenskap() throws Exception {
    int userType = master.userMethods.getCurrentUserType();
    if (userType == 1) {
      Student loggedInStudent = (Student) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
      loggedInStudent.setProgram("Systemvetenskap");
    } else if (userType == 2) {
      Administrator loggedInAdmin = (Administrator) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
      loggedInAdmin.setProgram("Systemvetenskap");
    }
    updateProfileTab();
    master.json.saveUsers(master.userMethods.activeUsers);
  }

  @FXML
    void onButtonPressDatavetenskap() throws Exception {
    int userType = master.userMethods.getCurrentUserType();
    if (userType == 1) {
      Student loggedInStudent = (Student) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
      loggedInStudent.setProgram("Datavetenskap");
    } else if (userType == 2) {
      Administrator loggedInAdmin = (Administrator) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
      loggedInAdmin.setProgram("Datavetenskap");
    }
    updateProfileTab();
    master.json.saveUsers(master.userMethods.activeUsers);
  }

  @FXML
    void onButtonPressKognitionsvetenskap() throws Exception {
    int userType = master.userMethods.getCurrentUserType();
    if (userType == 1) {
      Student loggedInStudent = (Student) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
      loggedInStudent.setProgram("Kognitionsvetenskap");
    } else if (userType == 2) {
      Administrator loggedInAdmin = (Administrator) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
      loggedInAdmin.setProgram("Kognitionsvetenskap");
    }
    updateProfileTab();
    master.json.saveUsers(master.userMethods.activeUsers);
  }

  @FXML
    void onButtomPressChangeGraduationYear() throws Exception {
    int userType = master.userMethods.getCurrentUserType();

    if (!tfNewGraduationYear.getText().matches("(.*[0-9].*)")) {
      lblGraduationYearError.setVisible(true);
    } else {
      if (userType == 1) {
        Student loggedInStudent = (Student) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
        loggedInStudent.setGraduationYear(Integer.parseInt(tfNewGraduationYear.getText()));
      } else if (userType == 2) {
        Administrator loggedInAdmin = (Administrator) master.userMethods.activeUsers.get(master.userMethods.findUser(master.userMethods.getLoggedInUser()));
        loggedInAdmin.setGraduationYear(Integer.parseInt(tfNewGraduationYear.getText()));
      }
      lblGraduationYearError.setVisible(false);
      updateProfileTab();
      master.json.saveUsers(master.userMethods.activeUsers);
    }
  }

  //Testing stuff
  @FXML
    private Button btbTestingUserInfo;
  @FXML
    private Label lblTest;

  @FXML
void onButtonClickTestUserInfo(ActionEvent event) throws Exception { //What should load when you go to this javaFX scene
    // User currentUser2 = master.userMethods.getLoggedInUser();
    lblTest.setText(String.valueOf(master.userMethods.activeUsers.get(5).getClass()));

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



 }