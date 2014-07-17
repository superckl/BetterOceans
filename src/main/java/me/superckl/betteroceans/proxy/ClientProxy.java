package me.superckl.betteroceans.proxy;

import me.superckl.betteroceans.client.handler.RenderTickHandler;
import me.superckl.betteroceans.client.render.RenderWoodenBoat;
import me.superckl.betteroceans.common.entity.EntityWoodenBoat;
import net.minecraft.entity.item.EntityBoat;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{

	@Override
	public void registerHandlers() {
		super.registerHandlers();
		MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
	}

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityWoodenBoat.class, new RenderWoodenBoat());

	}

}
