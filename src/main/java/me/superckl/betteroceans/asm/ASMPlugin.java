package me.superckl.betteroceans.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@SortingIndex(2000)
@TransformerExclusions("me.superckl.betteroceans")
@MCVersion("1.7.10")
@Name("Better Oceans Core")
public class ASMPlugin implements IFMLLoadingPlugin{

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {ClassTransformer.class.getCanonicalName()};
	}

	@Override
	public String getModContainerClass() {
		return BODummyModContainer.class.getCanonicalName();
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(final Map<String, Object> data) {}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
