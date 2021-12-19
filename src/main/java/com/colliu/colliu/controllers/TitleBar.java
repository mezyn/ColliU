package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.awt.*;

public class TitleBar {

  private double xOffset = 0.0;
  private double yOffset = 0.0;

  private MasterController master;

  @FXML
  private Button closeWindow;

  @FXML
  private HBox titleBar;

  @FXML
  void closeProgram(ActionEvent event) {
    ((Node) (event.getSource())).getScene().getWindow().hide();
  }

  @FXML
  void mousClicked(MouseEvent event) {

  }

  @FXML
  void mouseEntered(MouseEvent event) {

  }

  @FXML
  void mouseExited(MouseEvent event) {

  }

  @FXML
  void mouseDragged(MouseEvent event) {
    titleBar.getScene().getWindow().setY(event.getScreenY() - yOffset);
    titleBar.getScene().getWindow().setX(event.getScreenX() - xOffset);
  }

  @FXML
  void mousePressed(MouseEvent event) {
    xOffset = event.getSceneX();
    yOffset = event.getSceneY();
  }

  @FXML
  void mouseReleased(MouseEvent event) {

  }
}
