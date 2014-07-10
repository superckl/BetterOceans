package me.superckl.betteroceans.reference;

import me.superckl.betteroceans.block.BlockSeaweed;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static final BlockSeaweed seaweed = new BlockSeaweed();

	public static void init(){
		GameRegistry.registerBlock(ModBlocks.seaweed, Names.SEAWEED);
	}

	public static class Names{

		public static final String SEAWEED = "blockSeaweed";


	}

}
