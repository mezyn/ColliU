package com.colliu.miscellaneous;

import com.colliu.event.Event;
import com.colliu.user.Administrator;
import com.colliu.user.Staff;
import com.colliu.user.Student;
import com.colliu.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.commons.io.*;

/**
 * This class is responsible for any input and output of object data.
 * It uses JSON to save data, via Google library GSON we can convert it back and forth.
 */
public class DataReadingWriting {
  public DataReadingWriting() {
    // Create a ColliU folder in Documents path if it does not exist.
    new File(Info.DOCUMENT_PATH).mkdirs();
  }

  /**
   * Loads object info for the User classes.
   * It will attempt to load three JSON Files Admin.JSON, Student.JSON and Staff.JSON-
   * from user's document/home directory inside a folder called ColliU.
   * If loading fails or there is no file the same file will be copied from the program's-
   * resource folder "json".
   - @return - An array of loaded user objects from either local disk or resources directory.
   - @throws IOException When copying json file from resources fails.
   */
  public ArrayList<User> loadUser() throws IOException {

    // Load all admins
    Type adminToken = TypeToken.getParameterized(ArrayList.class, Administrator.class).getType();
    ArrayList<Administrator> admins;
    try {
      admins = (ArrayList<Administrator>) loadJsonFile(Info.RESOURCE_FILE_ADMIN, adminToken);
    } catch (FileNotFoundException e) {
      try {
        FileUtils.copyFileToDirectory(new File(Paths.get(
            "colliu", "src", "main", "resources", "com", "colliu", "json")
            .toFile().getAbsolutePath() + "/Admin.json"), new File(Info.DOCUMENT_PATH));
      } catch (Exception d) {
        FileUtils.copyFileToDirectory(new File(
            Paths.get("src", "main", "resources", "com", "colliu", "json")
                .toFile().getAbsolutePath() + "/Admin.json"), new File(Info.DOCUMENT_PATH));
      }
      return loadUser();
    }

    // Load all students
    Type studentToken = TypeToken.getParameterized(ArrayList.class, Student.class).getType();
    ArrayList<Student> students;
    try {
      students = (ArrayList<Student>) loadJsonFile(Info.RESOURCE_FILE_STUDENT, studentToken);
    } catch(FileNotFoundException e) {
      try {
        FileUtils.copyFileToDirectory(new File(
            Paths.get("colliu", "src", "main", "resources", "com", "colliu", "json")
                .toFile().getAbsolutePath() + "/Student.json"), new File(Info.DOCUMENT_PATH));
      } catch (Exception d) {
        FileUtils.copyFileToDirectory(new File(
            Paths.get("src", "main", "resources", "com", "colliu", "json")
                .toFile().getAbsolutePath() + "/Student.json"), new File(Info.DOCUMENT_PATH));
      }
      return loadUser();
    }

    // Load all staff
    Type staffToken = TypeToken.getParameterized(ArrayList.class, Staff.class).getType();
    ArrayList<Staff> staff;
    try {
      staff = (ArrayList<Staff>) loadJsonFile(Info.RESOURCE_FILE_STAFF, staffToken);
    } catch (FileNotFoundException e) {
      try {
        FileUtils.copyFileToDirectory(new File(
            Paths.get("colliu", "src", "main", "resources", "com", "colliu", "json")
                .toFile().getAbsolutePath() + "/Staff.json"), new File(Info.DOCUMENT_PATH));
      } catch (Exception d) {
        FileUtils.copyFileToDirectory(new File(
            Paths.get("src", "main", "resources", "com", "colliu", "json")
                .toFile().getAbsolutePath() + "/Staff.json"), new File(Info.DOCUMENT_PATH));
      }
      return loadUser();
    }

    // Add all objects into a new ArrayList and return if successful.
    ArrayList<User> users = new ArrayList<>();
    users.addAll(staff);
    users.addAll(students);
    users.addAll(admins);

    return users;
  }

  /**
   * Loads object info for the Event class.
   * It will attempt to load a JSON Files Event.JSON from user's document/home directory inside-
     a folder called ColliU.
   * If loading fails or there is no file the same file will be copied from the program's-
   * resource folder "json".
   - @return - An array of loaded Event objects from either local disk or resources directory.
   - @throws IOException When copying json file from resources fails.
   */

  public ArrayList<Event> loadEvent() throws IOException {
    Type gsonToken = TypeToken.getParameterized(ArrayList.class, Event.class).getType();
    try {
      return (ArrayList<Event>) loadJsonFile(Info.RESOURCE_FILE_EVENT, gsonToken);
    } catch (FileNotFoundException e) {
      try {
        FileUtils.copyFileToDirectory(new File(Paths.get("colliu", "src", "main", "resources", "com", "colliu", "json").toFile().getAbsolutePath() + "/Event.json"), new File(Info.DOCUMENT_PATH));
      } catch(Exception d) {
        FileUtils.copyFileToDirectory(new File(Paths.get("src", "main", "resources", "com", "colliu", "json").toFile().getAbsolutePath() + "/Event.json"), new File(Info.DOCUMENT_PATH));
      }
      return loadEvent();
    }
  }

  /**
   * Moves all objects into their own array for separate saving.
   * Then calls the save method three times with different data in parameters for each file.
   - @param userData is expecting an arraylist of Users to be saved.
   - @return True if successful or false if saving wasn't successful.
   */

  public boolean saveUsers(ArrayList<?> userData) {
    ArrayList<Student> students = new ArrayList<>();
    ArrayList<Staff> staff = new ArrayList<>();
    ArrayList<Administrator> admins = new ArrayList<>();
    for (Object userDatum : userData) {
      if (userDatum instanceof Administrator) {
        admins.add((Administrator) userDatum);
      } else if (userDatum instanceof Staff) {
        staff.add((Staff) userDatum);
      } else {
        students.add((Student) userDatum);
      }
    }
    return save(students, Info.RESOURCE_FILE_STUDENT) && save(staff, Info.RESOURCE_FILE_STAFF) && save(admins, Info.RESOURCE_FILE_ADMIN);
  }

  /**
   * Saves all events data into a json file.
   - @param eventDataThe arraylist that contains object ot be saved.
   - @return True if save was sucessful or false if not.
   */

  public boolean saveEvents(ArrayList<Event> eventData) {
    return save(eventData, Info.RESOURCE_FILE_EVENT);
  }


  /**
   * Saves ArrayList objects into a JSON file.
   - @param data The arrayList which object's we want to save.
   - @param fileName the exact directory and filename where we want our json file saved.
   - @return True if saving was successful, or false if not.
   */
  private boolean save(ArrayList<?> data, String fileName) {
    try {
      FileWriter jsFile = new FileWriter(fileName);
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      gson.toJson(data, jsFile);
      jsFile.close();
    } catch (IOException fail) {
      fail.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * This method loads any JSON data into an arraylist.
   - @param fileName - The exact path and filename for the json file.
   - @param gsonToken - The token used to interpret the json data correctly.
   - @return - An Array of loaded objects.
   - @throws IOException - When there is no file, or opening/closing the IS/R fails.
   */

  private ArrayList<?> loadJsonFile(String fileName, Type gsonToken) throws IOException {
    InputStream is = new FileInputStream(fileName);
    Reader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
    Gson gson = new GsonBuilder().create();
    JsonStreamParser jsp = new JsonStreamParser(isr);
    ArrayList<Object> dataList = new ArrayList<>();
    while (jsp.hasNext()) {
      JsonElement jsonData = jsp.next();
      dataList = gson.fromJson(jsonData, gsonToken);
    }
    is.close();
    isr.close();
    return dataList;
  }

}
