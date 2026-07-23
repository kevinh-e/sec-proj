package dev.kevinh.secproj;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.systems.RenderSystem;

import dev.kevinh.secproj.gui.Overlay;
import dev.kevinh.secproj.gui.SecprojMenuScreen;
import dev.kevinh.secproj.tools.ClientOptions;
import dev.kevinh.secproj.tools.ClientState;
import dev.kevinh.secproj.tools.Render3d;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

public class SecurityProjectClient implements ClientModInitializer {
  private MinecraftClient client;

  private static ClientOptions clientOptions = new ClientOptions();
  public static ClientState clientState = new ClientState();

  public KeyBinding OPEN_MENU_KEY;
  public KeyBinding AUTOCLICKER_TOGGLE_KEY;
  public KeyBinding NOFALL_TOGGLE_KEY;
  public KeyBinding FREECAM_TOGGLE_KEY;
  public KeyBinding STEP_TOGGLE_KEY;
  public KeyBinding FULLBRIGHT_TOGGLE_KEY;
  public KeyBinding REACH_TOGGLE_KEY;
  public KeyBinding CRITICALS_TOGGLE_KEY;
  public KeyBinding MACE_TOGGLE_KEY;

  @Override
  public void onInitializeClient() {
    SecurityProject.LOGGER.info("Setting up " + SecurityProject.MOD_ID + "Client");
    client = MinecraftClient.getInstance();

    this.initializeKeymappings();
    ClientTickEvents.END_CLIENT_TICK.register(client -> fetchClientTickEvents(client));

    // draw shi
    HudRenderCallback.EVENT.register((context, tickDeltaManager) -> {
      Overlay.renderOverlay(context, tickDeltaManager);
    });

    // draw 3d overlays (has render depth test disabled)
    WorldRenderEvents.AFTER_TRANSLUCENT.register(ctx -> {
      RenderSystem.disableDepthTest();

      Render3d.drawTestBox(ctx);

      RenderSystem.enableDepthTest();
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
    if (this.REACH_TOGGLE_KEY.wasPressed()) {
      clientOptions.setReachEnabled(!clientOptions.isReachEnabled());
    }
    if (this.CRITICALS_TOGGLE_KEY.wasPressed()) {
      clientOptions.setCriticalsEnabled(!clientOptions.isCriticalsEnabled());
    }
    if (this.MACE_TOGGLE_KEY.wasPressed()) {
      clientOptions.setMaceEnabled(!clientOptions.isMaceEnabled());
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
    this.REACH_TOGGLE_KEY = KeyBindingHelper
        .registerKeyBinding(
            new KeyBinding("key.secproj.reach", GLFW.GLFW_KEY_PERIOD, "key.categories.secproj"));
    this.CRITICALS_TOGGLE_KEY = KeyBindingHelper
        .registerKeyBinding(
            new KeyBinding("key.secproj.criticals", GLFW.GLFW_KEY_APOSTROPHE, "key.categories.secproj"));
    this.MACE_TOGGLE_KEY = KeyBindingHelper
        .registerKeyBinding(
            new KeyBinding("key.secproj.mace", GLFW.GLFW_KEY_MINUS, "key.categories.secproj"));
  }

  public static ClientOptions getClientOptions() {
    return clientOptions;
  }

  public static ClientState getClientState() {
    return clientState;
  }
}
