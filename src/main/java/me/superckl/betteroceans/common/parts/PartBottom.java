package me.superckl.betteroceans.common.parts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.init.Blocks;
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


	public class PartWoodenBottom extends PartBottom{

		@Override
		public List<ItemStack> getCraftingIngredients() {
			return Arrays.asList(new ItemStack(Blocks.planks, 3));
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
