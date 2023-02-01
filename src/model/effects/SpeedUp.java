package model.effects;

import model.world.Champion;

public class SpeedUp extends Effect{

	public SpeedUp(int duration) {
		super("SpeedUp",duration,EffectType.BUFF);
	}
	
	//New
	
	public void apply(Champion c){

		c.incrementSpeed(15);
		
		c.incrementCurrentActionPoints(1);
		
		c.incrementMaxActionPoints(1);
		
	}
	
	public void remove(Champion c) {

		//c.getAppliedEffects().remove(this);
		
		c.decrementSpeed(15);
		
		c.decrementCurrentActionPoints(1);
		
		c.decrementMaxActionPoints(1);
	}

}
