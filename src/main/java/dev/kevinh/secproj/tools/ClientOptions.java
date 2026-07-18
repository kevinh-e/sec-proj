package dev.kevinh.secproj.tools;

import dev.kevinh.secproj.SecurityProjectClient;
import net.minecraft.client.MinecraftClient;

public class ClientOptions {
  private boolean autoClickerEnabled;
  private boolean noFallEnabled;
  private boolean freecamEnabled;
  private boolean stepEnabled;
  private double freecamSpeed;
  private boolean fullBrightEnabled;

  public static final double FREECAM_SPEED_MIN = 0.05;
  public static final double FREECAM_SPEED_MAX = 1.0;
  public static final double FREECAM_SPEED_DEFAULT = 0.3;

  public ClientOptions() {
    autoClickerEnabled = false;
    noFallEnabled = false;
    freecamEnabled = false;
    stepEnabled = false;
    freecamSpeed = FREECAM_SPEED_DEFAULT;
    fullBrightEnabled = false;
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
    MinecraftClient client = MinecraftClient.getInstance();
    this.freecamEnabled = flag;
    SecurityProjectClient.getClientState().setFreeCamState(client.gameRenderer.getCamera().getPos());
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

  public void setFreecamSpeed(double speed) {
    this.freecamSpeed = speed;
  }

  public double getFreecamSpeed() {
    return this.freecamSpeed;
  }

  public boolean isFullBrightEnabled() {
    return this.fullBrightEnabled;
  }

  public void setFullBrightEnabled(boolean flag) {
    this.fullBrightEnabled = flag;
  }
}
