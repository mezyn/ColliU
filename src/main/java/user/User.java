package user;


import java.util.ArrayList;
import java.util.Date;

/*
 This class is a parent class for the other user type classes; namely, student, staff and administrator.
 As this is an abstract class, a User object cannot be generated; a user shall be either student (administrator is also student) of staff.
 For every user, the application requires information regarding the attributes below.
 */
public abstract class User {

  private int type;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private ArrayList<Date> logins; //Date of last login - relevant to
  private boolean accountBanned; //A user can be banned from the system. This attribute saves the information if a user is banned or not.

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

  public User(String email, String password, String firstName, String lastName, int type) throws Exception {

    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.email = email;
    this.logins = new ArrayList<>(); //It saves the last login date
    this.accountBanned = false; //By default, all users are not banned upon creation
    this.type = type;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getType() {
    return this.type;
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