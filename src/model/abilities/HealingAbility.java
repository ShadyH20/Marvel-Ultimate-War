package model.abilities;

import model.world.Champion;
import model.world.Damageable;

import java.util.ArrayList;

public  class HealingAbility extends Ability {
	private int healAmount;

	public HealingAbility(String name,int cost, int baseCoolDown, int castRadius, AreaOfEffect area,int required, int healingAmount) {
		super(name,cost, baseCoolDown, castRadius, area,required);
		this.healAmount = healingAmount;
	}

	public int getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}
	
	// Milestone 2
	
	public void execute(ArrayList<Damageable> targets) {
		for(Damageable d: targets) {
			Champion c=(Champion) d;
			c.heal(this.getHealAmount());
				
		}
		
		
	}
	

	

	

}
