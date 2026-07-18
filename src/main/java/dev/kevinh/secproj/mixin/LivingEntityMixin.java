package dev.kevinh.secproj.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.kevinh.secproj.SecurityProjectClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
  @Inject(method = "getStepHeight", at = @At("RETURN"), cancellable = true)
  public void injectStepHeight(CallbackInfoReturnable<Float> cir) {
    if (SecurityProjectClient.getClientOptions().isStepEnabled()) {
      // if it's the client's player
      if ((Object) this instanceof ClientPlayerEntity) {
        cir.setReturnValue(1.0F);
      }
    }
  }
}
