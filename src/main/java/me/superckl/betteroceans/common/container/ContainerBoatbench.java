package me.superckl.betteroceans.common.container;

import lombok.Getter;
import me.superckl.betteroceans.common.container.components.FluidContainerSlot;
import me.superckl.betteroceans.common.container.components.NoPutSlot;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBoatbench extends Container{

	@Getter
	private final TileEntityBoatbench tileEntity;

	public ContainerBoatbench(final InventoryPlayer inventoryPlayer, final TileEntityBoatbench te){
		this.tileEntity = te;
		this.bindPlayerInventory(inventoryPlayer);
		this.addSlotToContainer(new Slot(te, 0, 68, 29));
		this.addSlotToContainer(new Slot(te, 1, 68, 47));
		this.addSlotToContainer(new Slot(te, 2, 86, 38));

		final FluidContainerSlot fSlot = (FluidContainerSlot) this.addSlotToContainer(new FluidContainerSlot(te, 3, 178, 31));
		final Slot slot = this.addSlotToContainer(new NoPutSlot(te, 4, 178, 49));
		fSlot.setDependency(slot);
		/*for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				this.addSlotToContainer(new Slot(te, j + i * 3,
						15 + j * 18, 18 + i * 18));*/
		this.addSlotToContainer(new NoPutSlot(te, 5, 144, 38));
	}

	@Override
	public boolean canInteractWith(final EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(final EntityPlayer player, final int slot) {
		ItemStack stack = null;
		final Slot slotObject = (Slot) this.inventorySlots.get(slot);
		//null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			final ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			//merges the item into player inventory since its in the tileEntity
			if (slot < 5) {
				if (!this.mergeItemStack(stackInSlot, 0, 35, true))
					return null;
			}
			//places it into the tileEntity is possible since its in the player inventory
			else if (!this.mergeItemStack(stackInSlot, 0, 9, false))
				return null;

			if (stackInSlot.stackSize == 0)
				slotObject.putStack(null);
			else
				slotObject.onSlotChanged();

			if (stackInSlot.stackSize == stack.stackSize)
				return null;
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}

	protected void bindPlayerInventory(final InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
						32 + j * 18, 88 + i * 18));

		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 32 + i * 18, 146));
	}

}