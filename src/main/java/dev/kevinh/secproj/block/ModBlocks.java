package dev.kevinh.secproj.block;

import dev.kevinh.secproj.SecurityProject;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
  private static final Block EXAMPLE_BLOCK = registerBlock("example_block",
      new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.ANVIL)));

  private static Block registerBlock(String name, Block block) {
    registerBlockItem(name, block);
    return Registry.register(Registries.BLOCK, Identifier.of(SecurityProject.MOD_ID, name), block);
  }

  private static void registerBlockItem(String name, Block block) {
    Registry.register(Registries.ITEM, Identifier.of(SecurityProject.MOD_ID, name),
        new BlockItem(block, new Item.Settings()));
  }

  public static void registerModBlocks() {
    SecurityProject.LOGGER.info("Registering Mod blocks for " + SecurityProject.MOD_ID);
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
      entries.add(ModBlocks.EXAMPLE_BLOCK);
    });
  }
}
