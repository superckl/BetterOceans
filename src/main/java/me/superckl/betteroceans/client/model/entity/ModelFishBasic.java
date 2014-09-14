package me.superckl.betteroceans.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelFishBasic extends ModelBase{

	ModelRenderer body;
	ModelRenderer tail;
	ModelRenderer caudal;
	ModelRenderer dorsal;

	public ModelFishBasic()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.body = new ModelRenderer(this, 0, 0);
		this.body.addBox(-2F, 0F, -2.5F, 4, 4, 5);
		this.body.setRotationPoint(0F, 20F, 0F);
		this.body.setTextureSize(64, 32);
		this.body.mirror = true;
		this.setRotation(this.body, 0F, 0F, 0F);
		this.tail = new ModelRenderer(this, 0, 0);
		this.tail.addBox(-1F, 0F, 2.5F, 2, 2, 2);
		this.tail.setRotationPoint(0F, 21F, 0F);
		this.tail.setTextureSize(64, 32);
		this.tail.mirror = true;
		this.setRotation(this.tail, 0F, 0F, 0F);
		this.caudal = new ModelRenderer(this, 0, 0);
		this.caudal.addBox(-0.5F, 0F, 4F, 1, 4, 3);
		this.caudal.setRotationPoint(0F, 20F, 0F);
		this.caudal.setTextureSize(64, 32);
		this.caudal.mirror = true;
		this.setRotation(this.caudal, 0F, 0F, 0F);
		this.dorsal = new ModelRenderer(this, 0, 0);
		this.dorsal.addBox(-0.5F, 0F, -1.5F, 1, 3, 3);
		this.dorsal.setRotationPoint(0F, 19.5F, 0F);
		this.dorsal.setTextureSize(64, 32);
		this.dorsal.mirror = true;
		this.setRotation(this.dorsal, 0.1115358F, 0F, 0F);
	}

	@Override
	public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5)
	{
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.body.render(f5);
		this.tail.render(f5);
		this.caudal.render(f5);
		this.dorsal.render(f5);
	}

	private void setRotation(final ModelRenderer model, final float x, final float y, final float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	@Override
	public void setRotationAngles(final float p_78087_1_, final float p_78087_2_, float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_)
	{
		p_78087_3_*=.25F;
		this.tail.rotateAngleY = .5F*MathHelper.cos(p_78087_3_ * 0.9F + 1 * 0.15F * (float)Math.PI) * (float)Math.PI * 0.05F * (1 + Math.abs(1 - 2));
		this.tail.rotationPointX = .5F*MathHelper.sin(p_78087_3_ * 0.9F + 1 * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * Math.abs(1 - 2);

		this.caudal.rotateAngleY = .7F*MathHelper.cos(p_78087_3_ * 0.9F + 1 * 0.15F * (float)Math.PI) * (float)Math.PI * 0.05F * (1 + Math.abs(1 - 2));
		this.caudal.rotationPointX = .5F*MathHelper.sin(p_78087_3_ * 0.9F + 1 * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * Math.abs(1 - 2);

		/*for (int i = 0; i < this.silverfishFinParts.length; ++i)
		{
			this.silverfishFinParts[i].rotateAngleY = MathHelper.cos(p_78087_3_ * 0.9F + i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.05F * (1 + Math.abs(i - 2));
			this.silverfishFinParts[i].rotationPointX = MathHelper.sin(p_78087_3_ * 0.9F + i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * Math.abs(i - 2);
		}*/

	}

}
