package dev.kevinh.secproj;

import dev.kevinh.secproj.tools.TimedQueue;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class SecurityProjectClient implements ClientModInitializer {
  public static final TimedQueue clickQueue = new TimedQueue();
  private final MinecraftClient client = MinecraftClient.getInstance();

  @Override
  public void onInitializeClient() {
    SecurityProject.LOGGER.info("UI Renderer Client for " + SecurityProject.MOD_ID);

    // draw shi
    HudRenderCallback.EVENT.register((context, tickDeltaManager) -> {
      fetchTickEvents(context, tickDeltaManager);
      renderCPS(context, tickDeltaManager);
    });
  }

  private void fetchTickEvents(DrawContext context, RenderTickCounter tickDeltaManager) {
    if (this.client.options.attackKey.wasPressed()) {
      clickQueue.addEventNow();
    }
  }

  private void renderCPS(DrawContext context, RenderTickCounter tickDeltaManager) {
    TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    context.drawText(textRenderer, "CPS " + clickQueue.size(), 10, 200, 0xFFFFFFFF, false);
  }
}
