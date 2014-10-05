package me.superckl.betteroceans.common.entity.tile;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
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
		this.rotation = tag.getFloat("lRotation");
	}

	@Override
	public void writeToNBT(final NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setTag("powder", this.lumPowder.writeToNBT(new NBTTagCompound()));;
		tag.setFloat("lRotation", this.rotation);
	}

	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound comp = new NBTTagCompound();
		this.writeToNBT(comp);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, comp);
	}

	@Override
	public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt) {
		final NBTTagCompound comp = pkt.func_148857_g();
		this.readFromNBT(comp);
	}



}
