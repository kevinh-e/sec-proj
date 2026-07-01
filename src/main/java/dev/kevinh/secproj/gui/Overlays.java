package dev.kevinh.secproj.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class Overlays {

  public static void render(DrawContext context, RenderTickCounter tickDeltaManager) {
    fetchTickEvents(context, tickDeltaManager);
  }

  private static void fetchTickEvents(DrawContext context, RenderTickCounter tickDeltaManager) {
    MinecraftClient client = MinecraftClient.getInstance();

    // if (client.mouse.wasRightButtonClicked()) {
    // rightMouseWasClicked = true;
    // } else if (rightMouseWasClicked &&
    // !this.client.mouse.wasRightButtonClicked()) {
    // rightMouseWasClicked = false;
    // autoClickerEnabled = !autoClickerEnabled;
    // }
    //
    // if (this.client.mouse.wasLeftButtonClicked()) {
    // leftMouseWasClicked = true;
    // } else if (leftMouseWasClicked && !this.client.mouse.wasLeftButtonClicked())
    // {
    // clickQueue.addEventNow();
    // leftMouseWasClicked = false;
    // }
  }

}
