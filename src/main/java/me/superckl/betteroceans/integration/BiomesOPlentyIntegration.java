package me.superckl.betteroceans.integration;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.Config;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import net.minecraft.item.ItemStack;
import biomesoplenty.api.content.BOPCBlocks;

public class BiomesOPlentyIntegration {

	public static void init(){
		LogHelper.info("BiomesOPlenty found! Integrating...");
		final Config c = BetterOceans.getInstance().getConfig();
		if(c.isSeaweedOrKelp())
			RecipeHelper.replaceItem(new ItemStack(BOPCBlocks.coral1, 1, 11), new ItemStack(ModItems.itemSeaweed), true, false);
		if(c.isRemoveCoralReef());
		//TODO BiomeGenBase.getBiomeGenArray()[BOPCBiomes.coralReef.biomeID] = null;
	}

}
