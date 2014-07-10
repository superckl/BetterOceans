package me.superckl.betteroceans.reference;

import me.superckl.betteroceans.item.ItemCookedSeaweed;
import me.superckl.betteroceans.item.ItemSeaweed;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

	public static final ItemSeaweed seaweed = new ItemSeaweed();
	public static final ItemCookedSeaweed cookedSeaweed = new ItemCookedSeaweed();

	public static void init(){
		GameRegistry.registerItem(ModItems.seaweed, Names.SEAWEED);
		GameRegistry.registerItem(ModItems.cookedSeaweed, Names.COOKED_SEAWEED);
	}

	public static class Names{

		public static final String SEAWEED = "itemSeaweed";
		public static final String COOKED_SEAWEED = "itemCookedSeaweed";
	}

}
