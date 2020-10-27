package pokemon.golder.server.pms.listener;

import java.util.Map;
import pokemon.golder.server.context.ApplicationContextListener;

public class AppInitListener implements ApplicationContextListener {
  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("Pokemon Golder에 오신 걸 환영합니다!");
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("Pokemon Golder를 종료합니다!");
  }
}
