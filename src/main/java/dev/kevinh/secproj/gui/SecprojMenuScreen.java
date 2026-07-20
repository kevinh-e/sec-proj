package dev.kevinh.secproj.gui;

import dev.kevinh.secproj.SecurityProjectClient;
import dev.kevinh.secproj.tools.ClientOptions;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Positioner;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class SecprojMenuScreen extends Screen {
  private static final Text TITLE_TEXT = Text.translatable("gui.secproj.menu_screen.title");
  private static final Text NOFALL_TEXT = Text.translatable("gui.secproj.menu_screen.nofall");
  private static final Text AUTOCLICKER_TEXT = Text.translatable("gui.secproj.menu_screen.autoclicker");
  private static final Text FREECAM_TEXT = Text.translatable("gui.secproj.menu_screen.freecam");
  private static Text freecamSpeedText;
  private static final Text STEP_TEXT = Text.translatable("gui.secproj.menu_screen.step");
  private static final Text FULLBRIGHT_TEXT = Text.translatable("gui.secproj.menu_screen.fullbright");
  private static final Text CPS_TEXT = Text.translatable("gui.secproj.menu_screen.cps");
  private static final Text OVERLAY_TEXT = Text.translatable("gui.secproj.menu_screen.overlay");
  private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this, 61, 33);
  private ClientOptions clientOptions = SecurityProjectClient.getClientOptions();

  public SecprojMenuScreen() {
    super(TITLE_TEXT);
  }

  @Override
  protected void init() {
    DirectionalLayoutWidget mainLayout = this.layout.addHeader(DirectionalLayoutWidget.vertical().spacing(8));

    mainLayout.add(new TextWidget(TITLE_TEXT, this.textRenderer), Positioner::alignHorizontalCenter);

    GridWidget mainGrid = new GridWidget().setSpacing(2);
    GridWidget.Adder adder = mainGrid.createAdder(2);

    // Column 1: enable/disable the feature. Column 2: show/hide it in the overlay.
    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isNoFallEnabled()).build(NOFALL_TEXT,
            (button, val) -> clientOptions.setNoFall(val)));
    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isNoFallShownInOverlay()).build(OVERLAY_TEXT,
            (button, val) -> clientOptions.setNoFallShownInOverlay(val)));

    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isAutoClickerEnabled()).build(AUTOCLICKER_TEXT,
            (button, val) -> clientOptions.setAutoClicker(val)));
    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isAutoClickerShownInOverlay()).build(OVERLAY_TEXT,
            (button, val) -> clientOptions.setAutoClickerShownInOverlay(val)));

    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isFreecamEnabled()).build(FREECAM_TEXT,
            (button, val) -> clientOptions.setFreecam(val)));
    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isFreecamShownInOverlay()).build(OVERLAY_TEXT,
            (button, val) -> clientOptions.setFreecamShownInOverlay(val)));

    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isStepEnabled()).build(STEP_TEXT,
            (button, val) -> clientOptions.setStep(val)));
    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isStepShownInOverlay()).build(OVERLAY_TEXT,
            (button, val) -> clientOptions.setStepShownInOverlay(val)));

    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isFullBrightEnabled()).build(FULLBRIGHT_TEXT,
            (button, val) -> clientOptions.setFullBrightEnabled(val)));
    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isFullBrightShownInOverlay()).build(OVERLAY_TEXT,
            (button, val) -> clientOptions.setFullBrightShownInOverlay(val)));

    double freecamSpeed = clientOptions.getFreecamSpeed();
    freecamSpeedText = Text
        .translatable("gui.secproj.menu_screen.freecam_speed" + ": " + freecamSpeed);
    double normalizedFreecamSpeed = (freecamSpeed - ClientOptions.FREECAM_SPEED_MIN)
        / (ClientOptions.FREECAM_SPEED_MAX - ClientOptions.FREECAM_SPEED_MIN);

    adder.add(new SliderWidget(0, 0, 200, 20, freecamSpeedText, normalizedFreecamSpeed) {
      @Override
      protected void updateMessage() {
        this.setMessage(Text.literal(String.format("Speed: %.2f", clientOptions.getFreecamSpeed())));
      }

      @Override
      protected void applyValue() {
        clientOptions.setFreecamSpeed(ClientOptions.FREECAM_SPEED_MIN
            + this.value * (ClientOptions.FREECAM_SPEED_MAX - ClientOptions.FREECAM_SPEED_MIN));
      }
    });
    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isFreecamSpeedShownInOverlay()).build(OVERLAY_TEXT,
            (button, val) -> clientOptions.setFreecamSpeedShownInOverlay(val)));

    adder.add(new TextWidget(CPS_TEXT, this.textRenderer));
    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isCpsShownInOverlay()).build(OVERLAY_TEXT,
            (button, val) -> clientOptions.setCpsShownInOverlay(val)));

    this.layout.addBody(mainGrid);
    this.layout.addFooter(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close()).width(200).build());

    this.layout.forEachChild(element -> {
      ClickableWidget widget = this.addDrawableChild(element);
    });
    layout.refreshPositions();
  }

  @Override
  public void render(DrawContext context, int mouseX, int mouseY, float delta) {
    super.render(context, mouseX, mouseY, delta);
  }

  @Override
  public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
    this.renderInGameBackground(context);
  }

  @Override
  public boolean shouldPause() {
    return false;
  }
}
