package com.colliu.colliu.controllers;

import com.colliu.colliu.MasterController;
import event.EventMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateEventController {

    EventMethods eventController;
    MasterController master;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    private String[] categories = {"Gaming", "Guest Lecture", "Hackathon","Lunch Lecture", "Mingle", "Sports","Student Union", "Workshop","Others"};

    private String time;

    @FXML
    private TextField courseCode;

    @FXML
    private Button createEvent;

    @FXML
    private DatePicker myDatePicker;

    LocalDate eventDate;

    @FXML
    private ChoiceBox<String> hourChoiceBox;

    private String[] hours = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};

    @FXML
    private ChoiceBox<String> minuteChoiceBox;

    private String[] minutes = {"00","05","10","15","20","25","30","35","40","45","50","55"};

    @FXML
    private TextField eventTitle;

    @FXML
    private TextField eventLocation;

    @FXML
    private Label warningLabel;
                       /*
    @FXML
    void onCreateEventClicked(ActionEvent event) {
        if (eventTitle.getText().isBlank()) {
            warningLabel.setText("Title cannot be empty.");
        } else if (eventLocation.getText().isBlank()) {
            warningLabel.setText("Location cannot be empty");
        } else if (eventDate.isBefore(LocalDate.now())) {
            warningLabel.setText("Event cannot be created for a past date.");
        } else if (hourChoiceBox.)

        eventController.addEvent(eventTitle.getText(), eventDate, time, eventLocation.getText(), categoryChoiceBox.getValue(), courseCode.getText());
    }
                  */
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
    void onCreateEventClicked(ActionEvent event) {

    }

    @FXML
    void setDate(ActionEvent event) {
        eventDate = myDatePicker.getValue();
    }

    public void load() {
        time = hourChoiceBox.getValue() + ":" + minuteChoiceBox.getValue();
        categoryChoiceBox.getItems().addAll(categories);
        hourChoiceBox.getItems().addAll(hours);
        minuteChoiceBox.getItems().addAll(minutes);
    }
}
