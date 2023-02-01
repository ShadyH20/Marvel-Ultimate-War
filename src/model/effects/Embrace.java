package model.effects;

import model.world.Champion;

public class Embrace extends Effect {
	

	public Embrace(int duration) {
		super("Embrace", duration, EffectType.BUFF);
	}
	
	//New 
	
	public void apply(Champion c){

		//Permanently!
		int MaxHP = c.getMaxHP();
		int Incremented_value = (int) (MaxHP*0.2);
		c.incrementCurrentHP(Incremented_value);
		
		//Permanently!
		c.incrementMana(20);		
		
		c.incrementSpeed(20);
		
		c.incrementNormalAttack(20);
		
	}
	
	public void remove(Champion c) {

		//c.getAppliedEffects().remove(this);

		c.decrementSpeed(20);

		c.decrementNormalAttack(20);
		
	}

}
