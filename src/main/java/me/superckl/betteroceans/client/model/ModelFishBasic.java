package me.superckl.betteroceans.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelFishBasic extends ModelBase{

	private final ModelRenderer[] silverfishBodyParts = new ModelRenderer[3];
	private final ModelRenderer[] silverfishFinParts = new ModelRenderer[1];

	private static final int[][] silverfishBoxLength = new int[][] {{3, 4, 5}, {2, 2, 2}, {1, 4, 2}};
	private static final int[][] silverfishTexturePositions = new int[][] {{0, 0}, {0, 9}, {0, 0}};
	private static final float[] silverfishYOffset = new float[] {0F, 2F, 0F};

	private static final int[][] silverfishFinBoxLength = new int[][] {{1, 3, 3}};
	private static final int[][] silverfishFinTexturePositions = new int[][] {{0, 0}};
	private static final float[] silverfishFinYOffset = new float[] {3.5F};
	private static final float[][] silverfishFinRotation = new float[][] {{(float) (Math.PI/2.2F), 0, 0}};


	public ModelFishBasic() {
		float f = -3.5F;

		for (int i = 0; i < this.silverfishBodyParts.length; ++i)
		{
			this.silverfishBodyParts[i] = new ModelRenderer(this, ModelFishBasic.silverfishTexturePositions[i][0], ModelFishBasic.silverfishTexturePositions[i][1]);
			this.silverfishBodyParts[i].addBox(ModelFishBasic.silverfishBoxLength[i][0] * -0.5F, ModelFishBasic.silverfishYOffset[i]* -0.5F, ModelFishBasic.silverfishBoxLength[i][2] * -0.5F, ModelFishBasic.silverfishBoxLength[i][0], ModelFishBasic.silverfishBoxLength[i][1], ModelFishBasic.silverfishBoxLength[i][2]);
			this.silverfishBodyParts[i].setRotationPoint(0.0F, 24 - ModelFishBasic.silverfishBoxLength[i][1], f);

			if (i < this.silverfishBodyParts.length - 1)
				f += (ModelFishBasic.silverfishBoxLength[i][2] + ModelFishBasic.silverfishBoxLength[i + 1][2]) * 0.5F;
		}
		f = -3.5F;

		for (int i = 0; i < this.silverfishFinParts.length; ++i)
		{
			this.silverfishFinParts[i] = new ModelRenderer(this, ModelFishBasic.silverfishFinTexturePositions[i][0], ModelFishBasic.silverfishFinTexturePositions[i][1]);
			this.silverfishFinParts[i].addBox(ModelFishBasic.silverfishFinBoxLength[i][0] * -0.5F, ModelFishBasic.silverfishFinYOffset[i]* -0.5F, ModelFishBasic.silverfishFinBoxLength[i][2] * -0.5F, ModelFishBasic.silverfishFinBoxLength[i][0], ModelFishBasic.silverfishFinBoxLength[i][1], ModelFishBasic.silverfishFinBoxLength[i][2]);
			this.silverfishFinParts[i].setRotationPoint(0.0F, 24 - ModelFishBasic.silverfishFinBoxLength[i][1], f);
			this.silverfishFinParts[i].rotateAngleX = ModelFishBasic.silverfishFinRotation[i][0];
			this.silverfishFinParts[i].rotateAngleY = ModelFishBasic.silverfishFinRotation[i][1];
			this.silverfishFinParts[i].rotateAngleZ = ModelFishBasic.silverfishFinRotation[i][2];
			if (i < this.silverfishFinParts.length - 1)
				f += (ModelFishBasic.silverfishFinBoxLength[i][2] + ModelFishBasic.silverfishFinBoxLength[i + 1][2]) * 0.5F;
		}
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_)
	{
		this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);

		for (final ModelRenderer silverfishBodyPart : this.silverfishBodyParts)
			silverfishBodyPart.render(p_78088_7_);

		for(final ModelRenderer renderer:this.silverfishFinParts)
			renderer.render(p_78088_7_);
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
			if(i == 0)
				continue;
			this.silverfishBodyParts[i].rotateAngleY = MathHelper.cos(p_78087_3_ * 0.9F + i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.05F * (1 + Math.abs(i - 2));
			this.silverfishBodyParts[i].rotationPointX = MathHelper.sin(p_78087_3_ * 0.9F + i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * Math.abs(i - 2);
		}

		/*for (int i = 0; i < this.silverfishFinParts.length; ++i)
		{
			this.silverfishFinParts[i].rotateAngleY = MathHelper.cos(p_78087_3_ * 0.9F + i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.05F * (1 + Math.abs(i - 2));
			this.silverfishFinParts[i].rotationPointX = MathHelper.sin(p_78087_3_ * 0.9F + i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * Math.abs(i - 2);
		}*/

	}

}
