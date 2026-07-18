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
    stepEnabled = false;
    freecamSpeed = (float) 0.3;
  }

  public void setAutoClicker(boolean flag) {
    this.autoClickerEnabled = flag;
  }

  public boolean isAutoClickerEnabled() {
    return autoClickerEnabled;
  }

  public void setNoFall(boolean flag) {
    this.noFallEnabled = flag;
  }

  public boolean isNoFallEnabled() {
    return noFallEnabled;
  }

  public void setFreecam(boolean flag) {
    this.freecamEnabled = flag;
  }

  public boolean isFreecamEnabled() {
    return freecamEnabled;
  }

  public void setStep(boolean flag) {
    this.stepEnabled = flag;
  }

  public boolean isStepEnabled() {
    return this.stepEnabled;
  }

  public float getFreecamSpeed() {
    return this.freecamSpeed;
  }
}
