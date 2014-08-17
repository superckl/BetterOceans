package me.superckl.betteroceans.proxy;

import me.superckl.betteroceans.client.handler.RenderTickHandler;
import me.superckl.betteroceans.client.render.RenderBOBoat;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{

	@Override
	public void registerHandlers() {
		super.registerHandlers();
		MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
		//MinecraftForge.EVENT_BUS.register(new GUIEventHandler());
	}

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityBOBoat.class, new RenderBOBoat());

	}

}
