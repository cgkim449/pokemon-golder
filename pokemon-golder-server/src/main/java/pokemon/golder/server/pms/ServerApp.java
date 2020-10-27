package pokemon.golder.server.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import pokemon.golder.server.context.ApplicationContextListener;
import pokemon.golder.server.pms.domain.Member;
import pokemon.golder.server.pms.handler.Command;
import pokemon.golder.server.pms.listener.AppInitListener;
import pokemon.golder.server.pms.listener.DataHandlerListener;
import pokemon.golder.server.pms.listener.RequestMappingListener;

public class ServerApp {

  // 클라이언트가 "stop" 명령을 보내면 이 값이 true로 변경된다.
  // - 이 값이 true 이면 다음 클라이언트 접속할 때 서버를 종료한다.
  static boolean stop = false;


  // 스레드풀 준비
  ExecutorService threadPool = Executors.newCachedThreadPool();



  //각 클라이언트의 로그인 정보를 보관할 맵 객체
  // => Map<clientID, result>
  static Map<Long, Member> signInContext = new HashMap<>();



  // 옵저버와 공유할 맵 객체
  static Map<String,Object> context = new Hashtable<>();

  // 옵저버를 보관할 컬렉션 객체
  List<ApplicationContextListener> listeners = new ArrayList<>();

  // 옵저버를 등록하는 메서드
  public void addApplicationContextListener(ApplicationContextListener listener) {
    listeners.add(listener);
  }

  // 옵저버를 제거하는 메서드
  public void removeApplicationContextListener(ApplicationContextListener listener) {
    listeners.remove(listener);
  }

  // 옵저버에게 통지한다.
  private void notifyApplicationContextListenerOnServiceStarted() {
    for (ApplicationContextListener listener : listeners) {
      listener.contextInitialized(context);
    }
  }

  // 옵저버에게 통지한다.
  private void notifyApplicationContextListenerOnServiceStopped() {
    for (ApplicationContextListener listener : listeners) {
      listener.contextDestroyed(context);
    }
  }

  public void service(int port) {

    notifyApplicationContextListenerOnServiceStarted();

    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("서버 실행 중...");

      while (true) {
        Socket clientSocket = serverSocket.accept();

        if (stop) {
          break;
        }
        // 직접 스레드를 생성하는 것이 아니라 스레드풀에 작업을 맡긴다.
        threadPool.execute(() -> handleClient(clientSocket));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    notifyApplicationContextListenerOnServiceStopped();

    // 스레드풀을 종료한다.
    threadPool.shutdown();

    try {
      // 스레드풀의 모든 스레드가 종료될 때까지 기다린다.
      if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
        System.out.println("아직 종료 안된 작업이 있다.");
        System.out.println("남아 있는 작업의 강제 종료를 시도하겠다.");
        // => 만약 10초가 경과될 때까지 종료되지 않으면,
        //    수행 중인 작업을 강제 종료하라고 지시하고,
        //    대기 중인 작업은 취소한다.
        threadPool.shutdownNow();

        // 그리고 다시 작업이 종료될 때까지 기다린다.
        if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
          System.out.println("스레드풀의 강제 종료를 완료하지 못했다.");
        } else {
          System.out.println("모든 작업을 강제 종료했다.");
        }
      }
    } catch (Exception e) {
      System.out.println("스레드풀 종료 중 오류 발생!");
    }
    System.out.println("서버 종료!");
  }

  public static void main(String[] args) {
    ServerApp server = new ServerApp();

    // 리스너(옵저버/구독자) 등록
    server.addApplicationContextListener(new AppInitListener());
    server.addApplicationContextListener(new DataHandlerListener());
    server.addApplicationContextListener(new RequestMappingListener());

    server.service(8888);
  }

  @SuppressWarnings("unchecked")
  private static void handleClient(Socket clientSocket) {
    InetAddress address = clientSocket.getInetAddress();
    System.out.printf("클라이언트(%s)가 연결되었습니다.\n",
        address.getHostAddress());

    try (Socket socket = clientSocket; // try 블록을 떠날 때 close()가 자동 호출된다.
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream())) {

      // 클라이언트를 구분하기 위한 아이디
      // => 0: 아직 클라이언트 아이디가 없다는 의미
      // => x: 서버가 클라이언트에게 아이디를 부여했다는 의미
      long clientId = Long.parseLong(in.readLine());

      // 클라이언트의 로그인 정보 꺼내기
      Member client = signInContext.get(clientId);

      if (client != null) {
        System.out.printf("%d 기존 고객!\n", clientId);
      } else {
        // 해당 클라이언트가 방문한적이 없다면, 새 클라이언트 아이디를 발급한다.
        clientId = System.currentTimeMillis();
        System.out.printf("%d 신규 고객!\n", clientId);
        client = new Member();
      }
      out.println(clientId);
      out.flush();

      // 클라이언트가 보낸 요청을 읽는다.
      String request = in.readLine();

      if (request.equalsIgnoreCase("quit")) {

        for (int i = 0; i < ((List<Member>)context.get("memberList")).size(); i++) {
          Member member = ((List<Member>)context.get("memberList")).get(i);
          if (member.getName().equals(client.getName())) {
            member.setSignIn(0);
          }
        }
        signInContext.remove(clientId);

        out.println();
        out.flush();
        return;
      }

      if (request.equalsIgnoreCase("stop")) {

        stop = true; // 서버의 상태를 멈추라는 의미로 true로 설정한다.

        for (int i = 0; i < ((List<Member>)context.get("memberList")).size(); i++) {
          Member member = ((List<Member>)context.get("memberList")).get(i);
          if (member.getName().equals(client.getName())) {
            member.setSignIn(0);
          }
        }
        //        signInContext.remove(clientId);

        out.println("서버를 종료하는 중입니다!");
        out.println();
        out.flush();
        return;
      }

      Command command = (Command) context.get(request);
      if (command != null) {
        command.execute(out, in, signInContext, clientId, client);
      } else {
        out.println("해당 명령을 처리할 수 없습니다!");
      }

      out.println();
      out.flush();

    } catch (Exception e) {
      System.out.println("클라이언트와의 통신 오류!");
    }

    System.out.printf("클라이언트(%s)와의 연결을 끊었습니다.\n",
        address.getHostAddress());
  }
}