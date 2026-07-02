package dev.kevinh.secproj.gui;

import dev.kevinh.secproj.tools.ClientOptions;
import dev.kevinh.secproj.tools.TimedQueue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class Overlay {
  private TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
  private static final int TEXT_COLOUR = 0xFFFFFFFF;
  private static final int PADDING_SIZE = 5;

  private ClientOptions options;

  public void init(ClientOptions options) {
    this.options = options;
  }

  public void renderClickerOverlay(DrawContext context, RenderTickCounter tickDeltaManager,
      TimedQueue clickQueue) {
    String cpsCopy = "CPS " + clickQueue.size();
    String autoClickerCopy = "AutoClicker "
        + (options.autoClickerEnabled ? "enabled" : "disabled");

    int textWidth = Math.max(this.textRenderer.getWidth(cpsCopy), this.textRenderer.getWidth(autoClickerCopy));
    int textHeight = this.textRenderer.fontHeight + PADDING_SIZE;

    // Background
    context.fill(5, 155, textWidth + 3 * PADDING_SIZE, 155 + 2 * (textHeight + PADDING_SIZE), 0, 0xBF3A3632);
    context.drawBorder(5, 155, textWidth + 2 * PADDING_SIZE, 2 * (textHeight + PADDING_SIZE), 0xBF57534E);

    // Text
    context.drawText(textRenderer, cpsCopy, 10, 160, TEXT_COLOUR, false);
    context.drawText(textRenderer, autoClickerCopy, 10, 160 + PADDING_SIZE + textHeight, TEXT_COLOUR, false);
  }
}
