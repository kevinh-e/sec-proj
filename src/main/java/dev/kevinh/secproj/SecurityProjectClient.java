package dev.kevinh.secproj;

import dev.kevinh.secproj.gui.Overlay;
import dev.kevinh.secproj.tools.ClientOptions;
import dev.kevinh.secproj.tools.TimedQueue;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class SecurityProjectClient implements ClientModInitializer {
  public static final TimedQueue clickQueue = new TimedQueue();
  private final MinecraftClient client = MinecraftClient.getInstance();
  private boolean leftMouseWasClicked = false;
  private boolean rightMouseWasClicked = false;
  public static ClientOptions clientOptions = new ClientOptions();
  public Overlay overlay = new Overlay();

  @Override
  public void onInitializeClient() {
    SecurityProject.LOGGER.info("Setting up " + SecurityProject.MOD_ID + "Client");

    overlay.init(clientOptions);

    // draw shi
    HudRenderCallback.EVENT.register((context, tickDeltaManager) -> {
      fetchTickEvents(context, tickDeltaManager);
      overlay.renderClickerOverlay(context, tickDeltaManager, clickQueue);
    });
  }

  private void fetchTickEvents(DrawContext context, RenderTickCounter tickDeltaManager) {
    if (this.client.mouse.wasRightButtonClicked()) {
      rightMouseWasClicked = true;
    } else if (rightMouseWasClicked && !this.client.mouse.wasRightButtonClicked()) {
      rightMouseWasClicked = false;
      clientOptions.autoClickerEnabled = !clientOptions.autoClickerEnabled;
    }

    if (this.client.mouse.wasLeftButtonClicked()) {
      leftMouseWasClicked = true;
    } else if (leftMouseWasClicked && !this.client.mouse.wasLeftButtonClicked()) {
      clickQueue.addEventNow();
      leftMouseWasClicked = false;
    }
  }
}
