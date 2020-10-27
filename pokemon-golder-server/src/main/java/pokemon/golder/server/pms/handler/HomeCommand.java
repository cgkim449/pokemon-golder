package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;
import pokemon.golder.server.pms.domain.Member;

public class HomeCommand implements Command {

  @Override
  public void execute(PrintWriter out, BufferedReader in, Map<Long, Member> signInContext,
      long clientId, Member client) {

    out.println(" ");
    out.println("Pokemon Golder");
    out.println(" ");
    out.println("로그인 :'/signIn'");
    out.println("로그아웃 : '/signOut");
    out.println("회원가입 : '/signUp");
    out.println(" ");
    signInContext.put(clientId, client);
  }

}
