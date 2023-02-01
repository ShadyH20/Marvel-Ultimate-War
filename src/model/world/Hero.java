package model.world;


import model.effects.Effect;
import model.effects.EffectType;
import model.effects.Embrace;

import java.util.ArrayList;

public class Hero extends Champion {

	public Hero(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}

	//New
	public void useLeaderAbility(ArrayList<Champion> targets){
		//Negative Effects: Debuff

		//Remove all negative effects & add embrace effect to all target Champions
		for(Champion c : targets){
			//Remove negative effects
			ArrayList<Effect> effects = c.getAppliedEffects();
			int size = effects.size();
			for (int i = size-1 ; i >= 0 ; i--) {
				Effect e = effects.get(i);
				if(e.getType().equals(EffectType.DEBUFF)){
					effects.remove(e);
					e.remove(c);
				}
			}

			//Add embrace effect for 2 turns
			Embrace embrace = new Embrace(2);
			embrace.apply(c);
			effects.add(embrace);
		}
	}

}
