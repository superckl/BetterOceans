package me.superckl.betteroceans.common.parts;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import lombok.Getter;
import net.minecraft.item.ItemStack;

public class BoatbenchRecipeHandler implements IBoatPartRecipeHandler{

	public static BoatbenchRecipeHandler INSTANCE = new BoatbenchRecipeHandler();

	private BoatbenchRecipeHandler() {}

	@Getter
	private final TreeMap<Integer, IBoatPartRecipeHandler> idOverrides = new TreeMap<Integer, IBoatPartRecipeHandler>();

	private Collection<IBoatPartRecipeHandler> reverse;

	@Override
	public List<ItemStack> getRequiredItemsFor(final BoatPart part){
		if(this.reverse == null)
			this.reverse = this.idOverrides.descendingMap().values();
		for(final IBoatPartRecipeHandler handler:this.reverse)
			if(handler.shouldHandle(part))
				return handler.getRequiredItemsFor(part);
		return part.getCraftingIngredients();
	}

	public IBoatPartRecipeHandler registerRecipeOverride(final int priority, final IBoatPartRecipeHandler handler){
		final IBoatPartRecipeHandler old = this.idOverrides.put(priority, handler);
		this.reverse = this.idOverrides.descendingMap().values();
		return old;
	}

	@Override
	public boolean shouldHandle(final BoatPart part) {
		return false;
	}

}
