package dev.kevinh.secproj.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import dev.kevinh.secproj.SecurityProjectClient;
import dev.kevinh.secproj.tools.PacketHelper;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.MaceItem;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
  private MinecraftClient client = MinecraftClient.getInstance();

  @Inject(method = "attackEntity", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILSOFT)
  public void injectCriticals(CallbackInfo info, PlayerEntity player) {
    if (!SecurityProjectClient.getClientOptions().isCriticalsEnabled())
      return;

    if (SecurityProjectClient.getClientOptions().isMaceEnabled()) {
      if (player.getMainHandStack().getItem() instanceof MaceItem) {
        if (player.isFallFlying())
          return;
        PacketHelper.sendMacePacket(client, 0);
        PacketHelper.sendMacePacket(client, 1.501 + SecurityProjectClient.getClientOptions().getMaceHeight());
        PacketHelper.sendMacePacket(client, 0);
      }
    }
  }
}
