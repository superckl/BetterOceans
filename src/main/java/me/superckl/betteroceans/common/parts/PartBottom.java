package me.superckl.betteroceans.common.parts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import me.superckl.betteroceans.common.BoatPart;

public abstract class PartBottom extends BoatPart{

	protected List<ModelRenderer> renderers;
	
	@Override
	public List<ModelRenderer> getRenderers(ModelBase base){
		if(renderers == null){
			renderers = new ArrayList<ModelRenderer>();
			ModelRenderer part0 = new ModelRenderer(base, 0, 8);
			//Front
			ModelRenderer part1 = new ModelRenderer(base, 0, 8);
			ModelRenderer part2 = new ModelRenderer(base, 0, 8);
			//Back
			ModelRenderer part3 = new ModelRenderer(base, 0, 8);
			ModelRenderer part4 = new ModelRenderer(base, 0, 8);
			
			final byte b0 = 24; //side length, base length
			final byte b1 = 6; //side height
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
			
			renderers.add(part0);
			renderers.add(part1);
			renderers.add(part2);
			renderers.add(part3);
			renderers.add(part4);
		}
		return renderers;
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
		public ItemStack asItemStack() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
