package me.superckl.betteroceans.common.entity.tile;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

@RequiredArgsConstructor
public class TileEntityLantern extends TileEntity{

	@Getter
	@NonNull
	private ItemStack lumPowder;
	@Getter
	@Setter
	private float rotation;

	public TileEntityLantern() {}

	@Override
	public void readFromNBT(final NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.lumPowder = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("powder"));
		this.rotation = tag.getFloat("rotation");
	}

	@Override
	public void writeToNBT(final NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setTag("powder", this.lumPowder.writeToNBT(new NBTTagCompound()));;
		tag.setFloat("rotation", this.rotation);
	}



}
