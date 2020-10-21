package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import pokemon.golder.server.pms.domain.User;
import pokemon.golder.server.util.Prompt;

public class UserUpdateCommand implements Command {

  List<User> memberList;

  public UserUpdateCommand(List<User> list) {
    this.memberList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      out.println("[회원 변경]");
      int no = Prompt.inputInt("번호? ", out, in);
      User member = findByNo(no);

      if (member == null) {
        out.println("해당 번호의 회원이 없습니다.");
        return;
      }

      String name = Prompt.inputString(
          String.format("이름(%s)? ", member.getName()), out, in);

      String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ", out, in);
      if (!response.equalsIgnoreCase("y")) {
        out.println("회원 변경을 취소하였습니다.");
        return;
      }

      member.setName(name);

      out.println("회원을 변경하였습니다.");

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }

  private User findByNo(int no) {
    for (int i = 0; i < memberList.size(); i++) {
      User member = memberList.get(i);
      if (member.getNo() == no) {
        return member;
      }
    }
    return null;
  }
}
