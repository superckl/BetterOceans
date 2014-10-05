package me.superckl.betteroceans.common.parts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModItems;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class PartBottom extends BoatPart{

	protected List<ModelRenderer> renderers;

	@Override
	@SideOnly(Side.CLIENT)
	public List<ModelRenderer> getRenderers(final ModelBase base){
		if(this.renderers == null){
			this.renderers = new ArrayList<ModelRenderer>();
			final ModelRenderer part0 = new ModelRenderer(base, 0, 8);
			//Front
			final ModelRenderer part1 = new ModelRenderer(base, 0, 8);
			final ModelRenderer part2 = new ModelRenderer(base, 0, 8);
			//Back
			final ModelRenderer part3 = new ModelRenderer(base, 0, 8);
			final ModelRenderer part4 = new ModelRenderer(base, 0, 8);

			final byte b0 = 24; //side length, base length
			final byte b2 = 20; //Base width
			final byte b3 = 4; //base depth
			//Side width = 2

			part0.addBox(-b0 / 2, -b2 / 2 + 2, -3.0F, b0 /*length*/, b2 - 4 /*width*/, 4, 0.0F);
			part0.setRotationPoint(0.0F, b3, 0.0F);

			part1.addBox(-b0 / 2, -b2 / 2 + 2, -3.0F, b0-10 /*length*/, b2 - 4 /*width*/, 4, 0.0F);
			part1.setRotationPoint(-9F, (float)b3-2, 0.0F);

			part2.addBox(-b0 / 2, -b2 / 2 + 2, -3.0F, 6 /*length*/, 12/*width*/, 4, 0.0F);
			part2.setRotationPoint(-12F, (float)b3-4, 2F);

			part3.addBox(-b0 / 2, -b2 / 2 + 2, -3.0F, b0-10 /*length*/, b2 - 4 /*width*/, 4, 0.0F);
			part3.setRotationPoint(18F, (float)b3-2, 0.0F);

			part4.addBox(-b0 / 2, -b2 / 2 + 2, -3.0F, 6 /*length*/, 12/*width*/, 4, 0.0F);
			part4.setRotationPoint(29F, (float)b3-4, 2F);

			part0.rotateAngleX = (float)Math.PI / 2F;
			part1.rotateAngleX = (float)Math.PI / 2F;
			part2.rotateAngleX = (float)Math.PI / 2F;
			part3.rotateAngleX = (float)Math.PI / 2F;
			part4.rotateAngleX = (float)Math.PI / 2F;

			this.renderers.add(part0);
			this.renderers.add(part1);
			this.renderers.add(part2);
			this.renderers.add(part3);
			this.renderers.add(part4);
		}
		return this.renderers;
	}

	@Override
	public Type getType(){
		return Type.BOTTOM;
	}

	@Override
	public int getMaxNumberOnBoat(){
		return 1;
	}

	@Override
	public boolean useOverallComplexity(){
		return true;
	}

	@Override
	public int getRequiredComplexity(){
		return 0;
	}

	@Override
	public void getRequiredTypesWithComplexities(final List<TypeRequirement> required){
		throw new UnsupportedOperationException("This doesn't do anything!");
	}


	public static class PartWoodenBottom extends PartBottom{

		private static int partConstructorID = BoatPart.registerPart(PartWoodenBottom.class);

		@Override
		public List<ItemStack> getCraftingIngredients(final boolean nei) {
			return Arrays.asList(new ItemStack(Blocks.planks, 3, nei ? Short.MAX_VALUE:0));
		}

		@Override
		public Material getMaterial() {
			return Material.WOOD;
		}

		@Override
		public ItemStack getCraftingResult() {
			return new ItemStack(ModItems.boatPart, 1, PartWoodenBottom.partConstructorID);
		}

		@Override
		public double getSpeedModifier(){
			return 0.955D;
		}

		@Override
		public int getPartID() {
			return PartWoodenBottom.partConstructorID;
		}

		@Override
		public boolean shouldDrop(final Random random) {
			return random.nextDouble() < .9D;
		}

		@Override
		public String getItemTexture() {
			return ModData.MOD_ID+":part_bottom_wood";
		}

	}

	public static class PartIronBottom extends PartBottom{

		private static int partConstructorID = BoatPart.registerPart(PartIronBottom.class);

		@Override
		public List<ItemStack> getCraftingIngredients(final boolean nei) {
			return Arrays.asList(new ItemStack(Items.iron_ingot, 4, nei ? Short.MAX_VALUE:0), new ItemStack(Blocks.planks, 3, nei ? Short.MAX_VALUE:0));
		}

		@Override
		public Material getMaterial() {
			return Material.IRON;
		}

		@Override
		public ItemStack getCraftingResult() {
			return new ItemStack(ModItems.boatPart, 1, PartIronBottom.partConstructorID);
		}

		@Override
		public double getSpeedModifier(){
			return 1.02D;
		}
		@Override
		public double getSinkChanceModifier(){
			return .6D;
		}
		@Override
		public double getTurnModifier(){
			return 1.1D;
		}

		@Override
		public int getCreationTime(){
			return 400;
		}

		@Override
		public int getPartID() {
			return PartIronBottom.partConstructorID;
		}

		@Override
		public boolean shouldDrop(final Random random) {
			return random.nextDouble() < .95D;
		}

		@Override
		public String getItemTexture() {
			return ModData.MOD_ID+":part_bottom_iron";
		}

	}

}
