package me.superckl.betteroceans.asm;

import me.superckl.betteroceans.common.utility.BOReflectionHelper;
import me.superckl.betteroceans.common.utility.CollectionHelper;
import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ClassTransformer implements IClassTransformer{

	@Override
	public byte[] transform(final String name, final String transformedName, final byte[] bytes) {
		if(CollectionHelper.find(name, BOReflectionHelper.class_biomeGenBase) != -1){
			final ClassNode cNode = new ClassNode();
			final ClassReader reader = new ClassReader(bytes);
			reader.accept(cNode, 0);
			MethodNode mNode = null;
			for(final MethodNode node:cNode.methods)
				if(CollectionHelper.find(node.name, BOReflectionHelper.method_genBiomeTerrain) != -1 && CollectionHelper.find(node.desc, BOReflectionHelper.desc_genBiomeTerrain) != -1){
					mNode = node;
					break;
				}
			if(mNode == null){
				BODummyModContainer.logger.error("Failed to find method in BiomeGenBase to patch! Ye who continue now abandon all hope.");
				return bytes;
			}
			int fixed = 0;
			for(AbstractInsnNode node:mNode.instructions.toArray())
				if(node instanceof FieldInsnNode){
					int index;
					final FieldInsnNode vNode = (FieldInsnNode) node;
					if(CollectionHelper.find(vNode.name, BOReflectionHelper.field_water) != -1){
						node = vNode.getNext();
						if(node instanceof VarInsnNode && ((VarInsnNode)node).var == 10){

							final InsnList list = new InsnList();
							list.add(new VarInsnNode(Opcodes.ALOAD, 0));
							list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/superckl/betteroceans/common/utility/BiomeHelper", "getWaterBlockFor", "(Lnet/minecraft/world/biome/BiomeGenBase;)Lnet/minecraft/block/Block;"));

							mNode.instructions.insert(vNode, list);
							mNode.instructions.remove(vNode);

							BODummyModContainer.logger.debug("Patched "+name+"."+mNode.name);
							BODummyModContainer.logger.info("Saltwater injected into top layer.");
							fixed++;
						}
					}else if((index = CollectionHelper.find(vNode.name, BOReflectionHelper.field_gravel)) != -1){
						vNode.name = BOReflectionHelper.field_sand[index];
						vNode.desc = "Lnet/minecraft/block/BlockSand;";
						BODummyModContainer.logger.debug("Patched "+name+"."+mNode.name);
						BODummyModContainer.logger.info("Sand injected into bottom layer.");
						fixed++;
					}
					if(fixed >= 2)
						break;
				}
			if(fixed < 2)
				BODummyModContainer.logger.error("Failed to patch BiomeGenBase! Ye who continue now abandon all hope.");
			else
				BODummyModContainer.logger.info("Sucessfully patched BiomeGenBase!");
			final ClassWriter cWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			cNode.accept(cWriter);
			return cWriter.toByteArray();
		}else if(CollectionHelper.find(name, BOReflectionHelper.class_chunkProviderGenerate) != -1){
			final ClassNode cNode = new ClassNode();
			final ClassReader reader = new ClassReader(bytes);
			reader.accept(cNode, 0);
			MethodNode mNode = null;
			for(final MethodNode node:cNode.methods)
				if(CollectionHelper.find(node.name, BOReflectionHelper.method_func_147424_a) != -1 && CollectionHelper.find(node.desc, BOReflectionHelper.desc_func_147424_a) != -1){
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
					int index;
					if((index = CollectionHelper.find(vNode.name, BOReflectionHelper.field_water)) != -1){

						final InsnList list = new InsnList();
						list.add(new VarInsnNode(Opcodes.ALOAD, 0));
						list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/world/gen/ChunkProviderGenerate", BOReflectionHelper.field_worldObj[index], "Lnet/minecraft/world/World;"));
						list.add(new VarInsnNode(Opcodes.ILOAD, 44));
						list.add(new VarInsnNode(Opcodes.ILOAD, 1));
						list.add(new VarInsnNode(Opcodes.ILOAD, 2));
						list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "me/superckl/betteroceans/common/utility/BiomeHelper", "getWaterBlockFor", "(Lnet/minecraft/world/World;III)Lnet/minecraft/block/Block;"));

						mNode.instructions.insert(vNode, list);
						mNode.instructions.remove(vNode);

						BODummyModContainer.logger.debug("Patched "+name+"."+mNode.name);
						BODummyModContainer.logger.info("Saltwater injected into base layer.");
						fixed = true;
						break;
					}
				}
			if(!fixed)
				BODummyModContainer.logger.error("Failed to patch ChunkProviderGenerate! Ye who continue now abandon all hope.");
			else
				BODummyModContainer.logger.info("Sucessfully patched ChunkProviderGenerate!");
			final ClassWriter cWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			cNode.accept(cWriter);
			return cWriter.toByteArray();
		}
		return bytes;
	}

}
