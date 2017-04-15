package mockexercise.realDB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SystemUser implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(length = 25, nullable = false)
  private String userName;
  
  @Column(length = 35,nullable = false)
  String password;

  public SystemUser() {}

  public SystemUser(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }
  
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUser() {
    return userName;
  }

  public void setUser(String user) {
    this.userName = user;
  }  
}
