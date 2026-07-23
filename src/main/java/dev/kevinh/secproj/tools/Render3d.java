package dev.kevinh.secproj.tools;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.Box;

public class Render3d {
  public static void drawTestBox(WorldRenderContext ctx) {
    Box box = new Box(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    WorldRenderer.drawBox(ctx.matrixStack(), ctx.consumers().getBuffer(RenderLayer.getLines()), box, (float) 1.0,
        (float) 1.0,
        (float) 1.0, (float) 1.0);
  }
}
