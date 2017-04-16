package mockexercise;

import mockexercise.realDB.UserFacade;
import static org.mockito.Mockito.spy;

/**
 * Test with either a local database, or MySQL on travis-CI if running here
 * @author plaul1
 */
public class AuthenticatorIntegrationTest extends AuthenticaterTest {

  private String getPuName(){
    if (System.getenv("TRAVIS")!= null) {
      return "pu_mySql_travis_Integration";
    }
    return "pu_localDB";
  }
  
  @Override
  public Authenticator makeAuthenticator() {
    return new Authenticator(new UserFacade(getPuName()));
    
  }

  @Override
  public Authenticator makeSpyAuthenticator() {
    Authenticator authenticater = spy(Authenticator.class);
    authenticater.setUsers(new UserFacade(getPuName()));
    return authenticater;
  }

}
