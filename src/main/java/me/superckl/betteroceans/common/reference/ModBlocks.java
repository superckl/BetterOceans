package me.superckl.betteroceans.common.reference;

import me.superckl.betteroceans.common.block.BlockBasicBoatWorkbench;
import me.superckl.betteroceans.common.block.BlockSeaweed;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(ModData.MOD_ID)
public class ModBlocks {

	public static final BlockSeaweed seaweed = new BlockSeaweed();
	public static final BlockBasicBoatWorkbench basicBoatWorkbench = new BlockBasicBoatWorkbench();

	public static void init(){
		GameRegistry.registerBlock(ModBlocks.seaweed, Names.SEAWEED);
		GameRegistry.registerBlock(ModBlocks.basicBoatWorkbench, Names.BASIC_BOAT_WORKBENCH);
	}

	public static class Names{

		public static final String SEAWEED =  "seaweed";
		public static final String BASIC_BOAT_WORKBENCH =  "basicBoatWorkbench";

	}

}
