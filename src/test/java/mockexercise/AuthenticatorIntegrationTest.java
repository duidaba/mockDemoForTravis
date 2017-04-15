package mockexercise;

import mockexercise.realDB.UserFacade;
import static org.mockito.Mockito.spy;

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
