package me.superckl.betteroceans.common.container;

import me.superckl.betteroceans.common.container.components.FluidContainerSlot;
import me.superckl.betteroceans.common.container.components.NoPutSlot;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerInterBoatbench extends ContainerBoatbench{

	public ContainerInterBoatbench(final InventoryPlayer inventoryPlayer,
			final TileEntityBoatbench te) {
		super(inventoryPlayer, te);
		this.bindPlayerInventoryAt(32, 88);
		this.bindCraftingAt(67, 29);

		final FluidContainerSlot fSlot = (FluidContainerSlot) this.addSlotToContainer(new FluidContainerSlot(te, 3, 178, 31));
		final Slot slot = this.addSlotToContainer(new NoPutSlot(te, 4, 178, 49));
		fSlot.setDependency(slot);
	}

}
