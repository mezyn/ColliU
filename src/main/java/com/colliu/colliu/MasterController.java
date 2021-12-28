package com.colliu.colliu;

import com.colliu.colliu.controllers.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import event.Event;
import event.EventMethods;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import miscellaneous.Data;
import user.*;

/**
 * * * * * * * * * * * *
 * DOCUMENTATION HERE. *
 * * * * * * * * * * * *
 **/

public class MasterController {

  private final UserMethods userMethods;
  private final Data json;
  private final EventMethods eventMethods;
  private Stage latestStage;
  private Stage previousStage;
  public User user;

  private final String FAIL_SAVE_USERS = "Could not save users to: Students.JSON and/or Staff.JSON.";
  private final String FAIL_SAVE_EVENTS = "Could not save events to: Event.JSON.";
  private final String NO_USER_FILE = "Missing file(s): Student.JSON or Staff.JSON in documents/ColliU/ directory.";
  private final String USER_FILE_CORRUPT = "User file(s): Student.JSON or Staff.JSON are corrupt and can not be loaded.";
  private final String CANT_PROMOTE_STUDENT = "An error was caught when promoting student: ";
  private final String CANT_CREATE_STAFF = "An error was caught when creating new staff: ";
  private final String CANT_CREATE_STUDENT = "An error was caught when creating new student: ";
  private final String NO_EVENT_FILE = "Missing file: Event.JSON in documents/ColliU/ directory." + System.lineSeparator() + "A blank Event file is loaded. All previous events are lost.";
  private final String EVENT_FILE_CORRUPT = "Event file: Event.JSON are corrupt and can not be loaded." + System.lineSeparator() + "A blank Event file is loaded. All previous events are lost.";
  private final int UPCOMING_EVENTS = 1;
  private final int PAST_EVENTS = 2;
  private final int ATTENDING_EVENTS = 3;

  public MasterController() {
    json = new Data();
    userMethods = new UserMethods(this);
    eventMethods = new EventMethods(this);
  }

