package model.effects;

import model.world.Champion;



public class Shock extends Effect {

	public Shock(int duration) {
		super("Shock", duration, EffectType.DEBUFF);
		
	}
	
	//New
	
		public void apply(Champion c){
			
//			Effect effect = (Effect)this.clone();
//			Shock shock = (Shock)effect;
//			c.getAppliedEffects().add(shock);
;
			
			int Speed = c.getSpeed();
			int NewSpeed = (int) (Speed*0.9);
			c.setSpeed(NewSpeed);
			
			int NormalAttack = c.getAttackDamage();
			int NewNormalAttack = (int) (NormalAttack*0.9);
			c.setAttackDamage(NewNormalAttack);
			
			c.decrementCurrentActionPoints(1);
			
			c.decrementMaxActionPoints(1);
		
	}
	
	public void remove(Champion c) {

		//c.getAppliedEffects().remove(this);
		
		int Speed = c.getSpeed();
		int NewSpeed = (int) (Speed/0.9);
		c.setSpeed(NewSpeed);
		
		int NormalAttack = c.getAttackDamage();
		int NewNormalAttack = (int) (NormalAttack/0.9);
		c.setAttackDamage(NewNormalAttack);
		
		c.incrementCurrentActionPoints(1);
		
		c.incrementMaxActionPoints(1);
		
	}

}
