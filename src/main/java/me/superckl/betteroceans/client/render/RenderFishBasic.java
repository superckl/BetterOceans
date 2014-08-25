package me.superckl.betteroceans.client.render;

import me.superckl.betteroceans.client.model.ModelFishBasic;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderFishBasic extends RenderLiving{

	private static final ResourceLocation fishBasicTextures = new ResourceLocation("textures/entity/fishbasic.png");


	public RenderFishBasic() {
		super(new ModelFishBasic(), .2F);
	}

	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return RenderFishBasic.fishBasicTextures;
	}

}
