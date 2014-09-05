package me.superckl.betteroceans.common.container;

import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerBasicBoatbench extends ContainerBoatbench{

	public ContainerBasicBoatbench(final InventoryPlayer inventoryPlayer,
			final TileEntityBoatbench te) {
		super(inventoryPlayer, te);
		this.bindPlayerInventoryAt(8, 88);
		this.bindCraftingAt(71, 29);
	}

}
