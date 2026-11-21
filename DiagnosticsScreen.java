package pl.bombamod;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class DiagnosticsScreen extends Screen {
    private static final int PANEL_WIDTH = 340;
    private static final int PANEL_HEIGHT = 218;

    private String status;
    private int statusColor = 0xFFB7C0CC;

    public DiagnosticsScreen() {
        super(Text.translatable("screen.bombamod.title"));
        this.status = Text.translatable("screen.bombamod.ready").getString();
    }

    @Override
    protected void init() {
        int left = (this.width - PANEL_WIDTH) / 2;
        int top = (this.height - PANEL_HEIGHT) / 2;
        int buttonY = top + 151;

        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("screen.bombamod.scan"),
                button -> runLogScan()
        ).dimensions(left + 22, buttonY, 190, 24).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("screen.bombamod.close"),
                button -> this.close()
        ).dimensions(left + 220, buttonY, 98, 24).build());
    }

    private void runLogScan() {
        LocalLogScanner.ScanResult result = LocalLogScanner.scanLatestLog();
        this.status = result.message();
        this.statusColor = result.success() ? 0xFF77D69A : 0xFFFFB36B;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        int left = (this.width - PANEL_WIDTH) / 2;
        int top = (this.height - PANEL_HEIGHT) / 2;
        int right = left + PANEL_WIDTH;
        int bottom = top + PANEL_HEIGHT;

        // Main card and subtle header accent.
        context.fill(left, top, right, bottom, 0xF0181B22);
        context.fill(left, top, right, top + 3, 0xFFE34B4B);
        context.fill(left + 1, top + 1, right - 1, bottom - 1, 0xF01D222B);

        context.drawTextWithShadow(this.textRenderer,
                Text.translatable("screen.bombamod.title"),
                left + 22, top + 20, 0xFFFFFFFF);
        context.drawTextWithShadow(this.textRenderer,
                Text.translatable("screen.bombamod.subtitle"),
                left + 23, top + 39, 0xFFE36A6A);

        // Status panel.
        context.fill(left + 18, top + 68, right - 18, top + 130, 0xFF151920);
        context.fill(left + 18, top + 68, left + 21, top + 130, 0xFFE34B4B);
        context.drawTextWithShadow(this.textRenderer,
                Text.literal("LOCAL LOG AUDIT"),
                left + 31, top + 80, 0xFFB7C0CC);
        context.drawTextWithShadow(this.textRenderer,
                Text.literal(status),
                left + 31, top + 101, statusColor);

        context.drawTextWithShadow(this.textRenderer,
                Text.translatable("screen.bombamod.note"),
                left + 22, top + 139, 0xFF8F9AA8);

        Text footer = Text.translatable("screen.bombamod.footer");
        int footerWidth = this.textRenderer.getWidth(footer);
        context.drawTextWithShadow(this.textRenderer, footer,
                right - footerWidth - 20, bottom - 17, 0xFF687382);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
