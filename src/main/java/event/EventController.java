package event;

import com.colliu.colliu.Master;
import com.colliu.colliu.MasterController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/** This class handles the Event Object.
 * All methods for adding, getting and manipulating the info will be called from this class.
 */
public class EventController {

  ArrayList<Event> events;
  //added this one to store the filtered events later on

  Master master;

  public EventController(Master masterClass) {
    master = masterClass;
    events = new ArrayList<>();
  }


  public Boolean addEvent(String name, LocalDate eventDate, String time, String location, String description, String category, String courseId) {
    return events.add(new Event(events.size(), name, eventDate, time, location, description, category, courseId));
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
  Integer[] getEvents(Integer[] usersCourses) {
    ArrayList<Integer> recommendedEvents = new ArrayList<>();
    // Loop through all the courses that was sent with method
    for (Integer usersCourse : usersCourses) {
      // Loop through all the events:
      for (Event event : this.events) {
        // If event is for this course and the event is not in the past:
        //Mijin: I changed Date to LocalDate, hope the following line of code still works properly,
        if (event.getCourse().equals(usersCourse) && event.getDate().isAfter(LocalDate.now())){
          recommendedEvents.add(event.getId());
        }
      }
    }
    //Convert arraylist into normal array and return:
    return recommendedEvents.toArray(new Integer[0]);
  }

  public Integer[] getNotifications(Integer[] courses, Date lastLogin) { // Will return an array of all event-IDs that have not been seen
    Integer[] recommendedEvents = getEvents(courses);
    ArrayList<Integer> notSeenEvents = new ArrayList<>();
    for (int eventId : recommendedEvents) {
      Date eventCreated = events.get(eventId).getCreationDate();
      boolean isActive = events.get(eventId).isActive();
      if (eventCreated.after(lastLogin) && isActive) {
        notSeenEvents.add(eventId);
      }
    }
    return notSeenEvents.toArray(new Integer[0]);
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
//function for filtering the events
