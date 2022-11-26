package test;

import main.GamePlay;
import main.Player;

public class TestGamePlay {

	public void testIsHomeExceeded()
	{
		GamePlay gp = new GamePlay();
		Player p = new Player();

		p.move('a', 1);
		p.move('b', 54);
		p.move('c', 56);
		p.move('d', 37);

		System.out.println(gp.isHomeExceeded(p, 'a', 6));
		System.out.println(gp.isHomeExceeded(p, 'b', 5));
		System.out.println(gp.isHomeExceeded(p, 'c', 1));
		System.out.println(gp.isHomeExceeded(p, 'd', 4));
	}

	public void testIsMarkerAtJail()
	{
		GamePlay gp = new GamePlay();
		Player p = new Player();

		p.move('a', 1);
		p.move('b', 57);
		p.move('c', 34);

		System.out.println(gp.isMarkerAtJail(p, 'a'));
		System.out.println(gp.isMarkerAtJail(p, 'b'));
		System.out.println(gp.isMarkerAtJail(p, 'c'));
		System.out.println(gp.isMarkerAtJail(p, 'd'));
	}

	public void testIsMarkerAtHome()
	{
		GamePlay gp = new GamePlay();
		Player p = new Player();

		p.move('a', 1);
		p.move('b', 57);
		p.move('c', 34);

		System.out.println(gp.isMarkerAtHome(p, 'a'));
		System.out.println(gp.isMarkerAtHome(p, 'b'));
		System.out.println(gp.isMarkerAtHome(p, 'c'));
		System.out.println(gp.isMarkerAtHome(p, 'd'));
	}

	public void testIsValidMarker()
	{
		GamePlay gp = new GamePlay();
		Player p = new Player();

		System.out.println(gp.isValidMarker(p, 'a'));
		System.out.println(gp.isValidMarker(p, 'b'));
		System.out.println(gp.isValidMarker(p, 'c'));
		System.out.println(gp.isValidMarker(p, 'd'));

		System.out.println(gp.isValidMarker(p, 'A'));
		System.out.println(gp.isValidMarker(p, '8'));
		System.out.println(gp.isValidMarker(p, '*'));
		System.out.println(gp.isValidMarker(p, 'q'));
	}

	public void testCheckVictory()
	{
		GamePlay gp = new GamePlay();
		Player[] players = new Player[4];

		players[0] = new Player();
		players[1] = new Player();
		players[2] = new Player();
		players[3] = new Player();

		players[0].move('a', 57);
		players[0].move('b', 57);
		players[0].move('c', 57);
		players[0].move('d', 57);

		players[1].move('a', 57);
		players[1].move('b', 56);
		players[1].move('c', 55);
		players[1].move('d', 54);

		players[2].move('a', 57);
		players[2].move('b', 1);
		players[2].move('c', 34);
		players[2].move('d', 20);

		for(Player p: players)
			System.out.println(p.getPlayerName() + " " + gp.checkVictory(p));


	}

	public void testUpdateRank()
	{
		GamePlay gp = new GamePlay();
		Player[] players = new Player[2];

		players[0] = new Player();
		players[1] = new Player();

		players[1].move('a', 57);
		players[1].move('b', 57);
		players[1].move('c', 57);
		players[1].move('d', 57);

		gp.updateRank(players[1]);

		players[0].move('a', 57);
		players[0].move('b', 56);
		players[0].move('c', 55);
		players[0].move('d', 54);

		gp.updateRank(players[0]);

		players[0].move('b', 1);
		players[0].move('c', 2);
		players[0].move('d', 3);

		gp.updateRank(players[0]);

		for(Player p: players)
			System.out.println(p.getRank() + " " + p.getPlayerName());
	}

	public void testEndGame()
	{
		GamePlay gp = new GamePlay();
		Player[] players = new Player[4];

		players[0] = new Player();
		players[1] = new Player();
		players[2] = new Player();
		players[3] = new Player();

		for(Player p: players) //set all players' markers to HOME
		{
			p.move('a', 57);
			p.move('b', 57);
			p.move('c', 57);
			p.move('d', 57);
		}

		players[0].setRank(4); 
		players[1].setRank(2);
		players[2].setRank(1);
		players[3].setRank(3);

		gp.endGame(players);
	}

