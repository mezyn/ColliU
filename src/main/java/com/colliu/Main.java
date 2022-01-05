package com.colliu;

import com.colliu.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.colliu.miscellaneous.Info;


public class Main extends Application {
  private Stage stage;

  @Override
  public void start(Stage stage) throws Exception {
    this.stage = stage;
    showWindow();
  }

  public static void main(String[] args) {
    launch();
  }

  private void showWindow() throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/" + (Info.RESOURCE_LOGIN.endsWith(".fxml") ? Info.RESOURCE_LOGIN : Info.RESOURCE_LOGIN + ".fxml")));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setScene(scene);
    stage.initStyle(StageStyle.UNDECORATED);
    stage.show();
    LoginController loginController = fxmlLoader.getController();
    PageController masterInitiate = new PageController();
    loginController.setMaster(masterInitiate);
    masterInitiate.setStage(stage);
  }
}
