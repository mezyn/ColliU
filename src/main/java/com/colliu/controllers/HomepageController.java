package com.colliu.controllers;

import com.colliu.Main;
import com.colliu.PageController;
import com.colliu.event.Event;
import com.colliu.event.EventMethods;
import com.colliu.user.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import com.colliu.miscellaneous.Info;
import com.colliu.miscellaneous.Style;
import org.controlsfx.control.ToggleSwitch;
import com.colliu.user.User;

/**
 * This is the controller of EventPage.fxml, it lets the user display and modify information in the ui.
 */
public class HomepageController {
  private PageController master;
  private EventMethods eventMethods;
  private User currentUser;
  private final ArrayList<String> tags = new ArrayList<>();


  @FXML
  private Button btnCreateEvent;

  @FXML
  private VBox eventItems;

  @FXML
  private ScrollPane eventScroll;

  @FXML
  private VBox vbNameDropDown;

  @FXML
  private Pane pnUserName;

  @FXML
  private Button btnArrowPoint;

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


  /**
   * This method lets the user switch between upcoming, past and attending events.
   * Upcoming events are events that haven't occurred yet.
   * Past events are events that have already occurred.
   * Attending events are upcoming events that the user has accepted.
   - @param event which Button that was pressed, to identify the user's selection.
   */

  @FXML
  private void eventView(ActionEvent event) {
    Button clicked = ((Button) event.getSource()); // cast to button
    Event[] events;
    // Change the style of UI and assign info to events array.
    if (clicked == btnUpcoming) { // Upcoming
      btnUpcoming.setUnderline(true);
      btnPast.setUnderline(false);
      btnAttending.setUnderline(false);
      events = eventMethods.getUpcomingEvents();
    } else if (clicked == btnPast) { // Past
      btnUpcoming.setUnderline(false);
      btnPast.setUnderline(true);
      btnAttending.setUnderline(false);
      events = eventMethods.getPastEvents();
    } else { // Attending
      btnUpcoming.setUnderline(false);
      btnPast.setUnderline(false);
      btnAttending.setUnderline(true);
      events = eventMethods.getAttendingEvents();
    }
    loadEvents(events);
    vbCategories.setDisable(clicked != btnUpcoming);
  }

  /**
   * Show or hide navigation bar beneath User's name in the upper right corner.
   */

  @FXML
  private void toggleDropDown() {
    boolean isClicked = !vbNameDropDown.isVisible(); // opposite
    vbNameDropDown.setVisible(isClicked); // Display / hide navigation bar
    String userNameEffect = Style.BACKGROUND_LIGHTGRAY
        + "-fx-border-width: 0 1 1 1; -fx-border-style: solid; -fx-border-color: #FAFAFA;";
    // set style and arrow direction depending on if toggled or not.
    pnUserName.setStyle((isClicked ? userNameEffect : Style.BACKGROUND_NONE));
    btnArrowPoint.setText((isClicked ? "▲" : "▼"));
  }

  /**
   * This method redirects the user to their Profile Settings page if the button is clicked.
   * */

  @FXML
  private void openProfileSettings() {
    master.showProfileSettingsPage();
  }

  /**
   * Calls method in master-controller to show LoginPage.fxml.
   */

  @FXML
  private void logOutUser() {
    master.showLogin();
  }

  /**
   * This method creates a hovering effect when the user is hovering over the button.
   - @param event Helps identify which button that triggered the method.
   */

  @FXML
  private void hoverEffectOn(MouseEvent event) {
    ((Button) event.getSource()).setStyle(Style.BACKGROUND_LIGHTGRAY);
  }

  /**
   * This method removes the hovering effect when the user is not hovering over the button.
   - @param event Helps identify which button that triggered the method.
   */
  @FXML
  private void hoverEffectOff(MouseEvent event) {
    ((Button) event.getSource()).setStyle(Style.BACKGROUND_NONE);
  }

