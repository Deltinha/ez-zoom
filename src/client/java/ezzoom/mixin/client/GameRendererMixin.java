package ezzoom.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ezzoom.EzZoomClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
	private void applyZoom(Camera camera, float tickDelta, boolean changingFov,
			CallbackInfoReturnable<Float> cir) {
		float originalFov = cir.getReturnValue();
		float zoomedFov = EzZoomClient.applyZoom(originalFov);
		cir.setReturnValue(zoomedFov);
	}
}