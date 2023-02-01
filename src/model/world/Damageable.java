package model.world;

import java.awt.*;

public interface Damageable {
	
	// No need for declaring Access Modifier to be " public " it is public by default
	Point getLocation();
	int getCurrentHP();
	void setCurrentHP(int hp);
	void damage(int damageamount);
	void heal(int healamount);
	
}
