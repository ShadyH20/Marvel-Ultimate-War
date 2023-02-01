package engine;

import exceptions.*;
import model.abilities.*;
import model.effects.*;
import model.world.*;
import view.game_scene.AnimationListener;

import java.awt.*;
import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;


// i love you ya shadyyyyyyyyyyyy
/// <3<3<3<3
public class Game {
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private Player firstPlayer;
	private Player secondPlayer;
	private Object[][] board;
	private PriorityQueue turnOrder;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private final static int BOARDWIDTH = 5;
	private final static int BOARDHEIGHT = 5;
	private AnimationListener anim;
	private GameListener listener;

	public Game(Player first, Player second) {
		firstPlayer = first;

		secondPlayer = second;
		availableChampions = new ArrayList<>();
		availableAbilities = new ArrayList<>();
		board = new Object[BOARDWIDTH][BOARDHEIGHT];
		turnOrder = new PriorityQueue(6);
		placeChampions();
		placeCovers();
	}

	public static void loadAbilities(String filePath) throws IOException {
//		InputStreamReader isr = new InputStreamReader(MethodHandles.lookup().lookupClass().getResourceAsStream("./"+filePath));

		InputStream inputStream = new FileInputStream("./"+filePath);
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(isr);
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Ability a = null;
			AreaOfEffect ar = null;
			switch (content[5]) {
			case "SINGLETARGET":
				ar = AreaOfEffect.SINGLETARGET;
				break;
			case "TEAMTARGET":
				ar = AreaOfEffect.TEAMTARGET;
				break;
			case "SURROUND":
				ar = AreaOfEffect.SURROUND;
				break;
			case "DIRECTIONAL":
				ar = AreaOfEffect.DIRECTIONAL;
				break;
			case "SELFTARGET":
				ar = AreaOfEffect.SELFTARGET;
				break;

			}
			Effect e = null;
			if (content[0].equals("CC")) {
				switch (content[7]) {
				case "Disarm":
					e = new Disarm(Integer.parseInt(content[8]));
					break;
				case "Dodge":
					e = new Dodge(Integer.parseInt(content[8]));
					break;
				case "Embrace":
					e = new Embrace(Integer.parseInt(content[8]));
					break;
				case "PowerUp":
					e = new PowerUp(Integer.parseInt(content[8]));
					break;
				case "Root":
					e = new Root(Integer.parseInt(content[8]));
					break;
				case "Shield":
					e = new Shield(Integer.parseInt(content[8]));
					break;
				case "Shock":
					e = new Shock(Integer.parseInt(content[8]));
					break;
				case "Silence":
					e = new Silence(Integer.parseInt(content[8]));
					break;
				case "SpeedUp":
					e = new SpeedUp(Integer.parseInt(content[8]));
					break;
				case "Stun":
					e = new Stun(Integer.parseInt(content[8]));
					break;
				}
			}
			switch (content[0]) {
			case "CC":
				a = new CrowdControlAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), e);
				break;
			case "DMG":
				a = new DamagingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
				break;
			case "HEL":
				a = new HealingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
				break;
			}
			availableAbilities.add(a);
			line = br.readLine();
		}
		br.close();
	}

	public static void loadChampions(String filePath) throws IOException {
		InputStream inputStream = new FileInputStream("./"+filePath);
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(isr);
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Champion c = null;
			switch (content[0]) {
			case "A":
				c = new AntiHero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;

			case "H":
				c = new Hero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			case "V":
				c = new Villain(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			}

			c.getAbilities().add(findAbilityByName(content[8]));
			c.getAbilities().add(findAbilityByName(content[9]));
			c.getAbilities().add(findAbilityByName(content[10]));
			availableChampions.add(c);
			line = br.readLine();
		}
		br.close();
	}

	private static Ability findAbilityByName(String name) {
		for (Ability a : availableAbilities) {
			if (a.getName().equals(name))
				return a;
		}
		return null;
	}

	public void placeCovers() {
		int i = 0;
		while (i < 5) {
			int x = ((int) (Math.random() * (BOARDWIDTH - 2))) + 1;
			int y = (int) (Math.random() * BOARDHEIGHT);

			if (board[x][y] == null) {
				board[x][y] = new Cover(x, y);
				i++;
			}
		}

	}

	public void placeChampions() {
		int i = 1;
		for (Champion c : firstPlayer.getTeam()) {
			board[0][i] = c;
			c.setLocation(new Point(0, i));
			i++;
		}
		i = 1;
		for (Champion c : secondPlayer.getTeam()) {
			board[BOARDHEIGHT - 1][i] = c;
			c.setLocation(new Point(BOARDHEIGHT - 1, i));
			i++;
		}
	
	}

	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}

	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public Object[][] getBoard() {
		return board;
	}

	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}

	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}

	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}

	public static int getBoardwidth() {
		return BOARDWIDTH;
	}

	public static int getBoardheight() {
		return BOARDHEIGHT;
	}

	public void setAnim(AnimationListener anim){this.anim = anim;}

	public void setListener(GameListener listener){this.listener = listener;}


	// Mazen : Engine 1-4
	
	
	public Champion getCurrentChampion() {
		if(turnOrder.isEmpty()) // to avoid NullPointerExcepiton
			this.prepareChampionTurns();

		return (Champion)turnOrder.peekMin();
	}

	public Player checkGameOver() {
		
		if( (firstPlayer.getTeam().size()!=0) && (secondPlayer.getTeam().size()==0) ) {
			return firstPlayer;
		}
		else if( (firstPlayer.getTeam().size()==0) && (secondPlayer.getTeam().size()!=0) ) {
			return secondPlayer;
		}
		else {
			return null; // for Draw
		}
		
	}

	public void move(Direction d) throws UnallowedMovementException , NotEnoughResourcesException {
		
		Champion CurrentChampion = getCurrentChampion();
		Point loc = new Point( CurrentChampion.getLocation().x , CurrentChampion.getLocation().y);

		if(CurrentChampion.getCurrentActionPoints() < 1) {
			throw new NotEnoughResourcesException("Champion does not have enough action points!");
		}
		// The Champion Should be Active to move

		// Champion is Stunned
		if(CurrentChampion.getCondition().equals(Condition.INACTIVE)) {
			throw new UnallowedMovementException("Cannot move while inactive!");
		}
		//Champion is Rooted
		if(CurrentChampion.getCondition().equals(Condition.ROOTED)) {
			throw new UnallowedMovementException("Cannot move while rooted!");
		}

		Point location = CurrentChampion.getLocation();

		// if Champion is at edges of the board he can not move outside border

		if( d.equals(Direction.UP) && location.x == 4) {
			throw new UnallowedMovementException("Cannot move outside the board!");
		}
		else if( d.equals(Direction.DOWN) && location.x == 0 ) {
			throw new UnallowedMovementException("Cannot move outside the board!");
		}
		else if( d.equals(Direction.LEFT) && location.y == 0 ) {
			throw new UnallowedMovementException("Cannot move outside the board!");
		}
		else if( d.equals(Direction.RIGHT) && location.y == 4 ) {
			throw new UnallowedMovementException("Cannot move outside the board!");
		}



		//move
		// if Champion try to move to a place where it contain object we throw UnallowedMovementException
		if(d.equals(Direction.UP)) {
				int NextX = CurrentChampion.getLocation().x;
				NextX++;
				if(board[NextX][CurrentChampion.getLocation().y]==null) {
					board[CurrentChampion.getLocation().x][CurrentChampion.getLocation().y] = null;
					board[NextX][CurrentChampion.getLocation().y] = CurrentChampion;
					CurrentChampion.getLocation().x = NextX;
				}
				else {
					throw new UnallowedMovementException("Cannot move to an occupied spot!");
				}
		}
		else if(d.equals(Direction.DOWN)) {
			int NextX = CurrentChampion.getLocation().x;
			NextX--;
			if(board[NextX][CurrentChampion.getLocation().y]==null) {
				board[CurrentChampion.getLocation().x][CurrentChampion.getLocation().y] = null;
				board[NextX][CurrentChampion.getLocation().y] = CurrentChampion;
				CurrentChampion.getLocation().x = NextX;
			}
			else {
				throw new UnallowedMovementException("Cannot move to an occupied spot!");
			}
		}
		else if(d.equals(Direction.RIGHT)) {
			int NextY = CurrentChampion.getLocation().y;
			NextY++;
			if(board[CurrentChampion.getLocation().x][NextY]==null) {
				board[CurrentChampion.getLocation().x][CurrentChampion.getLocation().y] = null;
				board[CurrentChampion.getLocation().x][NextY] = CurrentChampion;
				CurrentChampion.getLocation().y = NextY;
			}
			else {
				throw new UnallowedMovementException("Cannot move to an occupied spot!");
			}
		}
		else if(d.equals(Direction.LEFT)) {
			int NextY = CurrentChampion.getLocation().y;
			NextY--;
			if(board[CurrentChampion.getLocation().x][NextY]==null) {
				board[CurrentChampion.getLocation().x][CurrentChampion.getLocation().y] = null;
				board[CurrentChampion.getLocation().x][NextY] = CurrentChampion;
				CurrentChampion.getLocation().y = NextY;
			}
			else {
				throw new UnallowedMovementException("Cannot move to an occupied spot!");
			}
		}
		CurrentChampion.decrementCurrentActionPoints(1);

		//Animate move
		anim.onMove(loc ,CurrentChampion.getLocation());
	}
	
	public void attack(Direction d) throws ChampionDisarmedException , NotEnoughResourcesException{
		
		Champion CurrentChampion = this.getCurrentChampion();

		if(CurrentChampion == null)
			return;

		if(CurrentChampion.getCondition().equals(Condition.INACTIVE)) {
			return;
		}

		else if(CurrentChampion.stuned()) {
			return;
		}
		else if(CurrentChampion.disarm()) {
			throw new ChampionDisarmedException("Cannot attack while disarmed!");
		}
		else if(CurrentChampion.getCurrentActionPoints() < 2) {
			throw new NotEnoughResourcesException("Champion does not have enough action points!");
		}
		else {
			CurrentChampion.decrementCurrentActionPoints(2);

			Object object = null;
		if(d.equals(Direction.UP)) {
			object = searchUP(CurrentChampion.getLocation(),CurrentChampion.getAttackRange());
		}
		else if(d.equals(Direction.DOWN)) {
			object = searchDOWN(CurrentChampion.getLocation(),CurrentChampion.getAttackRange());
		}
		else if(d.equals(Direction.RIGHT)) {
			object = searchRIGHT(CurrentChampion.getLocation(),CurrentChampion.getAttackRange());
		}
		else if(d.equals(Direction.LEFT)) {
			object = searchLEFT(CurrentChampion.getLocation(),CurrentChampion.getAttackRange());
		}

		if(object == null) {
			return;
		}
		else {
			Damageable obj = (Damageable)(object);

			if(obj instanceof Champion) {
				if(((Champion) obj).dodge()) {
					return;
				}
				else if(((Champion) obj).containShield()) {
					return;
				}

				int attackDamage = CurrentChampion.getAttackDamage();
				if(obj instanceof Hero && ( CurrentChampion instanceof Villain || CurrentChampion instanceof AntiHero) ) {
						obj.damage((int)( (attackDamage * 1.5 )));
				}
				else if(obj instanceof Villain && ( CurrentChampion instanceof Hero || CurrentChampion instanceof AntiHero) ) {
					obj.damage((int)( (attackDamage * 1.5 )));
				}
				else if(obj instanceof AntiHero && ( CurrentChampion instanceof Hero || CurrentChampion instanceof Villain) ) {
					obj.damage((int)( (attackDamage * 1.5 )));
				}
				else {
					obj.damage(attackDamage);
				}
				//remove Champion if dead
				this.removeFromAll((Champion)obj);

			}
			else if(obj instanceof Cover) {
					obj.damage(CurrentChampion.getAttackDamage());
					//remove Cover if its health equal zero remove from Board
					if(((Cover)obj).getCurrentHP()==0) {
						board[obj.getLocation().x][obj.getLocation().y] = null;
					}
			}

			}
		}

		Player winner = checkGameOver();
		if(winner != null){
			listener.onGameOver(winner);
		}
	}
	
	public Object searchUP(Point start , int range) { 
		int x = start.x;
		int y = start.y;
		Champion CurrentChampion = this.getCurrentChampion();
		while(range!=0) {
			x ++;
			if(x > 4) // we did not found a target champion 
				return null;
			if( board[x][y] != null ) {
				Damageable object = (Damageable)(board[x][y]);
				if(object instanceof Cover) { // return object if cover
					return object;
				}
				if(!isAllies(CurrentChampion,(Champion)object)) { //return object if enemy champion
					return object;
				}
			}	
				range--;
		}
		return null; // No Champion in range 
	}
	
	public Object searchDOWN(Point start , int range) {
		int x = start.x;
		int y = start.y;
		Champion CurrentChampion = this.getCurrentChampion();
		while(range!=0) {	
			x --;	
			if(x < 0) // we did not found a target champion 
				return null;
			if( board[x][y] != null ) {
				Damageable object = (Damageable)(board[x][y]);
				if(object instanceof Cover) { // return object if cover
					return object;
				}
				if(!isAllies(CurrentChampion,(Champion)object)) { //return object if enemy champion
					return object;
				}
			}	
				range--;
		}
		return null; // No Champion in range 
	}

	public Object searchRIGHT(Point start , int range) {
		int x = start.x;
		int y = start.y;
		Champion CurrentChampion = this.getCurrentChampion();
		while(range!=0) {
			y ++;
			if(y > 4) // we did not found a target champion 
				return null;
			if( board[x][y] != null ) {
				Damageable object = (Damageable)(board[x][y]);
				if(object instanceof Cover) { // return object if cover
					return object;
				}
				if(!isAllies(CurrentChampion,(Champion)object)) { //return object if enemy champion
					return object;
				}
			}	
				range--;
		}
		return null; // No Champion in range 
	}

	public Object searchLEFT(Point start , int range) {
			int x = start.x;
			int y = start.y;
			Champion CurrentChampion = this.getCurrentChampion();
			while(range!=0) {
				y --;
				if(y < 0) // we did not found a target champion 
					return null;
				if( board[x][y] != null ) {
					Damageable object = (Damageable)(board[x][y]);
					if(object instanceof Cover) { // return object if cover
						return object;
					}
					if(isEnemy(CurrentChampion,(Champion)object)) { //return object if enemy champion
						return object;
					}
				}	
					range--;
			}
			return null; // No Champion in range 
		}
	
	public boolean isAllies(Champion champ1 , Champion champ2 ) {
		
		return ( firstPlayer.getTeam().contains(champ1) && firstPlayer.getTeam().contains(champ2) ) || ( secondPlayer.getTeam().contains(champ1) && secondPlayer.getTeam().contains(champ2) );
	}

	public void removeFromAll(Champion c) {
		
		if(c.getCondition().equals(Condition.KNOCKEDOUT)||c.getCurrentHP()==0) {
			if(firstPlayer.getTeam().contains(c)){
				firstPlayer.getTeam().remove(c);
			}
			else {
				secondPlayer.getTeam().remove(c);
			}
			
			PriorityQueue tmp = new PriorityQueue(6);
			int size_1 = turnOrder.size();
			
			for(int i=0;i<size_1;i++) {
				Champion champ = (Champion)turnOrder.remove();
				if(!champ.equals(c)) {
					tmp.insert(champ);
				}
			}

			int size_2 = tmp.size();
			for(int i=0;i<size_2;i++) {
				turnOrder.insert(tmp.remove());
			}
			//remove from Board
			board[c.getLocation().x][c.getLocation().y] = null;
		}
	}
	
	//Shady : Engine 5-7

	public boolean hasEffect(Champion c, String effectName){
		ArrayList<Effect> effects = c.getAppliedEffects();
		for(Effect eff : effects){
			if(eff.getName().equals(effectName))
				return true;
		}
		return false;
	}

	public int manhattanDist(Point a, Point b){
		return Math.abs(a.x - b.x) + Math.abs(a.y-b.y);
	}

	//Gets all targets in ability's range according to its AOE and Type
	public ArrayList<Damageable> targetsInRange(Champion champ, Ability a){
		ArrayList<Damageable> targets = new ArrayList<>();
		int range = a.getCastRange();
		Point pos = champ.getLocation();
		AreaOfEffect aoe = a.getCastArea();

		switch(aoe){
			case SURROUND:
				for(int i = pos.x - 1; i <= pos.x + 1; i++)
					for(int j = pos.y - 1; j <= pos.y + 1; j++){
						if( (i >= 0 && i < BOARDHEIGHT) && (j >= 0 && j < BOARDWIDTH) ) //Check if point is inside the board
							if(!(i == pos.x && j == pos.y))	//Check if point is not the middle(yellow)
								if(board[i][j] != null) {    //if cell NOT EMPTY
									//Add target
									addTarget(champ, (Damageable) board[i][j], a, targets);
								}
					}
				break;
			case SELFTARGET:
				targets.add(champ);
				break;
			case TEAMTARGET:
				//Check if ability is positive
				boolean isPositive =
						a instanceof HealingAbility ||
								(a instanceof CrowdControlAbility &&
								((CrowdControlAbility)a).getEffect().getType().equals(EffectType.BUFF));

				ArrayList<Champion> targetTeam = isPositive?getCurrentPlayer().getTeam():getOtherPlayer().getTeam();

				//Add champions from target team if in range
				for(Champion c : targetTeam){
					if(manhattanDist(champ.getLocation(),c.getLocation()) <= range)
						targets.add(c);
				}

				break;
		}

		return targets;
	}

	//Takes Target and adds it to list according to: type of Ability and type of Target
	void addTarget(Champion champ, Damageable target, Ability a, ArrayList<Damageable> targets){
		//Cover only affected by DMG ability
		if(target instanceof Cover){
			if(a instanceof DamagingAbility){
				if(!(a.getCastArea().equals(AreaOfEffect.TEAMTARGET)))
					targets.add(target);
			}
			return;
		}

		//target is a Champion
		boolean isEnemy = isEnemy(champ,(Champion)target);
		//From MS2 6.1 3rd point
		if(a instanceof CrowdControlAbility){
			Effect e = ((CrowdControlAbility) a).getEffect();
			if(e.getType() == EffectType.DEBUFF) {
				if (isEnemy)	//enemy
					targets.add(target);
			}
			else {		//BUFF
				if (!isEnemy)	//friendly
					targets.add(target);
			}
		}
		else if(a instanceof DamagingAbility){
			if(isEnemy) {
				//Checks for shield
				if(!((Champion) target).containShield())
					targets.add(target);
			}
		}
		else if(a instanceof HealingAbility){
			if(!isEnemy)
				targets.add(target);
		}
	}

	boolean isEnemy(Champion c1, Champion c2){
//		int c1Player = firstPlayer.getTeam().contains(c1)?1:2;
//		int c2Player = firstPlayer.getTeam().contains(c2)?1:2;
		int c1Player = 0;
		if(firstPlayer.getTeam().contains(c1))
			c1Player = 1;
		else if(secondPlayer.getTeam().contains(c1))
			c1Player = 2;
		else c1Player = searchForChampName(c1,firstPlayer.getTeam(),secondPlayer.getTeam());

		int c2Player = 0;
		if(firstPlayer.getTeam().contains(c2))
			c2Player = 1;
		else if(secondPlayer.getTeam().contains(c2))
			c2Player = 2;
		else c2Player = searchForChampName(c2,firstPlayer.getTeam(),secondPlayer.getTeam());

		
		return c1Player != c2Player;

	}

	int searchForChampName(Champion champ, ArrayList<Champion> t1, ArrayList<Champion> t2){
		for(Champion c : t1){
			if(isSameChamp(c,champ)){
				return 1;
			}
		}
		for(Champion c : t2){
			if(isSameChamp(c,champ)){
				return 2;
			}
		}
		return 0;
	}

	boolean isSameChamp(Champion c1, Champion c2){
		return c1.getName().equals(c2.getName()) &&
			   c1.getMaxHP() == c2.getMaxHP() &&
			   c1.getCurrentHP() == c2.getCurrentHP() &&
			   c1.getLocation().equals(c2.getLocation());
	}

	void abilityExceptions(Champion champ, Ability a) throws AbilityUseException,NotEnoughResourcesException{
		//Check Effects: Silence
		if(hasEffect(champ,"Silence")){
			throw new AbilityUseException("Cannot use abilities when Silenced!");
		}
		if(champ.getCondition() == Condition.INACTIVE) {
			throw new AbilityUseException("Cannot use abilities when Inactive!");
		}
		if(a.getCurrentCooldown() > 0){
			throw new AbilityUseException("Cannot use ability on cooldown!");
		}

		//Check resources: Mana & Action points
		if(champ.getMana() < a.getManaCost()){
			throw new NotEnoughResourcesException("Not enough Mana!");
		}
		if(champ.getCurrentActionPoints() < a.getRequiredActionPoints()){
			throw new NotEnoughResourcesException("Not enough Action Points!");
		}

	}

	//Engine : Method 5

	//AOE is : TEAMTARGET, SURROUND, SELFTARGET
	public void castAbility(Ability a) throws AbilityUseException, NotEnoughResourcesException, CloneNotSupportedException{

		Champion champ = getCurrentChampion();

		//Checks all ability conditions, and if any are violated we don't continue
		abilityExceptions(champ,a);

		//Get all targets in range
		ArrayList<Damageable> targets = targetsInRange(champ,a);

		//Execute the Ability on the targets list
		a.execute(targets);

		//Remove KNOCKEDOUT champions and covers
		for(Damageable d: targets){
			if(d instanceof Champion c) {
				if (c.getCondition().equals(Condition.KNOCKEDOUT) || c.getCurrentHP() == 0)
					this.removeFromAll(c);
			}
			else if(d instanceof Cover c){
				if(c.getCurrentHP() == 0){
					Point p = c.getLocation();
					board[p.x][p.y] = null;
				}
			}
		}

		//Use resources
		champ.decrementMana(a.getManaCost());
		champ.decrementCurrentActionPoints(a.getRequiredActionPoints());

		//Update Cooldown
		a.setCurrentCooldown(a.getBaseCooldown());

		Player winner = checkGameOver();
		if(winner != null){
			listener.onGameOver(winner);
		}

	}

	//Ability AOE is DIRECTIONAL
	public void castAbility(Ability a, Direction d) throws AbilityUseException,NotEnoughResourcesException,CloneNotSupportedException {
		Champion champ = getCurrentChampion();

		abilityExceptions(champ,a);

		//Get all targets in range
		ArrayList<Damageable> targets = new ArrayList<>();
		int range = a.getCastRange();
		Point p = champ.getLocation();

		switch (d){
			case RIGHT:
				for(int j = p.y + 1; j <= p.y + range; j++){
					if(j < BOARDWIDTH){
						if(board[p.x][j] != null){
							addTarget(champ,(Damageable) board[p.x][j],a,targets);
						}
					}
				}
				break;
			case LEFT:
				for(int j = p.y - 1; j >= p.y - range; j--){
					if(j >= 0){
						if(board[p.x][j] != null){
							addTarget(champ,(Damageable) board[p.x][j],a,targets);
						}
					}
				}
				break;
			case UP:
				for(int i = p.x + 1; i <= p.x + range; i++){
					if(i < BOARDHEIGHT){
						if(board[i][p.y] != null){
							addTarget(champ,(Damageable) board[i][p.y],a,targets);
						}
					}
				}
				break;
			case DOWN:
				for(int i = p.x - 1; i >= p.x - range; i--){
					if(i >= 0){
						if(board[i][p.y] != null){
							addTarget(champ,(Damageable) board[i][p.y],a,targets);
						}
					}
				}
				break;
		}

		//Execute the Ability on the targets list
		a.execute(targets);

		//Remove KNOCKEDOUT champions and covers
		for(Damageable dam: targets){
			if(dam instanceof Champion c) {
				if (c.getCondition().equals(Condition.KNOCKEDOUT) || c.getCurrentHP() == 0)
					this.removeFromAll(c);
			}
			else if(dam instanceof Cover c){
				if(c.getCurrentHP() == 0){
					Point pCover = c.getLocation();
					board[pCover.x][pCover.y] = null;
				}
			}
		}

		//Use resources
		champ.decrementMana(a.getManaCost());
		champ.decrementCurrentActionPoints(a.getRequiredActionPoints());

		//Update Cooldown
		a.setCurrentCooldown(a.getBaseCooldown());

		Player winner = checkGameOver();
		if(winner != null){
			listener.onGameOver(winner);
		}
	}

	//Ability AOE is SINGLETARGET
	public void castAbility(Ability a, int x, int y) throws AbilityUseException , InvalidTargetException ,NotEnoughResourcesException, CloneNotSupportedException {

		Champion champ = getCurrentChampion();

		abilityExceptions(champ,a);

		//Check if target not empty and if cover, that ability is DMG
		if(board[x][y] == null ||
				(!(a instanceof DamagingAbility) && board[x][y] instanceof Cover)){
			throw new InvalidTargetException("Invalid target.");
		}

		//Check if target not in range
		if(manhattanDist(champ.getLocation(),new Point(x,y)) > a.getCastRange()){
			throw new AbilityUseException("Target out of range!");
		}

		Damageable target = (Damageable) board[x][y];
		if(target instanceof Champion targetChamp){
			//Check if ability is positive
			boolean isPositive =
					a instanceof HealingAbility ||
							(a instanceof CrowdControlAbility &&
									((CrowdControlAbility)a).getEffect().getType().equals(EffectType.BUFF));
			//if target is an enemy
			boolean isEnemy = isEnemy(champ,targetChamp);

			if(isPositive == isEnemy){
				throw new InvalidTargetException("Invalid target team.");
			}
		}

		//Add target
		ArrayList<Damageable> targets = new ArrayList<>();
		targets.add((Damageable) board[x][y]);

		//Execute the Ability on the targets list
		a.execute(targets);

		//Remove KNOCKEDOUT champions and covers
		for(Damageable dam: targets){
			if(dam instanceof Champion c) {
				if (c.getCondition().equals(Condition.KNOCKEDOUT) || c.getCurrentHP() == 0)
					this.removeFromAll(c);
			}
			else if(dam instanceof Cover c){
				if(c.getCurrentHP() == 0){
					Point pCover = c.getLocation();
					board[pCover.x][pCover.y] = null;
				}
			}
		}

		//Use resources
		champ.decrementMana(a.getManaCost());
		champ.decrementCurrentActionPoints(a.getRequiredActionPoints());

		//Update Cooldown
		a.setCurrentCooldown(a.getBaseCooldown());

		Player winner = checkGameOver();
		if(winner != null){
			listener.onGameOver(winner);
		}
	}

	// Nour : Engine 8-10
	public Player getCurrentPlayer() {
		Champion CurrentChampion = this.getCurrentChampion();   //  To know  currentPlayer
		 ArrayList<Champion> team1=firstPlayer.getTeam();
		 ArrayList<Champion> team2=secondPlayer.getTeam();
		 
		if(team1.contains(CurrentChampion))
			return firstPlayer;
		else
			return secondPlayer;
	}
	public Player getOtherPlayer() {
		Champion CurrentChampion = this.getCurrentChampion();   //  To know  currentPlayer opponent  
		ArrayList<Champion> team1=firstPlayer.getTeam();
//		ArrayList<Champion> team2=secondPlayer.getTeam();
		 
		if(team1.contains(CurrentChampion))
			return secondPlayer;
		else
			return firstPlayer;
	}
	public boolean currentChampionIsLeader() {   // To check whether current player's champion is leader or not 
		Champion CurrentChampion = this.getCurrentChampion();
		Player currentPlayer=this.getCurrentPlayer();

		return CurrentChampion.equals(currentPlayer.getLeader());
		
	}
	public boolean  isCurrentPlayerLeaderAbilityUsed(Player p) {  
		if(p.equals(firstPlayer))
			return isFirstLeaderAbilityUsed();
		else
			return isSecondLeaderAbilityUsed();
		
		
	}
	// method 8
	public void useLeaderAbility() throws LeaderNotCurrentException,LeaderAbilityAlreadyUsedException,CloneNotSupportedException {

		Champion CurrentChampion = this.getCurrentChampion();
		if(CurrentChampion==null) {
			return;
		}
		Player currentPlayer=this.getCurrentPlayer();
		ArrayList<Champion> targets=new ArrayList<>();


		if(!(this.currentChampionIsLeader()))
			throw new LeaderNotCurrentException("Current champion is not the leader!");
		else {

			if(this.isCurrentPlayerLeaderAbilityUsed(currentPlayer))
				throw new LeaderAbilityAlreadyUsedException("Leader ability already used!");
			else {
				if(CurrentChampion instanceof Hero) {
					targets.addAll(currentPlayer.getTeam());       // if leader is a hero   targets ---->> leader's team
					CurrentChampion.useLeaderAbility(targets);

					if(currentPlayer.equals(firstPlayer))
                    	firstLeaderAbilityUsed=true;

                    else
                    	secondLeaderAbilityUsed=true;

				}

					if (CurrentChampion instanceof Villain){  // if leader is a villain     targets-->> opponent team
						for(Champion z: getOtherPlayer().getTeam()){
							if(z.lessThan30percent())
								targets.add(z);
						}
						CurrentChampion.useLeaderAbility(targets);
						for(Champion c: targets){
							if(c.getCondition().equals(Condition.KNOCKEDOUT))
							  this.removeFromAll(c);
						}

						if(currentPlayer.equals(firstPlayer))
							firstLeaderAbilityUsed=true;

						else
							secondLeaderAbilityUsed=true;
					}
					if (CurrentChampion instanceof AntiHero) {

						targets.addAll(firstPlayer.getTeam());
						targets.remove(firstPlayer.getLeader());   // if leader is anti-hero   targets-->> two teams except two leaders
						targets.addAll(secondPlayer.getTeam());
						targets.remove(secondPlayer.getLeader());


						CurrentChampion.useLeaderAbility(targets);
                        if(currentPlayer.equals(firstPlayer))
                        	firstLeaderAbilityUsed=true;

                        else
                        	secondLeaderAbilityUsed=true;
					}

				}
		}

		Player winner = checkGameOver();
		if(winner != null){
			listener.onGameOver(winner);
		}
	}
	public void endTurn() {
		turnOrder.remove();
		if(turnOrder.isEmpty()) {
			this.prepareChampionTurns();
		}

		while(this.getCurrentChampion().getCondition().equals(Condition.INACTIVE) || getCurrentChampion().stuned() ) {
			this.getCurrentChampion().updateAbilities();
			this.getCurrentChampion().updateActionPoints();
			this.getCurrentChampion().updateEffects();
			turnOrder.remove();

			if(turnOrder.isEmpty()) {
				this.prepareChampionTurns();
			}

		}

		this.getCurrentChampion().updateAbilities();
		this.getCurrentChampion().updateActionPoints();
		this.getCurrentChampion().updateEffects();
	}
		

	private void prepareChampionTurns() {
		ArrayList<Champion> team1=firstPlayer.getTeam();
		ArrayList<Champion> team2=secondPlayer.getTeam();
		for(Champion c:team1) {
		  if(!c.getCondition().equals(Condition.KNOCKEDOUT))
			  turnOrder.insert(c);
		}
		for(Champion c:team2) {
		  if(!c.getCondition().equals(Condition.KNOCKEDOUT) )
			  turnOrder.insert(c);
		}

	}

	
}
