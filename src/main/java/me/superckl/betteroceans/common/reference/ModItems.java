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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(ModData.MOD_ID)
public class ModItems {

	public static final ItemSeaweed itemSeaweed = new ItemSeaweed();
	public static final ItemCookedSeaweed cookedSeaweed = new ItemCookedSeaweed();
	public static final ItemSeaweedOil seaweedOil = new ItemSeaweedOil();
	public static final ItemDepthSounder depthSounder = new ItemDepthSounder();
	public static final ItemWoodenBoat woodenBoat = new ItemWoodenBoat();

	public static void init(){
		//TODO fix
		GameRegistry.registerItem(ModItems.itemSeaweed, Names.SEAWEED);
		GameRegistry.registerItem(ModItems.cookedSeaweed, Names.COOKED_SEAWEED);
		GameRegistry.registerItem(ModItems.seaweedOil, Names.SEAWEED_OIL);
		GameRegistry.registerItem(ModItems.depthSounder, Names.DEPTH_SOUNDER);
		GameRegistry.registerItem(ModItems.woodenBoat, Names.WOODEN_BOAT);

		ModItems.addRecipes();
	}

	private static void addRecipes(){
		//Add new recipes
		final Object[] items = new Object[9];
		Arrays.fill(items, 0, 8, ModItems.itemSeaweed);
		items[8] = Items.bucket;
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.seaweedOil), items);
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.depthSounder), "x", "y", "z",
				'x', Blocks.glass,
				'y', Items.redstone,
				'z', Items.iron_ingot);
	}

	public static void overrideItems(){
		RecipeHelper.removeRecipes(new ItemStack(Items.boat));
		Items.boat.setCreativeTab(null);
	}

	public static class Names{

		public static final String SEAWEED = "itemSeaweed";
		public static final String COOKED_SEAWEED =  "cookedSeaweed";
		public static final String SEAWEED_OIL =  "seaweedOil";
		public static final String DEPTH_SOUNDER =  "depthSounder";
		public static final String WOODEN_BOAT = "woodenBoat";

	}

}
