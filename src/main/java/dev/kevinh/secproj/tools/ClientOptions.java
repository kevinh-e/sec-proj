package dev.kevinh.secproj.tools;

public class ClientOptions {
  private boolean autoClickerEnabled;
  private boolean noFallEnabled;
  private boolean freecamEnabled;
  private boolean stepEnabled;
  private float freecamSpeed;

  public ClientOptions() {
    autoClickerEnabled = false;
    noFallEnabled = false;
    freecamEnabled = false;
    freecamSpeed = (float) 0.3;
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

  public float getFreecamSpeed() {
    return this.freecamSpeed;
  }

  public void toggleStep() {
    this.stepEnabled = !this.stepEnabled;
  }

  public boolean isStepEnabled() {
    return this.stepEnabled;
  }
}
