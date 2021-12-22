package com.colliu.colliu.controllers;

import com.colliu.colliu.Master;
import com.colliu.colliu.MasterController;
import event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import user.Administrator;
import user.Staff;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;

public class EventController {

  MasterController master;

  @FXML
  private Button btnAttending;

  @FXML
  private Button btnLogout;

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

  public void loadEvents() throws IOException {

    ArrayList<Event> test2 = new Data().loadEvent();

    Node[] adds = new StackPane[test2.size()];

    for (int i = 0; i < test2.size(); i++) {
      FXMLLoader eventLoader = new FXMLLoader(Master.class.getResource("fxml/event_design.fxml"));
      adds[i] = eventLoader.load();
      adds[i].setLayoutX(5);
      adds[i].setLayoutY(90 * i);
      eventItems.getChildren().add(adds[i]);
      EventItem eventController = eventLoader.getController();
      eventController.setEventInfo(test2.get(i));
    }
    eventItems.setSpacing(5);
    eventScroll.setContent(eventItems);
  }

  public void setMaster(MasterController master) {
    this.master = master;
  }

  public void onButtonPressOpenSettingsPage() throws Exception {
    master.showProfileSettingsPage();
  }

  @FXML
  void changeName(MouseEvent event) {
    ((Label)event.getSource()).setText(master.user.getFirstName() + ((Staff) master.user).getDepartment());
  }
}
