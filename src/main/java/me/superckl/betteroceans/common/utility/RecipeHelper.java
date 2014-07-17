package me.superckl.betteroceans.common.utility;

import java.util.List;
import java.util.ListIterator;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;

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
				} else if(NumberUtil.contains(ids0, ids))
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
				} else if(stack.stackSize > require.stackSize){
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
