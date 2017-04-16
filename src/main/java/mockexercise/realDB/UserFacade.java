package mockexercise.realDB;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import mockexercise.LoginStatus;
import mockexercise.IUserFacade;


public class UserFacade implements IUserFacade {

  EntityManagerFactory emf;
  
  public UserFacade(String puName) {
    emf = Persistence.createEntityManagerFactory(puName);
  }

  
  
  
  @Override
  public LoginStatus verifyUser(String userName, String pw) {
    EntityManager em = emf.createEntityManager();
    try{
      SystemUser user = em.find(SystemUser.class, userName);
      if(user == null){
        return LoginStatus.UNKNOWN_USER;
      }
       return user.getPassword().equals(pw) ? LoginStatus.OK : LoginStatus.INVALID_PASSWORD;
    }finally{
      em.close();
    }
  }
  
}
