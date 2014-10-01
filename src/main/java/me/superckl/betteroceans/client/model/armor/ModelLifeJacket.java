package me.superckl.betteroceans.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLifeJacket extends ModelBiped
{
	ModelRenderer vest1;
	ModelRenderer vest1m1;
	ModelRenderer vest2;
	ModelRenderer vest2m1;
	ModelRenderer neck;
	ModelRenderer neck2;
	ModelRenderer neck3;
	ModelRenderer tie;
	ModelRenderer tie2;
	ModelRenderer tie3;
	ModelRenderer tie4;

	public ModelLifeJacket()
	{
		this( 0.0f );
	}

	public ModelLifeJacket( final float par1 )
	{
		this.vest1 = new ModelRenderer( this, 91, 50 );
		this.vest1.setTextureSize( 128, 64 );
		this.vest1.addBox( -1.5F, -4F, -0.5F, 3, 8, 1);
		this.vest1.setRotationPoint( -2.5F, 7F, -2.5F );
		this.vest1m1 = new ModelRenderer( this, 90, 46 );
		this.vest1m1.setTextureSize( 128, 64 );
		this.vest1m1.addBox( -1F, -1.5F, -0.5F, 2, 3, 1);
		this.vest1m1.setRotationPoint( -3F, 1.5F, -2.5F );
		this.vest2 = new ModelRenderer( this, 101, 50 );
		this.vest2.setTextureSize( 128, 64 );
		this.vest2.addBox( -1.5F, -4F, -0.5F, 3, 8, 1);
		this.vest2.setRotationPoint( 2.5F, 7F, -2.5F );
		this.vest2m1 = new ModelRenderer( this, 104, 46 );
		this.vest2m1.setTextureSize( 128, 64 );
		this.vest2m1.addBox( -1F, -1.5F, -0.5F, 2, 3, 1);
		this.vest2m1.setRotationPoint( 3F, 1.5F, -2.5F );
		this.neck = new ModelRenderer( this, 62, 47 );
		this.neck.setTextureSize( 128, 64 );
		this.neck.addBox( -4.5F, -1F, -2.5F, 9, 2, 5);
		this.neck.setRotationPoint( 0F, 1F, 3F );
		this.neck2 = new ModelRenderer( this, 62, 47 );
		this.neck2.setTextureSize( 128, 64 );
		this.neck2.addBox( -0.5F, -1F, -2.5F, 1, 2, 5);
		this.neck2.setRotationPoint( -4F, 1F, 0F );
		this.neck3 = new ModelRenderer( this, 62, 47 );
		this.neck3.setTextureSize( 128, 64 );
		this.neck3.addBox( -0.5F, -1F, -2.5F, 1, 2, 5);
		this.neck3.setRotationPoint( 4F, 1F, 0F );
		this.tie = new ModelRenderer( this, 100, 42 );
		this.tie.setTextureSize( 128, 64 );
		this.tie.addBox( -1F, -0.5F, -0.5F, 2, 1, 1);
		this.tie.setRotationPoint( 0F, 7.5F, -2.5F );
		this.tie2 = new ModelRenderer( this, 97, 36 );
		this.tie2.setTextureSize( 128, 64 );
		this.tie2.addBox( -0.5F, -0.5F, -2.5F, 1, 1, 5);
		this.tie2.setRotationPoint( -4F, 7.5F, -0.5F );
		this.tie3 = new ModelRenderer( this, 97, 36 );
		this.tie3.setTextureSize( 128, 64 );
		this.tie3.addBox( -0.5F, -0.5F, -2.5F, 1, 1, 5);
		this.tie3.setRotationPoint( 4F, 7.5F, -0.5F );
		this.tie4 = new ModelRenderer( this, 101, 40 );
		this.tie4.setTextureSize( 128, 64 );
		this.tie4.addBox( -4.5F, -0.5F, -0.5F, 9, 1, 1);
		this.tie4.setRotationPoint( 0F, 7.5F, 2F );
	}

	@Override
	public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7)
	{

		this.vest1.rotateAngleX = 0F;
		this.vest1.rotateAngleY = 0F;
		this.vest1.rotateAngleZ = 0F;
		this.vest1.renderWithRotation(par7);

		this.vest1m1.rotateAngleX = 0F;
		this.vest1m1.rotateAngleY = 0F;
		this.vest1m1.rotateAngleZ = 0F;
		this.vest1m1.renderWithRotation(par7);

		this.vest2.rotateAngleX = 0F;
		this.vest2.rotateAngleY = 0F;
		this.vest2.rotateAngleZ = 0F;
		this.vest2.renderWithRotation(par7);

		this.vest2m1.rotateAngleX = 0F;
		this.vest2m1.rotateAngleY = 0F;
		this.vest2m1.rotateAngleZ = 0F;
		this.vest2m1.renderWithRotation(par7);

		this.neck.rotateAngleX = 0F;
		this.neck.rotateAngleY = 0F;
		this.neck.rotateAngleZ = 0F;
		this.neck.renderWithRotation(par7);

		this.neck2.rotateAngleX = 0F;
		this.neck2.rotateAngleY = 0F;
		this.neck2.rotateAngleZ = 0F;
		this.neck2.renderWithRotation(par7);

		this.neck3.rotateAngleX = 0F;
		this.neck3.rotateAngleY = 0F;
		this.neck3.rotateAngleZ = 0F;
		this.neck3.renderWithRotation(par7);

		this.tie.rotateAngleX = 0F;
		this.tie.rotateAngleY = 0F;
		this.tie.rotateAngleZ = 0F;
		this.tie.renderWithRotation(par7);

		this.tie2.rotateAngleX = 0F;
		this.tie2.rotateAngleY = 0F;
		this.tie2.rotateAngleZ = 0F;
		this.tie2.renderWithRotation(par7);

		this.tie3.rotateAngleX = 0F;
		this.tie3.rotateAngleY = 0F;
		this.tie3.rotateAngleZ = 0F;
		this.tie3.renderWithRotation(par7);

		this.tie4.rotateAngleX = 0F;
		this.tie4.rotateAngleY = 0F;
		this.tie4.rotateAngleZ = 0F;
		this.tie4.renderWithRotation(par7);

	}

}
