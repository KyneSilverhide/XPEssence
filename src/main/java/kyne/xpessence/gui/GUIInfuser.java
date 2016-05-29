package kyne.xpessence.gui;

import kyne.xpessence.Constants;
import kyne.xpessence.containers.ContainerInfuser;
import kyne.xpessence.tileentities.InfuserContentConfig;
import kyne.xpessence.tileentities.TileEntityInfuser;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GUIInfuser extends GuiContainer {

    private static final ResourceLocation infuserTexture = new ResourceLocation(
            Constants.MODID + ":textures/gui/xp_infuser.png");
    private static final int ARROW_X = 79;
    private static final int ARROW_Y = 34;
    private static final int ARROW_TEXTURE_X = 176;
    private static final int ARROW_TEXTURE_Y = 14;

    private final InventoryPlayer inventoryPlayer;
    private final TileEntityInfuser tileInfuser;
    private final InfuserContentConfig tileEntityContentConfig;

    public GUIInfuser(final InventoryPlayer parInventoryPlayer, final IInventory infuser) {
        super(new ContainerInfuser(parInventoryPlayer, infuser));
        inventoryPlayer = parInventoryPlayer;
        tileInfuser = (TileEntityInfuser) infuser;
        tileEntityContentConfig = tileInfuser.getTileEntityContentConfig();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        final String guiTitle = tileInfuser.getDisplayName().getUnformattedText();
        final int titleWidth = fontRendererObj.getStringWidth(guiTitle);
        fontRendererObj.drawString(guiTitle, xSize / 2 - titleWidth / 2, 6, GUIConstants.TEXT_COLOR_GRAY);

        final String inventoryName = inventoryPlayer.getDisplayName().getUnformattedText();
        fontRendererObj.drawString(inventoryName, 8, ySize - 94, GUIConstants.TEXT_COLOR_GRAY);

        drawInfusingTooltip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(infuserTexture);

        final int marginHorizontal = (width - xSize) / 2;
        final int marginVertical = (height - ySize) / 2;
        drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, xSize, ySize);

        if (tileInfuser.isInfusing()) {
            drawFuelStatus(marginHorizontal, marginVertical);
        }

        drawProgressArrow(marginHorizontal, marginVertical);
    }

    @SuppressWarnings("unchecked")
    private void drawInfusingTooltip(final int mouseX, final int mouseY) {
        final String fuelLeft = String.valueOf(tileEntityContentConfig.getInfusingFuelLeft());

        final int xMargin = (width - xSize) / 2;
        final int yMargin = (height - ySize) / 2;
        final int boxX = xMargin + 56;
        final int boxY = yMargin + 36;
        if (mouseX > boxX && mouseX < boxX + 14) {
            if (mouseY > boxY && mouseY < boxY + 14) {
                final List list = new ArrayList();
                list.add("Experience: " + fuelLeft);
                this.drawHoveringText(list, mouseX - xMargin, mouseY - yMargin, fontRendererObj);
            }
        }
    }

    private void drawFuelStatus(final int marginHorizontal, final int marginVertical) {
        final int infusingLevel = this.getInfusingFuelScaled();
        this.drawTexturedModalRect(marginHorizontal + 56, marginVertical + 36 + 12 - infusingLevel, 176,
                12 - infusingLevel, 14, infusingLevel + 1);
    }

    private void drawProgressArrow(final int marginHorizontal, final int marginVertical) {
        final int progressLevel = getProgressLevel();
        drawTexturedModalRect(marginHorizontal + ARROW_X, marginVertical + ARROW_Y, ARROW_TEXTURE_X, ARROW_TEXTURE_Y,
                progressLevel + 1, GUIConstants.ARROW_TEXTURE_HEIGHT);
    }

    private int getInfusingFuelScaled() {
        int i = this.tileEntityContentConfig.getCurrentItemBurnTime();
        if (i == 0) {
            i = TileEntityInfuser.ITEM_INFUSING_TIME;
        }
        return this.tileInfuser.getField(InfuserContentConfig.INFUSING_FUEL_LEFT) * 13 / i;
    }

    private int getProgressLevel() {
        final int itemInfusingStatus = tileEntityContentConfig.getItemInfusingStatus();
        final int timeToInfuseItem = tileEntityContentConfig.getTimeToInfuseItem();
        return timeToInfuseItem != 0 && itemInfusingStatus != 0 ? (itemInfusingStatus * GUIConstants
                .ARROW_TEXTURE_WIDTH / timeToInfuseItem) : 0;
    }
}