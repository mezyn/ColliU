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
public class EventController {

  ArrayList<Event> events;
  MasterController master;

  public EventController(MasterController masterClass) throws FileNotFoundException, UnsupportedEncodingException {
    master = masterClass;
    events = master.json.loadEvent();
  }


  public Boolean addEvent(String name, LocalDate eventDate, String time, String location, String description, String category, String Program) {
    return events.add(new Event(events.size(), name, eventDate, time, location, description, category, program));
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

  //a method for filtering events

  public ArrayList<Event> filterEvents(String tagName){
    ArrayList<Event> filteredEvents = new ArrayList<Event>();
    for(Event event : events){
      if(event.getCategory().equals(tagName)){ //but what happens if more than 1 filter has been chosen?
        filteredEvents.add(event);           //CHECK HOW CHECKBOXES WORK AND HOW I CAN IMPLEMENT THAT, AS WELL AS HOW THE EVENTS ARE GONNA BE DISPLAYED
      }
    }
    return filteredEvents; //how do we display them? and how we remove after that all the event objects from the ArrayList once we're finished with the filtering?
  }




}
