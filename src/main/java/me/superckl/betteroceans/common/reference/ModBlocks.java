package me.superckl.betteroceans.common.reference;

import me.superckl.betteroceans.common.block.BlockBoatWorkbench;
import me.superckl.betteroceans.common.block.BlockSeaweed;
import me.superckl.betteroceans.common.fluid.block.BlockFluidSaltWater;
import me.superckl.betteroceans.common.fluid.block.BlockFluidSeaweedOil;
import me.superckl.betteroceans.common.item.ItemBlockBoatWorkbench;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(ModData.MOD_ID)
public abstract class ModBlocks {

	public static final BlockSeaweed seaweed = new BlockSeaweed();
	public static final BlockBoatWorkbench basicBoatWorkbench = new BlockBoatWorkbench();
	public static BlockFluidSaltWater saltWater;
	public static BlockFluidSeaweedOil seaweedOil;

	public static void init(){
		GameRegistry.registerBlock(ModBlocks.seaweed, Names.SEAWEED);
		GameRegistry.registerBlock(ModBlocks.basicBoatWorkbench, ItemBlockBoatWorkbench.class, Names.BASIC_BOAT_WORKBENCH);
		ModBlocks.addRecipes();
	}

	private static void addRecipes(){
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.basicBoatWorkbench, "xxx", "yxy", "yyy", 'y', "plankWood", 'x', "cobblestone"));
	}

	public static class Names{

		public static final String SEAWEED =  "seaweed";
		public static final String BASIC_BOAT_WORKBENCH =  "basicBoatWorkbench";
		public static final String SALT_WATER = "saltWater";
		public static final String SEAWEED_OIL = "seaweedOil";

	}

}
