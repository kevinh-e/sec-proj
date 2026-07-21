package dev.kevinh.secproj.gui;

import java.util.ArrayList;
import java.util.List;

import dev.kevinh.secproj.SecurityProjectClient;
import dev.kevinh.secproj.tools.ClientOptions;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Positioner;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

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
  private static final Text REACH_TEXT = Text.translatable("gui.secproj.menu_screen.reach");
  private static final Text CRITICALS_TEXT = Text.translatable("gui.secproj.menu_screen.criticals");
  private static final Text MACE_TEXT = Text.translatable("gui.secproj.menu_screen.mace");
  private static Text maceHeightText;
  private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this, 31, 33);
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
        .translatable("gui.secproj.menu_screen.freecam_speed")
        .append(Text.literal(": " + freecamSpeed));
    double normalizedFreecamSpeed = (freecamSpeed - ClientOptions.FREECAM_SPEED_MIN)
        / (ClientOptions.FREECAM_SPEED_MAX - ClientOptions.FREECAM_SPEED_MIN);

    adder.add(new SliderWidget(0, 0, 200, 20, freecamSpeedText, normalizedFreecamSpeed) {
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
    });
    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isFreecamSpeedShownInOverlay()).build(OVERLAY_TEXT,
            (button, val) -> clientOptions.setFreecamSpeedShownInOverlay(val)));

    adder.add(new TextWidget(CPS_TEXT, this.textRenderer));
    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isCpsShownInOverlay()).build(OVERLAY_TEXT,
            (button, val) -> clientOptions.setCpsShownInOverlay(val)));

    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isReachEnabled()).build(REACH_TEXT,
            (button, val) -> clientOptions.setReachEnabled(val)));
    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isReachShownInOverlay()).build(OVERLAY_TEXT,
            (button, val) -> clientOptions.setReachShownInOverlay(val)));

    adder.add(buildReachSlider("gui.secproj.menu_screen.block_reach",
        clientOptions.getBlockReachValue(), clientOptions::setBlockReachValue), 2);
    adder.add(buildReachSlider("gui.secproj.menu_screen.entity_reach",
        clientOptions.getEntityReachValue(), clientOptions::setEntityReachValue), 2);

    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isCriticalsEnabled()).build(CRITICALS_TEXT,
            (button, val) -> clientOptions.setCriticalsEnabled(val)));
    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isCriticalsShownInOverlay()).build(OVERLAY_TEXT,
            (button, val) -> clientOptions.setCriticalsShownInOverlay(val)));

    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isMaceEnabled()).build(MACE_TEXT,
            (button, val) -> clientOptions.setMaceEnabled(val)));
    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isMaceShownInOverlay()).build(OVERLAY_TEXT,
            (button, val) -> clientOptions.setMaceShownInOverlay(val)));

    double maceHeight = clientOptions.getMaceHeight();
    maceHeightText = Text
        .translatable("gui.secproj.menu_screen.mace_height")
        .append(Text.literal(String.format(": %.2f", maceHeight)));
    double normalizedMaceHeight = (maceHeight - ClientOptions.MACE_HEIGHT_MIN)
        / (ClientOptions.MACE_HEIGHT_MAX - ClientOptions.MACE_HEIGHT_MIN);

    adder.add(new SliderWidget(0, 0, 200, 20, maceHeightText, normalizedMaceHeight) {
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

    int maxBodyHeight = Math.max(100,
        this.height - this.layout.getHeaderHeight() - this.layout.getFooterHeight() - 30);
    mainGrid.setSpacing(2);
    mainGrid.refreshPositions();
    int bodyHeight = Math.min(mainGrid.getHeight(), maxBodyHeight);
    ScrollableGridWidget scrollableBody = new ScrollableGridWidget(0, 0, this.width,
        Math.max(20, bodyHeight), mainGrid);
    this.layout.addBody(scrollableBody);
    this.layout.addFooter(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close()).width(200).build());

    this.layout.forEachChild(this::addDrawableChild);
    this.layout.refreshPositions();
    scrollableBody.refreshLayout();
  }

  private SliderWidget buildReachSlider(String labelKey, double currentValue,
      java.util.function.DoubleConsumer onValueChanged) {
    double normalized = (currentValue - ClientOptions.REACH_MIN)
        / (ClientOptions.REACH_MAX - ClientOptions.REACH_MIN);
    return new SliderWidget(0, 0, 200, 20,
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

  private static class ScrollableGridWidget extends ContainerWidget {
    private static final int SCROLLER_WIDTH = 6;
    private static final int SCROLLER_MARGIN = 2;
    private static final int SCROLL_AMOUNT = 20;

    private final GridWidget grid;
    private final List<Element> childElements = new ArrayList<>();
    private int contentHeight;
    private int scrollY;

    ScrollableGridWidget(int x, int y, int width, int height, GridWidget grid) {
      super(x, y, width, height, Text.literal(""));
      this.grid = grid;
    }

    @Override
    public List<? extends Element> children() {
      return this.childElements;
    }

    void refreshLayout() {
      int contentAreaWidth = Math.max(1,
          this.getWidth() - SCROLLER_WIDTH - SCROLLER_MARGIN * 2);
      this.grid.setColumnSpacing(2);
      this.grid.setRowSpacing(2);
      this.grid.setX(this.getX());
      this.grid.setY(this.getY());
      this.grid.refreshPositions();

      int gridWidth = this.grid.getWidth();
      if (gridWidth < contentAreaWidth) {
        this.grid.setX(this.getX() + (contentAreaWidth - gridWidth) / 2);
        this.grid.refreshPositions();
      }

      this.contentHeight = this.grid.getHeight();
      this.childElements.clear();
      this.grid.forEachChild(this.childElements::add);
      this.clampScroll();
      this.applyScroll();
    }

    private void applyScroll() {
      this.grid.setY(this.getY() - this.scrollY);
      this.grid.refreshPositions();
    }

    private int getMaxScrollY() {
      return Math.max(0, this.contentHeight - this.getHeight());
    }

    private boolean overflows() {
      return this.contentHeight > this.getHeight();
    }

    private void clampScroll() {
      this.scrollY = MathHelper.clamp(this.scrollY, 0, this.getMaxScrollY());
    }

    private void changeScroll(int delta) {
      if (!this.overflows()) {
        this.scrollY = 0;
        return;
      }
      this.scrollY = MathHelper.clamp(this.scrollY + delta, 0, this.getMaxScrollY());
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount,
        double verticalAmount) {
      if (this.overflows()) {
        this.changeScroll((int) (-verticalAmount * SCROLL_AMOUNT));
        this.applyScroll();
        return true;
      }
      return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
      int contentRight = this.getX() + this.getWidth() - SCROLLER_WIDTH - SCROLLER_MARGIN;
      if (contentRight > this.getX()) {
        context.enableScissor(this.getX(), this.getY(), contentRight,
            this.getY() + this.getHeight());
      }

      for (Element child : this.childElements) {
        if (child instanceof ClickableWidget widget) {
          widget.render(context, mouseX, mouseY, delta);
        }
      }

      if (contentRight > this.getX()) {
        context.disableScissor();
      }

      if (this.overflows()) {
        this.drawScrollbar(context);
      }
    }

    private void drawScrollbar(DrawContext context) {
      int scrollerX = this.getX() + this.getWidth() - SCROLLER_WIDTH;
      int trackTop = this.getY() + SCROLLER_MARGIN;
      int trackBottom = this.getY() + this.getHeight() - SCROLLER_MARGIN;
      int trackHeight = trackBottom - trackTop;
      int thumbHeight = Math.max(SCROLLER_WIDTH,
          (int) ((double) this.getHeight() * trackHeight / this.contentHeight));
      int thumbOffset = this.getMaxScrollY() == 0 ? 0
          : (int) ((double) (trackHeight - thumbHeight) * this.scrollY / this.getMaxScrollY());
      int thumbTop = trackTop + thumbOffset;

      context.fill(scrollerX, trackTop, scrollerX + SCROLLER_WIDTH, trackBottom, 0x30FFFFFF);
      context.fill(scrollerX, thumbTop, scrollerX + SCROLLER_WIDTH,
          thumbTop + thumbHeight, 0x80FFFFFF);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
      this.appendDefaultNarrations(builder);
    }
  }
}
