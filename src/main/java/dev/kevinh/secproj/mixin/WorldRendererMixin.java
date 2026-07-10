package dev.kevinh.secproj.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.kevinh.secproj.SecurityProjectClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

  @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;getFocusedEntity()Lnet/minecraft/entity/Entity;", ordinal = 0))
  public Entity alwaysRenderPlayerFreeCam(Camera camera) {
    if (SecurityProjectClient.getClientOptions().isFreecamEnabled())
      return null;
    else
      return camera.getFocusedEntity();
  }

}
