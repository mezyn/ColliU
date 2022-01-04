package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import java.time.Year;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import miscellaneous.Info;
import miscellaneous.Style;
import user.Student;
import user.User;
import user.UserMethods;
import static java.lang.Math.abs;

public class ProfileController {
  private MasterController master;
  private UserMethods userMethods;
  private User currentUser;
  private User oldUser;
  private boolean nameCheck = true;
  private boolean surnameCheck = true;
  private boolean passwordCheck = true;

  public void setMaster(MasterController master) {
    this.master = master;
    this.userMethods = master.getUserReference();
  }

  /**
   * Updates profile tab.
   * Updates the profile tab when we need to.
   */

  @FXML
  private Label lblYourEmail;
  @FXML
  private Label lblYourProgram;
  @FXML
  private Label lblYourExamYear;
  @FXML
  private Label lblYourUserClass;

  @FXML
  void onButtonPressPromoteUserToAdmin() {
    master.toggleAdminStatus(tfSearchUser.getText());
    toggleSearchButtons();
  }

  @FXML
  void onButtonPressBanUser() {
    User userProfile = userMethods.getUserByEmail(tfSearchUser.getText());
    boolean setBan = !(userProfile != null && userProfile.getAccountStatus());
    if (setBan) {
      master.banUser(tfSearchUser.getText());
    } else {
      master.unbanUser(tfSearchUser.getText());
    }
    toggleSearchButtons();
  }

  @FXML
  void onDeleteUser() {
    if (oldUser == null) {
      oldUser = userMethods.getUserByEmail(tfSearchUser.getText());
      master.removeUser(tfSearchUser.getText());
      load();
    } else {
      master.addUser(oldUser);
      tfSearchUser.setText(oldUser.getEmail());
      displayUser(oldUser);
      oldUser = null;
    }
    toggleSearchButtons();
    tfSearchUser.setStyle(Style.TEXTFIELD_GREEN);
  }

  @FXML
  private ComboBox<String> cbPrograms;

  @FXML
  private ComboBox<Integer> cbGraduationYear;

  @FXML
  private Label lblUsername;

  @FXML
  private VBox vbProfileDetails;

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
  private Label lblWarningName;

  @FXML
  private Label lblWarningSurname;

  @FXML
  private Label lblWarningPassword;

  @FXML
  private Label lblIncorrectPassword;

  @FXML
  private PasswordField pfCurrentPassword;

  @FXML
  private TextField tfSearchUser;

  @FXML
  private Button btnToggleBan;

  @FXML
  private Button btnDeleteAccount;

  @FXML
  private Button btnPromoteAccount;

  @FXML
  private Pane adminSettings;

  @FXML
  private AnchorPane apAccountSettings;

  @FXML
  private VBox vbName;

  @FXML
  private VBox vbSurname;

  @FXML
  private VBox vbPassword;

  @FXML
  private VBox vbStudies;

  @FXML
  void onTextfieldType(KeyEvent event) {
    if (event.getSource() instanceof PasswordField) {
      passwordFieldValidation();
    } else if (event.getSource() instanceof TextField typedField) {
      textFieldValidation(typedField);
    }
    checkSavePossible();
  }

  private void passwordFieldValidation() {
    String pass = pfPassword.getText();
    String passConfirm = pfPasswordConfirm.getText();
    int passSize = pass.length();
    int passConfirmSize = passConfirm.length();

    boolean checkOne = (master.checkPassword(pass));
    boolean checkTwo = (master.checkPassword(passConfirm));
    boolean checkThree = pass.equals(passConfirm);
    boolean checkFour = abs(passSize - passConfirmSize) == passSize;

    // if password is valid or length is zero == password confirm is valid or length is zero
    // If both have 0 length or both have correct pass
    lblWarningPassword.setText(
        (checkOne || checkTwo) && (checkThree || checkFour) ? "" : checkOne || checkTwo
            ? "Passwords don't match."
            : "Password requires minimum 11 characters, 1 uppercase, 1 lowercase and 1 number."
    );

    pfPassword.setStyle(checkOne ? Style.TEXTFIELD_GREEN : passSize == 0
        ? Style.TEXTFIELD_NORMAL : Style.TEXTFIELD_RED);
    pfPasswordConfirm.setStyle(checkTwo ? Style.TEXTFIELD_GREEN
        : passConfirmSize == 0 ? Style.TEXTFIELD_NORMAL : Style.TEXTFIELD_RED);
    passwordCheck = (checkOne && checkTwo && checkThree) || passSize + passConfirmSize == 0;
  }

