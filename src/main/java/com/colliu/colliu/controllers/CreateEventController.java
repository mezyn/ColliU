package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import event.EventMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;


/**
The goal of this class is to control EventCreation.fxml file, that is, to handle the event creation.
*/

public class CreateEventController {
  private MasterController master;
  private EventMethods eventMethods;
  private LocalDate eventDate;

  @FXML
  private ChoiceBox<String> cbCategories; //Event categories

  @FXML
  private DatePicker dpDate; //Event date

  @FXML
  private ChoiceBox<String> cbHours; //Event hour(time)

  @FXML
  private ChoiceBox<String> cbMinutes; //Event minute(time)

  @FXML
  private ChoiceBox<String> cbPrograms; //Study program related to event

  @FXML
  private TextField eventTitle;

  @FXML
  private TextField eventLocation;

  @FXML
  private TextField descriptionField;

  /* When a user did not fill all necessary information to create an event,
    The application tells the user what to be fixed by presenting messages with the JavaFX label. */
  @FXML
  private Label warningLabel;



  /**
    Upon 'create event' button click, the application check if the input from user is correct and valid.
    Below is a number of features of what this method does;
    - None of the field should be empty; i.e. only the event with all required information can be created.
    - The method takes an event date with JavaFX DatePicker. A user cannot create an event in the past.
    Upon a button click, the event creation page is closed and the user is sent back to the event page.
  */

  @FXML
  private void onCreateEventClicked() {
    if (eventTitle.getText().isBlank()) {
      warningLabel.setText("Title cannot be empty.");
    } else if (eventLocation.getText().isBlank()) {
      warningLabel.setText("Location cannot be empty.");
    } else if (eventDate == null) {
      warningLabel.setText("Please select a date for the event.");
    } else if (eventDate.isBefore(LocalDate.now())) {
      warningLabel.setText("Event cannot be created for a past date.");
    } else if (cbHours.getValue().equals("HH") || cbMinutes.getValue().equals("MM")) {
      warningLabel.setText("Event time cannot be empty.");
    } else if (cbCategories.getValue().equals("Choose category")) {
      warningLabel.setText("Choose a category for the event.");
    } else if (cbPrograms.getValue().equals("Choose program")) {
      warningLabel.setText("Choose a study program that is relevant to the event.");
    } else if (descriptionField.getText().isBlank()) {
      warningLabel.setText("Write a description about the event.");
    } else { // When all required information has been registered by a user
        String time = cbHours.getValue() + ":" + cbMinutes.getValue();
      eventMethods.addEvent(eventTitle.getText(), eventDate, time, eventLocation.getText(), descriptionField.getText(), cbCategories.getValue(), cbPrograms.getValue(), master.getCurrentUser().getEmail());
      master.saveEvents();
      master.showHomepage();
    }
  }

  // When cancel clicked, a user is sent back to the event page.
  @FXML
  private void onCancelClick() {
    master.showHomepage();
  }

  public void setMaster(MasterController master) {
    this.master = master;
    this.eventMethods = master.getEventReference();
  }

  @FXML
  private void setDate() {
    eventDate = dpDate.getValue();
  }

  /**
    This method contains components that shall be loaded in event creation page.
  */
  public void load() {

    // The page should show relevant alternatives to the choice boxes/dropdown boxes, among which a user can select information about events
    cbCategories.setValue("Choose category");
    cbHours.setValue("HH");
    cbMinutes.setValue("MM");
    cbPrograms.setValue("Choose program");

    // Alternatives that a user can choose - this will keep consistency of the application
    ObservableList<String> categories = FXCollections.observableArrayList("Gaming", "Guest Lecture", "Hackathon", "Lunch Lecture", "Mingle", "Sports", "Student Union", "Workshop", "Others");
    cbCategories.setItems(categories);
    ObservableList<String> hours = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    cbHours.setItems(hours);
    ObservableList<String> minutes = FXCollections.observableArrayList("00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55");
    cbMinutes.setItems(minutes);
    ObservableList<String> programs = FXCollections.observableArrayList("Datavetenskap", "Systemvetenskap", "Kognitionsvetenskap", "Software engineering and management");
    cbPrograms.setItems(programs);
  }
}
