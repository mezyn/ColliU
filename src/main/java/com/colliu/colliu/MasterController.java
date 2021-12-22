package com.colliu.colliu;

import com.colliu.colliu.controllers.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import miscellaneous.Data;
import user.User;
import user.UserMethods;

/**
 * * * * * * * * * * * *
 * DOCUMENTATION HERE. *
 * * * * * * * * * * * *
 **/

public class MasterController {

  private UserMethods userMethods;
  private Data json;
  private Stage latestStage;
  private Stage previousStage;

  public MasterController() throws FileNotFoundException, UnsupportedEncodingException {
    json = new Data();
    userMethods = new UserMethods(this);
  }

  private FXMLLoader showWindow(String fileName) throws IOException {
    fileName = "fxml/" + (fileName.endsWith(".fxml") ? fileName : fileName + ".fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
    FXMLLoader titleBarFXML = new FXMLLoader(Master.class.getResource("fxml/TitleBar.fxml"));
    Pane titleBar = titleBarFXML.load();
    Scene scene = new Scene(fxmlLoader.load());
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle(fileName.substring(0, fileName.length()-4));
    stage.initStyle(StageStyle.UNDECORATED);
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

  public void showProfileSettingsPage() throws Exception {
    String profileSettingsPage = "ProfileSettingsPage.fxml";
    closeWindow();
    FXMLLoader profileLoader = showWindow(profileSettingsPage);
    ProfileController profileController = profileLoader.getController();
    profileController.setMaster(this);
    profileController.updateProfileTab();
  }

  private void closeWindow() {
    if (latestStage != null) {
        latestStage.close();
    }
  }

  public void showLastWindow () {
    Stage temp = latestStage;
    latestStage = previousStage;
    previousStage = temp;
    previousStage.close();
    latestStage.show();
  }

  public void setStage (Stage stage) {
    this.latestStage = stage;
  }

}