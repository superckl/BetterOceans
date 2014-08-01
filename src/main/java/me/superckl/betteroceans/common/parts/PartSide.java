package me.superckl.betteroceans.common.parts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@RequiredArgsConstructor
public abstract class PartSide extends BoatPart{

	protected List<ModelRenderer> renderers;
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


	public class PartWoodenSide extends PartSide{

		public PartWoodenSide(final boolean leftSide) {
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
			// TODO Auto-generated method stub
			return null;
		}

	}

}
