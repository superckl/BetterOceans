package me.superckl.betteroceans.network;

import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessagePressCraftHandler implements IMessageHandler<MessagePressCraft, IMessage>{

	@Override
	public IMessage onMessage(final MessagePressCraft message, final MessageContext ctx) {
		final TileEntity te = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.getX(), message.getY(), message.getZ());
		if(te == null || !(te instanceof TileEntityBoatbench)){
			LogHelper.error("Failed to deserialize TileEntity");
			return null;
		}
		if(((TileEntityBoatbench)te).beginCrafting())
			((TileEntityBoatbench)te).setNoUseIngredients(true);
		return null;
	}

}
