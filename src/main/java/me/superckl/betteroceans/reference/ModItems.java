package me.superckl.betteroceans.reference;

import java.util.Arrays;

import me.superckl.betteroceans.item.ItemCookedSeaweed;
import me.superckl.betteroceans.item.ItemSeaweed;
import me.superckl.betteroceans.item.ItemSeaweedOil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

	public static final ItemSeaweed seaweed = new ItemSeaweed();
	public static final ItemCookedSeaweed cookedSeaweed = new ItemCookedSeaweed();
	public static final ItemSeaweedOil seaweedOil = new ItemSeaweedOil();

	public static void init(){
		GameRegistry.registerItem(ModItems.seaweed, Names.SEAWEED);
		GameRegistry.registerItem(ModItems.cookedSeaweed, Names.COOKED_SEAWEED);
		GameRegistry.registerItem(ModItems.seaweedOil, Names.SEAWEED_OIL);

		ModItems.addRecipes();
	}

	private static void addRecipes(){
		final ItemStack[] items = new ItemStack[9];
		Arrays.fill(items, 0, 8, new ItemStack(ModItems.seaweed, 1));
		items[8] = new ItemStack(Items.bucket, 1);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.seaweedOil, 1), (Object[]) items);
	}

	public static class Names{

		public static final String SEAWEED = "itemSeaweed";
		public static final String COOKED_SEAWEED = "itemCookedSeaweed";
		public static final String SEAWEED_OIL = "itemSeaweedOil";
	}

}
