package dev.kevinh.secproj;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.kevinh.secproj.block.ModBlocks;
import dev.kevinh.secproj.item.ModItems;

public class SecurityProject implements ModInitializer {
  public static final String MOD_ID = "secproj";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  @Override
  public void onInitialize() {
    ModItems.registerModItems();
    ModBlocks.registerModBlocks();

  }

  public static Identifier id(String path) {
    return Identifier.of(MOD_ID, path);
  }
}
