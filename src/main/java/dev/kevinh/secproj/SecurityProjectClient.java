package dev.kevinh.secproj;

import org.lwjgl.glfw.GLFW;

import dev.kevinh.secproj.gui.Overlay;
import dev.kevinh.secproj.gui.SecprojMenuScreen;
import dev.kevinh.secproj.tools.ClientOptions;
import dev.kevinh.secproj.tools.TimedQueue;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;

public class SecurityProjectClient implements ClientModInitializer {
  private MinecraftClient client = MinecraftClient.getInstance();

  private static ClientOptions clientOptions = new ClientOptions();
  public static Overlay overlay = new Overlay();
  public static final TimedQueue clickQueue = new TimedQueue();

  public KeyBinding OPEN_MENU_KEY;
  private boolean leftMouseWasClicked = false;
  private boolean rightMouseWasClicked = false;

  @Override
  public void onInitializeClient() {
    SecurityProject.LOGGER.info("Setting up " + SecurityProject.MOD_ID + "Client");

    overlay.init();
    this.initializeKeymappings();
    ClientTickEvents.END_CLIENT_TICK.register(client -> fetchClientTickEvents(client));

    // draw shi
    HudRenderCallback.EVENT.register((context, tickDeltaManager) -> {
      fetchRenderTickEvents(context, tickDeltaManager);
      overlay.renderClickerOverlay(context, tickDeltaManager, clickQueue);
    });
  }

  private void fetchClientTickEvents(MinecraftClient tickClient) {
    if (this.OPEN_MENU_KEY.wasPressed()) {
      tickClient.setScreen(new SecprojMenuScreen());
    }
  }

  private void fetchRenderTickEvents(DrawContext context, RenderTickCounter tickDeltaManager) {
    if (this.client.mouse.wasRightButtonClicked()) {
      rightMouseWasClicked = true;
    } else if (rightMouseWasClicked && !this.client.mouse.wasRightButtonClicked()) {
      rightMouseWasClicked = false;
      clientOptions.toggleAutoClicker();
    }

    if (this.client.mouse.wasLeftButtonClicked()) {
      leftMouseWasClicked = true;
    } else if (leftMouseWasClicked && !this.client.mouse.wasLeftButtonClicked()) {
      clickQueue.addEventNow();
      leftMouseWasClicked = false;
    }
  }

  private void initializeKeymappings() {
    this.OPEN_MENU_KEY = KeyBindingHelper
        .registerKeyBinding(new KeyBinding("key.secproj.menu", GLFW.GLFW_KEY_G, "key.categories.secproj"));
  }

  public static ClientOptions getClientOptions() {
    return clientOptions;
  }
}
