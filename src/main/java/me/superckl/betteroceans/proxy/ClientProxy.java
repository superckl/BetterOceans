package me.superckl.betteroceans.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import me.superckl.betteroceans.client.tick.RenderTickHandler;
import me.superckl.betteroceans.utility.LogHelper;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{

	@Override
	public void registerTickHandlers() {
		LogHelper.info("registering");
		FMLCommonHandler.instance().bus().register(new RenderTickHandler());
	}

}
