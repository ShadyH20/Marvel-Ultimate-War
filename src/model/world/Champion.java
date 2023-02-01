package model.world;

import engine.GameListener;
import model.abilities.Ability;
import model.effects.*;
import view.game_scene.AnimationListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

abstract public class Champion implements Comparable , Damageable{
	private String name;
	private int maxHP;
	private int currentHP;
	private int mana;
	private int maxActionPointsPerTurn;
	private int currentActionPoints;
	private int attackRange;
	private int attackDamage; // Normal Attack
	private int speed;
	private ArrayList<Ability> abilities;
	private ArrayList<Effect> appliedEffects;
	private Condition condition;
	private Point location;
	private DamageableListener listener;

	public Champion(String name, int maxHP, int mana, int actions, int speed, int attackRange, int attackDamage) {
		this.name = name;
		this.maxHP = maxHP;
		this.mana = mana;
		this.currentHP = this.maxHP;
		this.maxActionPointsPerTurn = actions;
		this.speed = speed;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		this.condition = Condition.ACTIVE;
		this.abilities = new ArrayList<Ability>();
		this.appliedEffects = new ArrayList<Effect>();
		this.currentActionPoints=maxActionPointsPerTurn;
	}

	public void setListener(DamageableListener cl){ listener = cl; }

	public int getMaxHP() {
		return maxHP;
	}

	public String getName() {
		return name;
	}

	public void setCurrentHP(int hp) {
		int prev = currentHP;
		if (hp <= 0) {
			currentHP = 0;
			this.condition = Condition.KNOCKEDOUT;
		} 
		else if (hp > maxHP)
			currentHP = maxHP;
		else
			currentHP = hp;

		if(listener!=null)listener.onHpSet(location,prev,currentHP,maxHP);

	}

	
	public int getCurrentHP() {

		return currentHP;
	}

	public ArrayList<Effect> getAppliedEffects() {
		return appliedEffects;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int currentSpeed) {
		if (currentSpeed < 0)
			this.speed = 0;
		else
			this.speed = currentSpeed;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point currentLocation) {
		this.location = currentLocation;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public int getCurrentActionPoints() {
		return currentActionPoints;
	}

	public void setCurrentActionPoints(int currentActionPoints) {
		if(currentActionPoints>maxActionPointsPerTurn)
			currentActionPoints=maxActionPointsPerTurn;
		else 
			if(currentActionPoints<0)
			currentActionPoints=0;
		this.currentActionPoints = currentActionPoints;
	}

	public int getMaxActionPointsPerTurn() {
		return maxActionPointsPerTurn;
	}

	public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
		this.maxActionPointsPerTurn = maxActionPointsPerTurn;
	}
	
	// Helper Methods
	
	public int compareTo(Object o) {
		Champion champ = (Champion) o ;
		if(this.speed > champ.speed ) return -1;
		else if(this.speed < champ.speed ) return 1;
		else return this.name.compareTo(champ.name);
	}
	
	
	public boolean lessThan30percent() {
		int value = (int)(maxHP * 0.3) ;
		return currentHP < value ;
	}
	
	public void incrementSpeed(int percentage) {
		
		int CurrentSpeed = this.getSpeed();
		int NewCurrentSpeed = (int) (CurrentSpeed*((percentage+100)/100.0));
		this.setSpeed(NewCurrentSpeed);
		
	}
	
	public void decrementSpeed(int percentage) {
		int CurrentSpeed = this.getSpeed();
		int NewCurrentSpeed = (int) (CurrentSpeed/((percentage+100)/100.0));
		this.setSpeed(NewCurrentSpeed); 
	}
	
	public void incrementMaxHP(int percentage) {
		if(!this.getCondition().equals(Condition.KNOCKEDOUT)) {
			int CurrentHP = this.getCurrentHP();
			int MaxHP = this.getMaxHP();
			int NewCurrentHP = (int) (CurrentHP +  (MaxHP*(percentage/100.0)));
			//int NewCurrentHP = (int) (CurrentHP + MaxHP*(percentage/100.0));
			this.setCurrentHP(NewCurrentHP);
		}
		
	}
	
	public void decrementMaxHp(int percentage) {
		if(!this.getCondition().equals(Condition.KNOCKEDOUT)) {
			int MaxHP = this.getMaxHP();
			int NewCurrentHP = (int)  (MaxHP/((percentage+100)/100.0));
			this.setCurrentHP(NewCurrentHP);
		}
	}
	
	public void incrementMana(int percentage) {
		
		int CurrentMana = this.getMana();
		int NewCurrentMana = (int) (CurrentMana +  (CurrentMana*(percentage/100.0)));
		this.setMana(NewCurrentMana);
	}
	
	public void decrementMana(int value) {
		
		int CurrentMana = this.mana;
		int NewCurrentMana = Math.max(0,  CurrentMana - value );	
		this.setMana(NewCurrentMana);
		
	}
	
