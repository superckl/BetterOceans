package me.superckl.betteroceans.common.container;

import lombok.Getter;
import me.superckl.betteroceans.common.container.components.BoatCraftingSlot;
import me.superckl.betteroceans.common.entity.IEntityBoat;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBoatWorkbench extends Container{

	@Getter
	private final TileEntityBoatWorkbench tileEntity;

	public ContainerBoatWorkbench(final InventoryPlayer inventoryPlayer, final TileEntityBoatWorkbench te){
		this.tileEntity = te;
		this.bindPlayerInventory(inventoryPlayer);
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				this.addSlotToContainer(new Slot(te, j + i * 3,
						15 + j * 18, 23 + i * 18));
		this.addSlotToContainer(new BoatCraftingSlot(te, 9, 221, 41));
	}

	public ContainerBoatWorkbench(final InventoryPlayer inventoryPlayer, final TileEntityBoatWorkbench te, final IEntityBoat activeSelection){
		this(inventoryPlayer, te);
		te.setActiveSelection(activeSelection);
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
						15 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 15 + i * 18, 142));
	}

}
