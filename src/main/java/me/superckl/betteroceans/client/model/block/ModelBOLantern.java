package me.superckl.betteroceans.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBOLantern extends ModelBase
{
	ModelRenderer Base1;
	ModelRenderer Top1;
	ModelRenderer Top2;
	ModelRenderer Top3;
	ModelRenderer Top4;
	ModelRenderer Base2;
	ModelRenderer Base3;
	ModelRenderer Base4;
	ModelRenderer Base5;
	ModelRenderer Top5;
	ModelRenderer Top6;
	ModelRenderer Top7;
	ModelRenderer Top8;
	ModelRenderer Top9;
	ModelRenderer Handle;
	ModelRenderer Handle2;
	ModelRenderer Handle3;
	ModelRenderer Handle4;
	ModelRenderer Handle5;
	ModelRenderer Handle6;
	ModelRenderer Handle7;
	ModelRenderer Handle8;
	ModelRenderer Handle9;
	ModelRenderer Handle10;
	ModelRenderer Support;
	ModelRenderer Support4;
	ModelRenderer Support5;
	ModelRenderer Support6;
	ModelRenderer Support2;
	ModelRenderer Support3;
	ModelRenderer Dust1;
	ModelRenderer Dust3;
	ModelRenderer Dust4;
	ModelRenderer Dust2;
	ModelRenderer Dust5;
	ModelRenderer Glass1;
	ModelRenderer Glass2;
	ModelRenderer Glass3;
	ModelRenderer Glass4;
	ModelRenderer Glass5;
	ModelRenderer Glass6;
	ModelRenderer Glass7;
	ModelRenderer Glass8;
	ModelRenderer Glass9;
	ModelRenderer Glass10;
	ModelRenderer Glass11;
	ModelRenderer Glass12;
	ModelRenderer Glass13;
	ModelRenderer Glass14;
	ModelRenderer Glass15;
	ModelRenderer Glass16;
	ModelRenderer Glass17;
	ModelRenderer Glass18;
	ModelRenderer Glass19;
	ModelRenderer Glass20;

	public ModelBOLantern()
	{
		this( 0.0f );
	}

	public ModelBOLantern( final float par1 )
	{
		this.Base1 = new ModelRenderer( this, 0, 108 );
		this.Base1.setTextureSize( 128, 128 );
		this.Base1.addBox( -9F, -1F, -9F, 18, 2, 18);
		this.Base1.setRotationPoint( 0F, 23F, 0F );
		this.Top1 = new ModelRenderer( this, 78, 116 );
		this.Top1.setTextureSize( 128, 128 );
		this.Top1.addBox( -5.5F, -0.5F, -5.5F, 11, 1, 11);
		this.Top1.setRotationPoint( 0F, 0.5F, 0F );
		this.Top2 = new ModelRenderer( this, 82, 106 );
		this.Top2.setTextureSize( 128, 128 );
		this.Top2.addBox( -4.5F, -0.5F, -4.5F, 9, 1, 9);
		this.Top2.setRotationPoint( 0F, -0.5F, 0F );
		this.Top3 = new ModelRenderer( this, 86, 98 );
		this.Top3.setTextureSize( 128, 128 );
		this.Top3.addBox( -3.5F, -0.5F, -3.5F, 7, 1, 7);
		this.Top3.setRotationPoint( 0F, -1.5F, 0F );
		this.Top4 = new ModelRenderer( this, 90, 92 );
		this.Top4.setTextureSize( 128, 128 );
		this.Top4.addBox( -2.5F, -0.5F, -2.5F, 5, 1, 5);
		this.Top4.setRotationPoint( 0F, -2.5F, 0F );
		this.Base2 = new ModelRenderer( this, 4, 87 );
		this.Base2.setTextureSize( 128, 128 );
		this.Base2.addBox( -8F, -2.5F, -8F, 16, 5, 16);
		this.Base2.setRotationPoint( 0F, 19.5F, 0F );
		this.Base3 = new ModelRenderer( this, 22, 78 );
		this.Base3.setTextureSize( 128, 128 );
		this.Base3.addBox( -3.5F, -1F, -3.5F, 7, 2, 7);
		this.Base3.setRotationPoint( 0F, 16F, 0F );
		this.Base4 = new ModelRenderer( this, 16, 67 );
		this.Base4.setTextureSize( 128, 128 );
		this.Base4.addBox( -5F, -0.5F, -5F, 10, 1, 10);
		this.Base4.setRotationPoint( 0F, 14.5F, 0F );
		this.Base5 = new ModelRenderer( this, 14, 55 );
		this.Base5.setTextureSize( 128, 128 );
		this.Base5.addBox( -5.5F, -0.5F, -5.5F, 11, 1, 11);
		this.Base5.setRotationPoint( 0F, 13.5F, 0F );
		this.Top5 = new ModelRenderer( this, 67, 75 );
		this.Top5.setTextureSize( 128, 128 );
		this.Top5.addBox( -7.5F, -0.5F, -7.5F, 15, 1, 15);
		this.Top5.setRotationPoint( 0F, -3.5F, 0F );
		this.Top6 = new ModelRenderer( this, 67, 75 );
		this.Top6.setTextureSize( 128, 128 );
		this.Top6.addBox( -7F, -1F, -7F, 14, 2, 14);
		this.Top6.setRotationPoint( 0F, -5F, 0F );
		this.Top7 = new ModelRenderer( this, 71, 77 );
		this.Top7.setTextureSize( 128, 128 );
		this.Top7.addBox( -6F, -1F, -6F, 12, 2, 12);
		this.Top7.setRotationPoint( 0F, -7F, 0F );
		this.Top8 = new ModelRenderer( this, 85, 63 );
		this.Top8.setTextureSize( 128, 128 );
		this.Top8.addBox( -3.5F, -1F, -3.5F, 7, 2, 7);
		this.Top8.setRotationPoint( 0F, -9F, 0F );
		this.Top9 = new ModelRenderer( this, 83, 54 );
		this.Top9.setTextureSize( 128, 128 );
		this.Top9.addBox( -4F, -0.5F, -4F, 8, 1, 8);
		this.Top9.setRotationPoint( 0F, -10.5F, 0F );
		this.Handle = new ModelRenderer( this, 15, 15 );
		this.Handle.setTextureSize( 128, 128 );
		this.Handle.addBox( -0.5F, -2F, -0.5F, 1, 4, 1);
		this.Handle.setRotationPoint( -5F, -9F, 2.486899E-14F );
		this.Handle2 = new ModelRenderer( this, 15, 15 );
		this.Handle2.setTextureSize( 128, 128 );
		this.Handle2.addBox( -0.5F, -2F, -0.5F, 1, 4, 1);
		this.Handle2.setRotationPoint( 5F, -8.999996F, 5.960464E-07F );
		this.Handle3 = new ModelRenderer( this, 15, 15 );
		this.Handle3.setTextureSize( 128, 128 );
		this.Handle3.addBox( -0.5F, -5F, -0.5F, 1, 10, 1);
		this.Handle3.setRotationPoint( 6.499997F, -14.5F, -1.400709E-06F );
		this.Handle4 = new ModelRenderer( this, 15, 15 );
		this.Handle4.setTextureSize( 128, 128 );
		this.Handle4.addBox( -0.5F, -5F, -0.5F, 1, 10, 1);
		this.Handle4.setRotationPoint( -6.500003F, -14.5F, 9.238719E-07F );
		this.Handle5 = new ModelRenderer( this, 15, 15 );
		this.Handle5.setTextureSize( 128, 128 );
		this.Handle5.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
		this.Handle5.setRotationPoint( -3.250006F, -20F, 9.983779E-07F );
		this.Handle6 = new ModelRenderer( this, 15, 15 );
		this.Handle6.setTextureSize( 128, 128 );
		this.Handle6.addBox( -0.5F, -2F, -0.5F, 1, 4, 1);
		this.Handle6.setRotationPoint( 1.299994F, -21.1F, 3.159049E-07F );
		this.Handle7 = new ModelRenderer( this, 15, 15 );
		this.Handle7.setTextureSize( 128, 128 );
		this.Handle7.addBox( -0.5F, -2F, -0.5F, 1, 4, 1);
		this.Handle7.setRotationPoint( -1.400007F, -21.1F, 7.987015E-07F );
		this.Handle8 = new ModelRenderer( this, 15, 15 );
		this.Handle8.setTextureSize( 128, 128 );
		this.Handle8.addBox( -0.5F, -2F, -0.5F, 1, 4, 1);
		this.Handle8.setRotationPoint( -5.000006F, -20F, 1.311302E-06F );
		this.Handle9 = new ModelRenderer( this, 15, 15 );
		this.Handle9.setTextureSize( 128, 128 );
		this.Handle9.addBox( -0.5F, -2F, -0.5F, 1, 4, 1);
		this.Handle9.setRotationPoint( 4.999994F, -20F, -4.768367E-07F );
		this.Handle10 = new ModelRenderer( this, 15, 15 );
		this.Handle10.setTextureSize( 128, 128 );
		this.Handle10.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
		this.Handle10.setRotationPoint( 3.099994F, -20F, -1.370904E-07F );
		this.Support = new ModelRenderer( this, 14, 14 );
		this.Support.setTextureSize( 128, 128 );
		this.Support.addBox( -1F, -12F, -1F, 2, 24, 2);
		this.Support.setRotationPoint( -9F, 7.5F, 0F );
		this.Support4 = new ModelRenderer( this, 14, 14 );
		this.Support4.setTextureSize( 128, 128 );
		this.Support4.addBox( -1F, -12F, -1F, 2, 24, 2);
		this.Support4.setRotationPoint( 8.999999F, 7.5F, 2.629008E-13F );
		this.Support5 = new ModelRenderer( this, 15, 15 );
		this.Support5.setTextureSize( 128, 128 );
		this.Support5.addBox( -1F, -1F, -0.5F, 2, 2, 1);
		this.Support5.setRotationPoint( 8.499999F, 20F, 8.940724E-08F );
		this.Support6 = new ModelRenderer( this, 15, 15 );
		this.Support6.setTextureSize( 128, 128 );
		this.Support6.addBox( -1F, -1F, -0.5F, 2, 2, 1);
		this.Support6.setRotationPoint( 7.999999F, -5F, 3.576281E-07F );
		this.Support2 = new ModelRenderer( this, 15, 15 );
		this.Support2.setTextureSize( 128, 128 );
		this.Support2.addBox( -1F, -1F, -0.5F, 2, 2, 1);
		this.Support2.setRotationPoint( -8.5F, 20F, -1.953993E-14F );
		this.Support3 = new ModelRenderer( this, 15, 15 );
		this.Support3.setTextureSize( 128, 128 );
		this.Support3.addBox( -1F, -1F, -0.5F, 2, 2, 1);
		this.Support3.setRotationPoint( -8F, -5F, -1.065814E-14F );
		this.Dust1 = new ModelRenderer( this, 28, 39 );
		this.Dust1.setTextureSize( 128, 128 );
		this.Dust1.addBox( -2.5F, -0.5F, -2.5F, 5, 1, 5);
		this.Dust1.setRotationPoint( 0F, 13F, 0F );
		this.Dust3 = new ModelRenderer( this, 32, 30 );
		this.Dust3.setTextureSize( 128, 128 );
		this.Dust3.addBox( -1.5F, -0.5F, -1.5F, 3, 1, 3);
		this.Dust3.setRotationPoint( 0F, 12F, 0F );
		this.Dust4 = new ModelRenderer( this, 34, 27 );
		this.Dust4.setTextureSize( 128, 128 );
		this.Dust4.addBox( -1F, -0.5F, -1F, 2, 1, 2);
		this.Dust4.setRotationPoint( 0F, 11.5F, 0F );
		this.Dust2 = new ModelRenderer( this, 30, 34 );
		this.Dust2.setTextureSize( 128, 128 );
		this.Dust2.addBox( -2F, -0.5F, -2F, 4, 1, 4);
		this.Dust2.setRotationPoint( 0F, 12.5F, 0F );
		this.Dust5 = new ModelRenderer( this, 36, 25 );
		this.Dust5.setTextureSize( 128, 128 );
		this.Dust5.addBox( -0.5F, -0.5F, -0.5F, 1, 1, 1);
		this.Dust5.setRotationPoint( 0F, 11F, 0F );
		this.Glass1 = new ModelRenderer( this, 73, 16 );
		this.Glass1.setTextureSize( 128, 128 );
		this.Glass1.addBox( -5F, -5F, -0.5F, 10, 10, 1);
		this.Glass1.setRotationPoint( 0F, 7F, -6F );
		this.Glass2 = new ModelRenderer( this, 73, 16 );
		this.Glass2.setTextureSize( 128, 128 );
		this.Glass2.addBox( -6F, -0.5F, -0.5F, 12, 1, 1);
		this.Glass2.setRotationPoint( 0F, 1.5F, -5F );
		this.Glass3 = new ModelRenderer( this, 73, 16 );
		this.Glass3.setTextureSize( 128, 128 );
		this.Glass3.addBox( -6F, -0.5F, -0.5F, 12, 1, 1);
		this.Glass3.setRotationPoint( 0F, 12.5F, -5F );
		this.Glass4 = new ModelRenderer( this, 73, 16 );
		this.Glass4.setTextureSize( 128, 128 );
		this.Glass4.addBox( -0.5F, -5F, -0.5F, 1, 10, 1);
		this.Glass4.setRotationPoint( 5.5F, 7F, -5F );
		this.Glass5 = new ModelRenderer( this, 73, 16 );
		this.Glass5.setTextureSize( 128, 128 );
		this.Glass5.addBox( -0.5F, -5F, -0.5F, 1, 10, 1);
		this.Glass5.setRotationPoint( -5.5F, 7F, -5F );
		this.Glass6 = new ModelRenderer( this, 73, 16 );
		this.Glass6.setTextureSize( 128, 128 );
		this.Glass6.addBox( -6F, -0.5F, -0.5F, 12, 1, 1);
		this.Glass6.setRotationPoint( 0F, 1.5F, 5F );
		this.Glass7 = new ModelRenderer( this, 73, 16 );
		this.Glass7.setTextureSize( 128, 128 );
		this.Glass7.addBox( -6F, -0.5F, -0.5F, 12, 1, 1);
		this.Glass7.setRotationPoint( 0F, 12.5F, 5F );
		this.Glass8 = new ModelRenderer( this, 73, 16 );
		this.Glass8.setTextureSize( 128, 128 );
		this.Glass8.addBox( -0.5F, -5F, -0.5F, 1, 10, 1);
		this.Glass8.setRotationPoint( -5.5F, 7F, 5F );
		this.Glass9 = new ModelRenderer( this, 73, 16 );
		this.Glass9.setTextureSize( 128, 128 );
		this.Glass9.addBox( -0.5F, -5F, -0.5F, 1, 10, 1);
		this.Glass9.setRotationPoint( 5.5F, 7F, 5F );
		this.Glass10 = new ModelRenderer( this, 73, 16 );
		this.Glass10.setTextureSize( 128, 128 );
		this.Glass10.addBox( -5F, -5F, -0.5F, 10, 10, 1);
		this.Glass10.setRotationPoint( -2.842171E-14F, 7F, 6F );
		this.Glass11 = new ModelRenderer( this, 73, 16 );
		this.Glass11.setTextureSize( 128, 128 );
		this.Glass11.addBox( -5F, -5F, -0.5F, 10, 10, 1);
		this.Glass11.setRotationPoint( 6F, 7F, 0F );
		this.Glass12 = new ModelRenderer( this, 73, 16 );
		this.Glass12.setTextureSize( 128, 128 );
		this.Glass12.addBox( -6F, -0.5F, -0.5F, 12, 1, 1);
		this.Glass12.setRotationPoint( 5F, 1.5F, 1.788139E-07F );
		this.Glass13 = new ModelRenderer( this, 73, 16 );
		this.Glass13.setTextureSize( 128, 128 );
		this.Glass13.addBox( -6F, -0.5F, -0.5F, 12, 1, 1);
		this.Glass13.setRotationPoint( 5F, 12.5F, 1.788139E-07F );
		this.Glass14 = new ModelRenderer( this, 73, 16 );
		this.Glass14.setTextureSize( 128, 128 );
		this.Glass14.addBox( -0.5F, -5F, -0.5F, 1, 10, 1);
		this.Glass14.setRotationPoint( 5.000001F, 7F, 5.5F );
		this.Glass15 = new ModelRenderer( this, 73, 16 );
		this.Glass15.setTextureSize( 128, 128 );
		this.Glass15.addBox( -0.5F, -5F, -0.5F, 1, 10, 1);
		this.Glass15.setRotationPoint( 4.999999F, 7F, -5.5F );
		this.Glass16 = new ModelRenderer( this, 73, 16 );
		this.Glass16.setTextureSize( 128, 128 );
		this.Glass16.addBox( -5F, -5F, -0.5F, 10, 10, 1);
		this.Glass16.setRotationPoint( -6F, 7F, 0F );
		this.Glass17 = new ModelRenderer( this, 73, 16 );
		this.Glass17.setTextureSize( 128, 128 );
		this.Glass17.addBox( -6F, -0.5F, -0.5F, 12, 1, 1);
		this.Glass17.setRotationPoint( -5F, 1.5F, 1.788139E-07F );
		this.Glass18 = new ModelRenderer( this, 73, 16 );
		this.Glass18.setTextureSize( 128, 128 );
		this.Glass18.addBox( -6F, -0.5F, -0.5F, 12, 1, 1);
		this.Glass18.setRotationPoint( -5F, 12.5F, 1.788139E-07F );
		this.Glass19 = new ModelRenderer( this, 73, 16 );
		this.Glass19.setTextureSize( 128, 128 );
		this.Glass19.addBox( -0.5F, -5F, -0.5F, 1, 10, 1);
		this.Glass19.setRotationPoint( -4.999999F, 7F, -5.5F );
		this.Glass20 = new ModelRenderer( this, 73, 16 );
		this.Glass20.setTextureSize( 128, 128 );
		this.Glass20.addBox( -0.5F, -5F, -0.5F, 1, 10, 1);
		this.Glass20.setRotationPoint( -5.000001F, 7F, 5.5F );
	}

	@Override
	public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7)
	{
		this.Base1.rotateAngleX = 0F;
		this.Base1.rotateAngleY = 0F;
		this.Base1.rotateAngleZ = 0F;
		this.Base1.renderWithRotation(par7);

		this.Top1.rotateAngleX = 0F;
		this.Top1.rotateAngleY = 0F;
		this.Top1.rotateAngleZ = 0F;
		this.Top1.renderWithRotation(par7);

		this.Top2.rotateAngleX = 0F;
		this.Top2.rotateAngleY = 0F;
		this.Top2.rotateAngleZ = 0F;
		this.Top2.renderWithRotation(par7);

		this.Top3.rotateAngleX = 0F;
		this.Top3.rotateAngleY = 0F;
		this.Top3.rotateAngleZ = 0F;
		this.Top3.renderWithRotation(par7);

		this.Top4.rotateAngleX = 0F;
		this.Top4.rotateAngleY = 0F;
		this.Top4.rotateAngleZ = 0F;
		this.Top4.renderWithRotation(par7);

		this.Base2.rotateAngleX = 0F;
		this.Base2.rotateAngleY = 0F;
		this.Base2.rotateAngleZ = 0F;
		this.Base2.renderWithRotation(par7);

		this.Base3.rotateAngleX = 0F;
		this.Base3.rotateAngleY = 0F;
		this.Base3.rotateAngleZ = 0F;
		this.Base3.renderWithRotation(par7);

		this.Base4.rotateAngleX = 0F;
		this.Base4.rotateAngleY = 0F;
		this.Base4.rotateAngleZ = 0F;
		this.Base4.renderWithRotation(par7);

		this.Base5.rotateAngleX = 0F;
		this.Base5.rotateAngleY = 0F;
		this.Base5.rotateAngleZ = 0F;
		this.Base5.renderWithRotation(par7);

		this.Top5.rotateAngleX = 0F;
		this.Top5.rotateAngleY = 0F;
		this.Top5.rotateAngleZ = 0F;
		this.Top5.renderWithRotation(par7);

		this.Top6.rotateAngleX = 0F;
		this.Top6.rotateAngleY = 0F;
		this.Top6.rotateAngleZ = 0F;
		this.Top6.renderWithRotation(par7);

		this.Top7.rotateAngleX = 0F;
		this.Top7.rotateAngleY = 0F;
		this.Top7.rotateAngleZ = 0F;
		this.Top7.renderWithRotation(par7);

		this.Top8.rotateAngleX = 0F;
		this.Top8.rotateAngleY = 0F;
		this.Top8.rotateAngleZ = 0F;
		this.Top8.renderWithRotation(par7);

		this.Top9.rotateAngleX = 0F;
		this.Top9.rotateAngleY = 0F;
		this.Top9.rotateAngleZ = 0F;
		this.Top9.renderWithRotation(par7);

		this.Handle.rotateAngleX = 1.570796F;
		this.Handle.rotateAngleY = -1.570796F;
		this.Handle.rotateAngleZ = 0F;
		this.Handle.renderWithRotation(par7);

		this.Handle2.rotateAngleX = 1.570796F;
		this.Handle2.rotateAngleY = -1.570796F;
		this.Handle2.rotateAngleZ = 0F;
		this.Handle2.renderWithRotation(par7);

		this.Handle3.rotateAngleX = 5.364417E-07F;
		this.Handle3.rotateAngleY = 1.570796F;
		this.Handle3.rotateAngleZ = 3.141592F;
		this.Handle3.renderWithRotation(par7);

		this.Handle4.rotateAngleX = 5.364417E-07F;
		this.Handle4.rotateAngleY = 1.570796F;
		this.Handle4.rotateAngleZ = 3.141592F;
		this.Handle4.renderWithRotation(par7);

		this.Handle5.rotateAngleX = 1.570796F;
		this.Handle5.rotateAngleY = -1.570796F;
		this.Handle5.rotateAngleZ = 0F;
		this.Handle5.renderWithRotation(par7);

		this.Handle6.rotateAngleX = 0.9599316F;
		this.Handle6.rotateAngleY = 1.570796F;
		this.Handle6.rotateAngleZ = 3.141592F;
		this.Handle6.renderWithRotation(par7);

		this.Handle7.rotateAngleX = 0.9599308F;
		this.Handle7.rotateAngleY = -1.570796F;
		this.Handle7.rotateAngleZ = 1.928537E-07F;
		this.Handle7.renderWithRotation(par7);

		this.Handle8.rotateAngleX = 1.570796F;
		this.Handle8.rotateAngleY = -1.570796F;
		this.Handle8.rotateAngleZ = 0F;
		this.Handle8.renderWithRotation(par7);

		this.Handle9.rotateAngleX = 1.570796F;
		this.Handle9.rotateAngleY = -1.570796F;
		this.Handle9.rotateAngleZ = 0F;
		this.Handle9.renderWithRotation(par7);

		this.Handle10.rotateAngleX = 1.570796F;
		this.Handle10.rotateAngleY = -1.570796F;
		this.Handle10.rotateAngleZ = 0F;
		this.Handle10.renderWithRotation(par7);

		this.Support.rotateAngleX = 0F;
		this.Support.rotateAngleY = -1.570796F;
		this.Support.rotateAngleZ = 0F;
		this.Support.renderWithRotation(par7);

		this.Support4.rotateAngleX = 0F;
		this.Support4.rotateAngleY = -1.570796F;
		this.Support4.rotateAngleZ = 0F;
		this.Support4.renderWithRotation(par7);

		this.Support5.rotateAngleX = 1.570796F;
		this.Support5.rotateAngleY = -1.570796F;
		this.Support5.rotateAngleZ = 0F;
		this.Support5.renderWithRotation(par7);

		this.Support6.rotateAngleX = 1.570796F;
		this.Support6.rotateAngleY = -1.570796F;
		this.Support6.rotateAngleZ = 0F;
		this.Support6.renderWithRotation(par7);

		this.Support2.rotateAngleX = 1.570796F;
		this.Support2.rotateAngleY = -1.570796F;
		this.Support2.rotateAngleZ = 0F;
		this.Support2.renderWithRotation(par7);

		this.Support3.rotateAngleX = 1.570796F;
		this.Support3.rotateAngleY = -1.570796F;
		this.Support3.rotateAngleZ = 0F;
		this.Support3.renderWithRotation(par7);

		this.Dust1.rotateAngleX = 0F;
		this.Dust1.rotateAngleY = 0F;
		this.Dust1.rotateAngleZ = 0F;
		this.Dust1.renderWithRotation(par7);

		this.Dust3.rotateAngleX = 0F;
		this.Dust3.rotateAngleY = 0F;
		this.Dust3.rotateAngleZ = 0F;
		this.Dust3.renderWithRotation(par7);

		this.Dust4.rotateAngleX = 0F;
		this.Dust4.rotateAngleY = 0F;
		this.Dust4.rotateAngleZ = 0F;
		this.Dust4.renderWithRotation(par7);

		this.Dust2.rotateAngleX = 0F;
		this.Dust2.rotateAngleY = 0F;
		this.Dust2.rotateAngleZ = 0F;
		this.Dust2.renderWithRotation(par7);

		this.Dust5.rotateAngleX = 0F;
		this.Dust5.rotateAngleY = 0F;
		this.Dust5.rotateAngleZ = 0F;
		this.Dust5.renderWithRotation(par7);

		this.Glass1.rotateAngleX = 0F;
		this.Glass1.rotateAngleY = 0F;
		this.Glass1.rotateAngleZ = 0F;
		this.Glass1.renderWithRotation(par7);

		this.Glass2.rotateAngleX = 0F;
		this.Glass2.rotateAngleY = 0F;
		this.Glass2.rotateAngleZ = 0F;
		this.Glass2.renderWithRotation(par7);

		this.Glass3.rotateAngleX = 0F;
		this.Glass3.rotateAngleY = 0F;
		this.Glass3.rotateAngleZ = 0F;
		this.Glass3.renderWithRotation(par7);

		this.Glass4.rotateAngleX = 0F;
		this.Glass4.rotateAngleY = 0F;
		this.Glass4.rotateAngleZ = 0F;
		this.Glass4.renderWithRotation(par7);

		this.Glass5.rotateAngleX = 0F;
		this.Glass5.rotateAngleY = 0F;
		this.Glass5.rotateAngleZ = 0F;
		this.Glass5.renderWithRotation(par7);

		this.Glass6.rotateAngleX = 0F;
		this.Glass6.rotateAngleY = -3.141592F;
		this.Glass6.rotateAngleZ = 0F;
		this.Glass6.renderWithRotation(par7);

		this.Glass7.rotateAngleX = 0F;
		this.Glass7.rotateAngleY = -3.141592F;
		this.Glass7.rotateAngleZ = 0F;
		this.Glass7.renderWithRotation(par7);

		this.Glass8.rotateAngleX = 0F;
		this.Glass8.rotateAngleY = -3.141592F;
		this.Glass8.rotateAngleZ = 0F;
		this.Glass8.renderWithRotation(par7);

		this.Glass9.rotateAngleX = 0F;
		this.Glass9.rotateAngleY = -3.141592F;
		this.Glass9.rotateAngleZ = 0F;
		this.Glass9.renderWithRotation(par7);

		this.Glass10.rotateAngleX = -6.283185F;
		this.Glass10.rotateAngleY = 0F;
		this.Glass10.rotateAngleZ = 0F;
		this.Glass10.renderWithRotation(par7);

		this.Glass11.rotateAngleX = 0F;
		this.Glass11.rotateAngleY = -1.570796F;
		this.Glass11.rotateAngleZ = 0F;
		this.Glass11.renderWithRotation(par7);

		this.Glass12.rotateAngleX = 0F;
		this.Glass12.rotateAngleY = -1.570796F;
		this.Glass12.rotateAngleZ = 0F;
		this.Glass12.renderWithRotation(par7);

		this.Glass13.rotateAngleX = 0F;
		this.Glass13.rotateAngleY = -1.570796F;
		this.Glass13.rotateAngleZ = 0F;
		this.Glass13.renderWithRotation(par7);

		this.Glass14.rotateAngleX = 0F;
		this.Glass14.rotateAngleY = -1.570796F;
		this.Glass14.rotateAngleZ = 0F;
		this.Glass14.renderWithRotation(par7);

		this.Glass15.rotateAngleX = 0F;
		this.Glass15.rotateAngleY = -1.570796F;
		this.Glass15.rotateAngleZ = 0F;
		this.Glass15.renderWithRotation(par7);

		this.Glass16.rotateAngleX = 0F;
		this.Glass16.rotateAngleY = 1.570796F;
		this.Glass16.rotateAngleZ = 0F;
		this.Glass16.renderWithRotation(par7);

		this.Glass17.rotateAngleX = 0F;
		this.Glass17.rotateAngleY = 1.570796F;
		this.Glass17.rotateAngleZ = 0F;
		this.Glass17.renderWithRotation(par7);

		this.Glass18.rotateAngleX = 0F;
		this.Glass18.rotateAngleY = 1.570796F;
		this.Glass18.rotateAngleZ = 0F;
		this.Glass18.renderWithRotation(par7);

		this.Glass19.rotateAngleX = 0F;
		this.Glass19.rotateAngleY = 1.570796F;
		this.Glass19.rotateAngleZ = 0F;
		this.Glass19.renderWithRotation(par7);

		this.Glass20.rotateAngleX = 0F;
		this.Glass20.rotateAngleY = 1.570796F;
		this.Glass20.rotateAngleZ = 0F;
		this.Glass20.renderWithRotation(par7);

	}

}
