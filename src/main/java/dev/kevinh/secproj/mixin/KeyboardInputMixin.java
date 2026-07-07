package dev.kevinh.secproj.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.kevinh.secproj.SecurityProjectClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin {

  @Inject(method = "tick", at = @At(value = "TAIL"))
  private void freezeMovement(CallbackInfo info) {
    if (SecurityProjectClient.getClientOptions().isFreecamEnabled()) {
      ((Input) (Object) this).movementForward = 0f;
      ((Input) (Object) this).movementSideways = 0f;
      ((Input) (Object) this).pressingForward = false;
      ((Input) (Object) this).pressingBack = false;
      ((Input) (Object) this).pressingLeft = false;
      ((Input) (Object) this).pressingRight = false;
      ((Input) (Object) this).sneaking = false;
      ((Input) (Object) this).jumping = false;
    }
  }
}
