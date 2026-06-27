package dev.kevinh.secproj.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.kevinh.secproj.SecurityProject;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.gui.screen.TitleScreen;

@Mixin(TitleScreen.class)
public class TitleMixin {
  @Shadow
  private SplashTextRenderer splashText;

  @Inject(method = "init", at = @At("HEAD"))
  protected void injectTitleScreenInit(CallbackInfo info) {
    SecurityProject.LOGGER.info("Mixin hello world from " + SecurityProject.MOD_ID);
    this.splashText = new SplashTextRenderer("security project");
  }
}
