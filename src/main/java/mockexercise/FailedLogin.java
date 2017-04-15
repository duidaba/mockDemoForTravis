package mockexercise;

public class FailedLogin {

  private long timeForFirstFailedLogin;
  private int failedLogins;
  private final long allowedTimeBetweenFailings;

  /**
   *
   * @param time current time in ms for this login
   * @param allowedTimeBetweenFailingsMin Time ( in minutes) that must pass between
   * illegal logins for a user for us not to be suspicious
   */
  public FailedLogin(long time, int allowedTimeBetweenFailingsMin) {
    this.timeForFirstFailedLogin = time;
    this.failedLogins = 1;
    this.allowedTimeBetweenFailings = allowedTimeBetweenFailingsMin * 1000 * 60; //convert to ms
  }

  /**
   * Increment then number of failed logins, if time since last failed login is < allowedTimeBetweenFailings
   * Otherwise  it resets failedLogins to 1
   * @param time Time in ms
   * @return number of failed logins 
   */
  public int incrementFailedLogins(long time) {
    long t = time -timeForFirstFailedLogin;
    if (t < allowedTimeBetweenFailings) {
    //if (time - timeForFirstFailedLogin < allowedTimeBetweenFailings) {
      return (++failedLogins);
    }
    //Else reset failedLoginCounts
    failedLogins = 1;
    return failedLogins;
  }
}
