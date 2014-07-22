package me.superckl.betteroceans.integration;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.common.gen.BiomeGenBetterDeepOcean;
import me.superckl.betteroceans.common.gen.BiomeGenBetterOcean;
import me.superckl.betteroceans.common.utility.BiomeHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.Loader;

public class BOIntegration {

	public static void postInit(){
		if(Loader.isModLoaded("BiomesOPlenty"))
			BiomesOPlentyIntegration.init();
		
		//Ensure oceans are still overriden...
		if(BetterOceans.getInstance().getConfig().isOverrideOcean() && (BiomeGenBase.getBiomeGenArray()[BiomeGenBase.ocean.biomeID] instanceof BiomeGenBetterOcean == false || 
				BiomeGenBase.getBiomeGenArray()[BiomeGenBase.deepOcean.biomeID] instanceof BiomeGenBetterDeepOcean == false)){
			LogHelper.warn("Something else overrode oceans since preInit(or overriding them failed)! You should probably report this (Include a list of possible mods, thanks)... Re-overriding...");
			BiomeHelper.replaceOceanBiomes();
		}
	}
	
	
}
