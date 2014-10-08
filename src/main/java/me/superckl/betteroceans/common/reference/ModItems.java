package me.superckl.betteroceans.common.reference;

import me.superckl.betteroceans.common.item.ItemBOBucket;
import me.superckl.betteroceans.common.item.ItemBoatPart;
import me.superckl.betteroceans.common.item.ItemCookedSeaweed;
import me.superckl.betteroceans.common.item.ItemDepthSounder;
import me.superckl.betteroceans.common.item.ItemLifeJacket;
import me.superckl.betteroceans.common.item.ItemLuminescentPowder;
import me.superckl.betteroceans.common.item.ItemSeaweed;
import me.superckl.betteroceans.common.utility.RecipeHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(ModData.MOD_ID)
public abstract class ModItems {

	public static final ItemSeaweed itemSeaweed = new ItemSeaweed();
	public static final ItemCookedSeaweed cookedSeaweed = new ItemCookedSeaweed();
	public static final ItemDepthSounder depthSounder = new ItemDepthSounder();
	public static final ItemBoatPart boatPart = new ItemBoatPart();
	public static final ItemBOBucket boBucket = new ItemBOBucket();
	public static final ItemLuminescentPowder lumPowder = new ItemLuminescentPowder();
	public static final ItemLifeJacket lifeJacket = new ItemLifeJacket();

	public static void init(){
		GameRegistry.registerItem(ModItems.itemSeaweed, Names.SEAWEED);
		GameRegistry.registerItem(ModItems.cookedSeaweed, Names.COOKED_SEAWEED);
		GameRegistry.registerItem(ModItems.depthSounder, Names.DEPTH_SOUNDER);
		GameRegistry.registerItem(ModItems.boatPart, Names.BOAT_PART);
		GameRegistry.registerItem(ModItems.boBucket, Names.BO_BUCKET);
		GameRegistry.registerItem(ModItems.lumPowder, Names.LUM_POWDER);
		GameRegistry.registerItem(ModItems.lifeJacket, Names.LIFE_JACKET);
		OreDictionary.registerOre("listAllveggie", ModItems.itemSeaweed);
		OreDictionary.registerOre("listAllgreenveggie", ModItems.itemSeaweed);
	}

	public static void overrideItems(){
		RecipeHelper.removeRecipes(new ItemStack(Items.boat));
		RecipeHelper.replaceItem(new ItemStack(Items.boat), BoatParts.woodenBottom.getCraftingResult(), false, true);
		Items.boat.setCreativeTab(null);
	}

	public static class Names{

		public static final String SEAWEED = "itemSeaweed";
		public static final String COOKED_SEAWEED =  "cookedSeaweed";
		public static final String DEPTH_SOUNDER =  "depthSounder";
		public static final String BOAT_PART = "boatPart";
		public static final String BO_BUCKET = "boBucket";
		public static final String LUM_POWDER = "lumPowder";
		public static final String LIFE_JACKET = "lifeJacket";

	}

}
