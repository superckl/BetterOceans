package me.superckl.betteroceans.common.parts;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BoatPart {

	protected ResourceLocation texture;

	public abstract Type getType();
	public abstract List<ItemStack> getCraftingIngredients();
	public abstract Material getMaterial();
	public double getSpeedModifier(){
		return 1D;
	}
	public abstract ItemStack getCraftingResult();
	public ResourceLocation getTexture(){
		if(this.texture == null)
			this.texture = new ResourceLocation(this.getMaterial().getDefaultResourceLocation());
		return this.texture;
	}

	/**
	 * This is called every time the entity is rendered. DO NOT MAKE A NEW LIST EVERY TIME
	 * Make sure to add @SideOnly with Side.Client
	 */
	@SideOnly(Side.CLIENT)
	public abstract List<ModelRenderer> getRenderers(final ModelBase base);

	public static enum Type{
		BOTTOM,
		SIDE,
		END;
	}

	@RequiredArgsConstructor
	public static enum Material{
		WOOD("textures/entity/boat.png"),
		IRON(""),//TODO
		GLASS("");//TODO

		@Getter
		private final String defaultResourceLocation;

	}

}
