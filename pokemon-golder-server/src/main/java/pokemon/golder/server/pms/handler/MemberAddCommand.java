package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.util.Prompt;

public class MemberAddCommand implements Command {

  List<Member> memberList;

  public MemberAddCommand(List<Member> list) {
    this.memberList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in,
      Map<Long,Member> signInContext, long clientId, Member member1) {
    if (member1.getAdmin() != 1) {
      out.print("권한이 없습니다.");
      signInContext.put(clientId, member1);
      return;
    }
    try {
      out.println("[회원 등록]");

      Member member = new Member();
      member.setNo(Prompt.inputInt("번호? ", out, in));
      member.setName(Prompt.inputString("이름? ", out, in));
      member.setEmail(Prompt.inputString("이메일? ", out, in));
      member.setPassword(Prompt.inputInt("암호? ", out, in));
      member.setPhoto(Prompt.inputString("사진? ", out, in));
      member.setTel(Prompt.inputString("전화? ", out, in));
      member.setRegisteredDate(new java.sql.Date(System.currentTimeMillis()));

      memberList.add(member);
      signInContext.put(clientId, member1);

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }
}
