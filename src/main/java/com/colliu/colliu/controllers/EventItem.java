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

public class EventItem {
  double normalHeight;
  double detailsHeight;
  int eventId;
  boolean attending;
  Event event;

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

  @FXML
  void attendEvent(ActionEvent event) throws IOException {
    setAttending(!attending);
  }

  @FXML
  void spinReaction(MouseEvent event) throws InterruptedException {
    ImageView reaction = ((ImageView) event.getSource());
    RotateTransition rt = new RotateTransition(Duration.millis(100), reaction);
    rt.setByAngle(360);
    rt.setCycleCount(Animation.INDEFINITE);
    rt.setInterpolator(Interpolator.LINEAR);
    rt.play();
  }

  @FXML
  void unSpin(MouseEvent event) {
    ImageView reaction = ((ImageView) event.getSource());
    RotateTransition rt = new RotateTransition(Duration.millis(100), reaction);
    rt.setByAngle(0);
    rt.setCycleCount(1);
    rt.setInterpolator(Interpolator.LINEAR);
    rt.stop();
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
    System.out.println(normalHeight);
    detailsHeight = pnEventDetails.getPrefHeight();
    spAllEvent.setMaxHeight(normalHeight - detailsHeight);
    pnEventDetails.setVisible(false);
    lblAttendees.setText(String.valueOf(event.getAttending().size()));
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

  public void setAttending(boolean status) throws IOException {
    this.attending = status;
    if (attending) {
      event.addAttendee(/*master.userMethods.currentUser;*/ "william@student.gu.se");
      btnAttend.setStyle("-fx-background-color: rgb(246, 168, 166);");
      btnAttend.setText("Cancel");
    } else {
      event.delAttendee(/*master.userMethods.currentUser*/"william@student.gu.se");
      btnAttend.setStyle("-fx-background-color: rgb(192,236,204);");
      btnAttend.setText("Attend");
    }
    master.saveEvents();
    setEventInfo();
  }
}
