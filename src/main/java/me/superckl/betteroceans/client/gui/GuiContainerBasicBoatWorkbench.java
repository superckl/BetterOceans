package me.superckl.betteroceans.client.gui;

import java.util.List;

import me.superckl.betteroceans.client.gui.components.ArrowButton;
import me.superckl.betteroceans.common.container.ContainerBoatWorkbench;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatWorkbench;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.BoatPart.Material;
import me.superckl.betteroceans.common.parts.BoatPart.Type;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.NetworkData;
import me.superckl.betteroceans.common.utility.CollectionHelper;
import me.superckl.betteroceans.common.utility.ItemStackHelper;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import me.superckl.betteroceans.common.utility.RenderHelper;
import me.superckl.betteroceans.common.utility.StringHelper;
import me.superckl.betteroceans.network.MessageSelectBoatPart;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class GuiContainerBasicBoatWorkbench extends GuiContainer{

	private final ResourceLocation texture = new ResourceLocation(ModData.MOD_ID+":textures/gui/basicbench.png");

	private int typeIndex = -1;
	private int materialIndex = -1;
	private BoatPart activePart;
	private ItemStack partStack;

	private int partX;
	private int materialX;

	public GuiContainerBasicBoatWorkbench(final InventoryPlayer inventoryPlayer,
			final TileEntityBoatWorkbench te) {
		super(new ContainerBoatWorkbench(inventoryPlayer, te));
		this.xSize = 256;
		this.ySize = 166;
		if(te.getActiveSelection() != null){
			this.activePart = te.getActiveSelection().getBoatParts().get(0);
			this.partStack = this.activePart.getCraftingResult();
			this.typeIndex = CollectionHelper.find(this.activePart.getType(), Type.values());
			this.materialIndex = CollectionHelper.find(this.activePart.getMaterial(), Material.values());
		}
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
		this.buttonList.add(new ArrowButton(0, i+x, j+106, 16, 20, "", true));
		x+=spacing+14;
		this.partX = x;
		x+=spacing+16;
		this.buttonList.add(new ArrowButton(1, i+x, j+106, 16, 20, "", false));
		x=176+spacing;
		this.buttonList.add(new ArrowButton(2, i+x, j+139, 16, 20, "", true));
		x+=spacing+14;
		this.materialX = x;
		x+=spacing+16;
		this.buttonList.add(new ArrowButton(3, i+x, j+139, 16, 20, "", false));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int param1, final int param2){
		final String name = LanguageRegistry.instance().getStringLocalization(StringHelper.formatGUIUnlocalizedName("basicboatbench"));
		final int i = this.xSize/ 2;
		this.fontRendererObj.drawString(name, i-this.fontRendererObj.getStringWidth(name)/2, 8, 0x404040);
		int x = 176;
		final int width = 77;
		this.fontRendererObj.drawString("Part", x+(width-this.fontRendererObj.getStringWidth("Part"))/2, 106-this.fontRendererObj.FONT_HEIGHT-1, 0x404040);
		this.fontRendererObj.drawString("Material", x+(width-this.fontRendererObj.getStringWidth("Material"))/2, 139-this.fontRendererObj.FONT_HEIGHT-1, 0x404040);
		net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
		if(this.activePart != null){
			GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), this.partStack, this.partX, 108);
			GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), Material.values()[this.materialIndex].getItemRepresentation(), this.materialX, 141);
		}
		final TileEntityBoatWorkbench te = ((ContainerBoatWorkbench)this.inventorySlots).getTileEntity();
		if(te.getActiveSelection() == null)
			return;
		final List<ItemStack> required = ItemStackHelper.deepClone(te.getActiveSelection().getBoatParts().get(0).getCraftingIngredients());
		RecipeHelper.areItemsPresent(required, te.getInventory(), false);
		if(required.isEmpty())
			return;

		final int y = 76;
		this.fontRendererObj.drawString("Requires", x+(width-this.fontRendererObj.getStringWidth("Requires:"))/2, 66, 0xdd0000);
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
		RenderHelper.drawTexturedRect(this.texture, xStart, yStart, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize, 1F);

		final EntityBOBoat entity = ((ContainerBoatWorkbench)this.inventorySlots).getTileEntity().getActiveSelection();
		if(entity == null)
			return;
		RenderHelper.renderEntityToGUI(entity, this.guiLeft + 123, this.guiTop + 54, 14F);
		//TODO
	}

	@Override
	protected void actionPerformed(final GuiButton button) {
		switch(button.id){
		case 0:
			this.typeIndex--;
			if(this.typeIndex < 0)
				this.typeIndex = Type.values().length-1;
			break;
		case 1:
			this.typeIndex++;
			if(this.typeIndex >= Type.values().length)
				this.typeIndex = 0;
			break;
		case 2:
			this.materialIndex--;
			if(this.materialIndex < 0)
				this.materialIndex = Material.values().length-1;
			break;
		case 3:
			this.materialIndex++;
			if(this.materialIndex >= Material.values().length)
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

	public void updateActivePart(){
		final BoatPart part = BoatPart.getPartByTypeAndMaterial(Type.values()[this.typeIndex], Material.values()[this.materialIndex]);
		if(part == null)
			return;
		this.activePart = part;
		this.partStack = part.getCraftingResult();
		final TileEntityBoatWorkbench te = ((ContainerBoatWorkbench)this.inventorySlots).getTileEntity();
		te.setActiveSelection(part.getOnePartBoat(te.getWorldObj()));
		NetworkData.PART_SELECT_CHANNEL.sendToServer(new MessageSelectBoatPart(te, part));
	}

}
