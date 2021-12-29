package user;

  /*
  Administrator class is a child class of Student class.
  In our application, an administrator is a student at GU who act as a moderator in the system, e.g. banning user.
  This class has only constructor for creating administrator objects.
  Relevant methods can be found in UserMethods class in the same package.
  */

public class Administrator extends Student {

  //Constructor for administrator (type 2)
  public Administrator(String email, String password, String firstName, String lastName, int graduationYear, String program) throws Exception {
    super(email, password, firstName, lastName, graduationYear, program, 2);
  }


}
