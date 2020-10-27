package pokemon.golder.server.pms.domain;

//Member 클래스는 더이상 CsvObject를 구현할 필요가 없다.
//
public class Pokemon {
  private int no;
  private String name;
  private int hp;
  private int attack;
  private int spAttack;
  private int defence;
  private int spDefence;
  private int speed;
  private int totalStat;
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
  public int getHp() {
    return hp;
  }
  public void setHp(int hp) {
    this.hp = hp;
  }
  public int getAttack() {
    return attack;
  }
  public void setAttack(int attack) {
    this.attack = attack;
  }
  public int getSpAttack() {
    return spAttack;
  }
  public void setSpAttack(int spAttack) {
    this.spAttack = spAttack;
  }
  public int getDefence() {
    return defence;
  }
  public void setDefence(int defence) {
    this.defence = defence;
  }
  public int getSpDefence() {
    return spDefence;
  }
  public void setSpDefence(int spDefence) {
    this.spDefence = spDefence;
  }
  public int getSpeed() {
    return speed;
  }
  public void setSpeed(int speed) {
    this.speed = speed;
  }
  public int getTotalStat() {
    return totalStat;
  }
  public void setTotalStat() {
    this.totalStat = hp + attack + spAttack + defence + spDefence + speed;
  }


}
