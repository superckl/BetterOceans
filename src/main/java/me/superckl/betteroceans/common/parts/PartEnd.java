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
public abstract class PartEnd extends BoatPart{

	protected List<ModelRenderer> renderers;
	@Getter
	protected final boolean front;

	@Override
	public Type getType() {
		return Type.END;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<ModelRenderer> getRenderers(final ModelBase base) {
		if(this.renderers == null){
			this.renderers = new ArrayList<ModelRenderer>();
			final ModelRenderer part0 = new ModelRenderer(base, 0, 0);

			final byte b0 = 24; //side length, base length
			final byte b1 = 6; //side height
			final byte b3 = 4; //base depth
			//Side width = 2
			if(this.front){
				part0.addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, 12, b1, 2, 0.0F);
				part0.setRotationPoint(-25F, (float)b3-4, 4F);
			}else{
				part0.addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, 12, b1, 2, 0.0F);
				part0.setRotationPoint(24F, (float)b3-4, 4F);
			}
			part0.rotateAngleY = (float)Math.PI * 3F / 2F;
			this.renderers.add(part0);
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
		required.add(new TypeRequirement(Type.SIDE, this.getComplexity()));
		required.add(new TypeRequirement(Type.SIDE, this.getComplexity()));
	}

	public static class PartWoodenEnd extends PartEnd{

		private static int frontID = BoatPart.registerPart(PartWoodenEnd.class, true);
		private static int backID = BoatPart.registerPart(PartWoodenEnd.class, false);

		public PartWoodenEnd(final Boolean front) {
			super(front);
		}

		@Override
		public List<ItemStack> getCraftingIngredients(final boolean nei) {
			return Arrays.asList(new ItemStack(Blocks.planks, 1, nei ? Short.MAX_VALUE:0));
		}

		@Override
		public Material getMaterial() {
			return Material.WOOD;
		}

		@Override
		public ItemStack getCraftingResult() {
			return new ItemStack(ModItems.boatPart, 2, PartWoodenEnd.frontID);
		}

		@Override
		public double getSpeedModifier(){
			return 0.985D;
		}

		@Override
		public boolean shouldDrop(final Random random) {
			return random.nextDouble() < .9D;
		}

		@Override
		public int getPartID() {
			if(this.front)
				return PartWoodenEnd.frontID;
			return PartWoodenEnd.backID;
		}

		@Override
		public String getItemTexture() {
			return ModData.MOD_ID+":part_end_wood";
		}

	}

	public static class PartIronEnd extends PartEnd{

		private static int frontID = BoatPart.registerPart(PartIronEnd.class, true);
		private static int backID = BoatPart.registerPart(PartIronEnd.class, false);

		public PartIronEnd(final Boolean front) {
			super(front);
		}

		@Override
		public List<ItemStack> getCraftingIngredients(final boolean nei) {
			return Arrays.asList(new ItemStack(Items.iron_ingot, 2, nei ? Short.MAX_VALUE:0));
		}

		@Override
		public Material getMaterial() {
			return Material.IRON;
		}

		@Override
		public ItemStack getCraftingResult() {
			return new ItemStack(ModItems.boatPart, 1, PartIronEnd.frontID);
		}

		@Override
		public double getSpeedModifier(){
			return 1.004D;
		}
		@Override
		public double getSinkChanceModifier(){
			return .95D;
		}
		@Override
		public double getTurnModifier(){
			return 1.05D;
		}

		@Override
		public int getCreationTime(){
			return 200;
		}

		@Override
		public int getPartID() {
			if(this.front)
				return PartIronEnd.frontID;
			return PartIronEnd.backID;
		}

		@Override
		public boolean shouldDrop(final Random random) {
			return random.nextDouble() < .95D;
		}

		@Override
		public String getItemTexture() {
			return ModData.MOD_ID+":part_end_iron";
		}

	}

}
