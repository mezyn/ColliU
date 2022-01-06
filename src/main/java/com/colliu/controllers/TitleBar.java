package com.colliu.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * This is the controller for TitleBar.fxml
 * This replaces the default Titlebar of the software with a styled one that fits our design.
 * Main purpose is to allow moving of the window and closing the window.
 */

public class TitleBar {

  private double offsetY = 0.0;
  private double offsetX = 0.0;

  @FXML
  private HBox titleBar;

  @FXML
  private void closeProgram(ActionEvent event) {
    ((Node) (event.getSource())).getScene().getWindow().hide();
  }

  @FXML
  private void mouseDragged(MouseEvent event) {
    // When dragging the stage update it's X and Y position using mouse's current location.
    titleBar.getScene().getWindow().setY(event.getScreenY() - offsetY);
    titleBar.getScene().getWindow().setX(event.getScreenX() - offsetX);
  }

  @FXML
  private void mousePressed(MouseEvent event) {
    // When pressing down mouse button on the stage get current coordinates of mouse.
    offsetX = event.getSceneX();
    offsetY = event.getSceneY();
  }
}
