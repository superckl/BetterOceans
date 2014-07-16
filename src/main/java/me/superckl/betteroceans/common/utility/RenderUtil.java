package me.superckl.betteroceans.common.utility;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class RenderUtil {

	public static void drawTexturedRect(final ResourceLocation texture, final double x, final double y, final int u, final int v, final int width, final int height, final int imageWidth, final int imageHeight, final double scale) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		final double minU = (double)u / (double)imageWidth;
		final double maxU = (double)(u + width) / (double)imageWidth;
		final double minV = (double)v / (double)imageHeight;
		final double maxV = (double)(v + height) / (double)imageHeight;
		final Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + scale*width, y + scale*height, 0, maxU, maxV);
		tessellator.addVertexWithUV(x + scale*width, y, 0, maxU, minV);
		tessellator.addVertexWithUV(x, y, 0, minU, minV);
		tessellator.addVertexWithUV(x, y + scale*height, 0, minU, maxV);
		tessellator.draw();
	}

}
