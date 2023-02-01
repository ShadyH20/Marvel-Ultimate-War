package model.abilities;

import model.effects.Effect;
import model.world.Champion;
import model.world.Damageable;

import java.util.ArrayList;
public class CrowdControlAbility extends Ability {
	private Effect effect;

	public CrowdControlAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area, int required,
			Effect effect) {
		super(name, cost, baseCoolDown, castRadius, area, required);
		this.effect = effect;

	}

	public Effect getEffect() {
		return effect;
	}
	
	
	// Milestone 2
	
	public void execute(ArrayList<Damageable> targets)throws CloneNotSupportedException {
		
		for(Damageable d: targets) {
	
				Champion c = (Champion)d;
				//clone
				Effect eff=this.getEffect();
				Effect effect = (Effect)eff.clone(); 
				c.getAppliedEffects().add(effect);
				eff.apply(c);
										
			
		}
		
	}
	
	

}
