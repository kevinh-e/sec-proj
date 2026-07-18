package dev.kevinh.secproj.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.kevinh.secproj.SecurityProjectClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;

@Mixin(SimpleOption.class)
public class SimpleOptionMixin {

  @Inject(method = "getValue", at = @At("RETURN"), cancellable = true)
  public void injectGammaOption(CallbackInfoReturnable<Double> cir) {
    MinecraftClient client = MinecraftClient.getInstance();
    SimpleOption<?> instance = (SimpleOption<?>) (Object) this;
    if (SecurityProjectClient.getClientOptions().isFullBrightEnabled()) {
      if (client != null && client.options != null && instance == client.options.getGamma())
        cir.setReturnValue(100.0);
    }
  }
}
