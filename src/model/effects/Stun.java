package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Stun extends Effect {

	public Stun(int duration) {
		super("Stun", duration, EffectType.DEBUFF);
	}
	
	//New 
	
	public void apply(Champion c) {
		
//		Effect effect = (Effect)this.clone();
//		Stun stun = (Stun)effect;
//		c.getAppliedEffects().add(stun);

		c.setCondition(Condition.INACTIVE);

	}
	
	public void remove(Champion c) {

		//c.getAppliedEffects().remove(this);
		
		boolean foundR = false;
		boolean foundS = false;

		for(Effect effect : c.getAppliedEffects()) {
			if(effect instanceof Root)
				foundR = true;
			if(effect instanceof Stun)
				foundS = true;
		}

		if(foundS)
			c.setCondition(Condition.INACTIVE);
		else if(foundR)
			c.setCondition(Condition.ROOTED);
		else
			c.setCondition(Condition.ACTIVE);
		
	}

}
