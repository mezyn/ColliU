package com.colliu.colliu.controllers;

import com.colliu.colliu.Master;
import com.colliu.colliu.MasterController;
import event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import miscellaneous.Data;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;

public class EventController {

  final String effectOn = "-fx-background-color: #FAFAFA;";
  final String effectOff = "-fx-background-color: none;";

  MasterController master;

  @FXML
  private Button btnAttending;

  @FXML
  private Button closWindow;

  @FXML
  private VBox eventItems;

  @FXML
  private ScrollPane eventScroll;

  @FXML
  private Label filterCategory;

  @FXML
  private ListView<?> filterList;

  @FXML
  private Pane filterPane;

  @FXML
  private ImageView imageProfile;

  @FXML
  private Pane listAnnouncements;

  @FXML
  private Pane titleBar;

  @FXML
  private TextField txtSearch;

  @FXML
  private Button btnLogout;

  @FXML
  private Button btnProfileSettings;

  @FXML
  private VBox vbNameDropDown;

  @FXML
  private Pane pnUserName;

  @FXML
  private Button btnArrowPoint;


  @FXML
  void closeProgram(ActionEvent event) {

  }

  @FXML
  void filterEvent(MouseEvent event) {

  }

  @FXML
  void logoutUser(ActionEvent event) throws IOException {
    master.showLogin();
  }

  @FXML
  void openSettings(MouseEvent event) {

  }

  @FXML
  void setOnMouseDragged(MouseEvent event) {

  }

  @FXML
  void setOnMousePressed(MouseEvent event) {

  }

  @FXML
  void showAttendingEvents(ActionEvent event) {

  }

  @FXML
  void viewAnnouncements(MouseEvent event) {

  }


  /*
    This method loads all the events;
    To make it work for tags you can add paremeters as needed:
   */

  public void loadEvents(String program, String tag) throws IOException {

    Event[] eventList = master.eventMethods.getEvents("SEM");

    Node[] adds = new StackPane[eventList.length];
    if (!tag.equals("all")) { // Does this make sense? <-- If tag is "All" show all events- Else show only events equal to "tag" parameter
      for (int i = 0; i < eventList.length; i++) {
        FXMLLoader eventLoader = new FXMLLoader(Master.class.getResource("fxml/event_design.fxml"));
        adds[i] = eventLoader.load();
        eventItems.getChildren().add(adds[i]);
        eventItems.getChildren().get(0).setLayoutX(0);
        EventItem eventController = eventLoader.getController();
        eventController.setEventInfo(eventList[i]);
      }
    } else {
      for (int i = 0; i < eventList.length; i++) {
        FXMLLoader eventLoader = new FXMLLoader(Master.class.getResource("fxml/event_design.fxml"));
        adds[i] = eventLoader.load();
        eventItems.getChildren().add(adds[i]);
        eventItems.getChildren().get(0).setLayoutX(0);
        EventItem eventController = eventLoader.getController();
        eventController.setEventInfo(eventList[i]);
      }
    }
    eventItems.setSpacing(5);
    eventScroll.setContent(eventItems);
    eventItems.setAlignment(Pos.CENTER);
  }

  @FXML
  void toggleDropDown(MouseEvent event) {
    boolean isClicked = !vbNameDropDown.isVisible();
    vbNameDropDown.setVisible(isClicked);
    String userNameEffectOn = effectOn + "-fx-border-width: 0 1 1 1; -fx-border-style: solid; -fx-border-color: #FAFAFA;";
    String userNameEffectOff = effectOn;
    pnUserName.setStyle((isClicked ? userNameEffectOn : userNameEffectOff));
    btnArrowPoint.setText((isClicked ? "▲" : "▼"));
  }

  @FXML
  void openProfileSettings(ActionEvent event) {

  }

  @FXML
  void logOutUser(ActionEvent event) {

  }

  void hoverEffectOn(Button button) {
    button.setStyle(effectOn);
  }

  void hoverEffectOff(Button button) {
    button.setStyle(effectOff);
  }

  @FXML
  void onProfileMouseEnter(MouseEvent event) {
    hoverEffectOn(btnProfileSettings);
  }

  @FXML
  void onProfileMouseExit(MouseEvent event) {
    hoverEffectOff(btnProfileSettings);
  }

  @FXML
  void onLogoutMouseEnter(MouseEvent event) {
    hoverEffectOn(btnLogout);
  }

  @FXML
  void onLogoutMouseExit(MouseEvent event) {
    hoverEffectOff(btnLogout);
  }

  @FXML
  void nameHoverOn(MouseEvent event) {
    pnUserName.setStyle(effectOn);
  }

  @FXML
  void nameHoverOff(MouseEvent event) {
    pnUserName.setStyle(effectOff);
  }

  public void setMaster(MasterController master) {
    this.master = master;
  }
}