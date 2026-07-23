package dev.kevinh.secproj.tools;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.Box;

public class Render3d {
  public static void drawTestBox(WorldRenderContext ctx) {
    double camX = ctx.camera().getPos().x;
    double camY = ctx.camera().getPos().y;
    double camZ = ctx.camera().getPos().z;

    Box box = new Box(-camX, -camY, -camZ, -camX + 1.0, -camY + 1.0, -camZ + 1.0);
    WorldRenderer.drawBox(ctx.matrixStack(), ctx.consumers().getBuffer(RenderLayer.getLines()), box, (float) 1.0,
        (float) 0.0,
        (float) 1.0, (float) 1.0);
    flushBuffer(ctx);
  }

  private static void flushBuffer(WorldRenderContext ctx) {
    ((VertexConsumerProvider.Immediate) ctx.consumers()).draw();
  }
}
