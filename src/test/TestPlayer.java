package test;

import main.Player;

public class TestPlayer {

	/*
	 * checks if players are initialized
	 */
	public void testPlayerInit()
	{
		Player[] players = new Player[2];
		players[0] = new Player();
		players[1] = new Player();
		
		for(Player p: players)
		{
			System.out.println(p.getPlayerName() + " " + p.getRank() + " " + p.getMarkers());
		}
	}
	
	/*
	 * check if all values(1 to 6) occurs when a roll is made
	 */
	public void testRoll()
	{
		Player p = new Player();
		int i = 1;
		while(i <= 100)
		{
			System.out.print(p.roll() + " ");
			i++;
		}
	}
	
	/*
	 * checks if move works as expected
	 */
	public void testMove()
	{
		Player p = new Player();
		Character selectMarker = p.selectMarker();
		int roll = p.roll();
		System.out.println("Dice value: " + roll);
		p.move(selectMarker, roll);
		System.out.println(p.getMarkers());
	}
}
