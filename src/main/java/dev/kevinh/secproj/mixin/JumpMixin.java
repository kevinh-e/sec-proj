package dev.kevinh.secproj.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.kevinh.secproj.SecurityProject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;

@Mixin(PlayerEntity.class)
public class JumpMixin {

  @Inject(method = "jump", at = @At("TAIL"))
  private void injectJump(CallbackInfo info) {
    if (!((Object) this instanceof ServerPlayerEntity)) {
      return;
    }

    double playerY = ((Entity) (Object) this).getY();
    StatHandler statHandler = ((ServerPlayerEntity) (Object) this).getStatHandler();
    SecurityProject.LOGGER.info("Jump no. {} at y={}", statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.JUMP)),
        playerY);
  }
}
