package me.superckl.betteroceans.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessagePartUpdateHandler implements IMessageHandler<MessagePartUpdate, IMessage>{

	@Override
	public IMessage onMessage(final MessagePartUpdate message, final MessageContext ctx) {
		if(message.getBoat() == null || message.getParts() == null)
			return null;
		message.getBoat().getBoatParts().addAll(message.getParts());
		return null;
	}

}
