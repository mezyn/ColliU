package com.colliu.colliu.controllers;

import event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class EventItem {
  double normalHeight;
  double detailsHeight;

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
  void attendEvent(ActionEvent event) {

  }

  @FXML
  void showDetails(ActionEvent event) {
    boolean showDetails = !(pnEventDetails.isVisible());
    spAllEvent.setMaxHeight((showDetails ? normalHeight : normalHeight - detailsHeight));
    btnShowDetails.setText((showDetails ? "Hide Details" : "Show Details"));
    pnEventDetails.setVisible(showDetails);
  }

  public void setEventInfo(Event eventInfo) {
    this.lblTitle.setText(eventInfo.getTitle());
    this.lblDate.setText(String.valueOf(eventInfo.getDate()).substring(0, 10));
    this.lblLocation.setText(eventInfo.getLocation());
    this.lblTime.setText(eventInfo.getTime());
    normalHeight = spAllEvent.getPrefHeight();
    System.out.println(normalHeight);
    detailsHeight = pnEventDetails.getPrefHeight();
    spAllEvent.setMaxHeight(normalHeight - detailsHeight);
    pnEventDetails.setVisible(false);
  }

}
