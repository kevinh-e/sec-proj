package dev.kevinh.secproj.gui;

import java.util.ArrayList;

import dev.kevinh.secproj.SecurityProjectClient;
import dev.kevinh.secproj.tools.TimedQueue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class Overlay {
  private static final int TEXT_COLOUR = 0xFFFFFFFF;
  private static final int PADDING_SIZE = 5;
  private static final int STARTING_Y = 130;

  public static void renderOverlay(DrawContext context, RenderTickCounter tickDeltaManager,
      TimedQueue clickQueue) {
    TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

    ArrayList<String> lines = new ArrayList<String>();

    lines.add("CPS " + clickQueue.size());
    lines.add("AutoClicker "
        + (SecurityProjectClient.getClientOptions().isAutoClickerEnabled() ? "enabled" : "disabled"));
    lines.add("Freecam "
        + (SecurityProjectClient.getClientOptions().isFreecamEnabled() ? "enabled" : "disabled"));

    int textWidth = lines.stream().mapToInt(textRenderer::getWidth).max().orElse(0);
    int textHeight = textRenderer.fontHeight + PADDING_SIZE;

    // Background
    context.fill(5, STARTING_Y, textWidth + (lines.size() + 1) * PADDING_SIZE,
        STARTING_Y + lines.size() * (textHeight + PADDING_SIZE), 0,
        0xBF3A3632);
    context.drawBorder(5, STARTING_Y, textWidth + lines.size() * PADDING_SIZE,
        lines.size() * (textHeight + PADDING_SIZE),
        0xBF57534E);

    // Text
    for (String line : lines) {
      context.drawText(textRenderer, line, 10,
          STARTING_Y + PADDING_SIZE + lines.indexOf(line) * (PADDING_SIZE + textHeight), TEXT_COLOUR,
          false);
    }
  }
}
