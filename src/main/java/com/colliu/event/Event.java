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
  //About com.colliu.event information - Explained in Constructor Documentation below
  // Will be used to filter events in the future.
  private final int id; // Will be increased by +1 arraylist size in EventMethods.java
  private final String time;
  private String title;
  private Date date;
  private String location;
  private String description;
  private final String category;
  private String program;
  private final String host;

  // The emails of users who will attend the com.colliu.colliu.event.
  private final ArrayList<String> attending;
  // The emails of users who have dismissed com.colliu.colliu.event notification.
  private final ArrayList<String> seenBy;
  // The emails, reactions and names of users who have reacted to the com.colliu.colliu.event.
  private final ArrayList<String[]> reactions;

  /**
   * When an object is created information is stored into variables.
   * New arraylists are also created for storing relevant info.
   - @param title A way to quickly describe the com.colliu.colliu.event.
   - @param date The date of when the com.colliu.colliu.event occurs.
   - @param time The time of when the com.colliu.colliu.event occurs on that day.
   - @param location The location where com.colliu.colliu.event is hosted.
   - @param program Which group of users will be able to see the com.colliu.colliu.event.
   - @param description A more detailed explanation/description for the com.colliu.colliu.event.
   - @param category A way to let users filter the com.colliu.colliu.event by type.
   - @param host The email of the com.colliu.colliu.user who created the com.colliu.colliu.event.
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
   - @return The date of when com.colliu.colliu.event is taking place, converted into a LocalDate object.
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
     Modify variables
  *******************
   */

  /**
   * Allows users to express their interest in attending an com.colliu.colliu.event.
   - @param email The email address of whatever com.colliu.colliu.user is attending the com.colliu.colliu.event.
   */

  public void addAttendee(String email) {
    if (!attending.contains(email)) {
      attending.add(email);
    }
  }

  /**Allows users to retract their interest in attending an com.colliu.colliu.event.
   - @param email The email address of whatever com.colliu.colliu.user is canceling their attendance.
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
  ***********************
  * OBJECT MODIFICATION
  ***********************
   */

  /**
   - @param newDate Is the new date for the com.colliu.colliu.event.
   */
  public void setDate(LocalDate newDate) {
    this.date = Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  /**
   - @param newLocation Is the new location for the com.colliu.colliu.event.
   */
  public void setLocation(String newLocation) {
    this.location = newLocation;
  }

  /**
   - @param newName Is the new title for the object.
   */
  public void setTitle(String newName) {
    this.title = newName;
  }

  /**
   - @param newProgram Is the new group to display the com.colliu.colliu.event for.
   */
  public void setProgram(String newProgram) {
    this.program = newProgram;
  }

  /**
   - @param newDescription New description/explanation for the com.colliu.colliu.event.
   */
  public void setDescription(String newDescription) {
    this.description = newDescription;
  }

  /**
   - @return Time of com.colliu.colliu.event.
   */
  public String getTime() {
    return time;
  }

  /**
   - @return Email of com.colliu.colliu.event creator.
   */
  public String getHost() {
    return host;
  }

  /**
   * Allows users to express their emotions to the com.colliu.colliu.event without attending or commenting.
   - @param email The email adress to identify a com.colliu.colliu.user
   - @param reaction An integer identifying which reaction the com.colliu.colliu.user clicked.
   - @param name The name to identify the com.colliu.colliu.user who reacted.
   - @return True if a reaction was deleted, true if added or modified.
   * Return type is meant to be used in future development but for now it's not used.
   */

  public boolean addReaction(String email, int reaction, String name) {
    String[] info = new String[3];
    info[0] = email;
    info[1] = "" + reaction;
    info[2] = name;
    boolean removeReaction = false;
    int index = -1;
    // Loop through all reactions
    for (int i = 0; i < reactions.size(); i++) {
      // Check if com.colliu.colliu.user's email is in the arraylist.
      if (reactions.get(i)[0].equals(info[0])) {
        index = i;
        removeReaction = (reactions.get(i)[1].equals(info[1]));
        break;
      }
    }
    // If com.colliu.colliu.user already reacted then replace active reaction
    if (index >= 0 && !removeReaction) {
      reactions.set(index, info);
      return false;
      // If com.colliu.colliu.user clicked the same reaction, then remove the reaction instead.
    } else if (index >= 0) {
      reactions.remove(index);
      return true;
      // If com.colliu.colliu.user has no reaction then add a new reaction.
    } else {
      reactions.add(info);
      return false;
    }
  }

  /**
   - @return A copy of all reactions.
   */

  public ArrayList<String[]> getReactions() {
    return reactions;
  }

}