package me.superckl.betteroceans.common.fluid.block;

import me.superckl.betteroceans.common.reference.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidSeaweedOil extends BlockBOFluidClassic{

	public BlockFluidSeaweedOil(final Fluid fluid, final Material material) {
		super(fluid, material);
		this.setBlockName("seaweedoil");
	}

	@Override
	public void registerBlockIcons(final IIconRegister register) {
		ModFluids.seaweedOil.setIcons(Blocks.lava.getIcon(0, 0));
	}

}
