package user;


import java.util.ArrayList;
import java.util.Date;

/**
 * Parameters for the User class.
 */
public class User {

  private int type;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private ArrayList<Date> logins;
  private boolean accountBanned;
  private ArrayList<Integer> courses; // Store only course ID here. Then use the ID to find the actual course object.

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
    if (email == null || email.isBlank()) {
      throw new Exception("Email cannot be blank.");
    }
    if (!email.endsWith(".gu.se")) {
      throw new Exception("The email must be a GU email-address.");
    }
    //If-statement needs to contain all different shortenings of departments teachers can belong to
    //Can't we just write ".gu.se" & "gu.se" instead? i did some research and many of them have ""gu.se" and there are
    // tons of variation -Mijin
    if (email.contains(" ")) {
      throw new Exception("There cannot be any blank spaces in the email.");
    }
    if (password == null || password.isBlank()) {
      throw new Exception("Password cannot be blank.");
    }
    if (password.length() < 11 || password.length() > 20) {
      throw new Exception("Password must be between 12 and 20 characters.");
    }
    if (!password.matches("(.*[A-Z].*)")) {
      throw new Exception("Password must contain at least one uppercase letter.");
    }
    if (!password.matches("(.*[a-z].*)")) {
      throw new Exception("Password must contain at least one lowercase character.");
    }
    if (!password.matches("(.*[0-9].*)")) {
      throw new Exception("Password must contain at least one number.");
    }
    if (password.contains(" ")) {
      throw new Exception("Passwords cannot contain blank spaces.");
    }
    if (firstName == null || firstName.isBlank()) {
      throw new Exception("First name cannot be blank.");
    }
    if (firstName.matches("(.*[0-9].*)")) {
      throw new Exception("First name cannot contain any numbers.");
    }

    if (lastName == null || lastName.isBlank()) {
      throw new Exception("Last name cannot be blank.");
    }
    if (lastName.matches("(.*[0-9].*)")) {
      throw new Exception("Last name cannot contain any numbers.");
    }
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.email = email;
    this.logins = new ArrayList<>();     //Not sure how this works
    this.accountBanned = false;
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



  public String toString() {
    return firstName + " " + lastName;
  }

  public boolean validatePassword(String password) {
    return password.equals(this.password);
  }

  public void setAccountStatus(boolean bannedStatus) {
    this.accountBanned = bannedStatus;
  }

  public boolean getAccountStatus() {
    return this.accountBanned;
  }

  public void login() {
    logins.add(new Date());
  }

  // Returns all user logins as an array(normal array).
  public Date[] getLogins() {
    return logins.toArray(new Date[0]);
  }

  public void addCourse(int id) {
    courses.add(id);
  }

  public Integer[] getCourses() {
    return courses.toArray(new Integer[0]);
  }
}