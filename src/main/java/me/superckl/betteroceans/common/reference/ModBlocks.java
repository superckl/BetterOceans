package me.superckl.betteroceans.common.reference;

import me.superckl.betteroceans.common.block.BlockBoatbench;
import me.superckl.betteroceans.common.block.BlockHardCoral;
import me.superckl.betteroceans.common.block.BlockSeaweed;
import me.superckl.betteroceans.common.fluid.block.BlockFluidSaltWater;
import me.superckl.betteroceans.common.fluid.block.BlockFluidSeaweedOil;
import me.superckl.betteroceans.common.item.ItemBlockBoatbench;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(ModData.MOD_ID)
public abstract class ModBlocks {

	public static final BlockSeaweed seaweed = new BlockSeaweed();
	public static final BlockBoatbench basicBoatbench = new BlockBoatbench();
	public static final BlockHardCoral hardCoral = new BlockHardCoral();
	public static BlockFluidSaltWater saltWater;
	public static BlockFluidSeaweedOil seaweedOil;

	public static void init(){
		GameRegistry.registerBlock(ModBlocks.seaweed, Names.SEAWEED);
		GameRegistry.registerBlock(ModBlocks.basicBoatbench, ItemBlockBoatbench.class, Names.BASIC_BOATBENCH);
		GameRegistry.registerBlock(ModBlocks.hardCoral, Names.HARD_CORAL);

		ModBlocks.addRecipes();
	}

	private static void addRecipes(){
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.basicBoatbench, "xxx", "yxy", "yyy", 'y', "plankWood", 'x', "cobblestone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.basicBoatbench, 1, 1), "xxx", "yxy", "yyy", 'y', "stone", 'x', "ingotIron"));
	}

	public static class Names{

		public static final String SEAWEED =  "seaweed";
		public static final String BASIC_BOATBENCH =  "basicBoatbench";
		public static final String SALT_WATER = "saltWater";
		public static final String SEAWEED_OIL = "seaweedOil";
		public static final String HARD_CORAL = "hardCoral";

	}

}
