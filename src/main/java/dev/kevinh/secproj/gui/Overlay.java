package dev.kevinh.secproj.gui;

import dev.kevinh.secproj.SecurityProjectClient;
import dev.kevinh.secproj.tools.TimedQueue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class Overlay {
  private static final int TEXT_COLOUR = 0xFFFFFFFF;
  private static final int PADDING_SIZE = 5;

  public static void renderClickerOverlay(DrawContext context, RenderTickCounter tickDeltaManager,
      TimedQueue clickQueue) {
    TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    String cpsCopy = "CPS " + clickQueue.size();
    String autoClickerCopy = "AutoClicker "
        + (SecurityProjectClient.getClientOptions().isAutoClickerEnabled() ? "enabled" : "disabled");

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
