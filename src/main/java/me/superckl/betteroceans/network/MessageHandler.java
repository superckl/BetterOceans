package me.superckl.betteroceans.network;

import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatWorkbench;
import me.superckl.betteroceans.common.reference.NetworkData;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageHandler implements IMessageHandler<MessageSelectBoat, IMessage>{

	//TODO change to parts

	@Override
	public IMessage onMessage(final MessageSelectBoat message, final MessageContext ctx) {
		try {
			final World world = WorldProvider.getProviderForDimension(message.getDimension()).worldObj;
			final TileEntity te = world.getTileEntity(message.getX(), message.getY(), message.getZ());
			if(te instanceof TileEntityBoatWorkbench == false)
				return null;
			final TileEntityBoatWorkbench tebw= (TileEntityBoatWorkbench) te;
			final Class<? extends EntityBOBoat> clazz = NetworkData.getEntity(message.getEntityId());
			final EntityBOBoat boat = clazz.getConstructor(world.getClass()).newInstance(world); //TODO EntityBOBoat
			tebw.setActiveSelection(boat);
		} catch (final Exception e) {
			LogHelper.warn("Failed to handle boat selection packet!");
			e.printStackTrace();
		}
		return null;
	}

}
