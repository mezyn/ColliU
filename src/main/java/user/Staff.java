package user;

/** Staff class.
 *
 */
public class Staff extends User {

  /** Constructor for Staff class.
   *
   */

  public Staff(String email, String password, String firstName, String lastName) throws Exception{
    super(email, password, firstName, lastName, 3);
  }
}
