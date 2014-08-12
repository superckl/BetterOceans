package me.superckl.betteroceans.common.reference;

import me.superckl.betteroceans.common.item.ItemBOBucket;
import me.superckl.betteroceans.common.item.ItemBoatPart;
import me.superckl.betteroceans.common.item.ItemCookedSeaweed;
import me.superckl.betteroceans.common.item.ItemDepthSounder;
import me.superckl.betteroceans.common.item.ItemSeaweed;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(ModData.MOD_ID)
public abstract class ModItems {

	public static final ItemSeaweed itemSeaweed = new ItemSeaweed();
	public static final ItemCookedSeaweed cookedSeaweed = new ItemCookedSeaweed();
	public static final ItemDepthSounder depthSounder = new ItemDepthSounder();
	public static final ItemBoatPart boatPart = new ItemBoatPart();
	public static final ItemBOBucket boBucket = new ItemBOBucket();

	public static void init(){
		//TODO fix
		GameRegistry.registerItem(ModItems.itemSeaweed, Names.SEAWEED);
		GameRegistry.registerItem(ModItems.cookedSeaweed, Names.COOKED_SEAWEED);
		GameRegistry.registerItem(ModItems.depthSounder, Names.DEPTH_SOUNDER);
		GameRegistry.registerItem(ModItems.boatPart, Names.BOAT_PART);
		GameRegistry.registerItem(ModItems.boBucket, Names.BO_BUCKET);

		ModItems.addRecipes();
	}

	private static void addRecipes(){
		//Add new recipes
		/*		final Object[] items = new Object[9];
		Arrays.fill(items, 0, 8, ModItems.itemSeaweed);
		items[8] = Items.bucket;
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.seaweedOil), items); *///TODO
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.depthSounder), "x", "y", "z",
				'x', Blocks.glass,
				'y', Items.redstone,
				'z', Items.iron_ingot);
	}

	public static void overrideItems(){
		RecipeHelper.removeRecipes(new ItemStack(Items.boat));
		//RecipeHelper.replaceItem(new ItemStack(Items.boat), new ItemStack(ModItems.woodenBoat), false, true); TODO boat part
		Items.boat.setCreativeTab(null);
	}

	public static class Names{

		public static final String SEAWEED = "itemSeaweed";
		public static final String COOKED_SEAWEED =  "cookedSeaweed";
		public static final String DEPTH_SOUNDER =  "depthSounder";
		public static final String BOAT_PART = "boatPart";
		public static final String BO_BUCKET = "boBucket";

	}

}
