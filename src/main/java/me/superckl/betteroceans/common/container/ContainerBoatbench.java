package me.superckl.betteroceans.common.container;

import lombok.Getter;
import me.superckl.betteroceans.common.container.components.NoPutSlot;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.parts.PartBottom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerBoatbench extends Container{

	@Getter
	private final TileEntityBoatbench tileEntity;
	@Getter
	private final InventoryPlayer playerInventory;

	public ContainerBoatbench(final InventoryPlayer inventoryPlayer, final TileEntityBoatbench te){
		this.tileEntity = te;
		this.playerInventory = inventoryPlayer;
		if(te.getActiveSelection() == null){
			final EntityBOBoat boat = new EntityBOBoat(inventoryPlayer.player.worldObj);
			boat.getBoatParts().add(new PartBottom.PartWoodenBottom());
			te.setActiveSelection(boat);
		}
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

	protected void bindCraftingAt(final int x, final int y){
		this.addSlotToContainer(new Slot(this.tileEntity, 0, x, y));
		this.addSlotToContainer(new Slot(this.tileEntity, 1, x, y+18));
		this.addSlotToContainer(new Slot(this.tileEntity, 2, x+18, y+9));
		this.addSlotToContainer(new NoPutSlot(this.tileEntity, 5, x+76, y+9));
	}

	protected void bindPlayerInventoryAt(final int x, final int y) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(this.playerInventory, j + i * 9 + 9,
						x + j * 18, y + i * 18));

		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(this.playerInventory, i, x + i * 18, y+58));
	}

}
