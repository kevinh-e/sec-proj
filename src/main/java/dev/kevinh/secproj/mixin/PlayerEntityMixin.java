package dev.kevinh.secproj.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.kevinh.secproj.SecurityProject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

  @Inject(method = "jump", at = @At("TAIL"))
  private void injectJump(CallbackInfo info) {
    if (!((Object) this instanceof ServerPlayerEntity)) {
      return;
    }

    double playerY = ((Entity) (Object) this).getY();
    StatHandler statHandler = ((ServerPlayerEntity) (Object) this).getStatHandler();
    SecurityProject.LOGGER.info("Jump no. {} at y={}",
        statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.JUMP)),
        playerY);
  }

  @Redirect(method = "handleFallDamage", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/entity/player/PlayerAbilities;allowFlying:Z"))
  private boolean setFlyingOnFallDamage(PlayerAbilities abilities) {
    if (!((Object) this instanceof ServerPlayerEntity)) {
      return true;
    }
    return abilities.allowFlying;
  }
}
