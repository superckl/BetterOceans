package me.superckl.betteroceans.common.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

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

	public static int replaceItem(final ItemStack toReplace, final ItemStack toPut, final boolean useDamage, final boolean override){
		int count = 0;
		final List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		final List<IRecipe> toAdd = new ArrayList<IRecipe>();
		for (final IRecipe recipe:recipes)
			if(recipe instanceof ShapedRecipes){
				ShapedRecipes sRecipe = (ShapedRecipes) recipe;
				if(!override)
					sRecipe = RecipeHelper.copy(sRecipe);
				for(int i = 0; i < sRecipe.recipeItems.length; i++)
					if(sRecipe.recipeItems[i] != null && sRecipe.recipeItems[i].isItemEqual(toReplace) && (!useDamage || sRecipe.recipeItems[i].getItemDamage() == toReplace.getItemDamage())){
						final ItemStack copy = toPut.copy();
						copy.stackSize = sRecipe.recipeItems[i].stackSize;
						sRecipe.recipeItems[i] = copy;
						count++;
					}
				if(!override)
					toAdd.add(sRecipe);
			}else if(recipe instanceof ShapelessRecipes){
				ShapelessRecipes sRecipe = (ShapelessRecipes) recipe;
				if(!override)
					sRecipe = RecipeHelper.copy(sRecipe);
				for(int i = 0; i < sRecipe.recipeItems.size(); i++){
					final ItemStack stack = (ItemStack) sRecipe.recipeItems.get(i);
					if(stack != null && stack.isItemEqual(toReplace) && (!useDamage || stack.getItemDamage() == toReplace.getItemDamage())){
						final ItemStack copy = toPut.copy();
						copy.stackSize = stack.stackSize;
						sRecipe.recipeItems.set(i, copy);
						count++;
					}
				}
				if(!override)
					toAdd.add(sRecipe);
			}
		//Now that we're out, add the recipes
		for(final IRecipe recipe:toAdd)
			GameRegistry.addRecipe(recipe);
		return count;
	}

	public static ShapedRecipes copy(final ShapedRecipes sRecipe){
		return new ShapedRecipes(sRecipe.recipeWidth, sRecipe.recipeHeight, ItemStackHelper.deepClone(sRecipe.recipeItems), sRecipe.getRecipeOutput());
	}

	public static ShapelessRecipes copy(final ShapelessRecipes sRecipe){
		return new ShapelessRecipes(sRecipe.getRecipeOutput(), ItemStackHelper.deepClone(sRecipe.recipeItems));
	}

	public static boolean areItemsPresent(final List<ItemStack> required, final ItemStack[] present, final boolean safe){
		final List<ItemStack> copy = safe ? ItemStackHelper.deepClone(required):required;
		final ListIterator<ItemStack> lit = copy.listIterator();
		while(lit.hasNext()){
			final ItemStack require = lit.next();
			if(require == null)
				continue;
			final int[] ids0 = OreDictionary.getOreIDs(require);
			for(final ItemStack stack:present){
				if(stack == null)
					continue;
				final int[] ids = OreDictionary.getOreIDs(stack);
				if(ids == null || ids.length == 0 || ids0 == null || ids0.length == 0){
					if(require.isItemEqual(stack))
						if(stack.stackSize >= require.stackSize){
							lit.remove();
							break;
						}else
							require.stackSize-=stack.stackSize;
				} else if(NumberHelper.contains(ids0, ids))
					if(stack.stackSize >= require.stackSize){
						lit.remove();
						break;
					}else
						require.stackSize-=stack.stackSize;
			}
		}
		return copy.isEmpty();
	}

	public static boolean removeItems(final List<ItemStack> required, final ItemStack[] inventory, final boolean safe){
		final List<ItemStack> copy = safe ? ItemStackHelper.deepClone(required):required;
		final ListIterator<ItemStack> lit = copy.listIterator();
		while(lit.hasNext()){
			final ItemStack require = lit.next();
			if(require == null)
				continue;
			final int[] ids0 = OreDictionary.getOreIDs(require);
			for(int i = 0; i < inventory.length; i++){
				final ItemStack stack = inventory[i];
				if(stack == null)
					continue;
				final int[] ids = OreDictionary.getOreIDs(stack);
				if(ids == null || ids.length == 0 || ids0 == null || ids0.length == 0){
					if(require.isItemEqual(stack))
						if(stack.stackSize > require.stackSize){
							lit.remove();
							stack.stackSize -= require.stackSize;
							break;
						}else if(stack.stackSize == require.stackSize){
							lit.remove();
							inventory[i] = null;
							break;
						}else{
							inventory[i] = null;
							require.stackSize-=stack.stackSize;
						}
				}else if(NumberHelper.contains(ids0, ids))
					if(stack.stackSize > require.stackSize){
						lit.remove();
						stack.stackSize -= require.stackSize;
						break;
					}else if(stack.stackSize == require.stackSize){
						lit.remove();
						inventory[i] = null;
						break;
					}else{
						inventory[i] = null;
						require.stackSize-=stack.stackSize;
					}
			}
		}
		return copy.isEmpty();
	}

}
