package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.util.Prompt;

public class SignOutCommand implements Command {

  List<Member> memberList;

  public SignOutCommand (List<Member> memberList) {
    this.memberList = memberList;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in, 
      Map<Long,Member> signInContext, long clientId, Member member1) {
    try {
      if (member1.getSignIn() == 0) {
        out.println("이미 로그아웃 되어있습니다.");
        out.println(" ");
        signInContext.put(clientId, member1);
        return;
      }

      String request = Prompt.inputString("로그아웃하시겠습니까? (y/N) : ", out, in);
      out.println(" ");

      if (request.equalsIgnoreCase("y")) {
        Member member = findByName(member1.getName());
        member.setSignIn(0);
        member1.setSignIn(0);
        member1.setAdmin(0);
        member1.setNo(0);
        member1.setName(null);
        out.println("로그아웃 되었습니다.");
        out.println(" ");
        signInContext.put(clientId, member1);
      } else {
        out.println("로그아웃이 취소되었습니다.");
        out.println(" ");
        signInContext.put(clientId, member1);
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