  private void textFieldValidation(TextField typedField) {
    String textInput = typedField.getText();
    int textLength = textInput.length();
    if ((typedField == tfName || typedField == tfNameConfirm) && (tfName.getText().equals(tfNameConfirm.getText()) || (tfName.getText().length() + tfNameConfirm.getText().length() == 0))) {
      nameCheck = true;
      lblWarningName.setText("");
      tfName.setStyle((tfName.getText().length() + tfNameConfirm.getText().length() == 0 ? Style.TEXTFIELD_NORMAL : Style.TEXTFIELD_GREEN));
      tfNameConfirm.setStyle((tfName.getText().length() + tfNameConfirm.getText().length() == 0 ? Style.TEXTFIELD_NORMAL : Style.TEXTFIELD_GREEN));
    } else if ((typedField == tfName || typedField == tfNameConfirm) && !tfNameConfirm.getText().equals(tfName.getText())) {
      nameCheck = false;
      lblWarningName.setText(tfNameConfirm.getText().length() > 0 && tfName.getText().length() > 0 ? "Names don't match." : "");
      tfNameConfirm.setStyle(tfNameConfirm.getText().length() > 0 && tfName.getText().length() > 0 ? Style.TEXTFIELD_RED : tfNameConfirm.getText().length() > 0 ? Style.TEXTFIELD_GREEN : Style.TEXTFIELD_NORMAL);
      tfName.setStyle(tfName.getText().length() > 0 ? Style.TEXTFIELD_GREEN : tfName.getText().length() > 0 ? Style.TEXTFIELD_GREEN : Style.TEXTFIELD_NORMAL);
    } else if ((typedField == tfSurname || typedField == tfSurnameConfirm) && (tfSurname.getText().equals(tfSurnameConfirm.getText()) || (tfSurname.getText().length() + tfSurnameConfirm.getText().length() == 0))) {
      surnameCheck = true;
      lblWarningSurname.setText("");
      tfSurname.setStyle((tfSurname.getText().length() + tfSurnameConfirm.getText().length() == 0 ? Style.TEXTFIELD_NORMAL : Style.TEXTFIELD_GREEN));
      tfSurnameConfirm.setStyle((tfSurname.getText().length() + tfSurnameConfirm.getText().length() == 0 ? Style.TEXTFIELD_NORMAL : Style.TEXTFIELD_GREEN));
    } else if ((typedField == tfSurname || typedField == tfSurnameConfirm) && !tfSurnameConfirm.getText().equals(tfSurname.getText())) {
      surnameCheck = false;
      lblWarningSurname.setText(tfSurnameConfirm.getText().length() > 0 && tfSurname.getText().length() > 0 ? "Surnames don't match." : "");
      tfSurname.setStyle(tfSurname.getText().length() > 0 ? Style.TEXTFIELD_GREEN : tfSurname.getText().length() > 0 ? Style.TEXTFIELD_GREEN : Style.TEXTFIELD_NORMAL);
      tfSurnameConfirm.setStyle(tfSurnameConfirm.getText().length() > 0 && tfSurname.getText().length() > 0 ? Style.TEXTFIELD_RED : tfSurnameConfirm.getText().length() > 0 ? Style.TEXTFIELD_GREEN : Style.TEXTFIELD_NORMAL);
    }
  }

  @FXML
  void onComboBoxSelect() {
    if (cbPrograms.getValue() != null && cbGraduationYear.getValue() != null) {
      checkSavePossible();
    }
  }

  @FXML
  void closeSettings() {
    master.showHomepage();
  }


