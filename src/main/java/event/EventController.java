package event;

import com.colliu.colliu.Master;
import com.colliu.colliu.MasterController;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

/** This class handles the Event Object.
 * All methods for adding, getting and manipulating the info will be called from this class.
 */
public class EventController {

  ArrayList<Event> events;
  MasterController master;

  public EventController(MasterController masterClass) throws FileNotFoundException, UnsupportedEncodingException {
    master = masterClass;
    events = master.json.loadEvent();
  }


  public Boolean addEvent(String name, Date eventDate, String location, String program) {
    return events.add(new Event(events.size(), name, eventDate, location, program));
  }

  /*¢
  ***********
    GETTERS
  ***********
   */

  /*
  This method takes an Array of integers with the user's courses
  It then loops through this array and compares it with events that haven't expired
  Then it returns an array of the Id of all those events.
   */
  public Event[] getEvents(String program) {
    ArrayList<Event> recommendedEvents = new ArrayList<>();
    // Loop through all the courses that was sent with method
    for (Event event : this.events) {
      // If event is for this course and the event is not in the past:
      if (event.getProgram().equals(program) && event.getDate().before(new Date())) {
        recommendedEvents.add(event);
      }
    }
    //Convert arraylist into normal array and return:
    return recommendedEvents.toArray(new Event[0]);
  }

  public Event[] getNotifications(String program, Date lastLogin) { // Will return an array of all event-IDs that have not been seen
    Event[] recommendedEvents = getEvents(program);
    ArrayList<Event> notSeenEvents = new ArrayList<>();
    for (Event event : recommendedEvents) {
      Date eventCreated = event.getCreationDate();
      boolean isActive = event.isActive();
      if (eventCreated.after(lastLogin) && isActive) {
        notSeenEvents.add(event);
      }
    }
    return notSeenEvents.toArray(new Event[0]);
  }
}
