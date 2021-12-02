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
  public Student (String email, String password, String firstName, String lastName, int graduationYear, String program) throws Exception {

    super();
    this.graduationYear = graduationYear;
    //this.courses = courses;
    //this.interests = interests;
    this.program = program;
    //this.isAdministrator = isAdministrator;

  }


  public String getProgram() {
    return program;
  }

  public int getGraduationYear() {
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

