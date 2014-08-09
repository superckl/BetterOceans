package me.superckl.betteroceans.integration;

import java.util.List;
import java.util.ListIterator;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.Config;
import me.superckl.betteroceans.common.gen.bop.BiomeGenCoralReefOverride;
import me.superckl.betteroceans.common.gen.bop.BiomeGenKelpForestOverride;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.BiomeHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import biomesoplenty.api.content.BOPCBiomes;
import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.common.world.BOPBiomeManager;

public class BiomesOPlentyIntegration{


	public static void preInit(){
		final Config c = BetterOceans.getInstance().getConfig();
		if(c.isSeaweedOrKelp()){
			RecipeHelper.replaceItem(new ItemStack(BOPCBlocks.coral1, 1, 11), new ItemStack(ModItems.itemSeaweed), true, false);
			LogHelper.debug("Added seaweed as kelp recipes...");
		}
		if(c.isOverrideOcean()){
			final BiomeGenKelpForestOverride kelp = new BiomeGenKelpForestOverride();
			final BiomeGenCoralReefOverride coral = new BiomeGenCoralReefOverride();
			BOPCBiomes.kelpForest = kelp;
			BOPCBiomes.coralReef = coral;
			final List<BiomeEntry> entries = BOPBiomeManager.overworldSubBiomes[BiomeGenBase.ocean.biomeID];
			final ListIterator<BiomeEntry> lit = entries.listIterator();
			BiomeEntry entry;
			while(lit.hasNext())
				if((entry = lit.next()).biome.biomeID == kelp.biomeID){
					lit.set(new BiomeEntry(kelp, entry.itemWeight));
					BiomeHelper.oceanBiomeIDs.add(kelp.biomeID);
					LogHelper.debug("Extended Kelp Forest...");
				}else if(entry.biome.biomeID == coral.biomeID){
					lit.set(new BiomeEntry(coral, entry.itemWeight));
					BiomeHelper.oceanBiomeIDs.add(coral.biomeID);
					LogHelper.debug("Extended Coral Reef...");
				}
		}
	}

	public static void postInit(){

	}

}
