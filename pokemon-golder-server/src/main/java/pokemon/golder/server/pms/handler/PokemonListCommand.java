package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.pms.domain.Pokemon;

public class PokemonListCommand implements Command {

  List<Pokemon> pokemonList;

  public PokemonListCommand(List<Pokemon> list) {
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
    out.println("[포켓몬 목록]");

    // 전체 목록을 조회할 때 `Iterator` 객체를 사용한다.
    // 만약 목록의 일부만 조회하면다면 인덱스를 직접 다루는 이전 방식을 사용해야 한다.
    Iterator<Pokemon> iterator = pokemonList.iterator();

    while (iterator.hasNext()) {
      Pokemon pokemon = iterator.next();
      out.println("번호, 이름");
      out.printf("%d, %s\n",
          pokemon.getNo(),
          pokemon.getName());
    }
    signInContext.put(clientId, client);
  }

  public Pokemon findByName(String name) {
    for (int i = 0; i < pokemonList.size(); i++) {
      Pokemon pokemon = pokemonList.get(i);
      if (pokemon.getName().equals(name)) {
        return pokemon;
      }
    }
    return null;
  }

}
