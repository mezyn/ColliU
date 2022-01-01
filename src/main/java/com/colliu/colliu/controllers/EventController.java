package com.colliu.colliu.controllers;

import com.colliu.colliu.Master;
import com.colliu.colliu.MasterController;
import event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.io.IOException;
import java.util.ArrayList;

public class EventController {

  final String BUTTON_HOVER_ON = "-fx-background-color: #FAFAFA;";
  final String BUTTON_HOVER_OFF = "-fx-background-color: none;";
  final int STAFF = 3;

  MasterController master;


  @FXML
  private Button btnCreateEvent;

  @FXML
  private Button btnLogout;


  @FXML
  private VBox eventItems;

  @FXML
  private ScrollPane eventScroll;

  @FXML
  private Label filterCategory;

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
  private Button btnProfileSettings;

  @FXML
  private VBox vbNameDropDown;

  @FXML
  private Pane pnUserName;

  @FXML
  private Button btnArrowPoint;

  @FXML
  private CheckBox cb1;

  @FXML
  private CheckBox cb2;

  @FXML
  private CheckBox cb3;

  @FXML
  private CheckBox cb4;

  @FXML
  private CheckBox cb5;

  @FXML
  private CheckBox cb6;

  @FXML
  private CheckBox cb7;

  @FXML
  private CheckBox cb8;

  @FXML
  private CheckBox cb9;

  @FXML
  private Label lblName;

  @FXML
  private Button btnUpcoming;

  @FXML
  private Button btnPast;

  @FXML
  private Button btnAttending;

  @FXML
  private VBox vbNotifications;

  @FXML
  void closeProgram(ActionEvent event) {

  }

  @FXML
  void filterEvent(MouseEvent event) {

  }

  @FXML
  void openSettings(MouseEvent event) {

  }

  @FXML
  void viewAnnouncements(MouseEvent event) {

  }

  /**This method alters which information about the event that is shown to the user depending on if the Event is
   a past or upcoming event.**/
  @FXML
  void eventView(ActionEvent event) {
    Button clicked = ((Button) event.getSource());
    Event[] events;
    if (clicked == btnUpcoming) {
      btnUpcoming.setUnderline(true);
      btnPast.setUnderline(false);
      btnAttending.setUnderline(false);
      events = master.getUpcomingEvents();
    } else if(clicked == btnPast) {
      btnUpcoming.setUnderline(false);
      btnPast.setUnderline(true);
      btnAttending.setUnderline(false);
      events = master.getPastEvents();
    } else {
      btnUpcoming.setUnderline(false);
      btnPast.setUnderline(false);
      btnAttending.setUnderline(true);
      events = master.getAttendingEvents();
    }
    try {
      loadEvents(events);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**This method loads all the events without filtering. **/
  public void loadEvents(Event[] events) throws IOException {
    eventItems.getChildren().clear(); // resets VBox contents(event list)
    int size = events.length;
    String uEmail = master.getCurrentUser().getEmail();
    setName();
    Node[] eventList = new StackPane[size];
    for (int i = 0; i < size; i++) {
      FXMLLoader eventLoader = new FXMLLoader(Master.class.getResource("fxml/event_design.fxml"));
      eventList[i] = eventLoader.load();
      eventItems.getChildren().add(eventList[i]);
      eventItems.getChildren().get(0).setLayoutX(0);
      EventItem eventController = eventLoader.getController();
      eventController.setMaster(master);
      eventController.setEvent(events[i]);
      eventController.setEventInfo();
    }
    eventItems.setSpacing(5);
    eventScroll.setContent(eventItems);
    eventItems.setAlignment(Pos.CENTER);
    if (master.getCurrentUser().getType() != STAFF) {
      vbNameDropDown.getChildren().remove(btnCreateEvent);
    }
  }

  public void loadNotifications() {
    Event[] events = master.getNotifications();
    int size = events.length;
    Node[] notifications = new Pane[size];

    for (int i = 0; i < size; i++) {
      notifications[i] = new Pane();
    }
  }

  /**This method shows or/and hides options by the user profile Icon depending on which of the arrows is pressed. **/
  @FXML
  void toggleDropDown(MouseEvent event) {
    boolean isClicked = !vbNameDropDown.isVisible();
    vbNameDropDown.setVisible(isClicked);
    String userNameEffectOn = BUTTON_HOVER_ON + "-fx-border-width: 0 1 1 1; -fx-border-style: solid; -fx-border-color: #FAFAFA;";
    pnUserName.setStyle((isClicked ? userNameEffectOn : BUTTON_HOVER_OFF));
    btnArrowPoint.setText((isClicked ? "▲" : "▼"));
  }

  @FXML
  /**This method redirects the user to their Profile Settings page if the button is clicked. **/
  void openProfileSettings(ActionEvent event) {
  }

  /**This method logs out the user when the logout button is clicked. **/
  @FXML
  void logOutUser(ActionEvent event) {
    master.showLogin();
  }

  /**This method creates a hovering effect when the user is hovering over the button. **/
  @FXML
  void hoverEffectOn(MouseEvent event) {
    ((Button) event.getSource()).setStyle(BUTTON_HOVER_ON);
  }

  /**This method removes the hovering effect when the user is not hovering over the button. **/
  @FXML
  void hoverEffectOff(MouseEvent event) {
    ((Button) event.getSource()).setStyle(BUTTON_HOVER_OFF);
  }

  /**This method creates a hovering effect when the user is hovering over their name in their profile. **/
  @FXML
  void nameHoverOn(MouseEvent event) {
    pnUserName.setStyle(BUTTON_HOVER_ON);
  }

  /**This method removes the hovering effect when the user is not hovering over their name in their profile. **/
  @FXML
  void nameHoverOff(MouseEvent event) {
    pnUserName.setStyle(BUTTON_HOVER_OFF);
  }

  /**This method enables the user to filter shown events depending on the set event-tags.**/
  @FXML
  void onFilterClick(ActionEvent event) throws Exception {
    ArrayList<String> tags = new ArrayList<>();

    if(cb1.isSelected()) {
      tags.add("Gaming");
    }
    if(cb2.isSelected()) {
      tags.add("Guest Lecture");
    }
    if(cb3.isSelected()) {
      tags.add("Hackathon");
    }
    if(cb4.isSelected()) {
      tags.add("Lunch lecture");
    }
    if(cb5.isSelected()) {
      tags.add("Mingle");
    }
    if(cb6.isSelected()) {
      tags.add("Sports");
    }
    if(cb7.isSelected()) {
      tags.add("Student Union");
    }
    if(cb8.isSelected()) {
      tags.add("Workshop");
    }
    if(cb9.isSelected()) {
      tags.add("Others");
    }

    String[] tagsToFilter = tags.toArray(new String[0]);
    loadEvents(master.filterEvents(tagsToFilter));
  }

  /**This method redirects the user(Staff) to the Create Event page. **/
  @FXML
  void createNewEvent(ActionEvent event) throws IOException {
    master.showEventCreationPage();
  }

  public void setMaster(MasterController master) {
    this.master = master;
  }

  public void onButtonPressOpenSettingsPage() throws Exception {
    master.showProfileSettingsPage();
  }

  void setName() {
    lblName.setText(master.getCurrentUser().getFirstName() + " " + master.getCurrentUser().getLastName());
  }
}
