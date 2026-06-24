package dev.kevinh.secproj;

import net.fabricmc.api.ClientModInitializer;

public class SecurityProjectClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    SecurityProject.LOGGER.info("Hello Fabric world from the client!");
  }
}
