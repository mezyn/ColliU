package com.colliu.colliu;

import course.CourseController;
import event.EventController;
import java.io.IOException;
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
    //showWindow("homepage.fxml");
    showWindow("EventPage.fxml");
  }

  public static void main(String[] args) {
    launch();
  }

// Write comment here
  private void showWindow(String fileName) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Master.class.getResource("fxml/" + (fileName.endsWith(".fxml") ? fileName : fileName + ".fxml")));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Hello!");
    stage.setScene(scene);
    stage.show();
  }
}
