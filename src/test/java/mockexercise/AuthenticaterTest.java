package mockexercise;

import mockexercise.db.UserFacadeFake;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Matchers;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AuthenticaterTest {

  public Authenticator makeAuthenticator(){
     return  new Authenticator(new UserFacadeFake());
  }
  
 public Authenticator makeSpyAuthenticator(){
    Authenticator authenticater = spy(Authenticator.class);
    authenticater.setUsers(new UserFacadeFake());
    return authenticater;
 }
  
  @Test
  public void testAuthenticateUserValidPassword() {
    //Given
    Authenticator authenticater = makeAuthenticator();
    //When
    boolean res = authenticater.authenticateUser("Jan", "abcde", 0);
    //Then
    assertThat(res, is(true));
  }

  @Test
  public void testAuthenticateUserInValidPassword() {
    //Given
    Authenticator authenticater = makeAuthenticator();
    //When
    boolean res = authenticater.authenticateUser("Jan", "fido", 0);
    //Then
    assertThat(res, is(false));
  }

  @Test
  public void testAuthenticateNonExistingUser() {
    //Given
    Authenticator authenticater = makeAuthenticator();
    //When
    boolean res = authenticater.authenticateUser("xxxx", "fido", 0);
    //Then
    assertThat(res, is(false));
  }

  @Test
  public void testAuthenticateEmailSentOnce() {
    //Given
    Authenticator authenticater = makeSpyAuthenticator();
    doNothing().when(authenticater).sendMail(Matchers.anyString());

    long timeForFirstTry = System.currentTimeMillis();

    //When
    boolean res1 = authenticater.authenticateUser("Jan", "fido", timeForFirstTry);
    boolean res2 = authenticater.authenticateUser("Jan", "fido", timeForFirstTry + 1000 * 60 * 10);
    boolean res3 = authenticater.authenticateUser("Jan", "fido", timeForFirstTry + 1000 * 60 * 20);
    
    //Then
    assertThat(res1, is(false));
    assertThat(res2, is(false));
    assertThat(res3, is(false));
    verify(authenticater, times(1)).sendMail(Matchers.anyString());
  }

  @Test
  public void testAuthenticateExistingUserInvalidPW_EmailNotSent() {
    //Given
    Authenticator authenticater = makeSpyAuthenticator();
    //org.mockito.Mockito.doN
    doNothing().when(authenticater).sendMail("Jan");
    
    long timeForFirstTry = System.currentTimeMillis();

    //When
    authenticater.authenticateUser("Jan", "fido", timeForFirstTry);
    authenticater.authenticateUser("Jan", "fido", timeForFirstTry + 1000 * 60 * 10);
    
    //Wait more than 30 min before next failing try
    authenticater.authenticateUser("Jan", "fido", timeForFirstTry + 1000 * 60 * 35);

    //Then
    verify(authenticater, times(0)).sendMail("Jan");
  }

  @Test
  public void testAuthenticateEmailSentTwice() {
    //Given
    Authenticator authenticater = makeSpyAuthenticator();
    doNothing().when(authenticater).sendMail(Matchers.anyString());

    long timeForFirstTry = System.currentTimeMillis();

    //When
    authenticater.authenticateUser("Jan", "fido", timeForFirstTry);
    authenticater.authenticateUser("Jan", "fido", timeForFirstTry + 1000 * 60 * 10);
    authenticater.authenticateUser("Jan", "fido", timeForFirstTry + 1000 * 60 * 20);
    authenticater.authenticateUser("Jan", "fido", timeForFirstTry + 1000 * 60 * 10);

    //Then
    verify(authenticater, times(2)).sendMail(Matchers.anyString());
  }
}
