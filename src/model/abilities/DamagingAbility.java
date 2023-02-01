package model.abilities;

import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;

import java.util.ArrayList;

public class DamagingAbility extends Ability {
	
	private int damageAmount;
	public DamagingAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area,int required,int damageAmount) {
		super(name, cost, baseCoolDown, castRadius, area,required);
		this.damageAmount=damageAmount;
	}
	public int getDamageAmount() {
		return damageAmount;
	}
	public void setDamageAmount(int damageAmount) {
		this.damageAmount = damageAmount;
	}
	
	// Milestone 2
	
	public void execute(ArrayList<Damageable> targets) {
		for(Damageable champ : targets) {
			if(champ instanceof Champion) {
				Champion c = (Champion)champ;
				if(c.containShield()) {
					continue;
				}
//				else if(c.dodge()) {
//					continue;
//				}
				else {
					c.damage(this.damageAmount);
				}
			}
			else if(champ instanceof Cover) {
				champ.damage(this.damageAmount);
			}
		}
	}
	
	

}
