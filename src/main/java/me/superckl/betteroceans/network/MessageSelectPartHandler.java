package me.superckl.betteroceans.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSelectPartHandler implements IMessageHandler<MessageSelectBoatPart, IMessage>{

	//TODO change to parts

	@Override
	public IMessage onMessage(final MessageSelectBoatPart message, final MessageContext ctx) {
		if(message.getTe() == null || message.getPart() == null)
			return null;
		message.getTe().setActiveSelection(message.getPart().getOnePartBoat(message.getTe().getWorldObj()));
		return null;
	}

}
