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

public class MasterController {

  private final UserMethods userMethods;
  private final Data json;
  private final EventMethods eventMethods;
  private Stage latestStage;
  public User user;

  /**
   * An object will be created only at the start of the program or when displaying the login-page.
   * In the constructor three new references are created, one for loading and saving to json.
   * One for handling the event-objects, and one for handing the user-objects.
  */
  public MasterController() {
    json = new Data();
    userMethods = new UserMethods(this);
    eventMethods = new EventMethods(this);
  }

  /**
   * Loads a FXML file and creates a new stage which the fxml data is loaded into.
   * If loading fails showError is called to display a new window containing error information.
   -@Param fileName is a String containing the filename of FXML file to be loaded.
   -@return fxmlloader is a reference to the created FXMLLoader used to access the controller.
   */
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

  /**
   * Display student's registration window and set masterController reference.
   */
  public void showRegisterStudent() {
    closeWindow();

    FXMLLoader studentLoader = showWindow(Info.RESOURCE_REGISTER_STUDENT);
    StudentController studentController = studentLoader.getController();
    studentController.setMaster(this);
  }

  /**
   * Display staff's registration window and set masterController reference.
   */
  public void showRegisterStaff() { //button that opens staff registration screen
    closeWindow();

    FXMLLoader staffLoader = showWindow(Info.RESOURCE_REGISTER_STAFF);
    StaffController staffController = staffLoader.getController();
    staffController.setMaster(this);
  }

  /**
   * Display the login window and set mastercontroller reference.
   * Method is called on program start, when logging out or when canceling registration.
   */
  public void showLogin() {
    closeWindow();
    FXMLLoader temp = showWindow(Info.RESOURCE_LOGIN);
    LoginController loginController = temp.getController();
    MasterController newMaster = new MasterController();
    newMaster.setStage(latestStage);
    loginController.setMaster(newMaster);
  }


  /**
   * Display the homepage and set masterController reference.
   * Also calls a method for loading necessary information.
   * This method is called when logging in or when closing profile settings.
   */

  public void showHomepage() {
    closeWindow();
    FXMLLoader temp = showWindow(Info.RESOURCE_HOMEPAGE);
    EventController eventController = temp.getController();
    eventController.setMaster(this);
    eventController.load();
  }


  /**
   * Display the creation page for new events. Only accessible by Staff objects.
   * Set the reference to masterController.
   * Method is called from the Homepage.
   */
  public void showEventCreationPage() {
    closeWindow();
    FXMLLoader eventCreateLoader = showWindow(Info.RESOURCE_EVENT_CREATION);
    CreateEventController eventCreateController = eventCreateLoader.getController();
    eventCreateController.setMaster(this);
    eventCreateController.load();
  }

  /**
   * Display the profileSettings and set master reference.
   * This page is accessible by all User objects.
   * Method is called from Homepage.
   */
  public void showProfileSettingsPage() {
    closeWindow();
    FXMLLoader profileLoader = showWindow(Info.RESOURCE_ACCOUNT_SETTINGS);
    ProfileController profileController = profileLoader.getController();
    profileController.setMaster(this);
    profileController.load();
  }

  /**
   * Close the currently open window/Stage.
   * A print will be done to the console if there is no latest window-
   * mainly for development purposes.
   * Called when displaying most new windows except for the error windows.
   */

  private void closeWindow() {
    if (latestStage != null) {
      latestStage.close();
    } else {
      System.out.println("Last stage is null ");
    }
  }

