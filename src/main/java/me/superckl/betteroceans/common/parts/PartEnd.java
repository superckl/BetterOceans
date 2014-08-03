package me.superckl.betteroceans.common.parts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.utility.CollectionHelper;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

	public static class PartWoodenEnd extends PartEnd{

		public PartWoodenEnd(final boolean front) {
			super(front);
		}

		@Override
		public List<ItemStack> getCraftingIngredients() {
			return Arrays.asList(new ItemStack(Blocks.planks, 1));
		}

		@Override
		public Material getMaterial() {
			return Material.WOOD;
		}

		@Override
		public ItemStack getCraftingResult() {
			return new ItemStack(ModItems.boatPart, 1, 4 + 8);
		}

		@Override
		public double getSpeedModifier(){
			return 0.985D;
		}

		@Override
		public void serialize(final NBTTagCompound comp) {
			LogHelper.info("Serializing end");
			comp.setInteger("ID", CollectionHelper.getByValue(BoatPart.getParts(), this.getClass()));
			comp.setBoolean("boolFlag", this.front);
		}

	}

}
