package me.superckl.betteroceans.integration.nei;

import java.awt.Point;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import lombok.NonNull;
import me.superckl.betteroceans.common.parts.BoatPart;
import me.superckl.betteroceans.common.parts.BoatbenchRecipeHandler;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.LogHelper;
import me.superckl.betteroceans.common.utility.PartSelectionManager;
import me.superckl.betteroceans.common.utility.RenderHelper;
import me.superckl.betteroceans.integration.IIntegrationModule;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NotEnoughItemsIntegration implements IIntegrationModule{


	@Override
	public void preInit() {}

	@Override
	public void init() {}

	@Override
	public void postInit(){
		API.registerRecipeHandler(new BasicBoatPartRecipeHandler());
		API.registerRecipeHandler(new InterBoatPartRecipeHandler());
		LogHelper.debug("Added part recipes to NEI...");
	}

	public static abstract class BoatPartRecipeHandler extends TemplateRecipeHandler{

		private final Minecraft mc = Minecraft.getMinecraft();
		protected final String name;
		protected final PartSelectionManager manager;
		protected final PartSelectionManager[] mustNotHave;

		public BoatPartRecipeHandler(final String name, final PartSelectionManager partManager, final PartSelectionManager[] mustNotHave) {
			super();
			this.name = name;
			this.manager = partManager;
			this.mustNotHave = mustNotHave;
		}

		@Override
		public abstract TemplateRecipeHandler newInstance();

		@Override
		public String getRecipeName() {
			return this.name;
		}

		@Override
		public String getGuiTexture() {
			return ModData.MOD_ID+":textures/gui/neiboatbench.png";
		}

		@Override
		public void drawExtras(final int recipe) {
			final BoatbenchCachedRecipe rec = (BoatbenchCachedRecipe) this.arecipes.get(recipe);
			BoatPart.deserialize(rec.getResult().item.getItemDamage());
			final Entry<Fluid, Integer> fuelUsage = rec.getFuel();
			if(fuelUsage != null && fuelUsage.getValue() > 0)
				RenderHelper.drawFluid(this.mc, 152-5, 74-11, 16, 60, new FluidTankInfo(new FluidStack(fuelUsage.getKey(), fuelUsage.getValue()), FluidContainerRegistry.BUCKET_VOLUME*4));
			RenderHelper.drawTankOverlay(156-5, 14-11);
		}



		@Override
		public List<String> handleTooltip(final GuiRecipe gui,
				final List<String> currenttip, final int recipe) {
			final Point offset = gui.getRecipePosition(recipe);
			final int leftX = offset.x+152;
			final int rightX = leftX+16;
			final int bottomY = offset.y+63;
			final int topY = bottomY-60;
			final Point mouse = GuiDraw.getMousePosition();
			final int mouseX = mouse.x;
			final int mouseY = mouse.y;

			if(mouseX <= rightX+gui.guiLeft && mouseX >= leftX+gui.guiLeft && mouseY <= bottomY+gui.guiTop && mouseY >= topY+gui.guiTop){
				final BoatbenchCachedRecipe rec = (BoatbenchCachedRecipe) this.arecipes.get(recipe);
				final Entry<Fluid, Integer> fuelUsage = rec.getFuel();
				if(fuelUsage != null && fuelUsage.getValue() > 0)
					return Arrays.asList(fuelUsage.getKey().getLocalizedName(new FluidStack(fuelUsage.getKey(), fuelUsage.getValue())), "Amount: "+fuelUsage.getValue()+"mb");
			}
			return super.handleTooltip(gui, currenttip, recipe);
		}

		@Override
		public void loadCraftingRecipes(final ItemStack result) {
			if(result.getItem() != ModItems.boatPart)
				return;
			final BoatPart part = BoatPart.deserialize(result.getItemDamage());
			if(part == null || !this.manager.contains(part))
				return;
			for(final PartSelectionManager manager:this.mustNotHave)
				if(manager.contains(part))
					return;
			this.arecipes.add(new BoatbenchCachedRecipe(part));
		}

		private List<PositionedStack> arrangeItemStacks(final List<ItemStack> stacks){
			if(stacks.size() > 3)
				throw new IllegalArgumentException("Can't arrange more than 3 stacks!");
			final List<PositionedStack> arranged = new ArrayList<PositionedStack>();
			if(stacks.size() > 0){
				final ItemStack stack = stacks.get(0);
				final PositionedStack pStack = new PositionedStack(stack.copy(), 40-5, 27-11);
				for(final ItemStack item:pStack.items)
					item.stackSize = stack.stackSize;
				arranged.add(pStack);
			}
			if(stacks.size() > 1){
				final ItemStack stack = stacks.get(1);
				final PositionedStack pStack = new PositionedStack(stack.copy(), 58-5, 36-11);
				for(final ItemStack item:pStack.items)
					item.stackSize = stack.stackSize;
				arranged.add(pStack);
			}
			if(stacks.size() > 2){
				final ItemStack stack = stacks.get(2);
				final PositionedStack pStack = new PositionedStack(stack.copy(), 40-5, 45-11);
				for(final ItemStack item:pStack.items)
					item.stackSize = stack.stackSize;
				arranged.add(pStack);
			}
			return arranged;
		}

		private class BoatbenchCachedRecipe extends CachedRecipe{

			private final BoatPart part;
			private final List<PositionedStack> ingredients;
			private final List<Fluid> fuels;

			public BoatbenchCachedRecipe(@NonNull final BoatPart part) {
				this.part = part;
				this.ingredients = BoatPartRecipeHandler.this.arrangeItemStacks(BoatbenchRecipeHandler.INSTANCE.getRequiredItemsFor(part, true));
				this.fuels = new ArrayList<Fluid>(BoatbenchRecipeHandler.INSTANCE.getFuelHandlers().keySet());
			}

			@Override
			public PositionedStack getResult() {
				return new PositionedStack(this.part.getCraftingResult(), 116-5, 36-11);
			}

			@Override
			public List<PositionedStack> getIngredients() {
				return this.getCycledIngredients(BoatPartRecipeHandler.this.cycleticks/20, this.ingredients);
			}

			public Entry<Fluid, Integer> getFuel(){
				final int cycle = BoatPartRecipeHandler.this.cycleticks/20;
				final Random rand = new Random(cycle);
				final Fluid fluid = this.fuels.get(rand.nextInt(this.fuels.size()));
				return new AbstractMap.SimpleEntry(fluid, BoatbenchRecipeHandler.INSTANCE.getFuelUsageFor(fluid, this.part));
			}

		}

	}

	public static class BasicBoatPartRecipeHandler extends BoatPartRecipeHandler{

		public BasicBoatPartRecipeHandler() {
			super("Basic Boatbench", PartSelectionManager.BASIC_BENCH, new PartSelectionManager[0]);
		}

		@Override
		public TemplateRecipeHandler newInstance() {
			return new BasicBoatPartRecipeHandler();
		}

	}

	public static class InterBoatPartRecipeHandler extends BoatPartRecipeHandler{

		public InterBoatPartRecipeHandler() {
			super("Intermediate Boatbench", PartSelectionManager.INTER_BENCH, new PartSelectionManager[] {PartSelectionManager.BASIC_BENCH});
		}

		@Override
		public TemplateRecipeHandler newInstance() {
			return new InterBoatPartRecipeHandler();
		}

	}

	@Override
	public String getName() {
		return "Not Enough Items Integration";
	}

}
