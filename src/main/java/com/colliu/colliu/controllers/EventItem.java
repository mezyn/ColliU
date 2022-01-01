package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import event.Event;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;

public class EventItem {
  double normalHeight;
  double detailsHeight;
  int eventId;
  boolean attending;
  Event event;

  int eventView = 1;

  @FXML
  private Pane eventBackground;

  @FXML
  private ImageView imgBanner;

  @FXML
  private Label lblAttendees;

  @FXML
  private Label lblDate;

  @FXML
  private Label lblLocation;

  @FXML
  private Label lblTime;

  @FXML
  private Label lblTitle;

  @FXML
  private TextArea txtDescription;

  @FXML
  private Button btnShowDetails;

  @FXML
  private Pane pnEventDetails;

  @FXML
  private StackPane spAllEvent;

  @FXML
  private ToggleButton btnAttend;

  private MasterController master;

  /**This method sets a users' status to Attending the event if the button is pressed. **/
  @FXML
  void attendEvent(ActionEvent event) throws IOException {
    setAttending(!attending);
  }

  /**This method spins the reaction image when the user hovers over it. **/
  @FXML
  void spinReaction(MouseEvent event) throws InterruptedException {
    ImageView reaction = ((ImageView) event.getSource());
    RotateTransition rt = new RotateTransition(Duration.millis(100), reaction);
    rt.setByAngle(360);
    rt.setCycleCount(Animation.INDEFINITE);
    rt.setInterpolator(Interpolator.LINEAR);
    rt.play();
  }

  /**This method stops the reaction image to spin if it is not hovered over again. **/
  @FXML
  void unSpin(MouseEvent event) {
    ImageView reaction = ((ImageView) event.getSource());
    RotateTransition rt = new RotateTransition(Duration.millis(100), reaction);
    rt.setByAngle(0);
    rt.setCycleCount(1);
    rt.setInterpolator(Interpolator.LINEAR);
    rt.stop();
  }

/**This method allows the user to show the event description. **/
  @FXML
  void showDetails(ActionEvent event) {
    boolean showDetails = !(pnEventDetails.isVisible());
    spAllEvent.setMaxHeight((showDetails ? normalHeight : normalHeight - detailsHeight));
    btnShowDetails.setText((showDetails ? "Hide Details" : "Show Details"));
    pnEventDetails.setVisible(showDetails);
  }

  /**This method gets all the information regarding the event except for the description.
   It also switches the status between Attend/Cancel depending on if the button is pressed or not pressed. **/

  public void setEventInfo() {
    this.lblTitle.setText(event.getTitle());
    this.lblDate.setText(String.valueOf(event.getDate()).substring(0, 10));
    this.lblLocation.setText(event.getLocation());
    this.lblTime.setText(event.getTime());
    normalHeight = spAllEvent.getPrefHeight();
    detailsHeight = pnEventDetails.getPrefHeight();
    spAllEvent.setMaxHeight(normalHeight - detailsHeight);
    lblAttendees.setText(String.valueOf(event.getAttending().size()));
    btnAttend.setDisable(event.getDate().isBefore(LocalDate.now()));
    String uEmail = master.getCurrentUser().getEmail();
    attending = event.getAttending().contains(uEmail);
    if (attending) {
      event.addAttendee(uEmail);
      btnAttend.setStyle("-fx-background-color: rgb(246, 168, 166);");
      btnAttend.setText("Cancel");
    } else {
      event.delAttendee(uEmail);
      btnAttend.setStyle("-fx-background-color: rgb(192,236,204);");
      btnAttend.setText("Attend");
    }
    pnEventDetails.setVisible(false);
  }

  /**This method creates a hover effect when the user is hovering the event. **/
  @FXML
  void hoverOn(MouseEvent event) {
    if(event.getSource() instanceof Button)
      ((Button) event.getSource()).setOpacity(0.8);
    else
      ((ToggleButton) event.getSource()).setOpacity(0.8);
  }

  /**This method removes the hovering effect when the user is no longer hovering the event. **/
  @FXML
  void hoverOff(MouseEvent event) {
    if(event.getSource() instanceof Button)
      ((Button) event.getSource()).setOpacity(1);
    else
      ((ToggleButton) event.getSource()).setOpacity(1);
  }
  
  public void setMaster(MasterController master) {
    this.master = master;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  /**This method stores the attendees of the event to the event object and alters the view depending on
   if the user has pressed "Attend" or not. **/
  public void setAttending(boolean status) throws IOException {
    this.attending = status;
    String uEmail = master.getCurrentUser().getEmail();
    if (attending) {
      event.addAttendee(uEmail);
      btnAttend.setStyle("-fx-background-color: rgb(246, 168, 166);");
      btnAttend.setText("Cancel");
    } else {
      event.delAttendee(uEmail);
      btnAttend.setStyle("-fx-background-color: rgb(192,236,204);");
      btnAttend.setText("Attend");
    }
    master.saveEvents();
    setEventInfo();
  }
}
