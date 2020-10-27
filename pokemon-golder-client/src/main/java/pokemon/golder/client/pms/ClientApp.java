package pokemon.golder.client.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import pokemon.golder.client.util.Prompt;

public class ClientApp {

  static String host;
  static int port;


  // 서버에서 이 클라이언트를 구분할 때 사용하는 번호이다.
  // => 0 번으로 서버에 요청하면, 서버가 신규 번호를 발급해 줄 것이다.
  static long clientId = 0;

  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("프로그램 사용법:");
      System.out.println("  java -cp ... ClientApp 서버주소 포트번호");
      System.exit(0);
    }

    host = args[0];
    port = Integer.parseInt(args[1]);


    while (true) {
      String input = Prompt.inputString("명령> ");
      if (input.equalsIgnoreCase("quit"))
        break;

      request(input);

      if (input.equalsIgnoreCase("stop"))
        break;
    }
    System.out.println("안녕!");


  }

  private static void request(String request) {
    // 클라이언트가 서버에 stop 명령을 보내면 다음 변수를 true로 변경한다.
    boolean stop = false;

    try (Socket socket = new Socket(host, port);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

      // => 서버에 클라이언트 아이디를 보낸다.
      out.println(clientId);
      out.flush();
      // => 서버에서 보낸 클라이언트 아이디를 읽는다.
      clientId = Long.parseLong(in.readLine());

      out.println(request);
      out.flush();

      receiveResponse(out, in);


      if (request.equalsIgnoreCase("stop")) {
        stop = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (stop) {
      // 서버를 멈추기 위해 그냥 접속했다가 끊는다.
      try (Socket socket = new Socket(host, port)) {
        // 아무것도 안한다.
        // 서버가 stop 할 기회를 주기 위함이다.
      } catch (Exception e) {
        // 아무것도 안한다.
      }
    }
  }

  private static void receiveResponse(PrintWriter out, BufferedReader in) throws Exception {
    while (true) {
      String response = in.readLine();
      if (response.length() == 0) {
        break;
      } else if (response.equals("!{}!")) {
        // 사용자로부터 값을 입력을 받아서 서버에 보낸다.
        out.println(Prompt.inputString(""));
        out.flush(); // 주의! 출력하면 버퍼에 쌓인다. 서버로 보내고 싶다면 flush()를 호출하라!
      } else {
        System.out.println(response);
      }
    }
  }
}
