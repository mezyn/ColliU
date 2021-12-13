package user;

public class Administrator extends Student {


  public Administrator(String email, String password, String firstName, String lastName, int graduationYear, String program) throws Exception {
      super(email, password, firstName, lastName, graduationYear, program);
  }

  public void  banUser(boolean isBanned) {
    setAccountStatus(isBanned);
  }

  //Don't we need 'unbanUser' method as well?

}