  /**
   * This method creates a hovering effect when the user is hovering-
   * over their name in their profile.
   */
  @FXML
  private void nameHoverOn() {
    pnUserName.setStyle(Style.BACKGROUND_LIGHTGRAY);
  }

  /**
   * This method removes the hovering effect when the user is no longer-
   * hovering over their name in their profile.
   */
  @FXML
  private void nameHoverOff() {
    pnUserName.setStyle(Style.BACKGROUND_NONE);
  }

  /**
   * When a toggleswitch is pressed this method is called, it adds a tag to a global arraylist
   * Then sends the information to a method in userMethods to filter the events based on tags.
   - @Paramt Category is the
  */
  @FXML
  private void onFilterClick(String category) {
    int categoryIndex = tags.indexOf(category);
    if (categoryIndex >= 0) {
      tags.remove(categoryIndex);
    } else {
      tags.add(category);
    }
    String[] tagsToFilter = tags.toArray(new String[0]);
    loadEvents(tags.size() > 0 ? eventMethods.filterEvents(tagsToFilter)
        : eventMethods.getUpcomingEvents());
  }

  /**
   * This method redirects the user(Staff) to the Create Event page.
   */
  @FXML
  private void createNewEvent()  {
    master.showEventCreationPage();
  }

  /**
   * Increases the speed when scrolling the ScrollPane wrapping all the events.
   - @param event is used to identify the scrollpane that called the method (for universal support).
   */

  @FXML
  private void scrollSpeed(ScrollEvent event) {
    final double speed = 0.001;
    double deltaY = event.getDeltaY() * speed;
    eventScroll.setVvalue(eventScroll.getVvalue() - deltaY);
  }
  /*
  **************
  * Functionality
  **************
   */

  /**
   - @param master Reference to the existing MasterController object.
   */

  public void setMaster(PageController master) {
    this.master = master;
    this.eventMethods = master.getEventReference();
  }

  /**
   * Loads all required information when opening the Homepage.
   */

  public void load() {
    Event[] allEvents = eventMethods.getUpcomingEvents(); // Default filter
    currentUser = master.getCurrentUser();
    setLoggedInName();
    loadEvents(allEvents);
    System.out.println(allEvents.length);
    // Load notifications and hide create event button if a student
    if (!(currentUser instanceof Staff)) {
      vbNameDropDown.getChildren().remove(btnCreateEvent);
      loadNotifications();
    } else {
      loadStaff();
    }
    setCategories(); // Adds the toggleswitches in the left menu.
  }

  /**
   * Loads all event data into the UI. An external FXML is used and loaded for each event.
   - @param events the arraydata of all events that is to be loaded.
   */

  private void loadEvents(Event[] events) {
    int size = events.length;
    // resets VBox contents(all previously loaded event_design.fxml)
    eventItems.getChildren().clear();
    Node[] eventList = new StackPane[size];
    for (int i = 0; i < size; i++) {
      try { // Attempt loading our event_deisgn.fxml and assign data from array to its controller.
        FXMLLoader eventLoader = new FXMLLoader(Main.class.getResource("fxml/event_design.fxml"));
        eventList[i] = eventLoader.load();
        eventItems.getChildren().add(eventList[i]);
        eventItems.getChildren().get(0).setLayoutX(0);
        EventItem eventController = eventLoader.getController();
        eventController.setMaster(master, this);
        eventController.load(events[i]);
      } catch (IOException failedLoad) {
        failedLoad.printStackTrace(); // This should only happen if the FXML is missing.
        // Might want to code in a manual event design as a backup.
      }
    }
    Label noEvents;
    if (events.length == 0) {
      noEvents = new Label("There are no events, check back later!");
      noEvents.setMinSize(500, 30);
      noEvents.setAlignment(Pos.CENTER);
      noEvents.setStyle("font-size:30px");
      eventItems.getChildren().add(noEvents);
    }
    eventItems.setSpacing(5);
    eventScroll.setContent(eventItems);
    eventItems.setAlignment(Pos.CENTER);
  }

