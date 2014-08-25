package me.superckl.betteroceans.integration;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import me.superckl.betteroceans.common.block.BlockBoatbench;
import me.superckl.betteroceans.common.entity.tile.TileEntityBoatbench;
import me.superckl.betteroceans.common.reference.ModBlocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTankInfo;

public class BOWailaProvider implements IWailaDataProvider{

	@Override
	public ItemStack getWailaStack(final IWailaDataAccessor accessor,
			final IWailaConfigHandler config) {
		if(accessor.getBlock() != null && accessor.getBlock() == ModBlocks.boatbench){
			final ItemStack stack = new ItemStack(ModBlocks.boatbench, 1, accessor.getMetadata());
			final String name = I18n.format(ModBlocks.boatbench.getUnlocalizedName(accessor.getMetadata()).concat(".name"));
			stack.setStackDisplayName(name);
			return stack;
		}
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
						currenttip.add(0, "Active Part: "+tebb.getActiveSelection().getBoatParts().get(0).getNiceName());
					final FluidTankInfo[] infos = tebb.getTankInfo(null);
					if(infos.length > 0 && infos[0] != null && infos[0].fluid != null)
						currenttip.add(1, "Contains "+infos[0].fluid.amount+"mb of "+infos[0].fluid.getFluid().getLocalizedName(infos[0].fluid));
					if(tebb.getPartBurnTime() > 0)
						currenttip.add(2, "Time Left: "+(tebb.getPartBurnTime()-tebb.getCookTime())/20+" seconds");

				}
			}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(final ItemStack itemStack,
			final List<String> currenttip, final IWailaDataAccessor accessor,
			final IWailaConfigHandler config) {
		// TODO Auto-generated method stub
		return currenttip;
	}

	public static void callbackRegister(final IWailaRegistrar registrar){
		registrar.addConfig("Better Oceans", "showbenchinfo", "Show Boatbench Info");
		final BOWailaProvider provider = new BOWailaProvider();
		//registrar.registerHeadProvider(provider, BlockBoatbench.class);
		registrar.registerStackProvider(provider, BlockBoatbench.class);
		registrar.registerBodyProvider(provider, BlockBoatbench.class);
	}

}
