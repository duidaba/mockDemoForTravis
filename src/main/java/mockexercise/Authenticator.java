package mockexercise;

import java.util.HashMap;
import java.util.Map;
import mockexercise.realDB.UserFacade;

public class Authenticator {

  private final int TIME_BETWEEN_FAILED_LOGIN = 30; //minutes  (hardcoded in this ex)
  private final String MAIL = "admin@aaa.dk"; //mail address to send warnings (hardcoded in this ex)
  IUserFacade users;
  Map<String, FailedLogin> usersWithFailingLogins = new HashMap();

  public Authenticator(IUserFacade users) {
    this.users = users;
  }
  public Authenticator() {}
  
  public void setUsers(IUserFacade users){
    this.users = users;
  }

  /**
   * 
   * @param user User name
   * @param pw Passord 
   * @param loginTime NOW, obtained from a call to System.currentTimeMillis()
   * @return true if user is authenticated, otherwise false
   */
  public boolean authenticateUser(String user, String pw, long loginTime) {
    LoginStatus status = users.verifyUser(user, pw);
    if (status == LoginStatus.UNKNOWN_USER) {
      return false;
    }
    if (status == LoginStatus.OK) {
      //If there were previous failed logins, remove them
      usersWithFailingLogins.remove(user);
      return true;
    }
    //Must be a Failed Login
    if (usersWithFailingLogins.containsKey(user)) {
      int failedLogins = usersWithFailingLogins.get(user).incrementFailedLogins(loginTime);
      if (failedLogins >= 3) {
        sendMail(user);
      }
    } else {
      usersWithFailingLogins.put(user, new FailedLogin(loginTime, TIME_BETWEEN_FAILED_LOGIN));
    }
    return false;
  }

  void sendMail(String user) {
    System.out.println("####################################################################");
    System.out.println("This simulates sending a mail to:" + MAIL + "\nFailed logins for user:  " + user);
    System.out.println("####################################################################");
  }

  public static void main(String[] args) {
    Authenticator authenticater = new Authenticator(new UserFacade("pu_localDB"));
    //Authenticator authenticater = new Authenticator(new UserFacade("pu_mySql_travis_Integration"));
    System.out.println(authenticater.authenticateUser("Jan", "abcde", System.currentTimeMillis()));
    System.out.println(authenticater.authenticateUser("Jan", "afdds", System.currentTimeMillis()));
    System.out.println(authenticater.authenticateUser("Jan", "abcfsdde", System.currentTimeMillis()));
    System.out.println(authenticater.authenticateUser("Jan", "abcfddde", System.currentTimeMillis()));
  }

}
