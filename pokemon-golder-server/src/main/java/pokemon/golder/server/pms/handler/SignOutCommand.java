package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import pokemon.golder.server.pms.domain.SignIn;
import pokemon.golder.server.util.Prompt;

public class SignOutCommand implements Command {

  SignIn signIn;

  public SignOutCommand (SignIn signIn) {
    this.signIn = signIn;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      if (signIn.getAdmin() == 3) {
        out.println("이미 로그아웃 되어있습니다.");
        out.println(" ");
        out.println();
        out.flush();
        return;
      }

      String request = Prompt.inputString("로그아웃하시겠습니까? (y/N) : ", out, in);
      out.println(" ");

      if (request.equalsIgnoreCase("y")) {
        signIn.setAdmin(3);
        signIn.setName(null);
        out.println("로그아웃 되었습니다.");
        out.println(" ");
        out.println();
        out.flush();
      } else {
        out.println("로그아웃이 취소되었습니다.");
        out.println(" ");
        out.println();
        out.flush();
      }

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }
}
