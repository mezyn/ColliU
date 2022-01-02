package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import java.io.IOException;
import java.time.Year;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import org.w3c.dom.Text;
import user.Administrator;
import user.Staff;
import user.Student;
import user.User;

import javax.security.auth.callback.PasswordCallback;

import static java.lang.Math.abs;

public class ProfileController {
  private MasterController master;
  private User currentUser;
  private User oldUser;
  private boolean nameCheck = true;
  private boolean surnameCheck = true;
  private boolean passwordCheck = true;

  public void setMaster(MasterController master) {
    this.master = master;
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
  void onButtonPressPromoteUserToAdmin() { // Method for changing the boolean value in the student/admin class to make them admin/student
    master.toggleAdminStatus(tfSearchUser.getText());
    toggleSearchButtons();
  }

  @FXML
  void onButtonPressBanUser() { // Method for toggling the boolean value inside the classes to ban them
    boolean setBan = !(master.findUser(tfSearchUser.getText()) != null && master.findUser(tfSearchUser.getText()).getAccountStatus());
    if (setBan) {
      master.banUser(tfSearchUser.getText());
    } else {
      master.unbanUser(tfSearchUser.getText());
    }
    toggleSearchButtons();
  }

  @FXML
  void onDeleteUser() { // Method for deleting an existing user from the system, while assigning it to the oldUser to make it available to restore if it has not been saved yet.
    if (oldUser == null) {
      oldUser = master.findUser(tfSearchUser.getText());
      master.removeUser(tfSearchUser.getText());
      load();
    } else { // If the oldUser isn't null it displays the current oldUser on the account settings page and allows the admin to unban/promote/un-delete their account
      master.addUser(oldUser);
      tfSearchUser.setText(oldUser.getEmail());
      displayUser(oldUser);
      oldUser = null;
    }
    toggleSearchButtons();
    tfSearchUser.setStyle(Style.TEXTFIELD_GREEN);
  }



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

    pfPassword.setStyle(checkOne ? Style.TEXTFIELD_GREEN : passSize == 0 ? Style.TEXTFIELD_NORMAL : Style.TEXTFIELD_RED);
    pfPasswordConfirm.setStyle(checkTwo ? Style.TEXTFIELD_GREEN : passConfirmSize == 0 ? Style.TEXTFIELD_NORMAL : Style.TEXTFIELD_RED);

/*
    if (passSize + passConfirmSize == 0) {
      lblWarningPassword.setText("");
      pfPassword.setStyle(Style.TEXTFIELD_NORMAL);
      pfPasswordConfirm.setStyle(Style.TEXTFIELD_NORMAL);
      passwordCheck = true;
    } else if (!master.checkPassword(pfPassword.getText()) && !master.checkPassword(pfPasswordConfirm.getText())) {
      lblWarningPassword.setWrapText(true);
      lblWarningPassword.setText((pfPassword.getText().length() > 0 && !master.checkPassword(pfPassword.getText())) || (pfPasswordConfirm.getText().length() > 0 && !master.checkPassword(pfPasswordConfirm.getText())) ? "Password requires minimum 11 characters, 1 uppercase, 1 lowercase, 1 number and 1 symbol." : "");
      passwordCheck = false;
      pfPasswordConfirm.setStyle(pfPasswordConfirm.getText().length() > 0 ? (master.checkPassword(pfPasswordConfirm.getText()) ? Style.TEXTFIELD_GREEN : Style.TEXTFIELD_RED) : Style.TEXTFIELD_NORMAL);
      pfPassword.setStyle(pfPassword.getText().length() > 0 ? (master.checkPassword(pfPassword.getText()) ? Style.TEXTFIELD_GREEN : Style.TEXTFIELD_RED) : Style.TEXTFIELD_NORMAL);
    } else if (!pfPassword.getText().equals(pfPasswordConfirm.getText())) {
      lblWarningPassword.setText(pfPassword.getText().length() > 0 && pfPasswordConfirm.getText().length() > 0 ? "Passwords don't match." : "");
      passwordCheck = false;
      pfPasswordConfirm.setStyle(pfPassword.getText().length() > 0 && pfPasswordConfirm.getText().length() > 0 ? Style.TEXTFIELD_RED : (master.checkPassword(pfPasswordConfirm.getText()) ? Style.TEXTFIELD_GREEN : Style.TEXTFIELD_NORMAL));
      pfPassword.setStyle(pfPassword.getText().length() > 0 && !master.checkPassword(pfPassword.getText()) ? Style.TEXTFIELD_RED : (master.checkPassword(pfPassword.getText()) ? Style.TEXTFIELD_GREEN : Style.TEXTFIELD_NORMAL));
    } else {
      lblWarningPassword.setText("");
      passwordCheck = true;
      pfPasswordConfirm.setStyle(Style.TEXTFIELD_GREEN);
      pfPassword.setStyle(Style.TEXTFIELD_GREEN);
    }*/
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
  void onComboBoxSelect(ActionEvent event) {
    if (cbPrograms.getValue() != null && cbGraduationYear.getValue() != null)
      checkSavePossible();
  }

  @FXML
  void closeSettings(ActionEvent event) { // Exits the account settings page and opens the event page
    master.showEventPage();
  }


  private void checkSavePossible() { //Checks if there have been any changes in the account settings page
    boolean noChanges = (tfName.getText().length() + tfNameConfirm.getText().length()
            + tfSurname.getText().length() + tfSurnameConfirm.getText().length()
            + pfPassword.getText().length() + pfPasswordConfirm.getText().length()) == 0
            && (currentUser.getType() == 3
            || (cbPrograms.getValue().equals(((Student) currentUser).getProgram())
            && cbGraduationYear.getValue() == ((Student) currentUser).getGraduationYear()));

    if (noChanges) { // If there haven't been any changes it allows the user to save their changes
      btnSaveSettings.setDisable(true);
      btnCloseSettings.setStyle(miscellaneous.Style.BUTTON_NORMAL);
      btnCloseSettings.setText(Info.CLOSE);
    } else if (nameCheck && surnameCheck && passwordCheck) { // Makes it so that the user cannot save their changes before noChanges == true
      btnSaveSettings.setDisable(false);
      btnCloseSettings.setText(Info.CANCEL);
      btnCloseSettings.setStyle(miscellaneous.Style.BUTTON_RED);
    } else { // Makes it so that the user cannot save their changes before noChanges == true
      btnSaveSettings.setDisable(true);
      btnCloseSettings.setText(Info.CANCEL);
      btnCloseSettings.setStyle(miscellaneous.Style.BUTTON_RED);
    }
  }

  //private void toggleClose() {
// Think this method isn't needed?
  //}

  @FXML
  void onPasswordEntered(KeyEvent event) { // Checks if the user has entered their password before saving the changes, if they have not done it will show a label to inform the user.
    lblIncorrectPassword.setVisible(false);
  }

  @FXML
  void onSearchUser(KeyEvent event) { // Method for looking up users and loading their information on the account settings page
    String email = tfSearchUser.getText();
    if (email.length() > 0 && master.findUser(email) == null) {
      load();
      tfSearchUser.setStyle(Style.TEXTFIELD_NORMAL);
      btnToggleBan.setDisable(true);
      btnPromoteAccount.setDisable(true);
      btnDeleteAccount.setDisable(true);
    } else if (master.findUser(email) == null) {
      load();
      tfSearchUser.setStyle(Style.TEXTFIELD_RED);
      btnToggleBan.setDisable(true);
      btnPromoteAccount.setDisable(true);
      btnDeleteAccount.setDisable(true);
    } else {
      tfSearchUser.setStyle(Style.TEXTFIELD_GREEN);
      // get logged in user's name:
      User user = master.findUser(email);
      displayUser(user);
      toggleSearchButtons();
    }
  }

  private void displayUser(User user) {
    int type = user.getType();
    String name = user.getFirstName() + " " + user.getLastName();
    String email = user.getEmail();
    String role;
    if (type < Info.TYPE_STAFF) {
      // Get student's program:
      String program = ((Student) master.findUser(email)).getProgram();
      // Get student's graduation year:
      String graduation = String.valueOf(((Student) master.findUser(email)).getGraduationYear());
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
  void saveSettings(ActionEvent event) { // Method for saving all the changes that has been made
    boolean correctPassword = currentUser.validatePassword(pfCurrentPassword.getText());
    if (correctPassword) { // If the password that has been entered is correct it saves the new settings into the current users account.
      String name = (tfName.getText().length() > 0 ? tfName.getText() : currentUser.getFirstName());
      String surname = (tfSurname.getText().length() > 0 ? tfSurname.getText() : currentUser.getLastName());
      String password = (pfPassword.getText().length() > 0 ? pfPassword.getText() : currentUser.getPassword());
      String program = cbPrograms.getValue();
      int graduationYear = cbGraduationYear.getValue();

      int index = master.getAllUsers().indexOf(currentUser);
      master.setName(index, name);
      master.setSurname(index, surname);
      master.setPassword(index, password);
      if (currentUser.getType() < 3) { // If the user isn't staff it also saves the program and graduation year.
        master.setProgram(index, program);
        master.setGraduation(index, graduationYear);
      }
      master.saveUsers();
      load();
      lblIncorrectPassword.setText(Info.SAVE_SUCCESS);
      lblIncorrectPassword.setStyle(Style.LABEL_GREEN);
      lblIncorrectPassword.setVisible(true);
    } else {
      lblIncorrectPassword.setText(pfCurrentPassword.getText().length() > 0 ? Info.INCORRECT_PASSWORD : Info.ENTER_PASSWORD);
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

  public void load() { // Method for loading the current users information
    currentUser = master.getCurrentUser();
    displayUser(currentUser); // Calls on the displayUser method to show the users information
    if (currentUser.getType() < 3) { // If the user isn't a staff it shows the program and graduation year as well.
      ObservableList<String> programs = FXCollections.observableArrayList(Info.DVET, Info.SVET, Info.KOG, Info.SEM);
      cbPrograms.setItems(programs);
      cbPrograms.setValue(((Student) currentUser).getProgram());
      int year = Year.now().getValue();
      ObservableList<Integer> graduationYears = FXCollections.observableArrayList(year, (year + 1), (year + 2), (year + 3));
      cbGraduationYear.setItems(graduationYears);
      cbGraduationYear.setValue(((Student) currentUser).getGraduationYear());
    } else {
      apAccountSettings.getChildren().remove(vbStudies);
    }
    if (currentUser.getType() != 2) { // If the account isn't an administrator it removes the administrator panel along with changing the appearance
      apAccountSettings.getChildren().remove(adminSettings);
      VBox asthetics = new VBox();
      asthetics.setPrefWidth(547);
      asthetics.setLayoutX(67);
      asthetics.setSpacing(45.5);
      asthetics.getChildren().add(vbName);
      asthetics.getChildren().add(vbSurname);
      asthetics.getChildren().add(vbPassword);
      vbStudies.getChildren().remove(cbPrograms);
      vbStudies.getChildren().remove(cbGraduationYear);
      if (currentUser.getType() != 3) {
        HBox flatStudies = new HBox();
        flatStudies.getChildren().add(cbPrograms);
        flatStudies.getChildren().add(cbGraduationYear);
        flatStudies.setSpacing(5);
        vbStudies.getChildren().add(1, flatStudies);
        asthetics.getChildren().add(vbStudies);
      }
      asthetics.setLayoutY((599 - 504) / 2);
      apAccountSettings.getChildren().setAll(asthetics);
    }
  }

  private void toggleSearchButtons() { // Method for changing the click-ability and color of the buttons
    String email = tfSearchUser.getText();
    boolean accountExists = oldUser == null;
    if (accountExists) {
      int role = master.findUser(tfSearchUser.getText()).getType();
      if (master.findUser(email).getAccountStatus()) {
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
        btnPromoteAccount.setStyle(role == 2 ? miscellaneous.Style.BUTTON_RED : miscellaneous.Style.BUTTON_GREEN);
      }
    } else {
      btnPromoteAccount.setDisable(true);
      btnToggleBan.setDisable(true);
    }
    btnDeleteAccount.setText(oldUser == null ? "Delete" : "Undo");
  }
}