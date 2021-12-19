package com.colliu.colliu.controllers;

import event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class EventItem {

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
  void attendEvent(ActionEvent event) {

  }

  public void setEventInfo(Event eventInfo) {
    this.lblTitle.setText(eventInfo.getTitle());
    this.lblDate.setText(String.valueOf(eventInfo.getDate()).substring(0, 10));
    this.lblLocation.setText(eventInfo.getLocation());
    this.lblTime.setText(eventInfo.getDate().toString().substring(11,16));
  }

}
