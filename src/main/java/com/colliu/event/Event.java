package com.colliu.event;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 *  Event class acts as an object for storing information about events.
 *  Relevant information can only be modified via EventMethods.java
 *  Any other modifications is done to cloned objects.
 */

public class Event {
  //About event information - Explained in Constructor Documentation below
  // Will be used to filter events in the future.
  private final int id; // Will be increased by +1 arraylist size in EventMethods.java
  private final String time;
  private final String title;
  private final Date date;
  private final String location;
  private final String description;
  private final String category;
  private final String program;
  private final String host;

  // The emails of users who will attend the event.
  private final ArrayList<String> attending;
  // The emails of users who have dismissed event notification.
  private final ArrayList<String> seenBy;
  // The emails, reactions and names of users who have reacted to the event.
  private final ArrayList<String[]> reactions;

  /**
   * When an object is created information is stored into variables.
   * New arraylists are also created for storing relevant info.
   - @param title A way to quickly describe the event.
   - @param date The date of when the event occurs.
   - @param time The time of when the event occurs on that day.
   - @param location The location where event is hosted.
   - @param program Which group of users will be able to see the event.
   - @param description A more detailed explanation/description for the event.
   - @param category A way to let users filter the event by type.
   - @param host The email of the user who created the event.
   */
  public Event(int id, String title, LocalDate date, String time, String location,
               String program, String description, String category, String host) {

    this.id = id;
    this.title = title;
    this.date = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    this.location = location;
    this.description = description;
    this.category = category;
    this.program = program;
    this.time = time;
    this.host = host;
    attending = new ArrayList<>();
    seenBy = new ArrayList<>();
    reactions = new ArrayList<>();
  }

  /*
  ********************
  Retrieve information
  ********************
   */

  public String getTitle() {
    return this.title;
  }

  /**
   * Converts Date object to LocalDate
   - @return The date of when event is taking place, converted into a LocalDate object.
   */
  public LocalDate getDate() {
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

  public ArrayList<String> getSeenBy() {
    return seenBy;
  }

  public ArrayList<String> getAttending() {
    return attending;
  }

  public boolean isActive() {
    return getDate().isAfter(LocalDate.now());
  }

  /*
  *******************
     Modify Arrays
  *******************
   */

  /**
   * Allows users to express their interest in attending an event.
   - @param email The email address of whatever user is attending the event.
   */

  public void addAttendee(String email) {
    if (!attending.contains(email)) {
      attending.add(email);
    }
  }

  /**Allows users to retract their interest in attending an event.
   - @param email The email address of whatever user is canceling their attendance.
   */
  public void delAttendee(String email) {
    int index = attending.indexOf(email);
    if (index != -1) { // if a User is present in the list then remove them.
      attending.remove(index);
    }
  }

  /**
   * When a user closes a notification of new event they will be added here so-
   * that it does not display again.
   - @param email The email address to identify which user has seen the event.
   */
  public void addSeenBy(String email) {
    if (!seenBy.contains(email)) {
      seenBy.add(email);
    }
  }

  /*
  ************************
  * RETRIEVE INFORMATION *
  ************************
   */

  /**
   - @return Time of event.
   */
  public String getTime() {
    return time;
  }

  /**
   - @return Email of event creator.
   */
  public String getHost() {
    return host;
  }

  /**
   * Allows users to express their emotions to the event without attending or commenting.
   - @param email The email address to identify a user
   - @param reaction An integer identifying which reaction the user clicked.
   - @param name The name to identify the user who reacted.
   */

  public void addReaction(String email, int reaction, String name) {
    String[] info = new String[3];
    info[0] = email;
    info[1] = "" + reaction;
    info[2] = name;
    boolean removeReaction = false;
    int index = -1;
    // Loop through all reactions
    for (int i = 0; i < reactions.size(); i++) {
      // Check if user's email is in the arraylist.
      if (reactions.get(i)[0].equals(info[0])) {
        index = i;
        removeReaction = (reactions.get(i)[1].equals(info[1]));
        break;
      }
    }
    // If user already reacted then replace active reaction
    if (index >= 0 && !removeReaction) {
      reactions.set(index, info);
      // If user clicked the same reaction, then remove the reaction instead.
    } else if (index >= 0) {
      reactions.remove(index);
      // If user has no reaction then add a new reaction.
    } else {
      reactions.add(info);
    }
  }

  /**
   - @return A copy of all reactions.
   */

  public ArrayList<String[]> getReactions() {
    return reactions;
  }

}