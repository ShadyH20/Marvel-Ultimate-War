package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Root extends Effect {

	public Root( int duration) {
		super("Root", duration, EffectType.DEBUFF);
		
	}
	
	//New
	
	public void apply(Champion c){

		if(c.getCondition().equals(Condition.ACTIVE))
			c.setCondition(Condition.ROOTED);

	}
	
	public void remove(Champion c) {
		
//		c.getAppliedEffects().remove(this);
		
		boolean found = false; // if there is a Stun or another Root
		
		for(Effect effect : c.getAppliedEffects()) {
			if(effect instanceof Root) {
				found = true;
				break;
			}
		}
		if(!c.getCondition().equals(Condition.INACTIVE) && !found)
			c.setCondition(Condition.ACTIVE);
		if(found && !c.getCondition().equals(Condition.INACTIVE)) {
			c.setCondition(Condition.ROOTED);
		}
	}

}
