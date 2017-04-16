package mockexercise.db;

import mockexercise.IUserFacade;
import mockexercise.LoginStatus;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class UserFacadeFakeTest {

  IUserFacade facade;

  @Before
  public void setup() {
     facade = new UserFacadeFake();
  }
  
  @Test
  public void authenticateOK(){
    //Given (in setup)
    //When
    LoginStatus res = facade.verifyUser("Jan", "abcde");
    //Then
    assertThat(res,is(LoginStatus.OK));
  }
  
  @Test
  public void authenticateValidUserWrongPW(){
    //Given (in setup)
    //When
    LoginStatus res = facade.verifyUser("Jan", "kfjdlsjaf");
    //Then
    assertThat(res,is(LoginStatus.INVALID_PASSWORD));
  }
  
  @Test
  public void authenticateNonExistingUser(){
    //Given (in setup)

    //When
    LoginStatus res = facade.verifyUser("xxxx", "kfjdlsjaf");
    
//Then
    assertThat(res,is(LoginStatus.UNKNOWN_USER));
  }

}
