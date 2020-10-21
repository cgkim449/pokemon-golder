package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import pokemon.golder.server.pms.domain.User;
import pokemon.golder.server.util.Prompt;

public class UserDeleteCommand implements Command {

  List<User> memberList;

  public UserDeleteCommand(List<User> list) {
    this.memberList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      out.println("[회원 삭제]");
      int no = Prompt.inputInt("번호? ", out, in);
      int index = indexOf(no);

      if (index == -1) {
        out.println("해당 번호의 회원이 없습니다.");
        return;
      }

      String response = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ", out, in);
      if (!response.equalsIgnoreCase("y")) {
        out.println("회원 삭제를 취소하였습니다.");
        return;
      }

      memberList.remove(index);
      out.println("회원을 삭제하였습니다.");

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }

  private int indexOf(int no) {
    for (int i = 0; i < memberList.size(); i++) {
      User member = memberList.get(i);
      if (member.getNo() == no) {
        return i;
      }
    }
    return -1;
  }
}