  private FXMLLoader showWindow(String fileName) {
    fileName = "fxml/" + (fileName.endsWith(".fxml") ? fileName : fileName + ".fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
    Scene scene;
    Stage stage;

    try {
      scene = new Scene(fxmlLoader.load());
      stage = new Stage();
      stage.setScene(scene);
      stage.setTitle(fileName.substring(0, fileName.length() - 4));
      stage.initStyle(StageStyle.UNDECORATED);

      this.latestStage = stage;
      stage.show();

    } catch (IOException e) {
      showError("Could not load: " + System.lineSeparator() + fileName);
    }
    return fxmlLoader;
  }

  public void showRegisterStudent() {
    String registerStudentPage = "student-registration.fxml";
    closeWindow();

    FXMLLoader studentLoader = showWindow(registerStudentPage);
    StudentController studentController = studentLoader.getController();
    studentController.setMaster(this);
  }

  public void showRegisterStaff() { //button that opens staff registration screen
    String registerStaffPage = "staff-registration.fxml";
    closeWindow();

    FXMLLoader staffLoader = showWindow(registerStaffPage);
    StaffController staffController = staffLoader.getController();
    staffController.setMaster(this);
  }

  public void showLogin() {
    String loginPage = "LoginPage.fxml";
    closeWindow();

    FXMLLoader temp = showWindow(loginPage);
    LoginController loginController = temp.getController();
    MasterController newMaster = new MasterController();
    newMaster.setStage(latestStage);
    loginController.setMaster(newMaster);
  }

  public void showEventPage() {
    String eventPage = "EventPage.fxml";
    closeWindow();
    FXMLLoader temp = showWindow(eventPage);
    EventController eventController = temp.getController();
    eventController.setMaster(this);
    eventController.load();
  }

  public void showEventCreationPage() {
    String eventCreationPage = "EventCreationPage.fxml";
    closeWindow();
    FXMLLoader eventCreateLoader = showWindow(eventCreationPage);
    CreateEventController eventCreateController = eventCreateLoader.getController();
    //eventCreateController.setMaster(this);
  }

  public void showForgottenPassword() {
    String forgottenPasswordPage = "forgot-password.fxml";
    ForgotPassword controller = showWindow(forgottenPasswordPage).getController();
    controller.setMaster(this);
  }

  public void showProfileSettingsPage() {
    String profileSettingsPage = "ProfileSettingsPage.fxml";
    closeWindow();
    FXMLLoader profileLoader = showWindow(profileSettingsPage);
    ProfileController profileController = profileLoader.getController();
    profileController.setMaster(this);
    profileController.updateProfileTab();
  }

  private void closeWindow() {
    if (latestStage != null) {
      latestStage.close();
    } else {
      System.out.println("Last stage is null ");
    }
  }

  public void showLastWindow() {
    Stage temp = latestStage;
    latestStage = previousStage;
    previousStage = temp;
    previousStage.close();
    latestStage.show();
  }

  private void showError(String errorMessage) {
    String errorPage = "ErrorPage.fxml";
    FXMLLoader errorLoader = showWindow(errorPage);
    ErrorController errorController = errorLoader.getController();
    errorController.setError(errorMessage);
  }

  public void setStage(Stage stage) {
    this.latestStage = stage;
  }

  /*
  *********************
      USER HANDLING
  *********************
   */

  public void setLoggedInUser(User user) {
    userMethods.setLoggedInUser(user);
  }

  public User getCurrentUser() {
    return userMethods.getLoggedInUser();
  }

  public User findUser(String email) {
    return userMethods.getUserByEmail(email);
  }

  public boolean validateLogin(String uEmail, String uPassword) {
    return userMethods.checkExistingEmail(uEmail) && userMethods.validatePassword(uPassword, uEmail);
  }

  public boolean checkPassword(String password) {
    return userMethods.checkPasswordComplexity(password);
  }

  public void banUser(String email) {
    userMethods.banUser(email);
  }

  public void unbanUser(String email) {
    userMethods.unbanUser(email);
  }

  public void makeAdmin(String email) {
    try {
      userMethods.promoteStudentToAdmin(email);
    } catch (Exception e) {
      StringWriter error = new StringWriter();
      e.printStackTrace(new PrintWriter(error));
      showError(CANT_PROMOTE_STUDENT + System.lineSeparator() + error);
    }
  }

  public void createStudent(String email, String password, String name, String surname, int graduationYear, String program) {
    try {
      userMethods.createStudent(email, password, name, surname, graduationYear, program);
    } catch (Exception e) {
      StringWriter error = new StringWriter();
      e.printStackTrace(new PrintWriter(error));
      showError(CANT_CREATE_STUDENT + System.lineSeparator() + error);
    }
  }

  public void createStaff(String email, String password, String name, String surname) {
    try {
      userMethods.createStaff(email, password, name, surname, department, staffTitle);
    } catch (Exception e) {
      StringWriter error = new StringWriter();
      e.printStackTrace(new PrintWriter(error));
      showError(CANT_CREATE_STAFF + System.lineSeparator() + error);
    }
  }

  public ArrayList<User> loadUsers() {
    try {
      return json.loadUser();
    } catch (FileNotFoundException e) {
      showError(NO_USER_FILE);
    } catch (UnsupportedEncodingException e) {
      showError(USER_FILE_CORRUPT);
    } catch (IOException e) {
      showError(NO_USER_FILE + e);
    }
    try {
      ArrayList<User> standardUsers = new ArrayList<>();
      standardUsers.add(new Administrator("admin@student.gu.se", "Hej123123!!", "Erik", "Harring", 2024, "SEM"));
      standardUsers.add(new Staff("staff@teacher.gu.se", "Hej123123!!", "William", "Hilmersson"));
      standardUsers.add(new Student("student@student.gu.se", "Hej123123!!", "Kristofer", "Koskunen", 2024, "SEM"));
      json.saveUsers(standardUsers);
      return loadUsers();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
      return null;
    }
  }

  public void saveUsers() {
    ArrayList<User> users = userMethods.getAllUsers();
    if (!json.saveUsers(users)) {
      showError(FAIL_SAVE_USERS);
    }
  }

  public ArrayList<User> getAllusers() {
    return userMethods.getAllUsers();
  }

  /*
      EVENT HANDLING
   */

  public ArrayList<Event> getAllEvents() {
    return eventMethods.getAllEvents();
  }

  public Event[] filterEvents(String[] tags) {
    String program = ((Student) getCurrentUser()).getProgram();
    return (tags.length > 0 ? eventMethods.filterEvents(program, tags) : eventMethods.getEvents(program, UPCOMING_EVENTS));
  }

  public Event[] getUpcomingEvents() {
    return getEvents(UPCOMING_EVENTS);
  }

  public Event[] getPastEvents() {
    return getEvents(PAST_EVENTS);
  }

  public Event[] getAttendingEvents() {
    return getEvents(ATTENDING_EVENTS);
  }

  private Event[] getEvents(int type) {
    if (getCurrentUser().getType() < 3 ) {
      String program = ((Student) getCurrentUser()).getProgram();
      return eventMethods.getEvents(program, type);
    } else {
      return eventMethods.getHostingEvents(getCurrentUser(), type);
    }
  }

  public void saveEvents() {
    ArrayList<Event> events = eventMethods.getAllEvents();
    if (!json.saveEvents(events)) {
      showError(FAIL_SAVE_EVENTS);
    }
  }

  public ArrayList<Event> loadEvents() {
    try {
      return json.loadEvent();
    } catch (FileNotFoundException e) {
      showError(NO_EVENT_FILE);
    } catch (UnsupportedEncodingException e) {
      showError(EVENT_FILE_CORRUPT);
    } catch (IOException e) {
      showError(NO_EVENT_FILE + e);
    }
    ArrayList<Event> standardEvents = new ArrayList<>();
    standardEvents.add(new Event(0, "Gaming nigt with Francisco", LocalDate.of(2021, 12, 31), "19:30", "Discord", "SEM", "Welcome to a great gaming event with all my favorite games!", "Gaming", "staff@teacher.gu.se"));
    standardEvents.add(new Event(1, "Barbecue with Christian", LocalDate.of(2022, 1, 14), "14:30", "Slottskogen", "KOG", "I sure do hope you are hungry!!", "Mingle", "Christinan.Berger@staff.gu.se"));
    standardEvents.add(new Event(2, "Guest lecture, no lunch allowed!!", LocalDate.of(2022, 3, 24), "12.00", "Svea HL123", "SEM", "VERY IMPORTANT LECTURE IN HOW TO START A COMPUTER. ATENDANCE IS MANDATORY!!!!", "Lunch lecture", "Tina.Turner@staff.gu.se"));
    standardEvents.add(new Event(3, "You were too late for this event, HAHA!!", LocalDate.of(2021, 12, 20), "18.00", "Chalmers Property", "SEM", "VERY IMPORTANT LECTURE IN HOW TO START A COMPUTER. ATENDANCE IS MANDATORY!!!!", "Student Union", "staff@teacher.gu.se"));
    standardEvents.add(new Event(4, "Little gingerbreadhouse creation day", LocalDate.of(2022, 1, 5), "16.30", "Gingerbread house", "SEM", "Today we will bake.", "Student Union", "staff@teacher.gu.se"));
    standardEvents.add(new Event(5, "Party without alcohol", LocalDate.of(2022, 1, 5), "18.00", "Bunkern", "SEM", "We call it a halfday in sweden because you are free the next day!", "Student Union", "staff@teacher.gu.se"));
    standardEvents.add(new Event(6, "Just another event", LocalDate.of(2022, 1, 20), "18.00", "Chalmers Property", "SEM", "Dont mind me I am just a filler :)", "Student Union", "staff@teacher.gu.se"));
    json.saveEvents(standardEvents);
    return loadEvents();
  }

  public void createEvent(String title, LocalDate date, String time, String location, String program, String description, String category, String host) {
    eventMethods.addEvent(title, date, time, location, program, description, category, host);
  }

  public Event[] getNotifications() {
    String uEmail = getCurrentUser().getEmail();
    String uProgram = ((Student)getCurrentUser()).getProgram();
    return eventMethods.getNotifications(uEmail, uProgram);
  }

  public ArrayList<User> getAllUsers() {
    return userMethods.getAllUsers();
  }

  public boolean checkExistingEmail(String email) {

    for (User user : getAllUsers()) {
      if (user.getEmail().equals(email)) {
        return true;
      }
    }
    return false;
  }
}