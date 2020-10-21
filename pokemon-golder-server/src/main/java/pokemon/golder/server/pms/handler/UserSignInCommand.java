package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import pokemon.golder.server.pms.domain.Login;
import pokemon.golder.server.pms.domain.User;
import pokemon.golder.server.util.Prompt;

public class UserSignInCommand implements Command {

  List<User> userList;
  UserListCommand userListCommand;
  Login login;

  public UserSignInCommand(List<User> list, UserListCommand userListCommand, Login login) {
    this.userList = list;
    this.userListCommand = userListCommand;
    this.login = login;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      out.println("[로그인]");
      while (true) {
        User user = userListCommand.findByName(Prompt.inputString("이름? ", out, in));
        if ( user == null) {
          out.println("해당 이름의 유저가 없습니다.");
          if(Prompt.inputString("다시 입력하시겠습니까? (y/N): ", out, in).equalsIgnoreCase("y")) {
            continue;
          } else {
            out.println("취소합니다.");
            return;
          }
        } else {
          out.println("로그인 성공");
          login.setName(user.getName());
          login.setSignIn(1);
          break;
        }
      }
    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }
}
