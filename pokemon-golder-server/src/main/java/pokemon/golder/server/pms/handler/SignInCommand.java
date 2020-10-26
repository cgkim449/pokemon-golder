package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.util.Prompt;

public class SignInCommand implements Command {

  Member member;
  List<Member> memberList;

  public SignInCommand (Member member, List<Member> memberList) {
    this.member = member;
    this.memberList = memberList;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      Member member1;

      out.println("[로그인] ");

      if (member.getSignIn() == 1) {
        out.println("이미 로그인 되어있습니다.");
        out.println(" ");
        out.println();
        out.flush();
        return;
      }

      while (true) {
        String name = Prompt.inputString("아이디를 입력해주세요 : ", out, in);
        if (findByName(name) == null) {
          String response = Prompt.inputString(
              "없는 아이디입니다. 계속하시겠습니까?(y/N):", out, in);
          if (response.equalsIgnoreCase("y")) {
            continue;
          } else {
            out.println("로그인을 취소합니다.");
            out.println(" ");
            out.flush();
            return;
          }
        } else {
          member1 = findByName(name);
          break;
        }
      }


      while(true) {
        if (member1.getPassword() != Prompt.inputInt("비밀번호 : ", out, in)) {
          String response = Prompt.inputString(
              "비밀번호가 틀렸습니다. 계속하시겠습니까?(y/N):", out, in);
          if (response.equalsIgnoreCase("y")) {
            continue;
          } else {
            out.println("로그인을 취소합니다.");
            out.println(" ");
            out.flush();
            return;
          }
        } else {
          break;
        }
      }
      out.println(" ");
      out.printf("%s님 안녕하세요.\n", member1.getName());
      out.println(" ");

      member.setSignIn(1);
      member.setName(member1.getName());
      member.setNo(member1.getNo());
      member.setAdmin((member1.getNo()));

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
