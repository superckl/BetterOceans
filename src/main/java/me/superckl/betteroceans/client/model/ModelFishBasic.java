package me.superckl.betteroceans.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelFishBasic extends ModelBase{

	private final ModelRenderer[] silverfishBodyParts = new ModelRenderer[7];

	private static final int[][] silverfishBoxLength = new int[][] {{3, 2, 2}, {4, 3, 2}, {6, 4, 3}, {3, 3, 3}, {2, 2, 3}, {2, 1, 2}, {1, 1, 2}};
	private static final int[][] silverfishTexturePositions = new int[][] {{0, 0}, {0, 4}, {0, 9}, {0, 16}, {0, 22}, {11, 0}, {13, 4}};

	public ModelFishBasic() {
		float f = -3.5F;

		for (int i = 0; i < this.silverfishBodyParts.length; ++i)
		{
			this.silverfishBodyParts[i] = new ModelRenderer(this, ModelFishBasic.silverfishTexturePositions[i][0], ModelFishBasic.silverfishTexturePositions[i][1]);
			this.silverfishBodyParts[i].addBox(ModelFishBasic.silverfishBoxLength[i][0] * -0.5F, 0.0F, ModelFishBasic.silverfishBoxLength[i][2] * -0.5F, ModelFishBasic.silverfishBoxLength[i][0], ModelFishBasic.silverfishBoxLength[i][1], ModelFishBasic.silverfishBoxLength[i][2]);
			this.silverfishBodyParts[i].setRotationPoint(0.0F, 24 - ModelFishBasic.silverfishBoxLength[i][1], f);

			if (i < this.silverfishBodyParts.length - 1)
				f += (ModelFishBasic.silverfishBoxLength[i][2] + ModelFishBasic.silverfishBoxLength[i + 1][2]) * 0.5F;
		}
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_)
	{
		this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		int i;

		for (i = 0; i < this.silverfishBodyParts.length; ++i)
			this.silverfishBodyParts[i].render(p_78088_7_);
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	@Override
	public void setRotationAngles(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_)
	{
		for (int i = 0; i < this.silverfishBodyParts.length; ++i)
		{
			this.silverfishBodyParts[i].rotateAngleY = MathHelper.cos(p_78087_3_ * 0.9F + i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.05F * (1 + Math.abs(i - 2));
			this.silverfishBodyParts[i].rotationPointX = MathHelper.sin(p_78087_3_ * 0.9F + i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * Math.abs(i - 2);
		}

	}

}
