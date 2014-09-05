package me.superckl.betteroceans.client.gui;

import me.superckl.betteroceans.client.gui.components.ArrowButton;
import me.superckl.betteroceans.common.container.ContainerBoatbench;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.BoatPart.Material;
import me.superckl.betteroceans.common.parts.BoatPart.Type;
import me.superckl.betteroceans.common.reference.NetworkData;
import me.superckl.betteroceans.common.reference.RenderData;
import me.superckl.betteroceans.common.utility.CollectionHelper;
import me.superckl.betteroceans.common.utility.RenderHelper;
import me.superckl.betteroceans.network.MessageSelectBoatPart;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class GuiContainerBoatbench extends GuiContainer{

	protected ResourceLocation backgroundTexture;

	protected int typeIndex = -1;
	protected Type[] validTypes;
	protected int materialIndex = -1;
	protected Material[] validMaterials;
	protected BoatPart activePart;
	protected ItemStack partStack;

	protected int partX, materialX, materialY;

	protected int craftingOverlayX, craftingOverlayY;

	protected int drawWidth, drawHeight;

	protected boolean buttonsEnabled;

	public GuiContainerBoatbench(final Container container, final TileEntityBoatbench te, final Type[] validTypes, final Material[] validMaterials) {
		super(container);
		this.validTypes = validTypes;
		this.validMaterials = validMaterials;
		if(te.getActiveSelection() != null){
			this.activePart = te.getActiveSelection().getBoatParts().get(0);
			this.partStack = this.activePart.getCraftingResult();
			this.typeIndex = CollectionHelper.find(this.activePart.getType(), Type.values());
			this.materialIndex = CollectionHelper.find(this.activePart.getMaterial(), Material.values());
			//this.updateActivePart();
		}
	}

	public void addPartSelectionAt(int x, final int y, final int width){
		final int i = (this.width - this.xSize) / 2;
		final int j = (this.height - this.ySize) / 2;
		final int leftover = width - 16-12*2;
		final int spacing = leftover/4;
		//int x = 4; // 176
		x += spacing;
		//12, 30 from
		this.buttonList.add(new ArrowButton(2, i+x, j+y, 12, 12, "", true));
		x+=spacing+12;
		this.materialX = x;
		x+=spacing+16;
		this.buttonList.add(new ArrowButton(3, i+x, j+y, 12, 12, "", false));
		x=4+spacing;
		this.buttonList.add(new ArrowButton(0, i+x, j+y+18, 12, 12, "", true));
		x+=spacing+12;
		this.partX = x;
		x+=spacing+16;
		this.buttonList.add(new ArrowButton(1, i+x, j+y+18, 12, 12, "", false));
		this.materialY = y-2;
		this.buttonsEnabled = true;
	}

	public void setCraftingOverlayLocation(final int x, final int y){
		this.craftingOverlayX = x;
		this.craftingOverlayY = y;
	}

	public void setDrawingDimensions(final int width, final int height){
		this.drawWidth = width;
		this.drawHeight = height;
	}

	/*	@Override
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

		this.buttonList.add(new HammerButton(4, i+this.partX-1, j+53, 18, 18, ""));
		this.buttonsEnabled = true;
	}*/



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
		//((GuiButton)this.buttonList.get(4)).enabled = this.activePart != null && this.activePart.getCreationTime() > 0 && te.checkRecipeCompletionNoSet();
	}

	public void updateActivePart(){
		final BoatPart part = BoatPart.getPartByTypeAndMaterial(this.validTypes[this.typeIndex], this.validMaterials[this.materialIndex]);
		if(part == null)
			return;
		final TileEntityBoatbench te = ((ContainerBoatbench)this.inventorySlots).getTileEntity();
		this.activePart = part;
		this.partStack = part.getCraftingResult();
		/*if(te.getPartBurnTime() <= 0 && this.activePart.getCreationTime() > 0)
			((GuiButton)this.buttonList.get(4)).enabled = true;
		else
			((GuiButton)this.buttonList.get(4)).enabled = false;*/
		te.setActiveSelection(part.getOnePartBoat(te.getWorldObj()));
		NetworkData.PART_SELECT_CHANNEL.sendToServer(new MessageSelectBoatPart(te, part));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int param1, final int param2){
		if(this.activePart != null){
			net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
			GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), this.partStack, this.partX, this.materialY+18);
			GuiScreen.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), Material.values()[this.materialIndex].getItemRepresentation(), this.materialX, this.materialY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float p_146976_1_,
			final int p_146976_2_, final int p_146976_3_) {
		final int xStart = (this.width - this.xSize) / 2;
		final int yStart = (this.height - this.ySize) / 2;
		RenderHelper.drawTexturedRect(this.backgroundTexture, xStart, yStart, 0, 0, this.drawWidth, this.drawHeight, this.xSize, this.ySize, 1F);
		final TileEntityBoatbench te = ((ContainerBoatbench)this.inventorySlots).getTileEntity();

		if(te.getStackInSlot(0) == null)
			RenderHelper.drawTexturedRect(RenderData.WIDGETS, xStart+this.craftingOverlayX, yStart+this.craftingOverlayY+1, 0, 95, 16, 16, 256, 256, 1F);

		if(te.getStackInSlot(1) == null)
			RenderHelper.drawTexturedRect(RenderData.WIDGETS, xStart+this.craftingOverlayX, yStart+this.craftingOverlayY+18+1, 0, 111, 16, 16, 256, 256, 1F);

		if(te.getStackInSlot(2) == null)
			RenderHelper.drawTexturedRect(RenderData.WIDGETS, xStart+this.craftingOverlayX+18, yStart+this.craftingOverlayY+9+1, 0, 95, 16, 16, 256, 256, 1F);
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
