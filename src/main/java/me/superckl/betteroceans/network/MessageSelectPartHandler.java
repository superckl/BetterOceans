package me.superckl.betteroceans.network;

import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSelectPartHandler implements IMessageHandler<MessageSelectBoatPart, IMessage>{

	@Override
	public IMessage onMessage(final MessageSelectBoatPart message, final MessageContext ctx) {
		final TileEntity te = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.getX(), message.getY(), message.getZ());
		if(te == null || !(te instanceof TileEntityBoatbench)){
			LogHelper.error("Failed to deserialize TileEntity");
			return null;
		}
		message.setTe((TileEntityBoatbench) te);
		if(message.getTe() == null || message.getPart() == null){
			LogHelper.error("Failed to deserialize message!");
			return null;
		}
		message.getTe().setActiveSelection(message.getPart());
		return null;
	}

}
