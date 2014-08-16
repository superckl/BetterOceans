package me.superckl.betteroceans.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.SortingIndex;

@SortingIndex(2000)
@MCVersion("1.7.10")
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
