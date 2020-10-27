package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.pms.domain.Pokemon;
import pokemon.golder.server.util.Prompt;

public class PokemonDeleteCommand implements Command {

  List<Pokemon> pokemonList;

  public PokemonDeleteCommand(List<Pokemon> list) {
    this.pokemonList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in,
      Map<Long,Member> signInContext, long clientId, Member client) {
    if (client.getAdmin() != 1) {
      out.println("권한이 없습니다.");
      signInContext.put(clientId, client);
      return;
    }
    try {
      out.println("[포켓몬 삭제]");
      int no = Prompt.inputInt("번호? ", out, in);
      int index = indexOf(no);

      if (index == -1) {
        out.println("해당 번호의 포켓몬이 없습니다.");
        signInContext.put(clientId, client);
        return;
      }

      String response = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ", out, in);
      if (!response.equalsIgnoreCase("y")) {
        out.println("포켓몬 삭제를 취소하였습니다.");
        signInContext.put(clientId, client);
        return;
      }

      pokemonList.remove(index);
      out.println("포켓몬을 삭제하였습니다.");
      signInContext.put(clientId, client);

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }

  private int indexOf(int no) {
    for (int i = 0; i < pokemonList.size(); i++) {
      Pokemon pokemon = pokemonList.get(i);
      if (pokemon.getNo() == no) {
        return i;
      }
    }
    return -1;
  }
}
