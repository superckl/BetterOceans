package me.superckl.betteroceans.utility;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class RecipeHelper {

	public int removeRecipes(final ItemStack toRemove){
		int count = 0;
		final List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++)
		{
			final IRecipe tmpRecipe = recipes.get(i);
			final ItemStack recipeResult = tmpRecipe.getRecipeOutput();
			if (ItemStack.areItemStacksEqual(toRemove, recipeResult)){
				recipes.remove(i--);
				count++;
			}
		}
		return count;
	}

}
