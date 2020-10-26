package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.pms.domain.SignIn;
import pokemon.golder.server.util.Prompt;

public class MemberAddCommand implements Command {

  List<Member> memberList;
  SignIn signIn;

  public MemberAddCommand(List<Member> list, SignIn signIn) {
    this.memberList = list;
    this.signIn = signIn;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    if (signIn.getAdmin() != 0) {
      out.print("권한이 없습니다.");
      out.println();
      out.flush();
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

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }
}
