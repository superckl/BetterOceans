package me.superckl.betteroceans.common.entity.tile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

@AllArgsConstructor
public class TileEntityLantern extends TileEntity{

	@Getter
	private ItemStack lumPowder;

	public TileEntityLantern() {}

	@Override
	public void readFromNBT(final NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.lumPowder = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("powder"));
	}

	@Override
	public void writeToNBT(final NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setTag("powder", this.lumPowder.writeToNBT(new NBTTagCompound()));;
	}



}
