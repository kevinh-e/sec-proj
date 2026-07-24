package dev.kevinh.secproj.tools;

import java.util.OptionalDouble;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class Render3d {
  public static RenderLayer MY_LINES = RenderLayer.of("SECPROJ_Render", VertexFormats.LINES, DrawMode.LINES, 1536,
      RenderLayer.MultiPhaseParameters.builder()
          .program(RenderPhase.LINES_PROGRAM)
          .depthTest(RenderPhase.ALWAYS_DEPTH_TEST)
          .lineWidth(new RenderPhase.LineWidth(OptionalDouble.empty()))
          .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
          .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
          .target(RenderPhase.ITEM_ENTITY_TARGET)
          .writeMaskState(RenderPhase.ALL_MASK)
          .cull(RenderPhase.DISABLE_CULLING)
          .build(false));

  public static void drawTestBox(WorldRenderContext ctx) {
    double camX = ctx.camera().getPos().x;
    double camY = ctx.camera().getPos().y;
    double camZ = ctx.camera().getPos().z;

    Box box = new Box(-camX, -camY, -camZ, -camX + 1.0, -camY + 1.0, -camZ + 1.0);
    WorldRenderer.drawBox(ctx.matrixStack(), ctx.consumers().getBuffer(MY_LINES), box, (float) 1.0,
        (float) 0.0,
        (float) 1.0, (float) 1.0);
  }

  public static void drawStorageBox(WorldRenderContext ctx, BlockPos pos) {
    Vec3d cam = ctx.camera().getPos();
    Box box = new Box(pos).offset(cam.negate());

    WorldRenderer.drawBox(ctx.matrixStack(), ctx.consumers().getBuffer(MY_LINES), box, (float) 1.0,
        (float) 0.0,
        (float) 1.0, (float) 1.0);
  }
}
