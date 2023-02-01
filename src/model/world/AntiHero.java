package model.world;

import model.effects.Effect;
import model.effects.Stun;

import java.util.ArrayList;

public class AntiHero extends Champion {

	public AntiHero(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}

	//New
	public void useLeaderAbility(ArrayList<Champion> targets) {
		for(Champion c : targets){
			ArrayList<Effect> effects = c.getAppliedEffects();
			Stun stun  = new Stun(2);
			stun.apply(c);
			effects.add(stun);
		}
	}
}
