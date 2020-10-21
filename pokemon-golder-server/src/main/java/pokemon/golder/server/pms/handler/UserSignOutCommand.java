package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import pokemon.golder.server.pms.domain.Login;
import pokemon.golder.server.pms.domain.User;
import pokemon.golder.server.util.Prompt;

public class UserSignOutCommand implements Command {

  List<User> userList;
  UserListCommand userListCommand;
  Login login;

  public UserSignOutCommand(List<User> list, UserListCommand userListCommand, Login login) {
    this.userList = list;
    this.userListCommand = userListCommand;
    this.login = login;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      out.println(login.getName());
      out.println(login.getSignIn());
      out.println("[로그아웃]");
      if(Prompt.inputString("정말 로그아웃하시겠습니까? (y/N): ", out, in).equalsIgnoreCase("y")) {
        out.printf("%s 로그아웃 성공\n", login.getName());
        login.setSignIn(0);
      } else {
        out.println("취소합니다.");
        return;
      }

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }
}
