package com.colliu.colliu.controllers;

import event.EventMethods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateEventController implements Initializable {

    EventMethods eventController;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    private String[] categories = {"Gaming", "Guest Lecture", "Hackathon","Lunch Lecture", "Mingle", "Sports","Student Union", "Workshop","Others"};


    @FXML
    private TextField courseCode;

    @FXML
    private Button createEvent;

    @FXML
    private DatePicker myDatePicker;
    LocalDate eventDate = myDatePicker.getValue();

    @FXML
    private ChoiceBox<String> hourChoiceBox;
    private String[] hours = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};

    @FXML
    private ChoiceBox<String> minuteChoiceBox;
    private String[] minutes = {"00","05","10","15","20","25","30","35","40","45","50","55"};

    String time = hourChoiceBox.getValue() + ":" + minuteChoiceBox.getValue();

    @FXML
    private TextField eventTitle;

    @FXML
    private TextField eventLocation;

 /*   @FXML
    void onCreateEventClicked(ActionEvent event) {
        eventController.addEvent(eventTitle.getText(), eventDate, time, eventLocation.getText(), categoryChoiceBox.getValue(), courseCode.getText());
    }*/
 @FXML
 void onCreateEventClicked() {
 }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryChoiceBox.getItems().addAll(categories);
        hourChoiceBox.getItems().addAll(hours);
        minuteChoiceBox.getItems().addAll(minutes);

    }

}
