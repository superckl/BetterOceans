package me.superckl.betteroceans.client.gui;

import me.superckl.betteroceans.client.gui.components.ArrowButton;
import me.superckl.betteroceans.client.gui.components.HammerButton;
import me.superckl.betteroceans.common.container.ContainerBoatbench;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.BoatPart.Material;
import me.superckl.betteroceans.common.parts.BoatPart.Type;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.NetworkData;
import me.superckl.betteroceans.common.utility.CollectionHelper;
import me.superckl.betteroceans.common.utility.RenderHelper;
import me.superckl.betteroceans.common.utility.StringHelper;
import me.superckl.betteroceans.network.MessagePressCraft;
import me.superckl.betteroceans.network.MessageSelectBoatPart;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTankInfo;

import org.lwjgl.opengl.GL11;

public class GuiContainerBoatbench extends GuiContainer{

	protected ResourceLocation texture = new ResourceLocation(ModData.MOD_ID+":textures/gui/boatbench.png");

	protected String unlocalizedName;
	protected int typeIndex = -1;
	protected Type[] validTypes;
	protected int materialIndex = -1;
	protected Material[] validMaterials;
	protected BoatPart activePart;
	protected ItemStack partStack;

	protected int partX;
	protected int materialX;

	private boolean buttonsEnabled;

	public GuiContainerBoatbench(final String unlocalizedName, final Container container, final TileEntityBoatbench te, final Type[] validTypes, final Material[] validMaterials) {
		super(container);
		this.unlocalizedName = unlocalizedName;
		this.validTypes = validTypes;
		this.validMaterials = validMaterials;
		this.xSize = 248;
		this.ySize = 170;
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
		final int width = 52;
		final int leftover = width - 16-12*2;
		final int spacing = leftover/4;
		int x = 4; // 176
		x += spacing;
		//12, 30 from
		this.buttonList.add(new ArrowButton(2, i+x, j+20, 12, 12, "", true));
		x+=spacing+12;
		this.materialX = x;
		x+=spacing+16;
		this.buttonList.add(new ArrowButton(3, i+x, j+20, 12, 12, "", false));
		x=4+spacing;
		this.buttonList.add(new ArrowButton(0, i+x, j+38, 12, 12, "", true));
		x+=spacing+12;
		this.partX = x;
		x+=spacing+16;
		this.buttonList.add(new ArrowButton(1, i+x, j+38, 12, 12, "", false));

		this.buttonList.add(new HammerButton(4, i+this.partX-2, j+54, 20, 20, ""));
		this.buttonsEnabled = true;
	}



	@Override
	public void updateScreen() {
		super.updateScreen();
		final TileEntityBoatbench te = ((ContainerBoatbench)this.inventorySlots).getTileEntity();
		if(te.getPartBurnTime() > 0 && this.buttonsEnabled){
			for(final Object button:this.buttonList)
				((GuiButton)button).enabled = false;
			this.buttonsEnabled = false;
		}else if(te.getPartBurnTime() <= 0 && !this.buttonsEnabled){
			for(final Object button:this.buttonList)
				((GuiButton)button).enabled = true;
			this.buttonsEnabled = true;
		}
		((GuiButton)this.buttonList.get(4)).enabled = this.activePart.getCreationTime() > 0 && te.checkRecipeCompletion();
	}

