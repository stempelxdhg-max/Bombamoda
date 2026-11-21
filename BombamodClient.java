package pl.bombamod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class BombamodClient implements ClientModInitializer {
    private static KeyBinding openPanel;

    @Override
    public void onInitializeClient() {
        openPanel = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.bombamod.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F6,
                "category.bombamod"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openPanel.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new DiagnosticsScreen());
                }
            }
        });
    }
}
