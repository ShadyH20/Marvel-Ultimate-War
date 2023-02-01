package engine;

import model.world.Champion;

import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Champion> team;
	private Champion leader;
	

	public Player(String name) {
		this.name = name;
		team = new ArrayList<Champion>();
		
	}


	public Champion getLeader() {
		return leader;
	}

	public void setLeader(Champion leader) {
		this.leader = leader;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Champion> getTeam() {
		return team;
	}


}
