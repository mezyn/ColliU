package event;

import com.colliu.colliu.MasterController;

import miscellaneous.Info;
import user.User;

import java.time.LocalDate;
import java.util.ArrayList;

/** This class handles the Event Object.
 * All methods for adding, getting and manipulating the info will be called from this class.
 */
public class EventMethods {

  private final ArrayList<Event> events;
  private final MasterController master;

  public EventMethods(MasterController masterClass) {
    master = masterClass;
    events = master.loadEvents();
  }

  //Take relevant event information from a user and create an event.
  public Boolean addEvent(String name, LocalDate eventDate, String time, String location, String description, String category, String program, String host) {
    return events.add(new Event(events.size(), name, eventDate, time, location, description, category, program, host));
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

  public Event[] getEvents(String program, int type) {
    if (program.equals(Info.STAFF_FILTER)) {
      return getHostingEvents(master.getCurrentUser(), Info.UPCOMING_EVENTS);
    }
    ArrayList<Event> recommendedEvents = new ArrayList<>();
    // Loop through all the courses that was sent with method
    for (Event event : this.events) {
      // If event is for this course and the event is not in the past:
      switch (type) {
        case 2:
          if (event.getProgram().equals(program) && event.getDate().isBefore(LocalDate.now())) {
            recommendedEvents.add(event);
          }
          break;
        case 3:
          if (event.getAttending().contains(master.getCurrentUser().getEmail()) && event.getDate().isAfter(LocalDate.now())) {
            recommendedEvents.add(event);
          }
          break;
        default:
          if (event.getProgram().equals(program) && event.getDate().isAfter(LocalDate.now())) {
            recommendedEvents.add(event);
          }
      }
    }
    //Convert arraylist into normal array and return:
    return recommendedEvents.toArray(new Event[0]);
  }

  public Event[] getNotifications(String email, String program) { // Will return an array of all event-IDs that have not been seen
    Event[] recommendedEvents = getEvents(program, Info.UPCOMING_EVENTS);
    ArrayList<Event> notSeenEvents = new ArrayList<>();
    for (Event event : recommendedEvents) {
      if (!event.getSeenBy().contains(email) && event.getDate().isAfter(LocalDate.now())) {
        notSeenEvents.add(event);
      }
    }
    return notSeenEvents.toArray(new Event[0]);
  }

  //a method for filtering events

  public Event[] filterEvents(String program, String[] tags) {
    Event[] programEvents = (program.equals(Info.STAFF_FILTER) ? getHostingEvents(master.getCurrentUser(), Info.UPCOMING_EVENTS) : getEvents(program, Info.UPCOMING_EVENTS));
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

  public ArrayList<Event> getAllEvents() {
    return events;
  }

  public Event[] getHostingEvents(User currentUser, int type) {
    ArrayList<Event> hostingEvents = new ArrayList<>();
    for (Event event : events) {
      switch (type) {
        case 2:
          if (event.getHost().equals(currentUser.getEmail()) && event.getDate().isBefore(LocalDate.now())) {
            hostingEvents.add(event);
          }
          break;
        default:
          if (event.getHost().equals(currentUser.getEmail()) && event.getDate().isAfter(LocalDate.now())) {
            hostingEvents.add(event);
          }
      }
    }
    return hostingEvents.toArray(new Event[0]);
  }
}
