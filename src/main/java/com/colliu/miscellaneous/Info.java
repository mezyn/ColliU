package com.colliu.miscellaneous;

import com.colliu.Main;

import javax.swing.*;

public class Info {
  public static final String INCORRECT_PASSWORD = "Invalid password.";
  public static final String INCORRECT_EMAIL = "Email doesn't match any users.";
  public static final String INCORRECT_LOGIN = "Email or Password is incorrect.";

  public static final String EMPTY_NAME = "Name cannot be empty.";
  public static final String EMPTY_PASSWORD = "Password cannot be empty.";
  public static final String EMPTY_EMAIL = "Email address cannot be empty.";
  public static final String EMPTY_REACTIONS = "No reactions yet.";
  public static final String EMPTY_ATTENDEES = "No attendees yet.";

  public static final String ACCOUNT_STATUS_BANNED = "Account is permanently banned.";

  public static final String FAIL_SAVE_USERS = "Could not save users to: Students.JSON and/or Staff.JSON.";
  public static final String FAIL_SAVE_EVENTS = "Could not save events to: Event.JSON.";
  public static final String NO_USER_FILE = "Missing file(s): Student.JSON or Staff.JSON in documents/ColliU/ directory.";
  public static final String USER_FILE_CORRUPT = "User file(s): Student.JSON or Staff.JSON are corrupt and can not be loaded.";
  public static final String CANT_PROMOTE_STUDENT = "An error was caught when promoting com.colliu.colliu.user: ";
  public static final String CANT_DEMOTE_ADMIN = "An error was caught when demoting com.colliu.colliu.user: ";
  public static final String CANT_CHANGE_STATUS = "An error was caught when changing com.colliu.colliu.user's status: ";
  public static final String CANT_CREATE_STAFF = "An error was caught when creating new staff: ";
  public static final String CANT_CREATE_STUDENT = "An error was caught when creating new student: ";
  public static final String NO_EVENT_FILE = "Missing file: Event.JSON in documents/ColliU/ directory." + System.lineSeparator() + "A blank Event file is loaded. All previous events are lost.";
  public static final String EVENT_FILE_CORRUPT = "Event file: Event.JSON are corrupt and can not be loaded." + System.lineSeparator() + "A blank Event file is loaded. All previous events are lost.";
  public static final String CANT_REMOVE_USER = "An error was caught when trying to remove a com.colliu.colliu.user: ";
  public static final String ATTEND = "Attend";
  public static final String SAVE_SUCCESS = "Saved successfully!";
  public static final String ENTER_PASSWORD = "Enter your current password.";
  public static final String CANCEL = "Cancel";
  public static final String CLOSE = "Close";
  public static final String DELETE = "Delete";
  public static final String UNDO = "Undo";
  public static final String PROMOTE = "Promote";
  public static final String DEMOTE = "Demote";
  public static final String SEM = "Software engineering and management";
  public static final String KOG = "Cognitive science";
  public static final String SVET = "Systems science";
  public static final String DVET = "Computer science";

  public static final int UPCOMING_EVENTS = 1;
  public static final int PAST_EVENTS = 2;
  public static final int ATTENDING_EVENTS = 3;

  public static final String REACTION_SMILE = "1";
  public static final String REACTION_HAPPY = "2";
  public static final String REACTION_SHOCK = "3";
  public static final String REACTION_LOVE = "4";
  public static final String REACTION_BLANK = "";


  public static final String[] CATEGORIES = { "Gaming", "Guest Lecture", "Hackathon",
      "Lunch Lecture", "Mingle", "Sports", "Student Union", "Workshop", "Others" };
  public static final String STAFF_FILTER = "ALL";

  /*
      Resources such as pages / images / css files
   */

  public static final String RESOURCE_ERROR_PAGE = "ErrorPage.fxml";
  public static final String RESOURCE_ACCOUNT_SETTINGS = "newAccountSettings.fxml";
  public static final String RESOURCE_EVENT_CREATION = "EventCreationPage.fxml";
  public static final String RESOURCE_HOMEPAGE = "EventPage.fxml";
  public static final String RESOURCE_LOGIN = "LoginPage.fxml";
  public static final String RESOURCE_REGISTER_STAFF = "staff-registration.fxml";
  public static final String RESOURCE_REGISTER_STUDENT = "student-registration.fxml";
  public static final String RESOURCE_IMAGE_SMILE = "images/reaction_one.png";
  public static final String RESOURCE_IMAGE_HAPPY = "images/reaction_two.png";
  public static final String RESOURCE_IMAGE_SHOCK = "images/reaction_three.png";
  public static final String RESOURCE_IMAGE_LOVE = "images/reaction_four.png";
  public static final String DOCUMENT_PATH = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/ColliU/";
  public static final String RESOURCE_FILE_STUDENT = DOCUMENT_PATH + "Student.JSON";
  public static final String RESOURCE_FILE_ADMIN = DOCUMENT_PATH + "Admin.JSON";
  public static final String RESOURCE_FILE_STAFF = DOCUMENT_PATH + "Staff.JSON";
  public static final String RESOURCE_FILE_EVENT = DOCUMENT_PATH + "Event.JSON";
  public static final String RESOURCE_FILE_ADMIN_BACKUP = "" + Main.class.getResource("json/Admin.JSON");
  public static final String RESOURCE_FILE_STUDENT_BACKUP = "" + Main.class.getResource("json/Student.JSON");
  public static final String RESOURCE_FILE_STAFF_BACKUP = "" + Main.class.getResource("json/Staff.JSON");

}
