package com.colliu.colliu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import course.CourseController;
import event.EventController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user.UserMethods;


public class Master extends Application {
  protected UserMethods user;
  public EventController event;
  public CourseController course;
  Stage stage;
  @Override
  public void start(Stage stage) throws IOException {
    this.stage = stage;
    showWindow("homepage.fxml");
  }

  public static void main(String[] args) {
    launch();
    new Master();
  }

  public Master() { // HAVE TO BE PUBLIC!
    user = new UserMethods(this);
    event = new EventController(this);
    course = new CourseController(this);

    String email;
    String password;
    String firstName;
    String lastName;
    int graduationYear;
    ArrayList<String> interests = new ArrayList<>();
    String program;

    email = "helloA@dam.gu.se";
    password ="PassWordMyAss45!";
    firstName = "adam";
    lastName = "Bengtsson";
    graduationYear = 2024;
    course.addCourse("DIT043");
    interests.add("Hockey");
    program = "SEM";

    try {
      System.out.println(user.createStudent(email, password, firstName, lastName, graduationYear, program));
      event.addEvent("Test Event", new Date(), "School", 0);
      user.getUserByEmail("helloA@dam.gu.se").login();
      Integer[] test = event.getNotifications(user.getUserByEmail("helloA@dam.gu.se").getCourses(), user.getUserByEmail("helloA@dam.gu.se").getLogins()[user.getUserByEmail("helloA@dam.gu.se").getLogins().length]);
      for(int i : test) {
        System.out.println("Testing notification function, course ID: " + i);
      }
    } catch (Exception exception) {
      System.out.println(exception);
    }
  }

  private void showWindow(String fileName) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Master.class.getResource((fileName.endsWith(".fxml") ? fileName : fileName + ".fxml")));
    Scene scene = new Scene(fxmlLoader.load(), 320, 240);
    stage.setTitle("Hello!");
    stage.setScene(scene);
    stage.show();
  }
}