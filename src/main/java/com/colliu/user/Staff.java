package com.colliu.user;

import com.colliu.miscellaneous.Info;

/** Staff class.
 *
 */
  /*
  Staff class is a child class of User class.
  In our application, a staff is an employee at GU who work with education-relevant assignment, thus, has a right to
  create an com.colliu.colliu.event that students can participate.
  This class has only constructor for creating staff objects.
  Relevant methods can be found in UserMethods class in the same package.
  */

public class Staff extends User {

  /** Constructor for Staff class.
   *
   */

  public Staff(String email, String password, String firstName, String lastName) throws Exception{
    super(email, password, firstName, lastName);
  }
}
