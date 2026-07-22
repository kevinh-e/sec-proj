package dev.kevinh.secproj.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class ScrollableGridWidget extends ContainerWidget {
  private static final int SCROLLER_WIDTH = 6;
  private static final int SCROLLER_MARGIN = 2;
  private static final int SCROLL_AMOUNT = 20;

  private final GridWidget grid;
  private final List<Element> childElements = new ArrayList<>();
  private int contentHeight;
  private int scrollY;

  public ScrollableGridWidget(int x, int y, int width, int height, GridWidget grid) {
    super(x, y, width, height, Text.literal(""));
    this.grid = grid;
  }

  public static int scrollbarWidth() {
    return SCROLLER_WIDTH + SCROLLER_MARGIN * 2;
  }

  @Override
  public List<? extends Element> children() {
    return this.childElements;
  }

  public void refreshLayout() {
    int contentAreaWidth = Math.max(1, this.getWidth() - scrollbarWidth());
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
