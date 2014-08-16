package me.superckl.betteroceans.integration;

import java.util.List;
import java.util.ListIterator;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.Config;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.common.world.BOPBiomeManager;

public class BiomesOPlentyIntegration{


	public static void preInit(){
		final Config c = BetterOceans.getInstance().getConfig();
		if(c.isSeaweedOrKelp()){
			RecipeHelper.replaceItem(new ItemStack(BOPCBlocks.coral1, 1, 11), new ItemStack(ModItems.itemSeaweed), true, false);
			LogHelper.debug("Added seaweed as kelp recipes...");
		}
		if(c.isOverrideOcean() && c.isRemoveSubbiomes()){
			//final BiomeGenKelpForestOverride kelp = new BiomeGenKelpForestOverride();
			//final BiomeGenCoralReefOverride coral = new BiomeGenCoralReefOverride();
			//BOPCBiomes.kelpForest = kelp;
			//BOPCBiomes.coralReef = coral;

			//Remove all ocean biomes
			LogHelper.debug("Removing BOP ocean subbiomes...");
			final List<BiomeEntry> entries = BOPBiomeManager.overworldSubBiomes[BiomeGenBase.ocean.biomeID];
			if(entries != null){
				final ListIterator<BiomeEntry> lit = entries.listIterator();
				while(lit.hasNext())
					BiomeGenBase.getBiomeGenArray()[lit.next().biome.biomeID] = null;
			}
			BOPBiomeManager.overworldSubBiomes[BiomeGenBase.ocean.biomeID]= null;

			/*final ListIterator<BiomeEntry> lit = entries.listIterator();
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
				}*/
		}
	}

	public static void postInit(){

	}

}
