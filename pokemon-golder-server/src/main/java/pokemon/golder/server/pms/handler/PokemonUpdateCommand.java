package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.pms.domain.Pokemon;
import pokemon.golder.server.util.Prompt;

public class PokemonUpdateCommand implements Command {

  List<Pokemon> pokemonList;

  public PokemonUpdateCommand(List<Pokemon> list) {
    this.pokemonList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in,
      Map<Long,Member> signInContext, long clientId, Member client) {
    if (client.getAdmin() != 1) {
      out.print("권한이 없습니다.");
      signInContext.put(clientId, client);
      return;
    }
    try {
      out.println("[포켓몬 변경]");
      int no = Prompt.inputInt("번호? ", out, in);

      Pokemon pokemon = findByNo(no);

      if (pokemon == null) {
        out.println("해당 번호의 포켓몬이 없습니다.");
        signInContext.put(clientId, client);
        return;
      }

      no = Prompt.inputInt(
          String.format("번호(%d)? ", pokemon.getNo()), out, in);
      String name = Prompt.inputString(
          String.format("이름(%s)? ", pokemon.getName()), out, in);
      int hp = Prompt.inputInt(
          String.format("Hp(%d)? ", pokemon.getHp()), out, in);
      int attack = Prompt.inputInt(
          String.format("공격력(%d)? ", pokemon.getAttack()), out, in);
      int spAttack = Prompt.inputInt(
          String.format("특수공격력(%d)? ", pokemon.getSpAttack()), out, in);
      int defence = Prompt.inputInt(
          String.format("방어력(%d)? ", pokemon.getDefence()), out, in);
      int spDefence = Prompt.inputInt(
          String.format("특수방어력(%d)? ", pokemon.getSpDefence()), out, in);
      int speed = Prompt.inputInt(
          String.format("스피드(%d)? ", pokemon.getSpeed()), out, in);

      String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ", out, in);
      if (!response.equalsIgnoreCase("y")) {
        out.println("포켓몬 변경을 취소하였습니다.");
        signInContext.put(clientId, client);
        return;
      }

      pokemon.setNo(no);
      pokemon.setName(name);

      pokemon.setHp(hp);
      pokemon.setAttack(attack);
      pokemon.setSpAttack(spAttack);
      pokemon.setDefence(defence);
      pokemon.setSpDefence(spDefence);
      pokemon.setSpeed(speed);
      pokemon.setTotalStat();

      out.println("포켓몬을 변경하였습니다.");
      signInContext.put(clientId, client);

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }

  private Pokemon findByNo(int no) {
    for (int i = 0; i < pokemonList.size(); i++) {
      Pokemon pokemon = pokemonList.get(i);
      if (pokemon.getNo() == no) {
        return pokemon;
      }
    }
    return null;
  }
}
