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
import miscellaneous.Data;
import user.Student;
import user.User;


public class Master extends Application {
  Stage stage;

  @Override
  public void start(Stage stage) throws Exception {
    this.stage = stage;
    //showWindow("homepage.fxml");
    Data json = new Data();
    ArrayList<User> users = new ArrayList<User>();
    users.add(new Student("mijin@student.gu.se", "Hej1234567!", "Mijin", "Kim", 2024, "SEM"));
    users.add(new Student("nia@student.gu.se", "Hej1234567!", "Nia", "G-something", 2024, "SEM"));
    users.add(new Student("adam@student.gu.se", "Hej1234567!", "Adam", "Ekwall", 2024, "SEM"));
    users.add(new Student("william@student.gu.se", "Hej1234567!", "William", "Hilmersson", 2024, "SEM"));
    json.saveUsers(users);
    showWindow("LoginPage.fxml");
  }

  public static void main(String[] args) {
    launch();
  }

  private void showWindow(String fileName) throws IOException {
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
  }
}
