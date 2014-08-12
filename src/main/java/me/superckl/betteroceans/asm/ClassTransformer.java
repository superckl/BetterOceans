package me.superckl.betteroceans.asm;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions("me.superckl.betteroceans")
public class ClassTransformer implements IClassTransformer{

	@Override
	public byte[] transform(final String name, final String transformedName, final byte[] bytes) {
		if(ClassTransformer.find(name, ClassTransformer.class_biomeGenBase) != -1){
			final ClassNode cNode = new ClassNode();
			final ClassReader reader = new ClassReader(bytes);
			reader.accept(cNode, 0);
			MethodNode mNode = null;
			for(final MethodNode node:cNode.methods)
				if(ClassTransformer.find(node.name, ClassTransformer.method_genBiomeTerrain) != -1 && ClassTransformer.find(node.desc, ClassTransformer.desc_genBiomeTerrain) != -1){
					mNode = node;
					break;
				}
			if(mNode == null){
				BODummyModContainer.logger.error("Failed to find method in BiomeGenBase to patch! Ye who continue now abandon all hope.");
				return bytes;
			}
			boolean fixed = false;
			for(AbstractInsnNode node:mNode.instructions.toArray())
				if(node instanceof FieldInsnNode){
					final FieldInsnNode vNode = (FieldInsnNode) node;
					if(ClassTransformer.find(vNode.name, ClassTransformer.field_blockWater) != -1){
						node = vNode.getNext();
						if(node instanceof VarInsnNode && ((VarInsnNode)node).var == 10){
							vNode.owner = "me/superckl/betteroceans/common/reference/ModBlocks";
							vNode.name = "saltWater";
							vNode.desc = "Lme/superckl/betteroceans/common/fluid/block/BlockFluidSaltWater;";
							BODummyModContainer.logger.debug("Patched "+name+"."+mNode.name);
							BODummyModContainer.logger.info("Saltwater injected into top layer.");
							fixed = true;
							break;
						}
					}
				}
			if(!fixed)
				BODummyModContainer.logger.error("Failed to patch BiomeGenBase! Ye who continue now abandon all hope.");
			final ClassWriter cWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			cNode.accept(cWriter);
			return cWriter.toByteArray();
		}else if(ClassTransformer.find(name, ClassTransformer.class_chunkProviderGenerate) != -1){
			final ClassNode cNode = new ClassNode();
			final ClassReader reader = new ClassReader(bytes);
			reader.accept(cNode, 0);
			MethodNode mNode = null;
			//FMLRelaunchLog.warning("%s", Arrays.toString(cNode.methods.toArray()));
			for(final MethodNode node:cNode.methods)
				if(ClassTransformer.find(node.name, ClassTransformer.method_func_147424_a) != -1 && ClassTransformer.find(node.desc, ClassTransformer.desc_func_147424_a) != -1){
					mNode = node;
					break;
				}
			if(mNode == null){
				BODummyModContainer.logger.error("Failed to find method in ChunkProviderGenerate to patch! Ye who continue now abandon all hope.");
				return bytes;
			}
			boolean fixed = false;
			for(final AbstractInsnNode node:mNode.instructions.toArray())
				if(node instanceof FieldInsnNode){
					final FieldInsnNode vNode = (FieldInsnNode) node;
					if(ClassTransformer.find(vNode.name, ClassTransformer.field_blockWater) != -1){
						vNode.owner = "me/superckl/betteroceans/common/reference/ModBlocks";
						vNode.name = "saltWater";
						vNode.desc = "Lme/superckl/betteroceans/common/fluid/block/BlockFluidSaltWater;";
						BODummyModContainer.logger.debug("Patched "+name+"."+mNode.name);
						BODummyModContainer.logger.info("Saltwater injected into base layer.");
						fixed = true;
						break;
					}
				}
			if(!fixed)
				BODummyModContainer.logger.error("Failed to patch ChunkProviderGenerate! Ye who continue now abandon all hope.");
			final ClassWriter cWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			cNode.accept(cWriter);
			return cWriter.toByteArray();
		}
		return bytes;
	}

	public static <T> int find(final T toFind, final T[] in){
		for(int i = 0; i < in.length; i++)
			if(in[i] == toFind || in[i].equals(toFind))
				return i;
		return -1;
	}

	public static final String[] field_ocean = {"ocean", "field_76771_b"};
	public static final String[] field_deepOcean = {"deepOcean", "field_150575_M"};
	public static final String[] field_blockWater = {"water", "field_150355_j"};

	public static final String[] method_genBiomeTerrain = {"genBiomeTerrain", "func_150560_b"};
	public static final String[] method_func_147424_a = {"func_147424_a"};

	public static final String[] desc_genBiomeTerrain = {"(Lnet/minecraft/world/World;Ljava/util/Random;[Lnet/minecraft/block/Block;[BIID)V", "(Lahb;Ljava/util/Random;[Laji;[BIID)V"};
	public static final String[] desc_func_147424_a = {"(II[Lnet/minecraft/block/Block;)V", "(II[Laji;)V"};

	public static final String[] class_biomeGenBase = {"net.minecraft.world.biome.BiomeGenBase", "ahu"};
	public static final String[] class_chunkProviderGenerate = {"net.minecraft.world.gen.ChunkProviderGenerate", "aqz"};

}
