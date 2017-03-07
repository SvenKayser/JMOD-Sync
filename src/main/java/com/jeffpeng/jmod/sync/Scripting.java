package com.jeffpeng.jmod.sync;

import com.jeffpeng.jmod.JMODRepresentation;
import com.jeffpeng.jmod.primitives.OwnedObject;

public class Scripting extends OwnedObject {
	
	public static boolean preventSyncRecipeReload = false;

	public Scripting(JMODRepresentation owner) {
		super(owner);
	}
	
	public void preventRecipeReload(boolean value){
		if(owner.testForMod("Sync")) preventSyncRecipeReload |= value;
	}

}
