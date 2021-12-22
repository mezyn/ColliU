package com.colliu.colliu;

import java.io.IOException;
import java.util.ArrayList;

import com.colliu.colliu.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import user.Administrator;
import user.Staff;
import user.Student;
import user.User;


public class Master extends Application {
  Stage stage;

  @Override
  public void start(Stage stage) throws Exception {
    this.stage = stage;
    //showWindow("homepage.fxml");
    showWindow("LoginPage.fxml");
  }

  public static void main(String[] args) {
    launch();
  }

  private void showWindow(String fileName) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(Master.class.getResource("fxml/" + (fileName.endsWith(".fxml") ? fileName : fileName + ".fxml")));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Hello!");
    stage.setScene(scene);
    stage.initStyle(StageStyle.UNDECORATED);
    stage.show();
    LoginController temp = fxmlLoader.getController();
    MasterController masterInitiate = new MasterController();
    temp.setMaster(masterInitiate);
    masterInitiate.setStage(stage);
    ArrayList<User> user = new ArrayList<>();
    user.add(new Student("erik@student.gu.se", "Hej123123!!", "Erik", "Harring", 2024, "SEM"));
    user.add(new Staff("william@student.gu.se", "Hej123123!!", "William", "Hilmersson", "IT", "Prof."));
    user.add(new Administrator("kris@student.gu.se", "Hej123123!!", "Kristofer", "Koskunen", 2024, "SEM"));
    masterInitiate.user = user.get(1);
    masterInitiate.json.saveUsers(user);
  }
}
