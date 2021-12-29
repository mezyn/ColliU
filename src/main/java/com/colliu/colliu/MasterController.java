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
  private final String CANT_REMOVE_USER = "An error was caught when trying to remove a user: ";
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
      e.printStackTrace();
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

  public void showEventCreationPage() throws IOException {
    String eventCreationPage = "create-event.fxml";
    closeWindow();
    FXMLLoader eventCreateLoader = showWindow(eventCreationPage);
    CreateEventController eventCreateController = eventCreateLoader.getController();
    eventCreateController.setMaster(this);
    eventCreateController.load();
  }

  public void showForgottenPassword() {
    String forgottenPasswordPage = "forgot-password.fxml";
    ForgotPassword controller = showWindow(forgottenPasswordPage).getController();
    controller.setMaster(this);
  }

  public void showProfileSettingsPage() {
    String profileSettingsPage = "newAccountSettings.fxml";
    closeWindow();
    FXMLLoader profileLoader = showWindow(profileSettingsPage);
    ProfileController profileController = profileLoader.getController();
    profileController.setMaster(this);
    //profileController.updateProfileTab();
    profileController.load();
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
      userMethods.createStaff(email, password, name, surname);
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
      standardUsers.add(new Administrator("gusandan@student.gu.se", "a11Black$", "Anna", "Andersson", 2024, "Software engineering and management"));
      standardUsers.add(new Staff("benjamin.bengtsson@gu.se", "!Lov3MyPiano", "Benjamin", "Bengtsson"));
      standardUsers.add(new Staff("christian.carlsson@cse.gu.se", "jellY22fi$h", "Christian", "Carlsson"));
      standardUsers.add(new Staff("info@gota.gu.se", "P^45k9jw", "Göta", "Student Union"));
      standardUsers.add(new Student("gusdavda@student.gu.se", "!ush3R", "Daniel", "Davidsson", 2022, "Datavetenskap"));
      standardUsers.add(new Student("guseriem@student.gu.se", "&ebAy.44", "Emil", "Eriksson", 2023, "Systemvetenskap"));
      standardUsers.add(new Student("gusfrefe@student.gu.se", "H!Mnpintd2r!", "Felix", "Fredriksson", 2024, "Kognitionsvetenskap"));
      standardUsers.add(new Student("gushenha@student.gu.se", "5wtyIbm!h", "Hans", "Henriksson", 2026, "Software engineering and management"));


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

    These three methods takes an index, where in the arraylist we find our user we want to edit.
    It also takes a String with updated info, for what we want to change.
    The reason we take the index is in case we want to further develop the admin tools to allow manually changing user-information.

   */

  public void setName(int index, String name) {
    userMethods.getAllUsers().get(index).setFirstName(name);
  }

  public void setSurname(int index, String surname) {
    userMethods.getAllUsers().get(index).setLastName(surname);
  }

  public void setPassword(int index, String password) {
    userMethods.getAllUsers().get(index).setPassword(password);
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
    standardEvents.add(new Event(0, "Gaming night", LocalDate.of(2022, 1, 17), "19:30", "Discord", "Software engineering and management", "Welcome to a great gaming event with all my favorite games!", "Gaming", "christian.carlsson@cse.gu.se"));
    standardEvents.add(new Event(1, "'The Complications of gaming' with Isak Ingvarsson", LocalDate.of(2022, 2, 24), "13:15", "Barbord", "Kognitionsvetenskap", "The beloved writer Isak Ingvarsson would like to share the groundbreaking findings from his research!", "Guest Lecture", "christian.carlsson@cse.gu.se"));
    standardEvents.add(new Event(2, "Weekend Hackathon", LocalDate.of(2022, 2, 12), "09:00", "Discord", "Software engineering and management", "The department of Computer science and Engineering creates an opportunity for the first year students to a hackathon with the theme of sustainable software development.", "Hackathon", "christian.carlsson@cse.gu.se"));
    standardEvents.add(new Event(3, "Lunch Lecture with Jonathan Johansson", LocalDate.of(2022, 3, 16), "12:15", "Styrbord", "Systemvetenskap", "Learn the new trend in the job market with free lunch!", "Lunch Lecture", "christian.carlsson@cse.gu.se"));
    standardEvents.add(new Event(4, "Social ", LocalDate.of(2022, 2, 23), "19:30", "Discord", "Software engineering and management", "", "Mingle", "benjamin.bengtsson@gu.se"));
    standardEvents.add(new Event(5, "Table Tennis", LocalDate.of(2022, 3, 06), "17:30", "Milla", "Software engineering and management", "The Grand Final of weekly table tennis competition.", "Sports", "benjamin.bengtsson@gu.se"));
    standardEvents.add(new Event(6, "Tentapub", LocalDate.of(2022, 3, 25), "18:00", "Patricia", "Software engineering and management", "Join the tentapub after the exam! Identification required.", "Student Union", "info@gota.gu.se"));
    standardEvents.add(new Event(7, "City walk", LocalDate.of(2022, 1, 16), "13:00", "Lindholmen Campus", "Datavetenskap", "[For exchange students] Let's have a walk through the city of Gothenburg and get to know the city.", "Student Union", "info@gota.gu.se"));
    standardEvents.add(new Event(8, "GitLab Workshop", LocalDate.of(2022, 1, 18), "17:00", "Discord", "Software engineering and management", "Great opportunity to learn more about how GitLab works.", "Workshop", "benjamin.bengtsson@gu.se"));
    standardEvents.add(new Event(9, "Weekend Flee Market", LocalDate.of(2022, 2, 12), "10:00", "Chalmers Johanneberg", "Software engineering and management", "Get the stuff you don't need anymore or come and find good stuff at a cheap price!", "Others", "benjamin.bengtsson@gu.se"));

    json.saveEvents(standardEvents);
    return loadEvents();
  }

  public void createEvent(String title, LocalDate date, String time, String location, String program, String description, String category, String host) {
    eventMethods.addEvent(title, date, time, location, program, description, category, host);
  }

  public Event[] getNotifications() {
    String userEmail = getCurrentUser().getEmail();
    String userProgram = ((Student)getCurrentUser()).getProgram();
    return eventMethods.getNotifications(userEmail, userProgram);
  }


}