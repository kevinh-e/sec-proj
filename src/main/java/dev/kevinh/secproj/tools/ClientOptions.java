package dev.kevinh.secproj.tools;

public class ClientOptions {
  private boolean autoClickerEnabled;
  private boolean noFallEnabled;

  public ClientOptions() {
    autoClickerEnabled = false;
    noFallEnabled = false;
  }

  public void toggleAutoClicker() {
    this.autoClickerEnabled = !this.autoClickerEnabled;
  }

  public void setNoFall(boolean flag) {
    this.noFallEnabled = flag;
  }

  public boolean isAutoClickerEnabled() {
    return autoClickerEnabled;
  }

  public boolean isNoFallEnabled() {
    return noFallEnabled;
  }
}
