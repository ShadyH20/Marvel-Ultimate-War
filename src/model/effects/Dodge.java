package model.effects;

import model.world.Champion;

public class Dodge extends Effect {

	public Dodge(int duration) {
		super("Dodge", duration, EffectType.BUFF);
		
	}
	
	//New 
	
	public void apply(Champion c){
				
		c.incrementSpeed(5);
		
	}
	
	public void remove(Champion c) {

//		c.getAppliedEffects().remove(this);

		c.decrementSpeed(5);

	}
	
}
