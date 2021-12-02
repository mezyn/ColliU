package user;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Parameters for the User class.
 */
public abstract class User {

  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private LocalDateTime lastActive;


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

  public User(String email, String password, String firstName, String lastName) throws Exception {
    if (email == null || email.isBlank()) {
      throw new Exception("Email cannot be blank.");
    }
    if (!email.endsWith("@student.gu.se")) {
      throw new Exception("The email must be a GU email-address.");
    }
    //If-statement needs to contain all different shortenings of departments teachers can belong to

    if (!email.endsWith("@cse.gu.se")) {
      throw new Exception("The email must be a GU email-address.");
    }
    if (email.contains(" ")) {
      throw new Exception("There cannot be any blank spaces in the email.");
    }
    this.email = email;
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
    this.password = password;
    if (firstName == null || firstName.isBlank()) {
      throw new Exception("First name cannot be blank.");
    }
    if (firstName.matches("(.*[0-9].*)")) {
      throw new Exception("First name cannot contain any numbers.");
    }
    this.firstName = firstName;

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
    this.lastActive = lastActive;
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
public String getPassword(){
    return this.password;
}
  public void setPassword(String password) {
    this.password = password;
  }

  public String toString() {
    return firstName + " " + lastName;
  }
  public LocalDateTime getLastActive(){
    return this.lastActive;
  }

}