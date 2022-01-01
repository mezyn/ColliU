package user;

import miscellaneous.Info;

public class Administrator extends Student {

  public Administrator(String email, String password, String firstName, String lastName, int graduationYear, String program) throws Exception {
    super(email, password, firstName, lastName, graduationYear, program, Info.TYPE_ADMIN);
  }


}
