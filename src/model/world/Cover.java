package model.world;

import java.awt.*;

public class Cover implements Damageable {
	private int currentHP;
	private int maxHp;
	private DamageableListener listener;

	private Point location;

	public Cover(int x, int y) {
		this.currentHP = (int)(( Math.random() * 900) + 100);
		maxHp = currentHP;
		location = new Point(x, y);
	}

	public void setListener(DamageableListener listener) {
		this.listener = listener;
	}

	public int getCurrentHP() {
		return this.currentHP;
	}

	public void setCurrentHP(int newHp) {
		int old = currentHP;
		if (newHp < 0) {
			currentHP = 0;
		
		} else
			currentHP = newHp;
		if(listener != null)
			listener.onHpSet(location,old,currentHP,maxHp);
	}

	public Point getLocation() {
		return location;
	}
	public int getMaxHp(){return maxHp;}
	
	// NEW 
	
	public void heal(int healamount) {
		if(this.getCurrentHP()!=0) {
			int CurrentHP = this.currentHP;
			int NewCurrentHP = CurrentHP + healamount ;
			this.setCurrentHP(NewCurrentHP);
		}
	}
	
	public void damage(int damageamount) {
		int CurrentHP = this.currentHP;
		int NewCurrentHP = CurrentHP - damageamount ;
		this.setCurrentHP(NewCurrentHP);
	}

	

}
