package com.colliu.controllers;

import com.colliu.PageController;

import java.time.Year;

import com.colliu.miscellaneous.Info;
import com.colliu.miscellaneous.Style;
import com.colliu.user.*;
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

import static java.lang.Math.abs;

public class ProfileController {
  private PageController master;
  private UserMethods userMethods;
  private User currentUser;
  private User oldUser;
  private boolean nameCheck = true;
  private boolean surnameCheck = true;
  private boolean passwordCheck = true;

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

  /**
   - @param email The email of what com.colliu.colliu.user to Toggle admin status.
   */

  @FXML
  private void onButtonPressPromoteUserToAdmin() {
    userMethods.toggleAdminStatus(tfSearchUser.getText());
    refreshAdminButtons();
  }

  @FXML
  private void onButtonPressBanUser() {
    User userProfile = userMethods.getUserByEmail(tfSearchUser.getText());
    boolean setBan = !(userProfile != null && userProfile.getAccountStatus());
    if (setBan) {
      userMethods.banUser(tfSearchUser.getText());
    } else {
      userMethods.unbanUser(tfSearchUser.getText());
    }
    refreshAdminButtons();
  }

  @FXML
  private void onDeleteUser() {
    if (oldUser == null) {
      oldUser = userMethods.getUserByEmail(tfSearchUser.getText());
      userMethods.removeUser(tfSearchUser.getText());
      load();
    } else {
      userMethods.addUser(oldUser);
      tfSearchUser.setText(oldUser.getEmail());
      displayUser(oldUser);
      oldUser = null;
    }
    refreshAdminButtons();
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
  private void onUserSettingsKeyTyped(KeyEvent event) {
    if (event.getSource() instanceof PasswordField) {
      checkPasswordChangeInput();
    } else if (event.getSource() instanceof TextField typedField) {
      checkTextfieldInput(typedField);
    }
    checkSavePossible();
  }

  @FXML
  private void onComboBoxSelect() {
    if (cbPrograms.getValue() != null && cbGraduationYear.getValue() != null) {
      checkSavePossible();
    }
  }

  @FXML
  private void closeSettings() {
    master.showHomepage();
  }

  @FXML
  private void onButtonEntered(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(0.8);
  }

  @FXML
  private void onButtonExited(MouseEvent event) {
    ((Button) event.getSource()).setOpacity(1);
  }

  @FXML
  private void onPasswordEntered() {
    lblIncorrectPassword.setVisible(false);
  }

  @FXML
  private void onSearchUserKeyTyped() {
    String email = tfSearchUser.getText();
    User userProfile = userMethods.getUserByEmail(email);
    if (email.length() == 0) {
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
    } else if (userProfile != currentUser) {
      tfSearchUser.setStyle(Style.TEXTFIELD_GREEN);
      // get logged in user's name:
      displayUser(userProfile);
      refreshAdminButtons();
    }
  }

  @FXML
  private void saveSettings() {
    boolean correctPassword = currentUser.validatePassword(pfCurrentPassword.getText());
    if (correctPassword) {
      //Get updated data, or set default if they weren't modified.
      String name = (tfName.getText().length() > 0 ? tfName.getText() : currentUser.getFirstName());
      String surname = (tfSurname.getText().length() > 0
          ? tfSurname.getText() : currentUser.getLastName());
      String password = (pfPassword.getText().length() > 0
          ? pfPassword.getText() : currentUser.getPassword());
      String program = cbPrograms.getValue();
      int graduationYear = cbGraduationYear.getValue();

      // Apply changes:
      currentUser.setFirstName(name);
      currentUser.setLastName(surname);
      currentUser.setPassword(password);

      if (!(currentUser instanceof Staff)) {
        ((Student) currentUser).setProgram(program);
        ((Student) currentUser).setGraduationYear(graduationYear);
      }
      // Save and reload ProfilePage.
      master.saveUsers();
      load();

      // Show user info was saved successfully.
      lblIncorrectPassword.setText(Info.SAVE_SUCCESS);
      lblIncorrectPassword.setStyle(Style.LABEL_GREEN);
      lblIncorrectPassword.setVisible(true);
      toggleClose();
    } else { // invalid password:
      lblIncorrectPassword.setText(pfCurrentPassword.getText().length() > 0
          ? Info.INCORRECT_PASSWORD : Info.ENTER_PASSWORD);
      lblIncorrectPassword.setStyle(Style.LABEL_RED);
      lblIncorrectPassword.setVisible(true);
    }
  }

  public void setMaster(PageController master) {
    this.master = master;
    this.userMethods = master.getUserReference();
  }

  /**
   * This method is called when showing hte profile settings window.
   * It does the initial loading of
   */

  public void load() {
    currentUser = master.getCurrentUser();
    displayUser(currentUser);
    if (!(currentUser instanceof Staff)) {
      ObservableList<String> programs = FXCollections.observableArrayList(
          Info.DVET, Info.SVET, Info.KOG, Info.SEM);
      cbPrograms.setItems(programs);
      cbPrograms.setValue(((Student) currentUser).getProgram());
      int year = Year.now().getValue();
      ObservableList<Integer> graduationYears = FXCollections.observableArrayList(
          year, (year + 1), -(year + 2), (year + 3));
      cbGraduationYear.setItems(graduationYears);
      cbGraduationYear.setValue(((Student) currentUser).getGraduationYear());
    } else {
      apAccountSettings.getChildren().remove(vbStudies);
    }
    if (!(currentUser instanceof Administrator)) {
      apAccountSettings.getChildren().remove(adminSettings);
      VBox aesthetics = new VBox();
      aesthetics.setPrefWidth(547);
      aesthetics.setLayoutX(67);
      aesthetics.setSpacing(currentUser instanceof Staff ? 45.5 : 27);
      aesthetics.getChildren().add(vbName);
      aesthetics.getChildren().add(vbSurname);
      aesthetics.getChildren().add(vbPassword);
      vbStudies.getChildren().remove(cbPrograms);
      vbStudies.getChildren().remove(cbGraduationYear);
      if (!(currentUser instanceof Staff)) {
        HBox flatStudies = new HBox();
        flatStudies.getChildren().add(cbPrograms);
        flatStudies.getChildren().add(cbGraduationYear);
        flatStudies.setSpacing(5);
        vbStudies.getChildren().add(1, flatStudies);
        aesthetics.getChildren().add(vbStudies);
      }
      apAccountSettings.getChildren().setAll(aesthetics);
    }
    refreshAdminButtons();
  }



  private void checkPasswordChangeInput() {
    String pass = pfPassword.getText();
    String passConfirm = pfPasswordConfirm.getText();
    int passSize = pass.length();
    int passConfirmSize = passConfirm.length();

    boolean checkOne = (userMethods.checkPasswordComplexity(pass));
    boolean checkTwo = (userMethods.checkPasswordComplexity(passConfirm));
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

  private void checkTextfieldInput(TextField typedField) {
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

  private void checkSavePossible() {

    boolean noChanges = (tfName.getText().length() + tfNameConfirm.getText().length()
            + tfSurname.getText().length() + tfSurnameConfirm.getText().length()
            + pfPassword.getText().length() + pfPasswordConfirm.getText().length()) == 0
            && (currentUser instanceof Staff
            || (cbPrograms.getValue().equals(((Student) currentUser).getProgram())
            && cbGraduationYear.getValue() == ((Student) currentUser).getGraduationYear()));

    if (noChanges) {
      btnSaveSettings.setDisable(true);
      btnCloseSettings.setStyle(Style.BUTTON_NORMAL);
      btnCloseSettings.setText(Info.CLOSE);
    } else if (nameCheck && surnameCheck && passwordCheck) {
      btnSaveSettings.setDisable(false);
      btnCloseSettings.setText(Info.CANCEL);
      btnCloseSettings.setStyle(Style.BUTTON_RED);
    } else {
      btnSaveSettings.setDisable(true);
      btnCloseSettings.setText(Info.CANCEL);
      btnCloseSettings.setStyle(Style.BUTTON_RED);
    }
  }

  private void toggleClose() {
    btnCloseSettings.setText(Info.CLOSE);
    btnCloseSettings.setStyle(Style.BUTTON_NORMAL);
  }

  private void displayUser(User user) {
    String name = user.getFirstName() + " " + user.getLastName();
    String email = user.getEmail();
    String role;
    if (!(user instanceof Staff)) {
      User student = userMethods.getUserByEmail(email);
      // Get student's program:
      String program = ((Student) student).getProgram();
      // Get student's graduation year:
      String graduation = String.valueOf(((Student) student).getGraduationYear());
      // Set role to student / admin depending on "type".
      role = user instanceof Administrator ? "Administrator" : "Student";
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

  /**
   * This will make sure that the admin panel always display the buttons correctly.
   * This menas that it will show demote on an admin, and promote on non admin student.
   * Same goes for ban, unban and delete, undo delete.
   */

  private void refreshAdminButtons() {
    String email = tfSearchUser.getText();
    User user = userMethods.getUserByEmail(tfSearchUser.getText());
    if (user != null && user != currentUser) {
      if (userMethods.getUserByEmail(email).getAccountStatus()) {
        btnToggleBan.setText("Unban");
        btnToggleBan.setStyle(Style.BUTTON_GREEN);
      } else {
        btnToggleBan.setText("Ban");
        btnToggleBan.setStyle(Style.BUTTON_RED);
      }
      if (user instanceof Staff) {
        btnPromoteAccount.setDisable(true);
      } else {
        btnPromoteAccount.setText(user instanceof Administrator ? "Demote" : "Promote");
        btnPromoteAccount.setStyle(user instanceof Administrator ? Style.BUTTON_RED
            : Style.BUTTON_GREEN);
      }
    } else {
      btnPromoteAccount.setDisable(true);
      btnToggleBan.setDisable(true);
      btnDeleteAccount.setDisable(true);
    }
    btnDeleteAccount.setText(oldUser == null ? "Delete" : "Undo");
  }
}