package user;

import com.colliu.colliu.MasterController;
import java.util.ArrayList;

  // This class contains methods relevant to users; student, staff, administrator.

  public class UserMethods {
    private ArrayList<User> users;
    MasterController master;
    public User currentUser;


  public UserMethods(MasterController master) {
    this.master = master;
    users = master.loadUsers();
  }

  //create user and sub-types, check for existing users (email?), add / not add into arraylist
  // update name, update password, update graduationYear,
  // limit user-permission & do exceptions (instanceOf etc.)
  // check when user latest logged in(dunno how)? william wrote about it
  //get a user by email?
  //Using user input + calling constructors from the different user-classes?


  // Method for finding a user by his Email.
  public int findUser(String searchEmail) {
    if (users.size() == 0) {
      return -1;
    }
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getEmail().equals(searchEmail)) {
        return i;
      }
    }
    return -1;
    /*
    A cleaner way to do this is:
    return activeUsers.indexOf(searchEmail);
    this will return either the index of the arraylist or -1 if it doesn't exist
    // William.
     */
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
// Function for registering as student.
  public String createStudent(String email, String password, String firstName, String lastName, int graduationYear, String program) throws Exception {
    users.add(new Student(email, password, firstName, lastName, graduationYear, program));
    return "User registered successfully.";
  }
// Function for registering as administrator.
  public String createAdministrator(String email, String password, String firstName, String lastName, int graduationYear, String program) throws Exception {
    users.add(new Administrator(email, password, firstName, lastName, graduationYear, program));
    return "User registered successfully.";
  }
// Write comment here
  public String createStaff(String email, String password, String firstName, String lastName) throws Exception {
    users.add(new Staff(email, password, firstName, lastName));
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


// Checks if a user with an email as the input String exists.
  public boolean checkExistingEmail(String email) {

    for (User user : users) {
      if (user.getEmail().equals(email)) {
        return true;
      }
    }
    return false;
  }
//Method for validating password
//I'm not sure if the validatePassword method is logically correct.
//So if the password input exist in the system it validates it no matter which user does the password belongs to? -Mijin
  public boolean validatePassword(String password, String email) {

    for (User user : users) {
      if (user.getPassword().equals(password) && user.getEmail().equals(email)) {
        return true;
      }
    }

    return false;
  }
// Write comment here
  public int getIndex(int id, ArrayList<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) == id) {
        return i;
      }
    }
    return -1;
  }
// Method for getting user by his email
  public User getUserByEmail(String email) {
    for (User user: users) {
      if (user.getEmail().equals(email)) {
        return user; //returning the user corresponding to that email
      }
    }
    return null;
  }

  // Function for checking the strength of each user's password.
  public boolean checkPasswordComplexity(String password) {
    if (password == null || password.isBlank()) {
      return false;
    }
    if (password.length() < 11 || password.length() > 20) {
      return false;
    }
    if (!password.matches("(.*[A-Z].*)")) {
      return false;
    }
    if (!password.matches("(.*[a-z].*)")) {
      return false;
    }
    if (!password.matches("(.*[0-9].*)")) {
      return false;
    }
    if (password.contains(" ")) {
      return false;
    } else {
      return true;
    }
  }

  public void setLoggedInUser(User user) {
    currentUser = user;
  }

  public boolean getAccountStatus(String email) {
    return currentUser.getAccountStatus();
  }

  public User getLoggedInUser() {
    currentUser = getUserByEmail(currentUser.getEmail());
    return currentUser;
  }

  public int getCurrentUserType() {
    return currentUser.getType();
  }

  public void toggleAdminStatus (String email) throws Exception {
    Student student = (Student) getUserByEmail(email);
    boolean isBanned = student.getAccountStatus();
    String password = student.getPassword();
    String firstName = student.getFirstName();
    String lastName = student.getLastName();
    String program = student.getProgram();
    int graduationYear = student.getGraduationYear();
    users.remove(findUser(email));
    if (student.getType() == 2) {
      createStudent(email, password, firstName, lastName, graduationYear, program);
    } else {
      createAdministrator(email, password, firstName, lastName, graduationYear, program);
    }
    Student newStudent = (Student) getUserByEmail(email);
    newStudent.setAccountStatus(isBanned);
   master.saveUsers();
  }

  public void promoteStudent(String email) throws Exception {
    toggleAdminStatus(email);
  }

  public void demoteAdmin(String email) throws Exception {
    toggleAdminStatus(email);
  }

  public void banUser(String userToBan)  {
    getUserByEmail(userToBan).setAccountStatus(true);
    master.saveUsers();
  }

 // Function for unbanning a user.
  public void unbanUser(String userToUnban)  {
    getUserByEmail(userToUnban).setAccountStatus(false);
    master.saveUsers();
  }

  public ArrayList<User> getAllUsers() {
    return users;
  }

  public void removeUser(String email) {
    users.remove(findUser(email));
    master.saveUsers();
  }

  public void addUser(User user) {
    users.add(user);
  }

}
