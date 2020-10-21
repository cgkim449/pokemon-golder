package pokemon.golder.server.pms.listener;

import java.util.List;
import java.util.Map;
import pokemon.golder.server.context.ApplicationContextListener;
import pokemon.golder.server.pms.domain.Board;
import pokemon.golder.server.pms.domain.Login;
import pokemon.golder.server.pms.domain.Project;
import pokemon.golder.server.pms.domain.Task;
import pokemon.golder.server.pms.domain.User;
import pokemon.golder.server.pms.handler.BoardAddCommand;
import pokemon.golder.server.pms.handler.BoardDeleteCommand;
import pokemon.golder.server.pms.handler.BoardDetailCommand;
import pokemon.golder.server.pms.handler.BoardListCommand;
import pokemon.golder.server.pms.handler.BoardUpdateCommand;
import pokemon.golder.server.pms.handler.CalculatorCommand;
import pokemon.golder.server.pms.handler.HelloCommand;
import pokemon.golder.server.pms.handler.ProjectAddCommand;
import pokemon.golder.server.pms.handler.ProjectDeleteCommand;
import pokemon.golder.server.pms.handler.ProjectDetailCommand;
import pokemon.golder.server.pms.handler.ProjectListCommand;
import pokemon.golder.server.pms.handler.ProjectUpdateCommand;
import pokemon.golder.server.pms.handler.TaskAddCommand;
import pokemon.golder.server.pms.handler.TaskDeleteCommand;
import pokemon.golder.server.pms.handler.TaskDetailCommand;
import pokemon.golder.server.pms.handler.TaskListCommand;
import pokemon.golder.server.pms.handler.TaskUpdateCommand;
import pokemon.golder.server.pms.handler.UserAddCommand;
import pokemon.golder.server.pms.handler.UserDeleteCommand;
import pokemon.golder.server.pms.handler.UserDetailCommand;
import pokemon.golder.server.pms.handler.UserListCommand;
import pokemon.golder.server.pms.handler.UserSignInCommand;
import pokemon.golder.server.pms.handler.UserSignOutCommand;
import pokemon.golder.server.pms.handler.UserUpdateCommand;

// 클라이언트 요청을 처리할 커맨드 객체를 준비한다.
public class RequestMappingListener implements ApplicationContextListener {

  @SuppressWarnings("unchecked")
  @Override
  public void contextInitialized(Map<String,Object> context) {

    // 옵저버가 작업한 결과를 맵에서 꺼낸다.
    Login login = (Login) context.get("login");

    List<Board> boardList = (List<Board>) context.get("boardList");
    List<User> userList = (List<User>) context.get("userList");
    List<Project> projectList = (List<Project>) context.get("projectList");
    List<Task> taskList = (List<Task>) context.get("taskList");

    context.put("/board/add", new BoardAddCommand(boardList));
    context.put("/board/list", new BoardListCommand(boardList));
    context.put("/board/detail", new BoardDetailCommand(boardList));
    context.put("/board/update", new BoardUpdateCommand(boardList));
    context.put("/board/delete", new BoardDeleteCommand(boardList));

    UserListCommand userListCommand = new UserListCommand(userList);
    context.put("/user/add", new UserAddCommand(userList));
    context.put("/user/list", userListCommand);
    context.put("/user/detail", new UserDetailCommand(userList));
    context.put("/user/update", new UserUpdateCommand(userList));
    context.put("/user/delete", new UserDeleteCommand(userList));

    context.put("/user/signIn", new UserSignInCommand(userList, userListCommand, login));
    context.put("/user/signOut", new UserSignOutCommand(userList, userListCommand, login));

    context.put("/project/add", new ProjectAddCommand(projectList, userListCommand));
    context.put("/project/list", new ProjectListCommand(projectList));
    context.put("/project/detail", new ProjectDetailCommand(projectList));
    context.put("/project/update", new ProjectUpdateCommand(projectList, userListCommand));
    context.put("/project/delete", new ProjectDeleteCommand(projectList));

    context.put("/task/add", new TaskAddCommand(taskList, userListCommand));
    context.put("/task/list", new TaskListCommand(taskList));
    context.put("/task/detail", new TaskDetailCommand(taskList));
    context.put("/task/update", new TaskUpdateCommand(taskList, userListCommand));
    context.put("/task/delete", new TaskDeleteCommand(taskList));

    context.put("/hello", new HelloCommand());

    context.put("/calc", new CalculatorCommand());
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
  }
}
