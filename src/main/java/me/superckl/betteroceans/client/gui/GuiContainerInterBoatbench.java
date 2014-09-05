package me.superckl.betteroceans.client.gui;

import me.superckl.betteroceans.client.gui.components.HammerButton;
import me.superckl.betteroceans.common.container.ContainerBoatbench;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.parts.BoatPart.Material;
import me.superckl.betteroceans.common.parts.BoatPart.Type;
import me.superckl.betteroceans.common.reference.NetworkData;
import me.superckl.betteroceans.common.reference.RenderData;
import me.superckl.betteroceans.common.utility.RenderHelper;
import me.superckl.betteroceans.common.utility.StringHelper;
import me.superckl.betteroceans.network.MessagePressCraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraftforge.fluids.FluidTankInfo;

public class GuiContainerInterBoatbench extends GuiContainerBoatbench{

	public GuiContainerInterBoatbench(final Container container, final TileEntityBoatbench te, final Type[] validTypes,
			final Material[] validMaterials) {
		super(container, te, validTypes, validMaterials);
		this.xSize = 224;
		this.ySize = 170;
		this.backgroundTexture = RenderData.INTER_BOAT_BENCH;
		this.setCraftingOverlayLocation(67, 29);
		this.setDrawingDimensions(this.xSize, this.ySize);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.addPartSelectionAt(4, 20, 52);
		final int i = (this.width - this.xSize) / 2;
		final int j = (this.height - this.ySize) / 2;
		this.buttonList.add(new HammerButton(4, i+this.partX-1, j+53, 18, 18, ""));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int param1, final int param2){
		super.drawGuiContainerForegroundLayer(param1, param2);
		final String name = I18n.format(StringHelper.formatGUIUnlocalizedName("interboatbench"));
		this.fontRendererObj.drawString(name, 8, 6, 0x404040);
		this.fontRendererObj.drawString("Inventory", 32, 76, 0x404040);
		final TileEntityBoatbench te = ((ContainerBoatbench)this.inventorySlots).getTileEntity();
		final FluidTankInfo info = te.getTankInfo(null)[0];
		if(info != null && info.fluid != null)
			RenderHelper.drawFluid(this.mc, 200, 78, 16, 60, info);
		else if(te.isShouldHandleFluids())
			this.fontRendererObj.drawString("No Fuel", 224-this.fontRendererObj.getStringWidth("No Fuel")-7, 6, 0xFF0000);
		RenderHelper.drawTankOverlay(204, 18);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float p_146976_1_,
			final int p_146976_2_, final int p_146976_3_) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, p_146976_2_, p_146976_3_);
		final int xStart = (this.width - this.xSize) / 2;
		final int yStart = (this.height - this.ySize) / 2;
		final TileEntityBoatbench te = ((ContainerBoatbench)this.inventorySlots).getTileEntity();
		if(te.getCookTime() > 0){
			final int width = 24;
			final int height = 16;
			final int amount = (int) (width*(float)te.getCookTime()/te.getPartBurnTime());
			RenderHelper.drawTexturedRect(RenderData.INTER_BOAT_BENCH, xStart+109, yStart+38, 224, 0, amount, height, this.xSize, this.ySize, 1F);
		}

		if(te.getStackInSlot(3) == null)
			RenderHelper.drawTexturedRect(RenderData.WIDGETS, xStart+178, yStart+31+1, 0, 127, 16, 16, 256, 256, 1F);

		if(te.isTakingInLiquid()){
			final int width = 4;
			final int height = 14;
			final int amount = (int) (width*((float)te.getLiquidTime()/(float)TileEntityBoatbench.LIQUID_INTAKE_TIME));
			RenderHelper.drawTexturedRect(RenderData.WIDGETS, xStart+195, yStart+32, 0, 80, amount, height, 256, 256, 1F);
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) {
		switch(button.id){
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
		}
		super.actionPerformed(button);
	}

}
