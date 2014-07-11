package me.superckl.betteroceans.reference;

import java.util.Arrays;

import me.superckl.betteroceans.item.ItemCookedSeaweed;
import me.superckl.betteroceans.item.ItemDepthSounder;
import me.superckl.betteroceans.item.ItemSeaweed;
import me.superckl.betteroceans.item.ItemSeaweedOil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

	public static final ItemSeaweed seaweed = new ItemSeaweed();
	public static final ItemCookedSeaweed cookedSeaweed = new ItemCookedSeaweed();
	public static final ItemSeaweedOil seaweedOil = new ItemSeaweedOil();
	public static final ItemDepthSounder depthSounder = new ItemDepthSounder();

	public static void init(){
		GameRegistry.registerItem(ModItems.seaweed, Names.SEAWEED);
		GameRegistry.registerItem(ModItems.cookedSeaweed, Names.COOKED_SEAWEED);
		GameRegistry.registerItem(ModItems.seaweedOil, Names.SEAWEED_OIL);
		GameRegistry.registerItem(ModItems.depthSounder, Names.DEPTH_SOUNDER);

		ModItems.addRecipes();
		ModItems.addMisc();
	}

	private static void addRecipes(){
		//Add new recipes
		final Object[] items = new Object[9];
		Arrays.fill(items, 0, 8, ModItems.seaweed);
		items[8] = Items.bucket;
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.seaweedOil, 1), items);
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.depthSounder, 1), "x", "y", "z",
				'x', Blocks.glass,
				'y', Items.redstone,
				'z', Items.iron_ingot);

		//Remove and replace existing recipes

	}

	private static void addMisc(){

	}

	public static class Names{

		public static final String SEAWEED = "itemSeaweed";
		public static final String COOKED_SEAWEED = "itemCookedSeaweed";
		public static final String SEAWEED_OIL = "itemSeaweedOil";
		public static final String DEPTH_SOUNDER = "itemDepthSounder";

	}

}