	public void testPlayerCount()
	{
		GamePlay gp = new GamePlay();

		System.out.println("0" + gp.isValidNumberOfPlayers(0));
		System.out.println("1" + gp.isValidNumberOfPlayers(1));
		System.out.println("4" + gp.isValidNumberOfPlayers(4));
		System.out.println("-1" + gp.isValidNumberOfPlayers(-1));
		System.out.println("10" + gp.isValidNumberOfPlayers(10));
	}

	public void testIsGameEnded()
	{
		GamePlay gp = new GamePlay(); //rank = 0
		Player p = new Player();

		p.move('a', 57);
		p.move('b', 57);
		p.move('c', 57);
		p.move('d', 57);

		System.out.println("isGameEnded " + gp.isGameEnded(2));
		gp.updateRank(p);
		System.out.println("isGameEnded " + gp.isGameEnded(2));
		gp.updateRank(p);
		System.out.println("isGameEnded " + gp.isGameEnded(2));

	}

	public void testCheckConflicts_1()
	{
		GamePlay gp = new GamePlay();
		Player[] players = new Player[2];

		players[0] = new Player();
		players[1] = new Player();

		players[0].move('a', 1);
		players[0].move('b', 1);
		players[0].move('c', 57);
		players[0].move('d', 17);

		players[1].move('a', 4);
		players[1].move('b', 57);
		players[1].move('c', 1);

		System.out.println("Before checking conflicts");
		for(Player p: players)
			System.out.println(p.getMarkers());

		gp.checkConflicts(players[1], 'a', players);

		System.out.println("After checking conflicts");
		for(Player p: players)
			System.out.println(p.getMarkers());
	}

	public void testCheckConflicts_2()
	{
		GamePlay gp = new GamePlay();
		Player[] players = new Player[2];

		players[0] = new Player();
		players[1] = new Player();

		players[0].move('a', 15);
		players[0].move('b', 1);
		players[0].move('c', 57);
		players[0].move('d', 33);

		players[1].move('a', 20);
		players[1].move('b', 57);
		players[1].move('c', 1);

		System.out.println("Before checking conflicts");
		for(Player p: players)
			System.out.println(p.getMarkers());

		gp.checkConflicts(players[0], 'd', players);

		System.out.println("After checking conflicts");
		for(Player p: players)
			System.out.println(p.getMarkers());
	}
	
	public void testCheckConflicts_3()
	{
		GamePlay gp = new GamePlay();
		Player[] players = new Player[2];

		players[0] = new Player();
		players[1] = new Player();

		players[0].move('a', 57);
		players[0].move('b', 14);
		players[0].move('c', 57);
		players[0].move('d', 55);

		players[1].move('a', 57);
		players[1].move('b', 57);
		players[1].move('c', 47);
		players[1].move('d', 1);

		System.out.println("Before checking conflicts");
		for(Player p: players)
			System.out.println(p.getMarkers());

		gp.checkConflicts(players[0], 'b', players);

		System.out.println("After checking conflicts");
		for(Player p: players)
			System.out.println(p.getMarkers());
	}
	
	public void testCheckConflicts_4()
	{
		GamePlay gp = new GamePlay();
		Player[] players = new Player[2];

		players[0] = new Player();
		players[1] = new Player();

		players[0].move('a', 6);
		players[0].move('b', 56);
		players[0].move('c', 34);
		players[0].move('d', 1);

		players[1].move('a', 21);
		players[1].move('b', 57);
		

		System.out.println("Before checking conflicts");
		for(Player p: players)
			System.out.println(p.getMarkers());

		gp.checkConflicts(players[0], 'c', players);

		System.out.println("After checking conflicts");
		for(Player p: players)
			System.out.println(p.getMarkers());
	}
	
	public void testHasAnotherTurn()
	{
		GamePlay gp = new GamePlay();
		
		System.out.println(gp.hasAnotherTurn(false, 6));
		System.out.println(gp.hasAnotherTurn(true, 1));
		System.out.println(gp.hasAnotherTurn(true, 6));
		System.out.println(gp.hasAnotherTurn(false, 1));
	}
}



