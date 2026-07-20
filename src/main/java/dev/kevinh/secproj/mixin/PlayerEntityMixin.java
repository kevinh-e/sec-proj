package dev.kevinh.secproj.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.kevinh.secproj.SecurityProject;
import dev.kevinh.secproj.SecurityProjectClient;
import dev.kevinh.secproj.tools.ClientOptions;
import net.minecraft.entity.Entity;
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

  @Inject(method = "getBlockInteractionRange", at = @At("RETURN"))
  public void modifyBlockReach(CallbackInfoReturnable<Double> cir) {
    ClientOptions options = SecurityProjectClient.getClientOptions();
    if (options.isReachEnabled()) {
      cir.setReturnValue(options.getBlockReachValue());
    }
  }

  @Inject(method = "getEntityInteractionRange", at = @At("RETURN"))
  public void modifyEntityReach(CallbackInfoReturnable<Double> cir) {
    ClientOptions options = SecurityProjectClient.getClientOptions();
    if (options.isReachEnabled()) {
      cir.setReturnValue(options.getEntityReachValue());
    }
  }
}
