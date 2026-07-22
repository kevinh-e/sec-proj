package dev.kevinh.secproj.gui;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import dev.kevinh.secproj.SecurityProjectClient;
import dev.kevinh.secproj.tools.ClientOptions;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
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
  private static final Text REACH_TEXT = Text.translatable("gui.secproj.menu_screen.reach");
  private static final Text CRITICALS_TEXT = Text.translatable("gui.secproj.menu_screen.criticals");
  private static final Text MACE_TEXT = Text.translatable("gui.secproj.menu_screen.mace");
  private static Text maceHeightText;

  private static final int OPTION_WIDTH = 140;
  private static final int OVERLAY_SIZE = 20;
  private static final int GRID_SPACING = 4;
  private static final int COLUMNS = 4;
  private static final int SLIDER_WIDTH_SPAN = (OPTION_WIDTH + GRID_SPACING + OVERLAY_SIZE) * 2 + GRID_SPACING;

  private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this, 21, 35);
  private ClientOptions clientOptions = SecurityProjectClient.getClientOptions();

  public SecprojMenuScreen() {
    super(TITLE_TEXT);
  }

  @Override
  protected void init() {
    DirectionalLayoutWidget mainLayout = this.layout.addHeader(DirectionalLayoutWidget.vertical().spacing(8));

    mainLayout.add(new TextWidget(TITLE_TEXT, this.textRenderer), Positioner::alignHorizontalCenter);

    GridWidget mainGrid = new GridWidget().setSpacing(GRID_SPACING);
    GridWidget.Adder adder = mainGrid.createAdder(COLUMNS);

    // Each "feature set" is a pair of columns: feature toggle + overlay toggle.
    // We lay out two complete sets per row to make better use of horizontal space.
    addFeaturePair(adder, NOFALL_TEXT, clientOptions.isNoFallEnabled(),
        clientOptions::setNoFall,
        clientOptions::isNoFallShownInOverlay,
        clientOptions::setNoFallShownInOverlay,
        AUTOCLICKER_TEXT, clientOptions.isAutoClickerEnabled(),
        clientOptions::setAutoClicker,
        clientOptions::isAutoClickerShownInOverlay,
        clientOptions::setAutoClickerShownInOverlay);

    addFeaturePair(adder, FREECAM_TEXT, clientOptions.isFreecamEnabled(),
        clientOptions::setFreecam,
        clientOptions::isFreecamShownInOverlay,
        clientOptions::setFreecamShownInOverlay,
        STEP_TEXT, clientOptions.isStepEnabled(),
        clientOptions::setStep,
        clientOptions::isStepShownInOverlay,
        clientOptions::setStepShownInOverlay);

    addFeaturePair(adder, FULLBRIGHT_TEXT, clientOptions.isFullBrightEnabled(),
        clientOptions::setFullBrightEnabled,
        clientOptions::isFullBrightShownInOverlay,
        clientOptions::setFullBrightShownInOverlay,
        REACH_TEXT, clientOptions.isReachEnabled(),
        clientOptions::setReachEnabled,
        clientOptions::isReachShownInOverlay,
        clientOptions::setReachShownInOverlay);

    double freecamSpeed = clientOptions.getFreecamSpeed();
    freecamSpeedText = Text
        .translatable("gui.secproj.menu_screen.freecam_speed")
        .append(Text.literal(": " + String.format("%.2f", freecamSpeed)));
    double normalizedFreecamSpeed = (freecamSpeed - ClientOptions.FREECAM_SPEED_MIN)
        / (ClientOptions.FREECAM_SPEED_MAX - ClientOptions.FREECAM_SPEED_MIN);

    // Freecam speed slider + overlay occupies the first feature set (2 columns).
    adder.add(new SliderWidget(0, 0, OPTION_WIDTH + GRID_SPACING + OVERLAY_SIZE, 20,
        freecamSpeedText, normalizedFreecamSpeed) {
      @Override
      protected void updateMessage() {
        this.setMessage(Text.translatable("gui.secproj.menu_screen.freecam_speed")
            .append(Text.literal(String.format(": %.2f", clientOptions.getFreecamSpeed()))));
      }

      @Override
      protected void applyValue() {
        clientOptions.setFreecamSpeed(ClientOptions.FREECAM_SPEED_MIN
            + this.value * (ClientOptions.FREECAM_SPEED_MAX - ClientOptions.FREECAM_SPEED_MIN));
      }
    }, 2);
    adder.add(this.buildOverlayButton(
        clientOptions::isFreecamSpeedShownInOverlay,
        clientOptions::setFreecamSpeedShownInOverlay));

    // CPS is overlay-only, so it occupies the second feature set (2 columns).
    adder.add(new TextWidget(CPS_TEXT, this.textRenderer));
    adder.add(this.buildOverlayButton(
        clientOptions::isCpsShownInOverlay,
        clientOptions::setCpsShownInOverlay));

    addFeaturePair(adder, CRITICALS_TEXT, clientOptions.isCriticalsEnabled(),
        clientOptions::setCriticalsEnabled,
        clientOptions::isCriticalsShownInOverlay,
        clientOptions::setCriticalsShownInOverlay,
        MACE_TEXT, clientOptions.isMaceEnabled(),
        clientOptions::setMaceEnabled,
        clientOptions::isMaceShownInOverlay,
        clientOptions::setMaceShownInOverlay);

    adder.add(buildReachSlider("gui.secproj.menu_screen.block_reach",
        clientOptions.getBlockReachValue(), clientOptions::setBlockReachValue, SLIDER_WIDTH_SPAN), COLUMNS);
    adder.add(buildReachSlider("gui.secproj.menu_screen.entity_reach",
        clientOptions.getEntityReachValue(), clientOptions::setEntityReachValue, SLIDER_WIDTH_SPAN), COLUMNS);

    double maceHeight = clientOptions.getMaceHeight();
    maceHeightText = Text
        .translatable("gui.secproj.menu_screen.mace_height")
        .append(Text.literal(String.format(": %.2f", maceHeight)));
    double normalizedMaceHeight = (maceHeight - ClientOptions.MACE_HEIGHT_MIN)
        / (ClientOptions.MACE_HEIGHT_MAX - ClientOptions.MACE_HEIGHT_MIN);

    adder.add(new SliderWidget(0, 0, OPTION_WIDTH + GRID_SPACING + OVERLAY_SIZE, 20,
        maceHeightText, normalizedMaceHeight) {
      @Override
      protected void updateMessage() {
        this.setMessage(Text.translatable("gui.secproj.menu_screen.mace_height")
            .append(Text.literal(String.format(": %.2f", clientOptions.getMaceHeight()))));
      }

      @Override
      protected void applyValue() {
        clientOptions.setMaceHeight(ClientOptions.MACE_HEIGHT_MIN
            + this.value * (ClientOptions.MACE_HEIGHT_MAX - ClientOptions.MACE_HEIGHT_MIN));
      }
    }, 2);
    adder.add(this.buildOverlayButton(
        clientOptions::isMaceHeightShownInOverlay,
        clientOptions::setMaceHeightShownInOverlay));

    int maxBodyHeight = Math.max(100,
        this.height - this.layout.getHeaderHeight() - this.layout.getFooterHeight() - 30);
    mainGrid.setSpacing(GRID_SPACING);
    mainGrid.refreshPositions();
    int bodyHeight = Math.min(mainGrid.getHeight(), maxBodyHeight);
    ScrollableGridWidget scrollableBody = new ScrollableGridWidget(
        0, 0, Math.min(this.width, mainGrid.getWidth() + ScrollableGridWidget.scrollbarWidth()),
        Math.max(20, bodyHeight), mainGrid);
    this.layout.addBody(scrollableBody);

    this.layout.addFooter(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close()).width(200).build());

    this.layout.forEachChild(this::addDrawableChild);
    this.layout.refreshPositions();
    scrollableBody.setX((this.width - scrollableBody.getWidth()) / 2);
    scrollableBody.refreshLayout();
  }

  private void addFeaturePair(GridWidget.Adder adder,
      Text leftLabel, boolean leftEnabled, Consumer<Boolean> leftSetter,
      BooleanSupplier leftOverlayShown, Consumer<Boolean> leftOverlaySetter,
      Text rightLabel, boolean rightEnabled, Consumer<Boolean> rightSetter,
      BooleanSupplier rightOverlayShown, Consumer<Boolean> rightOverlaySetter) {
    adder.add(CyclingButtonWidget.onOffBuilder(leftEnabled).build(leftLabel,
        (button, val) -> leftSetter.accept(val)));
    adder.add(this.buildOverlayButton(leftOverlayShown, leftOverlaySetter));
    adder.add(CyclingButtonWidget.onOffBuilder(rightEnabled).build(rightLabel,
        (button, val) -> rightSetter.accept(val)));
    adder.add(this.buildOverlayButton(rightOverlayShown, rightOverlaySetter));
  }

  private SliderWidget buildReachSlider(String labelKey, double currentValue,
      java.util.function.DoubleConsumer onValueChanged, int width) {
    double normalized = (currentValue - ClientOptions.REACH_MIN)
        / (ClientOptions.REACH_MAX - ClientOptions.REACH_MIN);
    return new SliderWidget(0, 0, width, 20,
        Text.translatable(labelKey).append(Text.literal(String.format(": %.1f", currentValue))),
        normalized) {
      @Override
      protected void updateMessage() {
        double value = ClientOptions.REACH_MIN + this.value * (ClientOptions.REACH_MAX - ClientOptions.REACH_MIN);
        this.setMessage(Text.translatable(labelKey)
            .append(Text.literal(String.format(": %.1f", value))));
      }

      @Override
      protected void applyValue() {
        onValueChanged.accept(ClientOptions.REACH_MIN
            + this.value * (ClientOptions.REACH_MAX - ClientOptions.REACH_MIN));
      }
    };
  }

  private ButtonWidget buildOverlayButton(BooleanSupplier currentValue, Consumer<Boolean> setter) {
    ButtonWidget button = ButtonWidget.builder(
        Text.literal(currentValue.getAsBoolean() ? "\u2713" : "\u2717"),
        b -> {
          boolean newValue = !currentValue.getAsBoolean();
          setter.accept(newValue);
          b.setMessage(Text.literal(newValue ? "\u2713" : "\u2717"));
        }).size(OVERLAY_SIZE, 20).build();
    return button;
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
