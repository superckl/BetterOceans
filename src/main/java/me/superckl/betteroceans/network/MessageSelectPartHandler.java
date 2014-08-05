package me.superckl.betteroceans.network;

import me.superckl.betteroceans.common.entity.tile.TileEntityBoatWorkbench;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSelectPartHandler implements IMessageHandler<MessageSelectBoatPart, IMessage>{

	//TODO change to parts

	@Override
	public IMessage onMessage(final MessageSelectBoatPart message, final MessageContext ctx) {
		final TileEntity te = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.getX(), message.getY(), message.getZ());
		LogHelper.info("received select part packet");
		if(te == null || te instanceof TileEntityBoatWorkbench == false){
			LogHelper.error("Failed to deserialize TileEntity");
			return null;
		}
		message.setTe((TileEntityBoatWorkbench) te);
		if(message.getTe() == null || message.getPart() == null){
			LogHelper.error("Failed to deserialize message!");
			return null;
		}
		message.getTe().setActiveSelection(message.getPart().getOnePartBoat(message.getTe().getWorldObj()));
		return null;
	}

}
