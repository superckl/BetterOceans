package me.superckl.betteroceans.common.reference;

import java.util.Arrays;

import me.superckl.betteroceans.common.item.ItemCookedSeaweed;
import me.superckl.betteroceans.common.item.ItemDepthSounder;
import me.superckl.betteroceans.common.item.ItemSeaweed;
import me.superckl.betteroceans.common.item.ItemSeaweedOil;
import me.superckl.betteroceans.common.item.ItemWoodenBoat;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(ModData.MOD_ID)
public class ModItems {

	public static final ItemSeaweed seaweed = new ItemSeaweed();
	public static final ItemCookedSeaweed cookedSeaweed = new ItemCookedSeaweed();
	public static final ItemSeaweedOil seaweedOil = new ItemSeaweedOil();
	public static final ItemDepthSounder depthSounder = new ItemDepthSounder();
	public static final ItemWoodenBoat woodenBoat = new ItemWoodenBoat();

	public static void init(){
		GameRegistry.registerItem(ModItems.seaweed, Names.SEAWEED);
		GameRegistry.registerItem(ModItems.cookedSeaweed, Names.COOKED_SEAWEED);
		GameRegistry.registerItem(ModItems.seaweedOil, Names.SEAWEED_OIL);
		GameRegistry.registerItem(ModItems.depthSounder, Names.DEPTH_SOUNDER);
		GameRegistry.registerItem(ModItems.woodenBoat, Names.WOODEN_BOAT);

		ModItems.addRecipes();
	}

	private static void addRecipes(){
		//Add new recipes
		final Object[] items = new Object[9];
		Arrays.fill(items, 0, 8, ModItems.seaweed);
		items[8] = Items.bucket;
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.seaweedOil), items);
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.depthSounder), "x", "y", "z",
				'x', Blocks.glass,
				'y', Items.redstone,
				'z', Items.iron_ingot);

		//Remove and replace existing recipes

	}

	public static void overrideItems(){
		RecipeHelper.removeRecipes(new ItemStack(Items.boat));
		Items.boat.setCreativeTab(null);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.woodenBoat), "x x", "xxx",
				'x', "plankWood"));
	}

	public static class Names{

		public static final String SEAWEED = "itemSeaweed";
		public static final String COOKED_SEAWEED = "itemCookedSeaweed";
		public static final String SEAWEED_OIL = "itemSeaweedOil";
		public static final String DEPTH_SOUNDER = "itemDepthSounder";
		public static final String WOODEN_BOAT = "itemWoodenBoat";

	}

}
