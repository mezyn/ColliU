package user;

import java.time.LocalDateTime;
import java.util.ArrayList;


/** Methods related to the users.
 *
 */
public class UserMethods {
  ArrayList<User> activeUsers;


  public UserMethods() {
    activeUsers = new ArrayList<User>();
  }
  //create user and sub-types, check for existing users (email?), add / not add into arraylist
  // update name, update password, update graduationYear,
  // limit user-permission & do exceptions (instanceOf etc)
  // check when user latest logged in(dunno how)? william wrote about it
  //Using user input + calling constructors from the different user-classes?



  public String createStudent(String email, String password, String firstName, String lastName, int graduationYear, String program) throws Exception {
    if (checkExistingEmail(email)) {
      throw new Exception("User is already registered, please try again.");
    }
    activeUsers.add(new Student(email, password, firstName, lastName, graduationYear, program));
    return "User registered successfully.";
  }

  public String createStaff(String email, String password, String firstName, String lastName, String department, String title) throws Exception {

    if (checkExistingEmail(email)) {
      throw new Exception("User is already registered, please try again.");
    }
    return "User registered successfully.";
  }

  public String userLogin(String email, String password) {
    boolean validLogin = false;
    String tryEmail = email;
    String tryPassword = password;

    if (checkExistingEmail(tryEmail) && (validatePassword(tryPassword))) {
      validLogin = true;
      return "Successful login!";
    } else {
      return "Account not found, please try again.";
    }
  }

  public boolean isAdministrator() {
    return false;
  }

  public boolean checkExistingEmail(String email) {

    for (User user : activeUsers) {
      if (user.getEmail().equals(email)) {
        return true;
      }
    }
    return false;
  }

  public boolean validatePassword(String password) {

    for (User user : activeUsers) {
      if (user.getPassword().equals(password)) {
        return true;
      }
    }
    return false;
  }

}