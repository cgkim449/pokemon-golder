package pokemon.golder.server.pms.domain;

import java.sql.Date;

//Member 클래스는 더이상 CsvObject를 구현할 필요가 없다.
//
public class User {
  private int no;
  private String name;
  private Date registeredDate;
  private int signIn=0;

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Date getRegisteredDate() {
    return registeredDate;
  }
  public void setRegisteredDate(Date registeredDate) {
    this.registeredDate = registeredDate;
  }
  public int getSignIn() {
    return signIn;
  }
  public void setSignIn(int signIn) {
    this.signIn = signIn;
  }
}
