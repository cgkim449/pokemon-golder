package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.util.Prompt;

public class SignOutCommand implements Command {

  Member member;
  List<Member> memberList;

  public SignOutCommand (Member member, List<Member> memberList) {
    this.member = member;
    this.memberList = memberList;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      //      if (member.getSignIn() == 0) {
      //        out.println("이미 로그아웃 되어있습니다.");
      //        out.println(" ");
      //        out.println();
      //        out.flush();
      //        return;
      //      }

      String request = Prompt.inputString("로그아웃하시겠습니까? (y/N) : ", out, in);
      out.println(" ");

      if (request.equalsIgnoreCase("y")) {
        member.setSignIn(0);
        member = findByName(member.getName());
        member.setSignIn(0);
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

  public Member findByName(String name) {
    for (int i = 0; i < memberList.size(); i++) {
      Member member = memberList.get(i);
      if (member.getName().equals(name)) {
        return member;
      }
    }
    return null;
  }
}
