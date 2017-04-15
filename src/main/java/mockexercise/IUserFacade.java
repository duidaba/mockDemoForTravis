package mockexercise;

import mockexercise.LoginStatus;


public interface IUserFacade {

  LoginStatus verifyUser(String user, String pw);
  
}
