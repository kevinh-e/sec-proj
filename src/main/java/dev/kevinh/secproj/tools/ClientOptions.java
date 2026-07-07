package dev.kevinh.secproj.tools;

public class ClientOptions {
  private boolean autoClickerEnabled;
  private boolean noFallEnabled;
  private boolean freecamEnabled;

  public ClientOptions() {
    autoClickerEnabled = false;
    noFallEnabled = false;
    freecamEnabled = false;
  }

  public void toggleAutoClicker() {
    this.autoClickerEnabled = !this.autoClickerEnabled;
  }

  public boolean isAutoClickerEnabled() {
    return autoClickerEnabled;
  }

  public void setNoFall(boolean flag) {
    this.noFallEnabled = flag;
  }

  public void toggleNoFall() {
    this.noFallEnabled = !this.noFallEnabled;
  }

  public boolean isNoFallEnabled() {
    return noFallEnabled;
  }

  public boolean isFreecamEnabled() {
    return freecamEnabled;
  }

  public void setFreecam(boolean flag) {
    this.freecamEnabled = flag;
  }

  public void toggleFreecam() {
    this.freecamEnabled = !this.freecamEnabled;
  }
}
