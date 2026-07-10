package dev.kevinh.secproj.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.kevinh.secproj.SecurityProjectClient;
import dev.kevinh.secproj.tools.ClientOptions;
import dev.kevinh.secproj.tools.ClientState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

@Mixin(Camera.class)
public class CameraMixin {
  @Shadow
  protected void setPos(Vec3d pos) {
    throw new AssertionError();
  }

  @Shadow
  protected void setPos(double x, double y, double z) {
    throw new AssertionError();
  }

  @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setPos(DDD)V"))
  protected void setFreeCamPos(Camera camera, double x, double y, double z) {
    ClientOptions options = SecurityProjectClient.getClientOptions();
    ClientState state = SecurityProjectClient.getClientState();

    if (options.isFreecamEnabled()
        && state.getFreeCamPos() != null) {
      // freeze the player (done)
      // remap wasd to move the camera pos
      MinecraftClient client = MinecraftClient.getInstance();
      Entity player = ((Camera) (Object) this).getFocusedEntity();
      // track player yaw in
      // degrees
      float yaw = player.getYaw();
      Vec3d direction = player.getRotationVector(0, yaw);

      Vec3d prevPos = state.getFreeCamPos();
      Vec3d newPos = prevPos;
      // wasd will apply a delta Vec3d
      if (client.options.forwardKey.isPressed()) {
        newPos = newPos.add(direction.multiply(options.getFreecamSpeed()));
      }
      if (client.options.backKey.isPressed()) {
        newPos = newPos.add(direction.multiply(-1 * options.getFreecamSpeed()));
      }
      if (client.options.leftKey.isPressed()) {
        newPos = newPos.add(player.getRotationVector(0, yaw - 90).multiply(options.getFreecamSpeed()));
      }
      if (client.options.rightKey.isPressed()) {
        newPos = newPos.add(player.getRotationVector(0, yaw + 90).multiply(options.getFreecamSpeed()));
      }
      if (client.options.jumpKey.isPressed()) {
        newPos = newPos.add(new Vec3d(0, 1.0 * options.getFreecamSpeed(), 0));
      }
      if (client.options.sneakKey.isPressed()) {
        newPos = newPos.add(new Vec3d(0, -1.0 * options.getFreecamSpeed(), 0));
      }

      // overloaded setpos
      state.setFreeCamState(newPos);
      this.setPos(newPos);
    } else {
      this.setPos(x, y, z);
    }

  }
}