  /**
   * Displays an errorpage in-case the program is not functioning as expected.
   - @param errorMessage the message to be displayed for users inside the error window.
   */
  private void showError(String errorMessage) {
    FXMLLoader errorLoader = showWindow(Info.RESOURCE_ERROR_PAGE);
    ErrorController errorController = errorLoader.getController();
    errorController.setErrorText(errorMessage);
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

  public User getCurrentUser() { // Getter for getting the current "current logged-in user".
    return userMethods.getLoggedInUser();
  }

  public User findUser(String email) { // Takes a String email and returns the corresponding user
    return userMethods.getUserByEmail(email);
  }

  /**
   If user exist check if password is match, return the result(True && True == True, else False)
   - @param email The email to check password for
   - @param password The password to compare with object information.
   - @return True if password matches object's information, otherwise false.
   */

  public boolean validateLogin(String email, String password) {
    return userMethods.checkExistingEmail(email)
        && userMethods.validatePassword(password, email);
  }

  /**
   - @param password String to compare with password requirements.
   - @return True if it passes testing, false if it doesn't fulfill requirements.
   */

  public boolean checkPassword(String password) {
    return userMethods.checkPasswordComplexity(password);
  }

  /**
   - @param email The email of what user to ban.
   */

  public void banUser(String email) {
    userMethods.banUser(email);
  }

  /**
   - @param email The email of what user to unban.
   */

  public void unbanUser(String email) {
    userMethods.unbanUser(email);
  }

  /**
   - @param email The email of what user to Toggle admin status.
   */

  public void toggleAdminStatus(String email) {
    try {
      userMethods.toggleAdminStatus(email);
    } catch (Exception e) {
      StringWriter error = new StringWriter();
      e.printStackTrace(new PrintWriter(error));
      showError(Info.CANT_CHANGE_STATUS + System.lineSeparator() + error);
    }
  }

  /**
   - @param email The email if what object to delete.
   */

  public void removeUser(String email) {
    try {
      userMethods.removeUser(email);
    } catch (Exception e) {
      StringWriter error = new StringWriter();
      e.printStackTrace(new PrintWriter(error));
      showError(Info.CANT_REMOVE_USER + System.lineSeparator() + error);
    }
  }

  /**
   - @param email The email adress of what new object to be created.
   - @param pass The password used to access the new object.
   - @param name The firstname to identify the object.
   - @param surname The lastname to indeitfy the object.
   - @param gradYear The expected graduation year of the user.
   - @param prog The program to differentiate the information to display to this user.
   */

  public void createStudent(String email, String pass, String name, String surname, int gradYear,
                            String prog) { // New line is checkstyle requirements(100 chars/line).
    try {
      userMethods.createStudent(email, password, name, surname, graduationYear, program);
    } catch (Exception e) {
      StringWriter error = new StringWriter();
      e.printStackTrace(new PrintWriter(error));
      showError(Info.CANT_CREATE_STUDENT + System.lineSeparator() + error);
    }
  }

  /**
   - @param email The email adress of what new object to be created.
   - @param password The password used to access the new object.
   - @param name The firstname to identify the object.
   - @param surname The lastname to indeitfy the object.
   */

  public void createStaff(String email, String password, String name, String surname) {
    try {
      userMethods.createStaff(email, password, name, surname);
    } catch (Exception e) {
      StringWriter error = new StringWriter();
      e.printStackTrace(new PrintWriter(error));
      showError(Info.CANT_CREATE_STAFF + System.lineSeparator() + error);
    }
  }

  /**
   * Adds an object into an arraylist that stores object references.
   * This is called when undoing a deletion of an object.
   - @param user
   */

  public void addUser(User user) {
    userMethods.addUser(user);
  }

  /**
   * Loads User objects from the JSON file if possible.
   * If the load fails a new file will be created with custom hardcoded user objects.
   - @return Arraylist of user references, or null if no new users could be created.
   */

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

  /**
   * Saves all user objects in our arraylist to JSON.
   */

  public void saveUsers() {
    ArrayList<User> users = userMethods.getAllUsers();
    if (!json.saveUsers(users)) {
      showError(Info.FAIL_SAVE_USERS);
    }
  }

  /**
   - @return A copy of our arraylist of users.
   */

  public ArrayList<User> getAllUsers() {
    return userMethods.getAllUsers();
  }

  /**
   - @param index Where in the arraylist we want to modify our object.
   - @param name The new name identity we want to set for our object.
   */

  public void setName(int index, String name) {
    userMethods.getAllUsers().get(index).setFirstName(name);
  }

  /**
   - @param index Where in the arraylist we want to modify our object.
   - @param surname The new lastname identity we want to set for our object.
   */

  public void setSurname(int index, String surname) {
    userMethods.getAllUsers().get(index).setLastName(surname);
  }

  /**
   - @param index Where in the arraylist we want to modify our object.
   - @param password The new password security the user want to access their object.
   */

  public void setPassword(int index, String password) {
    userMethods.getAllUsers().get(index).setPassword(password);
  }

  /**
   - @param index Where in the arraylist we want to modify our object.
   - @param program The new program to group our objects.
   */

  public void setProgram(int index, String program) {
    ((Student) userMethods.getAllUsers().get(index)).setProgram(program);
  }

  /**
   - @param index Where in the arraylist we want to modify our object.
   - @param year the updated expeceted graduation year set by user.
   */

  public void setGraduation(int index, int year) {
    ((Student) userMethods.getAllUsers().get(index)).setGraduationYear(year);
  }

  /*
  *********************
      USER HANDLING
  *********************
   */

  /**
   - @return A copy of our arraylist of event objects.
   */

  public ArrayList<Event> getAllEvents() {
    return eventMethods.getAllEvents();
  }

  /**
   - @param tags What we want to filter our event arraylist by.
   - @return Return an array of filtered events
   */

  public Event[] filterEvents(String[] tags) {
    // If object is a student/admin filter by program, else by Hosting.
    String filterBy = (getCurrentUser() instanceof Student
        ? ((Student) getCurrentUser()).getProgram() : Info.STAFF_FILTER);

    return (tags.length > 0 ? eventMethods.filterEvents(filterBy, tags)
        : eventMethods.getEvents(filterBy, Info.UPCOMING_EVENTS));
  }

  /**
   - @return Return an array of only upcoming events.
   */

  public Event[] getUpcomingEvents() {
    return getEvents(Info.UPCOMING_EVENTS);
  }

  /**
   - @return Return an array of only previous events.
   */

  public Event[] getPastEvents() {
    return getEvents(Info.PAST_EVENTS);
  }

  /**
   - @return Return an array of only events the user is attending.
   */

  public Event[] getAttendingEvents() {
    return getEvents(Info.ATTENDING_EVENTS);
  }

  /**
   - @param type is what type of object is trying to access events,
   -             1 = student, 2 = administrator(student) 3 = staff.
   - @return An array of filtered events based on object type.
   */

  private Event[] getEvents(int type) {
    if (getCurrentUser().getType() < Info.TYPE_STAFF) {
      String program = ((Student) getCurrentUser()).getProgram();
      return eventMethods.getEvents(program, type);
    } else {
      return eventMethods.getHostingEvents(getCurrentUser(), type);
    }
  }

  /**
   * Saves all events stored in our arraylist to a JSON document on the user's pc.
   */

  public void saveEvents() {
    ArrayList<Event> events = eventMethods.getAllEvents();
    if (!json.saveEvents(events)) {
      showError(Info.FAIL_SAVE_EVENTS);
    }
  }

  /**
   * Attempts loading events from a json document on the user's pc.
   * If loading fails will create 10 new hardcoded events and save to a new json file.
   - @return An arraylist of either loaded events or newly created events.
   */

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
    // event 1 Gaming - 2022-01-17 19:30 - On discord, for Software Engineering students.
    standardEvents.add(new Event(0, "Gaming night", LocalDate.of(2022, 1, 17),
        "19:30", "Discord", Info.SEM, "Welcome to a great gaming event with all my favorite games!",
        "Gaming", "christian.carlsson@cse.gu.se"));
    // event 2 Guest Lecture - 2022-02-24 13:15 - In Barbord, for Cognition-science students.
    standardEvents.add(new Event(1, "'The Complications of gaming' with Isak Ingvarsson",
        LocalDate.of(2022, 2, 24), "13:15", "Barbord", Info.KOG,
        "The beloved writer Isak Ingvarsson would like to share the groundbreaking"
            + " findings from his research!", "Guest Lecture", "christian.carlsson@cse.gu.se"));
    // event 3 Hackaton - 2022-02-12 09:00 - On Discord, For Software Engineering Students.
    standardEvents.add(new Event(2, "Weekend Hackathon", LocalDate.of(2022, 2, 12),
        "09:00", "Discord", Info.SEM, "The department of Computer science and Engineering"
        + "creates an opportunity for the first year students to a hackathon with the theme of"
        + " sustainable software development.", "Hackathon", "christian.carlsson@cse.gu.se"));
    // event 4 Lunch Lecture - 2022-03-16 12:15 - In Styrbord, for System-science students.
    standardEvents.add(new Event(3, "Lunch Lecture with Jonathan Johansson", LocalDate.of(2022, 3,
        16), "12:15", "Styrbord", Info.SVET, "Learn the new trend in the job market with"
        + " free lunch!", "Lunch Lecture", "christian.carlsson@cse.gu.se"));
    // event 5 Mingle - 2022-02-23 19:30 - On Discord, for Software Engineering students.
    standardEvents.add(new Event(4, "Social ", LocalDate.of(2022, 2, 23),
        "19:30", "Discord", Info.SEM, "", "Mingle", "benjamin.bengtsson@gu.se"));
    // event 6 Sports - 2022-03-06 - In Milla, for Software Engineering students.
    standardEvents.add(new Event(5, "Table Tennis", LocalDate.of(2022, 3, 6),
        "17:30", "Milla", Info.SEM, "The Grand Final of weekly table tennis competition.",
        "Sports", "benjamin.bengtsson@gu.se"));
    // event 7 Student Union - 2022-03-25 18:00 - I Patricia, for Software Engineering Students.
    standardEvents.add(new Event(6, "Tentapub", LocalDate.of(2022, 3, 25),
        "18:00", "Patricia", Info.SEM, "Join the tentapub after"
        + " the exam! Identification required.", "Student Union", "info@gota.gu.se"));
    // event 8 Student Union - 2022-01-16 13:00 - On Lindholmen, for Computer-science students.
    standardEvents.add(new Event(7, "City walk", LocalDate.of(2022, 1, 16),
        "13:00", "Lindholmen Campus", Info.DVET, "[For exchange students]"
        + " Let's have a walk through the city of Gothenburg and get to know the city.",
        "Student Union", "info@gota.gu.se"));
    // event 9 Workshop - 2022-01-18 17:00 - On Discord, for Software Engineering students.
    standardEvents.add(new Event(8, "GitLab Workshop", LocalDate.of(2022, 1, 18),
        "17:00", "Discord", Info.SEM, "Great opportunity"
        + " to learn more about how GitLab works.", "Workshop", "benjamin.bengtsson@gu.se"));
    // event 10 Others - 2022-02-12 10:00 - On Johanneberg, for Software Engineering students.
    standardEvents.add(new Event(9, "Weekend Flee Market", LocalDate.of(2022, 2, 12),
        "10:00", "Chalmers Johanneberg", Info.SEM, "Get the stuff you don't need anymore or"
        + " come and find good stuff at a cheap price!", "Others", "benjamin.bengtsson@gu.se"));

    json.saveEvents(standardEvents);
    return loadEvents();
  }

  /**
   - @param title A way to quickly describe the event.
   - @param date The date of when the event occurs.
   - @param time The time of when the event occurs on that day.
   - @param location The location where event is hosted.
   - @param program Which group of users will be able to see the event.
   - @param description A more detailed explanation/description for the event.
   - @param category A way to let users filter the event by type.
   - @param host The email of the user who created the event.
   */

  public void createEvent(String title, LocalDate date, String time, String location,
                          String program, String description, String category, String host) {
    eventMethods.addEvent(title, date, time, location, program, description, category, host);
  }

  /**
   * Filter all events that the user has not already seen or that has not passed already.
   - @return an array of filtered event objects.
   */

  public Event[] getNotifications() {
    String userEmail = getCurrentUser().getEmail();
    String userProgram = ((Student)getCurrentUser()).getProgram();
    return eventMethods.getNotifications(userEmail, userProgram);
  }
}