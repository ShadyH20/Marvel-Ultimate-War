package view.game_scene;

import engine.Game;
import model.abilities.Ability;
import model.world.Champion;
import model.world.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public interface GameSceneListener {
    void onGameActionClicked(String actionName);
    void onGameActionDirectional(String actionName, Direction d);
    void onDirectionSelected(String actionName, int abilityIndex, Direction d);
    void onCastAbilitySelected();
    void onAbilityClicked(int index);
    void onAbilitySelected(int index);
    void onDirectionalAbilityConfirm(int abilitySelected,Direction d);
    void onSingleTargetAbilityConfirm(int abilitySelected, Point p);
    String getLeaderAbilityDescription(Champion c);
    Game getGame();
    Champion getCurrChamp();
    JFrame getFrame();
}
