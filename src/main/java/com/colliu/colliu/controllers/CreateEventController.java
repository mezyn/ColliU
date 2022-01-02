package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

/*
The goal of this class is to control EventCreation.fxml file, that is, to handle the event creation.
*/

public class CreateEventController {
    private MasterController master;
    private String time;
    private LocalDate eventDate;

    @FXML
    private ChoiceBox<String> cbCategories;

    @FXML
    private DatePicker dpDate;

    @FXML
    private ChoiceBox<String> cbHours;

    @FXML
    private ChoiceBox<String> cbMinutes;

    @FXML
    private ChoiceBox<String> cbPrograms;

    @FXML
    private TextField eventTitle;

    @FXML
    private TextField eventLocation;

    @FXML
    private TextField descriptionField;

    @FXML
    private Label warningLabel;

    /*
    Upon 'create event' button click, the application check if the input from user is correct and valid.
    Below is a number of features of what this method does;
    - None of the field should be empty; i.e. only the event with all required information can be created.
    - The method takes an event date with JavaFX DatePicker. A user cannot create an event in the past.
    Upon the button click, the event creation page is closed and the user is sent back to the event page.
    */

    @FXML
    void onCreateEventClicked(ActionEvent event) {
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
        } else {
            time = cbHours.getValue() + ":" + cbMinutes.getValue();
            master.createEvent(eventTitle.getText(), eventDate, time, eventLocation.getText(), cbPrograms.getValue(), descriptionField.getText(), cbCategories.getValue(), master.getCurrentUser().getEmail());
            master.saveEvents();
            master.showHomepage();
        }
    }

    public void setMaster(MasterController master) {
        this.master = master;
    }


    @FXML
    void setDate(ActionEvent event) {
        eventDate = dpDate.getValue();
    }

    public void load() {
        //Adding relevant alternatives to the choice boxes/dropdown boxes, among which a user can select information about events
        cbCategories.setValue("Choose category");
        cbHours.setValue("HH");
        cbMinutes.setValue("MM");
        cbPrograms.setValue("Choose program");

        ObservableList<String> categories = FXCollections.observableArrayList("Gaming", "Guest Lecture", "Hackathon", "Lunch Lecture", "Mingle", "Sports", "Student Union", "Workshop", "Others");
        cbCategories.setItems(categories);
        ObservableList<String> hours = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        cbHours.setItems(hours);
        ObservableList<String> minutes = FXCollections.observableArrayList("00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55");
        cbMinutes.setItems(minutes);
        ObservableList<String> programs = FXCollections.observableArrayList("Datavetenskap", "Systemvetenskap", "Kognitionsvetenskap", "Software engineering and management");
        cbPrograms.setItems(programs);
    }

    @FXML
    void onCancelClick(ActionEvent event) {
        master.showHomepage();
    }

}
