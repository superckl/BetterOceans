package me.superckl.betteroceans.integration;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.Config;
import me.superckl.betteroceans.common.utility.LogHelper;

public class BiomesOPlentyIntegration {

	public static void init(){
		LogHelper.info("BiomesOPlenty found! Integrating...");
		final Config c = BetterOceans.getInstance().getConfig();
		if(c.isRemoveCoralReef());
		//TODO BiomeGenBase.getBiomeGenArray()[BOPCBiomes.coralReef.biomeID] = null;
	}

}
