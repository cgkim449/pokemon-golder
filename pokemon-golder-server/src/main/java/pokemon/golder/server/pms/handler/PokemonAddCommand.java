package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.pms.domain.Pokemon;
import pokemon.golder.server.util.Prompt;

public class PokemonAddCommand implements Command {

  List<Pokemon> pokemonList;

  public PokemonAddCommand(List<Pokemon> list) {
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
      out.println("[포켓몬 등록]");

      Pokemon pokemon = new Pokemon();
      pokemon.setNo(Prompt.inputInt("번호? ", out, in));
      pokemon.setName(Prompt.inputString("이름? ", out, in));
      pokemon.setHp(Prompt.inputInt("hp? ", out, in));
      pokemon.setAttack(Prompt.inputInt("공격력? ", out, in));
      pokemon.setSpAttack(Prompt.inputInt("특수공격력? ", out, in));
      pokemon.setDefence(Prompt.inputInt("방어력? ", out, in));
      pokemon.setSpDefence(Prompt.inputInt("특수방어력? ", out, in));
      pokemon.setSpeed(Prompt.inputInt("스피드? ", out, in));
      pokemon.setTotalStat();

      pokemonList.add(pokemon);

      signInContext.put(clientId, client);

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }
}
