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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import miscellaneous.Info;
import miscellaneous.Style;
import org.w3c.dom.Text;
import user.Administrator;
import user.Staff;
import user.Student;
import user.User;

public class ProfileController {
  MasterController master;
  User oldUser;

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
  void onButtonPressPromoteUserToAdmin() {
    master.toggleAdminStatus(tfSearchUser.getText());
    toggleSearchButtons();
  }

  @FXML
  void onButtonPressBanUser() {
    boolean setBan = !(master.findUser(tfSearchUser.getText()) != null && master.findUser(tfSearchUser.getText()).getAccountStatus());
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
      oldUser = master.findUser(tfSearchUser.getText());
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
  private Label lblWarningStudies;

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
  private VBox vbStudies;

  private boolean nameCheck = true;
  private boolean surnameCheck = true;
  private boolean passwordCheck = true;

  @FXML
  void onTextfieldType(KeyEvent event) {
    if (event.getSource() instanceof PasswordField) {
      if (pfPassword.getText().length() + pfPasswordConfirm.getText().length() == 0) {
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
      }
    } else if (event.getSource() instanceof TextField typedField) {
      if ((typedField == tfName || typedField == tfNameConfirm) && (tfName.getText().equals(tfNameConfirm.getText()) || (tfName.getText().length() + tfNameConfirm.getText().length() == 0))) {
        nameCheck = true;
        lblWarningName.setText("");
        tfNameConfirm.setStyle((tfName.getText().length() + tfNameConfirm.getText().length() == 0 ? Style.TEXTFIELD_NORMAL : Style.TEXTFIELD_GREEN));
        tfName.setStyle((tfName.getText().length() + tfNameConfirm.getText().length() == 0 ? Style.TEXTFIELD_NORMAL : Style.TEXTFIELD_GREEN));
      } else if ((typedField == tfName || typedField == tfNameConfirm) && !tfNameConfirm.getText().equals(tfName.getText())) {
        nameCheck = false;
        lblWarningName.setText(tfNameConfirm.getText().length() > 0 && tfName.getText().length() > 0 ? "Names don't match." : "");
        tfNameConfirm.setStyle(tfNameConfirm.getText().length() > 0 && tfName.getText().length() > 0 ? Style.TEXTFIELD_RED : Style.TEXTFIELD_NORMAL);
        tfName.setStyle(tfName.getText().length() > 0 ? Style.TEXTFIELD_GREEN : Style.TEXTFIELD_NORMAL);
      } else if ((typedField == tfSurname || typedField == tfSurnameConfirm) && (tfSurname.getText().equals(tfSurnameConfirm.getText()) || (tfSurname.getText().length() + tfSurnameConfirm.getText().length() == 0))) {
        surnameCheck = true;
        lblWarningSurname.setText("");
        tfSurnameConfirm.setStyle((tfSurname.getText().length() + tfSurnameConfirm.getText().length() == 0 ? Style.TEXTFIELD_NORMAL : Style.TEXTFIELD_GREEN));
        tfSurname.setStyle((tfSurname.getText().length() + tfSurnameConfirm.getText().length() == 0 ? Style.TEXTFIELD_NORMAL : Style.TEXTFIELD_GREEN));
      } else if ((typedField == tfSurname || typedField == tfSurnameConfirm) && !tfNameConfirm.getText().equals(tfSurname.getText())) {
        surnameCheck = false;
        lblWarningSurname.setText(tfSurnameConfirm.getText().length() > 0 && tfSurname.getText().length() > 0 ? "Surnames don't match." : "");
        tfSurname.setStyle(tfSurname.getText().length() > 0 ? Style.TEXTFIELD_GREEN : Style.TEXTFIELD_NORMAL);
        tfSurnameConfirm.setStyle(tfSurnameConfirm.getText().length() > 0 && tfSurname.getText().length() > 0 ? Style.TEXTFIELD_RED : Style.TEXTFIELD_NORMAL);
      }
    }
    checkSavePossible();
  }

  @FXML
  void onComboBoxSelect(ActionEvent event) {
    if (cbPrograms.getValue() != null && cbGraduationYear.getValue() != null)
      checkSavePossible();
  }

  @FXML
  void closeSettings(ActionEvent event) {
    master.showEventPage();
  }


  private void checkSavePossible() {

    boolean noChanges = (tfName.getText().length() + tfNameConfirm.getText().length()
            + tfSurname.getText().length() + tfSurnameConfirm.getText().length()
            + pfPassword.getText().length() + pfPasswordConfirm.getText().length()) == 0
            && (master.getCurrentUser().getType() == 3
            || (cbPrograms.getValue().equals(((Student) master.getCurrentUser()).getProgram())
            && cbGraduationYear.getValue() == ((Student) master.getCurrentUser()).getGraduationYear()));

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

  }

  @FXML
  void onPasswordEntered(KeyEvent event) {
    lblIncorrectPassword.setVisible(false);
  }

  @FXML
  void onSearchUser(KeyEvent event) {
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
    if (type < 3) {
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
  void saveSettings(ActionEvent event) {
    boolean correctPassword = master.getCurrentUser().validatePassword(pfCurrentPassword.getText());
    if (correctPassword) {
      String name = (tfName.getText().length() > 0 ? tfName.getText() : master.getCurrentUser().getFirstName());
      String surname = (tfSurname.getText().length() > 0 ? tfSurname.getText() : master.getCurrentUser().getLastName());
      String password = (pfPassword.getText().length() > 0 ? pfPassword.getText() : master.getCurrentUser().getPassword());
      String program = cbPrograms.getValue();
      int graduationYear = cbGraduationYear.getValue();

      int index = master.getAllUsers().indexOf(master.getCurrentUser());
      master.setName(index, name);
      master.setSurname(index, surname);
      master.setPassword(index, password);
      if (master.getCurrentUser().getType() < 3) {
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

  public void load() {
    User currentUser = master.getCurrentUser();
    displayUser(currentUser);
    if (currentUser.getType() < 3) {
      ObservableList<String> programs = FXCollections.observableArrayList(Info.DVET, Info.SVET, Info.KOG, Info.SEM);
      cbPrograms.setItems(programs);
      cbPrograms.setValue(((Student) currentUser).getProgram());
      int year = Year.now().getValue();
      ObservableList<Integer> graduationYears = FXCollections.observableArrayList(year, (year + 1), (year + 2), (year + 3));
      cbGraduationYear.setItems(graduationYears);
      cbGraduationYear.setValue(((Student) currentUser).getGraduationYear());
    } else {
      vbStudies.setDisable(true);
    }

    adminSettings.setDisable(currentUser.getType() != 2);
  }

  private void toggleSearchButtons() {
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