  private void checkSavePossible() {

    boolean noChanges = (tfName.getText().length() + tfNameConfirm.getText().length()
            + tfSurname.getText().length() + tfSurnameConfirm.getText().length()
            + pfPassword.getText().length() + pfPasswordConfirm.getText().length()) == 0
            && (currentUser.getType() == 3
            || (cbPrograms.getValue().equals(((Student) currentUser).getProgram())
            && cbGraduationYear.getValue() == ((Student) currentUser).getGraduationYear()));

    if (noChanges) {
      btnSaveSettings.setDisable(true);
      btnCloseSettings.setStyle(miscellaneous.Style.BUTTON_NORMAL);
      btnCloseSettings.setText(Info.CLOSE);
    } else if (nameCheck && surnameCheck && passwordCheck) {
      btnSaveSettings.setDisable(false);
      btnCloseSettings.setText(Info.CANCEL);
      btnCloseSettings.setStyle(miscellaneous.Style.BUTTON_RED);
    } else {
      btnSaveSettings.setDisable(true);
      btnCloseSettings.setText(Info.CANCEL);
      btnCloseSettings.setStyle(miscellaneous.Style.BUTTON_RED);
    }
  }

  private void toggleClose() {
    btnCloseSettings.setText(Info.CLOSE);
    btnCloseSettings.setStyle(Style.BUTTON_NORMAL);
  }

  @FXML
  void onPasswordEntered() {
    lblIncorrectPassword.setVisible(false);
  }

  @FXML
  void onSearchUser() {
    String email = tfSearchUser.getText();
    User userProfile = userMethods.getUserByEmail(email);
    if (email.length() > 0 && userProfile == null) {
      load();
      tfSearchUser.setStyle(Style.TEXTFIELD_NORMAL);
      btnToggleBan.setDisable(true);
      btnPromoteAccount.setDisable(true);
      btnDeleteAccount.setDisable(true);
    } else if (userProfile == null) {
      load();
      tfSearchUser.setStyle(Style.TEXTFIELD_RED);
      btnToggleBan.setDisable(true);
      btnPromoteAccount.setDisable(true);
      btnDeleteAccount.setDisable(true);
    } else {
      tfSearchUser.setStyle(Style.TEXTFIELD_GREEN);
      // get logged in user's name:
      displayUser(userProfile);
      toggleSearchButtons();
    }
  }

  private void displayUser(User user) {
    int type = user.getType();
    String name = user.getFirstName() + " " + user.getLastName();
    String email = user.getEmail();
    String role;
    if (type < Info.TYPE_STAFF) {
      User student = userMethods.getUserByEmail(email);
      // Get student's program:
      String program = ((Student) student).getProgram();
      // Get student's graduation year:
      String graduation = String.valueOf(((Student) student).getGraduationYear());
      // Set role to student / admin depending on "type".
      role = type == 1 ? "Student" : "Administrator";
      // Set info on profile dashboard
      lblYourProgram.setText(program);
      lblYourExamYear.setText(graduation);
    } else { // If staff:
      role = "Staff";
      // Removes labels from profile dashboard:
      vbProfileDetails.getChildren().remove(lblYourProgram);
      vbProfileDetails.getChildren().remove(lblYourExamYear);
    }
    // Set universal info on profile dashboard
    lblUsername.setText(name);
    lblYourEmail.setText(email);
    lblYourUserClass.setText(role);
    btnToggleBan.setDisable(false);
    btnPromoteAccount.setDisable(false);
    btnDeleteAccount.setDisable(false);
  }

