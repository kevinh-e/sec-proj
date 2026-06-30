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
  private boolean leftMouseWasClicked = false;
  private boolean rightMouseWasClicked = false;
  public static boolean autoClickerEnabled = false;
  private static final int TEXT_COLOUR = 0xFFFFFFFF;
  private static final int PADDING_SIZE = 5;

  @Override
  public void onInitializeClient() {
    SecurityProject.LOGGER.info("UI Renderer Client for " + SecurityProject.MOD_ID);

    // draw shi
    HudRenderCallback.EVENT.register((context, tickDeltaManager) -> {
      fetchTickEvents(context, tickDeltaManager);
      renderOverlay(context, tickDeltaManager);
    });
  }

  private void fetchTickEvents(DrawContext context, RenderTickCounter tickDeltaManager) {
    if (this.client.mouse.wasRightButtonClicked()) {
      rightMouseWasClicked = true;
    } else if (rightMouseWasClicked && !this.client.mouse.wasRightButtonClicked()) {
      rightMouseWasClicked = false;
      autoClickerEnabled = !autoClickerEnabled;
    }

    if (this.client.mouse.wasLeftButtonClicked()) {
      leftMouseWasClicked = true;
    } else if (leftMouseWasClicked && !this.client.mouse.wasLeftButtonClicked()) {
      clickQueue.addEventNow();
      leftMouseWasClicked = false;
    }
  }

  private void renderOverlay(DrawContext context, RenderTickCounter tickDeltaManager) {
    String cpsCopy = "CPS " + clickQueue.size();
    String autoClickerCopy = "AutoClicker " + (autoClickerEnabled ? "enabled" : "disabled");

    TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    int textWidth = Math.max(textRenderer.getWidth(cpsCopy), textRenderer.getWidth(autoClickerCopy));
    int textHeight = textRenderer.fontHeight + PADDING_SIZE;

    // Background
    context.fill(5, 155, textWidth + 3 * PADDING_SIZE, 155 + 2 * (textHeight + PADDING_SIZE), 0, 0xBF3A3632);
    context.drawBorder(5, 155, textWidth + 2 * PADDING_SIZE, 2 * (textHeight + PADDING_SIZE), 0xBF57534E);

    // Text
    context.drawText(textRenderer, cpsCopy, 10, 160, TEXT_COLOUR, false);
    context.drawText(textRenderer, autoClickerCopy, 10, 160 + PADDING_SIZE + textHeight, TEXT_COLOUR, false);
  }
}
