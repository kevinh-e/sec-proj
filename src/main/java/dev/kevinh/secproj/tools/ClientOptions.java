package dev.kevinh.secproj.tools;

public class ClientOptions {
  public boolean autoClickerEnabled;
  public boolean noFallEnabled;

  public ClientOptions() {
    this.autoClickerEnabled = false;
    this.noFallEnabled = false;
  }

  public void toggleAutoClicker() {
    this.autoClickerEnabled = !this.autoClickerEnabled;
  }

  public void setNoFall(boolean flag) {
    this.noFallEnabled = flag;
  }

}
