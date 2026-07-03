package dev.kevinh.secproj.tools;

public class ClientOptions {
  public static boolean autoClickerEnabled;
  public static boolean noFallEnabled;

  public ClientOptions() {
    autoClickerEnabled = false;
    noFallEnabled = false;
  }

  public static void toggleAutoClicker() {
    autoClickerEnabled = !autoClickerEnabled;
  }

  public static void setNoFall(boolean flag) {
    noFallEnabled = flag;
  }

}
