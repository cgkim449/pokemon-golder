package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import pokemon.golder.server.pms.domain.Member;

public class MemberListCommand implements Command {

  List<Member> memberList;
  Member member;

  public MemberListCommand(List<Member> list, Member member) {
    this.member = member;
    this.memberList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    //    if (member.getAdmin() != 1) {
    //      out.print("권한이 없습니다.");
    //      out.println();
    //      out.flush();
    //      return;
    //    }
    out.println("[회원 목록]");

    // 전체 목록을 조회할 때 `Iterator` 객체를 사용한다.
    // 만약 목록의 일부만 조회하면다면 인덱스를 직접 다루는 이전 방식을 사용해야 한다.
    Iterator<Member> iterator = memberList.iterator();

    out.println(member.getNo());
    out.println(member.getName());
    out.println(member.getPassword());
    out.println(member.getRegisteredDate());

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
