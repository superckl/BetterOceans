package me.superckl.betteroceans.common.reference;

import java.util.HashMap;
import java.util.Map;

import me.superckl.betteroceans.common.fluid.FluidSaltWater;
import me.superckl.betteroceans.common.fluid.FluidSeaweedOil;
import me.superckl.betteroceans.common.fluid.block.BlockFluidSaltWater;
import me.superckl.betteroceans.common.fluid.block.BlockFluidSeaweedOil;
import me.superckl.betteroceans.common.handler.IFluidFuelHandler;
import me.superckl.betteroceans.common.parts.BoatPart;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class ModFluids {

	private static Map<Fluid, IFluidFuelHandler> fuelHandlers = new HashMap<Fluid, IFluidFuelHandler>();

	public static final FluidSaltWater saltWater = new FluidSaltWater("saltwater");
	public static final FluidSeaweedOil seaweedOil = new FluidSeaweedOil("seaweedoil");

	public static void init(){
		FluidRegistry.registerFluid(ModFluids.saltWater);
		ModBlocks.saltWater = new BlockFluidSaltWater(ModFluids.saltWater, Material.water);
		GameRegistry.registerBlock(ModBlocks.saltWater, ModBlocks.Names.SALT_WATER);
		ItemStack filledBucket = new ItemStack(ModItems.boBucket);
		ModItems.boBucket.fill(filledBucket, new FluidStack(ModFluids.saltWater, FluidContainerRegistry.BUCKET_VOLUME), true);
		FluidContainerRegistry.registerFluidContainer(ModFluids.saltWater, filledBucket, FluidContainerRegistry.EMPTY_BUCKET);

		FluidRegistry.registerFluid(ModFluids.seaweedOil);
		ModBlocks.seaweedOil = new BlockFluidSeaweedOil(ModFluids.seaweedOil, Material.water);
		GameRegistry.registerBlock(ModBlocks.seaweedOil, ModBlocks.Names.SEAWEED_OIL);
		filledBucket = new ItemStack(ModItems.boBucket);
		ModItems.boBucket.fill(filledBucket, new FluidStack(ModFluids.seaweedOil, FluidContainerRegistry.BUCKET_VOLUME), true);
		FluidContainerRegistry.registerFluidContainer(ModFluids.seaweedOil, filledBucket, FluidContainerRegistry.EMPTY_BUCKET);

		ModFluids.addValidFuel(ModFluids.seaweedOil, new IFluidFuelHandler() {

			@Override
			public int getFuelUsageFor(final Fluid fluid, final BoatPart part) {
				return (int) (part.getCreationTime()*2.5);
			}
		});
	}

	/**
	 * Registers a new FluidFuelHandler for Boatbenches.
	 * @param fluid The fluid to map this handler to. A handler can be mapped to more than one fluid.
	 * @param handler The handler to map the fluid to. A fluid cannot be mapped to more than one handler.
	 * @return The previous handler for this fluid, or null if there was not one.
	 */
	public static IFluidFuelHandler addValidFuel(final Fluid fluid, final IFluidFuelHandler handler){
		return ModFluids.fuelHandlers.put(fluid, handler);
	}

	public static boolean isFuel(final Fluid fluid){
		return ModFluids.fuelHandlers.containsKey(fluid);
	}

	public static int getFuelUsageFor(final Fluid fluid, final BoatPart part){
		return ModFluids.fuelHandlers.containsKey(fluid) ? ModFluids.fuelHandlers.get(fluid).getFuelUsageFor(fluid, part):-1;
	}

}
