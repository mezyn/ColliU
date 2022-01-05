package com.colliu.user;

import com.colliu.miscellaneous.Info;
  /*
  Administrator class is a child class of Student class.
  In our application, an administrator is a student at GU who act as a moderator in the system, e.g. banning com.colliu.colliu.user.
  This class has only constructor for creating administrator objects.
  Relevant methods can be found in UserMethods class in the same package.
  */

public class Administrator extends Student { // ADMINS ARE STUDENTS.

  //Constructor for administrator (type 2)
  public Administrator(String email, String password, String firstName, String lastName, int graduationYear, String program) throws Exception {
    super(email, password, firstName, lastName, graduationYear, program);
  }


}
