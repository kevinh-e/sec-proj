package dev.kevinh.secproj.gui;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

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
  private static final Text STEP_TEXT = Text.translatable("gui.secproj.menu_screen.step");
  private static final Text FULLBRIGHT_TEXT = Text.translatable("gui.secproj.menu_screen.fullbright");
  private static final Text REACH_TEXT = Text.translatable("gui.secproj.menu_screen.reach");
  private static final Text CRITICALS_TEXT = Text.translatable("gui.secproj.menu_screen.criticals");
  private static final Text MACE_TEXT = Text.translatable("gui.secproj.menu_screen.mace");

  private static final int OPTION_WIDTH = 150;
  private static final int OVERLAY_SIZE = 20;
  private static final int GRID_SPACING = 4;
  private static final int COLUMNS = 4;
  private static final int SLIDER_WIDTH_SPAN = 150;

  private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this, 21, 35);
  private final ClientOptions clientOptions = SecurityProjectClient.getClientOptions();

  public SecprojMenuScreen() {
    super(TITLE_TEXT);
  }

  @Override
  protected void init() {
    DirectionalLayoutWidget mainLayout = this.layout.addHeader(
        DirectionalLayoutWidget.vertical().spacing(8));
    mainLayout.add(new TextWidget(TITLE_TEXT, this.textRenderer), Positioner::alignHorizontalCenter);

    GridWidget mainGrid = new GridWidget().setSpacing(GRID_SPACING);
    GridWidget.Adder adder = mainGrid.createAdder(COLUMNS);

    addFeaturePair(adder,
        new ToggleFeature(NOFALL_TEXT,
            clientOptions::isNoFallEnabled, clientOptions::setNoFall,
            clientOptions::isNoFallShownInOverlay, clientOptions::setNoFallShownInOverlay),
        new ToggleFeature(AUTOCLICKER_TEXT,
            clientOptions::isAutoClickerEnabled, clientOptions::setAutoClicker,
            clientOptions::isAutoClickerShownInOverlay, clientOptions::setAutoClickerShownInOverlay));

    addFeaturePair(adder,
        new ToggleFeature(FREECAM_TEXT,
            clientOptions::isFreecamEnabled, clientOptions::setFreecam,
            clientOptions::isFreecamShownInOverlay, clientOptions::setFreecamShownInOverlay),
        new ToggleFeature(STEP_TEXT,
            clientOptions::isStepEnabled, clientOptions::setStep,
            clientOptions::isStepShownInOverlay, clientOptions::setStepShownInOverlay));

    addFeaturePair(adder,
        new ToggleFeature(FULLBRIGHT_TEXT,
            clientOptions::isFullBrightEnabled, clientOptions::setFullBrightEnabled,
            clientOptions::isFullBrightShownInOverlay, clientOptions::setFullBrightShownInOverlay),
        new ValueFeature(
            Text.translatable("gui.secproj.menu_screen.freecam_speed"),
            ClientOptions.FREECAM_SPEED_MIN,
            ClientOptions.FREECAM_SPEED_MAX,
            clientOptions::getFreecamSpeed,
            clientOptions::setFreecamSpeed,
            "%.2f",
            clientOptions::isFreecamSpeedShownInOverlay,
            clientOptions::setFreecamSpeedShownInOverlay));

    addFeaturePair(adder,
        new ToggleFeature(REACH_TEXT,
            clientOptions::isReachEnabled, clientOptions::setReachEnabled,
            clientOptions::isReachShownInOverlay, clientOptions::setReachShownInOverlay),
        new ToggleFeature(CRITICALS_TEXT,
            clientOptions::isCriticalsEnabled, clientOptions::setCriticalsEnabled,
            clientOptions::isCriticalsShownInOverlay, clientOptions::setCriticalsShownInOverlay));

    addReachSlider(adder,
        Text.translatable("gui.secproj.menu_screen.block_reach"),
        clientOptions.getBlockReachValue(), clientOptions::setBlockReachValue);
    addReachSlider(adder,
        Text.translatable("gui.secproj.menu_screen.entity_reach"),
        clientOptions.getEntityReachValue(), clientOptions::setEntityReachValue);

    addFeaturePair(adder,
        new ToggleFeature(MACE_TEXT,
            clientOptions::isMaceEnabled, clientOptions::setMaceEnabled,
            clientOptions::isMaceShownInOverlay, clientOptions::setMaceShownInOverlay),
        new ValueFeature(
            Text.translatable("gui.secproj.menu_screen.mace_height"),
            ClientOptions.MACE_HEIGHT_MIN,
            ClientOptions.MACE_HEIGHT_MAX,
            clientOptions::getMaceHeight,
            clientOptions::setMaceHeight,
            "%.2f",
            clientOptions::isMaceHeightShownInOverlay,
            clientOptions::setMaceHeightShownInOverlay));

    // Build scrollable body.
    int maxBodyHeight = Math.max(100,
        this.height - this.layout.getHeaderHeight() - this.layout.getFooterHeight() - 30);
    mainGrid.refreshPositions();
    int bodyHeight = Math.min(mainGrid.getHeight(), maxBodyHeight);
    ScrollableGridWidget scrollableBody = new ScrollableGridWidget(
        0, 0,
        Math.min(this.width, mainGrid.getWidth() + ScrollableGridWidget.scrollbarWidth()),
        Math.max(20, bodyHeight), mainGrid);
    this.layout.addBody(scrollableBody);

    this.layout.addFooter(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close()).width(200).build());

    this.layout.forEachChild(this::addDrawableChild);
    this.layout.refreshPositions();
    scrollableBody.setX((this.width - scrollableBody.getWidth()) / 2);
    scrollableBody.refreshLayout();
  }

  private record ToggleFeature(
      Text label,
      BooleanSupplier isEnabled,
      Consumer<Boolean> setEnabled,
      BooleanSupplier isShownInOverlay,
      Consumer<Boolean> setShownInOverlay) {
  }

  private record ValueFeature(
      Text label,
      double min,
      double max,
      DoubleSupplier getValue,
      DoubleConsumer setValue,
      String valueFormat,
      BooleanSupplier isShownInOverlay,
      Consumer<Boolean> setShownInOverlay) {
  }

  private void addFeaturePair(GridWidget.Adder adder, ToggleFeature left, Object right) {
    addToggleFeature(adder, left);
    if (right instanceof ToggleFeature toggle) {
      addToggleFeature(adder, toggle);
    } else if (right instanceof ValueFeature value) {
      addValueFeature(adder, value);
    }
  }

  private void addToggleFeature(GridWidget.Adder adder, ToggleFeature feature) {
    adder.add(CyclingButtonWidget.onOffBuilder(feature.isEnabled.getAsBoolean())
        .build(feature.label, (button, value) -> feature.setEnabled.accept(value)));
    adder.add(buildOverlayButton(feature.isShownInOverlay, feature.setShownInOverlay));
  }

  private void addValueFeature(GridWidget.Adder adder, ValueFeature feature) {
    adder.add(buildSlider(feature.label, feature.min, feature.max, feature.getValue.getAsDouble(),
        feature.setValue, feature.valueFormat, OPTION_WIDTH));
    adder.add(buildOverlayButton(feature.isShownInOverlay, feature.setShownInOverlay));
  }

  private void addReachSlider(GridWidget.Adder adder, Text label, double value, DoubleConsumer setter) {
    adder.add(buildSlider(label, ClientOptions.REACH_MIN, ClientOptions.REACH_MAX,
        value, setter, "%.1f", SLIDER_WIDTH_SPAN), COLUMNS);
  }

  private SliderWidget buildSlider(Text label, double min, double max, double value,
      DoubleConsumer setter, String valueFormat, int width) {
    double range = max - min;
    double normalized = (value - min) / range;
    return new SliderWidget(0, 0, width, 20,
        label.copy().append(Text.literal(String.format(": " + valueFormat, value))),
        normalized) {
      @Override
      protected void updateMessage() {
        double current = min + this.value * range;
        this.setMessage(label.copy().append(Text.literal(String.format(": " + valueFormat, current))));
      }

      @Override
      protected void applyValue() {
        setter.accept(min + this.value * range);
      }
    };
  }

  private ButtonWidget buildOverlayButton(BooleanSupplier currentValue, Consumer<Boolean> setter) {
    return ButtonWidget.builder(
        Text.literal(currentValue.getAsBoolean() ? "\u2713" : "\u2717"),
        b -> {
          boolean newValue = !currentValue.getAsBoolean();
          setter.accept(newValue);
          b.setMessage(Text.literal(newValue ? "\u2713" : "\u2717"));
        }).size(OVERLAY_SIZE, 20).build();
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
