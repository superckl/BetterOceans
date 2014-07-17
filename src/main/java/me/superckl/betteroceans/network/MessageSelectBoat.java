package me.superckl.betteroceans.network;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

@AllArgsConstructor
public class MessageSelectBoat implements IMessage{

	@Getter
	private int entityId, dimension, x, y, z;

	@Override
	public void fromBytes(final ByteBuf buf) {
		this.entityId = buf.readInt();
		this.dimension = buf.readInt();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeInt(this.entityId).writeInt(this.dimension).writeInt(this.x).writeInt(this.y).writeInt(this.z);
	}

}
