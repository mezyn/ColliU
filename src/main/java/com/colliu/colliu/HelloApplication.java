package com.colliu.colliu;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import miscellaneous.UserInput;
import user.UserMethods;


public class HelloApplication extends Application {
  private UserMethods user;
  Stage stage;
  @Override
  public void start(Stage stage) throws IOException {
    this.stage = stage;
    showWindow("homepage");
  }

  public static void main(String[] args) {
    launch();
    //new HelloApplication();
  }

  public HelloApplication() {
    user = new UserMethods();
    String email;
    String password;
    String firstName;
    String lastName;
    int graduationYear;
    ArrayList<String> courses = new ArrayList<>();
    ArrayList<String> interests = new ArrayList<>();
    String program;

    email = "helloA@dam.gu.se";
    password ="PassWordMyAss45!";
    firstName = "adam";
    lastName = "Bengtsson";
    graduationYear = 2024;
    courses.add("DIT043");
    interests.add("Hockey");
    //program = UserInput.readLine("Enter program: ");
    program = "SEM";
    try {
      System.out.println(user.createStudent(email, password, firstName, lastName, graduationYear, program));
    } catch (Exception exception) {
      System.out.println(exception);
    }
  }

  private void showWindow(String fxmlFile) throws IOException {
    fxmlFile = (fxmlFile.endsWith(".fxml") ? fxmlFile : fxmlFile + ".fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Hello!");
    stage.setScene(scene);
    stage.show();
  }
}