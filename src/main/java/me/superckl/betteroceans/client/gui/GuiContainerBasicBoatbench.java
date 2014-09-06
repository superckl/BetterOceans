package me.superckl.betteroceans.client.gui;

import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.parts.BoatPart.Material;
import me.superckl.betteroceans.common.parts.BoatPart.Type;
import me.superckl.betteroceans.common.reference.RenderData;
import me.superckl.betteroceans.common.utility.StringHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;

public class GuiContainerBasicBoatbench extends GuiContainerBoatbench{

	public GuiContainerBasicBoatbench(final Container container,
			final TileEntityBoatbench te, final Type[] validTypes, final Material[] validMaterials) {
		super(container, te, validTypes, validMaterials);
		this.xSize = 176;
		this.ySize = 170;
		this.backgroundTexture = RenderData.BASIC_BOAT_BENCH;
		this.setCraftingOverlayLocation(71, 29);
		this.setDrawingDimensions(this.xSize, this.ySize);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.addPartSelectionAt(4, 31, 52);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int param1, final int param2){
		super.drawGuiContainerForegroundLayer(param1, param2);
		final String name = I18n.format(StringHelper.formatGUIUnlocalizedName("basicboatbench"));
		this.fontRendererObj.drawString(name, 8, 6, 0x404040);
		this.fontRendererObj.drawString("Inventory", 8, 76, 0x404040);

	}

}
