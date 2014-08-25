package me.superckl.betteroceans.network;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

@AllArgsConstructor
public class MessagePressCraft implements IMessage{

	@Getter
	@Setter
	private int x, y, z;

	public MessagePressCraft(){}

	@Override
	public void fromBytes(final ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();

	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeInt(this.x).writeInt(this.y).writeInt(this.z);

	}

}
