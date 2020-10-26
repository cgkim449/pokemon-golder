package pokemon.golder.server.pms.domain;

public class SignIn {

  private String name;
  private int admin =3;

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getAdmin() {
    return admin;
  }
  public void setAdmin(int admin) {
    this.admin = admin;
  }

}