package me.superckl.betteroceans.integration;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.gen.BiomeGenBetterDeepOcean;
import me.superckl.betteroceans.common.gen.BiomeGenBetterOcean;
import me.superckl.betteroceans.common.utility.BiomeHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.integration.bop.BiomesOPlentyIntegration;
import me.superckl.betteroceans.integration.nei.NotEnoughItemsIntegration;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.Loader;

public class BOIntegration {

	public static void preInit(){
		if(Loader.isModLoaded("BiomesOPlenty"))
			BiomesOPlentyIntegration.preInit();
	}

	public static void postInit(){
		if(Loader.isModLoaded("BiomesOPlenty"))
			BiomesOPlentyIntegration.postInit();
		if(Loader.isModLoaded("NotEnoughItems"))
			NotEnoughItemsIntegration.postInit();

		//Ensure oceans are still overriden...
		if(BetterOceans.getInstance().getConfig().isOverrideOcean() && BetterOceans.getInstance().getConfig().isForceOverride() && (!(BiomeGenBase.getBiomeGenArray()[BiomeGenBase.ocean.biomeID] instanceof BiomeGenBetterOcean) ||
				!(BiomeGenBase.getBiomeGenArray()[BiomeGenBase.deepOcean.biomeID] instanceof BiomeGenBetterDeepOcean))){
			LogHelper.warn("Something else overrode oceans since Init (or overriding them failed)! You should probably report this (Include a list of possible mods, thanks)... Re-overriding...");
			BiomeHelper.replaceOceanBiomes();
		}
	}


}
