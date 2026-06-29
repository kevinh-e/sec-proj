package dev.kevinh.secproj.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.kevinh.secproj.SecurityProjectClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

  @Shadow
  private boolean doAttack() {
    throw new AssertionError();
  }

  @Inject(method = "handleInputEvents", at = @At("HEAD"))
  private void leftClick(CallbackInfo info) {
    if (SecurityProjectClient.autoClickerEnabled) {
      this.doAttack();
    }
  }

  // @Redirect(method = "handleInputEvents", at = @At(value = "INVOKE", target =
  // "Lnet/minecraft/client/option/KeyBinding;wasPressed()Z"))
  // private boolean alwaysAttack(KeyBinding keyBinding) {
  // if (keyBinding == ((MinecraftClient) (Object) this).options.attackKey) {
  // if (!hasClicked) {
  // return false;
  // }
  // if (SecurityProjectClient.autoClickerEnabled) {
  // keyBinding.wasPressed();
  // this.hasClicked = true;
  // return true;
  // }
  // }
  // return keyBinding.wasPressed();
  // }
}
