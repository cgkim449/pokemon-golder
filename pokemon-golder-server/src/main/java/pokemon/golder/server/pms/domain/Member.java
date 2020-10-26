package pokemon.golder.server.pms.domain;

import java.sql.Date;

//Member 클래스는 더이상 CsvObject를 구현할 필요가 없다.
//
public class Member {
  private int no;
  private String name;
  private String email;
  private int password;
  private String photo;
  private String tel;
  private Date registeredDate;
  private int admin;
  private int signIn;

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
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public int getPassword() {
    return password;
  }
  public void setPassword(int password) {
    this.password = password;
  }
  public String getPhoto() {
    return photo;
  }
  public void setPhoto(String photo) {
    this.photo = photo;
  }
  public String getTel() {
    return tel;
  }
  public void setTel(String tel) {
    this.tel = tel;
  }
  public Date getRegisteredDate() {
    return registeredDate;
  }
  public void setRegisteredDate(Date registeredDate) {
    this.registeredDate = registeredDate;
  }
  public int getAdmin() {
    return admin;
  }
  public void setAdmin(int admin) {
    this.admin = admin;
  }
  public int getSignIn() {
    return signIn;
  }
  public void setSignIn(int signIn) {
    this.signIn = signIn;
  }
}
