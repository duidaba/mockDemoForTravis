package mockexercise;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

public class FailedLoginTest {
  
  public FailedLoginTest() {
  }

  @Test
  public void testIncrementFailedLoginsToShortTime() {
    //Given
    long startTime = System.currentTimeMillis();
    FailedLogin failedLogin = new FailedLogin(startTime, 10);
    //When
    int failCount1 = failedLogin.incrementFailedLogins(startTime+60*1000);
    int failCount2 = failedLogin.incrementFailedLogins(startTime+60*1000);
    //Then
    assertThat(failCount1, is(2));
    assertThat(failCount2, is(3));
  }
  
  @Test
  public void testIncrementFailedLoginsValidTimeBetweenTries() {
    //Given
    long startTime = System.currentTimeMillis();
    FailedLogin failedLogin = new FailedLogin(startTime, 1);
    //When
    int failCount1 = failedLogin.incrementFailedLogins(startTime+60*1000*2);
    //Then
    assertThat(failCount1, is(1));
  }
  
}
