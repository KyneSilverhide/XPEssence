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

@SideOnly(Side.CLIENT)
public class GUICrucible extends GuiContainer {

    private static final ResourceLocation crucibleTexture = new ResourceLocation(Constants.MODID + ":textures/gui/xp_crucible.png");
    private static final int TEXT_COLOR_GRAY = 4210752;

    private static final int TEXTURES_X = 176;
    private static final int ARROW_TEXTURE_Y = 3;
    private static final int LIQUID_TEXTURE_Y = 20;
    private static final int FLUID_TEXTURE_HEIGHT = 54;

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
        fontRendererObj.drawString(inventoryName, 8, ySize - 94, 4210752);

        final String tankContent = String.valueOf(crucibleContentConfig.getTankContentMb());
        fontRendererObj.drawString("Experience (mb): " + tankContent, xSize / 2 - fontRendererObj.getStringWidth(tankContent) / 2,
                ySize - 94, TEXT_COLOR_GRAY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(crucibleTexture);

        final int marginHorizontal = (width - xSize) / 2;
        final int marginVertical = (height - ySize) / 2;
        drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, xSize, ySize);

        drawItemToTankProgressArrow(marginHorizontal, marginVertical);
        drawTankToBucketProgressArrow(marginHorizontal, marginVertical);

        drawTankContent(marginHorizontal, marginVertical);
    }

    private void drawTankContent(final int marginHorizontal, final int marginVertical) {
        final int tankContentMb = crucibleContentConfig.getTankContentMb();
        final int tankContentPixels = getTankContentInPixels(tankContentMb);
        drawTexturedModalRect(marginHorizontal + 78, marginVertical + 15 - tankContentPixels, TEXTURES_X, LIQUID_TEXTURE_Y, tankContentPixels, 16);
    }

    private int getTankContentInPixels(final int tankContentMb) {
        final float fillRatio = tankContentMb / CrucibleContentConfig.TANK_TOTAL_CAPACITY_MB;
        return (int) (FLUID_TEXTURE_HEIGHT * fillRatio);
    }

    private void drawTankToBucketProgressArrow(final int marginHorizontal, final int marginVertical) {
        final int progressLevel = getProgressLevel(crucibleContentConfig.getTankToBucketStatus());
        drawTexturedModalRect(marginHorizontal + 104, marginVertical + 34, TEXTURES_X, ARROW_TEXTURE_Y, progressLevel + 1, 16);
    }

    private void drawItemToTankProgressArrow(final int marginHorizontal, final int marginVertical) {
        final int progressLevel = getProgressLevel(crucibleContentConfig.getItemToTankStatus());
        drawTexturedModalRect(marginHorizontal + 47, marginVertical + 34, TEXTURES_X, ARROW_TEXTURE_Y, progressLevel + 1, 16);
    }

    private int getProgressLevel(final int itemTransferStatus) {
        final int itemTransferTime = CrucibleContentConfig.ITEM_TRANSFER_TIME;
        return itemTransferStatus != 0 ?(itemTransferStatus * GUI.ARROW_TEXTURE_WIDTH / itemTransferTime) : 0;
    }
}