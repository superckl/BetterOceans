package me.superckl.betteroceans.utility;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

public class RecipeHelper {

	public static int removeRecipes(final ItemStack toRemove){
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

	public static int replaceItem(final Item toReplace, final Item toPut){
		int count = 0;
		final List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (final IRecipe recipe:recipes)
			if(recipe instanceof ShapedRecipes){
				final ShapedRecipes sRecipe = (ShapedRecipes) recipe;
				for(int i = 0; i < sRecipe.recipeItems.length; i++)
					if(sRecipe.recipeItems[i] != null && sRecipe.recipeItems[i].getItem() == toReplace){
						sRecipe.recipeItems[i] = new ItemStack(toPut, sRecipe.recipeItems[i].stackSize);
						count++;
					}
			}else if(recipe instanceof ShapelessRecipes){
				final ShapelessRecipes sRecipe = (ShapelessRecipes) recipe;
				for(int i = 0; i < sRecipe.recipeItems.size(); i++){
					final ItemStack stack = (ItemStack) sRecipe.recipeItems.get(i);
					if(stack != null && stack.getItem() == toReplace){
						sRecipe.recipeItems.set(i, new ItemStack(toPut, stack.stackSize));
						count++;
					}
				}
			}
		return count;
	}

}
