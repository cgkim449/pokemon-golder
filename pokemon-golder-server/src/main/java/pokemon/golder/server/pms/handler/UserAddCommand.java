package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import pokemon.golder.server.pms.domain.User;
import pokemon.golder.server.util.Prompt;

public class UserAddCommand implements Command {

  List<User> userList;

  public UserAddCommand(List<User> list) {
    this.userList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      //      out.println("지금은 몇시인가?");
      out.println("포켓몬 세계에 잘왔다");
      out.println("내 이름은 엄박사");

      User user = new User();
      user.setName(Prompt.inputString("그럼 슬슬 네 이름을 가르쳐다오:", out, in));
      out.printf("%s! 준비는 되었는가\n", user.getName());
      out.println("포켓몬스터 세계가 너를 기다리고 있다");
      out.println("그럼 이따가 연구소에서 보자");
      user.setNo(userList.size() + 1);
      user.setRegisteredDate(new java.sql.Date(System.currentTimeMillis()));
      //      user.setSignIn(1);
      userList.add(user);

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s", e.getMessage());
      e.printStackTrace();
    }
  }
}
