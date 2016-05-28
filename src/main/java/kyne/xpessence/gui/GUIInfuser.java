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

@SideOnly(Side.CLIENT)
public class GUIInfuser extends GuiContainer {

    private static final ResourceLocation infuserTexture = new ResourceLocation(Constants.MODID + ":textures/gui/xp_infuser.png");
    private static final int TEXT_COLOR_GRAY = 4210752;

    private final InventoryPlayer inventoryPlayer;
    private final TileEntityInfuser tileInfuser;
    private final InfuserContentConfig tileEntityContentConfig;

    public GUIInfuser(final InventoryPlayer parInventoryPlayer, final IInventory infuser) {
        super(new ContainerInfuser(parInventoryPlayer, infuser));
        inventoryPlayer = parInventoryPlayer;
        tileInfuser = (TileEntityInfuser) infuser;
        tileEntityContentConfig = tileInfuser.getTileEntityContentConfig();
        tileEntityContentConfig.debug();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        final String guiTitle = tileInfuser.getDisplayName().getUnformattedText();
        final int titleWidth = fontRendererObj.getStringWidth(guiTitle);
        fontRendererObj.drawString(guiTitle, xSize / 2 - titleWidth / 2, 6, TEXT_COLOR_GRAY);

        final String inventoryName = inventoryPlayer.getDisplayName().getUnformattedText();
        fontRendererObj.drawString(inventoryName, 8, ySize - 94, 4210752);

        final String fuelLeft = String.valueOf(tileEntityContentConfig.getInfusingFuelLeft());
        fontRendererObj.drawString("Experience: " + fuelLeft, xSize / 2 - fontRendererObj.getStringWidth(fuelLeft) / 2,
                ySize - 94, TEXT_COLOR_GRAY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(infuserTexture);

        final int marginHorizontal = (width - xSize) / 2;
        final int marginVertical = (height - ySize) / 2;
        drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, xSize, ySize);

        if(tileInfuser.isInfusing()) {
            drawFuelStatus(marginHorizontal, marginVertical);
        }

        drawProgressArrow(marginHorizontal, marginVertical);
    }

    private void drawFuelStatus(final int marginHorizontal, final int marginVertical) {
        final int infusingLevel = this.getInfusingFuelScaled();
        this.drawTexturedModalRect(marginHorizontal + 56, marginVertical + 36 + 12 - infusingLevel, 176,
                12 - infusingLevel, 14, infusingLevel + 1);
    }

    private void drawProgressArrow(final int marginHorizontal, final int marginVertical) {
        final int progressLevel = getProgressLevel(24);
        drawTexturedModalRect(marginHorizontal + 79, marginVertical + 34, 176, 14, progressLevel + 1, 16);
    }

    private int getInfusingFuelScaled() {
        int i = this.tileEntityContentConfig.getCurrentItemBurnTime();
        if (i == 0) {
            i = TileEntityInfuser.ITEM_INFUSING_TIME;
        }
        return this.tileInfuser.getField(InfuserContentConfig.INFUSING_FUEL_LEFT) * 13 / i;
    }

    private int getProgressLevel(final int progressWithInPixels) {
        final int itemInfusingStatus = tileEntityContentConfig.getItemInfusingStatus();
        final int timeToInfuseItem = tileEntityContentConfig.getTimeToInfuseItem();
        return timeToInfuseItem != 0 && itemInfusingStatus != 0 ?
                (itemInfusingStatus * progressWithInPixels / timeToInfuseItem) : 0;
    }
}