	public void updateActivePart(){
		final BoatPart part = BoatPart.getPartByTypeAndMaterial(this.validTypes[this.typeIndex], this.validMaterials[this.materialIndex]);
		if(part == null)
			return;
		final TileEntityBoatbench te = ((ContainerBoatbench)this.inventorySlots).getTileEntity();
		this.activePart = part;
		this.partStack = part.getCraftingResult();
		if(te.getPartBurnTime() <= 0 && this.activePart.getCreationTime() > 0)
			((GuiButton)this.buttonList.get(4)).enabled = true;
		else
			((GuiButton)this.buttonList.get(4)).enabled = false;
		te.setActiveSelection(part.getOnePartBoat(te.getWorldObj()));
		NetworkData.PART_SELECT_CHANNEL.sendToServer(new MessageSelectBoatPart(te, part));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int param1, final int param2){
		final String name = I18n.format(StringHelper.formatGUIUnlocalizedName(this.unlocalizedName));
		this.fontRendererObj.drawString(name, 8, 6, 0x404040);
		this.fontRendererObj.drawString("Inventory", 32, 76, 0x404040);
		//int x = this.partX+8;
		//this.fontRendererObj.drawString("Part", x-(this.fontRendererObj.getStringWidth("Part"))/2, 61-this.fontRendererObj.FONT_HEIGHT, 0x404040);
		//this.fontRendererObj.drawString("Material", x-(this.fontRendererObj.getStringWidth("Material"))/2, 28-this.fontRendererObj.FONT_HEIGHT-1, 0x404040);
		final TileEntityBoatbench te = ((ContainerBoatbench)this.inventorySlots).getTileEntity();
		final FluidTankInfo info = te.getTankInfo(null)[0];
		if(info.fluid != null){
			final float perc = (float) info.fluid.amount/(float) info.capacity;
			int height = (int) (60F*perc);
			RenderHelper.setGLColorFromInt(info.fluid.getFluid().getColor(info.fluid));
			this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
			int y = 78;
			while(height > 0){
				this.drawTexturedModelRectFromIcon(200, y -= 15, info.fluid.getFluid().getIcon(), 16, 15);
				height -= 15;
			}
			this.drawTexturedModelRectFromIcon(200, 78 - height, info.fluid.getFluid().getIcon(), 16, height);
		}else
			this.fontRendererObj.drawString("No Fuel", 224-this.fontRendererObj.getStringWidth("No Fuel")-7, 6, 0xFF0000);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		RenderHelper.drawTexturedRect(this.texture, 205, 18, 225, 17, 12, 60, this.xSize, this.ySize, 1F);

		if(this.activePart != null){
			net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
			GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), this.partStack, this.partX, 36);
			GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), Material.values()[this.materialIndex].getItemRepresentation(), this.materialX, 18);
		}
		/*if(te.getActiveSelection() == null)
			return;
		final List<ItemStack> required = ItemStackHelper.deepClone(te.getActiveSelection().getBoatParts().get(0).getCraftingIngredients());
		RecipeHelper.areItemsPresent(required, te.getInventory(), false);
		if(required.isEmpty())
			return;
		x = 119;
		final int width = 48;
		final int y = 60+this.fontRendererObj.FONT_HEIGHT+2;
		this.fontRendererObj.drawString("Requires", x-(this.fontRendererObj.getStringWidth("Requires:"))/2, 60, 0xdd0000);
		final int leftover = width - 16*required.size();
		final int spacing = leftover/(required.size()+1);
		x-=width/2;
		x+=spacing;
		for(final ItemStack stack:required){
			net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
			GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, x, y);
			GuiScreen.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, x, y);
			//net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
			x+=16+spacing;
		}*/
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float p_146976_1_,
			final int p_146976_2_, final int p_146976_3_) {
		final int xStart = (this.width - this.xSize) / 2;
		final int yStart = (this.height - this.ySize) / 2;
		RenderHelper.drawTexturedRect(this.texture, xStart, yStart, 0, 0, 224, this.ySize, this.xSize, this.ySize, 1F);
		final TileEntityBoatbench te = ((ContainerBoatbench)this.inventorySlots).getTileEntity();
		if(te.getCookTime() > 0){
			final int width = 24;
			final int height = 16;
			final int amount = (int) (width*(float)te.getCookTime()/te.getPartBurnTime());
			RenderHelper.drawTexturedRect(this.texture, xStart+110, yStart+38, 224, 0, amount, height, this.xSize, this.ySize, 1F);
		}

		if(te.isTakingInLiquid()){
			final int width = 4;
			final int height = 14;
			final int amount = (int) (width*((float)te.getLiquidTime()/(float)TileEntityBoatbench.LIQUID_INTAKE_TIME));
			RenderHelper.drawTexturedRect(this.texture, xStart+195, yStart+32, 224, 80, amount, height, this.xSize, this.ySize, 1F);
		}
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
		case 4:
			final TileEntityBoatbench te = ((ContainerBoatbench)this.inventorySlots).getTileEntity();
			if(te.beginCrafting()){
				te.setNoUseIngredients(true);
				NetworkData.CRAFT_PRESS_CHANNEL.sendToServer(new MessagePressCraft(te.xCoord, te.yCoord, te.zCoord));
				for(final Object button1:this.buttonList)
					((GuiButton)button1).enabled = false;
				this.buttonsEnabled = false;
			}
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
