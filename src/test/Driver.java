package test;

import engine.Game;
import engine.Player;
import exceptions.GameActionException;
import model.effects.Effect;
import model.effects.Shield;
import model.world.*;



public class Driver {

	public static void main(String[]args) throws Exception {
		
		// first team
		Villain Hela = new Villain("Hela",200,200,100,15,25,150);
		AntiHero Deadpool = new AntiHero("Deadpool",300,200,100,100,25,50);
		Hero Ironman = new Hero("Iron",200,200,100,25,10,100);
		
		
		// second team
		Hero Spiderman = new Hero("Spider",200,200,100,150,25,150);
		Hero Thor = new Hero("Thor",300,200,100,20,25,50);
		Hero Hulk = new Hero("Hulk",200,200,100,25,10,100);
		
		Shield shield = new Shield(10);
		shield.apply(Spiderman);

		Player first = new Player("M1");
		first.getTeam().add(Hela);
		first.getTeam().add(Deadpool);
		first.getTeam().add(Ironman);
		
		Player second = new Player("M2");
		second.getTeam().add(Spiderman);
		second.getTeam().add(Thor);
		second.getTeam().add(Hulk);
		
		Game game = new Game(first,second);

		
		Object[][] board = game.getBoard();
		System.out.println("first team");
		System.out.println(((Champion)board[0][1]).getName());
		System.out.println(((Champion)board[0][2]).getName());
		System.out.println(((Champion)board[0][3]).getName());
		System.out.println();
		System.out.println("second team");
		System.out.println(((Champion)board[4][1]).getName());
		System.out.println(((Champion)board[4][2]).getName());
		System.out.println(((Champion)board[4][3]).getName());
		
		// Begin Game
		System.out.println();
		System.out.println("GAME BEGIN");
		System.out.println();

		//Display
		display(game);
		

		// First Turn
		System.out.println();
		System.out.println("Current Champion is " + game.getCurrentChampion().getName());
		System.out.println();

		//Action
		move(game,Direction.DOWN);
		move(game,Direction.DOWN);
		//game.castAbility(new CrowdControlAbility("Shield", 10 , 10 , 20 ,AreaOfEffect.SELFTARGET, 10, shield));

		//End
		display(game);
		game.endTurn();

		/////////////////////////////////////

		//Second Turn
		System.out.println();
		System.out.println("Current Champion is " + game.getCurrentChampion().getName());
		System.out.println();
		//Action
		move(game,Direction.UP);
		move(game,Direction.UP);
		attack(game,Direction.LEFT);
	//	attack(game,Direction.LEFT);

		//End
		display(game);
		game.endTurn();

		/////////////////////////////////////




		System.out.println();


		//System.out.println((int) (Hulk.getAttackDamage()*0.5));
























	}


	public static void display(Game game) {
		Object[][] board = game.getBoard();
		for(int i=game.getBoard().length-1;i>=0;i--) {
					for(int j=0;j<game.getBoard().length;j++) {
						if(board[i][j] instanceof Cover) {
							System.out.print("Cover"+"("+ i+","+j+")"+"("+((Cover)board[i][j]).getCurrentHP()+")" +"  ");
						}
						else if(board[i][j] instanceof Champion) {
							Champion c = (Champion)board[i][j];
							System.out.print(c.getName() +"("+ i+","+j+")"+"("+((Champion)board[i][j]).getCurrentHP()+")"+"  ");
						}
						else if(board[i][j] == null) {
							System.out.print("Null "+"("+ i+","+j+")("+0+")  ");
						}
					}
						System.out.println();
		}
	}


	public static void move(Game game,Direction d) throws GameActionException {
		game.move(d);
	}

	public static void attack(Game game,Direction d) throws GameActionException {
		game.attack(d);
	}

	public static void showappliedEffect(Game game) {
		for(Effect effect : game.getCurrentChampion().getAppliedEffects()) {
			System.out.println(effect.getName());
		}
	}







}







//System.out.println("Current Champion Location " + game.getCurrentChampion().getLocation());
//System.out.println((((Champion)(game.searchDOWN(game.getCurrentChampion().getLocation(), game.getCurrentChampion().getAttackRange())))).getName());
//System.out.println(game.getCurrentChampion().getLocation().x);

//game.useLeaderAbility();

/*
System.out.println(Hulk.getSpeed());
SpeedUp speed = new SpeedUp(2);
speed.apply(Hulk);
System.out.println(Hulk.getSpeed());
speed.remove(Hulk);
System.out.println(Hulk.getSpeed());
*/
