package me.superckl.betteroceans.common.reference;

import java.util.Arrays;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeRegistry {

	public static void registerBaseRecipes(){
		final Object[] items = new Object[9];
		Arrays.fill(items, 0, 8, ModItems.itemSeaweed);
		items[8] = Items.bucket;
		final ItemStack seaweedOil = new ItemStack(ModItems.boBucket);
		ModItems.boBucket.fill(seaweedOil, new FluidStack(ModFluids.saltWater, FluidContainerRegistry.BUCKET_VOLUME), true);
		GameRegistry.addRecipe(new ShapelessOreRecipe(seaweedOil, items));
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.depthSounder), "x", "y", "z",
				'x', Blocks.glass,
				'y', Items.redstone,
				'z', Items.iron_ingot);
		for(int i = 0; i < 5; i++){
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.lumPowder, 2, i), "xxx", "xyx", "xxx", 'x', "dustGlowstone", 'y', new ItemStack(ModBlocks.hardCoral, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.lantern, 1, i), "xxx", "zyz", "xxx", 'x', "stone", 'y', new ItemStack(ModItems.lumPowder, 1, i), 'z', "blockGlass"));
		}

		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.lifeJacket, "x x", "xyx", "xyx", 'x', Blocks.wool, 'y', Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.boatbench, "xxx", "yxy", "yyy", 'y', "plankWood", 'x', "cobblestone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.boatbench, 1, 1), "xxx", "yxy", "yyy", 'y', "stone", 'x', "ingotIron"));
	}

}
