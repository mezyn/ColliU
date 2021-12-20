package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.w3c.dom.Text;
import user.Student;
import user.User;
import user.UserMethods;

import java.io.IOException;

public class ProfileController {

   MasterController master;

    public void setMaster(MasterController master) {
        this.master = master;
    }


    @FXML
    private Button btnReturn;
    @FXML
    private Label Announcements;

 @FXML
 void onReturnButtonClick(ActionEvent event) throws Exception {
 master.showEventPage();
 }


 //Profile tab admin controlls
    @FXML
    private Button btnBanUser;
    @FXML
    private Button btnUnbanUser;
    @FXML
    private Button btnPromoteUserToAdmin;
    @FXML
    private Label lblAdministratorControls;
    @FXML
    private TextField tfAdministratorConstrols;

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
      User currentUser = master.userMethods.getUserByEmail(master.getCurrentUserEmail());
      currentUser.setFirstName(tfEnterNewFirstName.getText());

      lblFirstNameChangedSuccessfully.setVisible(true);
      lblFirstNameMatch.setVisible(false);

     } else {
      lblFirstNameMatch.setVisible(true);
      lblFirstNameChangedSuccessfully.setVisible(false);

     }


    }
 @FXML
 void onButtonChangeLastName(ActionEvent event) throws IOException {

      if (tfNewLastName.getText().equals(tfRepeatNewLastName.getText())) {
      User currentUser = master.userMethods.getUserByEmail(master.getCurrentUserEmail());
      currentUser.setLastName(tfNewLastName.getText());
      lblLastNameChangedSuccessfully.setVisible(true);
      lblLastNameMatch.setVisible(false);
    } else {
      lblLastNameMatch.setVisible(true);
      lblLastNameChangedSuccessfully.setVisible(false);
    }


 }


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
    User temporaryUser = master.userMethods.getUserByEmail(master.getCurrentUserEmail());
    temporaryUser.setPassword(tfNewPassword.getText());
    lblChangePasswordError.setVisible(false);
    lblPasswordChangedSuccessfully.setVisible(true);
    lblPasswordComplexityError.setVisible(false);
   } else {
    lblChangePasswordError.setVisible(true);
   }
  }


 }


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

    void onButtonPressSEM() throws Exception {
        Student temporaryUser = (Student) master.userMethods.getUserByEmail(master.getCurrentUserEmail());
        temporaryUser.setProgram("SEM");
    }
    void onButtonPressSystemvetenskap() throws Exception {
        Student temporaryUser = (Student) master.userMethods.getUserByEmail(master.getCurrentUserEmail());
        temporaryUser.setProgram("Systemvetenskap");
    }
    void onButtonPressDatavetenskap() throws Exception {
        Student temporaryUser = (Student) master.userMethods.getUserByEmail(master.getCurrentUserEmail());
        temporaryUser.setProgram("Datavetenskap");
    }
    void onButtonPressKognitionsvetenskap() throws Exception {
        Student temporaryUser = (Student) master.userMethods.getUserByEmail(master.getCurrentUserEmail());
        temporaryUser.setProgram("Kognitionsvetenskap");
    }

    void onButtomPressChangeGraduationYear() throws Exception {
        Student temporaryUser = (Student) master.userMethods.getUserByEmail(master.getCurrentUserEmail());
        if (!tfNewGraduationYear.getText().matches("(.*[0-9].*)")) {
            lblGraduationYearError.setVisible(true);
        } else {
            temporaryUser.setGraduationYear(Integer.parseInt(tfNewGraduationYear.getText()));
            lblGraduationYearError.setVisible(false);
        }
    }

    //Testing stuff
    @FXML
    private Button btnCreateErikHarring;
    @FXML
    private Button btnCreateKrisAdmin;
    @FXML
    private Button btbTestingUserInfo;
    @FXML
    private Label lblTest;

    @FXML
 void onButtonClickTestUserInfo(ActionEvent event) throws IOException {              //What should load when you go to this javaFX scene
  /* lblYourName.setText(userMethods.getUserByEmail(currentUserEmail).getFirstName() + " " + userMethods.getUserByEmail(currentUserEmail).getLastName());
  lblYourEmail.setText(currentUserEmail);
  // lblYourProgam.setText(userMethods.getUserByEmail(currentUserEmail).getProgram()); //does not exist yet
  // lblYourExamYear.setText(userMethods.getUserByEmail(currentUserEmail).getExamYear()); //does not exist yet
  lblYourUserClass.setText(String.valueOf(userMethods.getUserByEmail(currentUserEmail).getClass()));
  if (currentUserEmail.equals("guskris@student.gu.se")) {  //will be userMethods.getUserByEmail(currentUserEmail).getType().equals("2")) when implemented
   paneAdminControls.setVisible(true);
  } else {
   paneAdminControls.setVisible(false);
 */
    User currentUser2 = master.userMethods.getUserByEmail(master.getCurrentUserEmail());
     lblTest.setText(currentUser2.toString());
 }
  @FXML
  void onButtonCreateErikHarring(ActionEvent event) throws Exception {
   /*userMethods.createStudent("gusharrier@student.gu.se", "ErikHarring1337", "Erik", "Harring", 2024, "SEM");
   currentUserEmail = "gusharrier@student.gu.se";
   lblTest.setText("Erik has been created");

    */
  }

  //FOR TESTING
  @FXML
  void onButtonCreateKrisAdmin(ActionEvent event) throws Exception {
  /* userMethods.createAdministrator("guskris@student.gu.se", "ErikHarring1337", "Kris", "Admin", 2024, "SEM");
   currentUserEmail = "guskris@student.gu.se";
   lblTest.setText("Kris has been created");

   */
  }

 }




