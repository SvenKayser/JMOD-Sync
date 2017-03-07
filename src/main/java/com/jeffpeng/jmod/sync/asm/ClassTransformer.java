package com.jeffpeng.jmod.sync.asm;

import java.util.Iterator;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassTransformer implements IClassTransformer{

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (name.equalsIgnoreCase("sync.common.Sync"))
			return patchSync(basicClass);
		return basicClass;
	}
	
		private byte[] patchSync(byte[] basicClass){
		/*
		 * This patches Sync so it does not add recipes on server startup - if so desired.
		 */
		String method = "mapHardmodeRecipe";
		ClassNode cN = new ClassNode();
		ClassReader cR = new ClassReader(basicClass);
		cR.accept(cN, 0);
		Iterator<MethodNode> methods = cN.methods.iterator();
		while(methods.hasNext()){
			MethodNode mN = methods.next();
			if(mN.name.equals(method)){
				LabelNode lblnde = new LabelNode();
				AbstractInsnNode aIN = mN.instructions.getFirst();
				InsnList inject = new InsnList();
				inject.add(new FieldInsnNode(Opcodes.GETSTATIC,"com/jeffpeng/jmod/sync/Scripting","preventSyncRecipeReload","Z"));
				inject.add(new JumpInsnNode(Opcodes.IFEQ,	lblnde));
				inject.add(new InsnNode(Opcodes.RETURN));
				inject.add(lblnde);
				mN.instructions.insertBefore(aIN, inject);
				
			}
		}
		ClassWriter cW = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		cN.accept(cW);
		System.out.println("[JMOD ASM] sync.common.Sync");
		System.out.println("[JMOD ASM] This prevents Sync from adding the recipe for Sync:Sync_ItemPlaceholder on server start.");
		
		return cW.toByteArray();		
	}
}
