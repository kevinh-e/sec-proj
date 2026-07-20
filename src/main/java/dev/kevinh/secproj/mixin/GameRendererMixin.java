package dev.kevinh.secproj.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.kevinh.secproj.SecurityProjectClient;
import dev.kevinh.secproj.tools.ClientOptions;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

  @Redirect(method = "updateCrosshairTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getBlockInteractionRange()D"))
  public double modifyBlockReach(PlayerEntity playerEntity) {
    if (SecurityProjectClient.getClientOptions().isReachEnabled()) {
      return ClientOptions.BLOCK_REACH_DEFAULT;
    }
    return playerEntity.getBlockInteractionRange();
  }

  @Redirect(method = "updateCrosshairTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getEntityInteractionRange()D"))
  public double modifyEntityReach(PlayerEntity playerEntity) {
    if (SecurityProjectClient.getClientOptions().isReachEnabled()) {
      return ClientOptions.ENTITY_REACH_DEFAULT;
    }
    return playerEntity.getEntityInteractionRange();
  }
}
