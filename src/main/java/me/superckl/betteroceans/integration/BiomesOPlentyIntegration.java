package me.superckl.betteroceans.integration;

import java.util.List;
import java.util.ListIterator;

import cpw.mods.fml.relauncher.ReflectionHelper;
import me.superckl.betteroceans.BetterOceans;
import me.superckl.betteroceans.Config;
import me.superckl.betteroceans.common.gen.bop.BiomeGenKelpForestOverride;
import me.superckl.betteroceans.common.reference.ModItems;
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
		LogHelper.debug("BiomesOPlenty found! Integrating...");
		final Config c = BetterOceans.getInstance().getConfig();
		if(c.isSeaweedOrKelp())
			RecipeHelper.replaceItem(new ItemStack(BOPCBlocks.coral1, 1, 11), new ItemStack(ModItems.itemSeaweed), true, false);
		BiomeGenKelpForestOverride kelp = new BiomeGenKelpForestOverride();
		List<BiomeEntry> entries = BOPBiomeManager.overworldSubBiomes[BiomeGenBase.ocean.biomeID];
		ListIterator<BiomeEntry> lit = entries.listIterator();
		BiomeEntry entry;
		while(lit.hasNext())
			if((entry = lit.next()).biome.biomeID == kelp.biomeID){
				lit.set(new BiomeEntry(kelp, entry.itemWeight));
				break;
			}
	}

	public static void postInit(){

	}

}
