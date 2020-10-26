package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import pokemon.golder.server.pms.domain.Member;

public class MemberListCommand implements Command {

  List<Member> memberList;

  public MemberListCommand(List<Member> list) {
    this.memberList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in,
      Map<Long,Member> signInContext, long clientId, Member member1) {
    if (member1.getAdmin() != 1) {
      out.println("권한이 없습니다.");
      signInContext.put(clientId, member1);
      return;
    }
    out.println("[회원 목록]");

    // 전체 목록을 조회할 때 `Iterator` 객체를 사용한다.
    // 만약 목록의 일부만 조회하면다면 인덱스를 직접 다루는 이전 방식을 사용해야 한다.
    Iterator<Member> iterator = memberList.iterator();

    while (iterator.hasNext()) {
      Member member = iterator.next();
      out.println("번호, 이름, 이메일, 전화번호, 가입일, 관리자, 로그인상태");
      out.printf("%d, %s, %s, %s, %s, %d, %d\n",
          member.getNo(),
          member.getName(),
          member.getEmail(),
          member.getTel(),
          member.getRegisteredDate(),
          member.getAdmin(),
          member.getSignIn());
    }
    signInContext.put(clientId, member1);
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
