package me.superckl.betteroceans.integration;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import biomesoplenty.api.content.BOPCBiomes;
import biomesoplenty.api.content.BOPCItems;
import biomesoplenty.common.core.BOPItems;
import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.Config;
import me.superckl.betteroceans.common.gen.BiomeGenBetterDeepOcean;
import me.superckl.betteroceans.common.gen.BiomeGenBetterOcean;
import me.superckl.betteroceans.common.utility.BiomeHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.RecipeHelper;

public class BiomesOPlentyIntegration {

	public static void init(){
		LogHelper.info("BiomesOPlenty found! Integrating...");
		Config c = BetterOceans.getInstance().getConfig();
		if(c.isRemoveCoralReef());
			//TODO BiomeGenBase.getBiomeGenArray()[BOPCBiomes.coralReef.biomeID] = null;
	}
	
}
