package pokemon.golder.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class HelloCommand implements Command {

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    out.println("안녕하세요!");
  }
}
