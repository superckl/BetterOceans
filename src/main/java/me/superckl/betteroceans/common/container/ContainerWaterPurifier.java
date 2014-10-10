package me.superckl.betteroceans.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerWaterPurifier extends Container{

	@Override
	public boolean canInteractWith(final EntityPlayer p_75145_1_) {
		return true;
	}

}
