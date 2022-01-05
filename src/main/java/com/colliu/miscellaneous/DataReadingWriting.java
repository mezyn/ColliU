package com.colliu.miscellaneous;

import com.colliu.PageController;
import com.colliu.user.Administrator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import com.google.gson.reflect.TypeToken;
import com.colliu.event.Event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.colliu.user.Staff;
import com.colliu.user.Student;
import com.colliu.user.User;

public class DataReadingWriting {
  public DataReadingWriting() {
    new File(Info.DOCUMENT_PATH).mkdirs(); // Create ColliU folder in Documents path if does not exist.
  }

  public ArrayList<User> loadUser() throws IOException {
    ArrayList<User> users = new ArrayList<>();
    String fail = String.valueOf(this.getClass().getClassLoader().getResource("/json/Admin.JSON"));
    Type adminToken = TypeToken.getParameterized(ArrayList.class, Administrator.class).getType();
    ArrayList<Administrator> admins;
    try {
      admins = (ArrayList<Administrator>) loadJsonFile(Info.RESOURCE_FILE_ADMIN, adminToken);
    } catch(IOException e) {
      admins = (ArrayList<Administrator>) loadJsonFile(fail, adminToken);
      System.out.println("2" + admins);
    }

    Type studentToken = TypeToken.getParameterized(ArrayList.class, Student.class).getType();
    ArrayList<Student> students;
    try {
      students = (ArrayList<Student>) loadJsonFile(Info.RESOURCE_FILE_STUDENT, studentToken);
    } catch(IOException e) {
      students = (ArrayList<Student>) loadJsonFile(String.valueOf(getClass().getResource("json/Student.json")), studentToken);
    }

    Type staffToken = TypeToken.getParameterized(ArrayList.class, Staff.class).getType();
    ArrayList<Staff> staff;
    try {
      staff = (ArrayList<Staff>) loadJsonFile(Info.RESOURCE_FILE_STAFF, staffToken);
    } catch (IOException e) {
      staff = (ArrayList<Staff>) loadJsonFile(String.valueOf(getClass().getResource("json/Staff.json")), staffToken);
    }

    users.addAll(staff);
    users.addAll(students);
    users.addAll(admins);

    return users;
  }

  public ArrayList<Event> loadEvent() throws IOException {
    Type gsonToken = TypeToken.getParameterized(ArrayList.class, Event.class).getType();
    return (ArrayList<Event>) loadJsonFile(Info.RESOURCE_FILE_EVENT, gsonToken);
  }

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

  public boolean saveEvents(ArrayList<Event> eventData) {
    return save(eventData, Info.RESOURCE_FILE_EVENT);
  }

  private boolean save(ArrayList<?> data, String fileName) {
    try {
      FileWriter jsFile = new FileWriter(fileName);
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      gson.toJson(data, jsFile);
      jsFile.close();
    } catch (IOException fail) {
      fail.printStackTrace();
      System.out.println(fail.getMessage());
      return false;
    }
    return true;
  }

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
