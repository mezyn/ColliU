package user;

import com.colliu.colliu.MasterController;
import java.util.ArrayList;

  // This class contains methods relevant to users - student, staff, administrator.
  public class UserMethods {
    private ArrayList<User> users;
    MasterController master;
    public User currentUser;


  public UserMethods(MasterController master) {
    this.master = master;
    users = master.loadUsers();
  }

  // Method for finding a user with his/her registered email address.
  public int findUser(String searchEmail) {
    if (users.size() == 0) { //If no user is registered in the system
      return -1;
    }
    for (int i = 0; i < users.size(); i++) { //If a user matches to the provided email address
      if (users.get(i).getEmail().equals(searchEmail)) {
        return i;
      }
    }
    return -1;
  }

  // Method for creating/registering as student.
  public String createStudent(String email, String password, String firstName, String lastName, int graduationYear, String program) throws Exception {
    users.add(new Student(email, password, firstName, lastName, graduationYear, program));
    return "User registered successfully.";
  }

  // Method for creating/registering as administrator.
  public String createAdministrator(String email, String password, String firstName, String lastName, int graduationYear, String program) throws Exception {
    users.add(new Administrator(email, password, firstName, lastName, graduationYear, program));
    return "User registered successfully.";
  }

  // Method for creating/registering as staff.
  public String createStaff(String email, String password, String firstName, String lastName) throws Exception {
    users.add(new Staff(email, password, firstName, lastName));
    return "User registered successfully.";
  }


  // Checks if a user with a provided email exists.
  public boolean checkExistingEmail(String email) {
    for (User user : users) {
      if (user.getEmail().equals(email)) {
        return true;
      }
    }
    return false;
  }


  // Method for validating password upon user login
  // It checks if a specific user's provided email and password match to the one in JSON file
  public boolean validatePassword(String password, String email) {
    for (User user : users) {
      if (user.getPassword().equals(password) && user.getEmail().equals(email)) {
        return true;
      }
    }
    return false;
  }


  // Method for getting user by her/his email
  public User getUserByEmail(String email) {
    for (User user: users) {
      if (user.getEmail().equals(email)) {
        return user; //returning the user corresponding to that email
      }
    }
    return null;
  }

  // Method for checking the strength of each user's password.
  public boolean checkPasswordComplexity(String password) {
    if (password == null || password.isBlank()) { //No empty password field is allowed
      return false;
    }
    if (password.length() < 11 || password.length() > 20) { //Too long or too short password is not allowed
      return false;
    }
    if (!password.matches("(.*[A-Z].*)")) { //Password should contain at least one upper case letter
      return false;
    }
    if (!password.matches("(.*[a-z].*)")) { //Password should contain at least one lower case letter
      return false;
    }
    if (!password.matches("(.*[0-9].*)")) { //Password should contain at least one numerical digit
      return false;
    }
    if (password.contains(" ")) { //Password should not contain en empty space
      return false;
    } else {
      return true;
    }
  }

  /*
  Get/Set a logged-in user so that the system can work for the specific user
  */
  public void setLoggedInUser(User user) {
    currentUser = user;
  }

  public User getLoggedInUser() {
    currentUser = getUserByEmail(currentUser.getEmail());
    return currentUser;
  }


  /*
  This method assigns a user to an administrator position and vice versa by using polymorphism.
  The methods get and keep all the information that current User object has, and create a new administrator object
  when the user with the provided email is not an ordinary, non-administrator user.
  Likewise, the methods get and keep all the information that current User object has, and create a new student object
  when the user with the provided email is an administrator user.
  */
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

  //Promote student to administrator
  public void promoteStudent(String email) throws Exception {
    toggleAdminStatus(email);
  }

  //Demote administrator to student
  public void demoteAdmin(String email) throws Exception {
    toggleAdminStatus(email);
  }


  //Method for banning a user.
  public void banUser(String userToBan)  {
    getUserByEmail(userToBan).setAccountStatus(true);
    master.saveUsers();
  }

  //Method for banning a user.
  public void unbanUser(String userToUnban)  {
    getUserByEmail(userToUnban).setAccountStatus(false);
    master.saveUsers();
  }

  // Get all users in an ArrayList
  public ArrayList<User> getAllUsers() {
    return users;
  }


  // Remove a specific user from the system
  public void removeUser(String email) {
    users.remove(findUser(email));
    master.saveUsers();
  }

  // Add a specific user to the system
  public void addUser(User user) {
    users.add(user);
  }

}