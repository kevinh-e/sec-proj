package dev.kevinh.secproj.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.Spawner;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

public class WorldRender {
  public static void highlightBlockEntities(WorldRenderContext ctx) {
    ArrayList<WorldChunk> chunks = getClientChunks(MinecraftClient.getInstance());
    Map<BlockPos, BlockEntity> blockEntities = getBlockEntities(chunks);
    for (Map.Entry<BlockPos, BlockEntity> entry : blockEntities.entrySet()) {
      BlockPos pos = entry.getKey();
      BlockEntity blockEntity = entry.getValue();
      if (blockEntity != null) {
        switch (blockEntity) {
          case TrappedChestBlockEntity cbe -> Render3d.drawStorageBox(ctx, pos, 1.0f, 0.75f, 0.0f);
          case ChestBlockEntity cbe -> Render3d.drawStorageBox(ctx, pos, 1.0f, 1.0f, 0.0f);
          case ShulkerBoxBlockEntity cbe -> Render3d.drawStorageBox(ctx, pos, 0.0f, 0.8f, 0.8f);
          case SignBlockEntity cbe -> Render3d.drawStorageBox(ctx, pos, 0.9f, 0.9f, 0.9f);
          case AbstractFurnaceBlockEntity cbe -> Render3d.drawStorageBox(ctx, pos, 1.0f, 0.4f, 0.0f);
          case Spawner cbe -> Render3d.drawStorageBox(ctx, pos, 0.0f, 1.0f, 0.0f);
          case EnderChestBlockEntity cbe -> Render3d.drawStorageBox(ctx, pos, 0.6f, 0.0f, 1.0f);
          default -> Render3d.drawStorageBox(ctx, pos, 0.5f, 0.5f, 0.5f);
        }
      }
    }
  }

  public static ArrayList<WorldChunk> getClientChunks(MinecraftClient client) {
    ArrayList<WorldChunk> chunks = new ArrayList<WorldChunk>();
    ChunkPos playerChunk = client.player.getChunkPos();
    Integer rd = client.options.getViewDistance().getValue();
    for (int x = playerChunk.x - rd; x < playerChunk.x + rd; x++) {
      for (int z = playerChunk.z - rd; z < playerChunk.z + rd; z++) {
        if (client.world.isChunkLoaded(x, z)) {
          chunks.add(client.world.getChunk(x, z));
        }
      }
    }
    return chunks;
  }

  public static Map<BlockPos, BlockEntity> getBlockEntities(ArrayList<WorldChunk> chunks) {
    Map<BlockPos, BlockEntity> blockEntities = new HashMap<BlockPos, BlockEntity>();
    for (WorldChunk chunk : chunks) {
      blockEntities.putAll(chunk.getBlockEntities());
    }

    return blockEntities;
  }

}
