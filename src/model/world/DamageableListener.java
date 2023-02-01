package model.world;

import java.awt.*;

public interface DamageableListener {
    void onHpSet(Point location, int prev, int currentHP, int maxHP);

}
