package me.superckl.betteroceans.client.model;

import lombok.Getter;
import me.superckl.betteroceans.common.entity.EntityBOBoat;
import me.superckl.betteroceans.common.utility.LogHelper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBOBoat extends ModelBase{
	
	@Getter
	private ModelRenderer[] boatSides = new ModelRenderer[19];
	public ModelBOBoat()
	{
		this.boatSides[0] = new ModelRenderer(this, 0, 8);
		//Front
		this.boatSides[1] = new ModelRenderer(this, 0, 8);
		this.boatSides[2] = new ModelRenderer(this, 0, 8);
		this.boatSides[3] = new ModelRenderer(this, 0, 0);
		this.boatSides[4] = new ModelRenderer(this, 0, 0);
		this.boatSides[5] = new ModelRenderer(this, 0, 0);
		this.boatSides[6] = new ModelRenderer(this, 0, 0);
		this.boatSides[7] = new ModelRenderer(this, 0, 0);
		this.boatSides[8] = new ModelRenderer(this, 0, 0);
		this.boatSides[9] = new ModelRenderer(this, 0, 0);
		//Back
		this.boatSides[10] = new ModelRenderer(this, 0, 8);
		this.boatSides[11] = new ModelRenderer(this, 0, 8);
		this.boatSides[12] = new ModelRenderer(this, 0, 0);
		this.boatSides[13] = new ModelRenderer(this, 0, 0);
		this.boatSides[14] = new ModelRenderer(this, 0, 0);
		this.boatSides[15] = new ModelRenderer(this, 0, 0);
		this.boatSides[16] = new ModelRenderer(this, 0, 0);
		this.boatSides[17] = new ModelRenderer(this, 0, 0);
		this.boatSides[18] = new ModelRenderer(this, 0, 0);

		final byte b0 = 24; //side length, base length
		final byte b1 = 6; //side height
		final byte b2 = 20; //Base width
		final byte b3 = 4; //base depth
		//Side width = 2
		//Bottom
		this.boatSides[0].addBox(-b0 / 2, -b2 / 2 + 2, -3.0F, b0 /*length*/, b2 - 4 /*width*/, 4, 0.0F);
		this.boatSides[0].setRotationPoint(0.0F, b3, 0.0F);

		this.boatSides[1].addBox(-b0 / 2, -b2 / 2 + 2, -3.0F, b0-10 /*length*/, b2 - 4 /*width*/, 4, 0.0F);
		this.boatSides[1].setRotationPoint(-9F, (float)b3-2, 0.0F);

		this.boatSides[2].addBox(-b0 / 2, -b2 / 2 + 2, -3.0F, 6 /*length*/, 12/*width*/, 4, 0.0F);
		this.boatSides[2].setRotationPoint(-12F, (float)b3-4, 2F);

		/*
        //Back
        this.boatSides[2].addBox((float)(-b0 / 2 + 2), (float)(-b1 - 1), -1.0F, b0 - 4, b1, 2, 0.0F);
        this.boatSides[2].setRotationPoint((float)(b0 / 2 - 1), (float)b3, 0.0F);
		 */

		//Left
		this.boatSides[3].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
		this.boatSides[3].setRotationPoint(0.0F, b3, -b2 / 2 + 1);
		//Right
		this.boatSides[4].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
		this.boatSides[4].setRotationPoint(0.0F, b3, b2 / 2 - 1);

		//Left
		this.boatSides[5].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 10, b1, 2, 0.0F);
		this.boatSides[5].setRotationPoint(-11F, (float)b3-2, -b2 / 2 + 3);
		//Right
		this.boatSides[6].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 10, b1, 2, 0.0F);
		this.boatSides[6].setRotationPoint(-11F, (float)b3-2, b2 / 2 - 3);

		//Left
		this.boatSides[7].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, 6, b1, 2, 0.0F);
		this.boatSides[7].setRotationPoint(-14F, (float)b3-4, -b2 / 2 + 5);
		//Right
		this.boatSides[8].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, 6, b1, 2, 0.0F);
		this.boatSides[8].setRotationPoint(-14F, (float)b3-4, b2 / 2 - 5);

		//Front
		this.boatSides[9].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, 12, b1, 2, 0.0F);
		this.boatSides[9].setRotationPoint(-25F, (float)b3-4, 4F);

		//Next side

		this.boatSides[10].addBox(-b0 / 2, -b2 / 2 + 2, -3.0F, b0-10 /*length*/, b2 - 4 /*width*/, 4, 0.0F);
		this.boatSides[10].setRotationPoint(18F, (float)b3-2, 0.0F);

		this.boatSides[11].addBox(-b0 / 2, -b2 / 2 + 2, -3.0F, 6 /*length*/, 12/*width*/, 4, 0.0F);
		this.boatSides[11].setRotationPoint(29F, (float)b3-4, 2F);

		/*
        //Back
        this.boatSides[2].addBox((float)(-b0 / 2 + 2), (float)(-b1 - 1), -1.0F, b0 - 4, b1, 2, 0.0F);
        this.boatSides[2].setRotationPoint((float)(b0 / 2 - 1), (float)b3, 0.0F);
		 */

		//Left
		this.boatSides[12].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
		this.boatSides[12].setRotationPoint(0.0F, b3, -b2 / 2 + 1);
		//Right
		this.boatSides[13].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
		this.boatSides[13].setRotationPoint(0.0F, b3, b2 / 2 - 1);

		//Left
		this.boatSides[14].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 10, b1, 2, 0.0F);
		this.boatSides[14].setRotationPoint(16F, (float)b3-2, -b2 / 2 + 3);
		//Right
		this.boatSides[15].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 10, b1, 2, 0.0F);
		this.boatSides[15].setRotationPoint(16F, (float)b3-2, b2 / 2 - 3);

		//Left
		this.boatSides[16].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, 6, b1, 2, 0.0F);
		this.boatSides[16].setRotationPoint(27F, (float)b3-4, -b2 / 2 + 5);
		//Right
		this.boatSides[17].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, 6, b1, 2, 0.0F);
		this.boatSides[17].setRotationPoint(27F, (float)b3-4, b2 / 2 - 5);

		//Front
		this.boatSides[18].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, 12, b1, 2, 0.0F);
		this.boatSides[18].setRotationPoint(24F, (float)b3-4, 4F);


		this.boatSides[0].rotateAngleX = (float)Math.PI / 2F;
		this.boatSides[1].rotateAngleX = (float)Math.PI / 2F;
		this.boatSides[2].rotateAngleX = (float)Math.PI / 2F;
		this.boatSides[9].rotateAngleY = (float)Math.PI * 3F / 2F;
		this.boatSides[10].rotateAngleX = (float)Math.PI / 2F;
		this.boatSides[11].rotateAngleX = (float)Math.PI / 2F;
		this.boatSides[18].rotateAngleY = (float)Math.PI * 3F / 2F;
		//this.boatSides[2].rotateAngleY = ((float)Math.PI / 2F);
		//this.boatSides[3].rotateAngleY = (float)Math.PI;
	}
	
	private void init(EntityBOBoat boat){
		
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(final Entity entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7)
	{
		if(entity instanceof EntityBOBoat == false){
			//LogHelper.error("An entity was passed to the Boat Renderer that wasn't compatible!");
			return;
		}
		for (final ModelRenderer boatSide : this.boatSides)
			boatSide.render(par7);
	}

}
