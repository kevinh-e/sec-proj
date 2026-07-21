package dev.kevinh.secproj.tools;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class PacketHelper {
  public static PlayerMoveC2SPacket createMovementPacket(MinecraftClient client, double x, double y, double z) {
    return new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false);
  }

  public static void sendMacePacket(MinecraftClient client, double height) {
    double x = client.player.getX();
    double y = client.player.getY();
    double z = client.player.getZ();

    PlayerMoveC2SPacket packet = createMovementPacket(client, x, y + height, z);
    client.getNetworkHandler().sendPacket(packet);
  }
}
