package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import pokemon.golder.server.pms.domain.User;
import pokemon.golder.server.util.Prompt;

public class UserDetailCommand implements Command {

  List<User> memberList;

  public UserDetailCommand(List<User> list) {
    this.memberList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      out.println("[회원 상세보기]");
      int no = Prompt.inputInt("번호? ", out, in);
      User member = findByNo(no);

      if (member == null) {
        out.println("해당 번호의 회원이 없습니다.");
        return;
      }

      out.printf("이름: %s\n", member.getName());
      out.printf("등록일: %s\n", member.getRegisteredDate());

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
