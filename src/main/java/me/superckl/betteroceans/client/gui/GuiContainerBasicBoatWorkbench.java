package me.superckl.betteroceans.client.gui;

import java.util.List;

import me.superckl.betteroceans.common.container.ContainerBoatWorkbench;
import me.superckl.betteroceans.common.entity.EntityWoodenBoat;
import me.superckl.betteroceans.common.entity.IEntityBoat;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatWorkbench;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.utility.ItemStackHelper;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import me.superckl.betteroceans.common.utility.RenderUtil;
import me.superckl.betteroceans.common.utility.StringHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class GuiContainerBasicBoatWorkbench extends GuiContainer{

	private final ResourceLocation texture = new ResourceLocation(ModData.MOD_ID+":textures/gui/basicbench.png");
	public GuiContainerBasicBoatWorkbench(final InventoryPlayer inventoryPlayer,
			final TileEntityBoatWorkbench te) {
		super(new ContainerBoatWorkbench(inventoryPlayer, te));
		te.setActiveSelection(new EntityWoodenBoat(inventoryPlayer.player.worldObj));
		this.xSize = 256;
		this.ySize = 166;
	}

	@Override
	public void initGui(){
		super.initGui();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int param1, final int param2){
		this.fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization(StringHelper.formatGUIUnlocalizedName("basicboatbench")), 15+(162-this.fontRendererObj.getStringWidth(LanguageRegistry.instance().getStringLocalization(StringHelper.formatGUIUnlocalizedName("basicboatbench"))))/2, 8, 0x404040);
		final TileEntityBoatWorkbench te = ((ContainerBoatWorkbench)this.inventorySlots).getTileEntity();
		if(te.getActiveSelection() == null)
			return;
		final List<ItemStack> required = ItemStackHelper.deepClone(te.getActiveSelection().getCraftingIngredients());
		RecipeHelper.areItemsPresent(required, te.getInventory(), false);
		if(required.isEmpty())
			return;
		int x = 176;
		final int width = 75;
		final int y = 83;
		this.fontRendererObj.drawString("Requires:", x+(width-this.fontRendererObj.getStringWidth("Requires:"))/2, 70, 0xdd0000);
		final int leftover = width - 16*required.size();
		final int spacing = leftover/(required.size()+1);
		x+=spacing;
		for(final ItemStack stack:required){
			RenderHelper.enableGUIStandardItemLighting();
			GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, x, y);
			GuiScreen.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, x, y);
			x+=16+spacing;
		}
	}

	@Override
	public void updateScreen(){
		super.updateScreen();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float var1, final int var2,
			final int var3) {
		this.mc.renderEngine.bindTexture(this.texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		final int xStart = (this.width - this.xSize) / 2;
		final int yStart = (this.height - this.ySize) / 2;
		//this.drawTexturedModalRect(xStart, yStart, 0, 0, this.xSize, this.ySize);
		RenderUtil.drawTexturedRect(this.texture, xStart, yStart, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize, 1F);

		final IEntityBoat entity = ((ContainerBoatWorkbench)this.inventorySlots).getTileEntity().getActiveSelection();
		if(entity == null)
			return;
		RenderUtil.renderEntityToGUI(entity.asEntity(), this.guiLeft + 123, this.guiTop + 54, 14F);
		//TODO

	}

}
