package miscellaneous;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import com.google.gson.reflect.TypeToken;
import event.Event;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import user.User;

public class Data {

  final String DOCUMENT_PATH = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/ColliU/";
  final String USER_FILE = DOCUMENT_PATH + "User.JSON";
  final String EVENT_FILE = DOCUMENT_PATH + "Event.JSON";
  //final URL JSON_DIR;

  public Data() throws IOException {
    new File(DOCUMENT_PATH).mkdirs(); // Create ColliU folder in Documents path if does not exist.
    File user = new File(USER_FILE);
    user.createNewFile();
    new FileOutputStream(user, false);
    File event = new File(EVENT_FILE);
    event.createNewFile();
    new FileOutputStream(event, false);
  }

  public ArrayList<User> loadUser() throws FileNotFoundException, UnsupportedEncodingException {
    Type gsonToken = TypeToken.getParameterized(ArrayList.class, User.class).getType();
    return (ArrayList<User>) loadJsonFile(USER_FILE, gsonToken);
  }

  public ArrayList<Event> loadEvent() throws FileNotFoundException, UnsupportedEncodingException {
    Type gsonToken = TypeToken.getParameterized(ArrayList.class, Event.class).getType();
    return (ArrayList<Event>) loadJsonFile(EVENT_FILE, gsonToken);
  }

  /*public ArrayList<Program> loadProgram() throws FileNotFoundException, UnsupportedEncodingException {
    Type gsonToken = TypeToken.getParameterized(ArrayList.class, Program.class).getType();
    return (ArrayList<Event>) loadJsonFile(PROGRAM_FILE, gsonToken);
  }*/

  public void saveUsers(ArrayList<?> userData) throws IOException {
    save(userData, USER_FILE);
  }

  public void saveEvents(ArrayList<Event> eventData) throws IOException {
    save(eventData, EVENT_FILE);
  }

  private void save(ArrayList<?> data, String fileName) throws IOException {
    FileWriter jsFile = new FileWriter(fileName);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    gson.toJson(data, jsFile);
    jsFile.close();

  }

  private ArrayList<?> loadJsonFile(String fileName, Type gsonToken) throws FileNotFoundException, UnsupportedEncodingException {
    InputStream is = new FileInputStream(fileName);
    Reader r = new InputStreamReader(is, "UTF-8");
    Gson gson = new GsonBuilder().create();
    JsonStreamParser jsp = new JsonStreamParser(r);
    ArrayList<Object> dataList = new ArrayList<>();

    while (jsp.hasNext()) {
      JsonElement jsonData = jsp.next();
      dataList = gson.fromJson(jsonData, gsonToken);
    }
    return dataList;
  }

}
