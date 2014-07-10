package me.superckl.betteroceans.reference;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import cpw.mods.fml.common.registry.GameRegistry;
import me.superckl.betteroceans.Config;
import me.superckl.betteroceans.block.BlockSeaweed;
import me.superckl.betteroceans.item.ItemCookedSeaweed;
import me.superckl.betteroceans.item.ItemSeaweed;

public class ModItems {
	
	public static final ItemSeaweed seaweed = new ItemSeaweed();
	public static final ItemCookedSeaweed cookedSeaweed = new ItemCookedSeaweed();
	
	public static void init(){
		GameRegistry.registerItem(seaweed, Names.SEAWEED);
		GameRegistry.registerItem(cookedSeaweed, Names.COOKED_SEAWEED);
	}
	
	private void registerRecipes(){
		GameRegistry.addSmelting(seaweed, new ItemStack(cookedSeaweed, 1), 0);
	}
	
	public static class Names{
		
		public static final String SEAWEED = "itemSeaweed";
		public static final String COOKED_SEAWEED = "itemCookedSeaweed";
	}
	
}
