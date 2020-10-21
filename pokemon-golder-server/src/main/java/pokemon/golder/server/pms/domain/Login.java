package pokemon.golder.server.pms.domain;

//Member 클래스는 더이상 CsvObject를 구현할 필요가 없다.
//
public class Login {
  private String name;
  private int signIn;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getSignIn() {
    return signIn;
  }
  public void setSignIn(int signIn) {
    this.signIn = signIn;
  }

}
