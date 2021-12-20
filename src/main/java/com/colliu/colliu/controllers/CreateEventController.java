package com.colliu.colliu.controllers;

import event.EventController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateEventController implements Initializable {

    EventController eventController;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    private String[] categories = {"Gaming", "Guest Lecture", "Hackathon","Lunch Lecture", "Mingle", "Sports","Student Union", "Workshop","Others"};


    @FXML
    private TextField courseCode;

    @FXML
    private Button createEvent;

    @FXML
    private TextField date;

    String eventDate = date.getText();
    int eventYear = Integer.parseInt(eventDate.substring(0,3));
    int eventMonth = Integer.parseInt(eventDate.substring(5,6));
    int eventDay = Integer.parseInt(eventDate.substring(8,9));

    @FXML
    private TextField time;
    String eventTime = date.getText();
    int eventHour = Integer.parseInt(eventTime.substring(0,1));
    int eventMinute = Integer.parseInt(eventTime.substring(3,4));

    private Date dateAndTime = new Date(eventYear, eventMonth, eventDay, eventHour, eventMinute);

    @FXML
    private TextField eventTitle;

    @FXML
    private TextField eventLocation;

    @FXML
    void onCreateEventClicked(ActionEvent event) {
        eventController.addEvent(eventTitle.getText(), dateAndTime, eventLocation.getText(), categoryChoiceBox.getValue(), courseCode.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryChoiceBox.getItems().addAll(categories);
    }
}
