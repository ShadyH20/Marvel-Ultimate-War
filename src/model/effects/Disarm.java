package model.effects;

import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.DamagingAbility;
import model.world.Champion;

public class Disarm extends Effect {
	

	public Disarm( int duration) {
		super("Disarm", duration, EffectType.DEBUFF);
		
	}
	
	//New
	
	public void apply(Champion c){

		DamagingAbility dmg = new DamagingAbility("Punch",0,1,1,AreaOfEffect.SINGLETARGET,1,50);

		c.getAbilities().add(dmg);
		
	}
	
	public void remove(Champion c) {

		//c.getAppliedEffects().remove(this);

		// Note : We do need only remove any of the " Punch " abilities does not matter their order
		Ability RequiredAbility = null ;

		for(Ability ability : c.getAbilities()) {
			if(ability instanceof DamagingAbility) {
				if(ability.getName().equals("Punch")) {
					RequiredAbility = ability; // get its reference type
					break; // we need to remove one only
				}
			}
		}

		c.getAbilities().remove(RequiredAbility);

		
	}
	
}
