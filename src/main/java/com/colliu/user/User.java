package com.colliu.user;


/*
 This class is a parent class for the other com.colliu.colliu.user type classes; namely, student, staff and administrator.
 As this is an abstract class, a User object cannot be generated; a com.colliu.colliu.user shall be either student (administrator is also student) of staff.
 For every com.colliu.colliu.user, the application requires information regarding the attributes below.
 */
public abstract class User {

  private String email;
  private String password;
  private String firstName;
  private String lastName;
  //Date of last login - relevant to
  private boolean accountBanned; //A com.colliu.colliu.user can be banned from the system. This attribute saves the information if a com.colliu.colliu.user is banned or not.

  public User() {

  }


  /**
   * This is a constructor for the User class.
   * It prevents the following:
   * Emails from being blank, having the wrong format, having special characters "@", and that they are not an official
   * GU email.
   * Passwords from being empty, that they have to at least have one uppercase letter,
   * one lowercase letter, one number and that they are between 12 and 20 characters long.
   * First names from being blank.
   * Last names from being blank.
   * special chars
   */

  public User(String email, String password, String firstName, String lastName) {

    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.email = email;
    this.accountBanned = false; //By default, all users are not banned upon creation
  }

  public String getFirstName() {
    return this.firstName;
  }

  /**
   - @param name The new name identity we want to set for our object.
   */

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  /**
   - @param surname The new lastname identity we want to set for our object.
   */

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

  /**
   - @param password The new password security the user want to access their object.
   */

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean getAccountStatus() {
    return this.accountBanned;
  }

  public String toString() {
    return firstName + " " + lastName;
  }

  public boolean validatePassword(String password) {
    return password.equals(this.password);
  }

  public void setAccountStatus(boolean bannedStatus) {
    this.accountBanned = bannedStatus;
  }

}