package me.superckl.betteroceans.proxy;

import me.superckl.betteroceans.client.tick.RenderTickHandler;
import me.superckl.betteroceans.utility.LogHelper;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{

	@Override
	public void registerTickHandlers() {
		LogHelper.info("registering");
		MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
	}

}
