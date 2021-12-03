package drafts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class loadNotifications {

  String[] firstNames = { "Harry", "Ross",
    "Bruce", "Cook",
    "Carolyn", "Morgan",
    "Albert", "Walker",
    "Randy", "Reed",
    "Larry", "Barnes",
    "Lois", "Wilson",
    "Jesse", "Campbell",
    "Ernest", "Rogers",
    "Theresa", "Patterson",
    "Henry", "Simmons",
    "Michelle", "Perry",
    "Frank", "Butler",
    "Shirley" };

  String[] lastNames = { "Ruth", "Jackson",
    "Debra", "Allen",
    "Gerald", "Harris",
    "Raymond", "Carter",
    "Jacqueline", "Torres",
    "Joseph", "Nelson",
    "Carlos", "Sanchez",
    "Ralph", "Clark",
    "Jean", "Alexander",
    "Stephen", "Roberts",
    "Eric", "Long",
    "Amanda", "Scott",
    "Teresa", "Diaz",
    "Wanda", "Thomas" };

  String[] subjects = { "Programming", "Math",
    "English", "Science",
    "Chemistry", "Sports",
    "Leadership", "Marketing",
    "Psychology", "Swedish" };

  ArrayList<User> students = new ArrayList<>();
  ArrayList<Course> courses = new ArrayList<>();
  ArrayList<Event> events = new ArrayList<>();

  public static void main(String[] args) {
    new loadNotifications();
  }

  loadNotifications() {
    // code here
    createCourses(); // Creates objects for courses and stores in array
    createStudents(10); // Creates 10 random students.
    Scanner in = new Scanner(System.in);
    // Start notifications solution here:
    while (true) {
      System.out.print("1 Log in as random student, 2 Create an event for all courses, 3: Load all events: ");
      int opt = in.nextInt();
      if (opt == 1) {
        int random = (int) Math.floor(Math.random() * (students.size()));
        students.get(random).recordLogin();
      } else if (opt == 2) {
        for (int i = 0; i < courses.size(); i++) {
          createEvent("Event" + events.size(), new Date(), "School", courses.get(i).getId());
        }
      } else if (opt == 3) {
        for (Event event : events) {
          System.out.println(event.getTitle() + " for Course ID " + event.getCourse() + " : Created on " + event.getEventCreationDate());
        }
      }
    }

  }

  Boolean createEvent(String name, Date eventDate, String location, int courseId) {
    return events.add(new Event(events.size(), name, eventDate, location, courseId, this));
  }

  //Return an integer of
  public Integer[] getEvents(Integer[] usersCourses) {
    ArrayList<Integer> recommendedEvents = new ArrayList<>();
    for (Integer usersCourse : usersCourses) {
      for (Event event : this.events) {
        if (event.getCourse() == usersCourse) {
          recommendedEvents.add(event.getId());
        }
      }
    }
    return recommendedEvents.toArray(new Integer[0]);
  }

  private void createStudents(int n) {
    String name;
    int randIndex;
    int randIndex2;
    int randCourseSize;
    int id;
    // Create n amount of users
    for (int i = 0; i < n; i++) {
      // Randomize first and last name and amount of courses.
      randIndex = (int) Math.floor(Math.random() * (firstNames.length));
      randIndex2 = (int) Math.floor(Math.random() * (lastNames.length));
      randCourseSize = (int) Math.floor(Math.random() * (courses.size()) + 1);
      // Assign student ID and Name
      id = students.size();
      name = firstNames[randIndex] + " " + lastNames[randIndex2];
      // Add student and assign random courses
      students.add(new User(name, id, this)); // Creates object and store in array
      assignCourses(id, randCourseSize); // Assign courses to this student
      login(id); // Record this timestamp as latest login.
      System.out.println(name + " last login: " + new Date());
    }
  }

  private void createCourses() {
    // Create course objects depending on subjects array size.
    for (int i = 0; i < subjects.length; i++) {
      courses.add(new Course(i, subjects[i]));
    }
  }

  private void assignCourses(int id, int amt) {
    int course;
    amt = (Math.min(amt, courses.size()));
    // Assign users set amount of courses, but randomize them(obs may be duplicates).
    int randStart = (int) (Math.random() * (courses.size() - amt));
    for (int i = randStart; i < courses.size(); i++) {
      course = (int) Math.floor(Math.random() * (courses.size()));
      students.get(id).addCourse(course);
    }
  }

  private void login(int id) {
    students.get(id).recordLogin();
  }

}

