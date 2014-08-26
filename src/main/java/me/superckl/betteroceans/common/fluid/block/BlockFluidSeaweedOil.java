package me.superckl.betteroceans.common.fluid.block;

import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidSeaweedOil extends BlockBOFluidClassic{

	public BlockFluidSeaweedOil(final Fluid fluid, final Material material) {
		super(fluid, material);
		this.setBlockName("seaweedoil");
	}

	private final IIcon[] icons = new IIcon[2];

	@Override
	public void registerBlockIcons(final IIconRegister register) {
		this.icons[0] = register.registerIcon(ModData.MOD_ID+":seaweedoil_still");
		this.icons[1] = register.registerIcon(ModData.MOD_ID+":seaweedoil_flow");
		ModFluids.seaweedOil.setIcons(this.icons[0], this.icons[1]);
	}
	
	@Override
	public IIcon getIcon(final int side, final int meta)
	{
		return side != 0 && side != 1 ? this.icons[1]:this.icons[0];
	}

}
