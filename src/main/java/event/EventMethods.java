package event;

import com.colliu.colliu.Master;
import com.colliu.colliu.MasterController;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import com.colliu.colliu.MasterController;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/** This class handles the Event Object.
 * All methods for adding, getting and manipulating the info will be called from this class.
 */
public class EventMethods {

  ArrayList<Event> events;
  MasterController master;

  public EventMethods(MasterController masterClass) throws FileNotFoundException, UnsupportedEncodingException {
    master = masterClass;
    events = master.json.loadEvent();
  }


  public Boolean addEvent(String name, LocalDate eventDate, String time, String location, String description, String category, String program) {
    return events.add(new Event(events.size(), name, eventDate, time, location, program, description, category));
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
      if (event.getProgram().equals(program) && event.getDate().isAfter(LocalDate.now())) {
        recommendedEvents.add(event);
      }
    }
    //Convert arraylist into normal array and return:
    return recommendedEvents.toArray(new Event[0]);
  }

  public Event[] getNotifications(String program, LocalDate lastLogin) { // Will return an array of all event-IDs that have not been seen
    Event[] recommendedEvents = getEvents(program);
    ArrayList<Event> notSeenEvents = new ArrayList<>();
    for (Event event : recommendedEvents) {
      LocalDate eventCreated = event.getCreationDate();
      boolean isActive = event.isActive();
      if (eventCreated.isAfter(lastLogin) && isActive) {
        notSeenEvents.add(event);
      }
    }
    return notSeenEvents.toArray(new Event[0]);
  }

  //a method for filtering events

  public Event[] filterEvents(String program, String[] tags) {
    Event[] programEvents = getEvents(program);
    ArrayList<Event> filteredEvents = new ArrayList<>();
    // Loops through all the events at first:
    for (int i = 0; i < programEvents.length; i++) {
      // THen loops through all of the tags/filters:
      for (int j = 0; j < tags.length; j++) {
        // Checks if the category in the event is the same as one of our tags- Also checks if it's already added in our list. to avoid duplicates
        if (programEvents[i].getCategory().equals(tags[j])) {
          // Adds the event to our new arraylist of events we want to show
          filteredEvents.add(programEvents[i]);
        }
      }

    }
    // Returns only the filtered events:
    return filteredEvents.toArray(new Event[0]);
  }
}
