package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import event.Event;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
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
import javafx.scene.transform.Rotate;
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
  SequentialTransition sQ;
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

  @FXML
  private ImageView imgReactionOne;

  @FXML
  private ImageView imgReactionTwo;

  @FXML
  private ImageView imgReactionThree;

  @FXML
  private ImageView imgReactionFour;

  @FXML
  private Label lblReactions;

  private MasterController master;

  @FXML
  void attendEvent(ActionEvent event) throws IOException {
    setAttending(!attending);
  }

  @FXML
  void spinReaction(MouseEvent event) {
    ImageView reaction = ((ImageView) event.getSource()); // Gets the image that is hovered
    RotateTransition rotateLeft = new RotateTransition(Duration.millis(100), reaction); // Our animation for rotating picture left
    RotateTransition rotateRight = new RotateTransition(Duration.millis(100), reaction); // Our animation for rotating picture right
    RotateTransition rotatePaus = new RotateTransition(Duration.millis(100), reaction); // Slight paus in animation for smoothness

    rotateLeft.setByAngle(15); // Rotate 15 degrees to left
    rotateLeft.setCycleCount(1); // Rotate once
    rotateLeft.setInterpolator(Interpolator.LINEAR);

    rotateRight.setByAngle(-30); // Rotates 30 degrees to the right(15 degrees from 0)
    rotateRight.setCycleCount(1); // Rotate once
    rotateRight.setInterpolator(Interpolator.LINEAR);

    rotatePaus.setByAngle(0); // Does not rotate at all
    rotateRight.setCycleCount(1); // Pauses only once

    sQ = new SequentialTransition(rotateLeft, rotateRight, rotatePaus); // First rotate left, then right, then paus
    sQ.setCycleCount(Animation.INDEFINITE); // Rotate indefinitely
    sQ.play(); // Start sequence.
  }

  @FXML
  void unSpin(MouseEvent event) {
    sQ.stop(); // Stop the sequence of rotations
    ImageView reaction = ((ImageView) event.getSource()); // Get image that was hovered
    double yx = reaction.getLocalToSceneTransform().getMyx(); // Get image's current rotation
    double yy = reaction.getLocalToSceneTransform().getMyy(); // Get image's current rotation
    double angle = -(Math.atan2(yx, yy) * 100) / 2; // Then calculate the angle that needs to be reverse for rotation 0
    RotateTransition evenOut = new RotateTransition(Duration.millis(100), reaction); // Create a new rotation animation
    evenOut.setByAngle(angle); // Set the calculated angle
    evenOut.setCycleCount(1); // Rotate only once
    evenOut.setInterpolator(Interpolator.LINEAR);
    evenOut.play(); // Start reverse rotation
  }

  @FXML
  void showDetails(ActionEvent event) {
    boolean showDetails = !(pnEventDetails.isVisible());
    spAllEvent.setMaxHeight((showDetails ? normalHeight : normalHeight - detailsHeight));
    btnShowDetails.setText((showDetails ? "Hide Details" : "Show Details"));
    pnEventDetails.setVisible(showDetails);
  }

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
    addReactions();
    pnEventDetails.setVisible(false);
  }

  @FXML
  void hoverOn(MouseEvent event) {
    if(event.getSource() instanceof Button)
      ((Button) event.getSource()).setOpacity(0.8);
    else
      ((ToggleButton) event.getSource()).setOpacity(0.8);
  }

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

  public void setAttending(boolean status) {
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

  @FXML
  void reactEvent(MouseEvent event) {
    ImageView reactionImage = ((ImageView) event.getSource());
    int reaction;
    String guEmail = master.getCurrentUser().getEmail();
    if (reactionImage.equals(imgReactionOne)) {
      reaction = 1;
      imgReactionOne.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color:red; -fx-border-style:solid;");
    } else if (reactionImage.equals(imgReactionTwo)) {
      reaction = 2;
    } else if (reactionImage.equals(imgReactionThree)) {
      reaction = 3;
    } else {
      reaction = 4;
    }
    this.event.addReaction(guEmail, reaction);
    addReactions();
    master.saveEvents();
  }

  void addReactions() {
    int reactions = event.getReactions().size();
    lblReactions.setText("" + reactions);
    System.out.println(event.getReactions());
  }
}
