package me.superckl.betteroceans.client.render.entity;

import me.superckl.betteroceans.client.model.entity.ModelFishBasic;
import me.superckl.betteroceans.common.reference.ModData;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderFishBasic extends RenderLiving{

	private static final ResourceLocation fishBasicTextures = new ResourceLocation(ModData.MOD_ID+":textures/entity/fishbasic.png");


	public RenderFishBasic() {
		super(new ModelFishBasic(), .2F);
	}

	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return RenderFishBasic.fishBasicTextures;
	}

}
