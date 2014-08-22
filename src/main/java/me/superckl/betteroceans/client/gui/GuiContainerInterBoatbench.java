package me.superckl.betteroceans.client.gui;

import me.superckl.betteroceans.common.container.ContainerInterBoatbench;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.parts.BoatPart.Material;
import me.superckl.betteroceans.common.parts.BoatPart.Type;
import me.superckl.betteroceans.common.reference.ModData;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiContainerInterBoatbench extends GuiContainerBoatBench{

	public GuiContainerInterBoatbench(final InventoryPlayer inventoryPlayer,
			final TileEntityBoatbench te, final Type[] validTypes,
			final Material[] validMaterials) {
		super(new ContainerInterBoatbench(inventoryPlayer, te), te, validTypes, validMaterials);
		this.setTexture(new ResourceLocation(ModData.MOD_ID+":textures/gui/interbench.png"), 400, 300); //TODO
		// TODO
	}

}
