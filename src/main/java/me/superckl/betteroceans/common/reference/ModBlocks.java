package me.superckl.betteroceans.common.reference;

import me.superckl.betteroceans.common.block.BlockBoatbench;
import me.superckl.betteroceans.common.block.BlockHardCoral;
import me.superckl.betteroceans.common.block.BlockSeaweed;
import me.superckl.betteroceans.common.block.BlockSoftCoral;
import me.superckl.betteroceans.common.block.item.ItemBlockBoatbench;
import me.superckl.betteroceans.common.block.item.ItemBlockHardCoral;
import me.superckl.betteroceans.common.fluid.block.BlockFluidSaltWater;
import me.superckl.betteroceans.common.fluid.block.BlockFluidSeaweedOil;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(ModData.MOD_ID)
public abstract class ModBlocks {

	public static final BlockSeaweed seaweed = new BlockSeaweed();
	public static final BlockBoatbench boatbench = new BlockBoatbench();
	public static final BlockHardCoral hardCoral = new BlockHardCoral();
	public static final BlockSoftCoral softCoral = new BlockSoftCoral();
	public static BlockFluidSaltWater saltWater;
	public static BlockFluidSeaweedOil seaweedOil;

	public static void init(){
		GameRegistry.registerBlock(ModBlocks.seaweed, Names.SEAWEED);
		GameRegistry.registerBlock(ModBlocks.boatbench, ItemBlockBoatbench.class, Names.BASIC_BOATBENCH);
		GameRegistry.registerBlock(ModBlocks.hardCoral, ItemBlockHardCoral.class, Names.HARD_CORAL);
		GameRegistry.registerBlock(ModBlocks.softCoral, Names.SOFT_CORAL);
	}

	public static class Names{

		public static final String SEAWEED =  "seaweed";
		public static final String BASIC_BOATBENCH =  "basicBoatbench";
		public static final String SALT_WATER = "saltWater";
		public static final String SEAWEED_OIL = "seaweedOil";
		public static final String HARD_CORAL = "hardCoral";
		public static final String SOFT_CORAL = "softCoral";

	}

}
