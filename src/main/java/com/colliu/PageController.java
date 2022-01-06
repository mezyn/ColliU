package com.colliu;

import com.colliu.controllers.*;
import com.colliu.event.Event;
import com.colliu.event.EventMethods;
import com.colliu.miscellaneous.DataReadingWriting;
import com.colliu.miscellaneous.Info;
import com.colliu.user.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.ArrayList;

/**
 * * * * * * * * * * * *
 * DOCUMENTATION HERE. *
 * * * * * * * * * * * *
 **/

public class PageController {

  private final UserMethods userMethods;
  private final DataReadingWriting json;
  private final EventMethods eventMethods;
  private Stage latestStage;

  /**
   * An object will be created only at the start of the program or when displaying the login-page.
   * In the constructor three new references are created, one for loading and saving to json.
   * One for handling the event-objects, and one for handing the user-objects.
  */
  public PageController() {
    json = new DataReadingWriting();
    userMethods = new UserMethods(this);
    eventMethods = new EventMethods(this);
  }

  /**
   * Returns a reference to different objects to allow functionality between classes.
   - @return reference the existing object to either EventMethods, UserMethods or logged-in User
   */

  public EventMethods getEventReference() {
    return eventMethods;
  }

  public UserMethods getUserReference() {
    return userMethods;
  }

  public User getCurrentUser() {
    return userMethods.getLoggedInUser();
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
    PageController newMaster = new PageController();
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
    HomepageController homepageController = temp.getController();
    homepageController.setMaster(this);
    homepageController.load();
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
  public void showError(String errorMessage) {
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

  /**
   * Loads User objects from the JSON file if possible.
   * If the load fails a new file will be created with preloaded user objects.
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
    return null;
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

  /*
  *********************
      EVENT HANDLING
  *********************
   */

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
    return loadEvents();
  }
}