  /**
   * Displays all the new events to user, can be dismissed permanently.
   */

  private void loadNotifications() {
    Event[] newEvents = eventMethods.getNotifications();
    int size = newEvents.length;
    // If no new events create 1 extra space.
    Node[] notifications = new Node[size == 0 ? 1 : size];

    for (int i = 0; i < size; i++) { // Loop the array if any events.
      String text = newEvents[i].getTitle();
      int index = newEvents[i].getId();
      // Creates a pane with the information and add into the array.
      notifications[i] = notificationPane(text, index, false);
    }

    if (size == 0) { // if there are no notifications.
      String empty = "No new events.";
      notifications[0] = notificationPane(empty, -1, true);
    }

    vbNotifications.setSpacing(5);
    vbNotifications.getChildren().setAll(notifications);
  }

  private void setLoggedInName() {
    lblName.setText(currentUser.getFirstName()
        + " " + currentUser.getLastName());
  }

  /**
   * If the logged in user is a Staff.
   */
  private void loadStaff() {
    apMiddle.getChildren().remove(btnAttending);
    btnUpcoming.setLayoutX(415);
    btnPast.setLayoutX(500);
    lblEventPageHeader.setText("Your Events");
  }

  /**
   * Creates a custom pane for displaying notifications.
   - @param text - The title of the event
   - @param index - Where in the arraylist of events the event is.
   - @param empty - Whether to create a special pane without an "X" mark and event listener.
   - @return - The custom created pane.
   */
  private Pane notificationPane(String text, int index, boolean empty) {
    // Create pane with style.
    Pane pnNotification = new Pane();
    pnNotification.setPrefHeight(50);
    pnNotification.setStyle("-fx-background-color:#FFF; -fx-border-width:1 0 1 0;"
        + " -fx-border-color:#AAA;");
    // Different styling if empty
    if (empty) {
      Label noEvents = new Label("No new events since last visit.");
      noEvents.setAlignment(Pos.CENTER);
      noEvents.setPrefHeight(50);
      noEvents.setPrefWidth(225);
      pnNotification.getChildren().add(noEvents);
    } else { // Styles notification and sets text.
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

      // Eventlisteners for the X mark.
      markRead.setOnMouseEntered(e -> pnNotification.setOpacity(0.4));
      markRead.setOnMouseExited(e -> pnNotification.setOpacity(1));
      markRead.setOnMouseClicked(event -> {
        // If clicked then add user's email to "seenBy" arraylist in event.java
        eventMethods.getAllEvents().get(index).addSeenBy(currentUser.getEmail());
        master.saveEvents();
        loadNotifications();
      });
    }
    return pnNotification;
  }

  /**
   * Creates the toggleswitches for filtering evengts by category.
   * I was unable to figure out how to do this with scenebuilder since it's an external library.
   */
  private void setCategories() {
    int size = Info.CATEGORIES.length;
    Node[] switches = new Node[size]; // Array of nodes for each category.
    // Loop through all the categories.
    for (int i = 0; i < size; i++) {
      // Creates the nodes necessary for each category.
      Label lblCategory = new Label(Info.CATEGORIES[i]);
      ToggleSwitch tsCategory = new ToggleSwitch();
      // Adds an eventlistener for the toggelswitch
      tsCategory.setOnMouseClicked(e -> onFilterClick(lblCategory.getText()));
      // Styling
      tsCategory.setMaxWidth(30);
      lblCategory.setMinWidth(168);
      tsCategory.setStyle(Style.TOGGLESWITCH);
      // Add to new HBox for layout purposes
      HBox hbCategory = new HBox();
      hbCategory.getChildren().add(lblCategory);
      hbCategory.getChildren().add(tsCategory);
      // Add to array.
      switches[i] = hbCategory;
    }
    vbCategories.getChildren().clear(); // Remove all items in-case this method is called again.
    vbCategories.getChildren().setAll(switches);
    vbCategories.setStyle("-fx-background-color:#EEE;");
  }

}
