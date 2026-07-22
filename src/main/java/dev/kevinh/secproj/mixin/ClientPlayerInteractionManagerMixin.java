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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.MaceItem;
import net.minecraft.server.network.ServerPlayNetworkHandler;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

  @Inject(method = "attackEntity", at = @At("HEAD"))
  public void injectCriticals(PlayerEntity player, Entity target, CallbackInfo info) {
    MinecraftClient client = MinecraftClient.getInstance();
    if (!SecurityProjectClient.getClientOptions().isCriticalsEnabled())
      return;
    double height = 0.001;
    if (SecurityProjectClient.getClientOptions().isMaceEnabled()
        && player.getMainHandStack().getItem() instanceof MaceItem) {
      height += 1.5 + SecurityProjectClient.getClientOptions().getMaceHeight();
    }
    if (player.isFallFlying())
      return;
    PacketHelper.sendMacePacket(client, 0);
    PacketHelper.sendMacePacket(client, height);
    PacketHelper.sendMacePacket(client, 0);
  }
}
