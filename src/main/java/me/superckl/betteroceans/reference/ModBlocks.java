package me.superckl.betteroceans.reference;

import cpw.mods.fml.common.registry.GameRegistry;
import me.superckl.betteroceans.Config;
import me.superckl.betteroceans.block.BlockSeaweed;
import me.superckl.betteroceans.item.ItemSeaweed;
import me.superckl.betteroceans.reference.ModBlocks.Names;

public class ModBlocks {

	public static final BlockSeaweed seaweed = new BlockSeaweed();
	
	public static void init(){
		GameRegistry.registerBlock(seaweed, Names.SEAWEED);
	}
	
	public static class Names{
		
		public static final String SEAWEED = "blockSeaweed";
		
		
	}
	
}
