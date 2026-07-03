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
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class SecprojMenuScreen extends Screen {
  private static final Text TITLE_TEXT = Text.translatable("gui.secproj.menu_screen.title");
  private static final Text NOFALL_TEXT = Text.translatable("gui.secproj.menu_screen.nofall");
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

    adder.add(
        CyclingButtonWidget.onOffBuilder(clientOptions.isNoFallEnabled()).build(NOFALL_TEXT,
            (button, val) -> clientOptions.setNoFall(val)));

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
