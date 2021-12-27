package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import event.Event;
import event.EventMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import user.User;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateEventController {

    EventMethods eventMethods;
    MasterController master;

//    String currentUser = master.getCurrentUser().getEmail();

    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    
    private String time;

    @FXML
    private DatePicker myDatePicker;

    LocalDate eventDate;

    @FXML
    private ChoiceBox<String> hourChoiceBox;

    @FXML
    private ChoiceBox<String> minuteChoiceBox;

    @FXML
    private ChoiceBox<String> programChoiceBox;

    @FXML
    private TextField eventTitle;

    @FXML
    private TextField eventLocation;

    @FXML
    private TextField descriptionField;

    @FXML
    private Label warningLabel;
/*
    public CreateEventController() {
    }*/

    @FXML
    void onCreateEventClicked(ActionEvent event) {
        if (eventTitle.getText().isBlank()) {
            warningLabel.setText("Title cannot be empty.");
        } else if (eventLocation.getText().isBlank()) {
            warningLabel.setText("Location cannot be empty");
        } else if (eventDate == null) {
            warningLabel.setText("Please select a date for the event.");
        } else if (eventDate.isBefore(LocalDate.now())) {
            warningLabel.setText("Event cannot be created for a past date.");
        }  else if (hourChoiceBox.getValue().isBlank() || minuteChoiceBox.getValue().isBlank()) {
            warningLabel.setText("Event time cannot be empty.");
        } else if (categoryChoiceBox.getValue().isBlank()) {
            warningLabel.setText("Choose a category for the event.");
        } else if (programChoiceBox.getValue().isBlank()) {
            warningLabel.setText("Choose a study program that is relevant to the event.");
        } else if (descriptionField.getText().isBlank()) {
            warningLabel.setText("Write a description about the event.");
        } else {
            time = hourChoiceBox.getValue() + ":" + minuteChoiceBox.getValue();
            eventMethods.addEvent(eventTitle.getText(), eventDate, time, eventLocation.getText(), descriptionField.getText() , categoryChoiceBox.getValue(), programChoiceBox.getValue(), master.getCurrentUser().getEmail());
        }
    }



    /*@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryChoiceBox.getItems().addAll(categories);
        hourChoiceBox.getItems().addAll(hours);
        minuteChoiceBox.getItems().addAll(minutes);

    }*/

    public void setMaster(MasterController master) {
        this.master = master;
    }


    @FXML
    void setDate(ActionEvent event) {
        eventDate = myDatePicker.getValue();
    }

    public void load() {
        //time = hourChoiceBox.getValue() + ":" + minuteChoiceBox.getValue();
        categoryChoiceBox.getItems().addAll("Gaming", "Guest Lecture", "Hackathon","Lunch Lecture", "Mingle", "Sports","Student Union", "Workshop","Others");
        hourChoiceBox.getItems().addAll("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
        minuteChoiceBox.getItems().addAll("00","05","10","15","20","25","30","35","40","45","50","55");
        programChoiceBox.getItems().addAll("Datavetenskap", "Systemvetenskap", "Kognitionsvetenskap", "Software engineering and management");
    }
}
