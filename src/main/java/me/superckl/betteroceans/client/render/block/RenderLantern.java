package me.superckl.betteroceans.client.render.block;

import me.superckl.betteroceans.client.model.block.ModelBOLantern;
import me.superckl.betteroceans.common.entity.tile.TileEntityLantern;
import me.superckl.betteroceans.common.reference.ModItems;
import me.superckl.betteroceans.common.reference.RenderData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderLantern extends TileEntitySpecialRenderer implements IItemRenderer{

	private final ModelBOLantern model = new ModelBOLantern();
	private final Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void renderTileEntityAt(final TileEntity te, final double x,
			final double y, final double z, final float f) {
		if(te instanceof TileEntityLantern == false)
			return;
		this.renderLantern((float) x, (float) y, (float) z, ((TileEntityLantern)te).getLumPowder().getItemDamage(), ((TileEntityLantern)te).getRotation());

	}

	public void renderLantern(final float x, final float y, final float z, final int meta, final float rotation){
		GL11.glPushMatrix();
		GL11.glTranslatef(x + 0.5F, y+.6F, z + 0.5F);
		GL11.glRotatef(180F, rotation, 0.0F, 1.0F);
		GL11.glScalef(0.4F, 0.4F, 0.4F);
		GL11.glEnable(GL11.GL_BLEND);
		this.mc.renderEngine.bindTexture(RenderData.LANTERN[meta]);
		this.model.render(null, 0F, 0F, 0F, 0F, 0F, RenderData.pixel);
		GL11.glPopMatrix();
	}

	@Override
	public boolean handleRenderType(final ItemStack item, final ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(final ItemRenderType type, final ItemStack item,
			final ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(final ItemRenderType type, ItemStack item, final Object... data) {
		item = new ItemStack(ModItems.lumPowder, 1, item.getItemDamage());
		switch(type){
		case ENTITY:
		{
			GL11.glPushMatrix();
			GL11.glScalef(2F, 2F, 2F);
			this.renderLantern(-0.5F, 0.0F, -0.5F, item.getItemDamage(), 0F);
			GL11.glPopMatrix();
			break;
		}
		case EQUIPPED:
		{
			this.renderLantern(0.0F, 0.0F, 0.0F, item.getItemDamage(), 0F);
			break;
		}
		case EQUIPPED_FIRST_PERSON:
		{
			this.renderLantern(0.0F, 0.0F, 0.0F, item.getItemDamage(), 0F);
			break;
		}
		case INVENTORY:
		{
			this.renderLantern(0.0F, 0.0F, 0.0F, item.getItemDamage(), 0F);
			break;
		}
		default:
			break;
		}
	}

}
