package pokemon.golder.server.pms.listener;

import java.util.List;
import java.util.Map;
import pokemon.golder.server.context.ApplicationContextListener;
import pokemon.golder.server.pms.domain.Board;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.pms.domain.Pokemon;
import pokemon.golder.server.pms.domain.Project;
import pokemon.golder.server.pms.domain.Task;
import pokemon.golder.server.pms.handler.BoardAddCommand;
import pokemon.golder.server.pms.handler.BoardDeleteCommand;
import pokemon.golder.server.pms.handler.BoardDetailCommand;
import pokemon.golder.server.pms.handler.BoardListCommand;
import pokemon.golder.server.pms.handler.BoardUpdateCommand;
import pokemon.golder.server.pms.handler.CalculatorCommand;
import pokemon.golder.server.pms.handler.HomeCommand;
import pokemon.golder.server.pms.handler.MemberAddCommand;
import pokemon.golder.server.pms.handler.MemberDeleteCommand;
import pokemon.golder.server.pms.handler.MemberDetailCommand;
import pokemon.golder.server.pms.handler.MemberListCommand;
import pokemon.golder.server.pms.handler.MemberUpdateCommand;
import pokemon.golder.server.pms.handler.PokemonAddCommand;
import pokemon.golder.server.pms.handler.PokemonDeleteCommand;
import pokemon.golder.server.pms.handler.PokemonDetailCommand;
import pokemon.golder.server.pms.handler.PokemonListCommand;
import pokemon.golder.server.pms.handler.PokemonUpdateCommand;
import pokemon.golder.server.pms.handler.ProjectAddCommand;
import pokemon.golder.server.pms.handler.ProjectDeleteCommand;
import pokemon.golder.server.pms.handler.ProjectDetailCommand;
import pokemon.golder.server.pms.handler.ProjectListCommand;
import pokemon.golder.server.pms.handler.ProjectUpdateCommand;
import pokemon.golder.server.pms.handler.SignInCommand;
import pokemon.golder.server.pms.handler.SignOutCommand;
import pokemon.golder.server.pms.handler.SignUpCommand;
import pokemon.golder.server.pms.handler.TaskAddCommand;
import pokemon.golder.server.pms.handler.TaskDeleteCommand;
import pokemon.golder.server.pms.handler.TaskDetailCommand;
import pokemon.golder.server.pms.handler.TaskListCommand;
import pokemon.golder.server.pms.handler.TaskUpdateCommand;

// 클라이언트 요청을 처리할 커맨드 객체를 준비한다.
public class RequestMappingListener implements ApplicationContextListener {

  @SuppressWarnings("unchecked")
  @Override
  public void contextInitialized(Map<String,Object> context) {

    // 옵저버가 작업한 결과를 맵에서 꺼낸다.
    List<Pokemon> pokemonList = (List<Pokemon>) context.get("pokemonList");
    List<Board> boardList = (List<Board>) context.get("boardList");
    List<Member> memberList = (List<Member>) context.get("memberList");
    List<Project> projectList = (List<Project>) context.get("projectList");
    List<Task> taskList = (List<Task>) context.get("taskList");

    context.put("/home", new HomeCommand());

    PokemonListCommand pokemonListCommand = new PokemonListCommand(pokemonList);
    context.put("/pokemon/add", new PokemonAddCommand(pokemonList));
    context.put("/pokemon/list", pokemonListCommand);
    context.put("/pokemon/detail", new PokemonDetailCommand(pokemonList));
    context.put("/pokemon/update", new PokemonUpdateCommand(pokemonList));
    context.put("/pokemon/delete", new PokemonDeleteCommand(pokemonList));

    MemberListCommand memberListCommand = new MemberListCommand(memberList);
    context.put("/member/add", new MemberAddCommand(memberList));
    context.put("/member/list", memberListCommand);
    context.put("/member/detail", new MemberDetailCommand(memberList));
    context.put("/member/update", new MemberUpdateCommand(memberList));
    context.put("/member/delete", new MemberDeleteCommand(memberList));
    context.put("/signIn", new SignInCommand(memberList));
    context.put("/signOut", new SignOutCommand(memberList));
    context.put("/signUp", new SignUpCommand(memberList));



    context.put("/board/add", new BoardAddCommand(boardList));
    context.put("/board/list", new BoardListCommand(boardList));
    context.put("/board/detail", new BoardDetailCommand(boardList));
    context.put("/board/update", new BoardUpdateCommand(boardList));
    context.put("/board/delete", new BoardDeleteCommand(boardList));

    context.put("/project/add", new ProjectAddCommand(projectList, memberListCommand));
    context.put("/project/list", new ProjectListCommand(projectList));
    context.put("/project/detail", new ProjectDetailCommand(projectList));
    context.put("/project/update", new ProjectUpdateCommand(projectList, memberListCommand));
    context.put("/project/delete", new ProjectDeleteCommand(projectList));

    context.put("/task/add", new TaskAddCommand(taskList, memberListCommand));
    context.put("/task/list", new TaskListCommand(taskList));
    context.put("/task/detail", new TaskDetailCommand(taskList));
    context.put("/task/update", new TaskUpdateCommand(taskList, memberListCommand));
    context.put("/task/delete", new TaskDeleteCommand(taskList));

    context.put("/calc", new CalculatorCommand());
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
  }
}