// NEW CLASS JUST FOR OOP TESTING PURPOSE.

class User {
  private final int id;
  private String name;
  private final ArrayList<Integer> courses = new ArrayList<>();
  private final ArrayList<Date> logins = new ArrayList<>();
  private final loadNotifications refControl;

  User(String name, int id, loadNotifications referenceToController) {
    this.name = name;
    this.id = id;
    refControl = referenceToController;
  }

  public void addCourse(int courseId) {
    courses.add(courseId);
  }

  public String getName() {
    return name;
  }

  public Integer[] getCourses() {
    return courses.toArray(new Integer[0]);
  }

  public void recordLogin() {
    loadNotifications();
    logins.add(new Date());
  }

  public Date getLastLogin() {
    return logins.get(logins.size() - 1);
  }

  public void setName(String newName) {
    this.name = newName;
  }

  public int getId() {
    return this.id;
  }

  public Integer[] loadNotifications() { // Will return an array of all event-IDs that have not been seen
    Integer[] recommendedEvents = refControl.getEvents(getCourses());
    ArrayList<Integer> notSeenEvents = new ArrayList<>();
    for (int eventId : recommendedEvents) {
      Date eventCreated = refControl.events.get(eventId).getEventCreationDate();
      if (eventCreated.after(getLastLogin())) {
        notSeenEvents.add(eventId);
      }
    }
    System.out.println(name + " has " + notSeenEvents.size() + " new notifications since login:");
    for (Integer notSeenEvent : notSeenEvents) {
      System.out.println("New events for course id: " + notSeenEvent);
    }
    return notSeenEvents.toArray(new Integer[0]);
  }

  public boolean canHideEvent(Date creationDate) {
    int numberOfLoginsSinceEventCreated = 0;
    for (int i = 0; i < logins.size(); i++) {
      if (logins.get(i).after(creationDate)) {
        numberOfLoginsSinceEventCreated++;
      }
    }
    if (numberOfLoginsSinceEventCreated >= 2) {
      return true;
    } else {
      return false;
    }
  }

}

class Course {

  private final int id;
  private final String course;

  Course(int id, String course) {
    this.id = id;
    this.course = course;
  }

  String getName() {
    return course;
  }

  int getId() {
    return id;
  }
}

class Event {
  private String title;
  private Date date;
  private final Date creationDate;
  private String location;
  private int course;
  private final int id;
  private ArrayList<Integer> attending;
  private ArrayList<Integer> guestTutors;
  private ArrayList<Integer> seenBy;
  loadNotifications referenceToController;

  Event(int id, String eventName, Date eventDate, String location, int courseId, loadNotifications controllerReference) {
    this.id = id;
    this.title = eventName;
    this.date = eventDate;
    this.location = location;
    this.course = courseId;
    referenceToController = controllerReference; // Reference
    creationDate = new Date();
  }

  /*
  ********************
  Retrieve information
  ********************
   */

  public String getTitle() {
    return this.title;
  }

  public Date getDate() {
    return this.date;
  }

  public String getLocation(){
    return this.location;
  }

  public int getCourse(){
    return this.course;
  }

  public int getId() {
    return this.id;
  }

  public Integer[] getSeen() {
    return seenBy.toArray(new Integer[0]); // converts into a regular array.
  }

  public Integer[] getAttendees() {
    return attending.toArray(new Integer[0]); // Converts into an Integer array.
  }

  public Date getEventCreationDate() {
    return creationDate;
  }

  /*
  *******************
     Modify object
  *******************
   */

  public boolean addParticipant(int id) {
    return (!lookupId(id, attending) && attending.add(id));
  }

  public void addSeen(int id) {
    if (!lookupId(id, seenBy)) {
      seenBy.add(id);
    }
  }

  public boolean deleteParticipant(int id) {
    int index = lookupIndex(id, attending);
    if (index == -1) {
      return false;
    } else {
      attending.remove(index);
      return true;
    }
  }

  public void setDate(Date newDate) {
    this.date = newDate;
  }

  public void setLocation(String newLocation) {
    this.location = newLocation;
  }

  public void setTitle(String newName) {
    this.title = newName;
  }

  public void setCourse(int newCourse) {
    this.course = newCourse;
  }

  private boolean lookupId(int id, ArrayList<Integer> list) {
    return list.contains(id);
  }

  private int lookupIndex(int id, ArrayList<Integer> list) {
    return list.indexOf(id);
  }
}