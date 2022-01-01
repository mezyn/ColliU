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
  public Data() {
    new File(Info.DOCUMENT_PATH).mkdirs(); // Create ColliU folder in Documents path if does not exist.
  }

  public ArrayList<User> loadUser() throws FileNotFoundException, UnsupportedEncodingException, IOException {
    ArrayList<User> users = new ArrayList<>();

    Type studentToken = TypeToken.getParameterized(ArrayList.class, Student.class).getType();
    ArrayList<Student> students = (ArrayList<Student>) loadJsonFile(Info.RESOURCE_FILE_STUDENT, studentToken);

    Type staffToken = TypeToken.getParameterized(ArrayList.class, Staff.class).getType();
    ArrayList<Staff> staff = (ArrayList<Staff>) loadJsonFile(Info.RESOURCE_FILE_STAFF, staffToken);

    users.addAll(staff);
    users.addAll(students);

    return users;
  }

  public ArrayList<Event> loadEvent() throws IOException {
    Type gsonToken = TypeToken.getParameterized(ArrayList.class, Event.class).getType();
    return (ArrayList<Event>) loadJsonFile(Info.RESOURCE_FILE_EVENT, gsonToken);
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
    return save(students, Info.RESOURCE_FILE_STUDENT) && save(staff, Info.RESOURCE_FILE_STAFF);
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
