package dev.kevinh.secproj;

import org.lwjgl.glfw.GLFW;

import dev.kevinh.secproj.gui.Overlay;
import dev.kevinh.secproj.gui.SecprojMenuScreen;
import dev.kevinh.secproj.tools.ClientOptions;
import dev.kevinh.secproj.tools.ClientState;
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
  private MinecraftClient client;

  private static ClientOptions clientOptions = new ClientOptions();
  public static final TimedQueue clickQueue = new TimedQueue();
  public static ClientState clientState = new ClientState();

  public KeyBinding OPEN_MENU_KEY;
  public KeyBinding AUTOCLICKER_TOGGLE_KEY;
  public KeyBinding NOFALL_TOGGLE_KEY;
  public KeyBinding FREECAM_TOGGLE_KEY;
  public KeyBinding STEP_TOGGLE_KEY;
  public KeyBinding FULLBRIGHT_TOGGLE_KEY;

  private boolean leftMouseWasClicked = false;

  @Override
  public void onInitializeClient() {
    SecurityProject.LOGGER.info("Setting up " + SecurityProject.MOD_ID + "Client");
    client = MinecraftClient.getInstance();

    this.initializeKeymappings();
    ClientTickEvents.END_CLIENT_TICK.register(client -> fetchClientTickEvents(client));

    // draw shi
    HudRenderCallback.EVENT.register((context, tickDeltaManager) -> {
      fetchRenderTickEvents(context, tickDeltaManager);
      Overlay.renderOverlay(context, tickDeltaManager, clickQueue);
    });
  }

  private void fetchClientTickEvents(MinecraftClient tickClient) {
    if (this.OPEN_MENU_KEY.wasPressed()) {
      tickClient.setScreen(new SecprojMenuScreen());
    }
    if (this.AUTOCLICKER_TOGGLE_KEY.wasPressed()) {
      clientOptions.setAutoClicker(!clientOptions.isAutoClickerEnabled());
    }
    if (this.NOFALL_TOGGLE_KEY.wasPressed()) {
      clientOptions.setNoFall(!clientOptions.isNoFallEnabled());
    }
    if (this.FREECAM_TOGGLE_KEY.wasPressed()) {
      clientOptions.setFreecam(!clientOptions.isFreecamEnabled());
    }
    if (this.STEP_TOGGLE_KEY.wasPressed()) {
      clientOptions.setStep(!clientOptions.isStepEnabled());
    }
    if (this.FULLBRIGHT_TOGGLE_KEY.wasPressed()) {
      clientOptions.setFullBrightEnabled(!clientOptions.isFullBrightEnabled());
    }
  }

  private void fetchRenderTickEvents(DrawContext context, RenderTickCounter tickDeltaManager) {
    if (client.mouse.wasLeftButtonClicked()) {
      leftMouseWasClicked = true;
    } else if (leftMouseWasClicked && !client.mouse.wasLeftButtonClicked()) {
      clickQueue.addEventNow();
      leftMouseWasClicked = false;
    }
  }

  private void initializeKeymappings() {
    this.OPEN_MENU_KEY = KeyBindingHelper
        .registerKeyBinding(new KeyBinding("key.secproj.menu", GLFW.GLFW_KEY_G, "key.categories.secproj"));
    this.AUTOCLICKER_TOGGLE_KEY = KeyBindingHelper
        .registerKeyBinding(
            new KeyBinding("key.secproj.autoclicker", GLFW.GLFW_KEY_RIGHT_BRACKET, "key.categories.secproj"));
    this.NOFALL_TOGGLE_KEY = KeyBindingHelper
        .registerKeyBinding(
            new KeyBinding("key.secproj.nofall", GLFW.GLFW_KEY_LEFT_BRACKET, "key.categories.secproj"));
    this.FREECAM_TOGGLE_KEY = KeyBindingHelper
        .registerKeyBinding(
            new KeyBinding("key.secproj.freecam", GLFW.GLFW_KEY_N, "key.categories.secproj"));
    this.STEP_TOGGLE_KEY = KeyBindingHelper
        .registerKeyBinding(
            new KeyBinding("key.secproj.step", GLFW.GLFW_KEY_SEMICOLON, "key.categories.secproj"));
    this.FULLBRIGHT_TOGGLE_KEY = KeyBindingHelper
        .registerKeyBinding(
            new KeyBinding("key.secproj.fullbright", GLFW.GLFW_KEY_B, "key.categories.secproj"));
  }

  public static ClientOptions getClientOptions() {
    return clientOptions;
  }

  public static ClientState getClientState() {
    return clientState;
  }
}
