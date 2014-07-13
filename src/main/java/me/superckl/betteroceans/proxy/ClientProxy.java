package me.superckl.betteroceans.proxy;

import me.superckl.betteroceans.client.handler.RenderTickHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{

	@Override
	public void registerTickHandlers() {
		MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
	}

}
