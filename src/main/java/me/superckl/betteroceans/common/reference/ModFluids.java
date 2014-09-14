package me.superckl.betteroceans.common.reference;

import me.superckl.betteroceans.common.fluid.FluidSaltWater;
import me.superckl.betteroceans.common.fluid.FluidSeaweedOil;
import me.superckl.betteroceans.common.fluid.block.BlockFluidSaltWater;
import me.superckl.betteroceans.common.fluid.block.BlockFluidSeaweedOil;
import me.superckl.betteroceans.common.handler.IFluidFuelHandler;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.BoatbenchRecipeHandler;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class ModFluids {

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

		BoatbenchRecipeHandler.INSTANCE.addValidFuel(ModFluids.seaweedOil, new IFluidFuelHandler() {

			@Override
			public int getFuelUsageFor(final Fluid fluid, final BoatPart part) {
				return (int) (part.getCreationTime()*2.5);
			}
		});
	}

}
