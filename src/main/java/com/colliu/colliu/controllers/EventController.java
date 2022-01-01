package com.colliu.colliu.controllers;

import com.colliu.colliu.Master;
import com.colliu.colliu.MasterController;
import event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import java.io.IOException;
import java.util.ArrayList;
import miscellaneous.Info;
import miscellaneous.Style;
import org.controlsfx.control.ToggleSwitch;

public class EventController {
  private MasterController master;
  private ArrayList<String> tags = new ArrayList<>();


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
  private AnchorPane apMiddle;

  @FXML
  private Label lblEventPageHeader;

  @FXML
  private VBox vbCategories;

  @FXML
  void filterEvent(MouseEvent event) {

  }

  @FXML
  void openSettings(MouseEvent event) {

  }

  @FXML
  void viewAnnouncements(MouseEvent event) {

  }

  @FXML
  void eventView(ActionEvent event) {
    Button clicked = ((Button) event.getSource());
    Event[] events;
    if (clicked == btnUpcoming) {
      btnUpcoming.setUnderline(true);
      btnPast.setUnderline(false);
      btnAttending.setUnderline(false);
      events = master.getUpcomingEvents();
    } else if (clicked == btnPast) {
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
    loadEvents(events);
  }

  @FXML
  void toggleDropDown(MouseEvent event) {
    boolean isClicked = !vbNameDropDown.isVisible();
    vbNameDropDown.setVisible(isClicked);
    String userNameEffect = Style.BACKGROUND_LIGHTGRAY + "-fx-border-width: 0 1 1 1; -fx-border-style: solid; -fx-border-color: #FAFAFA;";
    pnUserName.setStyle((isClicked ? userNameEffect : Style.BACKGROUND_NONE));
    btnArrowPoint.setText((isClicked ? "▲" : "▼"));
  }

  @FXML
  void openProfileSettings(ActionEvent event) {
    master.showProfileSettingsPage();
  }

  @FXML
  void logOutUser(ActionEvent event) {
    master.showLogin();
  }

  @FXML
  void hoverEffectOn(MouseEvent event) {
    ((Button) event.getSource()).setStyle(Style.BACKGROUND_LIGHTGRAY);
  }

  @FXML
  void hoverEffectOff(MouseEvent event) {
    ((Button) event.getSource()).setStyle(Style.BACKGROUND_NONE);
  }

  @FXML
  void nameHoverOn(MouseEvent event) {
    pnUserName.setStyle(Style.BACKGROUND_LIGHTGRAY);
  }

  @FXML
  void nameHoverOff(MouseEvent event) {
    pnUserName.setStyle(Style.BACKGROUND_NONE);
  }

  /*
  By ticking the check boxes of categories, a user can filter out only the events of one's interest.
  If none of the checkboxes are selected(default), the page shows all the events without filtering.
  */
  @FXML
  void onFilterClick(String category) {
    int categoryIndex = tags.indexOf(category);
    if (categoryIndex >= 0) {
      tags.remove(categoryIndex);
    } else {
      tags.add(category);
    }
    System.out.println(category);
    String[] tagsToFilter = tags.toArray(new String[0]);
    loadEvents(master.filterEvents(tagsToFilter));
  }

  @FXML
  void createNewEvent(ActionEvent event) throws IOException {
    master.showEventCreationPage();
  }

  @FXML
  public void onButtonPressOpenSettingsPage() throws Exception {
    master.showProfileSettingsPage();
  }

  @FXML
  void scrollSpeed(ScrollEvent event) {
    final double SPEED = 0.001;
    double deltaY = event.getDeltaY() * SPEED;
    eventScroll.setVvalue(eventScroll.getVvalue() - deltaY);
  }

  public void load() {
    Event[] allEvents = master.getUpcomingEvents();
    setLoggedInName();
    loadEvents(allEvents);
    if (master.getCurrentUser().getType() != Info.TYPE_STAFF) {
      vbNameDropDown.getChildren().remove(btnCreateEvent);
      loadNotifications();
    } else {
      loadStaff();
    }

    setCategories();

  }

  /*
    This method loads all the events;
    To make it work for tags you can add parameters as needed:
   */

  private void loadEvents(Event[] events) {
    int size = events.length;
    eventItems.getChildren().clear(); // resets VBox contents(event list)
    Node[] eventList = new StackPane[size];
    for (int i = 0; i < size; i++) {
      try {
        FXMLLoader eventLoader = new FXMLLoader(Master.class.getResource("fxml/event_design.fxml"));
        eventList[i] = eventLoader.load();
        eventItems.getChildren().add(eventList[i]);
        eventItems.getChildren().get(0).setLayoutX(0);
        EventItem eventController = eventLoader.getController();
        eventController.setMaster(master);
        eventController.load(events[i]);
      } catch (IOException failedLoad) {
        failedLoad.printStackTrace(); // This should only happen if the FXML is missing.
        // Might want to code in a manual event design as a backup.
      }
    }
    eventItems.setSpacing(5);
    eventScroll.setContent(eventItems);
    eventItems.setAlignment(Pos.CENTER);
  }

  private void loadNotifications() {
    Event[] newEvents = master.getNotifications();
    int size = newEvents.length;
    Node[] notifications = new Node[size == 0 ? 1 : size];
    Pane pnNotification;

    for (int i = 0; i < size; i++) {
      String text = newEvents[i].getTitle();
      int index = newEvents[i].getId();
      notifications[i] = notificationPane(text, index, false);
    }

    if (size == 0) {
      String empty = "No new events.";
      notifications[0] = notificationPane(empty, -1, true);
    }

    vbNotifications.setSpacing(5);
    vbNotifications.getChildren().setAll(notifications);
  }

  public void setMaster(MasterController master) {
    this.master = master;
  }

  private void setLoggedInName() {
    lblName.setText(master.getCurrentUser().getFirstName() + " " + master.getCurrentUser().getLastName());
  }

  private void loadStaff() {
    apMiddle.getChildren().remove(btnAttending);
    btnUpcoming.setLayoutX(415);
    btnPast.setLayoutX(500);
    lblEventPageHeader.setText("Your Events");
  }

  private Pane notificationPane(String text, int index, boolean empty) {
    Pane pnNotification = new Pane();
    pnNotification.setPrefHeight(50);
    pnNotification.setStyle("-fx-background-color:#FFF; -fx-border-width:1 0 1 0; -fx-border-color:#AAA;");
    if (empty) {
      Label noEvents = new Label("No new events since last visit.");
      noEvents.setAlignment(Pos.CENTER);
      noEvents.setPrefHeight(50);
      noEvents.setPrefWidth(225);
      pnNotification.getChildren().add(noEvents);
    } else {
      Label title = new Label(text);
      title.setLayoutY(15);
      title.setLayoutX(5);
      Button markRead = new Button("×");
      markRead.setCursor(Cursor.HAND);
      markRead.setLayoutX(195);
      markRead.setLayoutY(12.5);
      markRead.setStyle("-fx-background-color:transparent;");
      markRead.setTooltip(new Tooltip("Mark as read."));
      pnNotification.getChildren().add(title);
      pnNotification.getChildren().add(markRead);

      markRead.setOnMouseEntered(e -> pnNotification.setOpacity(0.4));
      markRead.setOnMouseExited(e -> pnNotification.setOpacity(1));
      markRead.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          master.getAllEvents().get(index).addSeenBy(master.getCurrentUser().getEmail());
          master.saveEvents();
          loadNotifications();
        }
      });
    }
    return pnNotification;
  }

  private void setCategories() {
    int size = Info.CATEGORIES.length;
    Node[] switches = new Node[size];;
    for (int i = 0; i < size; i++) {
      HBox hbCategory = new HBox();
      Label lblCategory = new Label(Info.CATEGORIES[i]);
      ToggleSwitch tsCategory = new ToggleSwitch();
      tsCategory.setOnMouseClicked(e -> onFilterClick(lblCategory.getText()));
      tsCategory.setMaxWidth(30);
      lblCategory.setMinWidth(168);
      hbCategory.getChildren().add(lblCategory);
      hbCategory.getChildren().add(tsCategory);
      tsCategory.setStyle(Style.TOGGLESWITCH);
      switches[i] = hbCategory;
    }
    vbCategories.getChildren().clear();
    vbCategories.getChildren().setAll(switches);
    vbCategories.setStyle("-fx-background-color:#EEE;");
  }

}
