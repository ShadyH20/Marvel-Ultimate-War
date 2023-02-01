package model.effects;

import model.abilities.Ability;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;

public class PowerUp extends Effect {
	

	public PowerUp(int duration) {
		super("PowerUp", duration, EffectType.BUFF);
		
	}
	
	//New
	
	public void apply(Champion c){

		for(Ability a : c.getAbilities() ) {
			if(a instanceof HealingAbility ha) {
				int HealAmount = ha.getHealAmount();
				int NewHealAmount = (int) (HealAmount*1.2);
				ha.setHealAmount(NewHealAmount);
			}
			else if(a instanceof DamagingAbility da){
				int DamageAmount = da.getDamageAmount();
				int NewDamageAmount = (int) (DamageAmount*1.2);
				da.setDamageAmount(NewDamageAmount);
			}
		}
	}
	
	public void remove(Champion c) {

		//c.getAppliedEffects().remove(this);
			
		for(Ability a : c.getAbilities() ) {
			if(a instanceof HealingAbility ha) {
				int HealAmount = ha.getHealAmount();
				int NewHealAmount = (int) (HealAmount/1.2);
				ha.setHealAmount(NewHealAmount);
			}
			else if(a instanceof DamagingAbility da){
				int DamageAmount = da.getDamageAmount();
				int NewDamageAmount = (int) (DamageAmount/1.2);
				da.setDamageAmount(NewDamageAmount);
			}
		}
	}
		
}
