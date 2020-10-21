package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import pokemon.golder.server.pms.domain.User;

public class UserListCommand implements Command {

  List<User> userList;

  public UserListCommand(List<User> list) {
    this.userList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    out.println("[유저 목록]");

    // 전체 목록을 조회할 때 `Iterator` 객체를 사용한다.
    // 만약 목록의 일부만 조회하면다면 인덱스를 직접 다루는 이전 방식을 사용해야 한다.
    Iterator<User> iterator = userList.iterator();

    while (iterator.hasNext()) {
      User user = iterator.next();
      out.printf("%d, %s, %d, %s\n",
          user.getNo(),
          user.getName(),
          user.getSignIn(),
          user.getRegisteredDate());
    }
  }

  public User findByName(String name) {
    for (int i = 0; i < userList.size(); i++) {
      User user = userList.get(i);
      if (user.getName().equals(name)) {
        return user;
      }
    }
    return null;
  }

}
