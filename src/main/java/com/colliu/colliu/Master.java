package com.colliu.colliu;

import com.colliu.colliu.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import miscellaneous.Info;


public class Master extends Application {
  private Stage stage;

  @Override
  public void start(Stage stage) throws Exception {
    this.stage = stage;
    showWindow(Info.RESOURCE_LOGIN);
  }

  public static void main(String[] args) {
    launch();
  }

  private void showWindow(String fileName) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(Master.class.getResource("fxml/" + (fileName.endsWith(".fxml") ? fileName : fileName + ".fxml")));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setScene(scene);
    stage.initStyle(StageStyle.UNDECORATED);
    stage.show();
    LoginController loginController = fxmlLoader.getController();
    MasterController masterInitiate = new MasterController();
    loginController.setMaster(masterInitiate);
    masterInitiate.setStage(stage);
  }
}
