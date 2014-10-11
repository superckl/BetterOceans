package me.superckl.betteroceans.integration.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import me.superckl.betteroceans.common.block.BlockBoatbench;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.reference.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTankInfo;

public class BOWailaDataProvider implements IWailaDataProvider{

	@Override
	public ItemStack getWailaStack(final IWailaDataAccessor accessor,
			final IWailaConfigHandler config) {
		/*if(accessor.getBlock() != null && accessor.getBlock() == ModBlocks.boatbench){
			final ItemStack stack = new ItemStack(ModBlocks.boatbench, 1, accessor.getMetadata());
			final String name = I18n.format(ModBlocks.boatbench.getUnlocalizedName(accessor.getMetadata()).concat(".name"));
			stack.setStackDisplayName(name);
			return stack;
		}*/
		return null;
	}

	@Override
	public List<String> getWailaHead(final ItemStack itemStack,
			final List<String> currenttip, final IWailaDataAccessor accessor,
			final IWailaConfigHandler config) {
		/*if(accessor.getBlock() != null && accessor.getBlock() == ModBlocks.boatbench){
			String name = I18n.format(ModBlocks.boatbench.getUnlocalizedName(accessor.getMetadata()).concat(".name"));
			//LogHelper.info(ModBlocks.boatbench.getUnlocalizedName(accessor.getMetadata())+" - "+name);
			//LogHelper.info(currenttip);
			if(currenttip.size() == 0)
				currenttip.add(name);
			else
				currenttip.set(0, name);
		}*/
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(final ItemStack itemStack,
			final List<String> currenttip, final IWailaDataAccessor accessor,
			final IWailaConfigHandler config) {
		if(accessor.getBlock() != null && accessor.getBlock() == ModBlocks.boatbench)
			if(config.getConfig("showbenchinfo", true)){
				final TileEntity te = accessor.getTileEntity();
				if(te != null && te instanceof TileEntityBoatbench){
					final TileEntityBoatbench tebb = (TileEntityBoatbench) te;
					if(tebb.getActiveSelection() != null)
						currenttip.add(currenttip.size(), "Active Part: "+tebb.getActiveSelection().getNiceName());
					final FluidTankInfo[] infos = tebb.getTankInfo(null);
					if(infos.length > 0 && infos[0] != null && infos[0].fluid != null)
						currenttip.add(currenttip.size(), "Contains "+infos[0].fluid.amount+"mb of "+infos[0].fluid.getFluid().getLocalizedName(infos[0].fluid));
					if(tebb.getPartBurnTime() > 0)
						currenttip.add(currenttip.size(), "Time Remaining: "+(tebb.getPartBurnTime()-tebb.getCookTime())/20+" seconds");

				}
			}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(final ItemStack itemStack,
			final List<String> currenttip, final IWailaDataAccessor accessor,
			final IWailaConfigHandler config) {
		return currenttip;
	}

	public static void callbackRegister(final IWailaRegistrar registrar){
		registrar.addConfig("Better Oceans", "showbenchinfo", "Show Boatbench Info");
		final BOWailaDataProvider provider = new BOWailaDataProvider();
		registrar.registerStackProvider(provider, BlockBoatbench.class);
		registrar.registerBodyProvider(provider, BlockBoatbench.class);
	}

}