  @FXML
  void saveSettings() {
    boolean correctPassword = currentUser.validatePassword(pfCurrentPassword.getText());
    if (correctPassword) {
      String name = (tfName.getText().length() > 0 ? tfName.getText() : currentUser.getFirstName());
      String surname = (tfSurname.getText().length() > 0
          ? tfSurname.getText() : currentUser.getLastName());
      String password = (pfPassword.getText().length() > 0
          ? pfPassword.getText() : currentUser.getPassword());
      String program = cbPrograms.getValue();
      int graduationYear = cbGraduationYear.getValue();

      int index = master.getAllUsers().indexOf(currentUser);
      master.setName(index, name);
      master.setSurname(index, surname);
      master.setPassword(index, password);
      if (currentUser.getType() < 3) {
        master.setProgram(index, program);
        master.setGraduation(index, graduationYear);
      }
      master.saveUsers();
      load();
      lblIncorrectPassword.setText(Info.SAVE_SUCCESS);
      lblIncorrectPassword.setStyle(Style.LABEL_GREEN);
      lblIncorrectPassword.setVisible(true);
      toggleClose();
    } else {
      lblIncorrectPassword.setText(pfCurrentPassword.getText().length() > 0
          ? Info.INCORRECT_PASSWORD : Info.ENTER_PASSWORD);
      lblIncorrectPassword.setStyle(Style.LABEL_RED);
      lblIncorrectPassword.setVisible(true);
    }
  }

  @FXML
  void onButtonEntered(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(0.8);
  }

  @FXML
  void onButtonExited(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(1);
  }

  /**
   * This method is called when showing hte profile settings window.
   * It does the initial loading of
   */
  public void load() {
    currentUser = master.getCurrentUser();
    displayUser(currentUser);
    if (currentUser.getType() < Info.TYPE_STAFF) {
      ObservableList<String> programs = FXCollections.observableArrayList(
          Info.DVET, Info.SVET, Info.KOG, Info.SEM);
      cbPrograms.setItems(programs);
      cbPrograms.setValue(((Student) currentUser).getProgram());
      int year = Year.now().getValue();
      ObservableList<Integer> graduationYears = FXCollections.observableArrayList(
          year, (year + 1), (year + 2), (year + 3));
      cbGraduationYear.setItems(graduationYears);
      cbGraduationYear.setValue(((Student) currentUser).getGraduationYear());
    } else {
      apAccountSettings.getChildren().remove(vbStudies);
    }
    if (currentUser.getType() != Info.TYPE_ADMIN) {
      apAccountSettings.getChildren().remove(adminSettings);
      VBox aesthetics = new VBox();
      aesthetics.setPrefWidth(547);
      aesthetics.setLayoutX(67);
      aesthetics.setSpacing((currentUser.getType()) == Info.TYPE_STAFF ? 45.5 : 27);
      aesthetics.getChildren().add(vbName);
      aesthetics.getChildren().add(vbSurname);
      aesthetics.getChildren().add(vbPassword);
      vbStudies.getChildren().remove(cbPrograms);
      vbStudies.getChildren().remove(cbGraduationYear);
      if (currentUser.getType() != Info.TYPE_STAFF) {
        HBox flatStudies = new HBox();
        flatStudies.getChildren().add(cbPrograms);
        flatStudies.getChildren().add(cbGraduationYear);
        flatStudies.setSpacing(5);
        vbStudies.getChildren().add(1, flatStudies);
        aesthetics.getChildren().add(vbStudies);
      }
      apAccountSettings.getChildren().setAll(aesthetics);
    }
  }

  private void toggleSearchButtons() {
    String email = tfSearchUser.getText();
    boolean accountExists = oldUser == null;
    if (accountExists) {
      int role = userMethods.getUserByEmail(tfSearchUser.getText()).getType();
      if (userMethods.getUserByEmail(email).getAccountStatus()) {
        btnToggleBan.setText("Unban");
        btnToggleBan.setStyle(miscellaneous.Style.BUTTON_GREEN);
      } else {
        btnToggleBan.setText("Ban");
        btnToggleBan.setStyle(miscellaneous.Style.BUTTON_RED);
      }
      if (role == 3) {
        btnPromoteAccount.setDisable(true);
      } else {
        btnPromoteAccount.setText(role == 2 ? "Demote" : "Promote");
        btnPromoteAccount.setStyle(role == 2 ? miscellaneous.Style.BUTTON_RED
            : miscellaneous.Style.BUTTON_GREEN);
      }
    } else {
      btnPromoteAccount.setDisable(true);
      btnToggleBan.setDisable(true);
    }
    btnDeleteAccount.setText(oldUser == null ? "Delete" : "Undo");
  }
}