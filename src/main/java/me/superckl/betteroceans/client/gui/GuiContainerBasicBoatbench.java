package me.superckl.betteroceans.client.gui;

import java.util.List;

import me.superckl.betteroceans.client.gui.components.ArrowButton;
import me.superckl.betteroceans.common.container.ContainerBasicBoatbench;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.parts.BoatPart.Material;
import me.superckl.betteroceans.common.parts.BoatPart.Type;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.utility.ItemStackHelper;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import me.superckl.betteroceans.common.utility.RenderHelper;
import me.superckl.betteroceans.common.utility.StringHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class GuiContainerBasicBoatbench extends GuiContainerBoatBench{

	public GuiContainerBasicBoatbench(final InventoryPlayer inventoryPlayer,
			final TileEntityBoatbench te, final Type[] validTypes, final Material[] validMaterials) {
		super(new ContainerBasicBoatbench(inventoryPlayer, te), te, validTypes, validMaterials);
		this.setTexture(new ResourceLocation(ModData.MOD_ID+":textures/gui/basicbench.png"), 256, 161);
	}

	@Override
	public void initGui(){
		super.initGui();
		final int i = (this.width - this.xSize) / 2;
		final int j = (this.height - this.ySize) / 2;
		final int width = 77;
		final int leftover = width - 16-14*2;
		final int spacing = leftover/4;
		int x = 176;
		x += spacing;
		this.buttonList.add(new ArrowButton(0, i+x, j+97, 16, 20, "", true));
		x+=spacing+14;
		this.partX = x;
		x+=spacing+16;
		this.buttonList.add(new ArrowButton(1, i+x, j+97, 16, 20, "", false));
		x=176+spacing;
		this.buttonList.add(new ArrowButton(2, i+x, j+130, 16, 20, "", true));
		x+=spacing+14;
		this.materialX = x;
		x+=spacing+16;
		this.buttonList.add(new ArrowButton(3, i+x, j+130, 16, 20, "", false));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int param1, final int param2){
		final String name = LanguageRegistry.instance().getStringLocalization(StringHelper.formatGUIUnlocalizedName("basicboatbench"));
		//final int i = (this.width - this.xSize) / 2+69;
		this.fontRendererObj.drawString(name, 15/*-this.fontRendererObj.getStringWidth(name)/2*/, 7, 0x404040);
		int x = 176;
		final int width = 77;
		this.fontRendererObj.drawString("Part", x+(width-this.fontRendererObj.getStringWidth("Part"))/2, 98-this.fontRendererObj.FONT_HEIGHT-1, 0x404040);
		this.fontRendererObj.drawString("Material", x+(width-this.fontRendererObj.getStringWidth("Material"))/2, 131-this.fontRendererObj.FONT_HEIGHT-1, 0x404040);
		net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
		if(this.activePart != null){
			GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), this.partStack, this.partX, 98);
			GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), Material.values()[this.materialIndex].getItemRepresentation(), this.materialX, 131);
		}
		final TileEntityBoatbench te = ((ContainerBasicBoatbench)this.inventorySlots).getTileEntity();
		if(te.getActiveSelection() == null)
			return;
		final List<ItemStack> required = ItemStackHelper.deepClone(te.getActiveSelection().getBoatParts().get(0).getCraftingIngredients());
		RecipeHelper.areItemsPresent(required, te.getInventory(), false);
		if(required.isEmpty())
			return;

		final int y = 67;
		this.fontRendererObj.drawString("Requires", x+(width-this.fontRendererObj.getStringWidth("Requires:"))/2, 57, 0xdd0000);
		final int leftover = width - 16*required.size();
		final int spacing = leftover/(required.size()+1);
		x+=spacing;
		for(final ItemStack stack:required){
			net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
			GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, x, y);
			GuiScreen.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, x, y);
			x+=16+spacing;
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float var1, final int var2,
			final int var3) {
		super.drawGuiContainerBackgroundLayer(var1, var2, var3);
		final EntityBOBoat entity = ((ContainerBasicBoatbench)this.inventorySlots).getTileEntity().getActiveSelection();
		if(entity == null)
			return;
		RenderHelper.renderEntityToGUI(entity, this.guiLeft + 123, this.guiTop + 48, 14F);
		//TODO
	}

	@Override
	protected void actionPerformed(final GuiButton button) {
		switch(button.id){
		case 0:
			this.typeIndex--;
			if(this.typeIndex < 0)
				this.typeIndex = this.validTypes.length-1;
			break;
		case 1:
			this.typeIndex++;
			if(this.typeIndex >= this.validTypes.length)
				this.typeIndex = 0;
			break;
		case 2:
			this.materialIndex--;
			if(this.materialIndex < 0)
				this.materialIndex = this.validMaterials.length-1;
			break;
		case 3:
			this.materialIndex++;
			if(this.materialIndex >= this.validMaterials.length)
				this.materialIndex = 0;
			break;
		default:
			break;
		}
		if(this.typeIndex < 0)
			this.typeIndex = 0;
		if(this.materialIndex < 0)
			this.materialIndex = 0;
		this.updateActivePart();
	}

}
