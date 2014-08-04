package me.superckl.betteroceans.network;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatWorkbench;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.utility.BoatHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

@RequiredArgsConstructor
public class MessageSelectBoatPart implements IMessage{

	@Getter
	@Setter
	@NonNull
	private TileEntityBoatWorkbench te;
	@Getter
	@Setter
	@NonNull
	private BoatPart part;
	@Getter
	@Setter
	int x, y, z;

	public MessageSelectBoatPart() {

	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		final BoatPart part = BoatHelper.readPartFromBuffer(buf);
		if(part == null){
			LogHelper.error("Failed to deserialize BoatPart!");
			return;
		}
		this.part = part;
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeInt(this.te.xCoord).writeInt(this.te.yCoord).writeInt(this.te.zCoord);
		BoatHelper.writePartToBuffer(this.part, buf);
	}

}
