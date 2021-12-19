package com.colliu.colliu;

import java.io.IOException;

import com.colliu.colliu.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Master extends Application {
  Stage stage;

  @Override
  public void start(Stage stage) throws IOException {
    this.stage = stage;
    //showWindow("homepage.fxml");
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
