package dev.kevinh.secproj.tools;

import net.minecraft.util.math.Vec3d;

public class ClientState {
  // private Quaternionf freeCamRotation;
  private Vec3d freeCamPos;

  public ClientState() {
    // this.freeCamRotation = null;
    this.freeCamPos = null;
  }

  public void setFreeCamState(Vec3d pos) {
    this.freeCamPos = pos;
    // this.freeCamRotation = rotation;
  }

  public Vec3d getFreeCamPos() {
    return this.freeCamPos;
  }
}
