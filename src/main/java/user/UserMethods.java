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
  // limit user-permission & do exceptions (instanceOf etc.)
  // check when user latest logged in(dunno how)? william wrote about it
  //get a user by email?
  //Using user input + calling constructors from the different user-classes?
  public int findUser(String searchEmail) {
    if (activeUsers.size() == 0) {
      return -1;
    }
    for (int i = 0; i < activeUsers.size(); i++) {
      if (activeUsers.get(i).getEmail().equals(searchEmail)) {
        return i;
      }
    }
    return -1;
  }

  public boolean testMethod(String email, String password) {
    User userToTest = getUserByEmail(email); // Fetch user object if email exist else fetch null
    if(userToTest != null && userToTest.validatePassword(password)) { // Test if object is a user(or null) and then test if password is correct
      // Let user login!
    } else {
        // Wrong email or password
    }
    return true; // change this
  }

  public String createStudent(String email, String password, String firstName, String lastName, int graduationYear, String program) throws Exception {
    if (checkExistingEmail(email)) {
      throw new Exception("User is already registered, please try again.");
    }
    activeUsers.add(new Student(email, password, firstName, lastName, graduationYear, program));
    return "User registered successfully.";
  }

  public String createAdministrator(String email, String password, String firstName, String lastName, int graduationYear, String program) throws Exception {
    if (checkExistingEmail(email)) {
      throw new Exception("User is already registered, please try again.");
    }
    activeUsers.add(new Administrator(email, password, firstName, lastName, graduationYear, program));
    return "User registered successfully.";
  }

  public String createStaff(String email, String password, String firstName, String lastName, String department, String title) throws Exception {

    if (checkExistingEmail(email)) {
      throw new Exception("User is already registered, please try again.");
    }
    return "User registered successfully.";
  }
  /*
    public boolean userLogin(String email, String password) {

      if (checkExistingEmail(email) && validatePassword(password)) {
        return true;
      }
      return false;
    }
   */



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

  public int getIndex(int id, ArrayList<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) == id) {
        return i;
      }
    }
    return -1;
  }

  public User getUserByEmail(String email) {
    for (User user: activeUsers) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    return null;
  }






}