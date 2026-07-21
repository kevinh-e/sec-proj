package dev.kevinh.secproj.gui;

import java.util.ArrayList;

import dev.kevinh.secproj.SecurityProjectClient;
import dev.kevinh.secproj.tools.ClientOptions;
import dev.kevinh.secproj.tools.TimedQueue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class Overlay {
  private static final int TEXT_COLOUR = 0xFFFFFFFF;
  private static final int PADDING_SIZE = 5;
  private static final int STARTING_Y = 100;

  public static void renderOverlay(DrawContext context, RenderTickCounter tickDeltaManager,
      TimedQueue clickQueue) {
    TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

    ClientOptions options = SecurityProjectClient.getClientOptions();

    ArrayList<String> lines = new ArrayList<String>();

    if (options.isCpsShownInOverlay()) {
      lines.add("CPS " + clickQueue.size());
    }
    if (options.isAutoClickerShownInOverlay()) {
      lines.add("AutoClicker " + (options.isAutoClickerEnabled() ? "enabled" : "disabled"));
    }
    if (options.isNoFallShownInOverlay()) {
      lines.add("NoFall " + (options.isNoFallEnabled() ? "enabled" : "disabled"));
    }
    if (options.isFreecamShownInOverlay()) {
      lines.add("Freecam " + (options.isFreecamEnabled() ? "enabled" : "disabled"));
    }
    if (options.isFreecamSpeedShownInOverlay()) {
      lines.add(String.format("Freecam Speed %.2f", options.getFreecamSpeed()));
    }
    if (options.isStepShownInOverlay()) {
      lines.add("Step " + (options.isStepEnabled() ? "enabled" : "disabled"));
    }
    if (options.isFullBrightShownInOverlay()) {
      lines.add("FullBright " + (options.isFullBrightEnabled() ? "enabled" : "disabled"));
    }
    if (options.isReachShownInOverlay()) {
      lines.add("Reach " + (options.isReachEnabled() ? "enabled" : "disabled"));
      if (options.isReachEnabled()) {
        lines.add(String.format("Block Reach %.1f", options.getBlockReachValue()));
        lines.add(String.format("Entity Reach %.1f", options.getEntityReachValue()));
      }
    }
    if (options.isCriticalsShownInOverlay()) {
      lines.add("Criticals " + (options.isCriticalsEnabled() ? "enabled" : "disabled"));
    }
    if (options.isMaceShownInOverlay()) {
      lines.add("Mace " + (options.isMaceEnabled() ? "enabled" : "disabled"));
      if (options.isMaceEnabled()) {
        lines.add(String.format("Mace Height %.2f", options.getMaceHeight()));
      }
    }

    if (lines.isEmpty()) {
      return;
    }

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
