package ezzoom;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.InputUtil;

public class EzZoomClient implements ClientModInitializer {

	private static final KeyBinding zoomKey = KeyBindingHelper.registerKeyBinding(
			new KeyBinding("key.ezzoom.zoom",
					InputUtil.Type.KEYSYM,
					GLFW.GLFW_KEY_V,
					"category.ezzoom"));

	private static final double ZOOM_LEVEL = 3.0;
	private static Double defaultMouseSensitivity = null;
	private static boolean wasZoomingLastTick = false;

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			boolean isCurrentlyZooming = isZoomKeyPressed();

			if (isCurrentlyZooming != wasZoomingLastTick) {
				if (isCurrentlyZooming) {
					saveMouseSensitivity();
				} else {
					restoreMouseSensitivity();
				}
				wasZoomingLastTick = isCurrentlyZooming;
			}

			if (isCurrentlyZooming) {
				applyZoomSensitivity();
			}
		});
	}

	public static float applyZoom(float fov) {
		if (isZoomKeyPressed()) {
			return (float) (fov / ZOOM_LEVEL);
		}
		return fov;
	}

	private static boolean isZoomKeyPressed() {
		return zoomKey.isPressed();
	}

	private static void saveMouseSensitivity() {
		if (defaultMouseSensitivity == null) {
			MinecraftClient client = MinecraftClient.getInstance();
			defaultMouseSensitivity = client.options.getMouseSensitivity().getValue();
		}
	}

	private static void applyZoomSensitivity() {
		if (defaultMouseSensitivity != null) {
			MinecraftClient client = MinecraftClient.getInstance();
			SimpleOption<Double> mouseSensitivitySetting = client.options.getMouseSensitivity();
			mouseSensitivitySetting.setValue(defaultMouseSensitivity / ZOOM_LEVEL);
		}
	}

	private static void restoreMouseSensitivity() {
		if (defaultMouseSensitivity != null) {
			MinecraftClient client = MinecraftClient.getInstance();
			SimpleOption<Double> mouseSensitivitySetting = client.options.getMouseSensitivity();
			mouseSensitivitySetting.setValue(defaultMouseSensitivity);
		}
	}
}
