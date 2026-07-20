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
  private boolean reachEnabled;
  private double blockReachValue;
  private double entityReachValue;

  private boolean cpsShownInOverlay;
  private boolean autoClickerShownInOverlay;
  private boolean noFallShownInOverlay;
  private boolean freecamShownInOverlay;
  private boolean freecamSpeedShownInOverlay;
  private boolean stepShownInOverlay;
  private boolean fullBrightShownInOverlay;

  public static final double FREECAM_SPEED_MIN = 0.05;
  public static final double FREECAM_SPEED_MAX = 1.0;
  public static final double FREECAM_SPEED_DEFAULT = 0.3;
  public static final double ENTITY_REACH_DEFAULT = 5;
  public static final double BLOCK_REACH_DEFAULT = 6.5;

  public ClientOptions() {
    autoClickerEnabled = false;
    noFallEnabled = false;
    freecamEnabled = false;
    stepEnabled = false;
    freecamSpeed = FREECAM_SPEED_DEFAULT;
    fullBrightEnabled = false;
    reachEnabled = false;
    blockReachValue = BLOCK_REACH_DEFAULT;
    entityReachValue = ENTITY_REACH_DEFAULT;

    cpsShownInOverlay = true;
    autoClickerShownInOverlay = true;
    noFallShownInOverlay = true;
    freecamShownInOverlay = true;
    freecamSpeedShownInOverlay = true;
    stepShownInOverlay = true;
    fullBrightShownInOverlay = true;
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

<<<<<<< HEAD
  public boolean isCpsShownInOverlay() {
    return this.cpsShownInOverlay;
  }

  public void setCpsShownInOverlay(boolean flag) {
    this.cpsShownInOverlay = flag;
  }

  public boolean isAutoClickerShownInOverlay() {
    return this.autoClickerShownInOverlay;
  }

  public void setAutoClickerShownInOverlay(boolean flag) {
    this.autoClickerShownInOverlay = flag;
  }

  public boolean isNoFallShownInOverlay() {
    return this.noFallShownInOverlay;
  }

  public void setNoFallShownInOverlay(boolean flag) {
    this.noFallShownInOverlay = flag;
  }

  public boolean isFreecamShownInOverlay() {
    return this.freecamShownInOverlay;
  }

  public void setFreecamShownInOverlay(boolean flag) {
    this.freecamShownInOverlay = flag;
  }

  public boolean isFreecamSpeedShownInOverlay() {
    return this.freecamSpeedShownInOverlay;
  }

  public void setFreecamSpeedShownInOverlay(boolean flag) {
    this.freecamSpeedShownInOverlay = flag;
  }

  public boolean isStepShownInOverlay() {
    return this.stepShownInOverlay;
  }

  public void setStepShownInOverlay(boolean flag) {
    this.stepShownInOverlay = flag;
  }

  public void setReachEnabled(boolean flag) {
    this.reachEnabled = flag;
  }

  public boolean isReachEnabled() {
    return this.reachEnabled;
  }

  public double getBlockReachValue() {
    return blockReachValue;
  }

  public void setBlockReachValue(double blockReachValue) {
    this.blockReachValue = blockReachValue;
  }

  public double getEntityReachValue() {
    return entityReachValue;
  }

  public void setEntityReachValue(double entityReachValue) {
    this.entityReachValue = entityReachValue;
  }

  public boolean isCpsShownInOverlay() {
    return this.cpsShownInOverlay;
  }

  public void setCpsShownInOverlay(boolean flag) {
    this.cpsShownInOverlay = flag;
  }

  public boolean isAutoClickerShownInOverlay() {
    return this.autoClickerShownInOverlay;
  }

  public void setAutoClickerShownInOverlay(boolean flag) {
    this.autoClickerShownInOverlay = flag;
  }

  public boolean isNoFallShownInOverlay() {
    return this.noFallShownInOverlay;
  }

  public void setNoFallShownInOverlay(boolean flag) {
    this.noFallShownInOverlay = flag;
  }

  public boolean isFreecamShownInOverlay() {
    return this.freecamShownInOverlay;
  }

  public void setFreecamShownInOverlay(boolean flag) {
    this.freecamShownInOverlay = flag;
  }

  public boolean isFreecamSpeedShownInOverlay() {
    return this.freecamSpeedShownInOverlay;
  }

  public void setFreecamSpeedShownInOverlay(boolean flag) {
    this.freecamSpeedShownInOverlay = flag;
  }

  public boolean isStepShownInOverlay() {
    return this.stepShownInOverlay;
  }

  public void setStepShownInOverlay(boolean flag) {
    this.stepShownInOverlay = flag;
  }

  public boolean isFullBrightShownInOverlay() {
    return this.fullBrightShownInOverlay;
  }

  public void setFullBrightShownInOverlay(boolean flag) {
    this.fullBrightShownInOverlay = flag;
  }
}
