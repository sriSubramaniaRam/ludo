package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Player {
	
	private String playerName;
	private Character markerColor;
	private Map<Character, Integer> markers;
	private Integer rank;
	private Scanner sc;
	
	/*
	 * whenever a player is instantiated, all 4 markers are set to -1
	 * which means all 4 markers are in JAIL
	 */
	public Player()
	{
		sc = new Scanner(System.in);
		markers = new HashMap<Character, Integer>();
		
		System.out.println("Enter player name");
		playerName = sc.next();
		
		System.out.println("Enter marker color(r/b/y/g)");
		markerColor = sc.next().toLowerCase().charAt(0);
		
		while(markerColor != 'r' && markerColor != 'b' && markerColor != 'y' && markerColor != 'g')
		{
			System.out.println("Invalid marker color selected");
			System.out.println("Enter marker color(r/b/y/g)");
			markerColor = sc.next().toLowerCase().charAt(0);
		}
		
		markers.put('a', -1);
		markers.put('b', -1);
		markers.put('c', -1);
		markers.put('d', -1);

		rank = -1;
	}
	
	
	public String getPlayerName()
	{
		return playerName;
	}
	
	public void setRank(Integer rank)
	{
		this.rank = rank;
	}
	
	public Integer getRank()
	{
		return rank;
	}
	
	public Map<Character, Integer> getMarkers()
	{
		return markers;
	}
	
	/*
	 * method to roll a dice
	 * returns an integer between 1 and 6(both inclusive)
	 */
	public int roll() 
	{
		return (int) (1 + Math.random()*6);
	}
	
	/*
	 * displaces the selectedMarker, diceValue units away from it's current position 
	 */
	public void move(Character selectedMarker, Integer diceValue)
	{
		markers.put(selectedMarker, markers.get(selectedMarker)+diceValue);
	}

	/*
	 * method to select a marker for a move
	 */
	public Character selectMarker() 
	{
		System.out.println("Select a marker(a/b/c/d)");
		return sc.next().charAt(0);
	}
	
	
	public Character getMarkerColor()
	{
		return markerColor;
	}
	
	
}

