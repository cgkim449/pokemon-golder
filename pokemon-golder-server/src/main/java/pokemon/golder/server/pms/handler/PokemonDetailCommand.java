package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.pms.domain.Pokemon;
import pokemon.golder.server.util.Prompt;

public class PokemonDetailCommand implements Command {

  List<Pokemon> pokemonList;

  public PokemonDetailCommand(List<Pokemon> list) {
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
      out.println("[포켓몬 상세보기]");
      int no = Prompt.inputInt("번호? ", out, in);
      Pokemon pokemon = findByNo(no);

      if (pokemon == null) {
        out.println("해당 번호의 포켓몬이 없습니다.");
        signInContext.put(clientId, client);
        return;
      }

      out.printf("번호: %d\n", pokemon.getNo());
      out.printf("이름: %s\n", pokemon.getName());
      out.printf("HP: %d\n", pokemon.getAttack());
      out.printf("공격력: %d\n", pokemon.getAttack());
      out.printf("특수공격력: %d\n", pokemon.getSpAttack());
      out.printf("방어력: %d\n", pokemon.getSpDefence());
      out.printf("특수방어력: %d\n", pokemon.getSpDefence());
      out.printf("스피드: %d\n", pokemon.getSpeed());
      out.printf("종족값 총합: %d\n", pokemon.getTotalStat());
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
