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
import miscellaneous.Info;
import user.*;

/**
 * * * * * * * * * * * *
 * DOCUMENTATION HERE. *
 * * * * * * * * * * * *
 **/

public class

MasterController {

  private final UserMethods userMethods;
  private final Data json;
  private final EventMethods eventMethods;
  private Stage latestStage;
  private Stage previousStage;
  public User user;



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
    closeWindow();

    FXMLLoader studentLoader = showWindow(Info.REGISTER_STUDENT);
    StudentController studentController = studentLoader.getController();
    studentController.setMaster(this);
  }

  public void showRegisterStaff() { //button that opens staff registration screen
    closeWindow();

    FXMLLoader staffLoader = showWindow(Info.REGISTER_STAFF);
    StaffController staffController = staffLoader.getController();
    staffController.setMaster(this);
  }

  public void showLogin() {
    closeWindow();
    FXMLLoader temp = showWindow(Info.LOGIN);
    LoginController loginController = temp.getController();
    MasterController newMaster = new MasterController();
    newMaster.setStage(latestStage);
    loginController.setMaster(newMaster);
  }

  public void showEventPage() {
    closeWindow();
    FXMLLoader temp = showWindow(Info.HOMEPAGE);
    EventController eventController = temp.getController();
    eventController.setMaster(this);
    eventController.load();
  }

  public void showEventCreationPage() {
    closeWindow();
    FXMLLoader eventCreateLoader = showWindow(Info.EVENT_CREATION);
    CreateEventController eventCreateController = eventCreateLoader.getController();
    eventCreateController.setMaster(this);
    eventCreateController.load();
  }

  public void showProfileSettingsPage() {
    closeWindow();
    FXMLLoader profileLoader = showWindow(Info.ACCOUNT_SETTINGS);
    ProfileController profileController = profileLoader.getController();
    profileController.setMaster(this);
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
    FXMLLoader errorLoader = showWindow(Info.ERROR_PAGE);
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

  public void toggleAdminStatus(String email) {
    try {
      userMethods.toggleAdminStatus(email);
    } catch (Exception e) {
      StringWriter error = new StringWriter();
      e.printStackTrace(new PrintWriter(error));
      showError(Info.CANT_CHANGE_STATUS + System.lineSeparator() + error);
    }
  }

  public void makeAdmin(String email) {
    try {
      userMethods.promoteStudent(email);
    } catch (Exception e) {
      StringWriter error = new StringWriter();
      e.printStackTrace(new PrintWriter(error));
      showError(Info.CANT_PROMOTE_STUDENT + System.lineSeparator() + error);
    }
  }

  public void demoteAdmin(String email) {
    try {
      userMethods.demoteAdmin(email);
    } catch (Exception e) {
      StringWriter error = new StringWriter();
      e.printStackTrace(new PrintWriter(error));
      showError(Info.CANT_DEMOTE_ADMIN + System.lineSeparator() + error);
    }
  }

  public void removeUser(String email) {
    try {
      userMethods.removeUser(email);
    } catch (Exception e) {
      StringWriter error = new StringWriter();
      e.printStackTrace(new PrintWriter(error));
      showError(Info.CANT_REMOVE_USER + System.lineSeparator() + error);
    }
  }

  public void createStudent(String email, String password, String name, String surname, int graduationYear, String program) {
    try {
      userMethods.createStudent(email, password, name, surname, graduationYear, program);
    } catch (Exception e) {
      StringWriter error = new StringWriter();
      e.printStackTrace(new PrintWriter(error));
      showError(Info.CANT_CREATE_STUDENT + System.lineSeparator() + error);
    }
  }

  public void createStaff(String email, String password, String name, String surname) {
    try {
      userMethods.createStaff(email, password, name, surname);
    } catch (Exception e) {
      StringWriter error = new StringWriter();
      e.printStackTrace(new PrintWriter(error));
      showError(Info.CANT_CREATE_STAFF + System.lineSeparator() + error);
    }
  }

  public void addUser(User user) {
    userMethods.addUser(user);
  }

  public ArrayList<User> loadUsers() {
    try {
      return json.loadUser();
    } catch (FileNotFoundException e) {
      showError(Info.NO_USER_FILE);
    } catch (UnsupportedEncodingException e) {
      showError(Info.USER_FILE_CORRUPT);
    } catch (IOException e) {
      showError(Info.NO_USER_FILE + e);
    }
    try {
      ArrayList<User> standardUsers = new ArrayList<>();
      standardUsers.add(new Administrator("gusandan@student.gu.se", "a11Black$", "Anna", "Andersson", 2024, Info.SEM));
      standardUsers.add(new Staff("benjamin.bengtsson@gu.se", "!Lov3MyPiano", "Benjamin", "Bengtsson"));
      standardUsers.add(new Staff("christian.carlsson@cse.gu.se", "jellY22fi$h", "Christian", "Carlsson"));
      standardUsers.add(new Staff("info@gota.gu.se", "P^45k9jw", "Göta", "Student Union"));
      standardUsers.add(new Student("gusdavda@student.gu.se", "!ush3R", "Daniel", "Davidsson", 2022, Info.DVET));
      standardUsers.add(new Student("guseriem@student.gu.se", "&ebAy.44", "Emil", "Eriksson", 2023, Info.SVET));
      standardUsers.add(new Student("gusfrefe@student.gu.se", "H!Mnpintd2r!", "Felix", "Fredriksson", 2024, Info.KOG));
      standardUsers.add(new Student("gushenha@student.gu.se", "5wtyIbm!h", "Hans", "Henriksson", 2026, Info.SEM));


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
      showError(Info.FAIL_SAVE_USERS);
    }
  }

  public ArrayList<User> getAllUsers() {
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

  public void setProgram(int index, String program) {
    ((Student) userMethods.getAllUsers().get(index)).setProgram(program);
  }

  public void setGraduation(int index, int year) {
    ((Student) userMethods.getAllUsers().get(index)).setGraduationYear(year);
  }

  /*
      EVENT HANDLING
   */

  public ArrayList<Event> getAllEvents() {
    return eventMethods.getAllEvents();
  }

  public Event[] filterEvents(String[] tags) {
    String program = (getCurrentUser() instanceof Student ? ((Student) getCurrentUser()).getProgram() : Info.STAFF_FILTER);
    return (tags.length > 0 ? eventMethods.filterEvents(program, tags) : eventMethods.getEvents(program, Info.UPCOMING_EVENTS));
  }

  public Event[] getUpcomingEvents() {
    return getEvents(Info.UPCOMING_EVENTS);
  }

  public Event[] getPastEvents() {
    return getEvents(Info.PAST_EVENTS);
  }

  public Event[] getAttendingEvents() {
    return getEvents(Info.ATTENDING_EVENTS);
  }

  private Event[] getEvents(int type) {
    if (getCurrentUser().getType() < Info.TYPE_STAFF ) {
      String program = ((Student) getCurrentUser()).getProgram();
      return eventMethods.getEvents(program, type);
    } else {
      return eventMethods.getHostingEvents(getCurrentUser(), type);
    }
  }

  public void saveEvents() {
    ArrayList<Event> events = eventMethods.getAllEvents();
    if (!json.saveEvents(events)) {
      showError(Info.FAIL_SAVE_EVENTS);
    }
  }

  public ArrayList<Event> loadEvents() {
    try {
      return json.loadEvent();
    } catch (FileNotFoundException e) {
      showError(Info.NO_EVENT_FILE);
    } catch (UnsupportedEncodingException e) {
      showError(Info.EVENT_FILE_CORRUPT);
    } catch (IOException e) {
      showError(Info.NO_EVENT_FILE + e);
    }
    ArrayList<Event> standardEvents = new ArrayList<>();
    standardEvents.add(new Event(0, "Gaming night", LocalDate.of(2022, 1, 17), "19:30", "Discord", Info.SEM, "Welcome to a great gaming event with all my favorite games!", "Gaming", "christian.carlsson@cse.gu.se"));
    standardEvents.add(new Event(1, "'The Complications of gaming' with Isak Ingvarsson", LocalDate.of(2022, 2, 24), "13:15", "Barbord", Info.KOG, "The beloved writer Isak Ingvarsson would like to share the groundbreaking findings from his research!", "Guest Lecture", "christian.carlsson@cse.gu.se"));
    standardEvents.add(new Event(2, "Weekend Hackathon", LocalDate.of(2022, 2, 12), "09:00", "Discord", Info.SEM, "The department of Computer science and Engineering creates an opportunity for the first year students to a hackathon with the theme of sustainable software development.", "Hackathon", "christian.carlsson@cse.gu.se"));
    standardEvents.add(new Event(3, "Lunch Lecture with Jonathan Johansson", LocalDate.of(2022, 3, 16), "12:15", "Styrbord", Info.SVET, "Learn the new trend in the job market with free lunch!", "Lunch Lecture", "christian.carlsson@cse.gu.se"));
    standardEvents.add(new Event(4, "Social ", LocalDate.of(2022, 2, 23), "19:30", "Discord", Info.SEM, "", "Mingle", "benjamin.bengtsson@gu.se"));
    standardEvents.add(new Event(5, "Table Tennis", LocalDate.of(2022, 3, 06), "17:30", "Milla", Info.SEM, "The Grand Final of weekly table tennis competition.", "Sports", "benjamin.bengtsson@gu.se"));
    standardEvents.add(new Event(6, "Tentapub", LocalDate.of(2022, 3, 25), "18:00", "Patricia", Info.SEM, "Join the tentapub after the exam! Identification required.", "Student Union", "info@gota.gu.se"));
    standardEvents.add(new Event(7, "City walk", LocalDate.of(2022, 1, 16), "13:00", "Lindholmen Campus", Info.DVET, "[For exchange students] Let's have a walk through the city of Gothenburg and get to know the city.", "Student Union", "info@gota.gu.se"));
    standardEvents.add(new Event(8, "GitLab Workshop", LocalDate.of(2022, 1, 18), "17:00", "Discord", Info.SEM, "Great opportunity to learn more about how GitLab works.", "Workshop", "benjamin.bengtsson@gu.se"));
    standardEvents.add(new Event(9, "Weekend Flee Market", LocalDate.of(2022, 2, 12), "10:00", "Chalmers Johanneberg", Info.SEM, "Get the stuff you don't need anymore or come and find good stuff at a cheap price!", "Others", "benjamin.bengtsson@gu.se"));

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