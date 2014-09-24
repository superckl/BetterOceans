package me.superckl.betteroceans.common.parts;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.Getter;
import me.superckl.betteroceans.common.handler.IFluidFuelHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class BoatbenchRecipeHandler implements IBoatPartRecipeHandler{

	public static BoatbenchRecipeHandler INSTANCE = new BoatbenchRecipeHandler();

	@Getter
	private final Map<Fluid, IFluidFuelHandler> fuelHandlers = new HashMap<Fluid, IFluidFuelHandler>();

	private BoatbenchRecipeHandler() {}

	@Getter
	private final TreeMap<Integer, IBoatPartRecipeHandler> idOverrides = new TreeMap<Integer, IBoatPartRecipeHandler>();

	private Collection<IBoatPartRecipeHandler> reverse;

	@Override
	public List<ItemStack> getRequiredItemsFor(final BoatPart part){
		return this.getRequiredItemsFor(part, false);
	}

	public List<ItemStack> getRequiredItemsFor(final BoatPart part, final boolean nei){
		if(this.reverse == null)
			this.reverse = this.idOverrides.descendingMap().values();
		for(final IBoatPartRecipeHandler handler:this.reverse)
			if(handler.shouldHandle(part))
				return handler.getRequiredItemsFor(part);
		return part.getCraftingIngredients(nei);
	}

	public IBoatPartRecipeHandler registerRecipeHandler(final int priority, final IBoatPartRecipeHandler handler){
		final IBoatPartRecipeHandler old = this.idOverrides.put(priority, handler);
		this.reverse = this.idOverrides.descendingMap().values();
		return old;
	}

	@Override
	public boolean shouldHandle(final BoatPart part) {
		return false;
	}

	/**
	 * Registers a new FluidFuelHandler for Boatbenches.
	 * @param fluid The fluid to map this handler to. A handler can be mapped to more than one fluid.
	 * @param handler The handler to map the fluid to. A fluid cannot be mapped to more than one handler.
	 * @return The previous handler for this fluid, or null if there was not one.
	 */
	public IFluidFuelHandler addValidFuel(final Fluid fluid, final IFluidFuelHandler handler){
		return this.fuelHandlers.put(fluid, handler);
	}

	public boolean isFuel(final Fluid fluid){
		return this.fuelHandlers.containsKey(fluid);
	}

	public int getFuelUsageFor(final Fluid fluid, final BoatPart part){
		return this.fuelHandlers.containsKey(fluid) ? this.fuelHandlers.get(fluid).getFuelUsageFor(fluid, part):-1;
	}

}
