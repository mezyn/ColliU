package event;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 *<p>
 *  Event class acts as an object for storing event information.
 *  This will be handled by EventController class thus, all info is private or package-private.
 * </p>
 */

public class Event {
  private final Date creationDate;
  private final int id;
  private String time;
  //Related to the method of addEvent(String name, Date eventDate, String location, int courseId) in the EventController class,
  // you might want to explain what's the system in providing id to each event;
  // i.e. the first created event gets an id of 1, and the next one 2, ...
  private String title;
  private Date date;
  private String location;
  private String description;
  private String category;
  private String program;
  private boolean active;
  private final ArrayList<Integer> attending; //Does it mean participants/attendees? A comment for explanation would be nice
  private final ArrayList<Integer> guestTutors; //Same here, what does the roll of guest tutors at an event?
  private final ArrayList<Integer> seenBy;

  public Event(int id, String title, LocalDate date, String time, String location, String program, String description, String category) {
    this.id = id;
    this.title = title;
    this.date = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    System.out.println(date);
    this.location = location;
    this.program = program;
    creationDate = Date.from(LocalDate.now(ZoneId.of("Europe/Berlin")).atStartOfDay(ZoneId.systemDefault()).toInstant());
    this.time = time;
    attending = new ArrayList<>();
    guestTutors = new ArrayList<>();
    seenBy = new ArrayList<>();
    active = true;
  }

  /*
  ********************
  Retrieve information
  ********************
   */
//Getters and setters
  public String getTitle() {
    return this.title;
  }

  public LocalDate getDate() {
    System.out.println(date.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate());
    return date.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
  }

  public String getLocation() {
    return this.location;
  }

  public String getProgram() {
    return this.program;
  }

  public String getDescription() {
    return this.description;
  }

  public String getCategory() {
    return this.category;
  }

  public int getId() {
    return this.id;
  }

  public Integer[] getSeenBy() {
    return seenBy.toArray(new Integer[0]); // converts into a regular array.
  }

  public Integer[] getAttending() {
    return attending.toArray(new Integer[0]); // Converts into an Integer array.
  }

  public LocalDate getCreationDate() {
    return creationDate.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
  }

  public boolean isActive() {
    return active; // true if active, False if not.
  }

  /*
  *******************
     Modify object 
  *******************
   */

  public void addAttendee(int id) {
    int index = attending.indexOf(id);
    if (index == -1) { //If no match found
      attending.add(id);
    }
  }

  public void delAttendee(int id) {
    int index = attending.indexOf(id);
    if (index != -1) {
      attending.remove(index);
    }
  }


  public void addSeenBy(int id) {
    int index = seenBy.indexOf(id);
    if (index == -1) {
      seenBy.add(id);
    }
  }

  public void setDate(LocalDate newDate) {
    this.date = Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  public void setLocation(String newLocation) {
    this.location = newLocation;
  }

  public void setTitle(String newName) {
    this.title = newName;
  }

  public void setProgram (String newProgram) {
    this.program = newProgram;
  }

  public void setStatus(boolean status) {
    active = status;
  }

  public String getTime() {
    return time;
  }
}