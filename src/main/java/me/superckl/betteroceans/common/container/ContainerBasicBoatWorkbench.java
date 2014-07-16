package me.superckl.betteroceans.common.container;

import lombok.Getter;
import me.superckl.betteroceans.common.entity.EntityWoodenBoat;
import me.superckl.betteroceans.common.entity.tile.TileEntityBasicBoatWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBasicBoatWorkbench extends Container{

	@Getter
	private final EntityWoodenBoat entity;

	public ContainerBasicBoatWorkbench(final InventoryPlayer inventoryPlayer, final TileEntityBasicBoatWorkbench te){
		this.entity = new EntityWoodenBoat(inventoryPlayer.player.worldObj);
		this.entity.setRenderWithRotation(true);
		this.bindPlayerInventory(inventoryPlayer);
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
			if (slot < 9) {
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
						8 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
	}

}
