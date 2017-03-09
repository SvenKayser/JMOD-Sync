package com.jeffpeng.jmod.sync;

import com.jeffpeng.jmod.JMODRepresentation;
import com.jeffpeng.jmod.primitives.ModScriptObject;

public class Scripting extends ModScriptObject {
	
	public static boolean preventSyncRecipeReload = false;

	public Scripting(JMODRepresentation owner) {
		super(owner);
	}
	
	public void preventRecipeReload(boolean value){
		if(owner.testForMod("Sync")) preventSyncRecipeReload |= value;
	}

}
