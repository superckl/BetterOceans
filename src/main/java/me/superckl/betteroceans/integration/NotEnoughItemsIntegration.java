package me.superckl.betteroceans.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.RenderUtil;
import me.superckl.betteroceans.common.utility.StringHelper;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class NotEnoughItemsIntegration {

	public static void preInit(){
		API.registerRecipeHandler(new BoatRecipeHandler());
		LogHelper.info("Hooked NEI");
	}
	
	
	
	
	public static class BoatRecipeHandler extends TemplateRecipeHandler{

		private final ResourceLocation texture = new ResourceLocation(ModData.MOD_ID+":textures/gui/basicbench.png");
		private final Minecraft mc = Minecraft.getMinecraft();
		
		@Override
		public String getRecipeName() {
			return "Wooden Boat";
		}

		@Override
		public String getGuiTexture() {
			return ModData.MOD_ID+":textures/gui/basicbench.png";
		}
		
		@Override
		public void loadCraftingRecipes(ItemStack result) {
			if(result.getItem() == ModItems.woodenBoat)
			this.arecipes.add(new CachedRecipe(){

				@Override
				public PositionedStack getResult() {
					return new PositionedStack(new ItemStack(ModItems.woodenBoat), 220, 40);
				}
				
				@Override
				public List<PositionedStack> getIngredients() {
					return Arrays.asList(new PositionedStack(new ItemStack(Blocks.planks, 8), 15, 23));
		        }
				
			});
		}
		
	}
}
