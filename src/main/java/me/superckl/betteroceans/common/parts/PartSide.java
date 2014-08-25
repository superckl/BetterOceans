package me.superckl.betteroceans.common.parts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.superckl.betteroceans.common.reference.ModData;
import me.superckl.betteroceans.common.reference.ModItems;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@RequiredArgsConstructor
public abstract class PartSide extends BoatPart{

	protected List<ModelRenderer> renderers;
	@Getter
	protected final boolean leftSide;

	@Override
	public Type getType() {
		return Type.SIDE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<ModelRenderer> getRenderers(final ModelBase base) {
		if(this.renderers == null){
			this.renderers = new ArrayList<ModelRenderer>();
			final ModelRenderer part0 = new ModelRenderer(base, 0, 0);
			final ModelRenderer part1 = new ModelRenderer(base, 0, 0);
			final ModelRenderer part2 = new ModelRenderer(base, 0, 0);
			final ModelRenderer part3 = new ModelRenderer(base, 0, 0);
			final ModelRenderer part4 = new ModelRenderer(base, 0, 0);

			final byte b0 = 24; //side length, base length
			final byte b1 = 6; //side height
			final byte b2 = 20; //Base width
			final byte b3 = 4; //base depth
			//Side width = 2
			if(this.leftSide){
				part0.addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
				part0.setRotationPoint(0.0F, b3, -b2 / 2 + 1);
				part1.addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 10, b1, 2, 0.0F);
				part1.setRotationPoint(-11F, (float)b3-2, -b2 / 2 + 3);
				part2.addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, 6, b1, 2, 0.0F);
				part2.setRotationPoint(-14F, (float)b3-4, -b2 / 2 + 5);
				//this.boatSides[12].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
				//this.boatSides[12].setRotationPoint(0.0F, b3, -b2 / 2 + 1); This was here twice?
				part3.addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 10, b1, 2, 0.0F);
				part3.setRotationPoint(16F, (float)b3-2, -b2 / 2 + 3);
				part4.addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, 6, b1, 2, 0.0F);
				part4.setRotationPoint(27F, (float)b3-4, -b2 / 2 + 5);
			}else{
				part0.addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
				part0.setRotationPoint(0.0F, b3, b2 / 2 - 1);
				part1.addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 10, b1, 2, 0.0F);
				part1.setRotationPoint(-11F, (float)b3-2, b2 / 2 - 3);
				part2.addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, 6, b1, 2, 0.0F);
				part2.setRotationPoint(-14F, (float)b3-4, b2 / 2 - 5);
				//this.boatSides[13].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
				//this.boatSides[13].setRotationPoint(0.0F, b3, b2 / 2 - 1); This was here twice?
				part3.addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 10, b1, 2, 0.0F);
				part3.setRotationPoint(16F, (float)b3-2, b2 / 2 - 3);
				part4.addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, 6, b1, 2, 0.0F);
				part4.setRotationPoint(27F, (float)b3-4, b2 / 2 - 5);
			}

			this.renderers.add(part0);
			this.renderers.add(part1);
			this.renderers.add(part2);
			this.renderers.add(part3);
			this.renderers.add(part4);
		}
		return this.renderers;
	}

	@Override
	public int getMaxNumberOnBoat(){
		return 2;
	}

	@Override
	public void getRequiredTypesWithComplexities(final List<TypeRequirement> required) {
		required.add(new TypeRequirement(Type.BOTTOM, this.getComplexity()));

	}

	public static class PartWoodenSide extends PartSide{

		private static int leftID = BoatPart.registerPart(PartWoodenSide.class, true);
		private static int rightID = BoatPart.registerPart(PartWoodenSide.class, false);

		public PartWoodenSide(final Boolean leftSide) {
			super(leftSide);
		}

		@Override
		public List<ItemStack> getCraftingIngredients() {
			return Arrays.asList(new ItemStack(Blocks.planks, 2));
		}

		@Override
		public Material getMaterial() {
			return Material.WOOD;
		}

		@Override
		public ItemStack getCraftingResult() {
			return new ItemStack(ModItems.boatPart, 1, PartWoodenSide.leftID);
		}

		@Override
		public double getSpeedModifier(){
			return 0.988D;
		}

		@Override
		public boolean shouldDrop(final Random random) {
			return random.nextDouble() < .9D;
		}

		@Override
		public int getPartID() {
			return this.leftSide ? PartWoodenSide.leftID:PartWoodenSide.rightID;
		}

		@Override
		public String getItemTexture() {
			return ModData.MOD_ID+":part_side_wood";
		}

	}

	public static class PartIronSide extends PartSide{

		private static int leftID = BoatPart.registerPart(PartIronSide.class, true);
		private static int rightID = BoatPart.registerPart(PartIronSide.class, false);

		public PartIronSide(final Boolean leftSide) {
			super(leftSide);
		}

		@Override
		public List<ItemStack> getCraftingIngredients() {
			return Arrays.asList(new ItemStack(Items.iron_ingot, 3), new ItemStack(Blocks.planks));
		}

		@Override
		public Material getMaterial() {
			return Material.IRON;
		}

		@Override
		public ItemStack getCraftingResult() {
			return new ItemStack(ModItems.boatPart, 1, PartIronSide.leftID);
		}

		@Override
		public double getSpeedModifier(){
			return 1.05D;
		}
		@Override
		public double getSinkChanceModifier(){
			return .9D;
		}
		@Override
		public double getTurnModifier(){
			return 1.1D;
		}

		@Override
		public int getCreationTime(){
			return 300;
		}

		@Override
		public int getPartID() {
			return this.leftSide ? PartIronSide.leftID:PartIronSide.rightID;
		}

		@Override
		public boolean shouldDrop(final Random random) {
			return random.nextDouble() < .95D;
		}

		@Override
		public String getItemTexture() {
			return ModData.MOD_ID+":part_side_iron";
		}

	}

}
