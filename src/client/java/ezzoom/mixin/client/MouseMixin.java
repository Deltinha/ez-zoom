package ezzoom.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;

import ezzoom.EzZoomClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.player.PlayerInventory;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(at = @At("HEAD"), method = "onMouseScroll(JDD)V")
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        EzZoomClient.handleMouseScroll(vertical);
    }

    @WrapWithCondition(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;setSelectedSlot(I)V"), method = "onMouseScroll(JDD)V")
    private boolean shouldAllowHotbarScrolling(PlayerInventory inventory, int slot) {
        return !EzZoomClient.shouldPreventHotbarScrolling();
    }
}