	public void incrementNormalAttack(int percentage) {
		
		int NormalAttack = this.attackDamage;
		int NewNormalAttack = (int) (NormalAttack +  (NormalAttack)*(percentage/100.0));
		this.setAttackDamage(NewNormalAttack);
	}
	
	public void decrementNormalAttack(int percentage) {
		
		int NormalAttack = this.attackDamage;
		int NewNormalAttack = (int)  (NormalAttack/((percentage+100)/100.0));
		this.setAttackDamage(NewNormalAttack);
		
	}
	
	public void incrementCurrentActionPoints(int value) {
		int CurrentActionPoints = this.getCurrentActionPoints();
		this.setCurrentActionPoints(CurrentActionPoints + value);
	}
	
	public void decrementCurrentActionPoints(int value) {
		int CurrentActionPoints = this.getCurrentActionPoints();
		this.setCurrentActionPoints(CurrentActionPoints - value);
	}
	
	public void incrementMaxActionPoints(int value) {
		int MaxActionPoints = this.getMaxActionPointsPerTurn();
		int NewMaxActionPoints = MaxActionPoints + value;
		this.setMaxActionPointsPerTurn(NewMaxActionPoints);
	}
	
	public void decrementMaxActionPoints(int value) {
		int MaxActionPoints = this.getMaxActionPointsPerTurn();
		int NewMaxActionPoints = Math.max(0, MaxActionPoints - value);
		this.setMaxActionPointsPerTurn(NewMaxActionPoints);
	}
	
	public void incrementCurrentHP(int value) {

		int CurrentHP = this.getCurrentHP();
		this.setCurrentHP(CurrentHP + value);

	}
	
	public void decrementCurrentHP(int value) {
		int CurrentHP = this.getCurrentHP();
		this.setCurrentHP(CurrentHP - value);
	}
	
	public void damage(int value) {
		int CurrentHP = getCurrentHP();
		int NewCurrentHP = CurrentHP - value;
		if(NewCurrentHP<=0) {
			this.setCondition(Condition.KNOCKEDOUT);
		}
		this.setCurrentHP(NewCurrentHP);
	}
	
	public void heal(int value) {
		int CurrentHP = getCurrentHP();
		int NewCurrentHP = CurrentHP + value;
		this.setCurrentHP(NewCurrentHP);
	}
	
	public boolean containShield() {
		boolean found = false;
		Effect eff = null;
		for(Effect effect:this.getAppliedEffects()) {
			if(effect instanceof Shield) {
				eff = effect;
				found = true;
				break;
			}
		}
		if(found){
			getAppliedEffects().remove(eff);
			eff.remove(this);
		}
		return found;
	}
	
	public boolean dodge() {
		boolean found = false;
		for(Effect effect : this.getAppliedEffects()) {
			if(effect instanceof Dodge) {
				found = true;
			}
		}
		if(found) {
			Random random = new Random();
			int dodge = random.nextInt(2);
			return dodge == 1;
		}
		return false;
	}
	
	public boolean root() {
		boolean found = false;
		for(Effect effect:this.getAppliedEffects()) {
			if(effect instanceof Root) {
				found = true;
			}
		}
		return found;
	}
	
	public boolean disarm() {
		boolean found = false;
		for(Effect effect:this.getAppliedEffects()) {
			if(effect instanceof Disarm) {
				found = true;
			}
		}
		return found;
	}
	
	public boolean stuned() {
		boolean found = false;
		for(Effect effect:this.getAppliedEffects()) {
			if(effect instanceof Stun) {
				found = true;
			}
		}
		return found;
	}
	
	
	public void updateEffects() {

		//Decrement Effects
		for(Effect effect : this.getAppliedEffects()) {
			if(!effect.Duration_isZero()) {
				effect.setDuration(effect.getDuration()-1);
			}

		}

		int size = this.getAppliedEffects().size();

		//Remove if 0
		for(int i=size-1;i>=0;i--) {
			Effect effect = this.getAppliedEffects().get(i);
			if(effect.Duration_isZero()) {
				getAppliedEffects().remove(effect);
				effect.remove(this);
			}
		}

	}
	
	public void updateAbilities() {
		for(Ability a:this.abilities) {
			if(!a.isAbleToCast()) {
				a.setCurrentCooldown(a.getCurrentCooldown()-1);
			}
			
		}
		
	}
	
	public void updateActionPoints() {
		this.setCurrentActionPoints(this.getMaxActionPointsPerTurn());
	}
	
	public void endEffect() {
		int size = this.getAppliedEffects().size();
		for(int i=size-1;i>=0;i--) {
			Effect effect = this.getAppliedEffects().get(i);
			if(effect.Duration_isZero()) {
				effect.remove(this);
			}
		}
	}


	//5 : World
	abstract public void useLeaderAbility(ArrayList<Champion> targets);
}
