package me.superckl.betteroceans.integration;

import java.util.List;
import java.util.ListIterator;

import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.Config;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import me.superckl.betteroceans.common.utility.StringHelper;
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

			//Remove all ocean biomes
			LogHelper.debug("Removing BOP ocean subbiomes...");
			final List<BiomeEntry> entries = BOPBiomeManager.overworldSubBiomes[BiomeGenBase.ocean.biomeID];
			if(entries != null){
				final ListIterator<BiomeEntry> lit = entries.listIterator();
				BiomeEntry entry;
				while(lit.hasNext()){
					BiomeGenBase.getBiomeGenArray()[(entry = lit.next()).biome.biomeID] = null;
					LogHelper.debug(StringHelper.build("Removed ", entry.biome.biomeName, " subbiome..."));
				}
			}
			BOPBiomeManager.overworldSubBiomes[BiomeGenBase.ocean.biomeID]= null;

		}
	}

	public static void postInit(){

	}

}
