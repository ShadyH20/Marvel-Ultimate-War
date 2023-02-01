package model.effects;

import model.world.Champion;

public class Shield extends Effect {

	public Shield( int duration) {
		super("Shield", duration, EffectType.BUFF);
		
	}
	
	//New
	
	public void apply(Champion c){

		c.incrementSpeed(2);

	}
	
	public void remove(Champion c) {

		//c.getAppliedEffects().remove(this);
		
		c.decrementSpeed(2);
		
	}


}
