package me.superckl.betteroceans.integration.mariculture;

import mariculture.core.lib.Modules;
import mariculture.transport.Transport;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import me.superckl.betteroceans.integration.IIntegrationModule;
import net.minecraft.item.ItemStack;

public class MaricultureIntegration implements IIntegrationModule{

	@Override
	public void preInit() {}

	@Override
	public void init() {
		if(Modules.isActive(Modules.transport)){
			RecipeHelper.removeRecipes(new ItemStack(Transport.speedBoat));
			LogHelper.debug("Removed Mariculture speedboat recipe...");
		}

	}

	@Override
	public void postInit() {}

	@Override
	public String getName() {
		return "Mariculture Integration";
	}

}
