package model.world;


import java.util.ArrayList;

public class Villain extends Champion {

	public Villain(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}

	//New
	public void useLeaderAbility(ArrayList<Champion> targets) {
		for(Champion c : targets){
			//Knockout
			c.setCurrentHP(0);
			c.setCondition(Condition.KNOCKEDOUT);
		}
	}


}
