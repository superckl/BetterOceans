package me.superckl.betteroceans.integration;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NotEnoughItemsIntegration {

	public static void preInit(){
		API.registerRecipeHandler(new BoatPartRecipeHandler());
		LogHelper.debug("Added part recipes to NEI...");
	}

	@RequiredArgsConstructor
	public static class BoatPartRecipeHandler extends TemplateRecipeHandler{

		private final Minecraft mc = Minecraft.getMinecraft();

		@Override
		public String getRecipeName() {
			return "Boatbench";
		}

		@Override
		public String getGuiTexture() {
			return ModData.MOD_ID+":textures/gui/neibasicbench.png";
		}

		@Override
		public void loadCraftingRecipes(final ItemStack result) {
			if(result.getItem() != ModItems.boatPart)
				return;
			final BoatPart part = BoatPart.deserialize(result.getItemDamage());
			if(part == null)
				return;
			this.arecipes.add(new CachedRecipe(){

				@Override
				public PositionedStack getResult() {
					return new PositionedStack(part.getCraftingResult(), 10, 10);
				}

				@Override
				public List<PositionedStack> getIngredients() {
					return BoatPartRecipeHandler.this.arrangeItemStacks(part.getCraftingIngredients());
				}

			});
		}

		private List<PositionedStack> arrangeItemStacks(final List<ItemStack> stacks){
			if(stacks.size() > 9)
				throw new IllegalArgumentException("Can't arrange more than 9 stacks!");
			final List<PositionedStack> arranged = new ArrayList<PositionedStack>();
			int x = 10;
			int y = 10;
			int i = 0;
			for(final ItemStack stack:stacks){
				if(i >= 3){
					y += 18;
					x = 10;
					i = 0;
				}
				arranged.add(new PositionedStack(stack, x, y));
				x += 18;
				i++;
			}
			return arranged;
		}

	}
}
