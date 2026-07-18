package dev.kevinh.secproj.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.kevinh.secproj.SecurityProjectClient;
import net.minecraft.client.network.ClientPlayerEntity;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
  @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isOnGround()Z"))
  public boolean flashOnGroundWhenFalling(ClientPlayerEntity entity) {
    if (SecurityProjectClient.getClientOptions().isNoFallEnabled()) {
      if (entity.fallDistance >= 2.0f) {
        return true;
      }
    }
    return entity.isOnGround();
  }
}
