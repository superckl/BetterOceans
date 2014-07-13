package me.superckl.betteroceans.proxy;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.entity.EntityWoodenBoat;
import cpw.mods.fml.common.registry.EntityRegistry;


public abstract class CommonProxy implements IProxy{

	@Override
	public void registerEntities(){
		EntityRegistry.registerModEntity(EntityWoodenBoat.class, "woodenBoat",
				EntityRegistry.findGlobalUniqueEntityId(), BetterOceans.getInstance(),
				80, 3, true);
	}

}
