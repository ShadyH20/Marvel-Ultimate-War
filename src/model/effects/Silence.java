package model.effects;

import model.world.Champion;

public class Silence extends Effect {

	public Silence( int duration) {
		super("Silence", duration, EffectType.DEBUFF);
		
	}
	
	//New
	
	public void apply(Champion c) {

		c.incrementCurrentActionPoints(2);

		c.incrementMaxActionPoints(2);
		
	}
	
	public void remove(Champion c) {

		//c.getAppliedEffects().remove(this);
		
		c.decrementCurrentActionPoints(2);
		
		c.decrementMaxActionPoints(2);
	}
	
}
