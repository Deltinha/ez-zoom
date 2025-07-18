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

	private static final double DEFAULT_ZOOM_LEVEL = 3.0;
	private static final double MIN_ZOOM_LEVEL = 1.0;
	private static final double MAX_ZOOM_LEVEL = 50.0;

	private static Double currentZoomLevel = null;
	private static Double defaultMouseSensitivity = null;
	private static boolean wasZoomingLastTick = false;

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			boolean isCurrentlyZooming = isZoomKeyPressed();

			if (isCurrentlyZooming != wasZoomingLastTick) {
				if (isCurrentlyZooming) {
					saveMouseSensitivity();
					currentZoomLevel = DEFAULT_ZOOM_LEVEL;
				} else {
					restoreMouseSensitivity();
					currentZoomLevel = null;
				}
				wasZoomingLastTick = isCurrentlyZooming;
			}

			if (isCurrentlyZooming) {
				applyZoomSensitivity();
			}
		});
	}

	private static boolean isZoomKeyPressed() {
		return zoomKey.isPressed();
	}

	public static boolean shouldPreventHotbarScrolling() {
		return isZoomKeyPressed();
	}

	public static float applyZoom(float fov) {
		if (isZoomKeyPressed() && currentZoomLevel != null) {
			return (float) (fov / currentZoomLevel);
		}
		return fov;
	}

	public static void handleMouseScroll(double scrollAmount) {
		if (!isZoomKeyPressed()) {
			return;
		}

		if (currentZoomLevel == null) {
			currentZoomLevel = DEFAULT_ZOOM_LEVEL;
		}

		if (scrollAmount > 0) {
			currentZoomLevel *= 1.1;
		} else if (scrollAmount < 0) {
			currentZoomLevel *= 0.9;
		}

		currentZoomLevel = clampZoomLevel(currentZoomLevel);
	}

	public static Double clampZoomLevel(Double zoomLevel) {
		return Math.max(MIN_ZOOM_LEVEL, Math.min(MAX_ZOOM_LEVEL, zoomLevel));
	}

	private static void saveMouseSensitivity() {
		if (defaultMouseSensitivity == null) {
			MinecraftClient client = MinecraftClient.getInstance();
			defaultMouseSensitivity = client.options.getMouseSensitivity().getValue();
		}
	}

	private static void applyZoomSensitivity() {
		if (defaultMouseSensitivity != null && currentZoomLevel != null) {
			MinecraftClient client = MinecraftClient.getInstance();
			SimpleOption<Double> mouseSensitivitySetting = client.options.getMouseSensitivity();
			mouseSensitivitySetting.setValue(defaultMouseSensitivity / currentZoomLevel);
		}
	}

	private static void restoreMouseSensitivity() {
		if (defaultMouseSensitivity != null) {
			MinecraftClient client = MinecraftClient.getInstance();
			SimpleOption<Double> mouseSensitivitySetting = client.options.getMouseSensitivity();
			mouseSensitivitySetting.setValue(defaultMouseSensitivity);
			defaultMouseSensitivity = null;
		}
	}
}
