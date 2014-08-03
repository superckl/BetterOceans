package me.superckl.betteroceans.network;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatWorkbench;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.utility.BoatHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

@AllArgsConstructor
public class MessageSelectBoatPart implements IMessage{

	@Getter
	private TileEntityBoatWorkbench te;
	@Getter
	private BoatPart part;

	@Override
	public void fromBytes(final ByteBuf buf) {
		final World world = WorldProvider.getProviderForDimension(buf.readInt()).worldObj;
		if(world == null){
			LogHelper.error("Failed to deserialize TileEntityBoatWorkbench!");
			return;
		}
		final TileEntity te = world.getTileEntity(buf.readInt(), buf.readInt(), buf.readInt());
		if(te == null || te instanceof TileEntityBoatWorkbench == false){
			LogHelper.error("Failed to deserialize TileEntityBoatWorkbench!");
			return;
		}
		this.te = (TileEntityBoatWorkbench) te;
		final BoatPart part = BoatHelper.readPartFromBuffer(buf);
		if(part == null){
			LogHelper.error("Failed to deserialize BoatPart!");
			return;
		}
		this.part = part;
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeInt(this.te.getWorldObj().provider.dimensionId).writeInt(this.te.xCoord).writeInt(this.te.yCoord).writeInt(this.te.zCoord);
		BoatHelper.writePartToBuffer(this.part, buf);
	}

}
