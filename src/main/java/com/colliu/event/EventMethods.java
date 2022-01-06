package com.colliu.event;

import com.colliu.PageController;
import com.colliu.miscellaneous.Info;
import com.colliu.user.Staff;
import com.colliu.user.Student;
import com.colliu.user.User;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class handles the Event Object.
 * All methods for adding, getting and manipulating the info will be called from this class.
 */
public class EventMethods {

  private final ArrayList<Event> events;
  private final PageController master;
  private User currentUser;

  public EventMethods(PageController masterClass) {
    master = masterClass;
    events = master.loadEvents();
  }

  /**
   - @param title A way to quickly describe the com.colliu.colliu.event.
   - @param date The date of when the com.colliu.colliu.event occurs.
   - @param time The time of when the com.colliu.colliu.event occurs on that day.
   - @param location The location where com.colliu.colliu.event is hosted.
   - @param description A more detailed explanation/description for the com.colliu.colliu.event.
   - @param category A way to let users filter the com.colliu.colliu.event by type.
   - @param program Which group of users will be able to see the com.colliu.colliu.event.
   - @param host The email of the com.colliu.colliu.user who created the com.colliu.colliu.event.
   */

  public void addEvent(String title, LocalDate date, String time, String location,
                          String description, String category, String program, String host) {
    events.add(new Event(events.size(), title, date, time, location,
        description, category, program, host));
  }


  /**
   * This method takes an Array of integers with the user's courses
  It then loops through this array and compares it with events that haven't expired
  Then it returns an array of the  of all those events.
   */

  public Event[] getEvents(int type) {
    currentUser = master.getCurrentUser();
    if (currentUser instanceof Staff) {
      return getHostingEvents(type);
    }

    String program = ((Student) currentUser).getProgram();
    if (program.equals(Info.STAFF_FILTER)) {
      return getHostingEvents(Info.UPCOMING_EVENTS);
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
          if (event.getAttending().contains(master.getCurrentUser().getEmail())
              && event.getDate().isAfter(LocalDate.now())) {
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

  /**
   - @return Return an array of only upcoming events.
   */

  public Event[] getUpcomingEvents() {
    return getEvents(Info.UPCOMING_EVENTS);
  }

  public Event[] getPastEvents() {
    return getEvents(Info.PAST_EVENTS);
  }

  public Event[] getAttendingEvents() {
    return getEvents(Info.ATTENDING_EVENTS);
  }

  /**
   * Filter all events that the user has not already seen or that has not passed already.
   - @return an array of filtered event objects.
   */

  public Event[] getNotifications() {
    String email = currentUser.getEmail();
    Event[] recommendedEvents = getEvents(Info.UPCOMING_EVENTS);
    ArrayList<Event> notSeenEvents = new ArrayList<>();
    for (Event event : recommendedEvents) {
      boolean isActive = event.isActive();
      if (!event.getSeenBy().contains(email) && isActive) {
        notSeenEvents.add(event);
      }
    }
    return notSeenEvents.toArray(new Event[0]);
  }

  /** Method for filtering events by programs or tags.
   - @return an array of filtered events.
   */
  public Event[] filterEvents(String[] tags) {
    Event[] programEvents = (currentUser instanceof Staff
        ? getHostingEvents(Info.UPCOMING_EVENTS) : getEvents(Info.UPCOMING_EVENTS));
    ArrayList<Event> filteredEvents = new ArrayList<>();
    // Loops through all the events at first
    for (Event programEvent : programEvents) {
      // Then loops through all the tags/filters
      for (String tag : tags) {
        // Checks if the category in the com.colliu.colliu.event is the same as one of our tags-
        // Also checks if it's already added in our list. to avoid duplicates
        if (programEvent.getCategory().equals(tag)) {
          // Adds the com.colliu.colliu.event to our new arraylist of events we want to show
          filteredEvents.add(programEvent);
        }
      }
    }
    // Returns only the filtered events:
    return filteredEvents.toArray(new Event[0]);
  }

  public ArrayList<Event> getAllEvents() {
    return events;
  }

  /**
   * Gets all the events that are matching the Host email with Staff-objects email identifier.
   - @param type - Is an integer deciding if an upcoming or past event was requested.
   - @return An array of events that fulfill the requirements of the request.
   */
  public Event[] getHostingEvents(int type) {
    ArrayList<Event> hostingEvents = new ArrayList<>();
    String email = currentUser.getEmail();
    for (Event event : events) {
      if (type == Info.PAST_EVENTS) {
        if (event.getHost().equals(email) && event.getDate().isBefore(LocalDate.now())) {
          hostingEvents.add(event);
        }
      } else {
        if (event.getHost().equals(email) && event.getDate().isAfter(LocalDate.now())) {
          hostingEvents.add(event);
        }
      }
    }
    return hostingEvents.toArray(new Event[0]);
  }
}
