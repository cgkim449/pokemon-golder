package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.util.Prompt;

public class SignUpCommand implements Command {

  List<Member> memberList;

  public SignUpCommand(List<Member> list) {
    this.memberList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in,
      Map<Long,Member> signInContext, long clientId, Member client) {
    try {
      out.println("[회원 가입]");

      Member member = new Member();

      while(true) {
        String name = Prompt.inputString("아이디? ", out, in);
        if (findByName(name) != null) {
          String response = Prompt.inputString(
              "중복된 아이디입니다. 회원가입을 계속하시겠습니까?(y/N):", out, in);
          if (response.equalsIgnoreCase("y")) {
            continue;
          } else {
            out.println("회원가입을 취소합니다.");
            out.println(" ");
            signInContext.put(clientId, client);
            return;
          }
        } else {
          member.setName(name);
          break;
        }
      }

      member.setNo(memberList.size());
      member.setEmail(Prompt.inputString("이메일? ", out, in));
      member.setPassword(Prompt.inputInt("암호? ", out, in));
      member.setPhoto(Prompt.inputString("사진? ", out, in));
      member.setTel(Prompt.inputString("전화? ", out, in));
      member.setRegisteredDate(new java.sql.Date(System.currentTimeMillis()));
      memberList.add(member);

      out.println("회원가입이 완료되었습니다.");
      out.println(" ");
      signInContext.put(clientId, client);

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
