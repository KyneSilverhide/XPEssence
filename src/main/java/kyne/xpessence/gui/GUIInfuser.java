package kyne.xpessence.gui;

import kyne.xpessence.containers.ContainerInfuser;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIInfuser extends GuiContainer {

    private static final ResourceLocation infuserTexture = new ResourceLocation("xpessence:textures/gui/xp_infuser.png");
    private final InventoryPlayer inventoryPlayer;
    private final IInventory tileInfuser;

    public GUIInfuser(final InventoryPlayer parInventoryPlayer,
                      final IInventory infuser) {
        super(new ContainerInfuser(parInventoryPlayer, infuser));
        inventoryPlayer = parInventoryPlayer;
        tileInfuser = infuser;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        final String s = tileInfuser.getDisplayName().getUnformattedText();
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        fontRendererObj.drawString(inventoryPlayer.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(infuserTexture);
        final int marginHorizontal = (width - xSize) / 2;
        final int marginVertical = (height - ySize) / 2;
        drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, xSize, ySize);

        if (TileEntityFurnace.isBurning(this.tileInfuser)) {
            final int infusingLevel = this.getInfusingScaled(13);
            this.drawTexturedModalRect(marginHorizontal + 56, marginVertical + 36 + 12 - infusingLevel, 176, 12 - infusingLevel, 14, infusingLevel + 1);
        }
        final int progressLevel = getProgressLevel(24);
        drawTexturedModalRect(marginHorizontal + 79, marginVertical + 34,  176, 14, progressLevel + 1, 16);
    }

    private int getProgressLevel(final int progressIndicatorPixelWidth) {
        final int ticksGrindingItemSoFar = tileInfuser.getField(2);
        final int ticksPerItem = tileInfuser.getField(3);
        return ticksPerItem != 0 && ticksGrindingItemSoFar != 0 ?
                ticksGrindingItemSoFar * progressIndicatorPixelWidth / ticksPerItem : 0;
    }

    private int getInfusingScaled(final int pixels) {
        int i = this.tileInfuser.getField(1);
        if (i == 0) {
            i = 200;
        }
        return this.tileInfuser.getField(0) * pixels / i;
    }
}