package user;

import java.time.LocalDateTime;

/** Staff class.
 *
 */
public class Staff extends User {
  private String department;

  /** Constructor for the Staff class.
   *
     */
  public Staff(String email, String password, String firstName, String lastName, String department) throws Exception{
    super(email, password, firstName, lastName, 3);
    this.department = department;
  }
// Getters and setters

  public String getDepartment() {
    return this.department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }



}
