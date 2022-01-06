package com.colliu.user;

import com.colliu.PageController;
import com.colliu.miscellaneous.Info;
import java.util.ArrayList;

/**
 * This class contains methods relevant to handling the Users:
 * Student, Staff and Administrator objects.
  */

public class UserMethods {
  private final ArrayList<User> users;
  private final PageController master;
  public User currentUser;


  public UserMethods(PageController master) {
    this.master = master;
    users = master.loadUsers();
  }

  /**
   * CreateStudent, CreateAdministrator
   - @param email The email address of what new object to be created.
   - @param pass The password used to access the new object.
   - @param name The firstname to identify the object.
   - @param surname The lastname to indeitfy the object.
   - @param gradYear The expected graduation year of the user.
   - @param program The program to differentiate the information to display to this user.
   */

  public void createStudent(String email, String password, String firstName,
                            String lastName, int graduationYear, String program) throws Exception {
    users.add(new Student(email, password, firstName, lastName, graduationYear, program));
  }

  public void createAdministrator(String email, String password, String firstName,
                          String lastName, int graduationYear, String program) throws Exception {
    users.add(new Administrator(email, password, firstName, lastName, graduationYear, program));
  }

  /**
   - @param email The email address of what new object to be created.
   - @param password The password used to access the new object.
   - @param name The firstname to identify the object.
   - @param surname The lastname to identify the object.
   */

  public void createStaff(String email, String password, String firstName,
                          String lastName) throws Exception {
    users.add(new Staff(email, password, firstName, lastName));
  }

  /**
   * Search through the arraylist for an object with matching variable.
   - @param searchEmail The registered email identifier being sought.
   - @return An integer index in the array or -1 if there is no match.
   */
  public int getUserIndex(String searchEmail) {
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getEmail().equals(searchEmail)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Validates that the entered password matches the entered email.
   - @param password The password being tested.
   - @param email The email identifier for password validation.
   - @return True if password is valid, false if invalid or no users.
   */

  public boolean validatePassword(String email, String password) {
    return getUserByEmail(email).getPassword().equals(password);
  }


  /**
   - @param email The email identifier of which user object is requested.
   - @return An existing user object, or null if there is none matching the param.
   */

  public User getUserByEmail(String email) {
    int index = getUserIndex(email);
    return (index == -1 ? null : users.get(index));
  }

  /**
   * Method for checking the strength of a password.
   - @param password - The password being tested.
   - @return true if all checks pass, else false.
   */

  public boolean checkPasswordComplexity(String password) {
    // not null, not blank, not between 11-20 letters does not contain upper-, lowercase or numbers
    // no spaces in password.
    return !(password == null || password.isBlank() || (password.length() < 11
        || password.length() > 20) || password.matches("(.*[A-Z].*)")
        || password.matches("(.*[a-z].*)") || password.matches("(.*[0-9].*)")
        || !password.contains(" "));

  }

  /**
    Sets a reference to the currently logged-in user for easier handling.
   -param user is the reference being copied.
  */

  public void setLoggedInUser(User user) {
    currentUser = user;
  }

  /**
   - @return currentUser is the currently logged-in user or null.
   */
  public User getLoggedInUser() {
    return currentUser;
  }

  /**
   * This method toggles the user information between Student and Administrator objects.
   * All information is copied from one object into the new object.
   * If they are a student, their info is copied to a new Administrator object, and vice versa.
   - @param email The email identifier of the object being copied.
   */

  public void toggleAdminStatus(String email) {
    Student student = (Student) getUserByEmail(email);
    boolean isBanned = student.getAccountStatus();
    String password = student.getPassword();
    String firstName = student.getFirstName();
    String lastName = student.getLastName();
    String program = student.getProgram();
    int graduationYear = student.getGraduationYear();
    users.remove(getUserIndex(email));
    if (student instanceof Administrator) {
      try {
        createStudent(email, password, firstName, lastName, graduationYear, program);
      } catch (Exception e) {
        master.showError(Info.CANT_DEMOTE_ADMIN);
      }
    } else {
      try {
        createAdministrator(email, password, firstName, lastName, graduationYear, program);
      } catch (Exception e) {
        master.showError(Info.CANT_PROMOTE_STUDENT);
      }
    }
    Student newStudent = (Student) getUserByEmail(email);
    newStudent.setAccountStatus(isBanned);
    master.saveUsers();
  }

  /**
   - @param email The email of what user to ban.
   */

  public void banUser(String userToBan)  {
    getUserByEmail(userToBan).setAccountStatus(true);
    master.saveUsers();
  }

  /**
   - @param email The email of what user to unban.
   */

  public void unbanUser(String userToUnban)  {
    getUserByEmail(userToUnban).setAccountStatus(false);
    master.saveUsers();
  }

  /**
   - @return A copy of our arraylist of users.
   */

  public ArrayList<User> getAllUsers() {
    return users;
  }

  /**
   - @param email The email if what object to delete.
   */

  public void removeUser(String email) {
    users.remove(getUserIndex(email));
    master.saveUsers();
  }

  /**
   * Adds an object into an arraylist that stores object references.
   * This is called when undoing a deletion of an object.
   - @param user is the object to add back into the arraylist.
   */

  public void addUser(User user) {
    users.add(user);
  }

}