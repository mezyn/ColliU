package miscellaneous;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import com.google.gson.reflect.TypeToken;
import event.Event;
import java.io.BufferedReader;
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
import javax.swing.JFileChooser;

import user.Administrator;
import user.Staff;
import user.Student;
import user.User;

public class Data {

  final String DOCUMENT_PATH = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/ColliU/";
  final String USER_FILE = DOCUMENT_PATH + "User.JSON";
  final String STUDENT_FILE = DOCUMENT_PATH + "Student.JSON";
  final String ADMIN_FILE = DOCUMENT_PATH + "Admin.JSON";
  final String STAFF_FILE = DOCUMENT_PATH + "Staff.JSON";
  final String EVENT_FILE = DOCUMENT_PATH + "Event.JSON";
  //final URL JSON_DIR;

  public Data() {
    new File(DOCUMENT_PATH).mkdirs(); // Create ColliU folder in Documents path if does not exist.
  }

  public ArrayList<User> loadUser() throws FileNotFoundException, UnsupportedEncodingException, IOException {
    ArrayList<User> users = new ArrayList<>();

    Type studentToken = TypeToken.getParameterized(ArrayList.class, Student.class).getType();
    ArrayList<Student> students = (ArrayList<Student>)loadJsonFile(STUDENT_FILE, studentToken);

    Type staffToken = TypeToken.getParameterized(ArrayList.class, Staff.class).getType();
    ArrayList<Staff> staff = (ArrayList<Staff>)loadJsonFile(STAFF_FILE, staffToken);

    users.addAll(staff);
    users.addAll(students);

    return users;
  }

  public ArrayList<Event> loadEvent() throws IOException {
    Type gsonToken = TypeToken.getParameterized(ArrayList.class, Event.class).getType();
    return (ArrayList<Event>) loadJsonFile(EVENT_FILE, gsonToken);
  }

  public boolean saveUsers(ArrayList<?> userData) {
    ArrayList<Student> students = new ArrayList<>();
    ArrayList<Staff> staff = new ArrayList<>();
    for (int i = 0; i < userData.size(); i++) {
      if (userData.get(i) instanceof Student) {
        students.add((Student) userData.get(i));
      } else if(userData.get(i) instanceof Staff) {
        staff.add((Staff) userData.get(i));
      }
    }
    return save(students, STUDENT_FILE) && save(staff, STAFF_FILE);
  }

  public boolean saveEvents(ArrayList<Event> eventData) {
    return save(eventData, EVENT_FILE);
  }

  private boolean save(ArrayList<?> data, String fileName) {
    try {
      FileWriter jsFile = new FileWriter(fileName);
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      gson.toJson(data, jsFile);
      jsFile.close();
    } catch(IOException fail) {
      fail.printStackTrace();
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
