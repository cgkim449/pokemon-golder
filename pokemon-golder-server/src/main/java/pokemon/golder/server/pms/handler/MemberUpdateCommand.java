package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.pms.domain.SignIn;
import pokemon.golder.server.util.Prompt;

public class MemberUpdateCommand implements Command {

  List<Member> memberList;
  SignIn signIn;

  public MemberUpdateCommand(List<Member> list, SignIn signIn) {
    this.signIn = signIn;
    this.memberList = list;
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
      out.println("[회원 변경]");
      int no = Prompt.inputInt("번호? ", out, in);
      Member member = findByNo(no);

      if (member == null) {
        out.println("해당 번호의 회원이 없습니다.");
        return;
      }

      String name = Prompt.inputString(
          String.format("이름(%s)? ", member.getName()), out, in);
      String email = Prompt.inputString(
          String.format("이메일(%s)? ", member.getEmail()), out, in);
      int password = Prompt.inputInt("암호? ", out, in);
      String photo = Prompt.inputString(
          String.format("사진(%s)? ", member.getPhoto()), out, in);
      String tel = Prompt.inputString(
          String.format("전화(%s)? ", member.getTel()), out, in);

      String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ", out, in);
      if (!response.equalsIgnoreCase("y")) {
        out.println("회원 변경을 취소하였습니다.");
        return;
      }

      member.setName(name);
      member.setEmail(email);
      member.setPassword(password);
      member.setPhoto(photo);
      member.setTel(tel);

      out.println("회원을 변경하였습니다.");

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }

  private Member findByNo(int no) {
    for (int i = 0; i < memberList.size(); i++) {
      Member member = memberList.get(i);
      if (member.getNo() == no) {
        return member;
      }
    }
    return null;
  }
}
