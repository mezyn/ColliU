package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import event.Event;
import event.EventMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class CreateEventController {

    MasterController master;

//    String currentUser = master.getCurrentUser().getEmail();

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    private String time;

    @FXML
    private DatePicker date;


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

    @FXML
    private AnchorPane anchorPane;

    Stage stage;

    @FXML
    void closeWindow(ActionEvent event) {
        stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }


    @FXML
    void onCreateEventClicked(ActionEvent event) throws Exception {
        if (eventTitle.getText().isBlank()) {
            warningLabel.setText("Title cannot be empty.");
        } else if (eventLocation.getText().isBlank()) {
            warningLabel.setText("Location cannot be empty.");
        } else if (eventDate == null) {
            warningLabel.setText("Please select a date for the event.");
        } else if (eventDate.isBefore(LocalDate.now())) {
            warningLabel.setText("Event cannot be created for a past date.");
        } else if (hourChoiceBox.getValue().equals("HH") || minuteChoiceBox.getValue().equals("MM")) {
            warningLabel.setText("Event time cannot be empty.");
        } else if (categoryChoiceBox.getValue().equals("Choose category")) {
            warningLabel.setText("Choose a category for the event.");
        } else if (programChoiceBox.getValue().equals("Choose program")) {
            warningLabel.setText("Choose a study program that is relevant to the event.");
        } else if (descriptionField.getText().isBlank()) {
            warningLabel.setText("Write a description about the event.");
        } else {

            time = hourChoiceBox.getValue() + ":" + minuteChoiceBox.getValue();
            master.createEvent(eventTitle.getText(), eventDate, time, eventLocation.getText(), programChoiceBox.getValue(), descriptionField.getText(), categoryChoiceBox.getValue(), master.getCurrentUser().getEmail());
            master.saveEvents();
            closeWindow(event);
            master.showEventPage();

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
        //date.setValue(LocalDate.now());
        eventDate = date.getValue();
    }

    public void load() {
        //time = hourChoiceBox.getValue() + ":" + minuteChoiceBox.getValue();
        //categoryChoiceBox.getItems().addAll("Gaming", "Guest Lecture", "Hackathon","Lunch Lecture", "Mingle", "Sports","Student Union", "Workshop","Others");
        //hourChoiceBox.getItems().addAll("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
        //minuteChoiceBox.getItems().addAll("00","05","10","15","20","25","30","35","40","45","50","55");
        //programChoiceBox.getItems().addAll("Datavetenskap", "Systemvetenskap", "Kognitionsvetenskap", "Software engineering and management");
        categoryChoiceBox.setValue("Choose category");
        hourChoiceBox.setValue("HH");
        minuteChoiceBox.setValue("MM");
        programChoiceBox.setValue("Choose program");
        ObservableList<String> categories = FXCollections.observableArrayList("Gaming", "Guest Lecture", "Hackathon", "Lunch Lecture", "Mingle", "Sports", "Student Union", "Workshop", "Others");
        categoryChoiceBox.setItems(categories);
        ObservableList<String> hours = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        hourChoiceBox.setItems(hours);
        ObservableList<String> minutes = FXCollections.observableArrayList("00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55");
        minuteChoiceBox.setItems(minutes);
        ObservableList<String> programs = FXCollections.observableArrayList("Datavetenskap", "Systemvetenskap", "Kognitionsvetenskap", "Software engineering and management");
        programChoiceBox.setItems(programs);

    }

}
