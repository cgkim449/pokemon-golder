package pokemon.golder.server.pms.listener;

import java.util.Map;
import pokemon.golder.server.context.ApplicationContextListener;

public class AppInitListener implements ApplicationContextListener {
  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("프로젝트 관리 시스템(PMS)에 오신 걸 환영합니다!");
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("프로젝트 관리 시스템(PMS)을 종료합니다!");
  }
}
