package me.superckl.betteroceans.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelLantern extends ModelBase{

	ModelRenderer base;
	ModelRenderer pillar0;
	ModelRenderer pillar1;
	ModelRenderer pillar2;
	ModelRenderer pillar3;
	ModelRenderer topBase;
	ModelRenderer topMiddle;
	ModelRenderer topPeak;

	public ModelLantern()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.base = new ModelRenderer(this, 0, 0);
		this.base.addBox(0F, 0F, 0F, 10, 1, 10);
		this.base.setRotationPoint(-5F, 23F, -5F);
		this.base.setTextureSize(64, 32);
		this.base.mirror = true;
		this.setRotation(this.base, 0F, 0F, 0F);
		this.pillar0 = new ModelRenderer(this, 0, 0);
		this.pillar0.addBox(0F, 0F, 0F, 1, 9, 1);
		this.pillar0.setRotationPoint(2.5F, 14F, 2.5F);
		this.pillar0.setTextureSize(64, 32);
		this.pillar0.mirror = true;
		this.setRotation(this.pillar0, 0F, 0F, 0F);
		this.pillar1 = new ModelRenderer(this, 0, 0);
		this.pillar1.addBox(0F, 0F, 0F, 1, 9, 1);
		this.pillar1.setRotationPoint(2.5F, 14F, -3.5F);
		this.pillar1.setTextureSize(64, 32);
		this.pillar1.mirror = true;
		this.setRotation(this.pillar1, 0F, 0F, 0F);
		this.pillar2 = new ModelRenderer(this, 0, 0);
		this.pillar2.addBox(0F, 0F, 0F, 1, 9, 1);
		this.pillar2.setRotationPoint(-3.5F, 14F, 2.5F);
		this.pillar2.setTextureSize(64, 32);
		this.pillar2.mirror = true;
		this.setRotation(this.pillar2, 0F, 0F, 0F);
		this.pillar3 = new ModelRenderer(this, 0, 0);
		this.pillar3.addBox(0F, 0F, 0F, 1, 9, 1);
		this.pillar3.setRotationPoint(-3.5F, 14F, -3.5F);
		this.pillar3.setTextureSize(64, 32);
		this.pillar3.mirror = true;
		this.setRotation(this.pillar3, 0F, 0F, 0F);
		this.topBase = new ModelRenderer(this, 0, 0);
		this.topBase.addBox(0F, 0F, 0F, 10, 1, 10);
		this.topBase.setRotationPoint(-5F, 13F, -5F);
		this.topBase.setTextureSize(64, 32);
		this.topBase.mirror = true;
		this.setRotation(this.topBase, 0F, 0F, 0F);
		this.topMiddle = new ModelRenderer(this, 0, 0);
		this.topMiddle.addBox(0F, 0F, 0F, 5, 1, 5);
		this.topMiddle.setRotationPoint(-2.5F, 12F, -2.5F);
		this.topMiddle.setTextureSize(64, 32);
		this.topMiddle.mirror = true;
		this.setRotation(this.topMiddle, 0F, 0F, 0F);
		this.topPeak = new ModelRenderer(this, 0, 0);
		this.topPeak.addBox(-0.5F, 0F, -0.5F, 1, 1, 1);
		this.topPeak.setRotationPoint(0F, 11F, 0F);
		this.topPeak.setTextureSize(64, 32);
		this.topPeak.mirror = true;
		this.setRotation(this.topPeak, 0F, 0F, 0F);
	}

	public void renderAll()
	{
		final float f5 = 0.0625F;
		this.base.render(f5);
		this.pillar0.render(f5);
		this.pillar1.render(f5);
		this.pillar2.render(f5);
		this.pillar3.render(f5);
		this.topBase.render(f5);
		this.topMiddle.render(f5);
		this.topPeak.render(f5);
	}

	private void setRotation(final ModelRenderer model, final float x, final float y, final float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
