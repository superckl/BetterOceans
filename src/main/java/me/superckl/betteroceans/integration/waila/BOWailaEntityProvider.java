package me.superckl.betteroceans.integration.waila;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.SpecialChars;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.utility.StringHelper;
import net.minecraft.entity.Entity;

public class BOWailaEntityProvider implements IWailaEntityProvider{

	@Override
	public Entity getWailaOverride(final IWailaEntityAccessor accessor,
			final IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(final Entity entity, final List<String> currenttip,
			final IWailaEntityAccessor accessor, final IWailaConfigHandler config) {
		if(currenttip.get(0).contains("entity.bobetterboat"))
			currenttip.set(0, SpecialChars.WHITE+"Better Boat");
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(final Entity entity, final List<String> currenttip,
			final IWailaEntityAccessor accessor, final IWailaConfigHandler config) {
		if(entity instanceof EntityBOBoat && config.getConfig("showboatinfo")){
			final EntityBOBoat boat = (EntityBOBoat) entity;
			final Map<String, Integer> map = new HashMap<String, Integer>();
			for(final BoatPart part:boat.getBoatParts()){
				final String name = part.getNiceName();
				if(!map.containsKey(name))
					map.put(name, 0);
				map.put(name, map.get(name)+1);
			}
			for(final Entry<String, Integer> entry:map.entrySet())
				currenttip.add(StringHelper.build(entry.getKey(), " x", entry.getValue()));
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(final Entity entity, final List<String> currenttip,
			final IWailaEntityAccessor accessor, final IWailaConfigHandler config) {
		return currenttip;
	}

	public static void callbackRegister(final IWailaRegistrar registrar){
		registrar.addConfig("Better Oceans", "showboatinfo", "Show Boat Info");
		final BOWailaEntityProvider provider = new BOWailaEntityProvider();
		registrar.registerBodyProvider(provider, EntityBOBoat.class);
	}

}
