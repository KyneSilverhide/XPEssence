package kyne.xpessence.gui;

import kyne.xpessence.tileentities.TileEntityXPFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIXPFurnace extends GuiFurnace {
    private static final ResourceLocation texture = new ResourceLocation("xpessence", "textures/gui/xp_furnace.png");

    private final IInventory furnaceInv;

    public GUIXPFurnace(InventoryPlayer playerInv, IInventory furnaceInv) {
        super(playerInv, furnaceInv);
        this.furnaceInv = furnaceInv;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if (TileEntityXPFurnace.isBurning(furnaceInv)) {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
    }

    private int getBurnLeftScaled(int pixels) {
        int i = furnaceInv.getField(1);
        if (i == 0) {
            i = 200;
        }
        return furnaceInv.getField(0) * pixels / i;
    }

    private int getCookProgressScaled(int pixels) {
        int i = furnaceInv.getField(2);
        int j = furnaceInv.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
}