package kyne.xpessence.gui;

import kyne.xpessence.Constants;
import kyne.xpessence.containers.ContainerCrucible;
import kyne.xpessence.tileentities.CrucibleContentConfig;
import kyne.xpessence.tileentities.base.BasicTileEntity;
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
public class GUICrucible extends GuiContainer {

    private static final ResourceLocation crucibleTexture = new ResourceLocation(
            Constants.MODID + ":textures/gui/xp_crucible.png");
    private static final int TEXT_COLOR_GRAY = 4210752;

    private static final int TEXTURES_X = 176;
    private static final int ARROW_TEXTURE_Y = 3;
    private static final int TANK_WIDTH = 20;
    private static final int LIQUID_TEXTURE_Y = 20;
    private static final int TANK_HEIGHT = 54;
    private static final int FLUID_TEXTURE_HEIGHT = TANK_HEIGHT;
    private static final int LIQUID_TEXTURE_HEIGHT = TANK_HEIGHT;
    private static final int TANK_X = 78;
    private static final int TANK_Y = 15;
    private static final int R_ARROW_X = 104;
    private static final int ARROWS_Y = 34;
    private static final int L_ARROW_X = 47;

    private final InventoryPlayer inventoryPlayer;
    private final BasicTileEntity titleCrucible;
    private final CrucibleContentConfig crucibleContentConfig;


    public GUICrucible(final InventoryPlayer parInventoryPlayer, final IInventory crucible) {
        super(new ContainerCrucible(parInventoryPlayer, crucible));
        inventoryPlayer = parInventoryPlayer;
        titleCrucible = (BasicTileEntity) crucible;
        crucibleContentConfig = (CrucibleContentConfig) titleCrucible.getTileEntityContentConfig();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        final String guiTitle = titleCrucible.getDisplayName().getUnformattedText();
        final int titleWidth = fontRendererObj.getStringWidth(guiTitle);
        fontRendererObj.drawString(guiTitle, xSize / 2 - titleWidth / 2, 6, TEXT_COLOR_GRAY);

        final String inventoryName = inventoryPlayer.getDisplayName().getUnformattedText();
        fontRendererObj.drawString(inventoryName, 8, ySize - 94, GUIConstants.TEXT_COLOR_GRAY);

        drawTankTooltip(mouseX, mouseY);
    }

    @SuppressWarnings("unchecked")
    private void drawTankTooltip(final int mouseX, final int mouseY) {
        final String tankContent = String.valueOf(crucibleContentConfig.getTankContentMb());

        final int xMargin = (width - xSize) / 2;
        final int yMargin = (height - ySize) / 2;
        final int boxX = xMargin + TANK_X;
        final int boxY = yMargin + TANK_Y;
        if (mouseX > boxX && mouseX < boxX + TANK_WIDTH) {
            if (mouseY > boxY && mouseY < boxY + TANK_HEIGHT) {
                final List list = new ArrayList();
                list.add("Experience (mb): " + tankContent);
                this.drawHoveringText(list, mouseX - xMargin, mouseY - yMargin, fontRendererObj);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(crucibleTexture);

        final int xMargin = (width - xSize) / 2;
        final int yMargin = (height - ySize) / 2;
        drawTexturedModalRect(xMargin, yMargin, 0, 0, xSize, ySize);

        drawItemToTankProgressArrow(xMargin, yMargin);
        drawTankToBucketProgressArrow(xMargin, yMargin);

        drawTankContent(xMargin, yMargin);
    }

    private void drawItemToTankProgressArrow(final int marginHorizontal, final int marginVertical) {
        final int progressLevel = getProgressLevel(crucibleContentConfig.getItemToTankStatus());
        drawTexturedModalRect(marginHorizontal + L_ARROW_X, marginVertical + ARROWS_Y, TEXTURES_X, ARROW_TEXTURE_Y,
                progressLevel + 1, 16);
    }

    private void drawTankToBucketProgressArrow(final int marginHorizontal, final int marginVertical) {
        final int progressLevel = getProgressLevel(crucibleContentConfig.getTankToBucketStatus());
        drawTexturedModalRect(marginHorizontal + R_ARROW_X, marginVertical + ARROWS_Y, TEXTURES_X, ARROW_TEXTURE_Y,
                progressLevel + 1, 16);
    }

    private void drawTankContent(final int marginHorizontal, final int marginVertical) {
        final int tankContentMb = crucibleContentConfig.getTankContentMb();
        final int tankContentPixels = getTankContentInPixels(tankContentMb);
        drawTexturedModalRect(marginHorizontal + TANK_X,
                marginVertical + TANK_Y + (LIQUID_TEXTURE_HEIGHT - tankContentPixels), TEXTURES_X, LIQUID_TEXTURE_Y,
                TANK_WIDTH, tankContentPixels);
    }

    private int getProgressLevel(final int itemTransferStatus) {
        final int itemTransferTime = CrucibleContentConfig.ITEM_TRANSFER_TIME;
        return itemTransferStatus != 0 ? (itemTransferStatus * GUIConstants.ARROW_TEXTURE_WIDTH / itemTransferTime) : 0;
    }

    private int getTankContentInPixels(final int tankContentMb) {
        final float fillRatio = (float) tankContentMb / CrucibleContentConfig.TANK_TOTAL_CAPACITY_MB;
        return (int) (FLUID_TEXTURE_HEIGHT * fillRatio);
    }
}