package user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

/** Student class.
 *
 */
public class Student extends User {

  private int graduationYear;
  //private ArrayList<String> courses;
  //private ArrayList<String> interests;
  private String program;
  //private boolean isAdministrator;
  /** Constructor for Student class.
     *
     */

  //public Student(){} //default constructor

  public Student(String email, String password, String firstName, String lastName, int graduationYear, String program) throws Exception {
    super(email, password, firstName, lastName, 1);
   //add exceptions
    this.graduationYear = graduationYear;
    this.program = program;
  }

  public Student(String email, String password, String firstName, String lastName, int graduationYear, String program, int type) throws Exception {
    super(email, password, firstName, lastName, type);
    //add exceptions
    this.graduationYear = graduationYear;
    this.program = program;
  }

//Getters and setters
  public String getProgram (String email) {
    return program;
  }

  public void setprogram (String program) {
    this.program = program;
  }

  public int getGraduationYear(String email) {
    return this.graduationYear;
  }

  public void setGraduationYear(int graduationYear) {
    this.graduationYear =  graduationYear;
  }

  public void setProgram(String program) {
    program = this.program;
  }

  //public ArrayList<String> getCourses() {
  //  return courses;
  //}

  //public ArrayList<String> getInterests() {
  //  return this.interests;
  //}

}

