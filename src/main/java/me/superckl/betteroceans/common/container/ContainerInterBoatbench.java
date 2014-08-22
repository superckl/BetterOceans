package me.superckl.betteroceans.common.container;

import lombok.Getter;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerInterBoatbench extends Container{

	@Getter
	private final TileEntityBoatbench tileEntity;

	public ContainerInterBoatbench(final InventoryPlayer inventoryPlayer, final TileEntityBoatbench te){
		this.tileEntity = te;
		this.bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canInteractWith(final EntityPlayer p_75145_1_) {
		return true;
	}

	protected void bindPlayerInventory(final InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
						15 + j * 18, 79 + i * 18));

		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 15 + i * 18, 137));
	}

}
